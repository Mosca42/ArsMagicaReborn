package amreborn.blocks;

import amreborn.ArsMagicaReborn;
import amreborn.blocks.tileentity.TileEntityTeleporter;
import amreborn.defs.CreativeTabsDefs;
import amreborn.defs.ItemDefs;
import amreborn.items.ItemBlockSubtypes;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTeleporterAutel extends Block implements ITileEntityProvider {

	public BlockTeleporterAutel() {
		super(Material.ROCK);
		setCreativeTab(CreativeTabsDefs.tabAM2Blocks);
		setHardness(5);
		setResistance(10);
		setHarvestLevel("pickaxe", 2);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityTeleporter();
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileEntityTeleporter) {
			((TileEntityTeleporter) tile).setOwner(placer.getUniqueID());
		}
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileEntityTeleporter) {
			((TileEntityTeleporter) tile).deleteTeleporter();
		}
		world.removeTileEntity(pos);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileEntityTeleporter) {
			TileEntityTeleporter t = (TileEntityTeleporter) tile;
			if (t.isValid()) {
				if (!player.getHeldItemMainhand().isEmpty() && player.getHeldItemMainhand().getItem() == ItemDefs.teleporterKey) {
					if (world.isRemote && player.getName().equals(t.getOwnerName()))
						return true;
					if (!world.isRemote && player.getUniqueID().equals(t.getOwner())) {
						ItemStack stack = player.getHeldItemMainhand();
						NBTTagCompound nbt = stack.getTagCompound();
						NBTTagList list = nbt.getTagList("teleporters", NBT.TAG_COMPOUND);
						for (int i = 0; i < list.tagCount(); i++) {
							if (list.getCompoundTagAt(i).getInteger("id") == t.getId()) {
								player.sendMessage(new TextComponentTranslation("teleporter.already.linked"));
								return true;
							}
						}
						t.getTeleporter().setPrivatized(true);
						list.appendTag(t.getTeleporter().serializeNBT());
						nbt.setTag("teleporters", list);
						player.sendMessage(new TextComponentTranslation("teleporter.linked"));
						return true;
					}
				}
				if (world.isRemote) {
					player.openGui(ArsMagicaReborn.instance, 24, world, pos.getX(), pos.getY(), pos.getZ());
				}
				return true;
			}
		}
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	public Block registerAndName(ResourceLocation rl) {
		this.setUnlocalizedName(rl.toString());
		GameRegistry.register(this, rl);
		GameRegistry.register(new ItemBlockSubtypes(this), rl);
		return this;
	}

}
