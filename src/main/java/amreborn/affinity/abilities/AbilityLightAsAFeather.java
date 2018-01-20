package amreborn.affinity.abilities;

import amreborn.ArsMagicaReborn;
import amreborn.api.affinity.AbstractAffinityAbility;
import amreborn.api.affinity.Affinity;
import amreborn.api.event.SpellCastEvent.Pre;
import amreborn.defs.PotionEffectsDefs;
import amreborn.packet.AMNetHandler;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class AbilityLightAsAFeather extends AbstractAffinityAbility {

	public AbilityLightAsAFeather() {
		super(new ResourceLocation(ArsMagicaReborn.MODID, "lightasafeather"));
	}

	@Override
	public float getMinimumDepth() {
		return 0.5f;
	}
	
	@Override
	public float getMaximumDepth() {
		return 0.85f;
	}

	@Override
	public Affinity getAffinity() {
		return Affinity.AIR;
	}
	
	@Override
	public void applyPreSpellCast(EntityPlayer ent, Pre event) {
		if (ent.world.isRaining() && !ent.world.isRemote && ent.getEntityWorld().getBiome(ent.getPosition()).canRain() && !ent.world.isRemote && ent.world.rand.nextInt(100) < 10){
			if (!ent.isSneaking() && !ent.isPotionActive(PotionEffectsDefs.gravityWell) && !ent.isInsideOfMaterial(Material.WATER) && ent.isWet()){
				double velX = ent.world.rand.nextDouble() - 0.5;
				double velY = ent.world.rand.nextDouble() - 0.5;
				double velZ = ent.world.rand.nextDouble() - 0.5;
				ent.addVelocity(velX, velY, velZ);
				AMNetHandler.INSTANCE.sendVelocityAddPacket(ent.world, ent, velX, velY, velZ);
			}
		}
	}
}
