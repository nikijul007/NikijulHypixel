package com.nikijulHypixel.gui;

import org.lwjgl.opengl.GL11;

import com.nikijulHypixel.NikijulHypixel;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;

public class TextureButton extends GuiButton {

	public ResourceLocation buttonTexture;
	private int x;
	private int y;
	
	private String text;
	private String[] label;
	private int color;

	public TextureButton(int buttonId, int x, int y, int xTexture, int yTexture, String text, String location, int color) {
		super(buttonId, x, y, xTexture, yTexture, "");
		this.x = x;
		this.y = y;
		this.text = text;
		this.color = color;
		buttonTexture = new ResourceLocation(NikijulHypixel.MODID + ":textures/gui/items/" +location+ "/" + text.toLowerCase() + ".png");
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		if (this.visible) {
			FontRenderer fontrenderer = mc.fontRendererObj;
			mc.getTextureManager().bindTexture(buttonTexture);

			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.pushMatrix();

			GlStateManager.scale(1f / 12.8f, 1f / 12.8f, 1f);
			drawTexturedModalRect((int) (this.x * 12.8), (int) (this.y * 12.8), 0, 0, 256, 256);
			
			GlStateManager.scale(1 * 12.8f, 1 * 12.8f, 1);
			GlStateManager.scale(1/2f, 1/2f, 1f);
			
			
			label = text.split("_");
			
			switch (label.length) {
			case 1:
				
				this.drawCenteredString(fontrenderer, text, (int) ((x + this.width / 2) * 2), (int) ((y + (this.height - 8) / 2) * 2 + 5), color);
				
				break;
				
			case 2:
				
				this.drawCenteredString(fontrenderer, label[0], (int) ((x + this.width / 2) * 2), (int) ((y + (this.height - 8) / 2) * 2 ), color);
				this.drawCenteredString(fontrenderer, label[1], (int) ((x + this.width / 2) * 2), (int) ((y + (this.height - 8) / 2) * 2 + 8), color);
				
				break;
				
			case 3:
				
				this.drawCenteredString(fontrenderer, label[0], (int) ((x + this.width / 2) * 2), (int) ((y + (this.height - 8) / 2) * 2 -3), color);
				this.drawCenteredString(fontrenderer, label[1], (int) ((x + this.width / 2) * 2), (int) ((y + (this.height - 8) / 2) * 2 + 5), color);
				this.drawCenteredString(fontrenderer, label[2], (int) ((x + this.width / 2) * 2), (int) ((y + (this.height - 8) / 2) * 2 + 13), color);
				
				break;
				
			case 4:
				
				this.drawCenteredString(fontrenderer, label[0], (int) ((x + this.width / 2) * 2), (int) ((y + (this.height - 8) / 2) * 2 - 8), color);
				this.drawCenteredString(fontrenderer, label[1], (int) ((x + this.width / 2) * 2), (int) ((y + (this.height - 8) / 2) * 2), color);
				this.drawCenteredString(fontrenderer, label[2], (int) ((x + this.width / 2) * 2), (int) ((y + (this.height - 8) / 2) * 2 + 8), color);
				this.drawCenteredString(fontrenderer, label[3], (int) ((x + this.width / 2) * 2), (int) ((y + (this.height - 8) / 2) * 2 + 16), color);
				
				break;
			default:
				break;
			}
			
			
			
			
			GlStateManager.popMatrix();

			this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width
					&& mouseY < this.y + this.height;

			
			
			
		}
	}
}
