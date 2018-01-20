package amreborn.affinity.abilities;

import amreborn.ArsMagicaReborn;
import amreborn.api.affinity.AbstractAffinityAbility;
import amreborn.api.affinity.Affinity;
import amreborn.extensions.AffinityData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class AbilityFastHealing extends AbstractAffinityAbility {

	public AbilityFastHealing() {
		super(new ResourceLocation(ArsMagicaReborn.MODID, "fasthealing"));
	}

	@Override
	public float getMinimumDepth() {
		return 0.0f;
	}

	@Override
	public Affinity getAffinity() {
		return Affinity.LIFE;
	}
	
	@Override
	public void applyTick(EntityPlayer player) {
		AffinityData.For(player).accumulatedLifeRegen += 0.025 * AffinityData.For(player).getAffinityDepth(Affinity.LIFE);
		if (AffinityData.For(player).accumulatedLifeRegen > 1.0f){
			AffinityData.For(player).accumulatedLifeRegen -= 1.0f;
			player.heal(1);
		}
	}

}
