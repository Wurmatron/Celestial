package com.wurmcraft.celestial.client.gui.helper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class GuiTexturedButton extends GuiButton {

	private final ResourceLocation TEXTURE;
	private final int U;
	private final int V;
	private final boolean val;
	private final boolean size;
	private boolean d;

	public GuiTexturedButton (int id,int x,int y,int width,int height,ResourceLocation texture,int u,int v,boolean val,boolean size) {
		super (id,x,y,width,height,"");
		this.TEXTURE = texture;
		this.U = u;
		this.V = v;
		this.val = val;
		this.size = size;
	}

	public GuiTexturedButton (int id,int x,int y,int width,int height,ResourceLocation texture,int u,int v,boolean val,boolean size, boolean d) {
		super (id,x,y,width,height,"");
		this.TEXTURE = texture;
		this.U = u;
		this.V = v;
		this.val = val;
		this.size = size;
		this.d = d;
	}

	public GuiTexturedButton (int id,int x,int y,int width,int height,ResourceLocation texture,int u,int v,boolean val,boolean size,String text) {
		super (id,x,y,width,height,text);
		this.TEXTURE = texture;
		this.U = u;
		this.V = v;
		this.val = val;
		this.size = size;
	}

	@Override
	public void drawButton (Minecraft mc,int mouseX,int mouseY,float partialTicks) {
		if (visible) {
			mc.getTextureManager ().bindTexture (TEXTURE);
			GlStateManager.color (1.0F,1.0F,1.0F,1.0F);
			hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
			int i = (getHoverState (hovered) - (displayString.length () > 0 ? 0 : -1)) * (size ? height : width);
			GlStateManager.enableBlend ();
			GlStateManager.tryBlendFuncSeparate (GlStateManager.SourceFactor.SRC_ALPHA,GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,GlStateManager.SourceFactor.ONE,GlStateManager.DestFactor.ZERO);
			GlStateManager.blendFunc (GlStateManager.SourceFactor.SRC_ALPHA,GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
			if (val)
				drawTexturedModalRect (x,y,U + i,V,width,height);
			else
				drawTexturedModalRect (x,y,U,V + i,width,height);
			drawString (mc.fontRenderer,displayString,x + 2,y + 3,Color.WHITE.getRGB ());
			mouseDragged (mc,mouseX,mouseY);
		}
	}
}
