package amreborn.affinity.abilities;

import amreborn.ArsMagicaReborn;
import amreborn.api.affinity.AbstractAffinityAbility;
import amreborn.api.affinity.Affinity;
import amreborn.buffs.BuffEffectSwiftSwim;
import amreborn.defs.PotionEffectsDefs;
import amreborn.extensions.AffinityData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class AbilitySwiftSwim extends AbstractAffinityAbility {

	public AbilitySwiftSwim() {
		super(new ResourceLocation(ArsMagicaReborn.MODID, "swiftswim"));
	}

	@Override
	public float getMinimumDepth() {
		return 0.5f;
	}

	@Override
	public Affinity getAffinity() {
		return Affinity.WATER;
	}

	@Override
	public void applyTick(EntityPlayer player) {
		if (player.isInWater()) {
			if (!player.world.isRemote && (!player.isPotionActive(PotionEffectsDefs.swiftSwim) || player.getActivePotionEffect(PotionEffectsDefs.swiftSwim).getDuration() < 10)){
				player.addPotionEffect(new BuffEffectSwiftSwim(100, AffinityData.For(player).getAffinityDepth(getAffinity()) > 0.75f ? 1 : 0));
			}
		}
	}

}
