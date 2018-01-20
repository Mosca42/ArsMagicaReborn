package amreborn.affinity.abilities;

import amreborn.ArsMagicaReborn;
import amreborn.api.affinity.AbstractToggledAffinityAbility;
import amreborn.api.affinity.Affinity;
import amreborn.extensions.AffinityData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class AbilityNightVision extends AbstractToggledAffinityAbility {

	public AbilityNightVision() {
		super(new ResourceLocation(ArsMagicaReborn.MODID, "nightvision"));
	}

	@Override
	protected boolean isEnabled(EntityPlayer player) {
		return AffinityData.For(player).getAbilityBoolean(AffinityData.NIGHT_VISION);
	}

	@Override
	public float getMinimumDepth() {
		return 0.75f;
	}

	@Override
	public Affinity getAffinity() {
		return Affinity.ENDER;
	}

	@Override
	public void applyTick(EntityPlayer player) {
		Potion nightVision = Potion.getPotionFromResourceLocation("night_vision");
		if (!player.world.isRemote && (!player.isPotionActive(nightVision) || player.getActivePotionEffect(nightVision).getDuration() <= 220)){
			player.addPotionEffect(new PotionEffect(nightVision, 300, 0, true, false));
		}
	}

}
