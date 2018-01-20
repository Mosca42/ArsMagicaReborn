package amreborn.utils;

import amreborn.ArsMagicaReborn;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class DimensionUtilities{
	public static void doDimensionTransfer(EntityLivingBase entity, int dimension){

		if (entity instanceof EntityPlayerMP){
			EntityPlayerMP player = (EntityPlayerMP)entity;
			new AMTeleporter(player.mcServer.worldServerForDimension(dimension)).teleport(entity);
		}else{
			entity.world.theProfiler.startSection("changeDimension");
			MinecraftServer minecraftserver = FMLCommonHandler.instance().getMinecraftServerInstance();
			int j = entity.dimension;
			WorldServer worldserver = minecraftserver.worldServerForDimension(j);
			WorldServer worldserver1 = minecraftserver.worldServerForDimension(dimension);
			entity.dimension = dimension;
			entity.world.removeEntity(entity);
			entity.isDead = false;
			entity.world.theProfiler.startSection("reposition");
			minecraftserver.getPlayerList().transferEntityToWorld(entity, j, worldserver, worldserver1, new AMTeleporter(worldserver1));
			entity.world.theProfiler.endStartSection("reloading");
			Entity e = EntityList.createEntityByIDFromName(new ResourceLocation(ArsMagicaReborn.MODID, EntityList.getEntityString(entity)), worldserver1);

			if (e != null){
				e.readFromNBT(entity.writeToNBT(new NBTTagCompound()));
				worldserver1.spawnEntity(e);
			}

			entity.isDead = true;
			entity.world.theProfiler.endSection();
			worldserver.resetUpdateEntityTick();
			worldserver1.resetUpdateEntityTick();
			entity.world.theProfiler.endSection();
		}
	}
}
