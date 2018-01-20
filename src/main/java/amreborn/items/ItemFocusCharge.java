package amreborn.items;

import amreborn.api.items.ItemFocus;
import amreborn.defs.ItemDefs;
import net.minecraft.init.Blocks;

public class ItemFocusCharge extends ItemFocus{

	public ItemFocusCharge(){
		super();
	}

	@Override
	public Object[] getRecipeItems(){
		return new Object[]{
				"CFC",
				'F', ItemDefs.standardFocus,
				'C', Blocks.GLASS
		};
	}

	@Override
	public String getInGameName(){
		return "Charge Focus";
	}
}
