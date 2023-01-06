package io.wurmatron.celestial.client.render;

import io.wurmatron.celestial.common.entity.EntityHomingArrow;
import io.wurmatron.celestial.common.reference.Global;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nullable;

public class RenderHomingArrow implements IRenderFactory <EntityHomingArrow> {

	public static final RenderHomingArrow INSTANCE = new RenderHomingArrow ();

	@Override
	public Render <? super EntityHomingArrow> createRenderFor (RenderManager manager) {
		return new EntityRenderExplosiveArrow (manager);
	}

	private class EntityRenderExplosiveArrow extends RenderArrow <EntityHomingArrow> {

		public EntityRenderExplosiveArrow (RenderManager renderManager) {
			super (renderManager);
		}

		@Nullable
		@Override
		protected ResourceLocation getEntityTexture (EntityHomingArrow entity) {
			return new ResourceLocation (Global.MODID,"textures/entity/homingarrow.png");
		}
	}
}
