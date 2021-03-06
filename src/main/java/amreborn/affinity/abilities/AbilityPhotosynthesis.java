package amreborn.affinity.abilities;

import amreborn.ArsMagicaReborn;
import amreborn.api.affinity.AbstractAffinityAbility;
import amreborn.api.affinity.Affinity;
import amreborn.extensions.AffinityData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class AbilityPhotosynthesis extends AbstractAffinityAbility {

	public AbilityPhotosynthesis() {
		super(new ResourceLocation(ArsMagicaReborn.MODID, "photosynthesis"));
	}

	@Override
	public float getMinimumDepth() {
		return 1f;
	}

	@Override
	public Affinity getAffinity() {
		return Affinity.NATURE;
	}
	
	@Override
	public void applyTick(EntityPlayer player) {
		AffinityData affinityData = AffinityData.For(player);
		if (player.world.isRemote) return;
		
		if (player.world.canBlockSeeSky(player.getPosition()) && player.world.isDaytime()){
			affinityData.accumulatedHungerRegen += 0.02f;
			if (affinityData.accumulatedHungerRegen > 1.0f){
				((EntityPlayer)player).getFoodStats().addStats(1, 0.025f);
				affinityData.accumulatedHungerRegen -= 1;
			}
		}else{
			((EntityPlayer)player).addExhaustion(0.025f);
		}
	}

}
