package com.wurmcraft.celestial.client.proxy;

import com.wurmcraft.celestial.client.render.*;
import com.wurmcraft.celestial.common.entity.*;
import com.wurmcraft.celestial.common.proxy.CommonProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit (FMLPreInitializationEvent e) {
		super.preInit (e);
		registerEntityRenderers ();
	}

	@Override
	public void init (FMLInitializationEvent e) {
		super.init (e);
	}

	@Override
	public void postInit (FMLPostInitializationEvent e) {
		super.postInit (e);
	}

	private void registerEntityRenderers () {
		RenderingRegistry.registerEntityRenderingHandler (EntityExplosiveArrow.class,RenderExplosiveArrow.INSTANCE);
		RenderingRegistry.registerEntityRenderingHandler (EntityLightningArrow.class,RenderLightningArrow.INSTANCE);
		RenderingRegistry.registerEntityRenderingHandler (EntityArcticArrow.class,RenderArcticArrow.INSTANCE);
		RenderingRegistry.registerEntityRenderingHandler (EntityRegularArrow.class,RenderRegularArrow.INSTANCE);
		RenderingRegistry.registerEntityRenderingHandler (EntityRainArrow.class,RenderRainArrow.INSTANCE);
		RenderingRegistry.registerEntityRenderingHandler (EntityHomingArrow.class,RenderHomingArrow.INSTANCE);
	}

	@Override
	public EntityPlayer getPlayer (MessageContext ctx) {
		return (ctx.side.isClient () ? Minecraft.getMinecraft ().player : super.getPlayer (ctx));
	}

	@Override
	public IThreadListener getThreadFromCTX (MessageContext ctx) {
		return (ctx.side.isClient () ? Minecraft.getMinecraft () : super.getThreadFromCTX (ctx));
	}
}
