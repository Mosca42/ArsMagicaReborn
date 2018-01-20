package amreborn.proxy;

import static amreborn.defs.IDDefs.GUI_ARCANE_DECONSTRUCTOR;
import static amreborn.defs.IDDefs.GUI_ARCANE_RECONSTRUCTOR;
import static amreborn.defs.IDDefs.GUI_ARMOR_INFUSION;
import static amreborn.defs.IDDefs.GUI_ASTRAL_BARRIER;
import static amreborn.defs.IDDefs.GUI_CALEFACTOR;
import static amreborn.defs.IDDefs.GUI_ESSENCE_BAG;
import static amreborn.defs.IDDefs.GUI_ESSENCE_REFINER;
import static amreborn.defs.IDDefs.GUI_INERT_SPAWNER;
import static amreborn.defs.IDDefs.GUI_INSCRIPTION_TABLE;
import static amreborn.defs.IDDefs.GUI_KEYSTONE_LOCKABLE;
import static amreborn.defs.IDDefs.GUI_MAGICIANS_WORKBENCH;
import static amreborn.defs.IDDefs.GUI_OBELISK;
import static amreborn.defs.IDDefs.GUI_OCCULUS;
import static amreborn.defs.IDDefs.GUI_RIFT;
import static amreborn.defs.IDDefs.GUI_RUNE_BAG;
import static amreborn.defs.IDDefs.GUI_SPELL_BOOK;
import static amreborn.defs.IDDefs.GUI_SPELL_CUSTOMIZATION;
import static amreborn.defs.IDDefs.GUI_SUMMONER;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import com.google.common.collect.ImmutableMap;

