package amreborn.defs;

import amreborn.ArsMagicaReborn;
import amreborn.api.SpellRegistry;
import amreborn.api.skill.SkillPoint;
import amreborn.api.spell.SpellShape;
import amreborn.spell.component.Absorption;
import amreborn.spell.component.Accelerate;
import amreborn.spell.component.Appropriation;
import amreborn.spell.component.AstralDistortion;
import amreborn.spell.component.Attract;
import amreborn.spell.component.BanishRain;
import amreborn.spell.component.Blind;
import amreborn.spell.component.Blink;
import amreborn.spell.component.Blizzard;
import amreborn.spell.component.Charm;
import amreborn.spell.component.ChronoAnchor;
import amreborn.spell.component.CreateWater;
import amreborn.spell.component.Daylight;
import amreborn.spell.component.Dig;
import amreborn.spell.component.Disarm;
import amreborn.spell.component.Dispel;
import amreborn.spell.component.DivineIntervention;
import amreborn.spell.component.Drought;
import amreborn.spell.component.Drown;
import amreborn.spell.component.EnderIntervention;
import amreborn.spell.component.Entangle;
import amreborn.spell.component.FallingStar;
import amreborn.spell.component.FireDamage;
import amreborn.spell.component.FireRain;
import amreborn.spell.component.Flight;
import amreborn.spell.component.Fling;
import amreborn.spell.component.Forge;
import amreborn.spell.component.Freeze;
import amreborn.spell.component.FrostDamage;
import amreborn.spell.component.Fury;
import amreborn.spell.component.GravityWell;
import amreborn.spell.component.Grow;
import amreborn.spell.component.HarvestPlants;
import amreborn.spell.component.Haste;
import amreborn.spell.component.Heal;
import amreborn.spell.component.Ignition;
import amreborn.spell.component.Invisiblity;
import amreborn.spell.component.Knockback;
import amreborn.spell.component.Leap;
import amreborn.spell.component.Levitation;
import amreborn.spell.component.LifeDrain;
import amreborn.spell.component.LifeTap;
import amreborn.spell.component.Light;
import amreborn.spell.component.LightningDamage;
import amreborn.spell.component.MagicDamage;
import amreborn.spell.component.ManaBlast;
import amreborn.spell.component.ManaDrain;
import amreborn.spell.component.ManaLink;
import amreborn.spell.component.ManaShield;
import amreborn.spell.component.Mark;
import amreborn.spell.component.MeltArmor;
import amreborn.spell.component.Moonrise;
import amreborn.spell.component.Nauseate;
import amreborn.spell.component.NightVision;
import amreborn.spell.component.PhysicalDamage;
import amreborn.spell.component.PlaceBlock;
import amreborn.spell.component.Plant;
import amreborn.spell.component.Plow;
import amreborn.spell.component.RandomTeleport;
import amreborn.spell.component.Recall;
import amreborn.spell.component.Reflect;
import amreborn.spell.component.Regeneration;
import amreborn.spell.component.Repel;
import amreborn.spell.component.Rift;
import amreborn.spell.component.ScrambleSynapses;
import amreborn.spell.component.Shield;
import amreborn.spell.component.Shrink;
import amreborn.spell.component.Silence;
import amreborn.spell.component.Slow;
import amreborn.spell.component.Slowfall;
import amreborn.spell.component.Storm;
import amreborn.spell.component.Summon;
import amreborn.spell.component.SwiftSwim;
import amreborn.spell.component.Telekinesis;
import amreborn.spell.component.Transplace;
import amreborn.spell.component.TrueSight;
import amreborn.spell.component.WaterBreathing;
import amreborn.spell.component.WateryGrave;
import amreborn.spell.component.WizardsAutumn;
import amreborn.spell.modifier.Bounce;
import amreborn.spell.modifier.BuffPower;
import amreborn.spell.modifier.Colour;
import amreborn.spell.modifier.Damage;
import amreborn.spell.modifier.Dismembering;
import amreborn.spell.modifier.Duration;
import amreborn.spell.modifier.FeatherTouch;
import amreborn.spell.modifier.Gravity;
import amreborn.spell.modifier.Healing;
import amreborn.spell.modifier.Lunar;
import amreborn.spell.modifier.MiningPower;
import amreborn.spell.modifier.Piercing;
import amreborn.spell.modifier.Prosperity;
import amreborn.spell.modifier.Radius;
import amreborn.spell.modifier.Range;
import amreborn.spell.modifier.RuneProcs;
import amreborn.spell.modifier.Solar;
import amreborn.spell.modifier.Speed;
import amreborn.spell.modifier.TargetNonSolidBlocks;
import amreborn.spell.modifier.VelocityAdded;
import amreborn.spell.shape.AoE;
import amreborn.spell.shape.Beam;
import amreborn.spell.shape.Binding;
import amreborn.spell.shape.Chain;
import amreborn.spell.shape.Channel;
import amreborn.spell.shape.Contingency_Death;
import amreborn.spell.shape.Contingency_Fall;
import amreborn.spell.shape.Contingency_Fire;
import amreborn.spell.shape.Contingency_Health;
import amreborn.spell.shape.Contingency_Hit;
import amreborn.spell.shape.MissingShape;
import amreborn.spell.shape.Projectile;
import amreborn.spell.shape.Rune;
import amreborn.spell.shape.Self;
import amreborn.spell.shape.Toggle;
import amreborn.spell.shape.Touch;
import amreborn.spell.shape.Wall;
import amreborn.spell.shape.Wave;
import amreborn.spell.shape.Zone;
import net.minecraft.util.ResourceLocation;

