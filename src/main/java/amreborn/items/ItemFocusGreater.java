package amreborn.items;

import amreborn.api.items.ISpellFocus;
import amreborn.api.items.ItemFocus;
import amreborn.defs.ItemDefs;
import net.minecraft.item.ItemStack;

public class ItemFocusGreater extends ItemFocus implements ISpellFocus{

	public ItemFocusGreater(){
		super();
	}

	@Override
	public Object[] getRecipeItems(){
		return new Object[]{
				"A A", "PFP", "A A",
				'A', new ItemStack(ItemDefs.itemOre, 1, ItemOre.META_ARCANEASH),
				'F', ItemDefs.standardFocus,
				'P', new ItemStack(ItemDefs.itemOre, 1, ItemOre.META_PURIFIED_VINTEUM)
		};
	}

	@Override
	public String getInGameName(){
		return "Greater Focus";
	}

	@Override
	public int getFocusLevel(){
		return 2;
	}
}
