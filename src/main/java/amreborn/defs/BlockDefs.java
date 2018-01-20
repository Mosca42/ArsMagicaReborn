package amreborn.defs;

import java.util.ArrayList;
import java.util.HashMap;

import amreborn.ArsMagicaReborn;
import amreborn.api.math.AMVector3;
import amreborn.blocks.BlockAM;
import amreborn.blocks.BlockAMFlower;
import amreborn.blocks.BlockArcaneDeconstructor;
import amreborn.blocks.BlockArcaneReconstructor;
import amreborn.blocks.BlockArmorInfuser;
import amreborn.blocks.BlockArsMagicaBlock;
import amreborn.blocks.BlockArsMagicaOre;
import amreborn.blocks.BlockCalefactor;
import amreborn.blocks.BlockCandle;
import amreborn.blocks.BlockCraftingAltar;
import amreborn.blocks.BlockDesertNova;
import amreborn.blocks.BlockEssenceConduit;
import amreborn.blocks.BlockEssenceGenerator;
import amreborn.blocks.BlockEssenceRefiner;
import amreborn.blocks.BlockEverstone;
import amreborn.blocks.BlockFrost;
import amreborn.blocks.BlockGroundRuneSpell;
import amreborn.blocks.BlockIllusionBlock;
import amreborn.blocks.BlockInertSpawner;
import amreborn.blocks.BlockInlay;
import amreborn.blocks.BlockInscriptionTable;
import amreborn.blocks.BlockInvisibleUtility;
import amreborn.blocks.BlockLectern;
import amreborn.blocks.BlockLightDecay;
import amreborn.blocks.BlockMageLight;
import amreborn.blocks.BlockMagicWall;
import amreborn.blocks.BlockMagiciansWorkbench;
import amreborn.blocks.BlockManaBattery;
import amreborn.blocks.BlockOcculus;
import amreborn.blocks.BlockOtherworldAura;
import amreborn.blocks.BlockSlipstreamGenerator;
import amreborn.blocks.BlockSummoner;
import amreborn.blocks.BlockTarmaRoot;
import amreborn.blocks.BlockTeleporterAutel;
import amreborn.blocks.BlockVinteumCauldron;
import amreborn.blocks.BlockVinteumTorch;
import amreborn.blocks.BlockWakebloom;
import amreborn.blocks.BlockWitchwoodLeaves;
import amreborn.blocks.BlockWitchwoodLog;
import amreborn.blocks.BlockWitchwoodPlanks;
import amreborn.blocks.BlockWitchwoodSapling;
import amreborn.blocks.BlockWitchwoodSlabsDouble;
import amreborn.blocks.BlockWitchwoodSlabsSimple;
import amreborn.blocks.BlockWitchwoodStairs;
import amreborn.blocks.BlockWizardsChalk;
import amreborn.blocks.colorizers.ManaBatteryColorizer;
import amreborn.blocks.colorizers.MonoColorizer;
import amreborn.items.rendering.IgnoreMetadataRenderer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockDefs {
	
	public static final Block manaBattery = new BlockManaBattery().registerAndName(new ResourceLocation(ArsMagicaReborn.MODID + ":mana_battery"));
	public static final BlockFrost frost = new BlockFrost().registerAndName(new ResourceLocation(ArsMagicaReborn.MODID + ":frost"));
	public static final BlockOcculus occulus = new BlockOcculus().registerAndName(new ResourceLocation(ArsMagicaReborn.MODID + ":occulus"));
	public static final BlockAM magicWall = new BlockMagicWall().registerAndName(new ResourceLocation(ArsMagicaReborn.MODID + ":magic_wall"));
	public static final BlockAM invisibleLight = new BlockLightDecay().registerAndName(new ResourceLocation(ArsMagicaReborn.MODID + ":invisible_light"));
	public static final BlockAM invisibleUtility = new BlockInvisibleUtility().registerAndName(new ResourceLocation(ArsMagicaReborn.MODID + ":invisible_utility"));
	public static final BlockAM ores = new BlockArsMagicaOre().registerAndName(new ResourceLocation(ArsMagicaReborn.MODID + ":ore"));
	public static final BlockAM blocks = new BlockArsMagicaBlock().registerAndName(new ResourceLocation(ArsMagicaReborn.MODID + ":block"));
	public static final BlockAM blockMageTorch = new BlockMageLight().registerAndName(new ResourceLocation(ArsMagicaReborn.MODID + ":block_mage_light"));
	public static final BlockAMFlower desertNova = new BlockDesertNova().registerAndName(new ResourceLocation(ArsMagicaReborn.MODID + ":desert_nova"));
	public static final BlockAMFlower cerublossom = new BlockAMFlower().registerAndName(new ResourceLocation(ArsMagicaReborn.MODID + ":cerublossom"));
	public static final BlockAMFlower wakebloom = new BlockWakebloom().registerAndName(new ResourceLocation(ArsMagicaReborn.MODID + ":wakebloom"));
	public static final BlockAMFlower aum = new BlockAMFlower().registerAndName(new ResourceLocation(ArsMagicaReborn.MODID + ":aum"));
	public static final BlockAMFlower tarmaRoot = new BlockTarmaRoot().registerAndName(new ResourceLocation(ArsMagicaReborn.MODID + ":tarma_root"));
	public static final BlockCraftingAltar craftingAltar = new BlockCraftingAltar().registerAndName(new ResourceLocation(ArsMagicaReborn.MODID + ":crafting_altar"));
	public static final Block wizardChalk = new BlockWizardsChalk().registerAndName(new ResourceLocation(ArsMagicaReborn.MODID + ":wizard_chalk_block"));
	public static final Block obelisk = new BlockEssenceGenerator(BlockEssenceGenerator.NEXUS_STANDARD).registerAndName(new ResourceLocation(ArsMagicaReborn.MODID + ":obelisk"));
	public static final Block celestialPrism = new BlockEssenceGenerator(BlockEssenceGenerator.NEXUS_LIGHT).registerAndName(new ResourceLocation(ArsMagicaReborn.MODID + ":celestial_prism"));
	public static final Block wardingCandle = new BlockCandle().registerAndName(new ResourceLocation(ArsMagicaReborn.MODID + ":warding_candle"));
	public static final Block lectern = new BlockLectern().registerAndName(new ResourceLocation(ArsMagicaReborn.MODID + ":lectern"));
	public static final Block inscriptionTable = new BlockInscriptionTable().registerAndName(new ResourceLocation(ArsMagicaReborn.MODID + ":inscription_table"));
	public static final Block armorImbuer = new BlockArmorInfuser().registerAndName(new ResourceLocation(ArsMagicaReborn.MODID + ":armor_imbuer"));
	public static final Block slipstreamGenerator = new BlockSlipstreamGenerator().registerAndName(new ResourceLocation(ArsMagicaReborn.MODID + ":slipstream_generator"));
	public static final Block witchwoodLog = new BlockWitchwoodLog().registerAndName(new ResourceLocation(ArsMagicaReborn.MODID + ":witchwood_log"));
	public static final Block essenceConduit = new BlockEssenceConduit().registerAndName(new ResourceLocation(ArsMagicaReborn.MODID + ":essence_conduit"));
	public static final Block redstoneInlay = new BlockInlay(BlockInlay.TYPE_REDSTONE).registerAndName(new ResourceLocation(ArsMagicaReborn.MODID + ":redstone_inlay"));
	public static final Block ironInlay = new BlockInlay(BlockInlay.TYPE_IRON).registerAndName(new ResourceLocation(ArsMagicaReborn.MODID + ":iron_inlay"));;
	public static final Block goldInlay = new BlockInlay(BlockInlay.TYPE_GOLD).registerAndName(new ResourceLocation(ArsMagicaReborn.MODID + ":gold_inlay"));;
	public static final Block vinteumTorch = new BlockVinteumTorch().registerAndName(new ResourceLocation(ArsMagicaReborn.MODID + ":vinteum_torch"));
	public static final Block witchwoodLeaves = new BlockWitchwoodLeaves().registerAndName(new ResourceLocation(ArsMagicaReborn.MODID + ":witchwood_leaves"));
	public static final Block witchwoodSapling = new BlockWitchwoodSapling().registerAndName(new ResourceLocation(ArsMagicaReborn.MODID + ":witchwood_sapling"));
	public static final Block everstone = new BlockEverstone().registerAndName(new ResourceLocation(ArsMagicaReborn.MODID + ":everstone"));
	public static final BlockGroundRuneSpell spellRune = (BlockGroundRuneSpell) new BlockGroundRuneSpell().registerAndName(new ResourceLocation(ArsMagicaReborn.MODID + ":spell_rune"));
	public static final Block arcaneDeconstructor = new BlockArcaneDeconstructor().registerAndName(new ResourceLocation(ArsMagicaReborn.MODID + ":arcane_deconstructor"));
	public static final Block arcaneReconstructor = new BlockArcaneReconstructor().registerAndName(new ResourceLocation(ArsMagicaReborn.MODID + ":arcane_reconstructor"));
	public static final Block essenceRefiner = new BlockEssenceRefiner().registerAndName(new ResourceLocation(ArsMagicaReborn.MODID + ":essence_refiner"));
	public static final BlockIllusionBlock illusionBlock = (BlockIllusionBlock) new BlockIllusionBlock().registerAndName(new ResourceLocation(ArsMagicaReborn.MODID + ":illusion_block"));
	public static final Block calefactor = new BlockCalefactor().registerAndName(new ResourceLocation(ArsMagicaReborn.MODID + ":calefactor"));
	public static final Block inertSpawner = new BlockInertSpawner().registerAndName(new ResourceLocation(ArsMagicaReborn.MODID + ":inert_spawner"));
	public static final Block magiciansWorkbench = new BlockMagiciansWorkbench().registerAndName(new ResourceLocation(ArsMagicaReborn.MODID + ":magicians_workbench"));
	public static final Block otherworldAura = new BlockOtherworldAura().registerAndName(new ResourceLocation(ArsMagicaReborn.MODID + ":otherworld_aura"));
	public static final Block summoner = new BlockSummoner().registerAndName(new ResourceLocation(ArsMagicaReborn.MODID + ":summoner"));
	public static final Block witchwoodPlanks = new BlockWitchwoodPlanks().registerAndName(new ResourceLocation(ArsMagicaReborn.MODID + ":witchwood_planks"));
	public static final Block witchwoodStairs = new BlockWitchwoodStairs(witchwoodPlanks.getDefaultState()).registerAndName(new ResourceLocation(ArsMagicaReborn.MODID + ":witchwood_stairs"));
	public static final BlockSlab witchwoodSingleSlab = new BlockWitchwoodSlabsSimple();
	public static final BlockSlab witchwoodDoubleSlab = new BlockWitchwoodSlabsDouble();
	public static final Block vinteumCauldron = new BlockVinteumCauldron().registerAndName(new ResourceLocation(ArsMagicaReborn.MODID + ":vinteum_cauldron"));
	public static final Block teleporterAutel = new BlockTeleporterAutel().registerAndName(new ResourceLocation(ArsMagicaReborn.MODID + ":teleporter_autel"));

	public static HashMap<Integer, ArrayList<AMVector3>> KeystonePortalLocations = new HashMap<>();
	public static Fluid liquid_essence = new Fluid("liquid_essence", new ResourceLocation(ArsMagicaReborn.MODID, "blocks/liquidessencestill"), new ResourceLocation(ArsMagicaReborn.MODID, "blocks/liquidessenceflowing")).setRarity(EnumRarity.RARE).setLuminosity(7);
	public static Fluid purified_liquid_essence = new Fluid("purified_liquid_essence", new ResourceLocation(ArsMagicaReborn.MODID, "blocks/purifiedliquidessencestill"), new ResourceLocation(ArsMagicaReborn.MODID, "blocks/purifiedliquidessenceflowing")).setRarity(EnumRarity.RARE).setLuminosity(10);

	
	public void preInit () {
		FluidRegistry.registerFluid(liquid_essence);
		FluidRegistry.addBucketForFluid(liquid_essence);
		liquid_essence = FluidRegistry.getFluid(BlockDefs.liquid_essence.getName());
		Block blockliquid_essence = new BlockFluidClassic(liquid_essence, Material.WATER).setUnlocalizedName(ArsMagicaReborn.MODID + ":fluid_block_liquid_essence");
		Item itemliquid_essence = new ItemBlock(blockliquid_essence);
		GameRegistry.register(blockliquid_essence, new ResourceLocation(ArsMagicaReborn.MODID + ":liquid_essence"));
		GameRegistry.register(itemliquid_essence, new ResourceLocation(ArsMagicaReborn.MODID + ":liquid_essence"));
		
		FluidRegistry.registerFluid(purified_liquid_essence);
		FluidRegistry.addBucketForFluid(purified_liquid_essence);
		purified_liquid_essence = FluidRegistry.getFluid(BlockDefs.purified_liquid_essence.getName());
		Block purified_blockliquid_essence = new BlockFluidClassic(purified_liquid_essence, Material.WATER).setUnlocalizedName(ArsMagicaReborn.MODID + ":fluid_block_purified_liquid_essence");
		Item purified_itemliquid_essence = new ItemBlock(purified_blockliquid_essence);
		GameRegistry.register(purified_blockliquid_essence, new ResourceLocation(ArsMagicaReborn.MODID + ":purified_liquid_essence"));
		GameRegistry.register(purified_itemliquid_essence, new ResourceLocation(ArsMagicaReborn.MODID + ":purified_liquid_essence"));
		
		GameRegistry.register(witchwoodSingleSlab, new ResourceLocation(ArsMagicaReborn.MODID + ":witchwood_slab"));
		GameRegistry.register(witchwoodDoubleSlab, new ResourceLocation(ArsMagicaReborn.MODID + ":witchwood_slab_double"));
		GameRegistry.register(new ItemSlab(witchwoodSingleSlab, witchwoodSingleSlab, witchwoodDoubleSlab), new ResourceLocation(ArsMagicaReborn.MODID + ":witchwood_slab"));
	}
	
	@SideOnly(Side.CLIENT)
	public void preInitClient() {
		Block blockliquid_essence = GameRegistry.findRegistry(Block.class).getValue(new ResourceLocation(ArsMagicaReborn.MODID + ":liquid_essence"));
		Item itemliquid_essence = GameRegistry.findRegistry(Item.class).getValue(new ResourceLocation(ArsMagicaReborn.MODID + ":liquid_essence"));
		
		Block blockpurified_liquid_essence = GameRegistry.findRegistry(Block.class).getValue(new ResourceLocation(ArsMagicaReborn.MODID + ":purified_liquid_essence"));
		Item itempurified_liquid_essence = GameRegistry.findRegistry(Item.class).getValue(new ResourceLocation(ArsMagicaReborn.MODID + ":purified_liquid_essence"));
		
		ModelBakery.registerItemVariants(itemliquid_essence, new ModelResourceLocation(new ResourceLocation(ArsMagicaReborn.MODID + ":liquid_essence"), liquid_essence.getName()));
		
		ModelBakery.registerItemVariants(itempurified_liquid_essence, new ModelResourceLocation(new ResourceLocation(ArsMagicaReborn.MODID + ":purified_liquid_essence"), purified_liquid_essence.getName()));

		ModelLoader.setCustomMeshDefinition(itempurified_liquid_essence, new ItemMeshDefinition() {
			
			@Override
			public ModelResourceLocation getModelLocation(ItemStack stack) {
				return new ModelResourceLocation(new ResourceLocation(ArsMagicaReborn.MODID + ":purified_liquid_essence"), purified_liquid_essence.getName());
			}
		});
		
		
		ModelLoader.setCustomStateMapper(blockpurified_liquid_essence, new StateMapperBase() {
			
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
				return new ModelResourceLocation(new ResourceLocation(ArsMagicaReborn.MODID + ":purified_liquid_essence"), purified_liquid_essence.getName());
			}
		});
		
		ModelLoader.setCustomMeshDefinition(itemliquid_essence, new ItemMeshDefinition() {
			
			@Override
			public ModelResourceLocation getModelLocation(ItemStack stack) {
				return new ModelResourceLocation(new ResourceLocation(ArsMagicaReborn.MODID + ":liquid_essence"), liquid_essence.getName());
			}
		});
		
		
		ModelLoader.setCustomStateMapper(blockliquid_essence, new StateMapperBase() {
			
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
				return new ModelResourceLocation(new ResourceLocation(ArsMagicaReborn.MODID + ":liquid_essence"), liquid_essence.getName());
			}
		});
	}
	
	
	@SideOnly(Side.CLIENT)
	public static void initClient () {
		IForgeRegistry<Item> items = GameRegistry.findRegistry(Item.class);
		RenderItem renderer = Minecraft.getMinecraft().getRenderItem();
		
		//Utility Blocks
		registerTexture(frost);
		registerTexture(invisibleLight);
		registerTexture(invisibleUtility);
		registerTexture(blockMageTorch);
		
		//Building Blocks
		registerTexture(magicWall);
		
		//Power Blocks
		registerTexture(obelisk);
		registerTexture(celestialPrism);
		registerTexture(manaBattery);
		registerTexture(armorImbuer);
		registerTexture(slipstreamGenerator);
		
		//Ritual Blocks
		registerTexture(wardingCandle);
		//registerTexture(wizardChalk);
		
		//Spell Blocks
		registerTexture(occulus);
		registerTexture(lectern);
		registerTexture(craftingAltar);
		registerTexture(inscriptionTable);
		
		//Flowers
		registerTexture(aum);
		registerTexture(cerublossom);
		registerTexture(wakebloom);
		registerTexture(tarmaRoot);
		registerTexture(desertNova);
		
		registerTexture(witchwoodLeaves);
		registerTexture(witchwoodLog);
		registerTexture(witchwoodSapling);
		registerTexture(essenceConduit);
		
		registerTexture(arcaneDeconstructor);
		registerTexture(arcaneReconstructor);
		registerTexture(essenceRefiner);
		registerTexture(everstone);
		
		registerTexture(illusionBlock);
		registerTexture(calefactor);
		registerTexture(inertSpawner);
		registerTexture(magiciansWorkbench);
		registerTexture(otherworldAura);
		registerTexture(summoner);
		registerTexture(witchwoodStairs);
		registerTexture(witchwoodPlanks);
		registerTexture(witchwoodSingleSlab);
		registerTexture(redstoneInlay);
		registerTexture(goldInlay);
		registerTexture(ironInlay);
		registerTexture(vinteumTorch);
		
		registerTexture(vinteumCauldron);
		
		Item ore = items.getValue(new ResourceLocation(ArsMagicaReborn.MODID + ":ore"));
		Item block = items.getValue(new ResourceLocation(ArsMagicaReborn.MODID + ":block"));
		
		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(new ManaBatteryColorizer(), manaBattery);
		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(new MonoColorizer(0x00ffff), essenceConduit);
		for (int i = 0; i < BlockArsMagicaOre.EnumOreType.values().length; i++) {
			ModelResourceLocation blockLoc = new ModelResourceLocation(ArsMagicaReborn.MODID + ":block_" + BlockArsMagicaOre.EnumOreType.values()[i].getName(), "inventory");
			ModelResourceLocation oreLoc = new ModelResourceLocation(ArsMagicaReborn.MODID + ":ore_" + BlockArsMagicaOre.EnumOreType.values()[i].getName(), "inventory");
			ModelBakery.registerItemVariants(ore, oreLoc);	
			ModelBakery.registerItemVariants(block, blockLoc);
			renderer.getItemModelMesher().register(ore, i, oreLoc);
			renderer.getItemModelMesher().register(block, i, blockLoc);
		}
	}
	
	@SideOnly(Side.CLIENT)
	private static void registerTexture(Block block) {
		ResourceLocation loc = block.getRegistryName();
		Item item = GameRegistry.findRegistry(Item.class).getValue(loc);
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, new IgnoreMetadataRenderer(new ModelResourceLocation(loc, "inventory")));
	}


}
