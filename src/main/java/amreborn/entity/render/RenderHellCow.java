package amreborn.entity.render;

import amreborn.ArsMagicaReborn;
import amreborn.entity.EntityHellCow;
import amreborn.models.ModelHellCow;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderHellCow extends RenderBiped<EntityHellCow>{

	private static final ResourceLocation rLoc = new ResourceLocation(ArsMagicaReborn.MODID, "textures/mobs/HellCow.png");

	public RenderHellCow(RenderManager manager){
		super(manager, new ModelHellCow(), 0.5f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityHellCow entity){
		return rLoc;
	}
}