public class SpellDefs {
	public static final SpellShape MISSING_SHAPE = new MissingShape();
	
	public static void init() {
		SpellRegistry.registerSpellShape("null", null, null, MISSING_SHAPE, null, 0, 0);
		SpellRegistry.registerSpellComponent("melt_armor", null, null, new MeltArmor(), null, 0, 0);
		SpellRegistry.registerSpellComponent("nauseate", null, null, new Nauseate(), null, 0, 0);
		SpellRegistry.registerSpellComponent("scramble_synapses", null, null, new ScrambleSynapses(), null, 0, 0);
		
		SpellRegistry.registerSpellModifier("colour", getModifierTexture("Colour"), SkillPoint.SKILL_POINT_1, new Colour(), SkillDefs.TREE_TALENT, 230, 75);

		handleOffenseTree();
		handleDefenseTree();
		handleUtilityTree();
	}
	
	public static void handleOffenseTree() {
		SpellRegistry.registerSpellShape("projectile", getShapeTexture("Projectile"), SkillPoint.SKILL_POINT_1, new Projectile(), SkillDefs.TREE_OFFENSE, 300, 45);
		SpellRegistry.registerSpellComponent("physical_damage", getComponentTexture("PhysicalDamage"), SkillPoint.SKILL_POINT_1, new PhysicalDamage(), SkillDefs.TREE_OFFENSE, 300, 90, ArsMagicaReborn.MODID + ":projectile");
		SpellRegistry.registerSpellModifier("gravity", getModifierTexture("Gravity"), SkillPoint.SKILL_POINT_1, new Gravity(), SkillDefs.TREE_OFFENSE, 255, 70, ArsMagicaReborn.MODID + ":projectile");
		SpellRegistry.registerSpellModifier("bounce", getModifierTexture("Bounce"), SkillPoint.SKILL_POINT_1, new Bounce(), SkillDefs.TREE_OFFENSE, 345, 70, ArsMagicaReborn.MODID + ":projectile");
		
		SpellRegistry.registerSpellComponent("fire_damage", getComponentTexture("FireDamage"), SkillPoint.SKILL_POINT_1, new FireDamage(), SkillDefs.TREE_OFFENSE, 210, 135, ArsMagicaReborn.MODID + ":physical_damage");
		SpellRegistry.registerSpellComponent("lightning_damage", getComponentTexture("LightningDamage"), SkillPoint.SKILL_POINT_1, new LightningDamage(), SkillDefs.TREE_OFFENSE, 255, 135, ArsMagicaReborn.MODID + ":fire_damage");
		SpellRegistry.registerSpellComponent("ignition", getComponentTexture("Ignition"), SkillPoint.SKILL_POINT_2, new Ignition(), SkillDefs.TREE_OFFENSE, 165, 135, ArsMagicaReborn.MODID + ":fire_damage");
		SpellRegistry.registerSpellComponent("forge", getComponentTexture("Forge"), SkillPoint.SKILL_POINT_2, new Forge(), SkillDefs.TREE_OFFENSE, 120, 135, ArsMagicaReborn.MODID + ":ignition");	
		
		SpellRegistry.registerSpellComponent("magic_damage", getComponentTexture("MagicDamage"), SkillPoint.SKILL_POINT_1, new MagicDamage(), SkillDefs.TREE_OFFENSE, 390, 135, ArsMagicaReborn.MODID + ":physical_damage");
		SpellRegistry.registerSpellComponent("frost_damage", getComponentTexture("FrostDamage"), SkillPoint.SKILL_POINT_1, new FrostDamage(), SkillDefs.TREE_OFFENSE, 345, 135, ArsMagicaReborn.MODID + ":magic_damage");
		
		SpellRegistry.registerSpellComponent("drown", getComponentTexture("Drown"), SkillPoint.SKILL_POINT_1, new Drown(), SkillDefs.TREE_OFFENSE, 435, 135, ArsMagicaReborn.MODID + ":magic_damage");
		
		SpellRegistry.registerSpellComponent("blind", getComponentTexture("Blind"), SkillPoint.SKILL_POINT_2, new Blind(), SkillDefs.TREE_OFFENSE, 233, 180, ArsMagicaReborn.MODID + ":fire_damage", ArsMagicaReborn.MODID + ":lightning_damage");
		SpellRegistry.registerSpellShape("aoe", getShapeTexture("AoE"), SkillPoint.SKILL_POINT_2, new AoE(), SkillDefs.TREE_OFFENSE, 300, 180, ArsMagicaReborn.MODID + ":frost_damage", ArsMagicaReborn.MODID + ":physical_damage", ArsMagicaReborn.MODID + ":fire_damage", ArsMagicaReborn.MODID + ":lightning_damage", ArsMagicaReborn.MODID + ":magic_damage");
		SpellRegistry.registerSpellComponent("freeze", getComponentTexture("Freeze"), SkillPoint.SKILL_POINT_2, new Freeze(), SkillDefs.TREE_OFFENSE, 345, 180, ArsMagicaReborn.MODID + ":frost_damage");
		SpellRegistry.registerSpellComponent("knockback", getComponentTexture("Knockback"), SkillPoint.SKILL_POINT_2, new Knockback(), SkillDefs.TREE_OFFENSE, 390, 180, ArsMagicaReborn.MODID + ":magic_damage");
		
		SpellRegistry.registerSpellShape("contingency_fire", getShapeTexture("Contingency_Fire"), SkillPoint.SKILL_POINT_2, new Contingency_Fire(), SkillDefs.TREE_OFFENSE, 165, 190, ArsMagicaReborn.MODID + ":ignition");
		SpellRegistry.registerSpellModifier("solar", getModifierTexture("Solar"), SkillPoint.SKILL_POINT_3, new Solar(), SkillDefs.TREE_OFFENSE, 210, 255, ArsMagicaReborn.MODID + ":blind");
		
		SpellRegistry.registerSpellComponent("storm", getComponentTexture("Storm"), SkillPoint.SKILL_POINT_3, new Storm(), SkillDefs.TREE_OFFENSE, 255, 225, ArsMagicaReborn.MODID + ":lightning_damage");
		SpellRegistry.registerSpellComponent("astral_distortion", getComponentTexture("AstralDistortion"), SkillPoint.SKILL_POINT_2, new AstralDistortion(), SkillDefs.TREE_OFFENSE, 367, 215, ArsMagicaReborn.MODID + ":magic_damage", ArsMagicaReborn.MODID + ":frost_damage");
		SpellRegistry.registerSpellComponent("silence", getComponentTexture("Silence"), SkillPoint.SKILL_POINT_3, new Silence(), SkillDefs.TREE_OFFENSE, 345, 245, ArsMagicaReborn.MODID + ":astral_distortion");
		
		SpellRegistry.registerSpellComponent("fling", getComponentTexture("Fling"), SkillPoint.SKILL_POINT_2, new Fling(), SkillDefs.TREE_OFFENSE, 390, 245, ArsMagicaReborn.MODID + ":knockback");
		SpellRegistry.registerSpellModifier("velocity_added", getModifierTexture("VelocityAdded"), SkillPoint.SKILL_POINT_3, new VelocityAdded(), SkillDefs.TREE_OFFENSE, 390, 290, ArsMagicaReborn.MODID + ":fling");
		SpellRegistry.registerSpellComponent("watery_grave", getComponentTexture("WateryGrave"), SkillPoint.SKILL_POINT_2, new WateryGrave(), SkillDefs.TREE_OFFENSE, 435, 245, ArsMagicaReborn.MODID + ":drown");
		
		SpellRegistry.registerSpellModifier("piercing", getModifierTexture("Piercing"), SkillPoint.SKILL_POINT_3, new Piercing(), SkillDefs.TREE_OFFENSE, 323, 215, ArsMagicaReborn.MODID + ":freeze");
		
		SpellRegistry.registerSpellShape("beam", getShapeTexture("Beam"), SkillPoint.SKILL_POINT_3, new Beam(), SkillDefs.TREE_OFFENSE, 300, 270, ArsMagicaReborn.MODID + ":aoe");	
		SpellRegistry.registerSpellModifier("damage", getModifierTexture("Damage"), SkillPoint.SKILL_POINT_3, new Damage(), SkillDefs.TREE_OFFENSE, 300, 315, ArsMagicaReborn.MODID + ":beam");
		SpellRegistry.registerSpellComponent("fury", getComponentTexture("Fury"), SkillPoint.SKILL_POINT_3, new Fury(), SkillDefs.TREE_OFFENSE, 255, 315, ArsMagicaReborn.MODID + ":beam", ArsMagicaReborn.MODID + ":storm");
		SpellRegistry.registerSpellShape("wave", getShapeTexture("Wave"), SkillPoint.SKILL_POINT_3, new Wave(), SkillDefs.TREE_OFFENSE, 367, 315, ArsMagicaReborn.MODID + ":beam", ArsMagicaReborn.MODID + ":fling");	
		
		SpellRegistry.registerSpellComponent("blizzard", getComponentTexture("Blizzard"), SkillPoint.SILVER_POINT, new Blizzard(), SkillDefs.TREE_OFFENSE, 75, 45);
		SpellRegistry.registerSpellComponent("falling_star", getComponentTexture("FallingStar"), SkillPoint.SILVER_POINT, new FallingStar(), SkillDefs.TREE_OFFENSE, 75, 90);
		SpellRegistry.registerSpellComponent("fire_rain", getComponentTexture("FireRain"), SkillPoint.SILVER_POINT, new FireRain(), SkillDefs.TREE_OFFENSE, 75, 135);
		SpellRegistry.registerSpellComponent("mana_blast", getComponentTexture("ManaBlast"), SkillPoint.SILVER_POINT, new ManaBlast(), SkillDefs.TREE_OFFENSE, 75, 180);
		SpellRegistry.registerSpellModifier("dismembering", getModifierTexture("Dismembering"), SkillPoint.SILVER_POINT, new Dismembering(), SkillDefs.TREE_OFFENSE, 75, 225);
	}
	
