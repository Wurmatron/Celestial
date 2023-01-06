package io.wurmatron.celestial.client.render;

import io.wurmatron.celestial.common.entity.EntityRegularArrow;
import io.wurmatron.celestial.common.reference.Global;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nullable;

public class RenderRegularArrow implements IRenderFactory <EntityRegularArrow> {

	public static final RenderRegularArrow INSTANCE = new RenderRegularArrow ();

	@Override
	public Render <? super EntityRegularArrow> createRenderFor (RenderManager manager) {
		return new EntityRenderRegularArrow (manager);
	}

	private class EntityRenderRegularArrow extends RenderArrow <EntityRegularArrow> {

		public EntityRenderRegularArrow (RenderManager renderManager) {
			super (renderManager);
		}

		@Nullable
		@Override
		protected ResourceLocation getEntityTexture (EntityRegularArrow entity) {
			return new ResourceLocation (Global.MODID,"textures/entity/regulararrow.png");
		}
	}
}
