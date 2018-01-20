package amreborn.blocks.tileentity;

import amreborn.blocks.BlockVinteumCauldron;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockMagma;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileEntityVinteumCauldron extends TileEntity implements ITickable {

	private int tick = 0;
	private boolean hasDust = false;

	@Override
	public void update() {
		if (!world.isRemote) {
			if (((BlockVinteumCauldron) world.getBlockState(pos).getBlock()).getWaterLevel(world, pos) == 3 || ((BlockVinteumCauldron) world.getBlockState(pos).getBlock()).getWaterLevel(world, pos) == 6) {
				if (hasDust) {
					if (world.getBlockState(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ())).getBlock() instanceof BlockLiquid) {
						BlockLiquid liquid = (BlockLiquid) world.getBlockState(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ())).getBlock();
						if (liquid.getMaterial(world.getBlockState(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ()))) == Material.LAVA) {
							if (world.getWorldTime() % 20 == 0) {
								if (tick < 5) {
									tick++;
								} else {

									if (world.getBlockState(pos).getBlock() instanceof BlockVinteumCauldron) {
										if (((BlockVinteumCauldron) world.getBlockState(pos).getBlock()).getWaterLevel(world, pos) == 3) {
											((BlockVinteumCauldron) world.getBlockState(pos).getBlock()).setWaterLevel(world, pos, world.getBlockState(pos), 6);
										} else if (((BlockVinteumCauldron) world.getBlockState(pos).getBlock()).getWaterLevel(world, pos) == 2) {
											((BlockVinteumCauldron) world.getBlockState(pos).getBlock()).setWaterLevel(world, pos, world.getBlockState(pos), 5);
										} else if (((BlockVinteumCauldron) world.getBlockState(pos).getBlock()).getWaterLevel(world, pos) == 1) {
											((BlockVinteumCauldron) world.getBlockState(pos).getBlock()).setWaterLevel(world, pos, world.getBlockState(pos), 4);
										}
									}
								}
							}
						}
					} else if (world.getBlockState(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ())).getBlock() instanceof BlockMagma || world.getBlockState(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ())).getBlock() instanceof BlockFire) {
						if (world.getWorldTime() % 20 == 0) {
							if (tick < 5) {
								tick++;
							} else {
								if (world.getBlockState(pos).getBlock() instanceof BlockVinteumCauldron) {
									if (((BlockVinteumCauldron) world.getBlockState(pos).getBlock()).getWaterLevel(world, pos) == 3) {
										((BlockVinteumCauldron) world.getBlockState(pos).getBlock()).setWaterLevel(world, pos, world.getBlockState(pos), 6);
									} else if (((BlockVinteumCauldron) world.getBlockState(pos).getBlock()).getWaterLevel(world, pos) == 2) {
										((BlockVinteumCauldron) world.getBlockState(pos).getBlock()).setWaterLevel(world, pos, world.getBlockState(pos), 5);
									} else if (((BlockVinteumCauldron) world.getBlockState(pos).getBlock()).getWaterLevel(world, pos) == 1) {
										((BlockVinteumCauldron) world.getBlockState(pos).getBlock()).setWaterLevel(world, pos, world.getBlockState(pos), 4);
									}
								}
							}
						}
					}
				}
			}
		}
	}

	public int getTick() {
		return tick;
	}

	public void setTick(int tick) {
		this.tick = tick;
	}

	public boolean isHasDust() {
		return hasDust;
	}

	public void setHasDust(boolean hasDust) {
		this.hasDust = hasDust;
	}

}
