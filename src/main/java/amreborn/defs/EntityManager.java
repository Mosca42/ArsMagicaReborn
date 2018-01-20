package amreborn.defs;

import java.util.Set;

import amreborn.ArsMagicaReborn;
import amreborn.LogHelper;
import amreborn.bosses.EntityAirGuardian;
import amreborn.bosses.EntityArcaneGuardian;
import amreborn.bosses.EntityEarthGuardian;
import amreborn.bosses.EntityEnderGuardian;
import amreborn.bosses.EntityFireGuardian;
import amreborn.bosses.EntityLifeGuardian;
import amreborn.bosses.EntityLightningGuardian;
import amreborn.bosses.EntityNatureGuardian;
import amreborn.bosses.EntityWaterGuardian;
import amreborn.bosses.EntityWinterGuardian;
import amreborn.bosses.renderers.RenderAirGuardian;
import amreborn.bosses.renderers.RenderArcaneGuardian;
import amreborn.bosses.renderers.RenderEarthGuardian;
import amreborn.bosses.renderers.RenderEnderGuardian;
import amreborn.bosses.renderers.RenderFireGuardian;
import amreborn.bosses.renderers.RenderIceGuardian;
import amreborn.bosses.renderers.RenderLifeGuardian;
import amreborn.bosses.renderers.RenderLightningGuardian;
import amreborn.bosses.renderers.RenderPlantGuardian;
import amreborn.bosses.renderers.RenderThrownRock;
import amreborn.bosses.renderers.RenderThrownSickle;
import amreborn.bosses.renderers.RenderWaterGuardian;
import amreborn.bosses.renderers.RenderWinterGuardianArm;
import amreborn.entity.EntityAirSled;
import amreborn.entity.EntityBoundArrow;
import amreborn.entity.EntityBroom;
import amreborn.entity.EntityDarkMage;
import amreborn.entity.EntityDarkling;
import amreborn.entity.EntityDryad;
import amreborn.entity.EntityEarthElemental;
import amreborn.entity.EntityFireElemental;
import amreborn.entity.EntityFlicker;
import amreborn.entity.EntityHecate;
import amreborn.entity.EntityHellCow;
import amreborn.entity.EntityLightMage;
import amreborn.entity.EntityManaCreeper;
import amreborn.entity.EntityManaElemental;
import amreborn.entity.EntityManaVortex;
import amreborn.entity.EntityRiftStorage;
import amreborn.entity.EntityShadowHelper;
import amreborn.entity.EntityShockwave;
import amreborn.entity.EntitySpellEffect;
import amreborn.entity.EntitySpellProjectile;
import amreborn.entity.EntityThrownRock;
import amreborn.entity.EntityThrownSickle;
import amreborn.entity.EntityWaterElemental;
import amreborn.entity.EntityWhirlwind;
import amreborn.entity.EntityWinterGuardianArm;
import amreborn.entity.render.RenderAirSled;
import amreborn.entity.render.RenderBoundArrow;
import amreborn.entity.render.RenderBroom;
import amreborn.entity.render.RenderDarkMage;
import amreborn.entity.render.RenderDarkling;
import amreborn.entity.render.RenderDryad;
import amreborn.entity.render.RenderEarthElemental;
import amreborn.entity.render.RenderFireElemental;
import amreborn.entity.render.RenderFlicker;
import amreborn.entity.render.RenderHecate;
import amreborn.entity.render.RenderHellCow;
import amreborn.entity.render.RenderHidden;
import amreborn.entity.render.RenderLightMage;
import amreborn.entity.render.RenderManaCreeper;
import amreborn.entity.render.RenderManaElemental;
import amreborn.entity.render.RenderManaVortex;
import amreborn.entity.render.RenderRiftStorage;
import amreborn.entity.render.RenderShadowHelper;
import amreborn.entity.render.RenderShockwave;
import amreborn.entity.render.RenderSpellProjectile;
import amreborn.entity.render.RenderWaterElemental;
import amreborn.utils.RenderFactory;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class EntityManager {

	public static final EntityManager instance = new EntityManager();

	private EntityManager() {

	}

	public void registerEntities() {
		EntityRegistry.registerModEntity(new ResourceLocation(ArsMagicaReborn.MODID, "SpellProjectile"), EntitySpellProjectile.class, "SpellProjectile", 0, ArsMagicaReborn.instance, 64, 2, true);
		EntityRegistry.registerModEntity(new ResourceLocation(ArsMagicaReborn.MODID, "RiftStorage"), EntityRiftStorage.class, "RiftStorage", 1, ArsMagicaReborn.instance, 64, 2, false);
		EntityRegistry.registerModEntity(new ResourceLocation(ArsMagicaReborn.MODID, "SpellEffect"), EntitySpellEffect.class, "SpellEffect", 2, ArsMagicaReborn.instance, 64, 2, true);
		EntityRegistry.registerModEntity(new ResourceLocation(ArsMagicaReborn.MODID, "ThrownRock"), EntityThrownRock.class, "ThrownRock", 3, ArsMagicaReborn.instance, 64, 2, true);
		EntityRegistry.registerModEntity(new ResourceLocation(ArsMagicaReborn.MODID, "BoundArrow"), EntityBoundArrow.class, "BoundArrow", 4, ArsMagicaReborn.instance, 64, 2, true);
		EntityRegistry.registerModEntity(new ResourceLocation(ArsMagicaReborn.MODID, "Darkling"), EntityDarkling.class, "Darkling", 5, ArsMagicaReborn.instance, 64, 2, true);
		EntityRegistry.registerModEntity(new ResourceLocation(ArsMagicaReborn.MODID, "DarkMage"), EntityDarkMage.class, "DarkMage", 6, ArsMagicaReborn.instance, 64, 2, true, 0xaa00ff, 0x660066);
		EntityRegistry.registerModEntity(new ResourceLocation(ArsMagicaReborn.MODID, "Dryad"), EntityDryad.class, "Dryad", 7, ArsMagicaReborn.instance, 64, 2, true, 0x00ff00, 0x34e122);
		EntityRegistry.registerModEntity(new ResourceLocation(ArsMagicaReborn.MODID, "EarthElemental"), EntityEarthElemental.class, "EarthElemental", 8, ArsMagicaReborn.instance, 64, 2, true, 0x61330b, 0x00ff00);
		EntityRegistry.registerModEntity(new ResourceLocation(ArsMagicaReborn.MODID, "FireElemental"), EntityFireElemental.class, "FireElemental", 9, ArsMagicaReborn.instance, 64, 2, true, 0xef260b, 0xff0000);
		EntityRegistry.registerModEntity(new ResourceLocation(ArsMagicaReborn.MODID, "LightMage"), EntityLightMage.class, "LightMage", 10, ArsMagicaReborn.instance, 64, 2, true, 0xaa00ff, 0xff00ff);
		EntityRegistry.registerModEntity(new ResourceLocation(ArsMagicaReborn.MODID, "ManaElemental"), EntityManaElemental.class, "ManaElemental", 11, ArsMagicaReborn.instance, 64, 2, true, 0xcccccc, 0xb935cd);
		EntityRegistry.registerModEntity(new ResourceLocation(ArsMagicaReborn.MODID, "ManaVortex"), EntityManaVortex.class, "ManaVortex", 12, ArsMagicaReborn.instance, 64, 2, true);
		EntityRegistry.registerModEntity(new ResourceLocation(ArsMagicaReborn.MODID, "Shockwave"), EntityShockwave.class, "Shockwave", 13, ArsMagicaReborn.instance, 64, 2, true);
		EntityRegistry.registerModEntity(new ResourceLocation(ArsMagicaReborn.MODID, "ThrownSickle"), EntityThrownSickle.class, "ThrownSickle", 14, ArsMagicaReborn.instance, 64, 2, true);
		EntityRegistry.registerModEntity(new ResourceLocation(ArsMagicaReborn.MODID, "Whirlwind"), EntityWhirlwind.class, "Whirlwind", 15, ArsMagicaReborn.instance, 64, 2, true);
		EntityRegistry.registerModEntity(new ResourceLocation(ArsMagicaReborn.MODID, "WinterGuardianArm"), EntityWinterGuardianArm.class, "WinterGuardianArm", 16, ArsMagicaReborn.instance, 64, 2, true);

		EntityRegistry.registerModEntity(new ResourceLocation(ArsMagicaReborn.MODID, "AirGuardian"), EntityAirGuardian.class, "AirGuardian", 17, ArsMagicaReborn.instance, 64, 2, true, 0xFFFFFF, 0xFFCC00);
		EntityRegistry.registerModEntity(new ResourceLocation(ArsMagicaReborn.MODID, "ArcaneGuardian"), EntityArcaneGuardian.class, "ArcaneGuardian", 18, ArsMagicaReborn.instance, 64, 2, true, 0x999999, 0xcc00cc);
		EntityRegistry.registerModEntity(new ResourceLocation(ArsMagicaReborn.MODID, "EarthGuardian"), EntityEarthGuardian.class, "EarthGuardian", 19, ArsMagicaReborn.instance, 64, 2, true, 0x663300, 0x339900);
		EntityRegistry.registerModEntity(new ResourceLocation(ArsMagicaReborn.MODID, "EnderGuardian"), EntityEnderGuardian.class, "EnderGuardian", 20, ArsMagicaReborn.instance, 64, 2, true, 0x000000, 0x6633);
		EntityRegistry.registerModEntity(new ResourceLocation(ArsMagicaReborn.MODID, "FireGuardian"), EntityFireGuardian.class, "FireGuardian", 21, ArsMagicaReborn.instance, 64, 2, true, 0xFFFFFF, 0xFF0000);
		EntityRegistry.registerModEntity(new ResourceLocation(ArsMagicaReborn.MODID, "LifeGuardian"), EntityLifeGuardian.class, "LifeGuardian", 22, ArsMagicaReborn.instance, 64, 2, true, 0x00E6FF, 0xFFE600);
		EntityRegistry.registerModEntity(new ResourceLocation(ArsMagicaReborn.MODID, "LightningGuardian"), EntityLightningGuardian.class, "LightningGuardian", 23, ArsMagicaReborn.instance, 64, 2, true, 0xFFE600, 0x00C4FF);
		EntityRegistry.registerModEntity(new ResourceLocation(ArsMagicaReborn.MODID, "NatureGuardian"), EntityNatureGuardian.class, "NatureGuardian", 24, ArsMagicaReborn.instance, 64, 2, true, 0x44FF00, 0x307D0F);
		EntityRegistry.registerModEntity(new ResourceLocation(ArsMagicaReborn.MODID, "WaterGuardian"), EntityWaterGuardian.class, "WaterGuardian", 25, ArsMagicaReborn.instance, 64, 2, true, 0x0F387D, 0x0097CE);
		EntityRegistry.registerModEntity(new ResourceLocation(ArsMagicaReborn.MODID, "WinterGuardian"), EntityWinterGuardian.class, "WinterGuardian", 26, ArsMagicaReborn.instance, 64, 2, true, 0x00CEBA, 0x104742);

		EntityRegistry.registerModEntity(new ResourceLocation(ArsMagicaReborn.MODID, "AirSled"), EntityAirSled.class, "AirSled", 27, ArsMagicaReborn.instance, 64, 2, true);
		EntityRegistry.registerModEntity(new ResourceLocation(ArsMagicaReborn.MODID, "Broom"), EntityBroom.class, "Broom", 28, ArsMagicaReborn.instance, 64, 2, true);
		EntityRegistry.registerModEntity(new ResourceLocation(ArsMagicaReborn.MODID, "WaterElemental"), EntityWaterElemental.class, "WaterElemental", 29, ArsMagicaReborn.instance, 64, 2, true, 0x0b5cef, 0x0000ff);
		EntityRegistry.registerModEntity(new ResourceLocation(ArsMagicaReborn.MODID, "ManaCreeper"), EntityManaCreeper.class, "ManaCreeper", 30, ArsMagicaReborn.instance, 64, 2, true, 0x0b5cef, 0xb935cd);
		EntityRegistry.registerModEntity(new ResourceLocation(ArsMagicaReborn.MODID, "Hecate"), EntityHecate.class, "Hecate", 31, ArsMagicaReborn.instance, 64, 2, true, 0xef260b, 0x3f043d);
		EntityRegistry.registerModEntity(new ResourceLocation(ArsMagicaReborn.MODID, "Flicker"), EntityFlicker.class, "Flicker", 32, ArsMagicaReborn.instance, 64, 2, true);
		EntityRegistry.registerModEntity(new ResourceLocation(ArsMagicaReborn.MODID, "HellCow"), EntityHellCow.class, "HellCow", 33, ArsMagicaReborn.instance, 64, 2, true);
		EntityRegistry.registerModEntity(new ResourceLocation(ArsMagicaReborn.MODID, "ShadowHelper"), EntityShadowHelper.class, "ShadowHelper", 34, ArsMagicaReborn.instance, 64, 2, true);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void registerRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(EntityRiftStorage.class, new RenderFactory(RenderRiftStorage.class));
		RenderingRegistry.registerEntityRenderingHandler(EntitySpellProjectile.class, new RenderFactory(RenderSpellProjectile.class));
		RenderingRegistry.registerEntityRenderingHandler(EntitySpellEffect.class, new RenderFactory(RenderHidden.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityThrownRock.class, new RenderFactory(RenderThrownRock.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityBoundArrow.class, new RenderFactory(RenderBoundArrow.class));

		RenderingRegistry.registerEntityRenderingHandler(EntityThrownSickle.class, new RenderFactory(RenderThrownSickle.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityWinterGuardianArm.class, new RenderFactory(RenderWinterGuardianArm.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityAirSled.class, new RenderFactory(RenderAirSled.class));
		// Bosses
		RenderingRegistry.registerEntityRenderingHandler(EntityAirGuardian.class, new RenderFactory(RenderAirGuardian.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityArcaneGuardian.class, new RenderFactory(RenderArcaneGuardian.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityEarthGuardian.class, new RenderFactory(RenderEarthGuardian.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityFireGuardian.class, new RenderFactory(RenderFireGuardian.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityEnderGuardian.class, new RenderFactory(RenderEnderGuardian.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityFireGuardian.class, new RenderFactory(RenderFireGuardian.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityLifeGuardian.class, new RenderFactory(RenderLifeGuardian.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityLightningGuardian.class, new RenderFactory(RenderLightningGuardian.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityNatureGuardian.class, new RenderFactory(RenderPlantGuardian.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityWaterGuardian.class, new RenderFactory(RenderWaterGuardian.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityWinterGuardian.class, new RenderFactory(RenderIceGuardian.class));

		RenderingRegistry.registerEntityRenderingHandler(EntityManaElemental.class, new RenderFactory(RenderManaElemental.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityWaterElemental.class, new RenderFactory(RenderWaterElemental.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityFireElemental.class, new RenderFactory(RenderFireElemental.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityEarthElemental.class, new RenderFactory(RenderEarthElemental.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityManaCreeper.class, new RenderFactory(RenderManaCreeper.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityLightMage.class, new RenderFactory(RenderLightMage.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityDarkMage.class, new RenderFactory(RenderDarkMage.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityManaVortex.class, new RenderFactory(RenderManaVortex.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityHecate.class, new RenderFactory(RenderHecate.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityDryad.class, new RenderFactory(RenderDryad.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityFlicker.class, new RenderFactory(RenderFlicker.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityHellCow.class, new RenderFactory(RenderHellCow.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityDarkling.class, new RenderFactory(RenderDarkling.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityShadowHelper.class, new RenderFactory(RenderShadowHelper.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityBroom.class, new RenderFactory(RenderBroom.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityShockwave.class, new RenderFactory(RenderShockwave.class));
	}

	public void initializeSpawns() {

		// SpawnListEntry wisps = new SpawnListEntry(EntityWisp.class, 1, 1, 1);
		SpawnListEntry manaElementals = new SpawnListEntry(EntityManaElemental.class, ArsMagicaReborn.config.GetManaElementalSpawnRate(), 1, 1);
		SpawnListEntry dryads = new SpawnListEntry(EntityDryad.class, ArsMagicaReborn.config.GetDryadSpawnRate(), 1, 2);
		SpawnListEntry hecates_nonHell = new SpawnListEntry(EntityHecate.class, ArsMagicaReborn.config.GetHecateSpawnRate(), 1, 1);
		SpawnListEntry hecates_hell = new SpawnListEntry(EntityHecate.class, ArsMagicaReborn.config.GetHecateSpawnRate() * 2, 1, 2);
		SpawnListEntry manaCreepers = new SpawnListEntry(EntityManaCreeper.class, ArsMagicaReborn.config.GetManaCreeperSpawnRate(), 1, 1);
		SpawnListEntry lightMages = new SpawnListEntry(EntityLightMage.class, ArsMagicaReborn.config.GetMageSpawnRate(), 1, 3);
		SpawnListEntry darkMages = new SpawnListEntry(EntityDarkMage.class, ArsMagicaReborn.config.GetMageSpawnRate(), 1, 3);
		SpawnListEntry waterElementals = new SpawnListEntry(EntityWaterElemental.class, ArsMagicaReborn.config.GetWaterElementalSpawnRate(), 1, 3);
		SpawnListEntry darklings = new SpawnListEntry(EntityDarkling.class, ArsMagicaReborn.config.GetDarklingSpawnRate(), 4, 8);
		SpawnListEntry earthElementals = new SpawnListEntry(EntityEarthElemental.class, ArsMagicaReborn.config.GetEarthElementalSpawnRate(), 1, 2);
		SpawnListEntry fireElementals = new SpawnListEntry(EntityFireElemental.class, ArsMagicaReborn.config.GetFireElementalSpawnRate(), 1, 1);
		SpawnListEntry flickers = new SpawnListEntry(EntityFlicker.class, ArsMagicaReborn.config.GetFlickerSpawnRate(), 1, 1);

		initSpawnsForBiomeTypes(manaElementals, EnumCreatureType.MONSTER, new Type[] { Type.BEACH, Type.DRY, Type.FOREST, Type.COLD, Type.HILLS, Type.JUNGLE, Type.MAGICAL, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP, Type.WASTELAND }, new Type[] { Type.END, Type.NETHER, Type.MUSHROOM });

		initSpawnsForBiomeTypes(dryads, EnumCreatureType.CREATURE, new Type[] { Type.BEACH, Type.FOREST, Type.MAGICAL, Type.HILLS, Type.JUNGLE, Type.MOUNTAIN, Type.PLAINS }, new Type[] { Type.END, Type.COLD, Type.MUSHROOM, Type.NETHER, Type.WASTELAND, Type.SWAMP, Type.DRY });

		initSpawnsForBiomeTypes(hecates_nonHell, EnumCreatureType.MONSTER, new Type[] { Type.BEACH, Type.DRY, Type.FOREST, Type.COLD, Type.HILLS, Type.JUNGLE, Type.MAGICAL, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP, Type.WASTELAND }, new Type[] { Type.END, Type.NETHER, Type.MUSHROOM });

		initSpawnsForBiomeTypes(hecates_hell, EnumCreatureType.MONSTER, new Type[] { Type.NETHER }, new Type[] { Type.MUSHROOM });

		initSpawnsForBiomeTypes(darklings, EnumCreatureType.MONSTER, new Type[] { Type.NETHER }, new Type[] { Type.MUSHROOM });

		initSpawnsForBiomeTypes(manaCreepers, EnumCreatureType.MONSTER, new Type[] { Type.BEACH, Type.DRY, Type.FOREST, Type.COLD, Type.HILLS, Type.JUNGLE, Type.MAGICAL, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP, Type.WASTELAND }, new Type[] { Type.END, Type.NETHER, Type.MUSHROOM });

		initSpawnsForBiomeTypes(lightMages, EnumCreatureType.MONSTER, new Type[] { Type.BEACH, Type.DRY, Type.FOREST, Type.COLD, Type.HILLS, Type.JUNGLE, Type.MAGICAL, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP, Type.WASTELAND }, new Type[] { Type.END, Type.NETHER, Type.MUSHROOM });

		initSpawnsForBiomeTypes(darkMages, EnumCreatureType.MONSTER, new Type[] { Type.BEACH, Type.DRY, Type.FOREST, Type.COLD, Type.HILLS, Type.JUNGLE, Type.MAGICAL, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP, Type.WASTELAND }, new Type[] { Type.END, Type.NETHER, Type.MUSHROOM });

		initSpawnsForBiomeTypes(waterElementals, EnumCreatureType.MONSTER, new Type[] { Type.WATER }, new Type[] { Type.END, Type.NETHER, Type.MUSHROOM });
		initSpawnsForBiomeTypes(waterElementals, EnumCreatureType.WATER_CREATURE, new Type[] { Type.WATER }, new Type[] { Type.END, Type.NETHER, Type.MUSHROOM });

		initSpawnsForBiomeTypes(earthElementals, EnumCreatureType.MONSTER, new Type[] { Type.HILLS, Type.MOUNTAIN }, new Type[] { Type.MUSHROOM });
		initSpawnsForBiomeTypes(fireElementals, EnumCreatureType.MONSTER, new Type[] { Type.NETHER }, new Type[] { Type.MUSHROOM });


	}

	private void initSpawnsForBiomeTypes(SpawnListEntry spawnListEntry, EnumCreatureType creatureType, Type[] types, Type[] exclusions) {
		if (spawnListEntry.itemWeight == 0) {
			LogHelper.info("Skipping spawn list entry for %s (as type %s), as the weight is set to 0.  This can be changed in config.", spawnListEntry.entityClass.getName(), creatureType.toString());
			return;
		}
		for (Type type : types) {
			initSpawnsForBiomes(BiomeDictionary.getBiomes(type), spawnListEntry, creatureType, exclusions);
		}
	}

	private void initSpawnsForBiomes(Set<Biome> set, SpawnListEntry spawnListEntry, EnumCreatureType creatureType, Type[] exclusions) {
		if (set == null)
			return;
		for (Biome biome : set) {
			if (biomeIsExcluded(biome, exclusions))
				continue;
			if (!biome.getSpawnableList(creatureType).contains(spawnListEntry))
				biome.getSpawnableList(creatureType).add(spawnListEntry);
		}
	}

	private boolean biomeIsExcluded(Biome biome, Type[] exclusions) {

		Set<Type> biomeTypes = BiomeDictionary.getTypes(biome);

		for (Type exclusion : exclusions) {
			for (Type biomeType : biomeTypes) {
				if (biomeType == exclusion)
					return true;
			}
		}
		return false;
	}
}
