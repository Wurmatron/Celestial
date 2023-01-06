package io.wurmatron.celestial;

import io.wurmatron.celestial.api.items.CelestialItems;
import io.wurmatron.celestial.client.gui.GuiHandler;
import io.wurmatron.celestial.common.Registry;
import io.wurmatron.celestial.common.blocks.CelestialModBlocks;
import io.wurmatron.celestial.common.entity.*;
import io.wurmatron.celestial.common.entity.EntityArcticArrow;
import io.wurmatron.celestial.common.entity.EntityExplosiveArrow;
import io.wurmatron.celestial.common.entity.EntityHomingArrow;
import io.wurmatron.celestial.common.entity.EntityLightningArrow;
import io.wurmatron.celestial.common.entity.EntityRainArrow;
import io.wurmatron.celestial.common.entity.EntityRegularArrow;
import io.wurmatron.celestial.common.items.CelestialModItems;
import io.wurmatron.celestial.common.network.NetworkHandler;
import io.wurmatron.celestial.common.proxy.CommonProxy;
import io.wurmatron.celestial.common.reference.Global;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;

@Mod (modid = Global.MODID, name = Global.NAME, version = Global.VERSION, dependencies = Global.DEPEND)
public class Celestial {

	@Mod.Instance (Global.MODID)
	public static Celestial instance;

	@SidedProxy (serverSide = Global.PROXY_SERVER, clientSide = Global.PROXY_CLIENT)
	public static CommonProxy proxy;

	public static int ENTITY_ID = 0;

	public static CreativeTabs tabCelestial = new CreativeTabs (CreativeTabs.getNextID (),"celestial") {
		@Override
		public ItemStack getTabIconItem () {
			return new ItemStack (CelestialItems.bowThanatos);
		}
	};

	@Mod.EventHandler
	public void preInit (FMLPreInitializationEvent e) {
		MinecraftForge.EVENT_BUS.register (new Registry());
		CelestialModBlocks.register ();
		CelestialModItems.register ();
		EntityRegistry.registerModEntity (new ResourceLocation (Global.MODID,"explosiveArrow"),
        EntityExplosiveArrow.class,"explosiveArrow",ENTITY_ID,instance,64,5,true);
		EntityRegistry.registerModEntity (new ResourceLocation (Global.MODID,"lightningArrow"),
        EntityLightningArrow.class,"lightningArrow",ENTITY_ID++,instance,64,5,true);
		EntityRegistry.registerModEntity (new ResourceLocation (Global.MODID,"arcticArrow"),
        EntityArcticArrow.class,"arcticArrow",ENTITY_ID++,instance,64,5,true);
		EntityRegistry.registerModEntity (new ResourceLocation (Global.MODID,"regularArrow"),
        EntityRegularArrow.class,"regularArrow",ENTITY_ID++,instance,64,5,true);
		EntityRegistry.registerModEntity (new ResourceLocation (Global.MODID,"rainArrow"),
        EntityRainArrow.class,"rainArrow",ENTITY_ID++,instance,64,5,true);
		EntityRegistry.registerModEntity (new ResourceLocation (Global.MODID,"homingArrow"),
        EntityHomingArrow.class,"homingArrow",ENTITY_ID++,instance,64,5,true);
		proxy.preInit (e);
	}

	@Mod.EventHandler
	public void init (FMLInitializationEvent e) {
		NetworkHandler.registerPackets ();
		NetworkRegistry.INSTANCE.registerGuiHandler (instance,new GuiHandler());
		proxy.init (e);
	}

	@Mod.EventHandler
	public void postInit (FMLPostInitializationEvent e) {
		proxy.postInit (e);
	}
}
