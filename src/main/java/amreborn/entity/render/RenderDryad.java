package amreborn.entity.render;

import amreborn.ArsMagicaReborn;
import amreborn.entity.EntityDryad;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderDryad extends RenderBiped<EntityDryad>{

	private static final ResourceLocation rLoc = new ResourceLocation(ArsMagicaReborn.MODID, "textures/mobs/mobDryad.png");

	public RenderDryad(RenderManager manager){
		super(manager, new ModelBiped(), 0.5f);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityDryad par1Entity){
		return rLoc;
	}

}
