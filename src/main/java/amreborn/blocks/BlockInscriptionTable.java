package amreborn.blocks;

import amreborn.ArsMagicaReborn;
import amreborn.blocks.tileentity.TileEntityInscriptionTable;
import amreborn.defs.IDDefs;
import amreborn.defs.ItemDefs;
import amreborn.items.ItemInscriptionTable;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockInscriptionTable extends BlockAMSpecialRenderContainer {

	public static final PropertyEnum<EnumFacing> FACING = PropertyEnum.create("facing", EnumFacing.class, EnumFacing.HORIZONTALS);

	public BlockInscriptionTable() {
		super(Material.WOOD);
		setHardness(2.0f);
		setResistance(2.0f);
		setLightLevel(0.4f);
		this.setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING);
	}

	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		this.setDefaultFacing(worldIn, pos, state);
	}

	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state) {
		if (!worldIn.isRemote) {
			IBlockState iblockstate = worldIn.getBlockState(pos.north());
			IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
			IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
			IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
			EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);

			if (enumfacing == EnumFacing.NORTH && iblockstate.isFullBlock() && !iblockstate1.isFullBlock()) {
				enumfacing = EnumFacing.SOUTH;
			} else if (enumfacing == EnumFacing.SOUTH && iblockstate1.isFullBlock() && !iblockstate.isFullBlock()) {
				enumfacing = EnumFacing.NORTH;
			} else if (enumfacing == EnumFacing.WEST && iblockstate2.isFullBlock() && !iblockstate3.isFullBlock()) {
				enumfacing = EnumFacing.EAST;
			} else if (enumfacing == EnumFacing.EAST && iblockstate3.isFullBlock() && !iblockstate2.isFullBlock()) {
				enumfacing = EnumFacing.WEST;
			}

			worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
		}
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).ordinal() - 2;
	}

	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(FACING, EnumFacing.values()[meta + 2]);
	}

	@Override
	public int getLightValue(IBlockState state) {
		return 12;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
		if (worldIn.isRemote) {
			return true;
		}

		TileEntityInscriptionTable te = (TileEntityInscriptionTable) worldIn.getTileEntity(pos);
		TileEntityInscriptionTable tealt = te;

		if (te == null)
			return true;

		if (te.isInUse(playerIn)) {
			playerIn.sendMessage(new TextComponentString("Someone else is using this."));
			return true;
		}

		ItemStack curItem = playerIn.getHeldItem(hand);
		if (curItem != ItemStack.EMPTY && curItem.getItem() == ItemDefs.inscriptionUpgrade) {
			if (te.getUpgradeState() == curItem.getItemDamage()) {
				playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, ItemStack.EMPTY);
				te.incrementUpgradeState();
				tealt.incrementUpgradeState();
				return true;
			}
		}

		FMLNetworkHandler.openGui(playerIn, ArsMagicaReborn.instance, IDDefs.GUI_INSCRIPTION_TABLE, worldIn, pos.getX(), pos.getY(), pos.getZ());

		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World par1World, int i) {
		return new TileEntityInscriptionTable();
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		TileEntityInscriptionTable insc = (TileEntityInscriptionTable) world.getTileEntity(pos);

		if (insc == null)
			return;

		if (!world.isRemote) {
			for (int l = 0; l < insc.getSizeInventory(); l++) {
				ItemStack itemstack = insc.getStackInSlot(l);
				if (itemstack == ItemStack.EMPTY) {
					continue;
				}
				spawnItemOnBreak(world, pos, itemstack);
			}

			int stat = insc.getUpgradeState();
			for (int m = 0; m < stat; ++m)
				spawnItemOnBreak(world, pos, new ItemStack(ItemDefs.inscriptionUpgrade, 1, m));
		}

		super.breakBlock(world, pos, state);
	}

	private void spawnItemOnBreak(World world, BlockPos pos, ItemStack itemstack) {
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

	@Override
	public BlockAMContainer registerAndName(ResourceLocation rl) {
		this.setUnlocalizedName(rl.toString());
		GameRegistry.register(this, rl);
		GameRegistry.register(new ItemInscriptionTable(this), rl);
		return this;
	}
}
