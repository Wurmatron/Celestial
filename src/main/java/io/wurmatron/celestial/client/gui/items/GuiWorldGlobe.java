package io.wurmatron.celestial.client.gui.items;

import io.wurmatron.celestial.api.items.WorldData;
import io.wurmatron.celestial.client.gui.GuiHandler;
import io.wurmatron.celestial.client.gui.helper.GuiTexturedButton;
import io.wurmatron.celestial.common.items.special.WorldGlobe;
import io.wurmatron.celestial.common.network.NetworkHandler;
import io.wurmatron.celestial.common.network.OpenGuiMessage;
import io.wurmatron.celestial.common.network.SelectTeleportMessage;
import io.wurmatron.celestial.common.reference.Global;
import io.wurmatron.celestial.common.reference.NBT;
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
			if (slide <= 0)
				up.enabled = false;
		for (int index = slide; index < worldData.size (); index++)
			if ((index - slide) < 10) {
				GuiTexturedButton button = new GuiTexturedButton (worldDataID++ + slide,startWidth + 7,startHeight + 7 + ((index - slide) * 15),96,14,BACKGROUND,58,164,false,true,worldData.get (index).getName ());
				if (selectedSlot == (worldDataID - 10) && slide == 0 || selectedSlot - slide == (worldDataID - 10) + 1 && slide > 0)
					button.enabled = false;
				buttonList.add (button);
			}
		if (worldData.size () - slide <= 10)
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
			int index = WorldGlobe.getIndexFromName (stack,button.displayString);
			NetworkHandler.sendToServer (new SelectTeleportMessage (index));
			if (!isShiftKeyDown ())
				mc.currentScreen = null;
			else
				NetworkHandler.sendToServer (new OpenGuiMessage (GuiHandler.WORLDGLOBE_CREATION_ID,player.getPosition ()));
		}
		if (button.id == 1 && button.enabled) {
			if (slide > 0)
				slide--;
		} else if (button.id == 0 && button.enabled)
			slide++;
		redrawButtons ();
	}

	private void redrawButtons () {
		buttonList.clear ();
		initGui ();
	}
}
