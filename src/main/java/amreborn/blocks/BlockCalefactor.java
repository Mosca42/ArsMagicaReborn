package amreborn.blocks;

import amreborn.ArsMagicaReborn;
import amreborn.api.blocks.IKeystoneLockable;
import amreborn.blocks.tileentity.TileEntityCalefactor;
import amreborn.defs.IDDefs;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;

public class BlockCalefactor extends BlockAMPowered {

	public static final PropertyDirection FACING = PropertyDirection.create("facing");

	public BlockCalefactor() {
		super(Material.ROCK);
		this.setHardness(3.0f);
		this.setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).ordinal();
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(FACING, EnumFacing.values()[meta]);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int i) {
		return new TileEntityCalefactor();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

		if (HandleSpecialItems(worldIn, playerIn, pos)) {
			return true;
		}
		if (!worldIn.isRemote) {
			super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
			FMLNetworkHandler.openGui(playerIn, ArsMagicaReborn.instance, IDDefs.GUI_CALEFACTOR, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		if (world.isRemote) {
			super.breakBlock(world, pos, state);
			return;
		}
		TileEntityCalefactor calefactor = (TileEntityCalefactor) world.getTileEntity(pos);
		if (calefactor == null)
			return;
		for (int l = 0; l < calefactor.getSizeInventory() - 3; l++) {
			ItemStack itemstack = calefactor.getStackInSlot(l);
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
		super.breakBlock(world, pos, state);
	}

	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
		IKeystoneLockable<?> lockable = (IKeystoneLockable<?>) world.getTileEntity(pos);
		return super.removedByPlayer(state, world, pos, player, willHarvest);
	}
}