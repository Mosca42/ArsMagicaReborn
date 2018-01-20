package amreborn.armor.infusions;

import java.util.EnumSet;

import amreborn.api.items.armor.ArmorImbuement;
import amreborn.api.items.armor.ImbuementApplicationTypes;
import amreborn.api.items.armor.ImbuementTiers;
import amreborn.buffs.BuffEffectRegeneration;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Healing extends ArmorImbuement{

	@Override
	public String getID(){
		return "healing";
	}

	@Override
	public ImbuementTiers getTier(){
		return ImbuementTiers.FOURTH;
	}

	@Override
	public EnumSet<ImbuementApplicationTypes> getApplicationTypes(){
		return EnumSet.of(ImbuementApplicationTypes.ON_HIT);
	}

	@Override
	public boolean applyEffect(EntityPlayer player, World world, ItemStack stack, ImbuementApplicationTypes matchedType, Object... params){

		if (world.isRemote)
			return false;

		if (player.getHealth() < (player.getMaxHealth() * 0.25f)){
			player.addPotionEffect(new BuffEffectRegeneration(240, 2));
			return true;
		}
		return false;
	}

	@Override
	public EntityEquipmentSlot[] getValidSlots(){
		return new EntityEquipmentSlot[]{EntityEquipmentSlot.CHEST};
	}

	@Override
	public boolean canApplyOnCooldown(){
		return false;
	}

	@Override
	public int getCooldown(){
		return 6400;
	}

	@Override
	public int getArmorDamage(){
		return 30;
	}
}
