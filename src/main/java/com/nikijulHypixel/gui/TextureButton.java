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
	private final int x;
	private final int y;

	public TextureButton(int buttonId, int x, int y, int xTexture, int yTexture, String location) {
		super(buttonId, x, y, xTexture, yTexture, "");
		this.x = x;
		this.y = y;
		buttonTexture = new ResourceLocation(NikijulHypixel.MODID + ":textures/gui/" + location);
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		if (this.visible) {
			mc.getTextureManager().bindTexture(buttonTexture);

			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.pushMatrix();

			GlStateManager.scale(1f / 12.8f, 1f / 12.8f, 1f);

			drawTexturedModalRect( (int) (this.x * 12.8), (int) (this.y * 12.8), 0, 0, 256, 256);
			GlStateManager.popMatrix();

			this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width
					&& mouseY < this.y + this.height;

		}
	}
}
