package amreborn.network.packets;

import amreborn.teleporter.TeleportationManager;
import amreborn.teleporter.Teleporter;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketTeleporterName implements IMessage {

    private int    id;
    private String name;

    public PacketTeleporterName() {}

    public PacketTeleporterName(int teleporterId, String newName) {
        this.id = teleporterId;
        this.name = newName;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.id = buf.readInt();
        this.name = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.id);
        ByteBufUtils.writeUTF8String(buf, this.name);
    }

    public static class Handler implements IMessageHandler<PacketTeleporterName, IMessage> {

        @Override
        public IMessage onMessage(PacketTeleporterName message, MessageContext ctx) {
            Teleporter teleporter = TeleportationManager.getTeleporterForId(message.id);
            if (teleporter == null)
                return null;

            EntityPlayerMP player = ctx.getServerHandler().playerEntity;

            /**
             * Checks distance between the player and the teleporter he's
             * teleporting from
             */
            if (teleporter.getPos().distanceSq(player.getPosition().getX() + 0.5D, player.getPosition().getY() + 0.5D, player.getPosition().getZ() + 0.5D) > 64D)
                return null;

            /**
             * Checks if the player own the teleporter
             */
            if (teleporter.getOwner() == null || !teleporter.getOwner().equals(player.getUniqueID()))
                return null;

            if (message.name.length() <= 15 && message.name.length() > 2)
                teleporter.setName(message.name);

            TeleportationManager.Save.save();
            return null;
        }

    }
}
