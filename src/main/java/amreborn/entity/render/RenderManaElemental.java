package amreborn.entity.render;

import amreborn.ArsMagicaReborn;
import amreborn.entity.EntityManaElemental;
import amreborn.entity.models.ModelManaElemental;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderManaElemental extends RenderBiped<EntityManaElemental>{

	private static final ResourceLocation rLoc = new ResourceLocation(ArsMagicaReborn.MODID, "textures/mobs/ManaElemental.png");

	public RenderManaElemental(RenderManager renderManager){
		super(renderManager, new ModelManaElemental(), 0.5f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityManaElemental par1Entity){
		return rLoc;
	}

}
