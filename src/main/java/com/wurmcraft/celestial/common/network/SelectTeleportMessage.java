package com.wurmcraft.celestial.common.network;

import com.wurmcraft.celestial.common.reference.NBT;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;

import java.io.IOException;

public class SelectTeleportMessage extends CustomMessage.CustomtServerMessage <SelectTeleportMessage> {

	private int index;

	public SelectTeleportMessage () {
	}

	public SelectTeleportMessage (int index) {
		this.index = index;
	}

	@Override
	protected void read (PacketBuffer buff) throws IOException {
		index = buff.readInt ();
	}

	@Override
	protected void write (PacketBuffer buff) throws IOException {
		buff.writeInt (index);
	}

	@Override
	public void process (EntityPlayer player,Side side) {
		ItemStack stack = player.getHeldItem (player.getActiveHand ());
		stack.getTagCompound ().setInteger (NBT.TELEPORT_LOCATION,index);
	}
}
