package amreborn.affinity;

import java.util.Map.Entry;

import amreborn.ArsMagicaReborn;
import amreborn.affinity.abilities.AbilityAgile;
import amreborn.affinity.abilities.AbilityAntiEndermen;
import amreborn.affinity.abilities.AbilityClearCaster;
import amreborn.affinity.abilities.AbilityColdBlooded;
import amreborn.affinity.abilities.AbilityExpandedLungs;
import amreborn.affinity.abilities.AbilityFastHealing;
import amreborn.affinity.abilities.AbilityFirePunch;
import amreborn.affinity.abilities.AbilityFireResistance;
import amreborn.affinity.abilities.AbilityFireWeakness;
import amreborn.affinity.abilities.AbilityFluidity;
import amreborn.affinity.abilities.AbilityFulmination;
import amreborn.affinity.abilities.AbilityLavaFreeze;
import amreborn.affinity.abilities.AbilityLeafLike;
import amreborn.affinity.abilities.AbilityLightAsAFeather;
import amreborn.affinity.abilities.AbilityLightningStep;
import amreborn.affinity.abilities.AbilityMagicWeakness;
import amreborn.affinity.abilities.AbilityNightVision;
import amreborn.affinity.abilities.AbilityOneWithMagic;
import amreborn.affinity.abilities.AbilityPacifist;
import amreborn.affinity.abilities.AbilityPhotosynthesis;
import amreborn.affinity.abilities.AbilityPoisonResistance;
import amreborn.affinity.abilities.AbilityReflexes;
import amreborn.affinity.abilities.AbilityRelocation;
import amreborn.affinity.abilities.AbilityRooted;
import amreborn.affinity.abilities.AbilityShortCircuit;
import amreborn.affinity.abilities.AbilitySolidBones;
import amreborn.affinity.abilities.AbilitySunlightWeakness;
import amreborn.affinity.abilities.AbilitySwiftSwim;
import amreborn.affinity.abilities.AbilityThorns;
import amreborn.affinity.abilities.AbilityThunderPunch;
import amreborn.affinity.abilities.AbilityWaterFreeze;
import amreborn.affinity.abilities.AbilityWaterWeakness;
import amreborn.api.affinity.AbstractAffinityAbility;
import amreborn.api.affinity.Affinity;
import amreborn.api.event.SpellCastEvent;
import amreborn.extensions.AffinityData;
import amreborn.utils.WorldUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AffinityAbilityHelper {
	
	static {
		//AIR
		GameRegistry.register(new AbilityLightAsAFeather());
		GameRegistry.register(new AbilityAgile());
		
		//ARCANE
		GameRegistry.register(new AbilityClearCaster());
		GameRegistry.register(new AbilityMagicWeakness());
		GameRegistry.register(new AbilityOneWithMagic());
		
		//EARTH
		GameRegistry.register(new AbilitySolidBones());
		
		//ENDER
		GameRegistry.register(new AbilityRelocation());
		GameRegistry.register(new AbilityNightVision());
		GameRegistry.register(new AbilityWaterWeakness(Affinity.ENDER));
		GameRegistry.register(new AbilityPoisonResistance());
		GameRegistry.register(new AbilitySunlightWeakness());
		
		//FIRE
		GameRegistry.register(new AbilityFireResistance());
		GameRegistry.register(new AbilityFirePunch());
		GameRegistry.register(new AbilityWaterWeakness(Affinity.FIRE));
		
		//ICE
		GameRegistry.register(new AbilityLavaFreeze());
		GameRegistry.register(new AbilityWaterFreeze());
		GameRegistry.register(new AbilityColdBlooded());
		
		//LIFE
		GameRegistry.register(new AbilityFastHealing());
		GameRegistry.register(new AbilityPacifist());
		
		//WATER
		GameRegistry.register(new AbilityExpandedLungs());
		GameRegistry.register(new AbilityFluidity());
		GameRegistry.register(new AbilitySwiftSwim());
		GameRegistry.register(new AbilityFireWeakness());
		GameRegistry.register(new AbilityAntiEndermen());
		
		//NATURE
		GameRegistry.register(new AbilityRooted());
		GameRegistry.register(new AbilityThorns());
		GameRegistry.register(new AbilityLeafLike());
		GameRegistry.register(new AbilityPhotosynthesis());
		
		//LIGHTNING
		GameRegistry.register(new AbilityLightningStep());
		GameRegistry.register(new AbilityReflexes());
		GameRegistry.register(new AbilityFulmination());
		GameRegistry.register(new AbilityShortCircuit());
		GameRegistry.register(new AbilityThunderPunch());
		GameRegistry.register(new AbilityWaterWeakness(Affinity.LIGHTNING));
	}
	
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onKeyInput(InputEvent.KeyInputEvent event) {
		for (AbstractAffinityAbility ability : GameRegistry.findRegistry(AbstractAffinityAbility.class).getValues()) {
			if (ability.getKey() != null && ability.getKey().isPressed()) {
				EntityPlayer player = ArsMagicaReborn.proxy.getLocalPlayer();
				//if (FMLCommonHandler.instance().getMinecraftServerInstance() == null)
				//	return;

				player = player.getEntityWorld().getPlayerEntityByUUID(player.getUniqueID());
				if (ability.canApply(player)) {

					WorldUtils.runSided(Side.CLIENT, ability.createRunnable(ArsMagicaReborn.proxy.getLocalPlayer()));
					WorldUtils.runSided(Side.SERVER, ability.createRunnable(player));
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerTick(LivingUpdateEvent event) {
		if (event.getEntityLiving() instanceof EntityPlayer) {
			if (!event.getEntityLiving().world.isRemote) {
				for (Entry<String, Integer> entry : AffinityData.For(event.getEntityLiving()).getCooldowns().entrySet()) {
					if (entry.getValue() > 0)
						AffinityData.For(event.getEntityLiving()).addCooldown(entry.getKey(), entry.getValue() - 1);
				}
			}
			for (AbstractAffinityAbility ability : GameRegistry.findRegistry(AbstractAffinityAbility.class).getValues()) {
				if (ability.canApply((EntityPlayer) event.getEntityLiving()))
					ability.applyTick((EntityPlayer) event.getEntityLiving());
				else
					ability.removeEffects((EntityPlayer) event.getEntityLiving());
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerHurt(LivingHurtEvent event) {
		if (event.getEntityLiving() instanceof EntityPlayer) {
			for (AbstractAffinityAbility ability : GameRegistry.findRegistry(AbstractAffinityAbility.class).getValues()) {
				if (ability.canApply((EntityPlayer) event.getEntityLiving()))
					ability.applyHurt((EntityPlayer) event.getEntityLiving(), event, false);
			}
		}
		if (event.getSource().getEntity() != null && event.getSource().getEntity() instanceof EntityPlayer) {
			for (AbstractAffinityAbility ability : GameRegistry.findRegistry(AbstractAffinityAbility.class).getValues()) {
				if (ability.canApply((EntityPlayer) event.getSource().getEntity()))
					ability.applyHurt((EntityPlayer) event.getSource().getEntity(), event, true);
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerFall(LivingFallEvent event) {
		if (event.getEntityLiving() instanceof EntityPlayer) {
			for (AbstractAffinityAbility ability : GameRegistry.findRegistry(AbstractAffinityAbility.class).getValues()) {
				if (ability.canApply((EntityPlayer) event.getEntityLiving()))
					ability.applyFall((EntityPlayer) event.getEntityLiving(), event);
			}
		}
	}
	
	@SubscribeEvent
	public void onDeath(LivingDeathEvent event) {
		if (event.getEntityLiving() instanceof EntityPlayer) {
			for (AbstractAffinityAbility ability : GameRegistry.findRegistry(AbstractAffinityAbility.class).getValues()) {
				if (ability.canApply((EntityPlayer) event.getEntityLiving()))
					ability.applyDeath((EntityPlayer) event.getEntityLiving(), event);
			}
		}
		if (event.getSource().getEntity() != null && event.getSource().getEntity() instanceof EntityPlayer) {
			for (AbstractAffinityAbility ability : GameRegistry.findRegistry(AbstractAffinityAbility.class).getValues()) {
				if (ability.canApply((EntityPlayer) event.getSource().getEntity()))
					ability.applyKill((EntityPlayer) event.getSource().getEntity(), event);
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerJump(LivingJumpEvent event) {
		if (event.getEntityLiving() instanceof EntityPlayer) {
			for (AbstractAffinityAbility ability : GameRegistry.findRegistry(AbstractAffinityAbility.class).getValues()) {
				if (ability.canApply((EntityPlayer) event.getEntityLiving()))
					ability.applyJump((EntityPlayer) event.getEntityLiving(), event);
			}
		}
	}
	
	@SubscribeEvent
	public void onSpellCast(SpellCastEvent.Post event) {
		if (event.entityLiving instanceof EntityPlayer) {
			for (AbstractAffinityAbility ability : GameRegistry.findRegistry(AbstractAffinityAbility.class).getValues()) {
				if (ability.canApply((EntityPlayer) event.entityLiving))
					ability.applySpellCast((EntityPlayer) event.entityLiving, event);
			}
		}
	}
	
	@SubscribeEvent
	public void onPreSpellCast(SpellCastEvent.Pre event) {
		if (event.entityLiving instanceof EntityPlayer) {
			for (AbstractAffinityAbility ability : GameRegistry.findRegistry(AbstractAffinityAbility.class).getValues()) {
				if (ability.canApply((EntityPlayer) event.entityLiving))
					ability.applyPreSpellCast((EntityPlayer) event.entityLiving, event);
			}
		}
	}
}
