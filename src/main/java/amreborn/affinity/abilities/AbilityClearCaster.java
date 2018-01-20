package amreborn.affinity.abilities;

import amreborn.ArsMagicaReborn;
import amreborn.api.affinity.AbstractAffinityAbility;
import amreborn.api.affinity.Affinity;
import amreborn.api.event.SpellCastEvent.Pre;
import amreborn.buffs.BuffEffectClarity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class AbilityClearCaster extends AbstractAffinityAbility {

	public AbilityClearCaster() {
		super(new ResourceLocation(ArsMagicaReborn.MODID, "clearcaster"));
	}

	@Override
	public float getMinimumDepth() {
		return 0.4f;
	}

	@Override
	public Affinity getAffinity() {
		return Affinity.ARCANE;
	}
	
	@Override
	public void applyPreSpellCast(EntityPlayer player, Pre event) {
		if (event.entityLiving.world.rand.nextInt(100) < 5 && !event.entityLiving.world.isRemote){
			event.entityLiving.addPotionEffect(new BuffEffectClarity(140, 0));
		}
	}
}
