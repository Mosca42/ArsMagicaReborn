package amreborn.entity.render;

import amreborn.ArsMagicaReborn;
import amreborn.entity.EntityHecate;
import amreborn.entity.models.ModelHecate;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderHecate extends RenderLiving<EntityHecate>{

	private ModelHecate model;
	private static final ResourceLocation rLoc = new ResourceLocation(ArsMagicaReborn.MODID, "textures/mobs/mobHecate.png");


	public RenderHecate(RenderManager manager){
		super(manager, new ModelHecate(), 0f);
		model = (ModelHecate)mainModel;
	}

	@Override
	public void doRender(EntityHecate hecate, double par2, double par4, double par6, float par8, float par9){
		setForwardRotation(hecate);
		setArmRotations(hecate);
		super.doRender(hecate, par2, par4, par6, par8, par9);
	}

	private void setForwardRotation(EntityHecate hecate){
		model.setMainRotationAngle(hecate.getForwardRotation());
	}

	private void setArmRotations(EntityHecate hecate){
		model.setLeftArmRotationOffset((float)hecate.getLeftArmOffset());
		model.setRightArmRotationOffset((float)hecate.getRightArmOffset());
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityHecate entity){
		return rLoc;
	}

}
