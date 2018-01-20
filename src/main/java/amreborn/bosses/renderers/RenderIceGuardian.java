package amreborn.bosses.renderers;

import amreborn.ArsMagicaReborn;
import amreborn.bosses.EntityWinterGuardian;
import amreborn.bosses.models.ModelWinterGuardian;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderIceGuardian extends RenderBoss<EntityWinterGuardian>{

	private static final ResourceLocation rLoc = new ResourceLocation(ArsMagicaReborn.MODID, "textures/mobs/bosses/ice_guardian.png");

	public RenderIceGuardian(RenderManager manager){
		super(manager, new ModelWinterGuardian());
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityWinterGuardian entity){
		return rLoc;
	}
}
