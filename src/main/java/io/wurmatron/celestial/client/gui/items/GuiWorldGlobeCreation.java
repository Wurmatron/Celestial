package io.wurmatron.celestial.client.gui.items;

import io.wurmatron.celestial.api.items.WorldData;
import io.wurmatron.celestial.common.items.special.WorldGlobe;
import io.wurmatron.celestial.common.reference.Global;
import io.wurmatron.celestial.common.reference.NBT;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiWorldGlobeCreation extends GuiScreen {

	private static final ResourceLocation BACKGROUND = new ResourceLocation (Global.MODID,"textures/gui/worldglobe_creation.png");
	protected int startWidth;
	protected int startHeight;
	private EntityPlayer player;
	private ItemStack stack;
	private WorldData data;

	public GuiWorldGlobeCreation (EntityPlayer player,ItemStack stack) {
		this.player = player;
		this.stack = stack;
		if (stack.getTagCompound () != null && stack.getTagCompound ().hasKey (NBT.TELEPORT_LOCATION))
			this.data = WorldGlobe.getWorldDataFromIndex (stack,stack.getTagCompound ().getInteger (NBT.TELEPORT_LOCATION));
		else
			this.data = new WorldData ("",(int) player.posX,(int) player.posY,(int) player.posZ,player.dimension);
	}

	@Override
	public void drawScreen (int mouseX,int mouseY,float partialTicks) {
		super.drawScreen (mouseX,mouseY,partialTicks);
		startWidth = (width - 171) / 2;
		startHeight = (height - 88) / 2;
		mc.renderEngine.bindTexture (BACKGROUND);
		drawTexturedModalRect (startWidth,startHeight,0,0,171,88);
	}

	@Override
	public boolean doesGuiPauseGame () {
		return false;
	}

}
