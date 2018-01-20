package amreborn.blocks;

import amreborn.ArsMagicaReborn;
import amreborn.api.blocks.IKeystoneLockable;
import amreborn.blocks.tileentity.TileEntityMagiciansWorkbench;
import amreborn.defs.IDDefs;
import amreborn.defs.ItemDefs;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;

public class BlockMagiciansWorkbench extends BlockAMSpecialRenderContainer {

	public static final PropertyEnum<EnumFacing> FACING = PropertyEnum.create("facing", EnumFacing.class, EnumFacing.HORIZONTALS);

	public BlockMagiciansWorkbench() {
		super(Material.WOOD);
		setHardness(2.0f);
		setResistance(2.0f);
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int meta = state.getValue(FACING).getHorizontalIndex();
		return meta;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta & 0x3));
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new TileEntityMagiciansWorkbench();
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.INVISIBLE;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

		TileEntity te = worldIn.getTileEntity(pos);
		if (te != null && te instanceof TileEntityMagiciansWorkbench) {

			super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
			if (playerIn.getHeldItem(hand) != ItemStack.EMPTY && playerIn.getHeldItem(hand).getItem() == ItemDefs.workbenchUpgrade) {
				((TileEntityMagiciansWorkbench) te).setUpgradeStatus(TileEntityMagiciansWorkbench.UPG_CRAFT, true);

				if (!worldIn.isRemote) {
					playerIn.getHeldItem(hand).shrink(1);

					if (playerIn.getHeldItem(hand).getCount() <= 0) {
						ItemStack stack = playerIn.getHeldItem(hand);
						stack = ItemStack.EMPTY;
					}

					playerIn.setItemStackToSlot(hand == EnumHand.MAIN_HAND ? EntityEquipmentSlot.MAINHAND : EntityEquipmentSlot.OFFHAND, playerIn.getHeldItem(hand));
				}
				return true;
			} else {
				if (!worldIn.isRemote) {
					super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
					FMLNetworkHandler.openGui(playerIn, ArsMagicaReborn.instance, IDDefs.GUI_MAGICIANS_WORKBENCH, worldIn, pos.getX(), pos.getY(), pos.getZ());
				}
			}

		}

		return true;
	}

	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
		TileEntityMagiciansWorkbench receptacle = (TileEntityMagiciansWorkbench) worldIn.getTileEntity(pos);
		if (receptacle == null)
			return;
		for (int i = receptacle.getSizeInventory() - 3; i < receptacle.getSizeInventory(); i++) {
			receptacle.decrStackSize(i, 9001);
			// arbitrary number, just in case rune stack sizes increase in the future
			// yes, it's hard-coded; yes, it's also less computationally intensive than a
			// stack size lookup
		}
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {

		if (world.isRemote) {
			super.breakBlock(world, pos, state);
			return;
		}
		TileEntityMagiciansWorkbench workbench = (TileEntityMagiciansWorkbench) world.getTileEntity(pos);
		if (workbench == null)
			return;

		for (int l = 0; l < workbench.getSizeInventory() - 3; l++) {
			ItemStack itemstack = workbench.getStackInSlot(l);
			if (itemstack == null) {
				continue;
			}
			float f = world.rand.nextFloat() * 0.8F + 0.1F;
			float f1 = world.rand.nextFloat() * 0.8F + 0.1F;
			float f2 = world.rand.nextFloat() * 0.8F + 0.1F;
			do {
				if (itemstack.getCount() <= 0) {
					break;
				}
				int i1 = world.rand.nextInt(21) + 10;
				if (i1 > itemstack.getCount()) {
					i1 = itemstack.getCount();
				}
				itemstack.shrink(i1);
				ItemStack newItem = new ItemStack(itemstack.getItem(), i1, itemstack.getItemDamage());
				newItem.setTagCompound(itemstack.getTagCompound());
				EntityItem entityitem = new EntityItem(world, pos.getX() + f, pos.getY() + f1, pos.getZ() + f2, newItem);
				float f3 = 0.05F;
				entityitem.motionX = (float) world.rand.nextGaussian() * f3;
				entityitem.motionY = (float) world.rand.nextGaussian() * f3 + 0.2F;
				entityitem.motionZ = (float) world.rand.nextGaussian() * f3;
				world.spawnEntity(entityitem);
			} while (true);
		}

		if (workbench.getUpgradeStatus(TileEntityMagiciansWorkbench.UPG_CRAFT)) {
			float f = world.rand.nextFloat() * 0.8F + 0.1F;
			float f1 = world.rand.nextFloat() * 0.8F + 0.1F;
			float f2 = world.rand.nextFloat() * 0.8F + 0.1F;
			ItemStack newItem = new ItemStack(ItemDefs.workbenchUpgrade, 1);
			EntityItem entityitem = new EntityItem(world, pos.getX() + f, pos.getY() + f1, pos.getZ() + f2, newItem);
			float f3 = 0.05F;
			entityitem.motionX = (float) world.rand.nextGaussian() * f3;
			entityitem.motionY = (float) world.rand.nextGaussian() * f3 + 0.2F;
			entityitem.motionZ = (float) world.rand.nextGaussian() * f3;
			world.spawnEntity(entityitem);
		}

		super.breakBlock(world, pos, state);
	}

	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
		IKeystoneLockable<?> lockable = (IKeystoneLockable<?>) world.getTileEntity(pos);
		return super.removedByPlayer(state, world, pos, player, willHarvest);
	}

}
