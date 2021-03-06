package amreborn.blocks.tileentity;

import java.util.List;

import com.google.common.collect.Lists;

import amreborn.api.blocks.IKeystoneLockable;
import amreborn.api.recipes.RecipesEssenceRefiner;
import amreborn.blocks.BlockEssenceRefiner;
import amreborn.defs.BlockDefs;
import amreborn.power.PowerNodeRegistry;
import amreborn.power.PowerTypes;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

public class TileEntityEssenceRefiner extends TileEntityAMPower implements IInventory, IKeystoneLockable<TileEntityEssenceRefiner>, ISidedInventory{

	public static final float REFINE_TIME = 400;
	private static final int OUTPUT_INDEX = 5;
	private static final int FUEL_INDEX = 2;
	public static final float TICK_REFINE_COST = 12.5f;

	private NonNullList<ItemStack> inventory;
	public float remainingRefineTime;

	public TileEntityEssenceRefiner(){
		super(1000);
		inventory = NonNullList.<ItemStack>withSize(getSizeInventory(), ItemStack.EMPTY);
		remainingRefineTime = 0;
	}

	@Override
	public int getSizeInventory(){
		return 9;
	}

	@Override
	public ItemStack getStackInSlot(int i){
		return inventory.get(i);
	}

	@Override
	public ItemStack decrStackSize(int i, int j){
		if (inventory.get(i) != ItemStack.EMPTY){
			if (inventory.get(i).getCount() <= j){
				ItemStack itemstack = inventory.get(i);
				inventory.set(i, ItemStack.EMPTY);
				return itemstack;
			}
			ItemStack itemstack1 = inventory.get(i).splitStack(j);
			if (inventory.get(i).getCount() == 0){
				inventory.set(i, ItemStack.EMPTY);
			}
			return itemstack1;
		}else{
			return ItemStack.EMPTY;
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack){
		inventory.set(i, itemstack);
		if (itemstack != ItemStack.EMPTY && itemstack.getCount() > getInventoryStackLimit()){
			itemstack.setCount(getInventoryStackLimit());
		}
	}

	@Override
	public String getName(){
		return "Essence Refiner";
	}

	@Override
	public int getInventoryStackLimit(){
		return 64;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound){
		super.readFromNBT(nbttagcompound);
		NBTTagList nbttaglist = nbttagcompound.getTagList("EssenceRefinerInventory", Constants.NBT.TAG_COMPOUND);
		inventory = NonNullList.<ItemStack>withSize(getSizeInventory(), ItemStack.EMPTY);
		for (int i = 0; i < nbttaglist.tagCount(); i++){
			String tag = String.format("ArrayIndex", i);
			NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.getCompoundTagAt(i);
			byte byte0 = nbttagcompound1.getByte(tag);
			if (byte0 >= 0 && byte0 < inventory.size()){
				inventory.set(byte0, new ItemStack(nbttagcompound1));
			}
		}

		remainingRefineTime = nbttagcompound.getFloat("RefineTime");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound){
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setFloat("RefineTime", remainingRefineTime);
		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < inventory.size(); i++){
			if (inventory.get(i) != ItemStack.EMPTY){
				String tag = String.format("ArrayIndex", i);
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte(tag, (byte)i);
				inventory.get(i).writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		nbttagcompound.setTag("EssenceRefinerInventory", nbttaglist);
		return nbttagcompound;
	}

	public int getRefinementProgressScaled(int i){
		return (int)((remainingRefineTime * i) / getRefineTime());
	}

	public float getRefinementPercentage(){
		if (!isRefining())
			return 0;
		return 1.0f - (remainingRefineTime / getRefineTime());
	}

	public boolean isRefining(){
		return remainingRefineTime > 0;
	}

	private float getRefineTime(){
		return REFINE_TIME;
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket(){
		NBTTagCompound compound = new NBTTagCompound();
		this.writeToNBT(compound);
		return new SPacketUpdateTileEntity(pos, 0, compound);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt){
		this.readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public void update(){
		super.update();

		if (!world.isRemote){
			if (canRefine()){
				if (remainingRefineTime <= 0){
					//start refining
					remainingRefineTime = getRefineTime();
					world.markAndNotifyBlock(pos, world.getChunkFromBlockCoords(pos), world.getBlockState(pos), world.getBlockState(pos), 2);
				}
			}else{
				if (remainingRefineTime != 0){
					remainingRefineTime = 0;
					world.markAndNotifyBlock(pos, world.getChunkFromBlockCoords(pos), world.getBlockState(pos), world.getBlockState(pos), 2);
				}
			}

			if (isRefining()){
				setActiveTexture();
				if (PowerNodeRegistry.For(this.world).checkPower(this, TICK_REFINE_COST)){
					remainingRefineTime--;
					if (remainingRefineTime % 10 == 0)
						world.markAndNotifyBlock(pos, world.getChunkFromBlockCoords(pos), world.getBlockState(pos), world.getBlockState(pos), 2);
					if (remainingRefineTime <= 0){
						remainingRefineTime = 0;
						if (!world.isRemote){
							refineItem();
						}
					}

					PowerNodeRegistry.For(this.world).consumePower(this, PowerNodeRegistry.For(this.world).getHighestPowerType(this), TICK_REFINE_COST);
				}
			}else{
				setActiveTexture();
			}
		}
	}

	private void setActiveTexture(){
		if (this.getWorld().getBlockState(pos).getBlock() != BlockDefs.essenceRefiner){ this.invalidate(); return;}
		if (world.getBlockState(pos).getValue(BlockEssenceRefiner.ACTIVE) == isRefining() || world.isRemote) return;
		if (isRefining()){
			if (!world.isRemote){
				world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockEssenceRefiner.ACTIVE, true), 3);
			}
		}else{
			if (!world.isRemote){
				world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockEssenceRefiner.ACTIVE, false), 3);
			}
		}
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
		return oldState.getBlock() != newSate.getBlock();
	}