import amreborn.AMChunkLoader;
import amreborn.ArsMagicaReborn;
import amreborn.affinity.AffinityAbilityHelper;
import amreborn.api.ArsMagicaAPI;
import amreborn.api.blocks.IKeystoneLockable;
import amreborn.api.extensions.IAffinityData;
import amreborn.api.extensions.IArcaneCompendium;
import amreborn.api.extensions.IDataSyncExtension;
import amreborn.api.extensions.IEntityExtension;
import amreborn.api.extensions.IRiftStorage;
import amreborn.api.extensions.ISkillData;
import amreborn.api.math.AMVector3;
import amreborn.api.power.IPowerNode;
import amreborn.api.spell.AbstractSpellPart;
import amreborn.armor.ArmorEventHandler;
import amreborn.armor.infusions.DamageReductionImbuement;
import amreborn.armor.infusions.Dispelling;
import amreborn.armor.infusions.FallProtection;
import amreborn.armor.infusions.FireProtection;
import amreborn.armor.infusions.Freedom;
import amreborn.armor.infusions.GenericImbuement;
import amreborn.armor.infusions.Healing;
import amreborn.armor.infusions.HungerBoost;
import amreborn.armor.infusions.ImbuementRegistry;
import amreborn.armor.infusions.JumpBoost;
import amreborn.armor.infusions.LifeSaving;
import amreborn.armor.infusions.Lightstep;
import amreborn.armor.infusions.MiningSpeed;
import amreborn.armor.infusions.Recoil;
import amreborn.armor.infusions.SwimSpeed;
import amreborn.armor.infusions.WaterBreathing;
import amreborn.armor.infusions.WaterWalking;
import amreborn.blocks.BlockArsMagicaBlock.EnumBlockType;
import amreborn.blocks.BlockArsMagicaOre.EnumOreType;
import amreborn.blocks.tileentity.TileEntityArcaneDeconstructor;
import amreborn.blocks.tileentity.TileEntityArcaneReconstructor;
import amreborn.blocks.tileentity.TileEntityArmorImbuer;
import amreborn.blocks.tileentity.TileEntityAstralBarrier;
import amreborn.blocks.tileentity.TileEntityCalefactor;
import amreborn.blocks.tileentity.TileEntityCandle;
import amreborn.blocks.tileentity.TileEntityCelestialPrism;
import amreborn.blocks.tileentity.TileEntityCraftingAltar;
import amreborn.blocks.tileentity.TileEntityEssenceConduit;
import amreborn.blocks.tileentity.TileEntityEssenceRefiner;
import amreborn.blocks.tileentity.TileEntityEverstone;
import amreborn.blocks.tileentity.TileEntityGroundRuneSpell;
import amreborn.blocks.tileentity.TileEntityIllusionBlock;
import amreborn.blocks.tileentity.TileEntityInertSpawner;
import amreborn.blocks.tileentity.TileEntityInscriptionTable;
import amreborn.blocks.tileentity.TileEntityLectern;
import amreborn.blocks.tileentity.TileEntityMagiciansWorkbench;
import amreborn.blocks.tileentity.TileEntityManaBattery;
import amreborn.blocks.tileentity.TileEntityObelisk;
import amreborn.blocks.tileentity.TileEntityOcculus;
import amreborn.blocks.tileentity.TileEntityOtherworldAura;
import amreborn.blocks.tileentity.TileEntitySlipstreamGenerator;
import amreborn.blocks.tileentity.TileEntitySummoner;
import amreborn.blocks.tileentity.TileEntityTeleporter;
import amreborn.blocks.tileentity.TileEntityVinteumCauldron;
import amreborn.container.ContainerArcaneDeconstructor;
import amreborn.container.ContainerArcaneReconstructor;
import amreborn.container.ContainerArmorInfuser;
import amreborn.container.ContainerAstralBarrier;
import amreborn.container.ContainerCalefactor;
import amreborn.container.ContainerEssenceBag;
import amreborn.container.ContainerEssenceRefiner;
import amreborn.container.ContainerInertSpawner;
import amreborn.container.ContainerInscriptionTable;
import amreborn.container.ContainerKeystoneLockable;
import amreborn.container.ContainerMagiciansWorkbench;
import amreborn.container.ContainerObelisk;
import amreborn.container.ContainerRiftStorage;
import amreborn.container.ContainerRuneBag;
import amreborn.container.ContainerSpellBook;
import amreborn.container.ContainerSpellCustomization;
import amreborn.container.ContainerSummoner;
import amreborn.defs.AMRecipes;
import amreborn.defs.BlockDefs;
import amreborn.defs.CreativeTabsDefs;
import amreborn.defs.EntityManager;
import amreborn.defs.ItemDefs;
import amreborn.defs.LootTablesArsMagica;
import amreborn.defs.LoreDefs;
import amreborn.defs.PotionEffectsDefs;
import amreborn.defs.SkillDefs;
import amreborn.defs.SpellDefs;
import amreborn.enchantments.AMEnchantments;
import amreborn.extensions.RiftStorage;
import amreborn.handler.EntityHandler;
import amreborn.handler.EventManager;
import amreborn.handler.FlickerEvents;
import amreborn.handler.FuelHandler;
import amreborn.handler.PotionEffectHandler;
import amreborn.handler.ShrinkHandler;
import amreborn.items.ItemEssenceBag;
import amreborn.items.ItemOre;
import amreborn.items.ItemRuneBag;
import amreborn.items.ItemSpellBook;
import amreborn.lore.CompendiumUnlockHandler;
import amreborn.network.PacketHandler;
import amreborn.network.SeventhSanctum;
import amreborn.packet.AMNetHandler;
import amreborn.packet.AMPacketProcessorServer;
import amreborn.particles.ParticleManagerServer;
import amreborn.power.PowerNodeCache;
import amreborn.power.PowerNodeEntry;
import amreborn.power.PowerTypes;
import amreborn.proxy.tick.ServerTickHandler;
import amreborn.spell.SpellUnlockManager;
import amreborn.trackers.ItemFrameWatcher;
import amreborn.trackers.PlayerTracker;
import amreborn.utils.NPCSpells;
import amreborn.world.AM2WorldDecorator;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;


public class CommonProxy implements IGuiHandler{

	public ParticleManagerServer particleManager;
	protected ServerTickHandler serverTickHandler;
	protected AMPacketProcessorServer packetProcessor;
	public PacketHandler packetHandler;
	private HashMap<EntityLivingBase, ArrayList<PotionEffect>> deferredPotionEffects = new HashMap<>();
	private HashMap<EntityLivingBase, Integer> deferredDimensionTransfers = new HashMap<>();
	public ItemDefs items;
	public BlockDefs blocks;
	public AMEnchantments enchantments;
	private int totalFlickerCount = 0;
	
	public PlayerTracker playerTracker;
	public ItemFrameWatcher itemFrameWatcher;
	private AM2WorldDecorator worldGen;
	public NBTTagCompound cwCopyLoc = null;
	
