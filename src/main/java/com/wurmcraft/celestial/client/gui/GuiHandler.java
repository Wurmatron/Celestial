package com.wurmcraft.celestial.client.gui;

import com.wurmcraft.celestial.client.gui.items.GuiWorldGlobe;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler {

	public static final int WORLDGLOBE_ID = 0;

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
			default:
				return null;
		}
	}
}
