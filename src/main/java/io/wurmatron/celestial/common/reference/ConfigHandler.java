package io.wurmatron.celestial.common.reference;

import io.wurmatron.celestial.common.utils.LogHandler;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber (modid = Global.MODID)
@Config (modid = Global.MODID)
public class ConfigHandler {

	@Config.Comment ("Enable / Disable Debug Mode")
	@Config.LangKey (Local.CONFIG_DEBUG)
	public static boolean debug = false;

	@SubscribeEvent
	public static void onConfigChanged (ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID ().equals (Global.MODID)) {
			ConfigManager.load (Global.MODID,Config.Type.INSTANCE);
			LogHandler.info ("Config Saved!");
		}
	}
}
