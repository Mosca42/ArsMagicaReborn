package amreborn.bosses.renderers;

import amreborn.ArsMagicaReborn;
import amreborn.bosses.models.ModelThrownRock;
import amreborn.entity.EntityThrownRock;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderThrownRock extends RenderLiving<EntityThrownRock>{

	private static final ResourceLocation rLoc = new ResourceLocation(ArsMagicaReborn.MODID, "textures/mobs/bosses/earth_guardian.png");

	public RenderThrownRock(RenderManager manager){
		super(manager, new ModelThrownRock(), 0.5f);
	}
	
	@Override
	public void doRender(EntityThrownRock entity, double x, double y, double z, float entityYaw, float partialTicks) {
		if (!((EntityThrownRock)entity).getIsShootingStar())
			super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityThrownRock entity){
		return rLoc;
	}

}
