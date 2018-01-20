package amreborn.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import amreborn.ArsMagicaReborn;
import amreborn.blocks.tileentity.TileEntityCelestialPrism;
import amreborn.blocks.tileentity.TileEntityObelisk;
import amreborn.defs.BlockDefs;
import amreborn.defs.IDDefs;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;

public class BlockEssenceGenerator extends BlockAMPowered {

	private int NexusType;

	public static final int NEXUS_STANDARD = 0;
	public static final int NEXUS_DARK = 1;
	public static final int NEXUS_LIGHT = 2;

	public static final PropertyEnum<EnumFacing> FACING = PropertyEnum.create("facing", EnumFacing.class, EnumFacing.HORIZONTALS);

	public BlockEssenceGenerator(int nexusType) {
		super(Material.CLOTH);
		setLightLevel(0.73f);
		setTickRandomly(true);
		setHardness(2f);
		setResistance(2f);
		this.NexusType = nexusType;
		switch (this.NexusType) {
		case NEXUS_STANDARD:
			setBlockBounds(0f, 0.0f, 0f, 1f, 2f, 1f);
			break;
		case NEXUS_LIGHT:
			setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 2f, 1.0f);
			break;
		case NEXUS_DARK:
			setBlockBounds(0.0f, 0.5f, 0.0f, 1.0f, 2f, 1.0f);
			break;
		}
	}

	private TileEntityObelisk getTileEntity(IBlockAccess blockAccess, BlockPos pos) {
		TileEntity te = blockAccess.getTileEntity(pos);
		if (te != null && te instanceof TileEntityObelisk) {
			return (TileEntityObelisk) te;
		}
		return null;
	}

	@Override
	public boolean canProvidePower(IBlockState state) {
		return true;
	}

	@Override
	public int quantityDropped(Random random) {
		return 1;
	}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
		if (this == BlockDefs.obelisk)
			drops.add(new ItemStack(BlockDefs.obelisk));
		else if (this == BlockDefs.celestialPrism)
			drops.add(new ItemStack(BlockDefs.celestialPrism));
		return drops;
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean p_185477_7_) {
		super.addCollisionBoxToList(state, worldIn, pos, entityBox, collidingBoxes, entityIn, p_185477_7_);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return super.getCollisionBoundingBox(blockState, worldIn, pos);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (HandleSpecialItems(worldIn, playerIn, pos))
			return true;
		if (worldIn.getBlockState(pos).getBlock() == BlockDefs.obelisk)
			FMLNetworkHandler.openGui(playerIn, ArsMagicaReborn.instance, IDDefs.GUI_OBELISK, worldIn, pos.getX(), pos.getY(), pos.getZ());
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
	}

	@Override
	public TileEntity createNewTileEntity(World par1World, int i) {
		if (this.NexusType == NEXUS_LIGHT)
			return new TileEntityCelestialPrism();
		else
			return new TileEntityObelisk();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).ordinal() - 2;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(FACING, EnumFacing.HORIZONTALS[meta]);
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {

		if (worldIn.isRemote) {
			super.breakBlock(worldIn, pos, state);
			return;
		}
		TileEntityObelisk obelisk = getTileEntity(worldIn, pos);
		if (obelisk == null)
			return;
		for (int l = 0; l < obelisk.getSizeInventory(); l++) {
			ItemStack itemstack = obelisk.getStackInSlot(l);
			if (itemstack == null) {
				continue;
			}
			float f = worldIn.rand.nextFloat() * 0.8F + 0.1F;
			float f1 = worldIn.rand.nextFloat() * 0.8F + 0.1F;
			float f2 = worldIn.rand.nextFloat() * 0.8F + 0.1F;
			do {
				if (itemstack.getCount() <= 0) {
					break;
				}
				int i1 = worldIn.rand.nextInt(21) + 10;
				if (i1 > itemstack.getCount()) {
					i1 = itemstack.getCount();
				}
				itemstack.shrink(i1);
				ItemStack newItem = new ItemStack(itemstack.getItem(), i1, itemstack.getItemDamage());
				newItem.setTagCompound(itemstack.getTagCompound());
				EntityItem entityitem = new EntityItem(worldIn, pos.getX() + f, pos.getY() + f1, pos.getZ() + f2, newItem);
				float f3 = 0.05F;
				entityitem.motionX = (float) worldIn.rand.nextGaussian() * f3;
				entityitem.motionY = (float) worldIn.rand.nextGaussian() * f3 + 0.2F;
				entityitem.motionZ = (float) worldIn.rand.nextGaussian() * f3;
				worldIn.spawnEntity(entityitem);
			} while (true);
		}
		super.breakBlock(worldIn, pos, state);
	}

}
