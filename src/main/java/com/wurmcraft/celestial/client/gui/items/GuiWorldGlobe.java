package com.wurmcraft.celestial.client.gui.items;

import com.wurmcraft.celestial.common.reference.Global;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiWorldGlobe extends GuiScreen {

	private static final ResourceLocation background = new ResourceLocation (Global.MODID,"textures/gui/worldglobe.png");
	protected int startWidth;
	protected int startHeight;
	private EntityPlayer player;
	private ItemStack stack;

	public GuiWorldGlobe (EntityPlayer player,ItemStack stack) {
		this.player = player;
		this.stack = stack;
	}

	@Override
	public void drawScreen (int mouseX,int mouseY,float partialTicks) {
		startWidth = (width - 186) / 2;
		startHeight = (height - 162) / 2;
		mc.renderEngine.bindTexture (background);
		drawTexturedModalRect (startWidth,startHeight,0,0,186,162);
		super.drawScreen (mouseX,mouseY,partialTicks);
	}

	@Override
	public void initGui () {
		startWidth = (width - 186) / 2;
		startHeight = (height - 162) / 2;
	}

	@Override
	public boolean doesGuiPauseGame () {
		return false;
	}
}
