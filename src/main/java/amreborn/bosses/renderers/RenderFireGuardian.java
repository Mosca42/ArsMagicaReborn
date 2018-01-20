package amreborn.bosses.renderers;

import amreborn.ArsMagicaReborn;
import amreborn.bosses.EntityFireGuardian;
import amreborn.bosses.models.ModelFireGuardian;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderFireGuardian extends RenderBoss<EntityFireGuardian>{
	private static final ResourceLocation rLoc = new ResourceLocation(ArsMagicaReborn.MODID, "textures/mobs/bosses/fire_guardian.png");

	public RenderFireGuardian(RenderManager manager){
		super(manager, new ModelFireGuardian());
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityFireGuardian entity){
		return rLoc;
	}
}
