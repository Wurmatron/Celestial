package com.wurmcraft.celestial.common.blocks;

import com.wurmcraft.celestial.common.Registry;
import net.minecraft.block.Block;

public class CelestialModBlocks {

	public static void register () {

	}

	private static void register (Block block) {
		Registry.registerBlock (block,block.getUnlocalizedName ().substring (5));
	}
}
