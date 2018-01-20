package amreborn.bosses.renderers;

import amreborn.ArsMagicaReborn;
import amreborn.bosses.EntityWaterGuardian;
import amreborn.bosses.models.ModelWaterGuardian;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderWaterGuardian extends RenderBoss<EntityWaterGuardian>{

	private static final ResourceLocation rLoc = new ResourceLocation(ArsMagicaReborn.MODID, "textures/mobs/bosses/water_guardian.png");

	public RenderWaterGuardian(RenderManager manager){
		super(manager, new ModelWaterGuardian());
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityWaterGuardian entity){
		return rLoc;
	}
}
