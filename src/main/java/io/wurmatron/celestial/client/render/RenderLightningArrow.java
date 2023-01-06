package io.wurmatron.celestial.client.render;

import io.wurmatron.celestial.common.entity.EntityLightningArrow;
import io.wurmatron.celestial.common.reference.Global;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nullable;

public class RenderLightningArrow implements IRenderFactory <EntityLightningArrow> {

	public static final RenderLightningArrow INSTANCE = new RenderLightningArrow ();

	@Override
	public Render <? super EntityLightningArrow> createRenderFor (RenderManager manager) {
		return new EntityRenderExplosiveArrow (manager);
	}

	private class EntityRenderExplosiveArrow extends RenderArrow <EntityLightningArrow> {

		public EntityRenderExplosiveArrow (RenderManager renderManager) {
			super (renderManager);
		}

		@Nullable
		@Override
		protected ResourceLocation getEntityTexture (EntityLightningArrow entity) {
			return new ResourceLocation (Global.MODID,"textures/entity/lightningarrow.png");
		}
	}
}
