package amreborn.blocks.tileentity;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import amreborn.teleporter.Structure;
import amreborn.teleporter.TeleportationManager;
import amreborn.teleporter.Teleporter;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class TileEntityTeleporter extends TileEntity implements ITickable {

    private Structure   structure;
    private boolean     valid            = false;
    private Teleporter  teleporter       = null;
    private Set<String> teleportersNames = new HashSet<String>();
    private String      name             = "";
    private UUID        owner;
    private String      ownerName        = "Un-owned";
    private boolean     isPrivatized     = false;
    private int         id               = -1;

    public boolean isValid() {
        return valid;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
        this.markDirty();
    }

    public UUID getOwner() {
        return this.owner;
    }
    
    public Teleporter getTeleporter() {
        return this.teleporter;
    }

    public String getOwnerName() {
        return this.ownerName;
    }

    public String getTeleporterName() {
        return this.name;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        this.name = compound.getString("name");
        if (compound.hasKey("ownerMost"))
            this.owner = compound.getUniqueId("owner");
        else
            this.owner = null;
        this.isPrivatized = compound.getBoolean("private");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setString("name", this.name);
        compound.setBoolean("private", this.isPrivatized);
        if (this.owner != null)
            compound.setUniqueId("owner", this.owner);
        return compound;
    }

    @Override
    public void onLoad() {
        switch (world.provider.getDimension()) {
            default:
                this.structure = new Structure().add(0, -1, 0, Blocks.DIAMOND_BLOCK).add(0, 1, 0, Blocks.AIR).add(0, 2, 0, Blocks.AIR);
        }
    }

    public Set<String> getTeleportersNames() {
        return Collections.unmodifiableSet(this.teleportersNames);
    }

    @Override
    public void update() {
        if (this.name.isEmpty()) {
            this.name = String.format("%d/%d/%d", this.pos.getX(), this.pos.getY(), this.pos.getZ());
        }
        if (!this.world.isRemote) {
            boolean isValid = structure.isStructureValid(this.world, this.pos);
            if (isValid && !valid) {
                this.valid = true;
                this.markDirty();
            } else if (!isValid) {
                this.valid = false;
                this.markDirty();
            }

            if (valid && teleporter == null) {
                teleporter = TeleportationManager.getOrCreateTeleporterAt(this.pos, this.world.provider.getDimension());
                teleporter.setName(this.name);
                teleporter.setOwner(this.owner);
                this.id = teleporter.getId();
                this.markDirty();
            } else if (!valid && teleporter != null) {
                TeleportationManager.removeTeleporter(this.teleporter);
                this.teleporter = null;
                this.markDirty();
            } else if (teleporter != null) {
                String lastName = this.name;
                boolean lastPrivatzation = this.isPrivatized;
                this.name = this.teleporter.getName();
                this.isPrivatized = this.teleporter.isPrivatized();
                if (!this.name.equals(lastName) || lastPrivatzation != this.isPrivatized) {
                    this.markDirty();
                }
            }

            if (valid) {
                Set<String> names = new HashSet<String>();
                for(Teleporter teleporter : TeleportationManager.getPublicTeleporters(TeleportationManager.getTeleportersForDim(this.world.provider.getDimension()))) {
                    names.add(String.format("%s#%d", teleporter.getName(), teleporter.getId()));
                }
                if (!names.equals(this.teleportersNames)) {
                    this.teleportersNames = names;
                    this.markDirty();
                    this.world.notifyBlockUpdate(this.pos, this.world.getBlockState(this.pos), this.world.getBlockState(this.pos), 3);
                }
            }
            this.world.notifyBlockUpdate(this.pos, this.world.getBlockState(this.pos), this.world.getBlockState(this.pos), 3);
        }
    }

    public void deleteTeleporter() {
        if (this.teleporter != null) {
            TeleportationManager.removeTeleporter(this.teleporter);
            this.markDirty();
        }
    }

    public int getId() {
        return this.id;
    }
    
    public boolean isPrivatized() {
        return this.isPrivatized;
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setBoolean("valid", this.valid);
        nbt.setString("teleporterName", this.name);

        if (valid) {
            NBTTagList teleporters = new NBTTagList();
            for(String name : this.teleportersNames) {
                teleporters.appendTag(new NBTTagString(name));
            }
            nbt.setTag("teleporters", teleporters);
        }

        if (this.owner != null) {
            nbt.setString("owner", FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerProfileCache().getProfileByUUID(this.owner).getName());
        }

        nbt.setInteger("teleporterId", this.id);
        nbt.setBoolean("private", this.isPrivatized);

        return new SPacketUpdateTileEntity(this.pos, 0, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        this.valid = pkt.getNbtCompound().getBoolean("valid");
        this.isPrivatized = pkt.getNbtCompound().getBoolean("private");
        this.id = pkt.getNbtCompound().getInteger("teleporterId");
        this.name = pkt.getNbtCompound().getString("teleporterName");
        if (pkt.getNbtCompound().hasKey("owner"))
            this.ownerName = pkt.getNbtCompound().getString("owner");
        if (this.valid) {
            this.teleportersNames.clear();
            NBTTagList teleporters = pkt.getNbtCompound().getTagList("teleporters", NBT.TAG_STRING);
            for(int i = 0; i < teleporters.tagCount(); i++) {
                this.teleportersNames.add(teleporters.getStringTagAt(i));
            }
        }
    }

    @Override
    public void markDirty() {
        super.markDirty();
        TeleportationManager.Save.save();
    }

}
