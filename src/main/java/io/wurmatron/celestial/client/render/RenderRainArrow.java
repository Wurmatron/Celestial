package io.wurmatron.celestial.client.render;

import io.wurmatron.celestial.common.entity.EntityRainArrow;
import io.wurmatron.celestial.common.reference.Global;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nullable;

public class RenderRainArrow implements IRenderFactory <EntityRainArrow> {

	public static final RenderRainArrow INSTANCE = new RenderRainArrow ();

	@Override
	public Render <? super EntityRainArrow> createRenderFor (RenderManager manager) {
		return new EntityRenderRegularArrow (manager);
	}

	private class EntityRenderRegularArrow extends RenderArrow <EntityRainArrow> {

		public EntityRenderRegularArrow (RenderManager renderManager) {
			super (renderManager);
		}

		@Nullable
		@Override
		protected ResourceLocation getEntityTexture (EntityRainArrow entity) {
			return new ResourceLocation (Global.MODID,"textures/entity/rainarrow.png");
		}
	}
}
