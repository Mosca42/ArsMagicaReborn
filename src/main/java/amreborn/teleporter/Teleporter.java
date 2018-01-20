package amreborn.teleporter;

import java.util.UUID;

import com.google.common.base.Objects;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.INBTSerializable;

/**
 * @author BrokenSwing
 *
 */
public class Teleporter implements INBTSerializable<NBTTagCompound> {

    private BlockPos pos;
    private int      dimension;
    private String   name;
    private boolean  privatized = false;
    private int      id         = -1;
    private UUID     owner      = null;

    public Teleporter(NBTTagCompound nbt) {
        this.deserializeNBT(nbt);
    }

    public Teleporter(BlockPos pos, int dimension, int id) {
        this.pos = pos;
        this.dimension = dimension;
        this.name = "";
        this.id = id;
    }

    public UUID getOwner() {
        return this.owner;
    }

    public void setOwner(UUID uuid) {
        this.owner = uuid;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPrivatized() {
        return privatized;
    }

    public void setPrivatized(boolean privatized) {
        this.privatized = privatized;
    }

    public BlockPos getPos() {
        return pos;
    }

    public int getDimension() {
        return dimension;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.dimension, this.pos);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Teleporter other = (Teleporter) obj;
        if (dimension != other.dimension)
            return false;
        if (pos == null) {
            if (other.pos != null)
                return false;
        } else if (!pos.equals(other.pos))
            return false;
        return true;
    }

    public boolean strictlyEquals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Teleporter))
            return false;
        Teleporter other = (Teleporter) obj;
        if (dimension != other.dimension)
            return false;
        if (id != other.id)
            return false;
        if (owner == null) {
            if (other.owner != null)
                return false;
        } else if (!owner.equals(other.owner))
            return false;
        if (pos == null) {
            if (other.pos != null)
                return false;
        } else if (!pos.equals(other.pos))
            return false;
        if (privatized != other.privatized)
            return false;
        return true;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setTag("pos", NBTUtil.createPosTag(this.pos));
        nbt.setInteger("dim", this.dimension);
        nbt.setInteger("id", this.id);
        nbt.setBoolean("private", this.privatized);
        nbt.setString("name", this.name);
        if (this.owner != null)
            nbt.setTag("owner", NBTUtil.createUUIDTag(this.owner));
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        this.pos = NBTUtil.getPosFromTag(nbt.getCompoundTag("pos"));
        this.dimension = nbt.getInteger("dim");
        this.id = nbt.getInteger("id");
        this.privatized = nbt.getBoolean("private");
        this.name = nbt.getString("name");
        if (nbt.hasKey("owner"))
            this.owner = NBTUtil.getUUIDFromTag(nbt.getCompoundTag("owner"));
    }

}
