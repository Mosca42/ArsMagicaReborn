package amreborn.bosses.renderers;

import amreborn.ArsMagicaReborn;
import amreborn.bosses.EntityEarthGuardian;
import amreborn.bosses.models.ModelEarthGuardian;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderEarthGuardian extends RenderBoss<EntityEarthGuardian>{

	private static final ResourceLocation rLoc = new ResourceLocation(ArsMagicaReborn.MODID, "textures/mobs/bosses/earth_guardian.png");

	public RenderEarthGuardian(RenderManager manager){
		super(manager, new ModelEarthGuardian());
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityEarthGuardian entity){
		return rLoc;
	}
}
