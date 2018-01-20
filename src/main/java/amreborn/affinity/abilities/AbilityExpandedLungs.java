package amreborn.affinity.abilities;

import amreborn.ArsMagicaReborn;
import amreborn.api.affinity.AbstractAffinityAbility;
import amreborn.api.affinity.Affinity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class AbilityExpandedLungs extends AbstractAffinityAbility {

	public AbilityExpandedLungs() {
		super(new ResourceLocation(ArsMagicaReborn.MODID, "expandedlungs"));
	}

	@Override
	public float getMinimumDepth() {
		return 0.4f;
	}

	@Override
	public Affinity getAffinity() {
		return Affinity.WATER;
	}

	@Override
	public void applyTick(EntityPlayer player) {
		if (player.isInWater() && player.world.rand.nextInt(20) < 4) {
			player.setAir(player.getAir() + 1);
		}
	}

}
