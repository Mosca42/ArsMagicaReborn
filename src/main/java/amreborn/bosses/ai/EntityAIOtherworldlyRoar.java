package amreborn.bosses.ai;

import amreborn.bosses.BossActions;
import amreborn.bosses.EntityEnderGuardian;
import amreborn.utils.NPCSpells;
import amreborn.utils.SpellUtils;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import thehippomaster.AnimationAPI.AIAnimation;
import thehippomaster.AnimationAPI.IAnimatedEntity;

public class EntityAIOtherworldlyRoar extends AIAnimation{

	private int cooldownTicks = 0;

	public EntityAIOtherworldlyRoar(IAnimatedEntity entity){
		super(entity);
	}

	@Override
	public int getAnimID(){
		return BossActions.LONG_CASTING.ordinal();
	}

	@Override
	public boolean isAutomatic(){
		return false;
	}

	@Override
	public int getDuration(){
		return 63;
	}

	@Override
	public boolean shouldAnimate(){
		//accessor method in AIAnimation that gives access to the entity
		EntityLiving living = getEntity();

		//must have an attack target
		if (living.getAttackTarget() == null) return false;

		if (living.world.getEntitiesWithinAABB(EntityLivingBase.class, living.getEntityBoundingBox().expand(9, 3, 9)).size() < 2){
			return false;
		}

		return cooldownTicks-- <= 0;
	}

	@Override
	public void resetTask(){
		cooldownTicks = 100;
		super.resetTask();
	}

	@Override
	public void updateTask(){
		EntityEnderGuardian guardian = getEntity();
		if (guardian.getAttackTarget() != null){
			if (guardian.getTicksInCurrentAction() == 33){
				guardian.faceEntity(guardian.getAttackTarget(), 180, 180);
				SpellUtils.applyStackStage(NPCSpells.instance.enderGuardian_otherworldlyRoar, guardian, guardian, guardian.posX, guardian.posY + 0.5f, guardian.posZ, null, guardian.world, false, false, 0);
			}else{
				guardian.faceEntity(guardian.getAttackTarget(), 180, 180);
			}
		}
	}


}
