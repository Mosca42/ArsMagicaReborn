package amreborn.items;

import amreborn.ArsMagicaReborn;
import amreborn.blocks.BlockInvisibleUtility;
import amreborn.defs.BlockDefs;
import amreborn.particles.AMParticle;
import amreborn.particles.ParticleHoldPosition;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class ItemCandle extends ItemArsMagica {

	private static final int radius = 10;
	private static final int short_radius = 5;
	private static final float immediate_radius = 2.5f;

	public ItemCandle() {
		super();
		setMaxStackSize(1);
	}

	public void setSearchBlock(IBlockState state, ItemStack item) {
		if (!item.hasTagCompound())
			item.setTagCompound(new NBTTagCompound());

		setFlameColor(item, 0, 1, 0);
		item.getTagCompound().setInteger("search_block", Block.getStateId(state));
	}

	private void setFlameColor(ItemStack stack, float r, float g, float b) {
		if (!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());

		stack.getTagCompound().setFloat("flame_red", r);
		stack.getTagCompound().setFloat("flame_green", g);
		stack.getTagCompound().setFloat("flame_blue", b);
	}

	@Override
	public boolean getShareTag() {
		return true;
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int indexInInventory, boolean isCurrentlyHeld) {
		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			if(player.getHeldItem(EnumHand.MAIN_HAND) == stack) {
			if (!world.isRemote && ArsMagicaReborn.config.candlesAreRovingLights() && world.isAirBlock(player.getPosition()) && world.getLightFor(EnumSkyBlock.BLOCK, player.getPosition()) < 14) {
				world.setBlockState(player.getPosition(), BlockDefs.invisibleUtility.getDefaultState().withProperty(BlockInvisibleUtility.TYPE, BlockInvisibleUtility.EnumInvisibleType.HIGH_ILLUMINATED), 2);
			}}
		}
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		String name = I18n.translateToLocal("item." + ArsMagicaReborn.MODID + ":warding_candle.name");
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("search_block")) {
			IBlockState state = Block.getStateById(stack.getTagCompound().getInteger("search_block"));
			ItemStack blockStack = new ItemStack(state.getBlock(), 0, state.getBlock().getMetaFromState(state));
			Item tempItem = blockStack.getItem();
			if (tempItem == null) {
				name += " (" + stack.getTagCompound().getInteger("search_block") + ":" + stack.getTagCompound().getInteger("search_meta") + ")";
			} else {
				name += " (" + blockStack.getDisplayName() + ")";
			}
		} else {
			name += " (" + I18n.translateToLocal("am2.tooltip.unattuned") + ")";
		}

		return name;
	}

	@Override
	public boolean getHasSubtypes() {
		return true;
	}

	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, NonNullList<ItemStack> par3List) {
		ItemStack unattuned = new ItemStack(this, 1, 0);
		par3List.add(unattuned);
	}
}
