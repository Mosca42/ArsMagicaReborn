package amreborn.gui;

import org.lwjgl.opengl.GL11;

import amreborn.ArsMagicaReborn;
import amreborn.blocks.tileentity.TileEntityAstralBarrier;
import amreborn.container.ContainerAstralBarrier;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiAstralBarrier extends GuiContainer{

	GuiButton radiusButton;

	private static final ResourceLocation background = new ResourceLocation(ArsMagicaReborn.MODID, "textures/gui/astralbarriergui.png");

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j){
		mc.renderEngine.bindTexture(background);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int l = (width - xSize) / 2;
		int i1 = (height - ySize) / 2;
		drawTexturedModalRect(l, i1, 0, 0, xSize, ySize);
	}

	public GuiAstralBarrier(InventoryPlayer inventoryplayer, TileEntityAstralBarrier astralBarrierEntity){
		super(new ContainerAstralBarrier(inventoryplayer, astralBarrierEntity));
		xSize = 176;
		ySize = 180;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2){
	}
}
