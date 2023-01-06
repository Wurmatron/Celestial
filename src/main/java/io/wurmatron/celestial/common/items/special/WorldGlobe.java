package io.wurmatron.celestial.common.items.special;

import io.wurmatron.celestial.Celestial;
import io.wurmatron.celestial.api.items.WorldData;
import io.wurmatron.celestial.client.gui.GuiHandler;
import io.wurmatron.celestial.common.network.ConsumeTeleportEnergyMessage;
import io.wurmatron.celestial.common.network.NetworkHandler;
import io.wurmatron.celestial.common.network.OpenGuiMessage;
import io.wurmatron.celestial.common.reference.Local;
import io.wurmatron.celestial.common.reference.NBT;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class WorldGlobe extends ItemBound {

	public WorldGlobe () {
		setMaxStackSize (1);
		setMaxDamage (1);
		setUnlocalizedName ("worldGlobe");
		setCreativeTab (Celestial.tabCelestial);
		setHasSubtypes (true);
	}

	public static ItemStack setWorldData (ItemStack stack,List <WorldData> worldData) {
		NBTTagCompound data = new NBTTagCompound ();
		for (int index = 0; index < worldData.size (); index++) {
			NBTTagCompound temp = new NBTTagCompound ();
			WorldData tempWorldData = worldData.get (index);
			temp.setString (NBT.NAME,tempWorldData.getName ());
			temp.setInteger (NBT.X,tempWorldData.getX ());
			temp.setInteger (NBT.Y,tempWorldData.getY ());
			temp.setInteger (NBT.Z,tempWorldData.getZ ());
			temp.setInteger (NBT.DIMENSION,tempWorldData.getDim ());
			data.setTag (Integer.toString (index),temp);
		}
		if (stack.getTagCompound () != null)
			stack.getTagCompound ().setTag (NBT.DATA,data);
		else {
			NBTTagCompound nbt = new NBTTagCompound ();
			nbt.setInteger (NBT.TELEPORT_LOCATION,0);
			nbt.setInteger (NBT.ENERGY,0);
			nbt.setTag (NBT.DATA,data);
			stack.setTagCompound (nbt);
		}
		return stack;
	}

	@Override
	public ActionResult <ItemStack> onItemRightClick (World world,EntityPlayer player,EnumHand hand) {
		if (Keyboard.isKeyDown (Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown (Keyboard.KEY_RSHIFT)) {
			if (Keyboard.isKeyDown (Keyboard.KEY_RCONTROL) || Keyboard.isKeyDown (Keyboard.KEY_LCONTROL))
				NetworkHandler.sendToServer (new OpenGuiMessage (GuiHandler.WORLDGLOBE_CREATION_ID,player.getPosition ()));
			else if (Keyboard.isKeyDown (Keyboard.KEY_TAB))
				NetworkHandler.sendToServer (new ConsumeTeleportEnergyMessage ());
			else
				NetworkHandler.sendToServer (new OpenGuiMessage (GuiHandler.WORLDGLOBE_ID,player.getPosition ()));
		} else {
			ItemStack stack = player.getHeldItem (hand);
			if (stack.getTagCompound () != null && stack.getTagCompound ().hasKey (NBT.TELEPORT_LOCATION)) {
				WorldData data = getWorldDataFromIndex (stack,stack.getTagCompound ().getInteger (NBT.TELEPORT_LOCATION));
				if (data != null) {
					if (stack.getTagCompound ().hasKey (NBT.ENERGY) && stack.getTagCompound ().getInteger (NBT.ENERGY) > 0) {
						player.setPositionAndUpdate (data.getX (),data.getY (),data.getZ ());
						stack.getTagCompound ().setInteger (NBT.ENERGY,stack.getTagCompound ().getInteger (NBT.ENERGY) - 1);
					} else if (world.isRemote)
						player.sendMessage (new TextComponentString (I18n.translateToLocal (Local.CHAT_NO_TELEPORT_ENERGY)));
				} else if (world.isRemote)
					player.sendMessage (new TextComponentString (I18n.translateToLocal (Local.CHAT_NO_SELECTED_TELEPORT)));
			}
		}
		return super.onItemRightClick (world,player,hand);
	}

	public static List <WorldData> getGlobeData (ItemStack stack) {
		NBTTagCompound nbt = stack.getTagCompound ();
		if (nbt != null && nbt.hasKey (NBT.DATA)) {
			NBTTagCompound data = nbt.getCompoundTag (NBT.DATA);
			if (data.getSize () > 0) {
				List <WorldData> worldData = new ArrayList <> ();
				for (int index = 0; index < data.getSize (); index++) {
					NBTTagCompound temp = data.getCompoundTag (Integer.toString (index));
					worldData.add (new WorldData (temp.getString (NBT.NAME),temp.getInteger (NBT.X),temp.getInteger (NBT.Y),temp.getInteger (NBT.Z),temp.getInteger (NBT.DIMENSION)));
				}
				return worldData;
			}
		}
		return new ArrayList <> ();
	}

	@Override
	public void addInformation (ItemStack stack,@Nullable World world,List <String> tip,ITooltipFlag flag) {
		tip.add (I18n.translateToLocal (Local.TOOLTIP_WORLDGLOBE_DESCRIPTION));
		if (stack.getTagCompound () != null && stack.getTagCompound ().hasKey (NBT.TELEPORT_LOCATION))
			tip.add (I18n.translateToLocal (Local.TOOLTIP_SELECTED_TELEPORT).replaceAll ("%LOCATION%",getNameFromIndex (stack,stack.getTagCompound ().getInteger (NBT.TELEPORT_LOCATION))));
		if (stack.getTagCompound () != null && stack.getTagCompound ().hasKey (NBT.ENERGY))
			tip.add (I18n.translateToLocal (Local.TOOLTIP_ENERGY_TELEPORT).replaceAll ("%ENERGY%","" + stack.getTagCompound ().getInteger (NBT.ENERGY)));
		super.addInformation (stack,world,tip,flag);
	}

	public static ItemStack addWorldData (ItemStack stack,WorldData worldData) {
		List <WorldData> currentWorldData = getGlobeData (stack);
		currentWorldData.add (worldData);
		return setWorldData (stack,currentWorldData);
	}

	@Override
	public void onCreated (ItemStack stack,World worldIn,EntityPlayer player) {
		super.onCreated (stack,worldIn,player);
		List <WorldData> worldData = new ArrayList <> ();
		worldData.add (new WorldData ("Zero-Zero",0,80,0,0));
		worldData.add (new WorldData ("Zero-One",1,80,0,0));
		worldData.add (new WorldData ("Zero-Two",2,80,0,0));
		worldData.add (new WorldData ("Three-Three",3,80,0,0));
		worldData.add (new WorldData ("Four-Three",4,80,0,0));
		worldData.add (new WorldData ("Five-Three",5,80,0,0));
		worldData.add (new WorldData ("Seven-Three",6,80,0,0));
		worldData.add (new WorldData ("Seven-Three",7,80,0,0));
		worldData.add (new WorldData ("Eight-Three",8,80,0,0));
		worldData.add (new WorldData ("Nine-Three",9,80,0,0));
		worldData.add (new WorldData ("Ten-Three",10,80,0,0));
		setWorldData (stack,worldData);
	}

	@Override
	public void getSubItems (CreativeTabs tab,NonNullList <ItemStack> items) {
		if (tab == Celestial.tabCelestial) {
			List <WorldData> worldData = new ArrayList <> ();
			worldData.add (new WorldData ("Zero-Zero",0,80,0,0));
			worldData.add (new WorldData ("Zero-One",1,80,0,0));
			worldData.add (new WorldData ("Zero-Two",2,80,0,0));
			worldData.add (new WorldData ("Three-Three",3,80,0,0));
			worldData.add (new WorldData ("Four-Three",4,80,0,0));
			worldData.add (new WorldData ("Five-Three",5,80,0,0));
			worldData.add (new WorldData ("Seven-Three",6,80,0,0));
			worldData.add (new WorldData ("Seven-Three",7,80,0,0));
			worldData.add (new WorldData ("Eight-Three",8,80,0,0));
			worldData.add (new WorldData ("Nine-Three",9,80,0,0));
			worldData.add (new WorldData ("Ten-Three",10,80,0,0));
			for (int index = 0; index < 20; index++)
				worldData.add (new WorldData ("Auto " + index,index * 10,80,index * 10,0));
			ItemStack stack = setWorldData (new ItemStack (this,1),worldData);
			stack.getTagCompound ().setInteger (NBT.ENERGY,100);
			items.add (stack);
		}
	}

	public static WorldData getWorldDataFromIndex (ItemStack stack,int index) {
		if (index <= getGlobeData (stack).size ())
			return getGlobeData (stack).get (index);
		return null;
	}

	public static String getNameFromIndex (ItemStack stack,int index) {
		List <WorldData> data = getGlobeData (stack);
		return index < data.size () && data.get (index) != null ? data.get (index).getName () : "None";
	}

	public static int getIndexFromName (ItemStack stack,String name) {
		List <WorldData> data = getGlobeData (stack);
		for (int index = 0; index < data.size (); index++)
			if (data.get (index).getName ().equals (name))
				return index;
		return -1;
	}
}
