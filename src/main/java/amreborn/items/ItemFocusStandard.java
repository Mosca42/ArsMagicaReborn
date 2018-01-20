package amreborn.items;

import amreborn.api.items.ISpellFocus;
import amreborn.api.items.ItemFocus;
import amreborn.defs.ItemDefs;
import net.minecraft.init.Items;

public class ItemFocusStandard extends ItemFocus implements ISpellFocus{

	public ItemFocusStandard(){
		super();
	}

	@Override
	public Object[] getRecipeItems(){
		return new Object[]{
				" R ", "RFR", " R ",
				'R', Items.REDSTONE,
				'F', ItemDefs.lesserFocus
		};
	}

	@Override
	public String getInGameName(){
		return "Focus";
	}

	@Override
	public int getFocusLevel(){
		return 1;
	}
}
