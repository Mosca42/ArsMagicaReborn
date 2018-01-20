package amreborn.defs;

import amreborn.ArsMagicaReborn;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class AMSounds {
	
	public static final SoundEvent AIR_GUARDIAN_HIT = register(ArsMagicaReborn.MODID + ":mob.airguardian.hit");
	public static final SoundEvent AIR_GUARDIAN_DEATH = register(ArsMagicaReborn.MODID + ":mob.airguardian.death");
	public static final SoundEvent AIR_GUARDIAN_IDLE = register(ArsMagicaReborn.MODID + ":mob.airguardian.idle");
	
	public static final SoundEvent ARCANE_GUARDIAN_HIT = register(ArsMagicaReborn.MODID + ":mob.arcaneguardian.hit");
	public static final SoundEvent ARCANE_GUARDIAN_DEATH = register(ArsMagicaReborn.MODID + ":mob.arcaneguardian.death");
	public static final SoundEvent ARCANE_GUARDIAN_IDLE = register(ArsMagicaReborn.MODID + ":mob.arcaneguardian.idle");
	public static final SoundEvent ARCANE_GUARDIAN_SPELL = register(ArsMagicaReborn.MODID + ":mob.arcaneguardian.spell");
	
	public static final SoundEvent LIGHTNING_GUARDIAN_IDLE = register(ArsMagicaReborn.MODID + ":mob.lightningguardian.idle");
	public static final SoundEvent LIGHTNING_GUARDIAN_ATTACK = register(ArsMagicaReborn.MODID + ":mob.lightningguardian.attack");
	public static final SoundEvent LIGHTNING_GUARDIAN_ATTACK_STATIC = register(ArsMagicaReborn.MODID + ":mob.lightningguardian.attack_static");
	public static final SoundEvent LIGHTNING_GUARDIAN_LIGHTNING_ROD_1 = register(ArsMagicaReborn.MODID + ":mob.lightningguardian.lightning_rod_1");
	public static final SoundEvent LIGHTNING_GUARDIAN_LIGHTNING_ROD_START = register(ArsMagicaReborn.MODID + ":mob.lightningguardian.lightning_rod_start");
	public static final SoundEvent LIGHTNING_GUARDIAN_STATIC = register(ArsMagicaReborn.MODID + ":mob.lightningguardian.static");
	public static final SoundEvent LIGHTNING_GUARDIAN_HIT = register(ArsMagicaReborn.MODID + ":mob.lightningguardian.hit");
	public static final SoundEvent LIGHTNING_GUARDIAN_DEATH = register(ArsMagicaReborn.MODID + ":mob.lightningguardian.death");

	public static final SoundEvent NATURE_GUARDIAN_WHIRL_LOOP = register(ArsMagicaReborn.MODID + ":mob.natureguardian.whirlloop");
	public static final SoundEvent NATURE_GUARDIAN_HIT = register(ArsMagicaReborn.MODID + ":mob.natureguardian.hit");
	public static final SoundEvent NATURE_GUARDIAN_IDLE = register(ArsMagicaReborn.MODID + ":mob.natureguardian.idle");
	public static final SoundEvent NATURE_GUARDIAN_DEATH = register(ArsMagicaReborn.MODID + ":mob.natureguardian.death");
	public static final SoundEvent NATURE_GUARDIAN_ATTACK = register(ArsMagicaReborn.MODID + ":mob.natureguardian.attack");
	
	public static final SoundEvent LIFE_GUARDIAN_SUMMON = register(ArsMagicaReborn.MODID + ":mob.lifeguardian.summon");
	public static final SoundEvent LIFE_GUARDIAN_HIT = register(ArsMagicaReborn.MODID + ":mob.lifeguardian.hit");
	public static final SoundEvent LIFE_GUARDIAN_DEATH = register(ArsMagicaReborn.MODID + ":mob.lifeguardian.death");
	public static final SoundEvent LIFE_GUARDIAN_IDLE = register(ArsMagicaReborn.MODID + ":mob.lifeguardian.idle");
	public static final SoundEvent LIFE_GUARDIAN_HEAL = register(ArsMagicaReborn.MODID + ":mob.lifeguardian.heal");
	
	public static final SoundEvent WINTER_GUARDIAN_LAUNCH_ARM = register(ArsMagicaReborn.MODID + ":mob.winterguardian.launcharm");
	public static final SoundEvent WINTER_GUARDIAN_IDLE = register(ArsMagicaReborn.MODID + ":mob.winterguardian.idle");
	public static final SoundEvent WINTER_GUARDIAN_HIT = register(ArsMagicaReborn.MODID + ":mob.winterguardian.hit");
	public static final SoundEvent WINTER_GUARDIAN_DEATH = register(ArsMagicaReborn.MODID + ":mob.winterguardian.death");
	public static final SoundEvent WINTER_GUARDIAN_ATTACK = register(ArsMagicaReborn.MODID + ":mob.winterguardian.attack");
	
	public static final SoundEvent EARTH_GUARDIAN_HIT = register(ArsMagicaReborn.MODID + ":mob.earthguardian.hit");
	public static final SoundEvent EARTH_GUARDIAN_DEATH = register(ArsMagicaReborn.MODID + ":mob.earthguardian.death");
	public static final SoundEvent EARTH_GUARDIAN_IDLE = register(ArsMagicaReborn.MODID + ":mob.earthguardian.idle");
	public static final SoundEvent EARTH_GUARDIAN_ATTACK = register(ArsMagicaReborn.MODID + ":mob.earthguardian.attack");
	
	public static final SoundEvent ENDER_GUARDIAN_ROAR = register(ArsMagicaReborn.MODID + ":mob.enderguardian.roar");
	public static final SoundEvent ENDER_GUARDIAN_FLAP = register(ArsMagicaReborn.MODID + ":mob.enderguardian.flap");
	public static final SoundEvent ENDER_GUARDIAN_HIT = register(ArsMagicaReborn.MODID + ":mob.enderguardian.hit");
	public static final SoundEvent ENDER_GUARDIAN_DEATH = register(ArsMagicaReborn.MODID + ":mob.enderguardian.death");
	public static final SoundEvent ENDER_GUARDIAN_IDLE = register(ArsMagicaReborn.MODID + ":mob.enderguardian.idle");
	public static final SoundEvent ENDER_GUARDIAN_ATTACK = register(ArsMagicaReborn.MODID + ":mob.enderguardian.attack");

	public static final SoundEvent FIRE_GUARDIAN_HIT = register(ArsMagicaReborn.MODID + ":mob.fireguardian.hit");
	public static final SoundEvent FIRE_GUARDIAN_DEATH = register(ArsMagicaReborn.MODID + ":mob.fireguardian.death");
	public static final SoundEvent FIRE_GUARDIAN_IDLE = register(ArsMagicaReborn.MODID + ":mob.fireguardian.idle");
	public static final SoundEvent FIRE_GUARDIAN_ATTACK = register(ArsMagicaReborn.MODID + ":mob.fireguardian.attack");

	public static final SoundEvent WATER_GUARDIAN_HIT = register(ArsMagicaReborn.MODID + ":mob.waterguardian.hit");
	public static final SoundEvent WATER_GUARDIAN_IDLE = register(ArsMagicaReborn.MODID + ":mob.waterguardian.idle");
	public static final SoundEvent WATER_GUARDIAN_DEATH = register(ArsMagicaReborn.MODID + ":mob.waterguardian.death");
	public static final SoundEvent WATER_GUARDIAN_ATTACK = register(ArsMagicaReborn.MODID + ":mob.waterguardian.attack");
	
	public static final SoundEvent MANA_ELEMENTAL_HIT = register(ArsMagicaReborn.MODID + ":mob.manaelemental.hit");
	public static final SoundEvent MANA_ELEMENTAL_IDLE = register(ArsMagicaReborn.MODID + ":mob.manaelemental.living");
	public static final SoundEvent MANA_ELEMENTAL_DEATH = register(ArsMagicaReborn.MODID + ":mob.manaelemental.death");
	
	public static final SoundEvent HECATE_IDLE = register(ArsMagicaReborn.MODID + ":mob.hecate.idle");
	public static final SoundEvent HECATE_DEATH = register(ArsMagicaReborn.MODID + ":mob.hecate.death");
	public static final SoundEvent HECATE_HIT = register(ArsMagicaReborn.MODID + ":mob.hecate.hit");
	
	public static final SoundEvent GATEWAY_OPEN = register(ArsMagicaReborn.MODID + ":misc.gateway.open");
	public static final SoundEvent RECONSTRUCTOR_COMPLETE = register(ArsMagicaReborn.MODID + ":misc.reconstructor.complete");
	public static final SoundEvent CALEFACTOR_BURN = register(ArsMagicaReborn.MODID + ":misc.calefactor.burn");
	public static final SoundEvent CRAFTING_ALTAR_CREATE_SPELL = register(ArsMagicaReborn.MODID + ":misc.craftingaltar.create_spell");
	
	public static final SoundEvent MOO_IDLE = register(ArsMagicaReborn.MODID + ":mob.moo.idle");
	public static final SoundEvent MOO_DEATH = register(ArsMagicaReborn.MODID + ":mob.moo.death");
	public static final SoundEvent MOO_HIT = register(ArsMagicaReborn.MODID + ":mob.moo.hit");
	
	private static SoundEvent register(String str) {
		return GameRegistry.register(new SoundEvent(new ResourceLocation(str)).setRegistryName(new ResourceLocation(str)));
	}
}
