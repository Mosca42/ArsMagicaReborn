package amreborn.utils;

import java.util.UUID;

import com.mojang.authlib.GameProfile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

public class DummyEntityPlayer extends EntityPlayer {

	private EntityLivingBase trackEntity = null;

	public DummyEntityPlayer(World world) {
		super(world, new GameProfile(UUID.randomUUID(), "dummyplayer"));
	}

	public static EntityPlayer fromEntityLiving(EntityLivingBase entity) {
		if (entity instanceof EntityPlayer)
			return (EntityPlayer) entity;

		DummyEntityPlayer dep = new DummyEntityPlayer(entity.world);
		dep.setPosition(entity.posX, entity.posY, entity.posZ);
		dep.setRotation(entity.rotationYaw, entity.rotationPitch);
		dep.trackEntity = entity;

		return dep;
	}

	public void copyEntityLiving(EntityLivingBase entity) {
		this.setPosition(entity.posX, entity.posY, entity.posZ);
		this.setRotation(entity.rotationYaw, entity.rotationPitch);
		this.trackEntity = entity;
		this.world = entity.world;
	}

	@Override
	public void onUpdate() {
		if (trackEntity != null) {
			this.setPosition(trackEntity.posX, trackEntity.posY, trackEntity.posZ);
			this.setRotation(trackEntity.rotationYaw, trackEntity.rotationPitch);

			this.motionX = trackEntity.motionX;
			this.motionY = trackEntity.motionY;
			this.motionZ = trackEntity.motionZ;
		}
	}

	@Override
	public boolean canUseCommand(int permLevel, String commandName) {
		return false;
	}


	@Override
	public void sendMessage(ITextComponent component) {
	}

	@Override
	public boolean isSpectator() {
		return false;
	}

	@Override
	public boolean isCreative() {
		return false;
	}

}
