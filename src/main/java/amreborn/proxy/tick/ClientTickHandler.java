package amreborn.proxy.tick;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import amreborn.ArsMagicaReborn;
import amreborn.LogHelper;
import amreborn.api.math.AMVector3;
import amreborn.api.power.IPowerNode;
import amreborn.api.spell.SpellComponent;
import amreborn.armor.ArmorHelper;
import amreborn.armor.infusions.GenericImbuement;
import amreborn.bosses.BossSpawnHelper;
import amreborn.defs.ItemDefs;
import amreborn.extensions.EntityExtension;
import amreborn.gui.AMGuiHelper;
import amreborn.items.ItemSpellBase;
import amreborn.items.ItemSpellBook;
import amreborn.packet.AMDataWriter;
import amreborn.packet.AMNetHandler;
import amreborn.packet.AMPacketIDs;
import amreborn.particles.AMLineArc;
import amreborn.power.PowerNodeEntry;
import amreborn.power.PowerTypes;
import amreborn.spell.component.Telekinesis;
import amreborn.trackers.EntityItemWatcher;
import amreborn.utils.DimensionUtilities;
import amreborn.utils.SpellUtils;
import amreborn.world.MeteorSpawnHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientTickHandler{
	public static HashMap<EntityLiving, EntityLivingBase> targetsToSet = new HashMap<EntityLiving, EntityLivingBase>();
	private int mouseWheelValue = 0;
	private int currentSlot = -1;
	private boolean usingItem;

	public static String worldName;

	private boolean firstTick = true;
	private boolean compendiumLoad;

	private ArrayList<AMLineArc> arcs = new ArrayList<AMLineArc>();
	private int arcSpawnCounter = 0;
	private final int arcSpawnFrequency = 95;

	private int powerWatchSyncTick = 0;
	private Vec3d powerWatch = Vec3d.ZERO;
	private boolean hasSynced = false;
	private PowerNodeEntry powerData = null;

	private String lastWorldName;

	private void gameTick_Start(){

		if (Minecraft.getMinecraft().isIntegratedServerRunning()){
			if (worldName == null || !worldName.equals(Minecraft.getMinecraft().getIntegratedServer().getWorldName())){
				worldName = Minecraft.getMinecraft().getIntegratedServer().getWorldName();
				firstTick = true;
			}
		}else{
			if (worldName != null && (lastWorldName == null || lastWorldName != worldName.replace(" ", "_"))){
				lastWorldName = worldName.replace(" ", "_");
				firstTick = true;
			}
		}

		if (firstTick){
//			ItemDefs.crystalPhylactery.getSpawnableEntities(Minecraft.getMinecraft().world);
			compendiumLoad = true;
			firstTick = false;
		}

		if (compendiumLoad){
			compendiumLoad = false;
		}
		ArsMagicaReborn.proxy.itemFrameWatcher.checkWatchedFrames();
	}

	private void applyDeferredPotionEffects(){
		for (EntityLivingBase ent : ArsMagicaReborn.proxy.getDeferredPotionEffects().keySet()){
			ArrayList<PotionEffect> potions = ArsMagicaReborn.proxy.getDeferredPotionEffects().get(ent);
			for (PotionEffect effect : potions)
				ent.addPotionEffect(effect);
		}

		ArsMagicaReborn.proxy.clearDeferredPotionEffects();
	}

	private void applyDeferredDimensionTransfers(){
		for (EntityLivingBase ent : ArsMagicaReborn.proxy.getDeferredDimensionTransfers().keySet()){
			DimensionUtilities.doDimensionTransfer(ent, ArsMagicaReborn.proxy.getDeferredDimensionTransfers().get(ent));
		}

		ArsMagicaReborn.proxy.clearDeferredDimensionTransfers();
	}

	private void gameTick_End(){

		AMGuiHelper.instance.tick();
		EntityItemWatcher.instance.tick();
		checkMouseDWheel();

		if (Minecraft.getMinecraft().isIntegratedServerRunning()){
			MeteorSpawnHelper.instance.tick();
			applyDeferredPotionEffects();
		}

		if (!powerWatch.equals(Vec3d.ZERO)){
			if (powerWatchSyncTick++ == 0){
				AMNetHandler.INSTANCE.sendPowerRequestToServer(powerWatch);
			}
			powerWatchSyncTick %= 20;
		}
	}

	private void spawnPowerPathVisuals(){
		if (Minecraft.getMinecraft().player.getItemStackFromSlot(EntityEquipmentSlot.HEAD) != null &&
				(Minecraft.getMinecraft().player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() == ItemDefs.magitechGoggles ||
						ArmorHelper.isInfusionPreset(Minecraft.getMinecraft().player.getItemStackFromSlot(EntityEquipmentSlot.HEAD), GenericImbuement.magitechGoggleIntegration))
				){

			if (arcSpawnCounter++ >= arcSpawnFrequency){
				arcSpawnCounter = 0;

				AMVector3 playerPos = new AMVector3(Minecraft.getMinecraft().player);

				HashMap<PowerTypes, ArrayList<LinkedList<Vec3d>>> paths = ArsMagicaReborn.proxy.getPowerPathVisuals();
				if (paths != null){
					for (PowerTypes type : paths.keySet()){

						String texture =
								type == PowerTypes.LIGHT ? "textures/blocks/oreblockbluetopaz.png" :
										type == PowerTypes.NEUTRAL ? "textures/blocks/oreblockvinteum.png" :
												type == PowerTypes.DARK ? "textures/blocks/oreblocksunstone.png" :
														"textures/blocks/oreblocksunstone.png";

						ArrayList<LinkedList<Vec3d>> pathList = paths.get(type);
						for (LinkedList<Vec3d> individualPath : pathList){
							for (int i = 0; i < individualPath.size() - 1; ++i){
								Vec3d start = individualPath.get(i + 1);
								Vec3d end = individualPath.get(i);

								if (start.squareDistanceTo(playerPos.toVec3D()) > 2500 || end.squareDistanceTo(playerPos.toVec3D()) > 2500){
									continue;
								}


								TileEntity teStart = Minecraft.getMinecraft().world.getTileEntity(new BlockPos(start));
								TileEntity teEnd = Minecraft.getMinecraft().world.getTileEntity(new BlockPos(end));

								if (teEnd == null || !(teEnd instanceof IPowerNode))
									break;

								double startX = start.xCoord + ((teStart != null && teStart instanceof IPowerNode) ? ((IPowerNode<?>)teStart).particleOffset(0) : 0.5f);
								double startY = start.yCoord + ((teStart != null && teStart instanceof IPowerNode) ? ((IPowerNode<?>)teStart).particleOffset(1) : 0.5f);
								double startZ = start.zCoord + ((teStart != null && teStart instanceof IPowerNode) ? ((IPowerNode<?>)teStart).particleOffset(2) : 0.5f);

								double endX = end.xCoord + ((IPowerNode<?>)teEnd).particleOffset(0);
								double endY = end.yCoord + ((IPowerNode<?>)teEnd).particleOffset(1);
								double endZ = end.zCoord + ((IPowerNode<?>)teEnd).particleOffset(2);

								AMLineArc arc = (AMLineArc)ArsMagicaReborn.proxy.particleManager.spawn(
										Minecraft.getMinecraft().world,
										texture,
										startX,
										startY,
										startZ,
										endX,
										endY,
										endZ);

								if (arc != null){
									arcs.add(arc);
								}
							}
						}
					}
				}
			}
		}else
		{
			Iterator<AMLineArc> it = arcs.iterator();
			while (it.hasNext()){
				AMLineArc arc = it.next();
				if (arc == null || !arc.isAlive())
					it.remove();
				else
					arc.setExpired();
			}
			arcSpawnCounter = arcSpawnFrequency;
		}
	}

	private void checkMouseDWheel(){
		if (this.mouseWheelValue != 0 && this.currentSlot > -1){
			Minecraft.getMinecraft().player.inventory.currentItem = this.currentSlot;

			ItemStack stack = Minecraft.getMinecraft().player.getHeldItem(EnumHand.MAIN_HAND);

			if (checkForTKMove(stack)){
				EntityExtension props = (EntityExtension)EntityExtension.For(Minecraft.getMinecraft().player);
				if (this.mouseWheelValue > 0 && props.getTKDistance() < 10){
					props.addToTKDistance(0.5f);
				}else if (this.mouseWheelValue < 0 && props.getTKDistance() > 0.3){
					props.addToTKDistance(-0.5f);
				}
				LogHelper.debug("TK Distance: %.2f", props.getTKDistance());
				props.syncTKDistance();
			}
				else if (stack.getItem() instanceof ItemSpellBook && Minecraft.getMinecraft().player.isSneaking()){
				ItemSpellBook isb = (ItemSpellBook)stack.getItem();
				if (this.mouseWheelValue != 0){
					byte subID = 0;
					if (this.mouseWheelValue < 0){
						isb.SetNextSlot(Minecraft.getMinecraft().player.getHeldItem(EnumHand.MAIN_HAND));
						subID = ItemSpellBook.ID_NEXT_SPELL;
					}else{
						isb.SetPrevSlot(Minecraft.getMinecraft().player.getHeldItem(EnumHand.MAIN_HAND));
						subID = ItemSpellBook.ID_PREV_SPELL;
					}
					//send packet to server
					AMNetHandler.INSTANCE.sendPacketToServer(
							AMPacketIDs.SPELLBOOK_CHANGE_ACTIVE_SLOT,
							new AMDataWriter()
									.add(subID)
									.add(Minecraft.getMinecraft().player.getEntityId())
									.add(Minecraft.getMinecraft().player.inventory.currentItem)
									.generate()
					);
				}
			}
			this.currentSlot = -1;
			this.mouseWheelValue = 0;
		}
	}

	private boolean checkForTKMove(ItemStack stack){
		if (stack.getItem() instanceof ItemSpellBook){
			ItemStack activeStack = ((ItemSpellBook)stack.getItem()).GetActiveItemStack(stack);
			if (activeStack != null)
				stack = activeStack;
		}
		if (stack.getItem() instanceof ItemSpellBase && stack.hasTagCompound() && this.usingItem){
			for (SpellComponent component : SpellUtils.getComponentsForStage(stack, -1)){
				if (component instanceof Telekinesis){
					return true;
				}
			}
		}
		return false;
	}

	private void renderTick_Start(){
		if (!Minecraft.getMinecraft().inGameHasFocus)
			AMGuiHelper.instance.guiTick();
	}

	private void renderTick_End(){
	}

	private void localServerTick_End(){
		BossSpawnHelper.instance.tick();
	}

	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event){
		if (event.phase == TickEvent.Phase.START){
			GuiScreen guiscreen = Minecraft.getMinecraft().currentScreen;
			if (guiscreen != null){
			}else{
				gameTick_Start();
			}
		}else if (event.phase == TickEvent.Phase.END){
			GuiScreen guiscreen = Minecraft.getMinecraft().currentScreen;
			if (guiscreen != null){
			}else{
				gameTick_End();
			}

			if (Minecraft.getMinecraft().world != null)
				spawnPowerPathVisuals();
		}
	}

	@SubscribeEvent
	public void onRenderTick(TickEvent.RenderTickEvent event){
		if (event.phase == TickEvent.Phase.START){
			renderTick_Start();
		}else if (event.phase == TickEvent.Phase.END){
			renderTick_End();
		}
	}
	
	@SubscribeEvent
	public void onBlockHighlight(DrawBlockHighlightEvent event){
		ArsMagicaReborn.proxy.drawPowerOnBlockHighlight(event.getPlayer(), event.getTarget(), event.getPartialTicks());
	}

	@SubscribeEvent
	public void onWorldTick(TickEvent.WorldTickEvent event){
//		if (Minecraft.getMinecraft().isIntegratedServerRunning()){
//			if (ArsMagicaReborn.config.retroactiveWorldgen())
//				RetroactiveWorldgenerator.instance.continueRetrogen(event.world);
//		}
		if (event.phase == TickEvent.Phase.END){
			applyDeferredDimensionTransfers();
		}
	}

	@SubscribeEvent
	public void onServerTick(TickEvent.ServerTickEvent event){
		if (event.phase == TickEvent.Phase.END){
			localServerTick_End();
		}
	}

	public void setDWheel(int dWheel, int slot, boolean usingItem){
		this.mouseWheelValue = dWheel;
		this.currentSlot = slot;
		this.usingItem = usingItem;
	}

	public Vec3d getTrackLocation(){
		return this.powerWatch;
	}

	public PowerNodeEntry getTrackData(){
		return this.powerData;
	}

	public void setTrackLocation(Vec3d location){
		if (location.equals(Vec3d.ZERO)){
			this.hasSynced = false;
			this.powerWatch = location;
			return;
		}
		if (!this.powerWatch.equals(location)){
			this.powerWatch = location;
			this.powerWatchSyncTick = 0;
			this.hasSynced = false;
		}
	}

	public void setTrackData(NBTTagCompound compound){
		this.powerData = new PowerNodeEntry();
		this.powerData.readFromNBT(compound);
		this.hasSynced = true;
	}

	public boolean getHasSynced(){
		return this.hasSynced;
	}

	public void addDeferredTarget(EntityLiving ent, EntityLivingBase target){
		targetsToSet.put(ent, target);
	}
}
