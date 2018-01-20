package amreborn.affinity.abilities;

import amreborn.ArsMagicaReborn;
import amreborn.api.affinity.AbstractAffinityAbility;
import amreborn.api.affinity.Affinity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class AbilityLightningStep extends AbstractAffinityAbility {

	public AbilityLightningStep() {
		super(new ResourceLocation(ArsMagicaReborn.MODID, "lightningstep"));
	}

	@Override
	public float getMinimumDepth() {
		return 0.5f;
	}

	@Override
	public Affinity getAffinity() {
		return Affinity.LIGHTNING;
	}
	
	@Override
	public void applyTick(EntityPlayer player) {
		player.stepHeight = 1.014F;
	}
	
	@Override
	public void removeEffects(EntityPlayer player) {
		if (player.stepHeight == 1.014F)
			player.stepHeight = 0.6F;
	}

}
