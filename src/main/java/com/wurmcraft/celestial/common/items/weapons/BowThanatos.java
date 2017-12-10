package com.wurmcraft.celestial.common.items.weapons;

import com.wurmcraft.celestial.api.items.CelestialItems;
import com.wurmcraft.celestial.common.entity.*;
import com.wurmcraft.celestial.common.reference.Local;
import com.wurmcraft.celestial.common.reference.NBT;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;

public class BowThanatos extends ItemBow {

	private static final String DEFAULT_MODE = NBT.MODE_EXPLOSIVE;

	private static final String[] MODES = new String[] {NBT.MODE_EXPLOSIVE,NBT.MODE_LIGHTNING,NBT.MODE_ARCTIC,NBT.MODE_REGULAR,NBT.MODE_RAIN};
	private static final HashMap <String, Float> MODES_DEFAULT_DATA = new HashMap <> ();

	public BowThanatos () {
		setMaxStackSize (1);
		setMaxDamage (-1);
		setUnlocalizedName ("bowThanatos");
		setCreativeTab (CreativeTabs.COMBAT);
		addPropertyOverride (new ResourceLocation ("pull"),new IItemPropertyGetter () {
			@SideOnly (Side.CLIENT)
			public float apply (ItemStack stack,@Nullable World world,@Nullable EntityLivingBase entity) {
				if (entity == null)
					return 0.0F;
				else
					return entity.getActiveItemStack ().getItem () != CelestialItems.bowThanatos ? 0.0F : (float) (stack.getMaxItemUseDuration () - entity.getItemInUseCount ()) / 20.0F;
			}
		});
		addPropertyOverride (new ResourceLocation ("pulling"),new IItemPropertyGetter () {
			@SideOnly (Side.CLIENT)
			public float apply (ItemStack stack,@Nullable World world,@Nullable EntityLivingBase entity) {
				return entity != null && entity.isHandActive () && entity.getActiveItemStack () == stack ? 1.0F : 0.0F;
			}
		});
		MODES_DEFAULT_DATA.put (NBT.MODE_EXPLOSIVE,4.0f);
		MODES_DEFAULT_DATA.put (NBT.MODE_LIGHTNING,1f);
		MODES_DEFAULT_DATA.put (NBT.MODE_ARCTIC,100f);
		MODES_DEFAULT_DATA.put (NBT.MODE_REGULAR,15f);
		MODES_DEFAULT_DATA.put (NBT.MODE_RAIN,5f);
	}

	public static float getArrowVelocity (int charge) {
		float f = (float) charge / 20.0F;
		f = (f * f + f * 2.0F) / 3.0F;
		if (f > 1.0F)
			f = 1.0F;
		return f;
	}

	public static ItemStack create (String mode,float modeData) {
		ItemStack bow = new ItemStack (CelestialItems.bowThanatos,1,0);
		NBTTagCompound nbt = new NBTTagCompound ();
		nbt.setString (NBT.MODE,mode);
		nbt.setFloat (NBT.MODE_DATA,modeData);
		bow.setTagCompound (nbt);
		return bow;
	}

	@Override
	public void onCreated (ItemStack stack,World world,EntityPlayer player) {
		NBTTagCompound nbt = new NBTTagCompound ();
		nbt.setString (NBT.MODE,DEFAULT_MODE);
		nbt.setFloat (NBT.MODE_DATA,MODES_DEFAULT_DATA.get (DEFAULT_MODE));
		stack.setTagCompound (nbt);
	}

	@Override
	public void getSubItems (CreativeTabs tab,NonNullList <ItemStack> items) {
		if (tab == CreativeTabs.COMBAT) {
			items.add (create (DEFAULT_MODE,MODES_DEFAULT_DATA.get (DEFAULT_MODE)));
		}
	}

	@Override
	public ActionResult <ItemStack> onItemRightClick (World world,EntityPlayer player,EnumHand hand) {
		if (Keyboard.isKeyDown (Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown (Keyboard.KEY_RSHIFT)) {
			ItemStack bow = player.getHeldItem (hand);
			String newMode = getNextMode (bow);
			bow.getTagCompound ().setString (NBT.MODE,newMode);
			bow.getTagCompound ().setFloat (NBT.MODE_DATA,MODES_DEFAULT_DATA.get (newMode));
			if (world.isRemote)
				player.sendMessage (new TextComponentString (I18n.translateToLocal (Local.CHAT_MODE_CHANGED).replaceAll ("%MODE%",convertToLocal (newMode))));
			return new ActionResult <> (EnumActionResult.SUCCESS,bow);
		}
		return super.onItemRightClick (world,player,hand);
	}

	public void onPlayerStoppedUsing (ItemStack stack,World world,EntityLivingBase entity,int timeLeft) {
		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			int arrowDraw = ForgeEventFactory.onArrowLoose (stack,world,player,getMaxItemUseDuration (stack) - timeLeft,true);
			float speed = getArrowVelocity (arrowDraw);
			if (!world.isRemote) {
				EntityArrow arrow = getArrow (world,player,stack);
				arrow.shoot (player,player.rotationPitch,player.rotationYaw,0,speed * 5,0);
				world.spawnEntity (arrow);
				world.playSound (player,player.posX,player.posY,player.posZ,SoundEvents.ENTITY_ARROW_SHOOT,SoundCategory.PLAYERS,1.0F,1.0F / (itemRand.nextFloat () * 0.4F + 1.2F) + speed * 0.5F);
				player.addStat (StatList.getObjectUseStats (this));
			}
		}
	}

