package amreborn.bosses.renderers;

import amreborn.ArsMagicaReborn;
import amreborn.bosses.EntityArcaneGuardian;
import amreborn.bosses.models.ModelArcaneGuardian;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderArcaneGuardian extends RenderBoss<EntityArcaneGuardian>{

	private static final ResourceLocation rLoc = new ResourceLocation(ArsMagicaReborn.MODID, "textures/mobs/bosses/arcane_guardian.png");

	public RenderArcaneGuardian(RenderManager manager){
		super(manager, new ModelArcaneGuardian());
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityArcaneGuardian entity){
		return rLoc;
	}

}
