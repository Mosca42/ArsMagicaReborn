package amreborn;

import java.io.File;

import amreborn.api.ArsMagicaAPI;
import amreborn.api.affinity.Affinity;
import amreborn.commands.CommandArsMagica;
import amreborn.config.AMConfig;
import amreborn.config.SpellPartConfiguration;
import amreborn.extensions.DataDefinitions;
import amreborn.packet.MessageBoolean;
import amreborn.packet.MessageCapabilities;
import amreborn.proxy.CommonProxy;
import amreborn.teleporter.TeleportationManager;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;


@Mod(modid=ArsMagicaReborn.MODID, version=ArsMagicaReborn.VERSION, guiFactory=ArsMagicaReborn.GUIFACTORY, canBeDeactivated=false)
public class ArsMagicaReborn {
	
	public static final String MODID = "arsmagicareborn";
	public static final String VERSION = "GRADLE:VERSION" + "GRADLE:BUILD";
	public static final String GUIFACTORY = "am2.config.AMGuiFactory";
	public static SimpleNetworkWrapper network;
	
	@SidedProxy(clientSide="amreborn.proxy.ClientProxy", serverSide="amreborn.proxy.CommonProxy", modId=MODID)
	public static CommonProxy proxy;
	
	@Instance(MODID)
	public static ArsMagicaReborn instance;
	public static AMConfig config;
	public static SpellPartConfiguration disabledSkills;
	private File configDir;
	
	static {
		new DataDefinitions();
		new ArsMagicaAPI();
		Affinity.registerAffinities();
		if (!FluidRegistry.isUniversalBucketEnabled())
			FluidRegistry.enableUniversalBucket();
		ForgeModContainer.replaceVanillaBucketModel = true;
	}
	
	@EventHandler
	public void preInit (FMLPreInitializationEvent e) {
		configDir = new File(e.getModConfigurationDirectory(), "ArsMagicaReborn");
		config = new AMConfig(new File(configDir, "amreborn.cfg"));
		//config = new AMConfig(new File(e.getModConfigurationDirectory() + "\\ArsMagicaReborn\\am2.cfg"));
		disabledSkills = new SpellPartConfiguration(new File(configDir, "skills.cfg"));
		proxy.preInit();
		network = NetworkRegistry.INSTANCE.newSimpleChannel("AM2");
		network.registerMessage(MessageBoolean.IceBridgeHandler.class, MessageBoolean.class, 1, Side.SERVER);
		network.registerMessage(MessageCapabilities.class, MessageCapabilities.class, 3, Side.SERVER);
	}
	
	@EventHandler
	public void init (FMLInitializationEvent e) {
		proxy.init();
	}
	
	@EventHandler
	public void postInit (FMLPostInitializationEvent e) {
		proxy.postInit();
	}
	
	@EventHandler
	public void serverLoad(FMLServerStartingEvent e) {
		e.registerServerCommand(new CommandArsMagica());
        TeleportationManager.Save.load(e);
	}
	
	public String getVersion() {
		return VERSION;
	}
	
}
