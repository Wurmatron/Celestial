package io.wurmatron.celestial.client.render;

import io.wurmatron.celestial.common.entity.EntityExplosiveArrow;
import io.wurmatron.celestial.common.reference.Global;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nullable;

public class RenderExplosiveArrow implements IRenderFactory <EntityExplosiveArrow> {

	public static final RenderExplosiveArrow INSTANCE = new RenderExplosiveArrow ();

	@Override
	public Render <? super EntityExplosiveArrow> createRenderFor (RenderManager manager) {
		return new EntityRenderExplosiveArrow (manager);
	}

	private class EntityRenderExplosiveArrow extends RenderArrow <EntityExplosiveArrow> {

		public EntityRenderExplosiveArrow (RenderManager renderManager) {
			super (renderManager);
		}

		@Nullable
		@Override
		protected ResourceLocation getEntityTexture (EntityExplosiveArrow entity) {
			return new ResourceLocation (Global.MODID,"textures/entity/explosivearrow.png");
		}
	}
}
