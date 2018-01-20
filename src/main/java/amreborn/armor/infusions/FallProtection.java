package amreborn.armor.infusions;

import java.util.EnumSet;

import amreborn.api.extensions.IEntityExtension;
import amreborn.api.items.armor.ArmorImbuement;
import amreborn.api.items.armor.ImbuementApplicationTypes;
import amreborn.api.items.armor.ImbuementTiers;
import amreborn.buffs.BuffEffectSlowfall;
import amreborn.defs.PotionEffectsDefs;
import amreborn.extensions.EntityExtension;
import amreborn.utils.MathUtilities;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FallProtection extends ArmorImbuement{

	@Override
	public String getID(){
		return "fallprot";
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

		int distanceToGround = MathUtilities.getDistanceToGround(player, world);
		IEntityExtension extendedProperties = EntityExtension.For(player);
		if (player.fallDistance >= extendedProperties.getFallProtection() + 4f && distanceToGround < -8 * player.motionY){
			if (!player.isPotionActive(PotionEffectsDefs.slowfall) && !player.capabilities.isFlying){

				BuffEffectSlowfall sf = new BuffEffectSlowfall(distanceToGround * 3, 1);
				player.addPotionEffect(sf);

				stack.damageItem((int)(player.fallDistance * 6), player);

				player.fallDistance = 0;
				return true;
			}
		}
		return false;
	}

	@Override
	public EntityEquipmentSlot[] getValidSlots(){
		return new EntityEquipmentSlot[]{EntityEquipmentSlot.FEET};
	}

	@Override
	public boolean canApplyOnCooldown(){
		return false;
	}

	@Override
	public int getCooldown(){
		return 900;
	}

	@Override
	public int getArmorDamage(){
		return 0;
	}
}