	private EntityArrow getArrow (World world,EntityPlayer player,ItemStack stack) {
		if (stack != null && stack.getTagCompound () != null && stack.getTagCompound ().hasKey (NBT.MODE)) {
			String mode = stack.getTagCompound ().getString (NBT.MODE);
			int power = EnchantmentHelper.getEnchantmentLevel (Enchantments.POWER,stack);
			switch (mode) {
				case (NBT.MODE_EXPLOSIVE):
					return new EntityExplosiveArrow (world,player,stack.getTagCompound () != null && stack.getTagCompound ().hasKey (NBT.MODE_DATA) && stack.getTagCompound ().getFloat (NBT.MODE_DATA) > 0 ? (stack.getTagCompound ().getFloat (NBT.MODE_DATA) + power) : 1);
				case (NBT.MODE_LIGHTNING):
					return new EntityLightningArrow (world,player).setAmount (power);
				case (NBT.MODE_ARCTIC):
					return new EntityArcticArrow (world,player,stack.getTagCompound () != null && stack.getTagCompound ().hasKey (NBT.MODE_DATA) && stack.getTagCompound ().getFloat (NBT.MODE_DATA) > 0 ? (int) (stack.getTagCompound ().getFloat (NBT.MODE_DATA) * power) : 1);
				case (NBT.MODE_REGULAR):
					return new EntityRegularArrow (world,player,stack.getTagCompound () != null && stack.getTagCompound ().hasKey (NBT.MODE_DATA) && stack.getTagCompound ().getFloat (NBT.MODE_DATA) > 0 ? (int) (stack.getTagCompound ().getFloat (NBT.MODE_DATA) * power) : 1);
				case (NBT.MODE_RAIN):
					return new EntityRainArrow (world,player,stack.getTagCompound () != null && stack.getTagCompound ().hasKey (NBT.MODE_DATA) && stack.getTagCompound ().getFloat (NBT.MODE_DATA) > 0 ? (int) (stack.getTagCompound ().getFloat (NBT.MODE_DATA) + power) : 1);
			}
		}
		return null;
	}

	@Override
	public void addInformation (ItemStack stack,@Nullable World world,List <String> tip,ITooltipFlag flag) {
		if (stack.getTagCompound () != null) {
			String mode = stack.getTagCompound ().getString (NBT.MODE);
			int power = EnchantmentHelper.getEnchantmentLevel (Enchantments.POWER,stack);
			tip.add (I18n.translateToLocal (Local.TOOLTIP_BOW_MODE).replaceAll ("%MODE%",convertToLocal (mode)));
			if (mode.equalsIgnoreCase (NBT.MODE_EXPLOSIVE))
				tip.add (I18n.translateToLocal (Local.TOOLTIP_BOW_EXPLOSIVE_SIZE).replaceAll ("%SIZE%","" + (stack.getTagCompound ().getFloat (NBT.MODE_DATA) + power)));
			else if (mode.equalsIgnoreCase (NBT.MODE_LIGHTNING))
				tip.add (I18n.translateToLocal (Local.TOOLTIP_BOW_LIGHTING_AMOUNT).replaceAll ("%AMOUNT%","" + ((int) stack.getTagCompound ().getFloat (NBT.MODE_DATA) + power)));
			else if (mode.equalsIgnoreCase (NBT.MODE_ARCTIC))
				tip.add (I18n.translateToLocal (Local.TOOLTIP_BOW_ARCTIC_AMOUNT).replaceAll ("%TIME%","" + ((int) stack.getTagCompound ().getFloat (NBT.MODE_DATA) * power)));
			else if (mode.equalsIgnoreCase (NBT.MODE_REGULAR))
				tip.add (I18n.translateToLocal (Local.TOOLTIP_BOW_REGULAR_DAMAGE).replaceAll ("%DAMAGE%","" + ((int) stack.getTagCompound ().getFloat (NBT.MODE_DATA) * power)));
			else if (mode.equalsIgnoreCase (NBT.MODE_RAIN))
				tip.add (I18n.translateToLocal (Local.TOOLTIP_BOW_RAIN_DAMAGE).replaceAll ("%RADIUS%","" + ((int) stack.getTagCompound ().getFloat (NBT.MODE_DATA) + power)));
		}
	}

	@SideOnly (Side.CLIENT)
	private String convertToLocal (String mode) {
		switch (mode.toLowerCase ()) {
			case (NBT.MODE_EXPLOSIVE):
				return I18n.translateToLocal (Local.TOOLTIP_MODE_EXPLOSIVE);
			case (NBT.MODE_LIGHTNING):
				return I18n.translateToLocal (Local.TOOLTIP_MODE_LIGHTNING);
			case (NBT.MODE_ARCTIC):
				return I18n.translateToLocal (Local.TOOLTIP_MODE_ARCTIC);
			case (NBT.MODE_REGULAR):
				return I18n.translateToLocal (Local.TOOLTIP_MODE_REGULAR);
			case (NBT.MODE_RAIN):
				return I18n.translateToLocal (Local.TOOLTIP_MODE_RAIN);
			default:
				return "";
		}
	}

	private String getNextMode (ItemStack stack) {
		String currentMode = stack.getTagCompound ().getString (NBT.MODE);
		for (int id = 0; id < MODES.length; id++)
			if (MODES[id].equalsIgnoreCase (currentMode)) {
				if ((id + 1) < MODES.length)
					return MODES[id + 1];
				else
					return MODES[0];
			}
		return currentMode;
	}
}
