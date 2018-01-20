package amreborn.affinity.abilities;

import amreborn.ArsMagicaReborn;
import amreborn.api.affinity.AbstractAffinityAbility;
import amreborn.api.affinity.Affinity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class AbilityThunderPunch extends AbstractAffinityAbility {

	public AbilityThunderPunch() {
		super(new ResourceLocation(ArsMagicaReborn.MODID, "thunderpunch"));
	}

	@Override
	public float getMinimumDepth() {
		return 0.75f;
	}

	@Override
	public Affinity getAffinity() {
		return Affinity.LIGHTNING;
	}
	
	@Override
	public void applyHurt(EntityPlayer player, LivingHurtEvent event, boolean isAttacker) {
		if (isAttacker && !player.world.isRemote && player.getHeldItemMainhand() == null) {
			player.world.addWeatherEffect(new EntityLightningBolt(player.world, event.getEntityLiving().posX, event.getEntityLiving().posY, event.getEntityLiving().posZ, false));
		} 
	}
}
