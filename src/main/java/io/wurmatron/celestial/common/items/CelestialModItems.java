package io.wurmatron.celestial.common.items;

import io.wurmatron.celestial.api.items.CelestialItems;
import io.wurmatron.celestial.common.Registry;
import io.wurmatron.celestial.common.items.celestial.CelestialArmor;
import io.wurmatron.celestial.common.items.demi.DemiArmor;
import io.wurmatron.celestial.common.items.special.WorldGlobe;
import io.wurmatron.celestial.common.items.weapons.BowThanatos;
import io.wurmatron.celestial.common.reference.Global;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;


public class CelestialModItems {

	public static final ItemArmor.ArmorMaterial DEMI_MATERIAL = EnumHelper.addArmorMaterial ("demi",Global.MODID + ":demi",-1,new int[] {9001,9001,9001,9001},1000,null,9001);
	public static final ItemArmor.ArmorMaterial CELESTIAL_MATERIAL = EnumHelper.addArmorMaterial ("celestial",Global.MODID + ":celestial",-1,new int[] {9001,9001,9001,9001},1000,null,9001);

	public static void register () {
		register (CelestialItems.bowThanatos = new BowThanatos());
		register (CelestialItems.worldGlobe = new WorldGlobe());
		register (CelestialItems.helmDemi = new DemiArmor(DEMI_MATERIAL,0,EntityEquipmentSlot.HEAD));
		register (CelestialItems.chestDemi = new DemiArmor (DEMI_MATERIAL,0,EntityEquipmentSlot.CHEST));
		register (CelestialItems.legsDemi = new DemiArmor (DEMI_MATERIAL,1,EntityEquipmentSlot.LEGS));
		register (CelestialItems.bootsDemi = new DemiArmor (DEMI_MATERIAL,0,EntityEquipmentSlot.FEET));
		register (CelestialItems.helmCelestial = new CelestialArmor(CELESTIAL_MATERIAL,0,EntityEquipmentSlot.HEAD));
		register (CelestialItems.chestCelestial = new CelestialArmor (CELESTIAL_MATERIAL,0,EntityEquipmentSlot.CHEST));
		register (CelestialItems.legsCelestial = new CelestialArmor (CELESTIAL_MATERIAL,1,EntityEquipmentSlot.LEGS));
		register (CelestialItems.bootsCelestial = new CelestialArmor (CELESTIAL_MATERIAL,0,EntityEquipmentSlot.FEET));
	}

	public static void register (Item item) {
		Registry.registerItem (item,item.getUnlocalizedName ().substring (5));
	}
}
