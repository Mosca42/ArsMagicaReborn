package amreborn.entity;

import amreborn.ArsMagicaReborn;
import amreborn.api.ArsMagicaAPI;
import amreborn.api.affinity.Affinity;
import amreborn.api.math.AMVector3;
import amreborn.armor.ArmorHelper;
import amreborn.armor.infusions.GenericImbuement;
import amreborn.particles.AMParticle;
import amreborn.particles.AMParticleDefs;
import amreborn.particles.ParticleFadeOut;
import amreborn.particles.ParticleMoveOnHeading;
import net.minecraft.block.Block;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityFlicker extends EntityAmbientCreature{

	private static final DataParameter<Integer> WATCHER_FLICKERTYPE = EntityDataManager.createKey(EntityFlicker.class, DataSerializers.VARINT);
	private static final DataParameter<Boolean> WATCHER_AMBIENTFLICK = EntityDataManager.createKey(EntityFlicker.class, DataSerializers.BOOLEAN);

	private static final int DIRECTION_CHANGE_TIME = 200;

	private AMVector3 targetPosition = null;
	private AMVector3 normalizedMovementVector = AMVector3.zero();

	private int flickCount = 0;

	public EntityFlicker(World par1World){
		super(par1World);
		this.setSize(0.5f, 0.5f);
		setFlickerType(ArsMagicaAPI.getAffinityRegistry().getRandomObject(getRNG()));
	}

	@Override
	protected void entityInit(){
		super.entityInit();
		this.dataManager.register(WATCHER_FLICKERTYPE, 0);
		this.dataManager.register(WATCHER_AMBIENTFLICK, false);
	}

	@Override
	public void setDead(){
		ArsMagicaReborn.proxy.decrementFlickerCount();
		super.setDead();
	}

	public void setFlickerType(Affinity affinity){
		this.dataManager.set(WATCHER_FLICKERTYPE, ArsMagicaAPI.getAffinityRegistry().getId(affinity));
	}

	public Affinity getFlickerAffinity(){
		return ArsMagicaAPI.getAffinityRegistry().getObjectById(dataManager.get(WATCHER_FLICKERTYPE));
	}

	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2){
		if (this.world.isRemote)
			return false;
		flick();
		return !par1DamageSource.isUnblockable();
	}

	@Override
	public boolean doesEntityNotTriggerPressurePlate(){
		return true;
	}

	@Override
	public void fall(float par1, float par2){
	}

	@Override
	public boolean canBePushed(){
		return false;
	}

	@Override
	public boolean canBreatheUnderwater(){
		return true;
	}

	@Override
	public boolean canTriggerWalking(){
		return false;
	}

	@Override
	public void readFromNBT(NBTTagCompound par1nbtTagCompound){
		ArsMagicaReborn.proxy.incrementFlickerCount();
		super.readFromNBT(par1nbtTagCompound);
	}

	@Override
	public void onUpdate(){
		super.onUpdate();

		this.motionX = 0;
		this.motionY = 0;
		this.motionZ = 0;

		long time = world.getWorldTime() % 24000;
		if (!world.isRemote && time >= 18500 && time <= 18600){
			//this.dataManager.set(WATCHER_AMBIENTFLICK, (byte)1);
			this.setDead();
			return;
		}

		boolean playerClose = false;
		AMVector3 me = new AMVector3(this);
		if (!world.isRemote){
			for (Object o : world.playerEntities){
				if (me.distanceSqTo(new AMVector3((EntityPlayer)o)) < 25){

					ItemStack chestArmor = ((EntityPlayer)o).getItemStackFromSlot(EntityEquipmentSlot.CHEST);
					if (chestArmor == null || !ArmorHelper.isInfusionPreset(chestArmor, GenericImbuement.flickerLure))
						playerClose = true;
					break;
				}
			}
		}

		if (this.ticksExisted > 100 && playerClose && !this.dataManager.get(WATCHER_AMBIENTFLICK)){
			if (this.getActivePotionEffects().size() == 0 || (this.getActivePotionEffects().size() == 1 && world.rand.nextDouble() < 0.1f))
				this.dataManager.set(WATCHER_AMBIENTFLICK, true);
		}else if (this.dataManager.get(WATCHER_AMBIENTFLICK)){
			flickCount++;
			if (world.isRemote && flickCount > 7)
				flick(); //client flick
			else if (!world.isRemote && flickCount > 10)
				flick(); //server flick
		}

		if (world.isRemote){
			//for (int i = 0; i < + 1; ++i){
			if (getRNG().nextInt(10) < ArsMagicaReborn.config.getGFXLevel()){
				AMParticle effect = (AMParticle)ArsMagicaReborn.proxy.particleManager.spawn(world, AMParticleDefs.getParticleForAffinity(getFlickerAffinity()), posX, posY, posZ);
				if (effect != null){
					effect.addRandomOffset(this.width, this.height, this.width);
					effect.setDontRequireControllers();
					effect.setMaxAge(10);
					if (getFlickerAffinity() == Affinity.EARTH)
						effect.setParticleScale(0.01f + rand.nextFloat() * 0.05f);
					else
						effect.setParticleScale(0.05f + rand.nextFloat() * 0.05f);
				}
			}
		}
	}
	
	

	@Override
	protected void updateAITasks(){
		super.updateAITasks();

		AMVector3 me = new AMVector3(this);

		boolean needsNewPath = targetPosition == null || this.ticksExisted % DIRECTION_CHANGE_TIME == 0;
		if (needsNewPath && world.rand.nextDouble() < 0.1f)
			pickNewTargetPosition();

		if (targetPosition == null) //this represents the pause state in between picking new waypoints
			return;
		
		if (me.distanceSqTo(targetPosition) > 400f){
			targetPosition = null;
			return;
		}
		
		if (me.distanceSqTo(targetPosition) < 1f){
			targetPosition = null;
			return;
		}

		this.rotationYaw = AMVector3.anglePreNorm(me, targetPosition);

		normalizedMovementVector = me.copy().sub(targetPosition).normalize();

		if (normalizedMovementVector.y > 0)
			rotatePitchTowards(-70 * normalizedMovementVector.y, 30);
		else
			rotatePitchTowards(0, 30);

		float speed = 0.2f;
		this.addVelocity(-normalizedMovementVector.x * speed, -normalizedMovementVector.y * speed, -normalizedMovementVector.z * speed);
	}

	public AMVector3 getNormalizedMovement(){
		return this.normalizedMovementVector;
	}

	private void rotatePitchTowards(float p, float step){
		if (this.rotationPitch != p){
			if (step > 0 && this.rotationPitch + step > p){
				step = p - this.rotationPitch;
			}else if (step < 0 && this.rotationPitch + step < p){
				step = p - this.rotationPitch;
			}
			this.rotationPitch += step;
		}
	}

	private void flick(){
		if (this.world.isRemote){
			for (int i = 0; i < 10 * ArsMagicaReborn.config.getGFXLevel(); ++i){
				AMParticle particle = (AMParticle)ArsMagicaReborn.proxy.particleManager.spawn(world, "radiant", posX, posY, posZ);
				if (particle != null){
					particle.AddParticleController(
							new ParticleMoveOnHeading(
									particle,
									world.rand.nextDouble() * 360,
									world.rand.nextDouble() * 360,
									world.rand.nextDouble() * 0.3f + 0.01f,
									1,
									false));
					particle.setRGBColorI(getFlickerAffinity().getColor());
					particle.AddParticleController(new ParticleFadeOut(particle, 1, false).setFadeSpeed((float)(world.rand.nextDouble() * 0.1 + 0.05)).setKillParticleOnFinish(true));
					particle.setIgnoreMaxAge(true);
					particle.setParticleScale(0.1f);
				}
			}
		}else{
			this.setDead();
		}
	}

	private void pickNewTargetPosition(){
		int groundLevel = 0;
		Affinity aff = this.getFlickerAffinity();
		if (aff == Affinity.WATER) {
			for (int i = 0; i < 5; ++i){
				targetPosition = new AMVector3(this.posX - 5 + world.rand.nextInt(10), this.posY - 5 + world.rand.nextInt(10), this.posZ - 5 + world.rand.nextInt(10));
				Block block = world.getBlockState(targetPosition.toBlockPos()).getBlock();
				if (block == Blocks.WATER || block == Blocks.FLOWING_WATER){
					break;
				}
			}
		} else if (aff == Affinity.AIR) {
			groundLevel = getTopBlockNearMe();
			targetPosition = new AMVector3(this.posX - 5 + world.rand.nextInt(10), groundLevel + 10 + world.rand.nextInt(15), this.posZ - 5 + world.rand.nextInt(10));			
		} else if (aff == Affinity.EARTH) {
			groundLevel = getTopBlockNearMe();
			targetPosition = new AMVector3(this.posX - 5 + world.rand.nextInt(10), groundLevel + world.rand.nextInt(1) + 1, this.posZ - 5 + world.rand.nextInt(10));			
		} else {
			groundLevel = getTopBlockNearMe();
			targetPosition = new AMVector3(this.posX - 5 + world.rand.nextInt(10), groundLevel + 3 + world.rand.nextInt(5), this.posZ - 5 + world.rand.nextInt(10));
		}
	}

	private int getTopBlockNearMe(){
		
		BlockPos checkPos = this.getPosition();
		
		while (checkPos.getY() > 0 && world.isAirBlock(checkPos))
			checkPos = checkPos.down();
		while (checkPos.getY() < world.getActualHeight() && !world.isAirBlock(checkPos))
			checkPos = checkPos.up();

		return checkPos.getY();
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound par1nbtTagCompound){
		super.writeEntityToNBT(par1nbtTagCompound);
		par1nbtTagCompound.setInteger("flickerType", dataManager.get(WATCHER_FLICKERTYPE));
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound par1nbtTagCompound){
		super.readEntityFromNBT(par1nbtTagCompound);
		dataManager.set(WATCHER_FLICKERTYPE, par1nbtTagCompound.getInteger("flickerType"));
		ArsMagicaReborn.proxy.incrementFlickerCount();
	}
}
