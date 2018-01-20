package amreborn.bosses.renderers;

import amreborn.ArsMagicaReborn;
import amreborn.bosses.EntityNatureGuardian;
import amreborn.bosses.models.ModelPlantGuardian;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderPlantGuardian extends RenderBoss<EntityNatureGuardian>{

	private static final ResourceLocation rLoc = new ResourceLocation(ArsMagicaReborn.MODID, "textures/mobs/bosses/plant_guardian.png");

	public RenderPlantGuardian(RenderManager manager){
		super(manager, new ModelPlantGuardian());
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityNatureGuardian entity){
		return rLoc;
	}

}
