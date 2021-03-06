package amreborn.buffs;

import amreborn.defs.BlockDefs;
import amreborn.defs.PotionEffectsDefs;
import net.minecraft.entity.EntityLivingBase;

public class BuffEffectIllumination extends BuffEffect{

	public BuffEffectIllumination(int duration, int amplifier){
		super(PotionEffectsDefs.illumination, duration, amplifier);
	}

	@Override
	public void applyEffect(EntityLivingBase entityliving){
	}

	@Override
	public void stopEffect(EntityLivingBase entityliving){
	}

	@Override
	public void performEffect(EntityLivingBase entityliving){
		if (!entityliving.world.isRemote && entityliving.ticksExisted % 10 == 0) {
			if (entityliving.world.isAirBlock(entityliving.getPosition()) && entityliving.world.getLight(entityliving.getPosition()) < 7){
				entityliving.world.setBlockState(entityliving.getPosition(), BlockDefs.invisibleLight.getDefaultState());
			}
		}
	}

	@Override
	protected String spellBuffName(){
		return "Illumination";
	}

}
