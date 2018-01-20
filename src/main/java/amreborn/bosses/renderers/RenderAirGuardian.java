package amreborn.bosses.renderers;

import amreborn.ArsMagicaReborn;
import amreborn.bosses.EntityAirGuardian;
import amreborn.bosses.models.ModelAirGuardian;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderAirGuardian extends RenderBoss<EntityAirGuardian>{

	private static final ResourceLocation rLoc = new ResourceLocation(ArsMagicaReborn.MODID, "textures/mobs/bosses/air_guardian.png");

	public RenderAirGuardian(RenderManager manager){
		super(manager, new ModelAirGuardian());
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityAirGuardian entity){
		return rLoc;
	}
}
