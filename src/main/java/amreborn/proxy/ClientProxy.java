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
import static amreborn.defs.IDDefs.GUI_MAGICIANS_WORKBENCH;
import static amreborn.defs.IDDefs.GUI_OBELISK;
import static amreborn.defs.IDDefs.GUI_OCCULUS;
import static amreborn.defs.IDDefs.GUI_RIFT;
import static amreborn.defs.IDDefs.GUI_RUNE_BAG;
import static amreborn.defs.IDDefs.GUI_SPELL_BOOK;
import static amreborn.defs.IDDefs.GUI_SPELL_CUSTOMIZATION;
import static amreborn.defs.IDDefs.GUI_SUMMONER;
import static amreborn.defs.IDDefs.GUI_TELEPORTER;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import amreborn.ArsMagicaReborn;
import amreborn.api.math.AMVector3;
import amreborn.api.power.IPowerNode;
import amreborn.api.spell.SpellComponent;
import amreborn.armor.ArmorHelper;
import amreborn.armor.infusions.GenericImbuement;
import amreborn.blocks.render.TileArcaneReconstructorRenderer;
import amreborn.blocks.render.TileAstralBarrierRenderer;
import amreborn.blocks.render.TileCalefactorRenderer;
import amreborn.blocks.render.TileCelestialPrismRenderer;
import amreborn.blocks.render.TileCraftingAltarRenderer;
import amreborn.blocks.render.TileEssenceConduitRenderer;
import amreborn.blocks.render.TileEverstoneRenderer;
import amreborn.blocks.render.TileIllusionBlockRenderer;
import amreborn.blocks.render.TileLecternRenderer;
import amreborn.blocks.render.TileMagiciansWorkbenchRenderer;
import amreborn.blocks.render.TileObeliskRenderer;
import amreborn.blocks.render.TileOtherworldAuraRenderer;
import amreborn.blocks.render.TileRuneRenderer;
import amreborn.blocks.render.TileSummonerRenderer;
import amreborn.blocks.tileentity.TileEntityArcaneDeconstructor;
import amreborn.blocks.tileentity.TileEntityArcaneReconstructor;
import amreborn.blocks.tileentity.TileEntityArmorImbuer;
import amreborn.blocks.tileentity.TileEntityAstralBarrier;
import amreborn.blocks.tileentity.TileEntityCalefactor;
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
import amreborn.blocks.tileentity.TileEntityObelisk;
import amreborn.blocks.tileentity.TileEntityOtherworldAura;
import amreborn.blocks.tileentity.TileEntitySummoner;
import amreborn.blocks.tileentity.TileEntityTeleporter;
import amreborn.commands.ConfigureAMUICommand;
import amreborn.defs.AMSounds;
import amreborn.defs.BindingsDefs;
import amreborn.defs.BlockDefs;
import amreborn.defs.EntityManager;
import amreborn.defs.ItemDefs;
import amreborn.extensions.RiftStorage;
import amreborn.gui.AMGuiHelper;
import amreborn.gui.AMIngameGUI;
import amreborn.gui.GuiArcaneDeconstructor;
import amreborn.gui.GuiArcaneReconstructor;
import amreborn.gui.GuiArmorImbuer;
import amreborn.gui.GuiAstralBarrier;
import amreborn.gui.GuiCalefactor;
import amreborn.gui.GuiEssenceBag;
import amreborn.gui.GuiEssenceRefiner;
import amreborn.gui.GuiInertSpawner;
import amreborn.gui.GuiInscriptionTable;
import amreborn.gui.GuiMagiciansWorkbench;
import amreborn.gui.GuiObelisk;
import amreborn.gui.GuiOcculus;
import amreborn.gui.GuiRiftStorage;
import amreborn.gui.GuiRuneBag;
import amreborn.gui.GuiSpellBook;
import amreborn.gui.GuiSpellCustomization;
import amreborn.gui.GuiSummoner;
import amreborn.gui.GuiTeleporter;
import amreborn.handler.BakingHandler;
import amreborn.items.ItemEssenceBag;
import amreborn.items.ItemRuneBag;
import amreborn.items.ItemSpellBase;
import amreborn.items.ItemSpellBook;
import amreborn.models.ArsMagicaModelLoader;
import amreborn.models.CullfaceModelLoader;
import amreborn.models.SpecialRenderModelLoader;
import amreborn.packet.AMNetHandler;
import amreborn.packet.AMPacketProcessorClient;
import amreborn.particles.AMParticleIcons;
import amreborn.particles.ParticleManagerClient;
import amreborn.power.PowerNodeEntry;
import amreborn.power.PowerTypes;
import amreborn.proxy.gui.ItemRenderer;
import amreborn.proxy.tick.ClientTickHandler;
import amreborn.spell.component.Telekinesis;
import amreborn.texture.SpellIconManager;
import amreborn.utils.RenderUtils;
import amreborn.utils.SpellUtils;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {

	public ClientTickHandler clientTickHandler;

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
		switch (ID) {
		case GUI_OCCULUS:
			return new GuiOcculus(player);
		case GUI_SPELL_CUSTOMIZATION:
			return new GuiSpellCustomization(player);
		case GUI_RIFT:
			return new GuiRiftStorage(player, RiftStorage.For(player));
		case GUI_SPELL_BOOK:
			ItemStack bookStack = player.getHeldItemMainhand();
			if (bookStack.getItem() == null || !(bookStack.getItem() instanceof ItemSpellBook)) {
				return null;
			}
			ItemSpellBook item = (ItemSpellBook) bookStack.getItem();
			return new GuiSpellBook(player.inventory, bookStack, item.ConvertToInventory(bookStack));
		case GUI_OBELISK:
			return new GuiObelisk((TileEntityObelisk) world.getTileEntity(new BlockPos(x, y, z)), player);
		case GUI_INSCRIPTION_TABLE:
			return new GuiInscriptionTable(player.inventory, (TileEntityInscriptionTable) world.getTileEntity(new BlockPos(x, y, z)));
		case GUI_ARMOR_INFUSION:
			return new GuiArmorImbuer(player, (TileEntityArmorImbuer) world.getTileEntity(new BlockPos(x, y, z)));
		case GUI_RUNE_BAG:
			ItemStack bagStack = player.getHeldItemMainhand();
			if (bagStack.getItem() == null || !(bagStack.getItem() instanceof ItemRuneBag)) {
				return null;
			}
			ItemRuneBag runebag = (ItemRuneBag) bagStack.getItem();
			return new GuiRuneBag(player.inventory, player.getHeldItemMainhand(), runebag.ConvertToInventory(bagStack));
		case GUI_ARCANE_DECONSTRUCTOR:
			return new GuiArcaneDeconstructor(player.inventory, (TileEntityArcaneDeconstructor) te);
		case GUI_ARCANE_RECONSTRUCTOR:
			return new GuiArcaneReconstructor(player.inventory, (TileEntityArcaneReconstructor) te);
		case GUI_ASTRAL_BARRIER:
			return new GuiAstralBarrier(player.inventory, (TileEntityAstralBarrier) te);
		case GUI_ESSENCE_REFINER:
			return new GuiEssenceRefiner(player.inventory, (TileEntityEssenceRefiner) te);
		case GUI_MAGICIANS_WORKBENCH:
			return new GuiMagiciansWorkbench(player.inventory, (TileEntityMagiciansWorkbench) te);
		case GUI_CALEFACTOR:
			return new GuiCalefactor(player, (TileEntityCalefactor) te);
		case GUI_INERT_SPAWNER:
			return new GuiInertSpawner(player, (TileEntityInertSpawner) te);
		case GUI_SUMMONER:
			return new GuiSummoner(player.inventory, (TileEntitySummoner) te);
		case GUI_ESSENCE_BAG:
			bagStack = player.getHeldItemMainhand();
			if (bagStack.getItem() == null || !(bagStack.getItem() instanceof ItemEssenceBag)) {
				return null;
			}
			ItemEssenceBag essenceBag = (ItemEssenceBag) bagStack.getItem();
			return new GuiEssenceBag(player.inventory, player.getHeldItemMainhand(), essenceBag.ConvertToInventory(bagStack));
		case GUI_TELEPORTER:
			return new GuiTeleporter((TileEntityTeleporter) te);
		}
		return super.getClientGuiElement(ID, player, world, x, y, z);
	}

	@Override
	public void preInit() {
		super.preInit();

		OBJLoader.INSTANCE.addDomain(ArsMagicaReborn.MODID);

		AMParticleIcons.instance.toString();
		SpellIconManager.INSTANCE.toString();

		ClientRegistry.registerKeyBinding(BindingsDefs.ICE_BRIDGE);
		ClientRegistry.registerKeyBinding(BindingsDefs.ENDER_TP);
		ClientRegistry.registerKeyBinding(BindingsDefs.AURA_CUSTOMIZATION);
		ClientRegistry.registerKeyBinding(BindingsDefs.SHAPE_GROUP);
		ClientRegistry.registerKeyBinding(BindingsDefs.NIGHT_VISION);
		ClientRegistry.registerKeyBinding(BindingsDefs.SPELL_BOOK_NEXT);
		ClientRegistry.registerKeyBinding(BindingsDefs.SPELL_BOOK_PREV);

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCraftingAltar.class, new TileCraftingAltarRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityObelisk.class, new TileObeliskRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCelestialPrism.class, new TileCelestialPrismRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLectern.class, new TileLecternRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEssenceConduit.class, new TileEssenceConduitRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGroundRuneSpell.class, new TileRuneRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEverstone.class, new TileEverstoneRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityArcaneReconstructor.class, new TileArcaneReconstructorRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityIllusionBlock.class, new TileIllusionBlockRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMagiciansWorkbench.class, new TileMagiciansWorkbenchRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCalefactor.class, new TileCalefactorRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySummoner.class, new TileSummonerRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAstralBarrier.class, new TileAstralBarrierRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityOtherworldAura.class, new TileOtherworldAuraRenderer());

		ModelLoaderRegistry.registerLoader(new ArsMagicaModelLoader());
		ModelLoaderRegistry.registerLoader(new CullfaceModelLoader());
		ModelLoaderRegistry.registerLoader(new SpecialRenderModelLoader());

		MinecraftForge.EVENT_BUS.register(new ArsMagicaModelLoader());
		MinecraftForge.EVENT_BUS.register(clientTickHandler);
		MinecraftForge.EVENT_BUS.register(ItemRenderer.instance);
		MinecraftForge.EVENT_BUS.register(new BakingHandler());
		MinecraftForge.EVENT_BUS.register(new BindingsDefs());
		MinecraftForge.EVENT_BUS.register(new AMIngameGUI());

		ArsMagicaReborn.config.clientInit();
		new AMSounds();
		EntityManager.instance.registerRenderers();
		blocks.preInitClient();

		ClientCommandHandler.instance.registerCommand(new ConfigureAMUICommand());
	}

	@Override
	public void initHandlers() {
		particleManager = new ParticleManagerClient();
		packetProcessor = new AMPacketProcessorClient();
		clientTickHandler = new ClientTickHandler();
	}

	@Override
	public void init() {
		super.init();
		BlockDefs.initClient();
		ItemDefs.initClient();
	}

	@Override
	public void setTrackedLocation(AMVector3 location) {
		clientTickHandler.setTrackLocation(location.toVec3D());
	}

	@Override
	public void setTrackedPowerCompound(NBTTagCompound compound) {
		clientTickHandler.setTrackData(compound);
	}

	@Override
	public boolean hasTrackedLocationSynced() {
		return clientTickHandler.getHasSynced();
	}

	@Override
	public PowerNodeEntry getTrackedData() {
		return clientTickHandler.getTrackData();
	}

	@Override
	public boolean setMouseDWheel(int dwheel) {
		if (dwheel == 0)
			return false;

		ItemStack stack = Minecraft.getMinecraft().player.getHeldItemMainhand();
		if (stack == null)
			return false;

		boolean store = checkForTKMove(stack);
		if (!store && stack.getItem() instanceof ItemSpellBook) {
			store = Minecraft.getMinecraft().player.isSneaking();
		}

		if (store) {
			clientTickHandler.setDWheel(dwheel / 120, Minecraft.getMinecraft().player.inventory.currentItem, Minecraft.getMinecraft().player.isHandActive());
			return true;
		} else {
			clientTickHandler.setDWheel(0, -1, false);
		}
		return false;
	}

	private boolean checkForTKMove(ItemStack stack) {
		if (stack.getItem() instanceof ItemSpellBook) {
			ItemStack activeStack = ((ItemSpellBook) stack.getItem()).GetActiveItemStack(stack);
			if (activeStack != null)
				stack = activeStack;
		}
		if (stack.getItem() instanceof ItemSpellBase && stack.hasTagCompound() && Minecraft.getMinecraft().player.isHandActive()) {
			for (SpellComponent component : SpellUtils.getComponentsForStage(stack, -1)) {
				if (component instanceof Telekinesis) {
					return true;
				}
			}
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void drawPowerOnBlockHighlight(EntityPlayer player, RayTraceResult target, float partialTicks) {

		if (Minecraft.getMinecraft().player.getItemStackFromSlot(EntityEquipmentSlot.HEAD) != null && (Minecraft.getMinecraft().player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() == ItemDefs.magitechGoggles) || ArmorHelper.isInfusionPreset(Minecraft.getMinecraft().player.getItemStackFromSlot(EntityEquipmentSlot.HEAD), GenericImbuement.magitechGoggleIntegration)) {
			if (target.getBlockPos() == null)
				return;
			TileEntity te = player.world.getTileEntity(target.getBlockPos());
			if (te != null && te instanceof IPowerNode) {
				ArsMagicaReborn.proxy.setTrackedLocation(new AMVector3(target.getBlockPos()));
			} else {
				ArsMagicaReborn.proxy.setTrackedLocation(AMVector3.zero());
			}

			if (ArsMagicaReborn.proxy.hasTrackedLocationSynced()) {
				PowerNodeEntry data = ArsMagicaReborn.proxy.getTrackedData();
				Block block = player.world.getBlockState(target.getBlockPos()).getBlock();
				float yOff = 0.5f;
				if (data != null) {
					GlStateManager.pushAttrib();
					for (PowerTypes type : ((IPowerNode<?>) te).getValidPowerTypes()) {
						float pwr = data.getPower(type);
						float pct = pwr / ((IPowerNode<?>) te).getCapacity() * 100;
						AMVector3 offset = new AMVector3(target.getBlockPos().getX() + 0.5, target.getBlockPos().getX() + 0.5, target.getBlockPos().getZ() + 0.5).sub(new AMVector3((player.prevPosX - (player.prevPosX - player.posX) * partialTicks), (player.prevPosY - (player.prevPosY - player.posY) * partialTicks) + player.getEyeHeight(), (player.prevPosZ - (player.prevPosZ - player.posZ) * partialTicks)));
						offset = offset.normalize();
						if (target.getBlockPos().getY() <= player.posY + player.getEyeHeight()) {
							RenderUtils.drawTextInWorldAtOffset(String.format("%s%.2f (%.2f%%)", type.getChatColor(), pwr, pct), target.getBlockPos().getX() - (player.prevPosX - (player.prevPosX - player.posX) * partialTicks) + 0.5f - offset.x, target.getBlockPos().getY() + yOff - (player.prevPosY - (player.prevPosY - player.posY) * partialTicks) + block.getBoundingBox(player.world.getBlockState(target.getBlockPos()), player.world, target.getBlockPos()).maxY * 0.8f, target.getBlockPos().getZ() - (player.prevPosZ - (player.prevPosZ - player.posZ) * partialTicks) + 0.5f - offset.z, 0xFFFFFF);
							yOff += 0.12f;
						} else {
							RenderUtils.drawTextInWorldAtOffset(String.format("%s%.2f (%.2f%%)", type.getChatColor(), pwr, pct), target.getBlockPos().getX() - (player.prevPosX - (player.prevPosX - player.posX) * partialTicks) + 0.5f - offset.x, target.getBlockPos().getY() - yOff - (player.prevPosY - (player.prevPosY - player.posY) * partialTicks) - block.getBoundingBox(player.world.getBlockState(target.getBlockPos()), player.world, target.getBlockPos()).maxY * 0.2f, target.getBlockPos().getZ() - (player.prevPosZ - (player.prevPosZ - player.posZ) * partialTicks) + 0.5f - offset.z, 0xFFFFFF);
							yOff -= 0.12f;
						}
					}
					GlStateManager.disableAlpha();
					GlStateManager.popAttrib();
				}
			}
		}
	}

	@Override
	public void requestPowerPathVisuals(IPowerNode<?> node, EntityPlayerMP player) {
		AMNetHandler.INSTANCE.syncPowerPaths(node, player);
	}

	@Override
	public void flashManaBar() {
		AMGuiHelper.instance.flashManaBar();
	}

	@Override
	public void receivePowerPathVisuals(HashMap<PowerTypes, ArrayList<LinkedList<Vec3d>>> paths) {
		powerPathVisuals = paths;
	}

	@Override
	public HashMap<PowerTypes, ArrayList<LinkedList<Vec3d>>> getPowerPathVisuals() {
		return powerPathVisuals;
	}

	@Override
	public EntityPlayer getLocalPlayer() {
		return Minecraft.getMinecraft().player;
	}

}