	private boolean canRefine(){
		if (inventory.get(FUEL_INDEX) == ItemStack.EMPTY){
			return false;
		}
		ItemStack itemstack = RecipesEssenceRefiner.essenceRefinement().GetResult(getCraftingGridContents(), null);
		if (itemstack == ItemStack.EMPTY){
			return false;
		}
		if (inventory.get(OUTPUT_INDEX) == ItemStack.EMPTY){
			return true;
		}
		if (!inventory.get(OUTPUT_INDEX).isItemEqual(itemstack)){
			return false;
		}
		if (inventory.get(OUTPUT_INDEX).getCount() < getInventoryStackLimit() && inventory.get(OUTPUT_INDEX).getCount() < inventory.get(OUTPUT_INDEX).getMaxStackSize()){
			return true;
		}
		return inventory.get(OUTPUT_INDEX).getCount() < itemstack.getMaxStackSize();
	}

	public void refineItem(){
		if (!canRefine()){
			return;
		}
		ItemStack itemstack = RecipesEssenceRefiner.essenceRefinement().GetResult(getCraftingGridContents(), null);
		if (inventory.get(OUTPUT_INDEX) == ItemStack.EMPTY){
			inventory.set(OUTPUT_INDEX, itemstack.copy());
		}else if (inventory.get(OUTPUT_INDEX).getItem() == itemstack.getItem()){
			inventory.get(OUTPUT_INDEX).grow(itemstack.getCount());
		}
		decrementCraftingGridContents();
	}

	private void decrementCraftingGridContents(){
		for (int i = 0; i < 5; ++i){
			decrementCraftingGridSlot(i);
		}
	}

	@SuppressWarnings("deprecation")
	private void decrementCraftingGridSlot(int slot){
		if (inventory.get(slot).getItem().hasContainerItem()){
			inventory.set(slot, new ItemStack(inventory.get(slot).getItem().getContainerItem()));
		}else{
			inventory.get(slot).shrink(1);
		}

		if (inventory.get(slot).getCount() <= 0){
			inventory.set(slot, ItemStack.EMPTY);
		}
	}

	private ItemStack[] getCraftingGridContents(){
		ItemStack[] contents = new ItemStack[5];
		for (int i = 0; i < 5; ++i){
			contents[i] = inventory.get(i);
		}
		return contents;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer entityplayer){
		if (world.getTileEntity(pos) != this){
			return false;
		}
		return entityplayer.getDistanceSqToCenter(pos) <= 64D;
	}

	@Override
	public void openInventory(EntityPlayer entityplayer){
	}

	@Override
	public void closeInventory(EntityPlayer entityplayer){
	}

	@Override
	public ItemStack removeStackFromSlot(int i){
		if (inventory.get(i) != ItemStack.EMPTY){
			ItemStack itemstack = inventory.get(i);
			inventory.set(i, ItemStack.EMPTY);
			return itemstack;
		}else{
			return ItemStack.EMPTY;
		}
	}

	@Override
	public boolean hasCustomName(){
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack){
		return false;
	}

	@Override
	public int getChargeRate(){
		return 500;
	}

	@Override
	public boolean canRelayPower(PowerTypes type){
		return false;
	}

	@Override
	public ItemStack[] getRunesInKey(){
		ItemStack[] runes = new ItemStack[3];
		runes[0] = inventory.get(6);
		runes[1] = inventory.get(7);
		runes[2] = inventory.get(8);
		return runes;
	}

	@Override
	public boolean keystoneMustBeHeld(){
		return false;
	}

	@Override
	public boolean keystoneMustBeInActionBar(){
		return false;
	}

	@Override
	public List<PowerTypes> getValidPowerTypes(){
		return Lists.newArrayList(
				PowerTypes.MANA
		);
	}


	@Override
	public int[] getSlotsForFace(EnumFacing side){
		return new int[]{5};
	}


	@Override
	public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_, EnumFacing p_102007_3_){
		return false;
	}


	@Override
	public boolean canExtractItem(int slot, ItemStack item, EnumFacing side){
		return slot == 5;
	}

	@Override
	public ITextComponent getDisplayName() {
		// TODO Auto-generated method stub
		return new TextComponentString("Essence Refiner");
	}

	@Override
	public int getField(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getFieldCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}
}
