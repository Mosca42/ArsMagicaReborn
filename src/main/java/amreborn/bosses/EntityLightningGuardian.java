package amreborn.bosses;

import amreborn.ArsMagicaReborn;
import amreborn.api.ArsMagicaAPI;
import amreborn.api.affinity.Affinity;
import amreborn.bosses.ai.EntityAICastSpell;
import amreborn.bosses.ai.EntityAIDispel;
import amreborn.bosses.ai.EntityAILightningBolt;
import amreborn.bosses.ai.EntityAILightningRod;
import amreborn.bosses.ai.EntityAIStatic;
import amreborn.defs.AMSounds;
import amreborn.defs.ItemDefs;
import amreborn.extensions.EntityExtension;
import amreborn.particles.AMParticle;
import amreborn.particles.ParticleHoldPosition;
import amreborn.utils.NPCSpells;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.BossInfo.Color;
import net.minecraft.world.World;
import thehippomaster.AnimationAPI.IAnimatedEntity;

//

public class EntityLightningGuardian extends AM2Boss implements IAnimatedEntity{

	public EntityLightningGuardian(World par1World){
		super(par1World);
		this.setSize(1.75f, 3);
	}

	@Override
	public float getEyeHeight(){
		return 2f;
	}


	@Override
	protected void initSpecificAI(){
		this.tasks.addTask(1, new EntityAIDispel(this));
		this.tasks.addTask(2, new EntityAILightningRod(this));
		this.tasks.addTask(3, new EntityAIStatic(this));
		this.tasks.addTask(3, new EntityAICastSpell<EntityLightningGuardian>(this, NPCSpells.instance.lightningRune, 22, 27, 200, BossActions.CASTING));
		this.tasks.addTask(3, new EntityAICastSpell<EntityLightningGuardian>(this, NPCSpells.instance.scrambleSynapses, 45, 60, 300, BossActions.SMASH));
		this.tasks.addTask(5, new EntityAILightningBolt(this));
	}

	@Override
	protected void applyEntityAttributes(){
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(250D);
	}

	@Override
	public void onDeath(DamageSource par1DamageSource){
		if (this.getAttackTarget() != null)
			EntityExtension.For(this.getAttackTarget()).setDisableGravity(false);
		super.onDeath(par1DamageSource);
	}

	@Override
	public int getTotalArmorValue(){
		return 18;
	}

	@Override
	public void onUpdate(){
		super.onUpdate();

		if (this.getAttackTarget() != null){
			if (this.getCurrentAction() != BossActions.LONG_CASTING){
				EntityExtension.For(getAttackTarget()).setDisableGravity(false);
			}

			if (!this.world.isRemote && this.getDistanceSqToEntity(getAttackTarget()) > 64D && this.getCurrentAction() == BossActions.IDLE){
				this.getNavigator().tryMoveToEntityLiving(getAttackTarget(), 0.5f);
			}
		}

		if (world.isRemote){
			int halfDist = 8;
			int dist = 16;
			if (this.getCurrentAction() == BossActions.CHARGE){
				if (ticksInCurrentAction > 50){
					for (int i = 0; i < 2 * ArsMagicaReborn.config.getGFXLevel(); ++i){
						AMParticle smoke = (AMParticle)ArsMagicaReborn.proxy.particleManager.spawn(world, "smoke", posX, posY + 4, posZ);
						if (smoke != null){
							smoke.addRandomOffset(halfDist, 1, halfDist);
							smoke.SetParticleAlpha(1f);
							smoke.setParticleScale(1f);
							smoke.setMaxAge(20);
							smoke.AddParticleController(new ParticleHoldPosition(smoke, 10, 1, false));

						}
					}
				}
				if (ticksInCurrentAction > 66){
					ArsMagicaReborn.proxy.particleManager.BoltFromPointToPoint(
							world,
							posX + rand.nextDouble() - 0.5,
							posY + rand.nextDouble() - 0.5 + 2,
							posZ + rand.nextDouble() - 0.5,
							posX + rand.nextDouble() * dist - halfDist,
							posY + rand.nextDouble() * dist - halfDist,
							posZ + rand.nextDouble() * dist - halfDist);
				}
			}else if (this.getCurrentAction() == BossActions.LONG_CASTING){
				if (ticksInCurrentAction > 25 && ticksInCurrentAction < 150){
					for (int i = 0; i < 2 * ArsMagicaReborn.config.getGFXLevel(); ++i){
						AMParticle smoke = (AMParticle)ArsMagicaReborn.proxy.particleManager.spawn(world, "smoke", posX, posY + 4, posZ);
						if (smoke != null){
							smoke.addRandomOffset(halfDist, 1, halfDist);
							smoke.SetParticleAlpha(1f);
							smoke.setParticleScale(1f);
							smoke.setRGBColorI(ticksInCurrentAction < 85 ? 0xFFFFFF - 0x111111 * ((ticksInCurrentAction - 25) / 4) : 0x222222);
							smoke.setMaxAge(20);
							smoke.AddParticleController(new ParticleHoldPosition(smoke, 10, 1, false));

						}
					}
				}
			}
		}
	}

	@Override
	protected float modifyDamageAmount(DamageSource source, float damageAmt){
		if (source.isMagicDamage() || source.damageType.equals("magic"))
			damageAmt *= 2;
		if (source.damageType.equals("drown"))
			damageAmt *= 4;
		if (source.damageType.equals("DamageAMLightning"))
			damageAmt *= -1;
		return damageAmt;
	}

	@Override
	protected SoundEvent getHurtSound(){
		return AMSounds.LIGHTNING_GUARDIAN_HIT;
	}

	@Override
	protected SoundEvent getDeathSound(){
		return AMSounds.LIGHTNING_GUARDIAN_DEATH;
	}

	@Override
	protected SoundEvent getAmbientSound(){
		return AMSounds.LIGHTNING_GUARDIAN_IDLE;
	}

	@Override
	public SoundEvent getAttackSound(){
		return AMSounds.LIGHTNING_GUARDIAN_ATTACK_STATIC;
	}

	@Override
	protected void dropFewItems(boolean par1, int par2){
		if (par1)
			this.entityDropItem(new ItemStack(ItemDefs.infinityOrb, 1, 1), 0.0f);

		int i = rand.nextInt(4);

		for (int j = 0; j < i; j++){
			this.entityDropItem(new ItemStack(ItemDefs.essence, 1, ArsMagicaAPI.getAffinityRegistry().getId(Affinity.LIGHTNING)), 0.0f);
		}
		i = rand.nextInt(10);

		if (i < 3 && par1){
			this.entityDropItem(ItemDefs.lightningCharmEnchanted.copy(), 0.0f);
		}
	}


	@Override
	public void setAnimID(int id){
		setCurrentAction(BossActions.values()[id]);
	}


	@Override
	public void setAnimTick(int tick){
		this.ticksInCurrentAction = tick;
	}


	@Override
	public int getAnimID(){
		return this.currentAction.ordinal();
	}


	@Override
	public int getAnimTick(){
		return this.ticksInCurrentAction;
	}

	@Override
	protected Color getBarColor() {
		return Color.GREEN;
	}

}
