package amreborn.bosses.renderers;

import amreborn.ArsMagicaReborn;
import amreborn.bosses.EntityLifeGuardian;
import amreborn.bosses.models.ModelLifeGuardian;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderLifeGuardian extends RenderBoss<EntityLifeGuardian>{

	private static final ResourceLocation rLoc = new ResourceLocation(ArsMagicaReborn.MODID, "textures/mobs/bosses/life_guardian.png");

	public RenderLifeGuardian(RenderManager manager){
		super(manager, new ModelLifeGuardian());
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityLifeGuardian entity){
		return rLoc;
	}

}
