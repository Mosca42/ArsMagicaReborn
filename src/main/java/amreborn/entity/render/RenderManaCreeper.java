package amreborn.entity.render;

import amreborn.ArsMagicaReborn;
import net.minecraft.client.renderer.entity.RenderCreeper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.ResourceLocation;

public class RenderManaCreeper extends RenderCreeper{

	public RenderManaCreeper(RenderManager renderManagerIn) {
		super(renderManagerIn);
	}

	private static final ResourceLocation rLoc = new ResourceLocation(ArsMagicaReborn.MODID, "textures/mobs/mana_creeper.png");

	@Override
	protected ResourceLocation getEntityTexture(EntityCreeper entity){
		return rLoc;
	}

}
