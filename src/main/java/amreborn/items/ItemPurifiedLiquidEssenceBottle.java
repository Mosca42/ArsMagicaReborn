package amreborn.items;

import amreborn.ArsMagicaReborn;
import amreborn.defs.CreativeTabsDefs;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemPurifiedLiquidEssenceBottle extends Item {

	public ItemPurifiedLiquidEssenceBottle() {
		setMaxStackSize(1);
		setCreativeTab(CreativeTabsDefs.tabAM2Items);
	}
	
	public Item registerAndName(String name) {
		this.setUnlocalizedName(new ResourceLocation(ArsMagicaReborn.MODID, name).toString());
		GameRegistry.register(this, new ResourceLocation(ArsMagicaReborn.MODID, name));
		return this;
	}
}
