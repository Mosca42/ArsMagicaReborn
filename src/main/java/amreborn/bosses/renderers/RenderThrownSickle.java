package amreborn.bosses.renderers;

import amreborn.ArsMagicaReborn;
import amreborn.bosses.models.ModelPlantGuardianSickle;
import amreborn.entity.EntityThrownSickle;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderThrownSickle extends RenderLiving<EntityThrownSickle>{

	private static final ResourceLocation rLoc = new ResourceLocation(ArsMagicaReborn.MODID, "textures/mobs/bosses/plant_guardian.png");

	public RenderThrownSickle(RenderManager manager){
		super(manager, new ModelPlantGuardianSickle(), 0.5f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityThrownSickle entity){
		return rLoc;
	}

}
