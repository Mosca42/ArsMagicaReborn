package amreborn.items.colorizers;

import amreborn.api.ArsMagicaAPI;
import amreborn.api.affinity.Affinity;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;

public class FlickerJarColorizer implements IItemColor{

	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex) {
		if (tintIndex == 0)
			return 0xffffff;
		int meta = stack.getItemDamage();
		Affinity aff = ArsMagicaAPI.getAffinityRegistry().getObjectById(meta);
		return aff.getColor();
	}

}
