package amreborn.network.packets;

import amreborn.defs.ItemDefs;
import amreborn.teleporter.TeleportationManager;
import amreborn.teleporter.Teleporter;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketTeleportation implements IMessage {

    private int from;
    private int to;

    public PacketTeleportation(int from, int to) {
        this.from = from;
        this.to = to;
    }

    public PacketTeleportation() {}

    @Override
    public void fromBytes(ByteBuf buf) {
        this.from = buf.readInt();
        this.to = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.from);
        buf.writeInt(this.to);
    }

    public static class Handler implements IMessageHandler<PacketTeleportation, IMessage> {

        @Override
        public IMessage onMessage(PacketTeleportation message, MessageContext ctx) {
            Teleporter teleporter = TeleportationManager.getTeleporterForId(message.from);
            if (teleporter == null)
                return null;

            EntityPlayerMP player = ctx.getServerHandler().playerEntity;

            /**
             * Checks distance between the player and the teleporter he's
             * teleporting from
             */
            if (teleporter.getPos().distanceSq(player.getPosition().getX() + 0.5D, player.getPosition().getY() + 0.5D, player.getPosition().getZ() + 0.5D) > 64D)
                return null;

            Teleporter t = TeleportationManager.getTeleporterForId(message.to);
            if (t == null)
                return null;

            /**
             * If the targeted teleporter is private and the player is not the
             * owner
             */
            if (t.isPrivatized() && !hasKeyWithId(player, t.getId()))
                return null;

            /**
             * Dimension check
             */
            if (t.getDimension() != teleporter.getDimension() || player.getEntityWorld().provider.getDimension() != t.getDimension())
                return null;

            player.setPositionAndUpdate(t.getPos().getX() + 0.5D, t.getPos().up().getY(), t.getPos().getZ() + 0.5D);
            return null;
        }

    }

    private static boolean hasKeyWithId(EntityPlayer player, int id) {
        for(ItemStack stack : player.inventory.mainInventory) {
            if (!stack.isEmpty() && stack.getItem() == ItemDefs.teleporterKey) {
                NBTTagCompound nbt = stack.getTagCompound();
                NBTTagList list = nbt.getTagList("teleporters", NBT.TAG_COMPOUND);
                for(int i = 0; i < list.tagCount(); i++) {
                    if (list.getCompoundTagAt(i).getInteger("id") == id)
                        return true;
                }
            }
        }
        return false;
    }

}