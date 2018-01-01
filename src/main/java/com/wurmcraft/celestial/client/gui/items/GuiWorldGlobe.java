package com.wurmcraft.celestial.client.gui.items;

import com.wurmcraft.celestial.api.items.WorldData;
import com.wurmcraft.celestial.client.gui.helper.GuiTexturedButton;
import com.wurmcraft.celestial.common.items.special.WorldGlobe;
import com.wurmcraft.celestial.common.network.NetworkHandler;
import com.wurmcraft.celestial.common.network.SelectTeleportMessage;
import com.wurmcraft.celestial.common.reference.Global;
import com.wurmcraft.celestial.common.reference.NBT;
import com.wurmcraft.celestial.common.utils.LogHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.util.List;

public class GuiWorldGlobe extends GuiScreen {

	private static final ResourceLocation BACKGROUND = new ResourceLocation (Global.MODID,"textures/gui/worldglobe.png");
	protected int startWidth;
	protected int startHeight;
	private EntityPlayer player;
	private ItemStack stack;
	private int selectedSlot;
	private int slide;

	public GuiWorldGlobe (EntityPlayer player,ItemStack stack) {
		this.player = player;
		this.stack = stack;
		selectedSlot = stack.getTagCompound ().getInteger (NBT.TELEPORT_LOCATION);
		slide = 0;
	}

	@Override
	public void drawScreen (int mouseX,int mouseY,float partialTicks) {
		startWidth = (width - 186) / 2;
		startHeight = (height - 162) / 2;
		mc.renderEngine.bindTexture (BACKGROUND);
		drawTexturedModalRect (startWidth,startHeight,0,0,186,162);
		drawTexturedModalRect (startWidth + 123,startHeight + 6,0,206,58,50);
		super.drawScreen (mouseX,mouseY,partialTicks);
	}

	@Override
	public void initGui () {
		startWidth = (width - 186) / 2;
		startHeight = (height - 162) / 2;
		super.initGui ();
		GuiTexturedButton down = new GuiTexturedButton (0,startWidth + 107,startHeight + 145,13,13,BACKGROUND,0,154,false,true,true);
		buttonList.add (down);
		GuiTexturedButton up = new GuiTexturedButton (1,startWidth + 107,startHeight + 5,13,13,BACKGROUND,13,154,false,true,true);
		buttonList.add (up);
		buttonList.add (new GuiTexturedButton (2,startWidth + 107,startHeight + 20,13,123,BACKGROUND,164,0,true,false));
		int worldDataID = 9;
		List <WorldData> worldData = WorldGlobe.getGlobeData (stack);
		if (worldData.size () > 0)
			for (int index = 0; index < worldData.size (); index++)
				if (index < 10) {
					GuiTexturedButton button = new GuiTexturedButton (worldDataID++,startWidth + 7,startHeight + 7 + (index * 15),96,14,BACKGROUND,58,164,false,true,worldData.get (index).getName ());
					if ((worldDataID - 10) == selectedSlot)
						button.enabled = false;
					buttonList.add (button);
				}
		if (slide <= 0)
			up.enabled = false;
		if (worldData.size () <= 10)
			down.enabled = false;
	}

	@Override
	public boolean doesGuiPauseGame () {
		return false;
	}

	@Override
	protected void actionPerformed (GuiButton button) throws IOException {
		super.actionPerformed (button);
		if (button.id >= 9) {
			NetworkHandler.sendToServer (new SelectTeleportMessage (button.id - 9));
			buttonList.clear ();
			selectedSlot = button.id - 9;
			redrawButtons ();
		}
		if (button.id == 0) {
			// UP
		} else if (button.id == 1) {
			slide++;
			buttonList.clear ();
			redrawButtons ();
		}
	}

	private void redrawButtons () {
		initGui ();
	}
}
