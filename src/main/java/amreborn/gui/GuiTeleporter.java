package amreborn.gui;

import static org.lwjgl.opengl.GL11.GL_SCISSOR_TEST;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glScissor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import amreborn.blocks.tileentity.TileEntityTeleporter;
import amreborn.defs.ItemDefs;
import amreborn.network.PacketHandler;
import amreborn.network.packets.PacketTeleportation;
import amreborn.network.packets.PacketTeleporterName;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiTeleporter extends GuiScreen {

	private TileEntityTeleporter tile;
	private int selectedIndex = -1;
	private byte direction = 0;
	private float selectionTime = 0;
	private int lastSelected = this.selectedIndex;
	private GuiTextField nameField = null;
	private ArrayList<String> names = new ArrayList<String>();

	public GuiTeleporter(TileEntityTeleporter tile) {
		this.tile = tile;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		int height = this.height / 2 - this.fontRendererObj.FONT_HEIGHT / 2;

		this.selectionTime = Math.min(this.selectionTime + partialTicks / 3f, 1.0f);

			this.drawCenteredString(this.fontRendererObj, "Press enter to teleport", this.width / 2, this.height - 20, 0xFF00);

		int frameSize = 10;
		int innerColor = 0xFFCCCCCC;
		int outerColor = 0xFFFFFFFF;
		Gui.drawRect(this.width / 3 - frameSize, 25 - frameSize, this.width / 3 * 2 + frameSize, this.height - 45 + frameSize, outerColor);
		Gui.drawRect(this.width / 3, 25, this.width / 3 * 2, this.height - 45, innerColor);

		glEnable(GL_SCISSOR_TEST);
		glScissor(this.mc.displayWidth / 3, 90, this.mc.displayWidth / 3, this.mc.displayHeight - 140);

		GlStateManager.pushMatrix();

		int space = (this.fontRendererObj.FONT_HEIGHT + 10);

		int offset = (int) (space * this.selectionTime);
		if (this.direction == -1)
			GlStateManager.translate(0, -offset + space, 0);
		if (this.direction == 1)
			GlStateManager.translate(0, offset - space, 0);

		int i = 0;
		for (String name : this.names) {

			if (i == this.selectedIndex)
				name = ">    " + name;

			String[] split = name.split("#");
			if (split.length != 2)
				continue;
			int wordWidth = this.fontRendererObj.getStringWidth(name);
			int nameWidth = this.fontRendererObj.getStringWidth(split[0] + " <");
			if (i == this.selectedIndex)
			this.fontRendererObj.drawString(split[0] + " <", this.width / 2 - wordWidth / 2, height + (i - this.selectedIndex) * space, 0x888888);
			else
				this.fontRendererObj.drawString(split[0] + " <", this.width / 2 - wordWidth / 2, height + (i - this.selectedIndex) * space, 0x888888);

			// this.fontRendererObj.drawString("#" + split[1], this.width / 2 - wordWidth /
			// 2 + nameWidth, height + (i - this.selectedIndex) * space, 0xAA00);
			i++;

		}
		glDisable(GL_SCISSOR_TEST);

		GlStateManager.popMatrix();

		frameSize = 5;
		Gui.drawRect(15 - frameSize * 2, 50 - frameSize * 2, this.nameField.xPosition + this.nameField.width + frameSize * 2, 15 / 2 + this.fontRendererObj.FONT_HEIGHT * 6 + 50 + frameSize, outerColor);
		Gui.drawRect(15 - frameSize, 50 - frameSize, this.nameField.xPosition + this.nameField.width + frameSize, 15 / 2 + this.fontRendererObj.FONT_HEIGHT * 6 + 50, innerColor);
		this.fontRendererObj.drawString("Name : ", 15, 15 / 2 - this.fontRendererObj.FONT_HEIGHT / 2 + 50, 0);
		this.nameField.drawTextBox();
		this.fontRendererObj.drawString("Owner : " + this.tile.getOwnerName(), 15, 15 / 2 + this.fontRendererObj.FONT_HEIGHT * 2 + 50, 0);
		this.fontRendererObj.drawString("Private : " + (this.tile.isPrivatized() ? "yes" : "no"), 15, 15 / 2 + this.fontRendererObj.FONT_HEIGHT * 4 + 50, 0);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	public void handleMouseInput() throws IOException {
		int wheel = Mouse.getDWheel();
		if (wheel < 0) {
			this.selectedIndex++;
		} else if (wheel > 0) {
			this.selectedIndex--;
		}
		super.handleMouseInput();
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		this.nameField.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		super.keyTyped(typedChar, keyCode);
		if (this.nameField.textboxKeyTyped(typedChar, keyCode)) {
			String name = this.nameField.getText().trim();
			if (name.length() > 2 && name.length() <= 15) {
				PacketHandler.INSTANCE.sendToServer(new PacketTeleporterName(this.tile.getId(), this.nameField.getText()));
			}
		}
		if (keyCode == Keyboard.KEY_DOWN && this.direction == 0) {
			this.selectedIndex++;
			this.direction = -1;
		} else if (keyCode == Keyboard.KEY_UP && this.direction == 0) {
			this.selectedIndex--;
			this.direction = 1;
		} else if (keyCode == Keyboard.KEY_RETURN || keyCode == Keyboard.KEY_NUMPADENTER) {
			int i = 0;
			for (String str : this.names) {
				if (i == this.selectedIndex) {
					String[] split = str.split("#");
					if (split.length != 2)
						return;
					try {
						PacketHandler.INSTANCE.sendToServer(new PacketTeleportation(this.tile.getId(), Integer.valueOf(split[1])));
					} catch (NumberFormatException e) {
					}
					this.mc.player.closeScreen();
					return;
				}
				i++;
			}
		}
	}

	@Override
	public void initGui() {
		boolean isOwner = this.mc.player.getName().equals(this.tile.getOwnerName());
		this.nameField = new GuiTextField(0, this.fontRendererObj, this.fontRendererObj.getStringWidth("Name : ") + 15, 50, 100, 15);
		this.nameField.setText(this.tile.getTeleporterName());
		this.nameField.setEnabled(isOwner);
		this.updateScreen();
		Keyboard.enableRepeatEvents(true);
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	public void updateScreen() {
		EntityPlayer player = Minecraft.getMinecraft().player;
		if (!this.tile.isValid() || this.tile.getPos().distanceSq(player.getPosition().getX() + 0.5D, player.getPosition().getY() + 0.5D, player.getPosition().getZ() + 0.5D) > 64D) {
			Minecraft.getMinecraft().player.closeScreen();
		} else {
			Set<String> list = new HashSet<String>();
			list.addAll(this.tile.getTeleportersNames());
			InventoryPlayer inv = Minecraft.getMinecraft().player.inventory;
			for (ItemStack s : inv.mainInventory) {
				if (s.isEmpty() || s.getItem() != ItemDefs.teleporterKey)
					continue;
				if (s.hasTagCompound()) {
					if (s.getTagCompound().hasKey("teleporters")) {
						NBTTagList l = s.getTagCompound().getTagList("teleporters", NBT.TAG_COMPOUND);
						for (int i = 0; i < l.tagCount(); i++) {
							NBTTagCompound nbt = l.getCompoundTagAt(i);
							list.add(nbt.getString("name") + "#" + nbt.getInteger("id"));
						}
					} else if (s.getTagCompound().hasKey("teleportersNames")) {
						NBTTagList l = s.getTagCompound().getTagList("teleporters", NBT.TAG_STRING);
						for (int i = 0; i < l.tagCount(); i++) {
							list.add(l.getStringTagAt(i));
						}
					}
				}
			}
			this.names.clear();
			this.names.addAll(list);
			this.names.sort(new Comparator<String>() {

				@Override
				public int compare(String t1, String t2) {
					String[] s1 = t1.split("#");
					String[] s2 = t2.split("#");
					if (s1.length == 2 && s2.length != 2)
						return 1;
					if (s1.length != 2 && s2.length == 2)
						return -1;
					if (s1.length != 2 && s2.length != 2)
						return 0;
					try {
						int id1 = Integer.valueOf(s1[1]);
						int id2 = Integer.valueOf(s2[1]);
						return id1 > id2 ? 1 : id1 < id2 ? -1 : 0;
					} catch (NumberFormatException e) {
						return 0;
					}
				}
			});

			if (this.names.isEmpty()) {
				this.selectedIndex = -1;
			} else {
				if (this.selectedIndex < 0) {
					this.selectedIndex = 0;
				} else {
					while (this.selectedIndex >= this.names.size()) {
						this.selectedIndex--;
					}
				}

			}
			if (this.lastSelected != this.selectedIndex && this.direction != 0) {
				this.selectionTime = 0;
			}

			if (this.selectionTime >= 1.0f)
				this.direction = 0;

			this.lastSelected = this.selectedIndex;

		}
	}

}
