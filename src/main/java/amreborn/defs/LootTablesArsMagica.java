package amreborn.defs;

import amreborn.ArsMagicaReborn;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;

public class LootTablesArsMagica {
	
	public static final ResourceLocation MAGE_TOWER_LOOT = LootTableList.register(new ResourceLocation(ArsMagicaReborn.MODID, "chests/mage_tower"));
	public static final ResourceLocation MAGE_TOWER_RARE_LOOT = LootTableList.register(new ResourceLocation(ArsMagicaReborn.MODID, "chests/mage_tower_rare"));
	public static final ResourceLocation MANA_CREEPER_LOOT = LootTableList.register(new ResourceLocation(ArsMagicaReborn.MODID, "entities/mana_creeper"));
	public static final ResourceLocation HELL_COW_LOOT = LootTableList.register(new ResourceLocation(ArsMagicaReborn.MODID, "entities/hell_cow"));
	public static final ResourceLocation DARK_MAGE_LOOT = LootTableList.register(new ResourceLocation(ArsMagicaReborn.MODID, "entities/dark_mage"));
	public static final ResourceLocation LIGHT_MAGE_LOOT = LootTableList.register(new ResourceLocation(ArsMagicaReborn.MODID, "entities/light_mage"));
}
