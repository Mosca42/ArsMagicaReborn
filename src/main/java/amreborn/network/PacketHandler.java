package amreborn.network;

import amreborn.ArsMagicaReborn;
import amreborn.network.packets.MessageTEUpdate;
import amreborn.network.packets.PacketTeleportation;
import amreborn.network.packets.PacketTeleporterName;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
    public static SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(ArsMagicaReborn.MODID);

    private static int id = 0;

    public static void registerMessages(){
        INSTANCE.registerMessage(MessageTEUpdate.MessageHolder.class, MessageTEUpdate.class, id++, Side.CLIENT);
        INSTANCE.registerMessage(PacketTeleportation.Handler.class, PacketTeleportation.class, id++, Side.SERVER);
        INSTANCE.registerMessage(PacketTeleporterName.Handler.class, PacketTeleporterName.class, id++, Side.SERVER);
    }
}
