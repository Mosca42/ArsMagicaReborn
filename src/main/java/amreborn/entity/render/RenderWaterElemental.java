package amreborn.entity.render;

import amreborn.ArsMagicaReborn;
import amreborn.entity.EntityWaterElemental;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderWaterElemental extends RenderBiped<EntityWaterElemental>{
	private static final ResourceLocation rLoc = new ResourceLocation(ArsMagicaReborn.MODID, "textures/mobs/mobWaterElemental.png");

	public RenderWaterElemental(RenderManager renderManager){
		super(renderManager, new ModelZombie(), 0.5f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityWaterElemental par1Entity){
		return rLoc;
	}
}
