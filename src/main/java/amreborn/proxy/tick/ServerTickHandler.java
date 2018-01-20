package amreborn.proxy.tick;

import java.util.ArrayList;
import java.util.HashMap;

import amreborn.ArsMagicaReborn;
import amreborn.bosses.BossSpawnHelper;
import amreborn.defs.ItemDefs;
import amreborn.packet.AMDataWriter;
import amreborn.packet.AMNetHandler;
import amreborn.packet.AMPacketIDs;
import amreborn.trackers.EntityItemWatcher;
import amreborn.utils.DimensionUtilities;
import amreborn.world.MeteorSpawnHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ServerTickHandler{

	private boolean firstTick = true;
	public static HashMap<EntityLiving, EntityLivingBase> targetsToSet = new HashMap<EntityLiving, EntityLivingBase>();

	public static String lastWorldName;

	private void gameTick_Start(){

		if (FMLCommonHandler.instance().getMinecraftServerInstance().getFolderName() != lastWorldName){
			lastWorldName = FMLCommonHandler.instance().getMinecraftServerInstance().getFolderName();
			firstTick = true;
		}

		if (firstTick){
			ItemDefs.crystalPhylactery.getSpawnableEntities(FMLCommonHandler.instance().getMinecraftServerInstance().worlds[0]);
			firstTick = false;
		}

		ArsMagicaReborn.proxy.itemFrameWatcher.checkWatchedFrames();
	}

	private void gameTick_End(){
		BossSpawnHelper.instance.tick();
		MeteorSpawnHelper.instance.tick();
		EntityItemWatcher.instance.tick();
	}
	
	@SubscribeEvent
	public void onServerTick(TickEvent.ServerTickEvent event){
		if (event.phase == TickEvent.Phase.START){
			gameTick_Start();
		}else if (event.phase == TickEvent.Phase.END){
			gameTick_End();
		}
	}

	@SubscribeEvent
	public void onWorldTick(TickEvent.WorldTickEvent event){
//		LogHelper.info(event.side);
//		if (AMCore.config.retroactiveWorldgen())
//			RetroactiveWorldgenerator.instance.continueRetrogen(event.world);
//
		applyDeferredPotionEffects();
		if (event.phase == TickEvent.Phase.END){
			applyDeferredDimensionTransfers();
		}
	}

	private void applyDeferredPotionEffects(){
		for (EntityLivingBase ent : ArsMagicaReborn.proxy.getDeferredPotionEffects().keySet()){
			ArrayList<PotionEffect> potions = ArsMagicaReborn.proxy.getDeferredPotionEffects().get(ent);
			for (PotionEffect effect : potions)
				ent.addPotionEffect(effect);
		}

		ArsMagicaReborn.proxy.clearDeferredPotionEffects();
	}
	
	@SubscribeEvent
	public void onConfigChanged (ConfigChangedEvent e) {
		if (!e.getModID().equals(ArsMagicaReborn.MODID))
			return;
		ArsMagicaReborn.config.save();
		ArsMagicaReborn.config.init();
		ArsMagicaReborn.disabledSkills.save();
		ArsMagicaReborn.disabledSkills.getDisabledSkills(true);
	}

	private void applyDeferredDimensionTransfers(){
		for (EntityLivingBase ent : ArsMagicaReborn.proxy.getDeferredDimensionTransfers().keySet()){
			DimensionUtilities.doDimensionTransfer(ent, ArsMagicaReborn.proxy.getDeferredDimensionTransfers().get(ent));
		}

		ArsMagicaReborn.proxy.clearDeferredDimensionTransfers();
	}

//	private void applyDeferredTargetSets(){
//		Iterator<Entry<EntityLiving, EntityLivingBase>> it = targetsToSet.entrySet().iterator();
//		while (it.hasNext()){
//			Entry<EntityLiving, EntityLivingBase> entry = it.next();
//			if (entry.getKey() != null && !entry.getKey().isDead)
//				entry.getKey().setAttackTarget(entry.getValue());
//			it.remove();
//		}
//	}

	public void addDeferredTarget(EntityLiving ent, EntityLivingBase target){
		targetsToSet.put(ent, target);
	}

	public void blackoutArmorPiece(EntityPlayerMP player, EntityEquipmentSlot slot, int cooldown){
		AMNetHandler.INSTANCE.sendPacketToClientPlayer(player, AMPacketIDs.FLASH_ARMOR_PIECE, new AMDataWriter().add(slot.getIndex()).add(cooldown).generate());
	}

}
