package com.wurmcraft.celestial.common.items;

import com.wurmcraft.celestial.api.items.CelestialItems;
import com.wurmcraft.celestial.common.Registry;
import com.wurmcraft.celestial.common.items.special.WorldGlobe;
import com.wurmcraft.celestial.common.items.weapons.BowThanatos;
import net.minecraft.item.Item;


public class CelestialModItems {

	public static void register () {
		register (CelestialItems.bowThanatos = new BowThanatos ());
		register (CelestialItems.worldGlobe = new WorldGlobe ());
	}

	public static void register (Item item) {
		Registry.registerItem (item,item.getUnlocalizedName ().substring (5));
	}
}
