package amreborn.affinity.abilities;

import amreborn.ArsMagicaReborn;
import amreborn.api.affinity.AbstractAffinityAbility;
import amreborn.api.affinity.Affinity;
import amreborn.extensions.EntityExtension;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class AbilityLeafLike extends AbstractAffinityAbility {

	public AbilityLeafLike() {
		super(new ResourceLocation(ArsMagicaReborn.MODID, "leaflike"));
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
		if (player.isCollidedHorizontally) {
			if (!player.isSneaking()) {
				float movement = EntityExtension.For(player).getIsFlipped() ? -0.25f : 0.25f;
				player.move(MoverType.SELF, 0, movement, 0);
				player.motionY = 0;
			} else {
				player.motionY *= 0.79999999;
			}
			player.fallDistance = 0;
		}
	}

}
