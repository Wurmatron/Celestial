package com.wurmcraft.celestial;

import com.wurmcraft.celestial.common.Registry;
import com.wurmcraft.celestial.common.blocks.CelestialModBlocks;
import com.wurmcraft.celestial.common.entity.*;
import com.wurmcraft.celestial.common.items.CelestialModItems;
import com.wurmcraft.celestial.common.proxy.CommonProxy;
import com.wurmcraft.celestial.common.reference.Global;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

@Mod (modid = Global.MODID, name = Global.NAME, version = Global.VERSION, dependencies = Global.DEPEND)
public class Celestial {

	@Mod.Instance (Global.MODID)
	public static Celestial instance;

	@SidedProxy (serverSide = Global.PROXY_SERVER, clientSide = Global.PROXY_CLIENT)
	public static CommonProxy proxy;

	public static int ENTITY_ID = 0;

	@Mod.EventHandler
	public void preInit (FMLPreInitializationEvent e) {
		MinecraftForge.EVENT_BUS.register (new Registry ());
		CelestialModBlocks.register ();
		CelestialModItems.register ();
		EntityRegistry.registerModEntity (new ResourceLocation (Global.MODID,"explosiveArrow"),EntityExplosiveArrow.class,"explosiveArrow",ENTITY_ID,instance,64,5,true);
		EntityRegistry.registerModEntity (new ResourceLocation (Global.MODID,"lightningArrow"),EntityLightningArrow.class,"lightningArrow",ENTITY_ID++,instance,64,5,true);
		EntityRegistry.registerModEntity (new ResourceLocation (Global.MODID,"arcticArrow"),EntityArcticArrow.class,"arcticArrow",ENTITY_ID++,instance,64,5,true);
		EntityRegistry.registerModEntity (new ResourceLocation (Global.MODID,"regularArrow"),EntityRegularArrow.class,"regularArrow",ENTITY_ID++,instance,64,5,true);
		EntityRegistry.registerModEntity (new ResourceLocation (Global.MODID,"rainArrow"),EntityRainArrow.class,"rainArrow",ENTITY_ID++,instance,64,5,true);
		proxy.preInit (e);
	}

	@Mod.EventHandler
	public void init (FMLInitializationEvent e) {
		proxy.init (e);
	}

	@Mod.EventHandler
	public void postInit (FMLPostInitializationEvent e) {
		proxy.postInit (e);
	}
}