	public static void handleDefenseTree () {
		//defense tree
		SpellRegistry.registerSpellShape("self", getShapeTexture("Self"), SkillPoint.SKILL_POINT_1, new Self(), SkillDefs.TREE_DEFENSE, 267, 45);

		SpellRegistry.registerSpellComponent("leap", getComponentTexture("Leap"), SkillPoint.SKILL_POINT_1, new Leap(), SkillDefs.TREE_DEFENSE, 222, 90, ArsMagicaReborn.MODID + ":self");
		SpellRegistry.registerSpellComponent("regeneration", getComponentTexture("Regeneration"), SkillPoint.SKILL_POINT_1, new Regeneration(), SkillDefs.TREE_DEFENSE, 357, 90, ArsMagicaReborn.MODID + ":self");

		SpellRegistry.registerSpellComponent("shrink", getComponentTexture("Shrink"), SkillPoint.SKILL_POINT_1, new Shrink(), SkillDefs.TREE_DEFENSE, 402, 90, ArsMagicaReborn.MODID + ":regeneration");
		SpellRegistry.registerSpellComponent("slowfall", getComponentTexture("Slowfall"), SkillPoint.SKILL_POINT_1, new Slowfall(), SkillDefs.TREE_DEFENSE, 222, 135, ArsMagicaReborn.MODID + ":leap");
		SpellRegistry.registerSpellComponent("heal", getComponentTexture("Heal"), SkillPoint.SKILL_POINT_1, new Heal(), SkillDefs.TREE_DEFENSE, 357, 135, ArsMagicaReborn.MODID + ":regeneration");
		SpellRegistry.registerSpellComponent("life_tap", getComponentTexture("LifeTap"), SkillPoint.SKILL_POINT_2, new LifeTap(), SkillDefs.TREE_DEFENSE, 312, 135, ArsMagicaReborn.MODID + ":heal");
		SpellRegistry.registerSpellModifier("healing", getModifierTexture("Healing"), SkillPoint.SKILL_POINT_3, new Healing(), SkillDefs.TREE_DEFENSE, 402, 135, ArsMagicaReborn.MODID + ":heal");

		SpellRegistry.registerSpellComponent("summon", getComponentTexture("Summon"), SkillPoint.SKILL_POINT_2, new Summon(), SkillDefs.TREE_DEFENSE, 267, 135, ArsMagicaReborn.MODID + ":life_tap");
		SpellRegistry.registerSpellShape("contingency_damage", getShapeTexture("Contingency_Damage"), SkillPoint.SKILL_POINT_2, new Contingency_Hit(), SkillDefs.TREE_DEFENSE, 447, 180, ArsMagicaReborn.MODID + ":healing");

		SpellRegistry.registerSpellComponent("haste", getComponentTexture("Haste"), SkillPoint.SKILL_POINT_1, new Haste(), SkillDefs.TREE_DEFENSE, 177, 155, ArsMagicaReborn.MODID + ":slowfall");
		SpellRegistry.registerSpellComponent("slow", getComponentTexture("Slow"), SkillPoint.SKILL_POINT_1, new Slow(), SkillDefs.TREE_DEFENSE, 132, 155, ArsMagicaReborn.MODID + ":slowfall");

		SpellRegistry.registerSpellComponent("gravity_well", getComponentTexture("GravityWell"), SkillPoint.SKILL_POINT_2, new GravityWell(), SkillDefs.TREE_DEFENSE, 222, 180, ArsMagicaReborn.MODID + ":slowfall");
		SpellRegistry.registerSpellComponent("life_drain", getComponentTexture("LifeDrain"), SkillPoint.SKILL_POINT_2, new LifeDrain(), SkillDefs.TREE_DEFENSE, 312, 180, ArsMagicaReborn.MODID + ":life_tap");
		SpellRegistry.registerSpellComponent("dispel", getComponentTexture("Dispel"), SkillPoint.SKILL_POINT_2, new Dispel(), SkillDefs.TREE_DEFENSE, 357, 180, ArsMagicaReborn.MODID + ":heal");

		SpellRegistry.registerSpellShape("contingency_fall", getShapeTexture("Contingency_Fall"), SkillPoint.SKILL_POINT_2, new Contingency_Fall(), SkillDefs.TREE_DEFENSE, 267, 180, ArsMagicaReborn.MODID + ":gravity_well");

		SpellRegistry.registerSpellComponent("swift_swim", getComponentTexture("SwiftSwim"), SkillPoint.SKILL_POINT_1, new SwiftSwim(), SkillDefs.TREE_DEFENSE, 177, 200, ArsMagicaReborn.MODID + ":haste");
		SpellRegistry.registerSpellComponent("repel", getComponentTexture("Repel"), SkillPoint.SKILL_POINT_2, new Repel(), SkillDefs.TREE_DEFENSE, 132, 200, ArsMagicaReborn.MODID + ":slow");

		SpellRegistry.registerSpellComponent("levitate", getComponentTexture("Levitate"), SkillPoint.SKILL_POINT_2, new Levitation(), SkillDefs.TREE_DEFENSE, 222, 225, ArsMagicaReborn.MODID + ":gravity_well");
		SpellRegistry.registerSpellComponent("mana_drain", getComponentTexture("ManaDrain"), SkillPoint.SKILL_POINT_2, new ManaDrain(), SkillDefs.TREE_DEFENSE, 312, 225, ArsMagicaReborn.MODID + ":life_drain");
		SpellRegistry.registerSpellShape("zone", getShapeTexture("Zone"), SkillPoint.SKILL_POINT_3, new Zone(), SkillDefs.TREE_DEFENSE, 357, 225, ArsMagicaReborn.MODID + ":dispel");

		SpellRegistry.registerSpellShape("wall", getShapeTexture("Wall"), SkillPoint.SKILL_POINT_2, new Wall(), SkillDefs.TREE_DEFENSE, 87, 200, ArsMagicaReborn.MODID + ":repel");
		SpellRegistry.registerSpellComponent("accelerate", getComponentTexture("Accelerate"), SkillPoint.SKILL_POINT_2, new Accelerate(), SkillDefs.TREE_DEFENSE, 177, 245, ArsMagicaReborn.MODID + ":swift_swim");
		SpellRegistry.registerSpellComponent("entangle", getComponentTexture("Entangle"), SkillPoint.SKILL_POINT_2, new Entangle(), SkillDefs.TREE_DEFENSE, 132, 245, ArsMagicaReborn.MODID + ":repel");
		SpellRegistry.registerSpellComponent("appropriation", getComponentTexture("Appropriation"), SkillPoint.SKILL_POINT_3, new Appropriation(), SkillDefs.TREE_DEFENSE, 87, 245, ArsMagicaReborn.MODID + ":entangle");

		SpellRegistry.registerSpellComponent("flight", getComponentTexture("Flight"), SkillPoint.SKILL_POINT_3, new Flight(), SkillDefs.TREE_DEFENSE, 222, 270, ArsMagicaReborn.MODID + ":levitate");
		SpellRegistry.registerSpellComponent("shield", getComponentTexture("Shield"), SkillPoint.SKILL_POINT_1, new Shield(), SkillDefs.TREE_DEFENSE, 357, 270, ArsMagicaReborn.MODID + ":zone");

		SpellRegistry.registerSpellShape("contingency_health", getShapeTexture("Contingency_Health"), SkillPoint.SKILL_POINT_3, new Contingency_Health(), SkillDefs.TREE_DEFENSE, 402, 270, ArsMagicaReborn.MODID + ":shield");

		SpellRegistry.registerSpellShape("rune", getShapeTexture("Rune"), SkillPoint.SKILL_POINT_2, new Rune(), SkillDefs.TREE_DEFENSE, 157, 315, ArsMagicaReborn.MODID + ":accelerate", ArsMagicaReborn.MODID + ":entangle");

		SpellRegistry.registerSpellModifier("rune_procs", getModifierTexture("RuneProcs"), SkillPoint.SKILL_POINT_2, new RuneProcs(), SkillDefs.TREE_DEFENSE, 157, 360, ArsMagicaReborn.MODID + ":rune");

		SpellRegistry.registerSpellModifier("speed", getModifierTexture("Speed"), SkillPoint.SKILL_POINT_3, new Speed(), SkillDefs.TREE_DEFENSE, 202, 315, ArsMagicaReborn.MODID + ":accelerate", ArsMagicaReborn.MODID + ":flight");

		SpellRegistry.registerSpellComponent("reflect", getComponentTexture("Reflect"), SkillPoint.SKILL_POINT_3, new Reflect(), SkillDefs.TREE_DEFENSE, 357, 315, ArsMagicaReborn.MODID + ":shield");
		SpellRegistry.registerSpellComponent("chrono_anchor", getComponentTexture("ChronoAnchor"), SkillPoint.SKILL_POINT_3, new ChronoAnchor(), SkillDefs.TREE_DEFENSE, 312, 315, ArsMagicaReborn.MODID + ":reflect");

		SpellRegistry.registerSpellModifier("duration", getModifierTexture("Duration"), SkillPoint.SKILL_POINT_3, new Duration(), SkillDefs.TREE_DEFENSE, 312, 360, ArsMagicaReborn.MODID + ":chrono_anchor");

		SpellRegistry.registerSpellComponent("absorption", getComponentTexture("Absorption"), SkillPoint.SKILL_POINT_3, new Absorption(), SkillDefs.TREE_DEFENSE, 312, 270, ArsMagicaReborn.MODID + ":shield");
		
		SpellRegistry.registerSpellComponent("mana_link", getComponentTexture("ManaLink"), SkillPoint.SILVER_POINT, new ManaLink(), SkillDefs.TREE_DEFENSE, 30, 45);
		SpellRegistry.registerSpellComponent("mana_shield", getComponentTexture("ManaShield"), SkillPoint.SILVER_POINT, new ManaShield(), SkillDefs.TREE_DEFENSE, 30, 90);
		SpellRegistry.registerSpellModifier("buff_power", getModifierTexture("BuffPower"), SkillPoint.SILVER_POINT, new BuffPower(), SkillDefs.TREE_DEFENSE, 30, 135);
		
	}
	
