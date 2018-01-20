package amreborn.affinity.abilities;

import amreborn.ArsMagicaReborn;
import amreborn.api.affinity.AbstractAffinityAbility;
import amreborn.api.affinity.Affinity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class AbilityFirePunch extends AbstractAffinityAbility {

	public AbilityFirePunch() {
		super(new ResourceLocation(ArsMagicaReborn.MODID, "firepunch"));
	}

	@Override
	public float getMinimumDepth() {
		return 0.8f;
	}

	@Override
	public Affinity getAffinity() {
		return Affinity.FIRE;
	}
	
	@Override
	public void applyHurt(EntityPlayer player, LivingHurtEvent event, boolean isAttacker) {
		if (isAttacker && !player.world.isRemote && player.getHeldItemMainhand() == null) {
			event.getEntityLiving().setFire(4);
			event.setAmount(event.getAmount() + 3);
		}
	}
}
