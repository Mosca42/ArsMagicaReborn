package amreborn.defs;

import java.util.Map.Entry;

import amreborn.ArsMagicaReborn;
import amreborn.api.SkillPointRegistry;
import amreborn.api.items.ItemFocus;
import amreborn.api.skill.SkillPoint;
import amreborn.armor.AMArmor;
import amreborn.armor.ArsMagicaArmorMaterial;
import amreborn.armor.ItemEarthGuardianArmor;
import amreborn.armor.ItemEnderBoots;
import amreborn.armor.ItemFireGuardianEars;
import amreborn.armor.ItemMageHood;
import amreborn.armor.ItemMagitechGoggles;
import amreborn.armor.ItemWaterGuardianOrbs;
import amreborn.enchantments.AMEnchantmentHelper;
import amreborn.items.ItemAffinityTome;
import amreborn.items.ItemAirSled;
import amreborn.items.ItemArcaneCompendium;
import amreborn.items.ItemArcaneGuardianSpellbook;
import amreborn.items.ItemArsMagica;
import amreborn.items.ItemBindingCatalyst;
import amreborn.items.ItemBoundArrow;
import amreborn.items.ItemBoundAxe;
import amreborn.items.ItemBoundBow;
import amreborn.items.ItemBoundHoe;
import amreborn.items.ItemBoundPickaxe;
import amreborn.items.ItemBoundShield;
import amreborn.items.ItemBoundShovel;
import amreborn.items.ItemBoundSword;
import amreborn.items.ItemCandle;
import amreborn.items.ItemCore;
import amreborn.items.ItemCrystalPhylactery;
import amreborn.items.ItemCrystalWrench;
import amreborn.items.ItemEssence;
import amreborn.items.ItemEssenceBag;
import amreborn.items.ItemFocusCharge;
import amreborn.items.ItemFocusCreature;
import amreborn.items.ItemFocusGreater;
import amreborn.items.ItemFocusItem;
import amreborn.items.ItemFocusLesser;
import amreborn.items.ItemFocusMana;
import amreborn.items.ItemFocusMob;
import amreborn.items.ItemFocusPlayer;
import amreborn.items.ItemFocusStandard;
import amreborn.items.ItemHellCowHorn;
import amreborn.items.ItemInfinityOrb;
import amreborn.items.ItemInscriptionTableUpgrade;
import amreborn.items.ItemJournal;
import amreborn.items.ItemLifeWard;
import amreborn.items.ItemLightningCharm;
import amreborn.items.ItemLiquidEssenceBottle;
import amreborn.items.ItemLostJournal;
import amreborn.items.ItemMagicBroom;
import amreborn.items.ItemManaCake;
import amreborn.items.ItemManaMartini;
import amreborn.items.ItemManaPotion;
import amreborn.items.ItemManaPotionBundle;
import amreborn.items.ItemNatureGuardianSickle;
import amreborn.items.ItemOre;
import amreborn.items.ItemRune;
import amreborn.items.ItemRuneBag;
import amreborn.items.ItemSpellBook;
import amreborn.items.ItemSpellComponent;
import amreborn.items.ItemSpellStaff;
import amreborn.items.ItemTeleporterKey;
import amreborn.items.ItemWinterGuardianArm;
import amreborn.items.SpellBase;
import amreborn.items.colorizers.CrystalPhylacteryColorizer;
import amreborn.items.colorizers.LostJournalColorizer;
import amreborn.items.colorizers.SpellBookColorizer;
import amreborn.items.rendering.AffinityRenderer;
import amreborn.items.rendering.CrystalWrenchRenderer;
import amreborn.items.rendering.DefaultWithMetaRenderer;
import amreborn.items.rendering.IgnoreMetadataRenderer;
import amreborn.items.rendering.ManaPotionBundleRenderer;
import amreborn.items.rendering.SpellRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDefs {
	
	public static final ToolMaterial BOUND = EnumHelper.addToolMaterial("BOUND", 3, 1561, 8.0F, 3.0F, 10);
	
	private static final ArmorMaterial MAGITECH = EnumHelper.addArmorMaterial("magitech", ArsMagicaReborn.MODID + ":magitech", 5, new int[]{1, 2, 3, 1}, 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F);
	private static final ArmorMaterial ENDER = EnumHelper.addArmorMaterial("ender", ArsMagicaReborn.MODID + ":ender", 7, new int[]{1, 3, 5, 2}, 25, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0.0F);
	private static final ArmorMaterial MAGE = EnumHelper.addArmorMaterial("mage", ArsMagicaReborn.MODID + ":mage", 7, new int[]{1, 3, 5, 2}, 25, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0.0F);
	private static final ArmorMaterial BATTLEMAGE = EnumHelper.addArmorMaterial("battlemage", ArsMagicaReborn.MODID + ":battlemage", 15, new int[]{2, 5, 6, 2}, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F);
	
	public static final ItemArsMagica spellParchment = new ItemArsMagica().registerAndName("spell_parchment");
	public static final ItemArsMagica affinityTome = new ItemAffinityTome().registerAndName("tome");
	public static final ItemArsMagica itemOre = new ItemOre().registerAndName("item_ore");
	public static final ItemArsMagica essence = new ItemEssence().registerAndName("essence");
	public static final ItemArsMagica rune = new ItemRune().registerAndName("rune");
	
	public static final ItemFocus mobFocus = new ItemFocusMob().registerAndName("mob_focus");
	public static final ItemFocus lesserFocus = new ItemFocusLesser().registerAndName("lesser_focus");
	public static final ItemFocus standardFocus = new ItemFocusStandard().registerAndName("standard_focus");
	public static final ItemFocus manaFocus = new ItemFocusMana().registerAndName("mana_focus");
	public static final ItemFocus greaterFocus = new ItemFocusGreater().registerAndName("greater_focus");
	public static final ItemFocus chargeFocus = new ItemFocusCharge().registerAndName("charge_focus");
	public static final ItemFocus itemFocus = new ItemFocusItem().registerAndName("item_focus");
	public static final ItemFocus playerFocus = new ItemFocusPlayer().registerAndName("player_focus");
	public static final ItemFocus creatureFocus = new ItemFocusCreature().registerAndName("creature_focus");
	public static final Item arcaneCompendium = new ItemArcaneCompendium().registerAndName("arcane_compendium");
	public static final Item crystalWrench = new ItemCrystalWrench().registerAndName("crystal_wrench");
	public static final Item magitechGoggles = new ItemMagitechGoggles(MAGITECH, 0).registerAndName("magitech_goggles");
	
	// PlaceHolder items
	public static final ItemArsMagica spell_component = new ItemSpellComponent().registerAndName("spell_component");
	public static final Item etherium = new ItemArsMagica().registerAndName("etherium").setCreativeTab(null);
	
	public static final ItemArsMagica blankRune = new ItemArsMagica().registerAndName("blank_rune");
	public static final ItemArsMagica spellStaffMagitech = new ItemSpellStaff(0, -1).registerAndName("spell_staff_magitech");
	public static final ItemArsMagica evilBook = new ItemArsMagica().registerAndName("evil_book");
	public static final ItemArsMagica wardingCandle = new ItemCandle().registerAndName("warding_candle");
	public static final ItemArsMagica liquidEssenceBottle = new ItemLiquidEssenceBottle().registerAndName("liquid_essence_bottle");
	public static final ItemArsMagica bindingCatalyst = new ItemBindingCatalyst().registerAndName("binding_catalyst");
	public static final ItemArsMagica inscriptionUpgrade = new ItemInscriptionTableUpgrade().registerAndName("inscription_upgrade");
	public static final ItemArsMagica infinityOrb = new ItemInfinityOrb().registerAndName("infinity_orb");
	public static final ItemSword BoundSword = new ItemBoundSword().registerAndName("bound_sword");
	public static final ItemAxe BoundAxe = new ItemBoundAxe().registerAndName("bound_axe");
	public static final ItemPickaxe BoundPickaxe = new ItemBoundPickaxe().registerAndName("bound_pickaxe");
	public static final ItemSpade BoundShovel = new ItemBoundShovel().registerAndName("bound_shovel");
	public static final ItemHoe BoundHoe = new ItemBoundHoe().registerAndName("bound_hoe");
	public static final ItemBow BoundBow = new ItemBoundBow().registerAndName("bound_bow");
	public static final ItemShield BoundShield = new ItemBoundShield().registerAndName("bound_shield");
	public static final ItemManaCake manaCake = new ItemManaCake().registerAndName("mana_cake");
	public static final Item woodenLeg = new ItemArsMagica().registerAndName("wooden_leg");

	public static final ItemArrow BoundArrow = new ItemBoundArrow().registerAndName("bound_arrow");

	public static final Item natureScythe = new ItemNatureGuardianSickle().registerAndName("nature_scythe");
	public static final Item winterArm = new ItemWinterGuardianArm().registerAndName("winter_arm");
	public static final Item airSled = new ItemAirSled().registerAndName("air_sled");
	public static final Item arcaneSpellbook = new ItemArcaneGuardianSpellbook().registerAndName("arcane_spellbook");
	public static final Item earthArmor = new ItemEarthGuardianArmor(ArmorMaterial.GOLD, ArsMagicaArmorMaterial.UNIQUE, 0, EntityEquipmentSlot.CHEST).registerAndName("earth_armor");
	public static final Item enderBoots = new ItemEnderBoots(ENDER, ArsMagicaArmorMaterial.UNIQUE, 0, EntityEquipmentSlot.FEET).registerAndName("ender_boots");
	public static final Item fireEars = new ItemFireGuardianEars(ArmorMaterial.GOLD, ArsMagicaArmorMaterial.UNIQUE, 0, EntityEquipmentSlot.HEAD).registerAndName("fire_ears");
	public static final Item lifeWard = new ItemLifeWard().registerAndName("life_ward");
	public static final Item lightningCharm = new ItemLightningCharm().registerAndName("lightning_charm");
	public static final Item waterOrbs = new ItemWaterGuardianOrbs(ArmorMaterial.GOLD, ArsMagicaArmorMaterial.UNIQUE, 0, EntityEquipmentSlot.LEGS).registerAndName("water_orbs");	
	public static final ItemSpellBook spellBook = (ItemSpellBook) new ItemSpellBook().registerAndName("spell_book");
	public static final Item magicBroom = new ItemMagicBroom().registerAndName("magic_broom");
	public static final ItemRuneBag runeBag = (ItemRuneBag) new ItemRuneBag().registerAndName("rune_bag");
	public static final ItemLostJournal lostJournal = new ItemLostJournal().registerAndName("lost_journal");
	public static final ItemCrystalPhylactery crystalPhylactery = (ItemCrystalPhylactery) new ItemCrystalPhylactery().registerAndName("crystal_phylactery");
	public static final Item core = new ItemCore().registerAndName("core");
	public static final Item deficitCrystal = new ItemArsMagica().registerAndName("deficit_crystal");
	public static final Item workbenchUpgrade = new ItemArsMagica().registerAndName("workbench_upgrade");
	public static final Item lesserManaPotion = new ItemManaPotion().registerAndName("lesser_mana_potion");
	public static final Item standardManaPotion = new ItemManaPotion().registerAndName("standard_mana_potion");
	public static final Item greaterManaPotion = new ItemManaPotion().registerAndName("greater_mana_potion");
	public static final Item epicManaPotion = new ItemManaPotion().registerAndName("epic_mana_potion");
	public static final Item legendaryManaPotion = new ItemManaPotion().registerAndName("legendary_mana_potion");
	public static final Item manaPotionBundle = new ItemManaPotionBundle().registerAndName("mana_potion_bundle");
	public static final Item essenceBag = new ItemEssenceBag().registerAndName("essence_bag");
	public static final ItemHellCowHorn hellCowHorn = (ItemHellCowHorn) new ItemHellCowHorn().registerAndName("hell_cow_horn");
	public static final Item journal = new ItemJournal().registerAndName("journal");
	public static final Item manaMartini = new ItemManaMartini().registerAndName("mana_martini");
	
	public static final Item mageHood = new ItemMageHood(MAGE, ArsMagicaArmorMaterial.MAGE, 0, EntityEquipmentSlot.HEAD).registerAndName("helmet_mage");
	public static final Item mageArmor = new AMArmor(MAGE, ArsMagicaArmorMaterial.MAGE, 0, EntityEquipmentSlot.CHEST).registerAndName("chest_mage");
	public static final Item mageLeggings = new AMArmor(MAGE, ArsMagicaArmorMaterial.MAGE, 0, EntityEquipmentSlot.LEGS).registerAndName("legs_mage");
	public static final Item mageBoots = new AMArmor(MAGE, ArsMagicaArmorMaterial.MAGE, 0, EntityEquipmentSlot.FEET).registerAndName("boots_mage");
	public static final Item battlemageHood = new ItemMageHood(BATTLEMAGE, ArsMagicaArmorMaterial.BATTLEMAGE, 0, EntityEquipmentSlot.HEAD).registerAndName("helmet_battlemage");
	public static final Item battlemageArmor = new AMArmor(BATTLEMAGE, ArsMagicaArmorMaterial.BATTLEMAGE, 0, EntityEquipmentSlot.CHEST).registerAndName("chest_battlemage");
	public static final Item battlemageLeggings = new AMArmor(BATTLEMAGE, ArsMagicaArmorMaterial.BATTLEMAGE, 0, EntityEquipmentSlot.LEGS).registerAndName("legs_battlemage");
	public static final Item battlemageBoots = new AMArmor(BATTLEMAGE, ArsMagicaArmorMaterial.BATTLEMAGE, 0, EntityEquipmentSlot.FEET).registerAndName("boots_battlemage");

	
	public static final Item teleporterKey = new ItemTeleporterKey().registerAndName("teleporter_key");
	public static final Item purifiedLiquidEssenceBottle = new ItemPurifiedLiquidEssenceBottle().registerAndName("purified_liquid_essence_bottle");

	
	//public static final ItemArsMagica chalk = new ItemChalk().registerAndName("chalk");

	
	public static ItemStack natureScytheEnchanted;
	public static ItemStack winterArmEnchanted;
	public static ItemStack airSledEnchanted;
	public static ItemStack arcaneSpellBookEnchanted;
	public static ItemStack earthArmorEnchanted;
	public static ItemStack enderBootsEnchanted;
	public static ItemStack fireEarsEnchanted;
	public static ItemStack lifeWardEnchanted;
	public static ItemStack lightningCharmEnchanted;
	public static ItemStack waterOrbsEnchanted;




	public static SpellBase spell = new SpellBase().registerAndName("spell");
	
	@SideOnly(Side.CLIENT)
	public static void initClient () {
		//Focus
		registerTexture(mobFocus);
		registerTexture(lesserFocus);
		registerTexture(standardFocus);
		registerTexture(manaFocus);
		registerTexture(greaterFocus);
		registerTexture(chargeFocus);
		registerTexture(itemFocus);
		registerTexture(playerFocus);
		registerTexture(creatureFocus);
		registerTexture(evilBook);
		
		registerTexture(spellParchment);
		
		registerTexture(arcaneCompendium);
		registerTexture(blankRune);
		registerTexture(spellStaffMagitech);
		
		registerTexture(magitechGoggles);
		registerTexture(etherium);
		registerTexture(BoundSword);
		registerTexture(BoundAxe);
		registerTexture(BoundPickaxe);
		registerTexture(BoundShovel);
		registerTexture(BoundHoe);
		registerTexture(BoundBow);
		registerTexture(BoundShield);
		registerTexture(liquidEssenceBottle);
		
		registerTexture(natureScythe);
		registerTexture(winterArm);
		registerTexture(airSled);
		registerTexture(arcaneSpellbook);
		registerTexture(earthArmor);
		registerTexture(enderBoots);
		registerTexture(fireEars);
		registerTexture(lifeWard);
		registerTexture(lightningCharm);
		registerTexture(waterOrbs);
		
		registerTexture(magicBroom);
		
		registerTexture(manaCake);
		
		registerTexture(spellBook);
		registerTexture(runeBag);
		registerTexture(woodenLeg);
		registerTexture(manaCake);
		registerTexture(lostJournal);
		registerTexture(crystalPhylactery);
		registerTexture(deficitCrystal);
		
		registerTexture(mageArmor);
		registerTexture(mageHood);
		registerTexture(mageLeggings);
		registerTexture(mageBoots);
		
		registerTexture(battlemageArmor);
		registerTexture(battlemageHood);
		registerTexture(battlemageLeggings);
		registerTexture(battlemageBoots);
		
		registerTexture(legendaryManaPotion);
		registerTexture(epicManaPotion);
		registerTexture(greaterManaPotion);
		registerTexture(standardManaPotion);
		registerTexture(lesserManaPotion);
		
		registerTexture(manaMartini);
		registerTexture(hellCowHorn);
		registerTexture(journal);
		registerTexture(essenceBag);
		registerTexture(workbenchUpgrade);
		
		
		//registerTexture(chalk);

		
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new LostJournalColorizer(), lostJournal);
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new CrystalPhylacteryColorizer(), crystalPhylactery);
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new SpellBookColorizer(), spellBook);
		
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
		
		renderItem.getItemModelMesher().register(crystalWrench, new CrystalWrenchRenderer(crystalWrench));