	public static HashMap<PowerTypes, ArrayList<LinkedList<Vec3d>>> powerPathVisuals;
	
	public CommonProxy() {
		playerTracker = new PlayerTracker();
		itemFrameWatcher = new ItemFrameWatcher();
	}
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
		switch (ID) {
		case GUI_OCCULUS: return null;
		case GUI_SPELL_CUSTOMIZATION: return new ContainerSpellCustomization(player);
		case GUI_RIFT: return new ContainerRiftStorage(player, RiftStorage.For(player));
		case GUI_SPELL_BOOK: 
			ItemStack bookStack = player.getHeldItemMainhand();
			if (bookStack.getItem() == null || !(bookStack.getItem() instanceof ItemSpellBook)){
				return null;
			}
			ItemSpellBook item = (ItemSpellBook)bookStack.getItem();
			return new ContainerSpellBook(player.inventory, bookStack, item.ConvertToInventory(bookStack));
		case GUI_OBELISK: return new ContainerObelisk((TileEntityObelisk)world.getTileEntity(new BlockPos(x, y, z)), player);
		case GUI_INSCRIPTION_TABLE: return new ContainerInscriptionTable((TileEntityInscriptionTable)world.getTileEntity(new BlockPos(x, y, z)), player.inventory);
		case GUI_ARMOR_INFUSION: return new ContainerArmorInfuser(player, (TileEntityArmorImbuer) world.getTileEntity(new BlockPos(x, y, z)));
		case GUI_KEYSTONE_LOCKABLE: return new ContainerKeystoneLockable(player.inventory, (IKeystoneLockable<?>)te);		
		case GUI_RUNE_BAG: 
			ItemStack bagStack = player.getHeldItemMainhand();
			if (bagStack.getItem() == null || !(bagStack.getItem() instanceof ItemRuneBag)){
				return null;
			}
			ItemRuneBag runebag = (ItemRuneBag)bagStack.getItem();
			return new ContainerRuneBag(player.inventory, player.getHeldItemMainhand(), runebag.ConvertToInventory(bagStack));
		case GUI_ARCANE_DECONSTRUCTOR: return new ContainerArcaneDeconstructor(player.inventory, (TileEntityArcaneDeconstructor) te);
		case GUI_ARCANE_RECONSTRUCTOR: return new ContainerArcaneReconstructor(player.inventory, (TileEntityArcaneReconstructor) te);
		case GUI_ASTRAL_BARRIER: return new ContainerAstralBarrier(player.inventory, (TileEntityAstralBarrier) te);
		case GUI_ESSENCE_REFINER: return new ContainerEssenceRefiner(player.inventory, (TileEntityEssenceRefiner) te);
		case GUI_MAGICIANS_WORKBENCH: return new ContainerMagiciansWorkbench(player.inventory, (TileEntityMagiciansWorkbench) te);
		case GUI_CALEFACTOR: return new ContainerCalefactor(player, (TileEntityCalefactor) te);
		case GUI_INERT_SPAWNER: return new ContainerInertSpawner(player, (TileEntityInertSpawner) te);
		case GUI_SUMMONER: return new ContainerSummoner(player.inventory, (TileEntitySummoner) te);
		case GUI_ESSENCE_BAG: 
			bagStack = player.getHeldItemMainhand();
			if (bagStack.getItem() == null || !(bagStack.getItem() instanceof ItemEssenceBag)){
				return null;
			}
			ItemEssenceBag essenceBag = (ItemEssenceBag)bagStack.getItem();
			return new ContainerEssenceBag(player.inventory, player.getHeldItemMainhand(), essenceBag.ConvertToInventory(bagStack));
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	public void preInit() {
		
		ForgeChunkManager.setForcedChunkLoadingCallback(ArsMagicaReborn.instance, AMChunkLoader.INSTANCE);
		NetworkRegistry.INSTANCE.registerGuiHandler(ArsMagicaReborn.instance, this);
		SeventhSanctum.instance.init();
		
		initHandlers();
		ArsMagicaReborn.config.init();
		serverTickHandler = new ServerTickHandler();
		packetHandler = new PacketHandler();
		packetHandler.registerMessages();
		enchantments = new AMEnchantments();
		AMNetHandler.INSTANCE.init();
		AMNetHandler.INSTANCE.registerChannels(packetProcessor);
		
		MinecraftForge.EVENT_BUS.register(serverTickHandler);
		MinecraftForge.EVENT_BUS.register(new CompendiumUnlockHandler());
		MinecraftForge.EVENT_BUS.register(new EntityHandler());
		MinecraftForge.EVENT_BUS.register(new PotionEffectHandler());
		MinecraftForge.EVENT_BUS.register(new AffinityAbilityHelper());
		MinecraftForge.EVENT_BUS.register(packetProcessor);
		MinecraftForge.EVENT_BUS.register(PowerNodeCache.instance);
		MinecraftForge.EVENT_BUS.register(new ArmorEventHandler());
		MinecraftForge.EVENT_BUS.register(playerTracker);
		MinecraftForge.EVENT_BUS.register(new FlickerEvents());
		MinecraftForge.EVENT_BUS.register(new ShrinkHandler());
		MinecraftForge.EVENT_BUS.register(new EventManager());
		
		registerInfusions();
		
		GameRegistry.registerTileEntity(TileEntityOcculus.class, "TileEntityOcculus");
		GameRegistry.registerTileEntity(TileEntityCraftingAltar.class, "TileEntityCraftingAltar");
		GameRegistry.registerTileEntity(TileEntityLectern.class, "TileEntityLectern");
		GameRegistry.registerTileEntity(TileEntityObelisk.class, "TileEntityObelisk");
		GameRegistry.registerTileEntity(TileEntityCelestialPrism.class, "TileEntityCelestialPrism");
		GameRegistry.registerTileEntity(TileEntityCandle.class, "TileEntityCandle");
		GameRegistry.registerTileEntity(TileEntityInscriptionTable.class, "TileEntityInscriptionTable");
		GameRegistry.registerTileEntity(TileEntityManaBattery.class, "TileEntityManaBattery");
		GameRegistry.registerTileEntity(TileEntityArmorImbuer.class, "TileEntityArmorImbuer");
		GameRegistry.registerTileEntity(TileEntitySlipstreamGenerator.class, "TileEntitySlipstramGenerator");
		GameRegistry.registerTileEntity(TileEntityArcaneDeconstructor.class, "TileEntityArcaneDeconstructor");
		GameRegistry.registerTileEntity(TileEntityArcaneReconstructor.class, "TileEntityArcaneReconstructor");
		GameRegistry.registerTileEntity(TileEntityAstralBarrier.class, "TileEntityAstralBarrier");
		GameRegistry.registerTileEntity(TileEntityEssenceRefiner.class, "TileEntityEssenceRefiner");
		GameRegistry.registerTileEntity(TileEntityEverstone.class, "TileEntityEverstone");
		GameRegistry.registerTileEntity(TileEntityGroundRuneSpell.class, "TileEntityGroundRuneSpell");
		GameRegistry.registerTileEntity(TileEntityEssenceConduit.class, "TileEntityEssenceConduit");
		GameRegistry.registerTileEntity(TileEntityIllusionBlock.class, "TileEntityIllusionBlock");
		GameRegistry.registerTileEntity(TileEntityCalefactor.class, "TileEntityCalefactor");
		GameRegistry.registerTileEntity(TileEntityInertSpawner.class, "TileEntityInertSpawner");
		GameRegistry.registerTileEntity(TileEntityMagiciansWorkbench.class, "TileEntityMagiciansWorkbench");
		GameRegistry.registerTileEntity(TileEntityOtherworldAura.class, "TileEntityOtherworldAura");
		GameRegistry.registerTileEntity(TileEntitySummoner.class, "TileEntitySummoner");
		GameRegistry.registerTileEntity(TileEntityVinteumCauldron.class, "TileEntityVinteumCauldron");
		GameRegistry.registerTileEntity(TileEntityTeleporter.class, "TileEntityTeleporter");

		worldGen = new AM2WorldDecorator();
		GameRegistry.registerWorldGenerator(worldGen, 0);
		GameRegistry.registerFuelHandler(new FuelHandler());
		EntityManager.instance.registerEntities();
		EntityManager.instance.initializeSpawns();
		AMEnchantments.Init();
		SkillDefs.init();
		SpellDefs.init();
		PotionEffectsDefs.init();
		NPCSpells.instance.toString();
		items = new ItemDefs();
		ItemDefs.initEnchantedItems();
		blocks = new BlockDefs();
		blocks.preInit();
		new CreativeTabsDefs();
		initOreDict();
		new LootTablesArsMagica();

		CapabilityManager.INSTANCE.register(IEntityExtension.class, new IEntityExtension.Storage(), new IEntityExtension.Factory());
		CapabilityManager.INSTANCE.register(IAffinityData.class, new IAffinityData.Storage(), new IAffinityData.Factory());
		CapabilityManager.INSTANCE.register(ISkillData.class, new ISkillData.Storage(), new ISkillData.Factory());
		CapabilityManager.INSTANCE.register(IRiftStorage.class, new IRiftStorage.Storage(), new IRiftStorage.Factory());
		CapabilityManager.INSTANCE.register(IArcaneCompendium.class, new IArcaneCompendium.Storage(), new IArcaneCompendium.Factory());
		CapabilityManager.INSTANCE.register(IDataSyncExtension.class, new IDataSyncExtension.Storage(), new IDataSyncExtension.Factory());
	}
	
	public void init() {

	}
	
	public void postInit() {
		playerTracker.postInit();
		MinecraftForge.EVENT_BUS.register(playerTracker);
		MinecraftForge.EVENT_BUS.register(new SpellUnlockManager());
		LoreDefs.postInit();	
		AMRecipes.addRecipes();
		for (AbstractSpellPart part : ArsMagicaAPI.getSpellRegistry().getValues()) {
			if (ArsMagicaAPI.getSkillRegistry().getValue(part.getRegistryName()) == null)
				throw new IllegalStateException("Spell Part " + part.getRegistryName() + " is missing a skill, this would cause severe problems");
		}
		ArsMagicaReborn.disabledSkills.getDisabledSkills(true);
	}
	
	public void initHandlers() {
		particleManager = new ParticleManagerServer();
		packetProcessor = new AMPacketProcessorServer();
	}
	
	public void addDeferredTargetSet(EntityLiving ent, EntityLivingBase target){
		serverTickHandler.addDeferredTarget(ent, target);
	}
	
	public ImmutableMap<EntityLivingBase, ArrayList<PotionEffect>> getDeferredPotionEffects(){
		return ImmutableMap.copyOf(deferredPotionEffects);
	}
	
	public void clearDeferredPotionEffects(){
		deferredPotionEffects.clear();
	}
	
	public void clearDeferredDimensionTransfers(){
		deferredDimensionTransfers.clear();
	}

	public ImmutableMap<EntityLivingBase, Integer> getDeferredDimensionTransfers(){
		return ImmutableMap.copyOf(deferredDimensionTransfers);
	}

	public void renderGameOverlay() {}
	
	public void addDeferredDimensionTransfer(EntityLivingBase ent, int dimension){
		deferredDimensionTransfers.put(ent, dimension);
	}

	public boolean setMouseDWheel(int dwheel) {
		return false;
	}
	
	public void setTrackedPowerCompound(NBTTagCompound compound){
	}

	public void setTrackedLocation(AMVector3 location){
	}

	public boolean hasTrackedLocationSynced(){
		return false;
	}

	public PowerNodeEntry getTrackedData(){
		return null;
	}

	public void drawPowerOnBlockHighlight(EntityPlayer player, RayTraceResult target, float partialTicks) {}

	public void receivePowerPathVisuals(HashMap<PowerTypes, ArrayList<LinkedList<Vec3d>>> nodePaths) {}

	public void requestPowerPathVisuals(IPowerNode<?> node, EntityPlayerMP player) {}

	public HashMap<PowerTypes, ArrayList<LinkedList<Vec3d>>> getPowerPathVisuals() {
		return null;
	}

	public void blackoutArmorPiece(EntityPlayerMP player, EntityEquipmentSlot slot, int cooldown){
		serverTickHandler.blackoutArmorPiece(player, slot, cooldown);
	}
	
	public void registerInfusions(){
		DamageReductionImbuement.registerAll();
		GenericImbuement.registerAll();
		ImbuementRegistry.instance.registerImbuement(new Dispelling());
		ImbuementRegistry.instance.registerImbuement(new FallProtection());
		ImbuementRegistry.instance.registerImbuement(new FireProtection());
		ImbuementRegistry.instance.registerImbuement(new Freedom());
		ImbuementRegistry.instance.registerImbuement(new Healing());
		ImbuementRegistry.instance.registerImbuement(new HungerBoost());
		ImbuementRegistry.instance.registerImbuement(new JumpBoost());
		ImbuementRegistry.instance.registerImbuement(new LifeSaving());
		ImbuementRegistry.instance.registerImbuement(new Lightstep());
		ImbuementRegistry.instance.registerImbuement(new MiningSpeed());
		ImbuementRegistry.instance.registerImbuement(new Recoil());
		ImbuementRegistry.instance.registerImbuement(new SwimSpeed());
		ImbuementRegistry.instance.registerImbuement(new WaterBreathing());
		ImbuementRegistry.instance.registerImbuement(new WaterWalking());
	}

	public void flashManaBar() {}
	
	public void incrementFlickerCount(){
		this.totalFlickerCount++;
	}

	public void decrementFlickerCount(){
		this.totalFlickerCount--;
		if (this.totalFlickerCount < 0)
			this.totalFlickerCount = 0;
	}

	public int getTotalFlickerCount(){
		return this.totalFlickerCount;
	}
	
	public AM2WorldDecorator getWorldGenerator() {
		return worldGen;
	}

	public EntityPlayer getLocalPlayer() {
		return null;
	}
	
	private void initOreDict() {
		OreDictionary.registerOre("fenceWood", Blocks.ACACIA_FENCE);
		OreDictionary.registerOre("fenceWood", Blocks.OAK_FENCE);
		OreDictionary.registerOre("fenceWood", Blocks.DARK_OAK_FENCE);
		OreDictionary.registerOre("fenceWood", Blocks.SPRUCE_FENCE);
		OreDictionary.registerOre("fenceWood", Blocks.BIRCH_FENCE);
		OreDictionary.registerOre("fenceWood", Blocks.JUNGLE_FENCE);
		OreDictionary.registerOre("oreBlueTopaz", new ItemStack(BlockDefs.ores, 1, EnumOreType.BLUETOPAZ.ordinal()));
		OreDictionary.registerOre("oreVinteum", new ItemStack(BlockDefs.ores, 1, EnumOreType.VINTEUM.ordinal()));
		OreDictionary.registerOre("oreChimerite", new ItemStack(BlockDefs.ores, 1, EnumOreType.CHIMERITE.ordinal()));
		OreDictionary.registerOre("oreMoonstone", new ItemStack(BlockDefs.ores, 1, EnumOreType.MOONSTONE.ordinal()));
		OreDictionary.registerOre("oreSunstone", new ItemStack(BlockDefs.ores, 1, EnumOreType.SUNSTONE.ordinal()));

		OreDictionary.registerOre("blockBlueTopaz", new ItemStack(BlockDefs.blocks, 1, EnumBlockType.BLUETOPAZ.ordinal()));
		OreDictionary.registerOre("blockVinteum", new ItemStack(BlockDefs.blocks, 1, EnumBlockType.VINTEUM.ordinal()));
		OreDictionary.registerOre("blockChimerite", new ItemStack(BlockDefs.blocks, 1, EnumBlockType.CHIMERITE.ordinal()));
		OreDictionary.registerOre("blockMoonstone", new ItemStack(BlockDefs.blocks, 1, EnumBlockType.MOONSTONE.ordinal()));
		OreDictionary.registerOre("blockSunstone", new ItemStack(BlockDefs.blocks, 1, EnumBlockType.SUNSTONE.ordinal()));

		OreDictionary.registerOre("chestWood", new ItemStack(Blocks.CHEST));
		OreDictionary.registerOre("craftingTableWood", new ItemStack(Blocks.CRAFTING_TABLE));
		
		OreDictionary.registerOre("dustVinteum", new ItemStack(ItemDefs.itemOre, 1, ItemOre.META_VINTEUM));
		OreDictionary.registerOre("arcaneAsh", new ItemStack(ItemDefs.itemOre, 1, ItemOre.META_ARCANEASH));
		OreDictionary.registerOre("gemBlueTopaz", new ItemStack(ItemDefs.itemOre, 1, ItemOre.META_BLUE_TOPAZ));
		OreDictionary.registerOre("gemChimerite", new ItemStack(ItemDefs.itemOre, 1, ItemOre.META_CHIMERITE));
		OreDictionary.registerOre("gemMoonstone", new ItemStack(ItemDefs.itemOre, 1, ItemOre.META_MOONSTONE));
		OreDictionary.registerOre("gemSunstone", new ItemStack(ItemDefs.itemOre, 1, ItemOre.META_SUNSTONE));

	}
}
