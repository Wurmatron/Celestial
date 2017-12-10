package com.wurmcraft.celestial.client.render;

import com.wurmcraft.celestial.common.entity.EntityArcticArrow;
import com.wurmcraft.celestial.common.reference.Global;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nullable;

public class RenderArcticArrow implements IRenderFactory <EntityArcticArrow> {

	public static final RenderArcticArrow INSTANCE = new RenderArcticArrow ();

	@Override
	public Render <? super EntityArcticArrow> createRenderFor (RenderManager manager) {
		return new EntityRenderExplosiveArrow (manager);
	}

	private class EntityRenderExplosiveArrow extends RenderArrow <EntityArcticArrow> {

		public EntityRenderExplosiveArrow (RenderManager renderManager) {
			super (renderManager);
		}

		@Nullable
		@Override
		protected ResourceLocation getEntityTexture (EntityArcticArrow entity) {
			return new ResourceLocation (Global.MODID,"textures/entity/arcticarrow.png");
		}
	}
}