	public static void handleUtilityTree () {
		//utility tree
		SpellRegistry.registerSpellShape("touch", getShapeTexture("touch"), SkillPoint.SKILL_POINT_1, new Touch(), SkillDefs.TREE_UTILITY, 275, 75);

		SpellRegistry.registerSpellComponent("dig", getComponentTexture("dig"), SkillPoint.SKILL_POINT_1, new Dig(), SkillDefs.TREE_UTILITY, 275, 120, ArsMagicaReborn.MODID + ":touch");
		SpellRegistry.registerSpellComponent("wizards_autumn", getComponentTexture("wizardsautumn"), SkillPoint.SKILL_POINT_1, new WizardsAutumn(), SkillDefs.TREE_UTILITY, 315, 120, ArsMagicaReborn.MODID + ":dig");
		SpellRegistry.registerSpellModifier("target_non_solid", getModifierTexture("targetnonsolid"), SkillPoint.SKILL_POINT_1, new TargetNonSolidBlocks(), SkillDefs.TREE_UTILITY, 230, 75, ArsMagicaReborn.MODID + ":touch");

		SpellRegistry.registerSpellComponent("place_block", getComponentTexture("placeblock"), SkillPoint.SKILL_POINT_1, new PlaceBlock(), SkillDefs.TREE_UTILITY, 185, 93, ArsMagicaReborn.MODID + ":dig");
		SpellRegistry.registerSpellModifier("feather_touch", getModifierTexture("feathertouch"), SkillPoint.SKILL_POINT_1, new FeatherTouch(), SkillDefs.TREE_UTILITY, 230, 137, ArsMagicaReborn.MODID + ":dig");
		SpellRegistry.registerSpellModifier("mining_power", getModifierTexture("miningpower"), SkillPoint.SKILL_POINT_2, new MiningPower(), SkillDefs.TREE_UTILITY, 185, 137, ArsMagicaReborn.MODID + ":feather_touch");

		SpellRegistry.registerSpellComponent("light", getComponentTexture("light"), SkillPoint.SKILL_POINT_1, new Light(), SkillDefs.TREE_UTILITY, 275, 165, ArsMagicaReborn.MODID + ":dig");
		SpellRegistry.registerSpellComponent("night_vision", getComponentTexture("nightvision"), SkillPoint.SKILL_POINT_1, new NightVision(), SkillDefs.TREE_UTILITY, 185, 165, ArsMagicaReborn.MODID + ":light");

		SpellRegistry.registerSpellShape("binding", getShapeTexture("binding"), SkillPoint.SKILL_POINT_1, new Binding(), SkillDefs.TREE_UTILITY, 275, 210, ArsMagicaReborn.MODID + ":light");
		SpellRegistry.registerSpellComponent("disarm", getComponentTexture("disarm"), SkillPoint.SKILL_POINT_1, new Disarm(), SkillDefs.TREE_UTILITY, 230, 210, ArsMagicaReborn.MODID + ":binding");
		SpellRegistry.registerSpellComponent("charm", getComponentTexture("charm"), SkillPoint.SKILL_POINT_1, new Charm(), SkillDefs.TREE_UTILITY, 315, 235, ArsMagicaReborn.MODID + ":binding");
		SpellRegistry.registerSpellComponent("true_sight", getComponentTexture("truesight"), SkillPoint.SKILL_POINT_1, new TrueSight(), SkillDefs.TREE_UTILITY, 185, 210, ArsMagicaReborn.MODID + ":night_vision");

		SpellRegistry.registerSpellModifier("lunar", getModifierTexture("lunar"), SkillPoint.SKILL_POINT_3, new Lunar(), SkillDefs.TREE_UTILITY, 145, 210, ArsMagicaReborn.MODID + ":true_sight");

		SpellRegistry.registerSpellComponent("harvest_plants", getComponentTexture("harvestplants"), SkillPoint.SKILL_POINT_2, new HarvestPlants(), SkillDefs.TREE_UTILITY, 365, 120, ArsMagicaReborn.MODID + ":binding");
		SpellRegistry.registerSpellComponent("plow", getComponentTexture("plow"), SkillPoint.SKILL_POINT_1, new Plow(), SkillDefs.TREE_UTILITY, 365, 165, ArsMagicaReborn.MODID + ":binding");
		SpellRegistry.registerSpellComponent("plant", getComponentTexture("plant"), SkillPoint.SKILL_POINT_1, new Plant(), SkillDefs.TREE_UTILITY, 365, 210, ArsMagicaReborn.MODID + ":binding");
		SpellRegistry.registerSpellComponent("create_water", getComponentTexture("createwater"), SkillPoint.SKILL_POINT_2, new CreateWater(), SkillDefs.TREE_UTILITY, 365, 255, ArsMagicaReborn.MODID + ":binding");
		SpellRegistry.registerSpellComponent("drought", getComponentTexture("drought"), SkillPoint.SKILL_POINT_2, new Drought(), SkillDefs.TREE_UTILITY, 365, 300, ArsMagicaReborn.MODID + ":binding");

		SpellRegistry.registerSpellComponent("banish_rain", getComponentTexture("banishrain"), SkillPoint.SKILL_POINT_2, new BanishRain(), SkillDefs.TREE_UTILITY, 365, 345, ArsMagicaReborn.MODID + ":drought");
		SpellRegistry.registerSpellComponent("water_breathing", getComponentTexture("waterbreathing"), SkillPoint.SKILL_POINT_1, new WaterBreathing(), SkillDefs.TREE_UTILITY, 410, 345, ArsMagicaReborn.MODID + ":drought");

		SpellRegistry.registerSpellComponent("grow", getComponentTexture("grow"), SkillPoint.SKILL_POINT_3, new Grow(), SkillDefs.TREE_UTILITY, 410, 210, ArsMagicaReborn.MODID + ":drought", ArsMagicaReborn.MODID + ":create_water", ArsMagicaReborn.MODID + ":plant", ArsMagicaReborn.MODID + ":plow", ArsMagicaReborn.MODID + ":harvest_plants");

		SpellRegistry.registerSpellShape("chain", getShapeTexture("chain"), SkillPoint.SKILL_POINT_3, new Chain(), SkillDefs.TREE_UTILITY, 455, 210, ArsMagicaReborn.MODID + ":grow");

		SpellRegistry.registerSpellComponent("rift", getComponentTexture("rift"), SkillPoint.SKILL_POINT_2, new Rift(), SkillDefs.TREE_UTILITY, 275, 255, ArsMagicaReborn.MODID + ":binding");
		SpellRegistry.registerSpellComponent("invisibility", getComponentTexture("invisibility"), SkillPoint.SKILL_POINT_2, new Invisiblity(), SkillDefs.TREE_UTILITY, 185, 255, ArsMagicaReborn.MODID + ":true_sight");

		SpellRegistry.registerSpellComponent("random_teleport", getComponentTexture("randomteleport"), SkillPoint.SKILL_POINT_1, new RandomTeleport(), SkillDefs.TREE_UTILITY, 185, 300, ArsMagicaReborn.MODID + ":invisibility");
		SpellRegistry.registerSpellComponent("attract", getComponentTexture("attract"), SkillPoint.SKILL_POINT_2, new Attract(), SkillDefs.TREE_UTILITY, 245, 300, ArsMagicaReborn.MODID + ":rift");
		SpellRegistry.registerSpellComponent("telekinesis", getComponentTexture("telekinesis"), SkillPoint.SKILL_POINT_2, new Telekinesis(), SkillDefs.TREE_UTILITY, 305, 300, ArsMagicaReborn.MODID + ":rift");

		SpellRegistry.registerSpellComponent("blink", getComponentTexture("blink"), SkillPoint.SKILL_POINT_2, new Blink(), SkillDefs.TREE_UTILITY, 185, 345, ArsMagicaReborn.MODID + ":random_teleport");
		SpellRegistry.registerSpellModifier("range", getModifierTexture("range"), SkillPoint.SKILL_POINT_3, new Range(), SkillDefs.TREE_UTILITY, 140, 345, ArsMagicaReborn.MODID + ":blink");
		SpellRegistry.registerSpellShape("channel", getShapeTexture("channel"), SkillPoint.SKILL_POINT_2, new Channel(), SkillDefs.TREE_UTILITY, 275, 345, ArsMagicaReborn.MODID + ":attract", ArsMagicaReborn.MODID + ":telekinesis");
		SpellRegistry.registerSpellShape("toggle", getShapeTexture("toggle"), SkillPoint.SKILL_POINT_3, new Toggle(), SkillDefs.TREE_UTILITY, 315, 345, ArsMagicaReborn.MODID + ":channel");

		SpellRegistry.registerSpellModifier("radius", getModifierTexture("Radius"), SkillPoint.SKILL_POINT_3, new Radius(), SkillDefs.TREE_UTILITY, 275, 390, ArsMagicaReborn.MODID + ":radius");
		SpellRegistry.registerSpellComponent("transplace", getComponentTexture("transplace"), SkillPoint.SKILL_POINT_1, new Transplace(), SkillDefs.TREE_UTILITY, 185, 390, ArsMagicaReborn.MODID + ":blink");

		SpellRegistry.registerSpellComponent("mark", getComponentTexture("mark"), SkillPoint.SKILL_POINT_2, new Mark(), SkillDefs.TREE_UTILITY, 155, 435, ArsMagicaReborn.MODID + ":transplace");
		SpellRegistry.registerSpellComponent("recall", getComponentTexture("recall"), SkillPoint.SKILL_POINT_2, new Recall(), SkillDefs.TREE_UTILITY, 215, 435, ArsMagicaReborn.MODID + ":transplace");

		SpellRegistry.registerSpellComponent("divine_intervention", getComponentTexture("divineintervention"), SkillPoint.SKILL_POINT_3, new DivineIntervention(), SkillDefs.TREE_UTILITY, 172, 480, ArsMagicaReborn.MODID + ":recall", ArsMagicaReborn.MODID + ":mark");
		SpellRegistry.registerSpellComponent("ender_intervention", getComponentTexture("enderintervention"), SkillPoint.SKILL_POINT_3, new EnderIntervention(), SkillDefs.TREE_UTILITY, 198, 480, ArsMagicaReborn.MODID + ":recall", ArsMagicaReborn.MODID + ":mark");

		SpellRegistry.registerSpellShape("contingency_death", getShapeTexture("contingency_death"), SkillPoint.SKILL_POINT_3, new Contingency_Death(), SkillDefs.TREE_UTILITY, 198, 524, ArsMagicaReborn.MODID + ":ender_intervention");

		SpellRegistry.registerSpellComponent("daylight", getComponentTexture("daylight"), SkillPoint.SILVER_POINT, new Daylight(), SkillDefs.TREE_UTILITY, 75, 45);
		SpellRegistry.registerSpellComponent("moonrise", getComponentTexture("moonrise"), SkillPoint.SILVER_POINT, new Moonrise(), SkillDefs.TREE_UTILITY, 75, 90);
		SpellRegistry.registerSpellModifier("prosperity", getModifierTexture("Prosperity"), SkillPoint.SILVER_POINT, new Prosperity(), SkillDefs.TREE_UTILITY, 75, 135);
	}
	
	private static ResourceLocation getComponentTexture(String name) {
		return new ResourceLocation(ArsMagicaReborn.MODID, "items/spells/components/" + name);
	}
	
	private static ResourceLocation getShapeTexture(String name) {
		return new ResourceLocation(ArsMagicaReborn.MODID, "items/spells/shapes/" + name);		
	}
	
	private static ResourceLocation getModifierTexture(String name) {
		return new ResourceLocation(ArsMagicaReborn.MODID, "items/spells/modifiers/" + name);		
	}
}
