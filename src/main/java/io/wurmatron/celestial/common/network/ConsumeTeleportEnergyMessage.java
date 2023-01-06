package io.wurmatron.celestial.common.network;

import io.wurmatron.celestial.common.reference.NBT;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;

public class ConsumeTeleportEnergyMessage extends CustomMessage.CustomtServerMessage <ConsumeTeleportEnergyMessage> {

	public ConsumeTeleportEnergyMessage () {
	}

	@Override
	protected void read (PacketBuffer buff) {
	}

	@Override
	protected void write (PacketBuffer buff) {
	}

	@Override
	public void process (EntityPlayer player,Side side) {
		ItemStack stack = player.getHeldItem (player.getActiveHand ());
		if (player.inventory.hasItemStack (new ItemStack (Items.ENDER_PEARL)))
			for (ItemStack item : player.inventory.mainInventory)
				if (item.isItemEqual (new ItemStack (Items.ENDER_PEARL))) {
					player.inventory.deleteStack (item);
					stack.getTagCompound ().setInteger (
              NBT.ENERGY,stack.getTagCompound ().getInteger (NBT.ENERGY) + 1);
				}
	}
}
