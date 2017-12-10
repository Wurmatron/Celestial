package com.wurmcraft.celestial.common.potion;

import com.wurmcraft.celestial.common.reference.Global;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
public class ArcticPotion extends Potion {

	public static final Potion INSTANCE = new ArcticPotion (true,0x42c5f4);
	private final ResourceLocation iconTexture;

	public ArcticPotion (boolean isBadEffectIn,int liquidColorIn) {
		super (isBadEffectIn,liquidColorIn);
		setPotionName ("Arctic");
		setRegistryName (new ResourceLocation (Global.MODID,"arctic"));
		iconTexture = new ResourceLocation (Global.MODID,"textures/potions/arctic.png");
	}

	@Override
	public boolean hasStatusIcon () {
		return true;
	}

	@Override
	public void performEffect (EntityLivingBase entity,int amplifier) {
		entity.setVelocity (0D,0D,0D);
		entity.velocityChanged = true;
	}

	@Override
	public boolean isReady (int duration,int amplifier) {
		return duration > 0;
	}

	@SideOnly (Side.CLIENT)
	@Override
	public void renderInventoryEffect (int x,final int y,final PotionEffect effect,final Minecraft mc) {
		if (mc.currentScreen != null) {
			mc.getTextureManager ().bindTexture (iconTexture);
			Gui.drawModalRectWithCustomSizedTexture (x + 6,y + 7,0,0,18,18,18,18);
		}
	}

	@SideOnly (Side.CLIENT)
	@Override
	public void renderHUDEffect (final int x,final int y,final PotionEffect effect,final Minecraft mc,final float alpha) {
		mc.getTextureManager ().bindTexture (iconTexture);
		Gui.drawModalRectWithCustomSizedTexture (x + 3,y + 3,0,0,18,18,18,18);
	}
}
