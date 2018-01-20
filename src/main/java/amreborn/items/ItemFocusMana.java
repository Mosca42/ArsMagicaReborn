package amreborn.items;

import amreborn.api.items.ItemFocus;
import amreborn.defs.ItemDefs;
import net.minecraft.item.ItemStack;

public class ItemFocusMana extends ItemFocus{

	public ItemFocusMana(){
		super();
	}

	@Override
	public Object[] getRecipeItems(){
		return new Object[]{
				"P", "F", "P",
				'P', new ItemStack(ItemDefs.itemOre, 1, ItemOre.META_VINTEUM),
				'F', ItemDefs.standardFocus
		};
	}

	@Override
	public String getInGameName(){
		return "Mana Focus";
	}
}
