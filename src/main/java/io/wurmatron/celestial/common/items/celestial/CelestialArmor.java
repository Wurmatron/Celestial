package io.wurmatron.celestial.common.items.celestial;

import io.wurmatron.celestial.Celestial;
import io.wurmatron.celestial.client.render.ModelDemi;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class CelestialArmor extends ItemArmor {

	public CelestialArmor (ArmorMaterial material,int renderIndex,EntityEquipmentSlot slot) {
		super (material,renderIndex,slot);
		setCreativeTab (Celestial.tabCelestial);
		setUnlocalizedName ("celestial" + slot.name ().substring (0,1) + slot.name ().substring (1,slot.name ().length ()).toLowerCase ());
	}

	@Nullable
	@Override
	public ModelBiped getArmorModel (EntityLivingBase entityLiving,ItemStack itemStack,EntityEquipmentSlot armorSlot,ModelBiped _default) {
		return new ModelDemi (0.006f,0,64,32);
	}

	@Override
	public void addInformation (ItemStack stack,@Nullable World world,List <String> tip,ITooltipFlag adv) {
		super.addInformation (stack,world,tip,adv);
	}

	@Override
	public String getItemStackDisplayName (ItemStack stack) {
		return "\u00a7d" + super.getItemStackDisplayName (stack);
	}

	@Override
	public void onArmorTick (World world,EntityPlayer player,ItemStack itemStack) {
		super.onArmorTick (world,player,itemStack);
		player.noClip = true;
		player.capabilities.allowFlying = true;
		player.capabilities.allowEdit = true;
		player.capabilities.disableDamage = true;
		player.capabilities.isFlying = true;
	}
}
