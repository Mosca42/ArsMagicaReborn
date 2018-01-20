package amreborn.items;

import amreborn.ArsMagicaReborn;
import amreborn.blocks.BlockArsMagicaBlock.EnumBlockType;
import amreborn.blocks.BlockArsMagicaOre.EnumOreType;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.translation.I18n;

@SuppressWarnings("deprecation")
public class ItemBlockOreBlock extends ItemBlockSubtypes {

	public ItemBlockOreBlock(Block block) {
		super(block);
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return I18n.translateToLocal("tile."+ ArsMagicaReborn.MODID +":block_" + EnumBlockType.values()[MathHelper.clamp(stack.getItemDamage(), 0, EnumOreType.values().length - 1)].getName().toLowerCase() + ".name");
	}
}