//		Iterator<Affinity> iter = ArsMagicaAPI.getAffinityRegistry().getValues().iterator();
//		int effMeta = 0;
//		for (int i = 0; i < ArsMagicaAPI.getAffinityRegistry().getValues().size(); i++) {
//			if (!iter.hasNext())
//				break;
//			Affinity entry = iter.next();
//			ModelBakery.registerItemVariants(affinityTome, new ModelResourceLocation(ArsMagicaReborn.MODID + ":affinity_tome_" + entry.getName(), "inventory"));
//			renderItem.getItemModelMesher().register(affinityTome, i, new ModelResourceLocation(ArsMagicaReborn.MODID + ":affinity_tome_" + entry.getName(), "inventory"));
//			if (entry.equals(Affinity.NONE)) {
//				continue;
//			}
//			ModelBakery.registerItemVariants(essence, new ModelResourceLocation(ArsMagicaReborn.MODID + ":essence_" + entry.getName(), "inventory"));
//			renderItem.getItemModelMesher().register(essence, effMeta, new ModelResourceLocation(ArsMagicaReborn.MODID + ":essence_" + entry.getName(), "inventory"));
//			effMeta++;
//		}
		renderItem.getItemModelMesher().register(essence, new AffinityRenderer("essence_").addModels(essence));
		renderItem.getItemModelMesher().register(affinityTome, new AffinityRenderer("affinity_tome_").addModels(affinityTome));
		for (int i = 0; i < 16; i++) {
			ModelBakery.registerItemVariants(rune, new ModelResourceLocation(ArsMagicaReborn.MODID + ":rune_" + EnumDyeColor.byDyeDamage(i).getName().toLowerCase(), "inventory"));
			renderItem.getItemModelMesher().register(rune, i, new ModelResourceLocation(ArsMagicaReborn.MODID + ":rune_" + EnumDyeColor.byDyeDamage(i).getName().toLowerCase(), "inventory"));
		}
		for (int i = 0; i < ItemOre.names.length; i++) {
			ModelResourceLocation loc = new ModelResourceLocation(new ResourceLocation(ArsMagicaReborn.MODID, "item_ore_" + ItemOre.names[i]), "inventory");
			ModelBakery.registerItemVariants(itemOre, loc);
			renderItem.getItemModelMesher().register(itemOre, i, loc);
		}
		
		DefaultWithMetaRenderer coreRenderer = new DefaultWithMetaRenderer(new ModelResourceLocation(core.getRegistryName() + "_base", "inventory"));
		for (int i = 0; i < 3; i++) {
			ModelResourceLocation loc = new ModelResourceLocation(core.getRegistryName().toString() + (i == ItemCore.META_BASE_CORE ? "_base" : (i == ItemCore.META_HIGH_CORE ? "_high" : "_pure")), "inventory");
			ModelBakery.registerItemVariants(core, loc);
			coreRenderer.addModel(i, loc);			
		}
		ModelBakery.registerItemVariants(core, new ModelResourceLocation(core.getRegistryName() + "_base", "inventory"));
		renderItem.getItemModelMesher().register(core, coreRenderer);
		
		renderItem.getItemModelMesher().register(manaPotionBundle, new ManaPotionBundleRenderer());
		DefaultWithMetaRenderer crystalPhylacteryRenderer = new DefaultWithMetaRenderer(new ModelResourceLocation(crystalPhylactery.getRegistryName(), "inventory"));
		for(int i = 1; i < 4; i++) {
			ModelResourceLocation loc = new ModelResourceLocation(crystalPhylactery.getRegistryName().toString() + (i == ItemCrystalPhylactery.META_QUARTER ? "_quarter" : (i == ItemCrystalPhylactery.META_HALF ? "_half" : "_full")), "inventory");
			ModelBakery.registerItemVariants(crystalPhylactery, loc);
			crystalPhylacteryRenderer.addModel(i, loc);
		}
		ModelBakery.registerItemVariants(crystalPhylactery, new ModelResourceLocation(crystalPhylactery.getRegistryName(), "inventory"));
		renderItem.getItemModelMesher().register(crystalPhylactery, crystalPhylacteryRenderer);
		
		DefaultWithMetaRenderer catalystRenderer = new DefaultWithMetaRenderer(new ModelResourceLocation(bindingCatalyst.getRegistryName(), "inventory"));
		for (int i = 0; i < 7; i++) {
			ModelResourceLocation loc = new ModelResourceLocation(bindingCatalyst.getRegistryName() + "_" + ItemBindingCatalyst.NAMES[i], "inventory");
			ModelBakery.registerItemVariants(bindingCatalyst, loc);
			catalystRenderer.addModel(i, loc);
		}
		ModelBakery.registerItemVariants(bindingCatalyst, new ModelResourceLocation(bindingCatalyst.getRegistryName(), "inventory"));
		
		DefaultWithMetaRenderer inforbRenderer = new DefaultWithMetaRenderer(new ModelResourceLocation(infinityOrb.getRegistryName(), "inventory"));
		for (Entry<Integer, SkillPoint> entry : SkillPointRegistry.getSkillPointMap().entrySet()) {
			if (entry.getKey().intValue() < 0)
				continue;
			ModelResourceLocation loc = new ModelResourceLocation(infinityOrb.getRegistryName() + "_" + entry.getValue().toString().toLowerCase(), "inventory");
			ModelBakery.registerItemVariants(infinityOrb, loc);
			inforbRenderer.addModel(entry.getKey(), loc);
		}
		renderItem.getItemModelMesher().register(infinityOrb, inforbRenderer);
		
		renderItem.getItemModelMesher().register(bindingCatalyst, catalystRenderer);
		for (int i = 0; i < 3; i++) {
			ModelResourceLocation loc = new ModelResourceLocation(inscriptionUpgrade.getRegistryName() + "_" + (i + 1), "inventory");
			ModelBakery.registerItemVariants(inscriptionUpgrade, loc);
			renderItem.getItemModelMesher().register(inscriptionUpgrade, i, loc);
		}
		renderItem.getItemModelMesher().register(spell, new SpellRenderer());
		
		


	}

	public static void initEnchantedItems(){
		natureScytheEnchanted = AMEnchantmentHelper.soulbindStack(new ItemStack(natureScythe));
		arcaneSpellBookEnchanted = AMEnchantmentHelper.soulbindStack(new ItemStack(arcaneSpellbook));
		winterArmEnchanted = AMEnchantmentHelper.soulbindStack(new ItemStack(winterArm));
		fireEarsEnchanted = AMEnchantmentHelper.soulbindStack(new ItemStack(fireEars));
		earthArmorEnchanted = AMEnchantmentHelper.soulbindStack(new ItemStack(earthArmor));
		airSledEnchanted = AMEnchantmentHelper.soulbindStack(new ItemStack(airSled));
		waterOrbsEnchanted = AMEnchantmentHelper.soulbindStack(new ItemStack(waterOrbs));
		enderBootsEnchanted = AMEnchantmentHelper.soulbindStack(new ItemStack(enderBoots));
		lightningCharmEnchanted = AMEnchantmentHelper.soulbindStack(new ItemStack(lightningCharm));
		lifeWardEnchanted = AMEnchantmentHelper.soulbindStack(new ItemStack(lifeWard));
	}
	
	@SideOnly(Side.CLIENT)
	private static void registerTexture(Item item) {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, new IgnoreMetadataRenderer( new ModelResourceLocation(item.getRegistryName(), "inventory")));
	}
	
	@SideOnly(Side.CLIENT)
	private static void registerTexture(Item item, int meta, String suffix) {
		DefaultWithMetaRenderer renderer = new DefaultWithMetaRenderer(new ModelResourceLocation(item.getRegistryName(), "inventory"));
		renderer.addModel(meta, new ModelResourceLocation(item.getRegistryName().toString() + suffix, "inventory"));
		ModelBakery.registerItemVariants(item, new ModelResourceLocation(item.getRegistryName().toString() + suffix, "inventory"), new ModelResourceLocation(item.getRegistryName(), "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, renderer);
	}
}
