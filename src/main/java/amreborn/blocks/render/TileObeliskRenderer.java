package amreborn.blocks.render;

import org.lwjgl.opengl.GL11;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;

import amreborn.ArsMagicaReborn;
import amreborn.blocks.BlockEssenceGenerator;
import amreborn.blocks.tileentity.TileEntityObelisk;
import amreborn.defs.BlockDefs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.obj.OBJModel;
import net.minecraftforge.common.model.TRSRTransformation;

public class TileObeliskRenderer extends TileEntitySpecialRenderer<TileEntityObelisk> {

	IModel defaultModel;
	IModel activeModel;
	IModel highPowerModel;
	IModel runesModel;
	IBakedModel defaultBakedModel;
	IBakedModel activeBakedModel;
	IBakedModel highPowerBakedModel;
	IBakedModel runesBakedModel;

	private void bake() {
		try {
			defaultModel = ModelLoaderRegistry.getModel(new ResourceLocation(ArsMagicaReborn.MODID, "block/obelisk.obj"));
			activeModel = ((OBJModel) defaultModel).retexture(ImmutableMap.of("#Material", ArsMagicaReborn.MODID + ":blocks/custom/obelisk_active"));
			highPowerModel = ((OBJModel) defaultModel).retexture(ImmutableMap.of("#Material", ArsMagicaReborn.MODID + ":blocks/custom/obelisk_active_highpower"));
			runesModel = ((OBJModel) defaultModel).retexture(ImmutableMap.of("#Material", ArsMagicaReborn.MODID + ":blocks/custom/obelisk_runes"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		Function<ResourceLocation, TextureAtlasSprite> getter = location -> Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());
		defaultBakedModel = defaultModel.bake(TRSRTransformation.identity(), DefaultVertexFormats.ITEM, getter);
		activeBakedModel = activeModel.bake(TRSRTransformation.identity(), DefaultVertexFormats.ITEM, getter);
		highPowerBakedModel = highPowerModel.bake(TRSRTransformation.identity(), DefaultVertexFormats.ITEM, getter);
		runesBakedModel = runesModel.bake(TRSRTransformation.identity(), DefaultVertexFormats.ITEM, getter);
	}

	
	 
	@Override
	public void renderTileEntityAt(TileEntityObelisk te, double x, double y, double z, float partialTicks, int destroyStage) {
//		if (!te.getWorld().isBlockLoaded(te.getPos(), false) || te.getWorld().getBlockState(te.getPos()).getBlock() != BlockDefs.obelisk) {
//			return;
//		}

		GlStateManager.pushAttrib();
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);
		GlStateManager.disableRescaleNormal();
		GlStateManager.pushMatrix();
		RenderHelper.disableStandardItemLighting();
		EnumFacing facing = EnumFacing.NORTH;
		if (te.hasWorld()) {
			IBlockState state = te.getWorld().getBlockState(te.getPos());
			facing = state.getValue(BlockEssenceGenerator.FACING);
		}
		if (facing == EnumFacing.WEST || facing == EnumFacing.SOUTH) {
			GlStateManager.translate(0, 0, 0.5);
		GlStateManager.scale(0.5, 0.5, 0.5);
		}
		if (facing == EnumFacing.SOUTH || facing == EnumFacing.EAST)
			GlStateManager.translate(1, 0, 0);
		GlStateManager.rotate(180 - facing.getHorizontalAngle(), 0, 1, 0);
		GlStateManager.translate(-te.getPos().getX(), -te.getPos().getY(), -te.getPos().getZ());
		if (Minecraft.isAmbientOcclusionEnabled())
			GlStateManager.shadeModel(GL11.GL_SMOOTH);
		else
			GlStateManager.shadeModel(GL11.GL_FLAT);
		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		Tessellator tessellator = Tessellator.getInstance();
		tessellator.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
		if (te.hasWorld())
			Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelRenderer().renderModel(te.getWorld(), getBakedModel(te), te.getWorld().getBlockState(te.getPos()), te.getPos(), tessellator.getBuffer(), false);
		else {
			Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelRenderer().renderModel(Minecraft.getMinecraft().world, getBakedModel(te), BlockDefs.obelisk.getDefaultState(), new BlockPos(0, 0, 0), tessellator.getBuffer(), false);
		}
			tessellator.draw();

		RenderHelper.enableStandardItemLighting();
		GlStateManager.popMatrix();
		GlStateManager.enableRescaleNormal();
		GlStateManager.popMatrix();
		GlStateManager.popAttrib();
	}

	private IBakedModel getBakedModel(TileEntityObelisk obelisk) {
		bake();
		return defaultBakedModel;
	}

}
