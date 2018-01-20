package amreborn.items.colorizers;

import amreborn.defs.ItemDefs;
import amreborn.lore.Story;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;

public class LostJournalColorizer implements IItemColor {

	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex) {

		Story s = ItemDefs.lostJournal.getStory(stack);
		if (s == null){
			return 0xffffff;
		}
		return s.getStoryPassColor(tintIndex);
	}

}
