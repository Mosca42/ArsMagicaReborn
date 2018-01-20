package amreborn.items;

import amreborn.api.items.ISpellFocus;
import amreborn.api.items.ItemFocus;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;

public class ItemFocusLesser extends ItemFocus implements ISpellFocus{

	public ItemFocusLesser(){
		super();
	}

	@Override
	public Object[] getRecipeItems(){
		return new Object[]{
				" G ", "GRG", " G ",
				'G', Items.GOLD_NUGGET,
				'R', Blocks.GLASS
		};
	}

	@Override
	public String getInGameName(){
		return "Lesser Focus";
	}

	@Override
	public int getFocusLevel(){
		return 0;
	}
}
