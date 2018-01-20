package amreborn.bosses.renderers;

import amreborn.ArsMagicaReborn;
import amreborn.bosses.EntityEnderGuardian;
import amreborn.bosses.models.ModelEnderGuardian;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderEnderGuardian extends RenderBoss<EntityEnderGuardian>{

	private static final ResourceLocation rLoc = new ResourceLocation(ArsMagicaReborn.MODID, "textures/mobs/bosses/ender_guardian.png");

	public RenderEnderGuardian(RenderManager manager){
		super(manager, new ModelEnderGuardian());
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityEnderGuardian entity){
		return rLoc;
	}
}
