package amreborn.teleporter;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class Structure {

    private HashMap<BlockPos, Set<IBlockState>> blocks = new HashMap<BlockPos, Set<IBlockState>>();

    public Structure add(BlockPos pos, IBlockState state) {
        if (!this.blocks.containsKey(pos))
            this.blocks.put(pos, new HashSet<IBlockState>());
        this.blocks.get(pos).add(state);
        return this;
    }

    public Structure add(int x, int y, int z, IBlockState state) {
        return this.add(new BlockPos(x, y, z), state);
    }

    public Structure add(int x, int y, int z, Block block) {
        return this.add(new BlockPos(x, y, z), block);
    }

    public Structure add(BlockPos pos, Block block) {
        for(IBlockState state : block.getBlockState().getValidStates()) {
            this.add(pos, state);
        }
        return this.add(pos, block.getDefaultState());
    }

    public Set<BlockPos> getPositions() {
        return Collections.unmodifiableSet(this.blocks.keySet());
    }

    public boolean isStructureValid(IBlockAccess access, BlockPos center) {
        for(Entry<BlockPos, Set<IBlockState>> entry : this.blocks.entrySet()) {
            IBlockState state = access.getBlockState(center.add(entry.getKey()));
            boolean flag = false;
            for(IBlockState s : entry.getValue()) {
                if (s.toString().equals(state.toString())) {
                    flag = true;
                    break;
                }
            }
            if (!flag)
                return false;
        }
        return true;
    }

}
