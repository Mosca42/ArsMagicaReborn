package amreborn.armor.infusions;

import java.util.EnumSet;

import amreborn.api.items.armor.ArmorImbuement;
import amreborn.api.items.armor.ImbuementApplicationTypes;
import amreborn.api.items.armor.ImbuementTiers;
import amreborn.buffs.BuffEffectSwiftSwim;
import amreborn.defs.PotionEffectsDefs;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class SwimSpeed extends ArmorImbuement{

	@Override
	public String getID(){
		return "swimspd";
	}
	
	@Override
	public ImbuementTiers getTier(){
		return ImbuementTiers.FOURTH;
	}

	@Override
	public EnumSet<ImbuementApplicationTypes> getApplicationTypes(){
		return EnumSet.of(ImbuementApplicationTypes.ON_TICK);
	}

	@Override
	public boolean applyEffect(EntityPlayer player, World world, ItemStack stack, ImbuementApplicationTypes matchedType, Object... params){
		if (world.isRemote)
			return false;

		if (player.isInsideOfMaterial(Material.WATER) && !player.isPotionActive(PotionEffectsDefs.swiftSwim)){
			player.addPotionEffect(new BuffEffectSwiftSwim(10, 1));
			return true;
		}
		return false;
	}

	@Override
	public EntityEquipmentSlot[] getValidSlots(){
		return new EntityEquipmentSlot[]{EntityEquipmentSlot.LEGS};
	}

	@Override
	public boolean canApplyOnCooldown(){
		return true;
	}

	@Override
	public int getCooldown(){
		return 0;
	}

	@Override
	public int getArmorDamage(){
		return 0;
	}
}
