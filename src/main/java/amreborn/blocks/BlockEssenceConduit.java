package amreborn.blocks;

import amreborn.blocks.tileentity.TileEntityEssenceConduit;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockEssenceConduit extends BlockAMPowered{
	
	public static final PropertyDirection FACING = PropertyDirection.create("facing");

	public BlockEssenceConduit(){
		super(Material.CLOTH);
		setHardness(3.0f);
		setBlockBounds(0.125f, 0.0f, 0.125f, 0.875f, 1.0f, 0.875f);
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.UP));
	}
	

	@Override
	public TileEntity createNewTileEntity(World par1World, int i){
		return new TileEntityEssenceConduit();
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
}
