package amreborn.affinity.abilities;

import amreborn.ArsMagicaReborn;
import amreborn.affinity.AffinityAbilityModifiers;
import amreborn.api.affinity.AbstractAffinityAbility;
import amreborn.api.affinity.Affinity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class AbilitySunlightWeakness extends AbstractAffinityAbility {

	public AbilitySunlightWeakness() {
		super(new ResourceLocation(ArsMagicaReborn.MODID, "sunlightweakness"));
	}

	@Override
	public float getMinimumDepth() {
		return 0.65f;
	}
	
	@Override
	public float getMaximumDepth() {
		return 0.95f;
	}

	@Override
	public Affinity getAffinity() {
		return Affinity.ENDER;
	}
	
	@Override
	public void applyTick(EntityPlayer player) {
		IAttributeInstance attribute = player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
		int worldTime = (int)player.world.getWorldTime() % 24000;
		AffinityAbilityModifiers.instance.applyOrRemoveModifier(attribute, AffinityAbilityModifiers.sunlightWeakness, player.world.canBlockSeeSky(player.getPosition()) && (worldTime > 23000 || worldTime < 12500));
	}
	
	@Override
	public void removeEffects(EntityPlayer player) {
		IAttributeInstance attribute = player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
		AffinityAbilityModifiers.instance.applyOrRemoveModifier(attribute, AffinityAbilityModifiers.iceAffinityColdBlooded, false);
	}

}
