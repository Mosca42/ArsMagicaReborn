package amreborn.bosses.ai;

import com.google.common.base.Predicate;

import amreborn.bosses.EntityWaterGuardian;
import net.minecraft.entity.Entity;

public class WaterGuardianTargetSelector implements Predicate<Entity>{

	@Override
	public boolean apply(Entity entity){
		return !(entity instanceof EntityWaterGuardian);
	}

}
