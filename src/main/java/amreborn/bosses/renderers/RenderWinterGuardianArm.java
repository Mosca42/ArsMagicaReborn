package amreborn.bosses.renderers;

import amreborn.ArsMagicaReborn;
import amreborn.bosses.models.ModelWinterGuardianArm;
import amreborn.entity.EntityWinterGuardianArm;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderWinterGuardianArm extends RenderLiving<EntityWinterGuardianArm>{

	private static final ResourceLocation rLoc = new ResourceLocation(ArsMagicaReborn.MODID, "textures/mobs/bosses/ice_guardian.png");

	public RenderWinterGuardianArm(RenderManager manager){
		super(manager, new ModelWinterGuardianArm(), 0.5f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityWinterGuardianArm entity){
		return rLoc;
	}

}
