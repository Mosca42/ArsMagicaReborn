package amreborn.affinity.abilities;

import amreborn.ArsMagicaReborn;
import amreborn.api.affinity.AbstractAffinityAbility;
import amreborn.api.affinity.Affinity;
import amreborn.api.event.SpellCastEvent.Pre;
import amreborn.defs.PotionEffectsDefs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class AbilityOneWithMagic extends AbstractAffinityAbility {

	public AbilityOneWithMagic() {
		super(new ResourceLocation(ArsMagicaReborn.MODID, "onewithmagic"));
	}

	@Override
	public float getMinimumDepth() {
		return 0.5f;
	}

	@Override
	public Affinity getAffinity() {
		return Affinity.ARCANE;
	}
	
	@Override
	public void applyPreSpellCast(EntityPlayer player, Pre event) {
		event.manaCost *= 0.95f;
		event.burnout *= 0.95f;

		if (event.entityLiving.isPotionActive(PotionEffectsDefs.clarity)){
			event.manaCost = 0f;
			event.burnout = 0f;
		}
	}
}
