package amreborn.entity.render;

import amreborn.ArsMagicaReborn;
import amreborn.entity.EntityAirSled;
import amreborn.models.ModelAirGuardianHoverball;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderAirSled extends RenderLiving<EntityAirSled>{

	private static final ResourceLocation rLoc = new ResourceLocation(ArsMagicaReborn.MODID, "textures/mobs/bosses/air_guardian.png");

	public RenderAirSled(RenderManager manager){
		super(manager, new ModelAirGuardianHoverball(), 0.5f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityAirSled entity){
		return rLoc;
	}

}
