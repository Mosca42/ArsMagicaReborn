package amreborn.items;

import amreborn.ArsMagicaReborn;
import amreborn.api.IBoundItem;
import amreborn.defs.ItemDefs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.common.registry.GameRegistry;

@SuppressWarnings("deprecation")
public class ItemBoundShield extends ItemShield implements IBoundItem {

	public ItemBoundShield() {
		super();
		this.maxStackSize = 1;
		this.setMaxDamage(0);
		this.setCreativeTab(null);
	}

	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		return false;
	}

	@Override
	public boolean onDroppedByPlayer(ItemStack item, EntityPlayer player) {
		item = new ItemStack(ItemDefs.spell);
		return false;
	}

	@Override
	public float maintainCost(EntityPlayer player, ItemStack stack) {
		return normalMaintain;
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return I18n.translateToLocal("item." + getRegistryName().toString() + ".name");
	}

	public ItemBoundShield registerAndName(String name) {
		this.setUnlocalizedName(new ResourceLocation(ArsMagicaReborn.MODID, name).toString());
		GameRegistry.register(this, new ResourceLocation(ArsMagicaReborn.MODID, name));
		return this;
	}
	
}
