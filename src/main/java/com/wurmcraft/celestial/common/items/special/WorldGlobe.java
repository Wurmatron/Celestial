package com.wurmcraft.celestial.common.items.special;

import com.wurmcraft.celestial.Celestial;
import com.wurmcraft.celestial.client.gui.GuiHandler;
import com.wurmcraft.celestial.common.network.NetworkHandler;
import com.wurmcraft.celestial.common.network.OpenGuiMessage;
import com.wurmcraft.celestial.common.reference.Local;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nullable;
import java.util.List;

public class WorldGlobe extends Item {

	public WorldGlobe () {
		setMaxStackSize (1);
		setMaxDamage (1);
		setUnlocalizedName ("worldGlobe");
		setCreativeTab (Celestial.tabCelestial);
	}

	@Override
	public ActionResult <ItemStack> onItemRightClick (World world,EntityPlayer player,EnumHand hand) {
		if (Keyboard.isKeyDown (Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown (Keyboard.KEY_RSHIFT)) {
			NetworkHandler.sendToServer (new OpenGuiMessage (GuiHandler.WORLDGLOBE_ID,player.getPosition ()));
		} else {
			// Teleport to Selected
		}
		return super.onItemRightClick (world,player,hand);
	}

	@Override
	public void addInformation (ItemStack stack,@Nullable World world,List <String> tip,ITooltipFlag flag) {
		tip.add (I18n.translateToLocal (Local.TOOLTIP_WORLDGLOBE_DESCRIPTION));
	}
}
