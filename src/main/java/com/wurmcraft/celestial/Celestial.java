package com.wurmcraft.celestial;

import com.wurmcraft.celestial.common.proxy.Common_Proxy;
import com.wurmcraft.celestial.common.reference.Global;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod (modid = Global.MODID, name = Global.NAME, version = Global.VERSION, dependencies = Global.DEPEND)
public class Celestial {

	@Mod.Instance (Global.MODID)
	public static Celestial instance;

	@SidedProxy (serverSide = Global.PROXY_SERVER, clientSide = Global.PROXY_CLIENT)
	public static Common_Proxy proxy;

	@Mod.EventHandler
	public void preInit (FMLPreInitializationEvent e) {
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
