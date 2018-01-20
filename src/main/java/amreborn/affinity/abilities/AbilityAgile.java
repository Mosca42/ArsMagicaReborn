package amreborn.affinity.abilities;

import amreborn.ArsMagicaReborn;
import amreborn.api.affinity.AbstractAffinityAbility;
import amreborn.api.affinity.Affinity;
import amreborn.extensions.AffinityData;
import amreborn.extensions.EntityExtension;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;

public class AbilityAgile extends AbstractAffinityAbility {

	public AbilityAgile() {
		super(new ResourceLocation(ArsMagicaReborn.MODID, "agile"));
	}

	@Override
	public float getMinimumDepth() {
		return 0.5f;
	}

	@Override
	public Affinity getAffinity() {
		return Affinity.AIR;
	}
	
	@Override
	public void applyJump(EntityPlayer player, LivingJumpEvent event) {
		double airDepth = AffinityData.For(player).getAffinityDepth(Affinity.AIR);
		double velocity = airDepth * 0.35f;
		if (EntityExtension.For(player).getIsFlipped())
			velocity *= -1;
		player.addVelocity(0, velocity, 0);
	}
	
	@Override
	public void applyFall(EntityPlayer player, LivingFallEvent event) {
		double airDepth = AffinityData.For(player).getAffinityDepth(Affinity.AIR);
		event.setDistance((float) (event.getDistance() - (2 * airDepth)));
		if (event.getDistance() < 0) event.setDistance(0);
	}

}
