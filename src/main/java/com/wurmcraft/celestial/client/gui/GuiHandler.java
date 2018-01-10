package com.wurmcraft.celestial.client.gui;

import com.wurmcraft.celestial.client.gui.items.GuiWorldGlobe;
import com.wurmcraft.celestial.client.gui.items.GuiWorldGlobeCreation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler {

	public static final int WORLDGLOBE_ID = 0;
	public static final int WORLDGLOBE_CREATION_ID = 1;

	@Nullable
	@Override
	public Object getServerGuiElement (int ID,EntityPlayer player,World world,int x,int y,int z) {
		return new ContainerPlayer (player.inventory,false,player);
	}

	@Nullable
	@Override
	public Object getClientGuiElement (int ID,EntityPlayer player,World world,int x,int y,int z) {
		switch (ID) {
			case (WORLDGLOBE_ID):
				return new GuiWorldGlobe (player,player.getHeldItemMainhand ());
			case (WORLDGLOBE_CREATION_ID):
				return new GuiWorldGlobeCreation (player,player.getHeldItemMainhand ());
			default:
				return null;
		}
	}
}
