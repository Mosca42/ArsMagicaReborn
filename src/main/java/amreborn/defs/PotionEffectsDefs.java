package amreborn.defs;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import amreborn.ArsMagicaReborn;
import amreborn.LogHelper;
import amreborn.buffs.BuffEffect;
import amreborn.buffs.BuffEffectAgility;
import amreborn.buffs.BuffEffectAstralDistortion;
import amreborn.buffs.BuffEffectBurnoutReduction;
import amreborn.buffs.BuffEffectCharmed;
import amreborn.buffs.BuffEffectClarity;
import amreborn.buffs.BuffEffectEntangled;
import amreborn.buffs.BuffEffectFlight;
import amreborn.buffs.BuffEffectFrostSlowed;
import amreborn.buffs.BuffEffectFury;
import amreborn.buffs.BuffEffectGravityWell;
import amreborn.buffs.BuffEffectHaste;
import amreborn.buffs.BuffEffectIllumination;
import amreborn.buffs.BuffEffectInstantMana;
import amreborn.buffs.BuffEffectLeap;
import amreborn.buffs.BuffEffectLevitation;
import amreborn.buffs.BuffEffectMagicShield;
import amreborn.buffs.BuffEffectManaRegen;
import amreborn.buffs.BuffEffectRegeneration;
import amreborn.buffs.BuffEffectScrambleSynapses;
import amreborn.buffs.BuffEffectShield;
import amreborn.buffs.BuffEffectShrink;
import amreborn.buffs.BuffEffectSilence;
import amreborn.buffs.BuffEffectSlowfall;
import amreborn.buffs.BuffEffectSpellReflect;
import amreborn.buffs.BuffEffectSwiftSwim;
import amreborn.buffs.BuffEffectTemporalAnchor;
import amreborn.buffs.BuffEffectTrueSight;
import amreborn.buffs.BuffEffectWaterBreathing;
import amreborn.buffs.BuffEffectWateryGrave;
import amreborn.buffs.BuffMaxManaIncrease;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class PotionEffectsDefs {
	
	public static final HashMap<Potion, Class<? extends BuffEffect>> classForId = new HashMap<Potion, Class<? extends BuffEffect>>();
	
	public static Potion agility;
	public static Potion astralDistortion;
	public static Potion burnoutReduction;
	public static Potion charme;
	public static Potion clarity;
	public static Potion entangle;
	public static Potion flight;
	public static Potion frostSlow;
	public static Potion fury;
	public static Potion gravityWell;
	public static Potion haste;
	public static Potion illumination;
	public static Potion instantMana;
	public static Potion leap;
	public static Potion levitation;
	public static Potion magicShield;
	public static Potion manaRegen;
	public static Potion regeneration;
	public static Potion scrambleSynapses;
	public static Potion shield;
	public static Potion shrink;
	public static Potion silence;
	public static Potion slowfall;
	public static Potion spellReflect;
	public static Potion swiftSwim;
	public static Potion temporalAnchor;
	public static Potion trueSight;
	public static Potion waterBreathing;
	public static Potion wateryGrave;
	public static Potion manaBoost;
	
	public static final int default_buff_duration = 600;
	
	public static void init () {
		agility = createPotion(new ResourceLocation(ArsMagicaReborn.MODID + ":agility"), false, 0xade000, 0, 0, BuffEffectAgility.class);
		astralDistortion = createPotion(new ResourceLocation(ArsMagicaReborn.MODID + ":astral_distortion"), true, 0x6c0000, 0, 4, BuffEffectAstralDistortion.class);
		burnoutReduction = createPotion(new ResourceLocation(ArsMagicaReborn.MODID + ":burnout_reduction"), false, 0xcc0000, 1, 1, BuffEffectBurnoutReduction.class);
		charme = createPotion(new ResourceLocation(ArsMagicaReborn.MODID + ":charme"), true, 0xff3ca2, 3, 2, BuffEffectCharmed.class);
		clarity = createPotion(new ResourceLocation(ArsMagicaReborn.MODID + ":clarity"), false, 0xbbffff, 0, 5, BuffEffectClarity.class);
		entangle = createPotion(new ResourceLocation(ArsMagicaReborn.MODID + ":entangle"), false, 0x009300, 3, 7, BuffEffectEntangled.class);
		flight = createPotion(new ResourceLocation(ArsMagicaReborn.MODID + ":flight"), false, 0xc6dada, 2, 1, BuffEffectFlight.class);
		frostSlow = createPotion(new ResourceLocation(ArsMagicaReborn.MODID + ":frost_slow"), true, 0x1fffdd, 3, 3, BuffEffectFrostSlowed.class);
		fury = createPotion(new ResourceLocation(ArsMagicaReborn.MODID + ":fury"), true, 0xff8033, 3, 6, BuffEffectFury.class);
		gravityWell = createPotion(new ResourceLocation(ArsMagicaReborn.MODID + ":gravity_well"), true, 0xa400ff, 0, 6, BuffEffectGravityWell.class);
		haste = createPotion(new ResourceLocation(ArsMagicaReborn.MODID + ":haste"), false, 0xf1f1f1, 2, 3, BuffEffectHaste.class);
		illumination = createPotion(new ResourceLocation(ArsMagicaReborn.MODID + ":illumination"), false, 0xffffbe, 1, 0, BuffEffectIllumination.class);
		instantMana = createPotion(new ResourceLocation(ArsMagicaReborn.MODID + ":instant_mana"), false, 0x00ffff, 0, 0, BuffEffectInstantMana.class);
		leap = createPotion(new ResourceLocation(ArsMagicaReborn.MODID + ":leap"), false, 0x00ff00, 0, 2, BuffEffectLeap.class);
		levitation = createPotion(new ResourceLocation(ArsMagicaReborn.MODID + ":levitation"), false, 0xd780ff, 0, 7, BuffEffectLevitation.class);
		magicShield = createPotion(new ResourceLocation(ArsMagicaReborn.MODID + ":magic_shield"), false, 0xd780ff, 3, 1, BuffEffectMagicShield.class);
		manaRegen = createPotion(new ResourceLocation(ArsMagicaReborn.MODID + ":mana_regen"), false, 0x8bffff, 3, 5, BuffEffectManaRegen.class);
		regeneration = createPotion(new ResourceLocation(ArsMagicaReborn.MODID + ":regeneration"), false, 0xff00ff, 2, 6, BuffEffectRegeneration.class);
		scrambleSynapses = createPotion(new ResourceLocation(ArsMagicaReborn.MODID + ":scramble_synapses"), true, 0x306600, 3, 7, BuffEffectScrambleSynapses.class);
		shield = createPotion(new ResourceLocation(ArsMagicaReborn.MODID + ":shield"), false, 0xc4c4c4, 0, 0, BuffEffectShield.class);
		shrink = createPotion(new ResourceLocation(ArsMagicaReborn.MODID + ":shrink"), false, 0x0000dd, 0, 5, BuffEffectShrink.class);
		silence = createPotion(new ResourceLocation(ArsMagicaReborn.MODID + ":silence"), true, 0xc1c1ff, 4, 6, BuffEffectSilence.class);
		slowfall = createPotion(new ResourceLocation(ArsMagicaReborn.MODID + ":slowfall"), false, 0xe3ffe3, 2, 2, BuffEffectSlowfall.class);
		spellReflect = createPotion(new ResourceLocation(ArsMagicaReborn.MODID + ":spell_reflect"), false, 0xadffff, 4, 3, BuffEffectSpellReflect.class);
		swiftSwim = createPotion(new ResourceLocation(ArsMagicaReborn.MODID + ":swift_swim"), false, 0x3b3bff, 4, 7, BuffEffectSwiftSwim.class);
		temporalAnchor = createPotion(new ResourceLocation(ArsMagicaReborn.MODID + ":temporal_anchor"), false, 0xa2a2a2, 3, 4, BuffEffectTemporalAnchor.class);
		trueSight = createPotion(new ResourceLocation(ArsMagicaReborn.MODID + ":true_sight"), false, 0xc400ff, 2, 4, BuffEffectTrueSight.class);
		waterBreathing = createPotion(new ResourceLocation(ArsMagicaReborn.MODID + ":water_breathing"), false, 0x0000ff, 2, 0, BuffEffectWaterBreathing.class);
		wateryGrave = createPotion(new ResourceLocation(ArsMagicaReborn.MODID + ":watery_grave"), true, 0x0000a2, 4, 0, BuffEffectWateryGrave.class);
		manaBoost = createPotion(new ResourceLocation(ArsMagicaReborn.MODID + ":mana_boost"), false, 0x0093ff, 3, 0, BuffMaxManaIncrease.class);
	}
	
	public static Potion createPotion(ResourceLocation loc, boolean isBad, int color, int posX, int posY, Class<? extends BuffEffect> clazz) {
		Potion potion = new AMPotion(isBad, color).setIconIndex(posX, posY).setPotionName(loc.toString());
		GameRegistry.register(potion, loc);
		classForId.put(potion, clazz);
		return potion;
	}
	
	public static PotionEffect getEffect(PotionEffect effect) {
		Class<? extends BuffEffect> clazz = classForId.get(effect.getPotion());
		if (clazz == null) return null;
		if (effect instanceof BuffEffect) return null;
		try {
			Constructor<? extends BuffEffect> constr;
			try {
				constr = clazz.getDeclaredConstructor(int.class, int.class);
			} catch (NoSuchMethodException e) {
				constr = clazz.getDeclaredConstructor(Potion.class, int.class, int.class);
				return constr.newInstance(effect.getPotion(), effect.getDuration(), effect.getAmplifier());
			}
			return constr.newInstance(effect.getDuration(), effect.getAmplifier());
		} catch (NoSuchMethodException e) {
			LogHelper.warn("Wrong definition for : " + clazz.getName());
		} catch (SecurityException e) {
			LogHelper.warn("Is this even a thing ? (SecurityException:" + clazz.getName() + ")");
		} catch (InstantiationException e) {
			LogHelper.warn("Could not create : " + clazz.getName());
		} catch (IllegalAccessException e) {
			LogHelper.warn("Could not access : " + clazz.getName());
		} catch (IllegalArgumentException e) {
			LogHelper.warn("Could not create : " + clazz.getName());
		} catch (InvocationTargetException e) {
			LogHelper.warn("InvocationTargetException" + clazz.getName());
		} catch (NullPointerException e) {
			LogHelper.warn("This should never be a thing : NullPointerExecption at " + clazz.getName());
		}
		
		return null;
	}
}
