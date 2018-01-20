package amreborn.entity.render;

import amreborn.ArsMagicaReborn;
import amreborn.entity.EntityDarkling;
import amreborn.models.ModelDarkling;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderDarkling extends RenderLiving<EntityDarkling>{

	private static final ResourceLocation rLoc = new ResourceLocation(ArsMagicaReborn.MODID, "textures/mobs/darkling.png");

	public RenderDarkling(RenderManager manager){
		super(manager, new ModelDarkling(), 0.5f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityDarkling par1Entity){
		return rLoc;
	}
}
