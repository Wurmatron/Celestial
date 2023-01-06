package io.wurmatron.celestial.common.items.special;

import io.wurmatron.celestial.common.reference.Local;
import io.wurmatron.celestial.common.reference.NBT;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.common.UsernameCache;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class ItemBound extends Item {

	private static final float DAMAGE_TICK = 1f;

	@Override
	public void onCreated (ItemStack stack,World world,EntityPlayer player) {
		NBTTagCompound nbt = new NBTTagCompound ();
		nbt.setString (NBT.OWNER,player.getGameProfile ().getId ().toString ());
		stack.setTagCompound (nbt);
	}

	@Override
	public void onUpdate (ItemStack stack,World world,Entity entity,int slot,boolean isSelected) {
		super.onUpdate (stack,world,entity,slot,isSelected);
		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			if (stack.getTagCompound () != null && !stack.getTagCompound ().hasKey (NBT.OWNER))
				stack.getTagCompound ().setString (NBT.OWNER,player.getGameProfile ().getId ().toString ());
			if (stack.getTagCompound () != null && stack.getTagCompound ().hasKey (NBT.OWNER) && !stack.getTagCompound ().getString (NBT.OWNER).equals (player.getGameProfile ().getId ()))
				player.attackEntityFrom (DamageSource.MAGIC,DAMAGE_TICK);
		}
	}

	@Override
	public void addInformation (ItemStack stack,@Nullable World world,List <String> tip,ITooltipFlag flag) {
		if (stack.getTagCompound () != null && stack.getTagCompound ().hasKey (NBT.OWNER))
			tip.add (I18n.translateToLocal (Local.TOOLTIP_OWNER).replaceAll ("%OWNER%",UsernameCache.getLastKnownUsername (UUID.fromString (stack.getTagCompound ().getString (NBT.OWNER)))));
	}
}
