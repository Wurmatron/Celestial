package com.wurmcraft.celestial.client.render;


import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

// TODO Create Actual Model
public class ModelDemi extends ModelBiped {

	public ModelRenderer leftTemp;
	public ModelRenderer rightTemp;

	public ModelDemi(float modelSize, float p_i1149_2_, int textureWidthIn, int textureHeightIn) {
		super(modelSize, p_i1149_2_, textureWidthIn, textureHeightIn);
		this.leftTemp = new ModelRenderer(this, 0, 0);
		this.leftTemp.setRotationPoint(4.5F, -1.5F, -3.5F);
		this.leftTemp.addBox(0.0F, 0.0F, 0.0F, 4, 1, 7, 0.0F);
		this.rightTemp = new ModelRenderer(this, 5, 20);
		this.rightTemp.setRotationPoint(-8.5F, -1.5F, -3.5F);
		this.rightTemp.addBox(0.0F, 0.0F, 0.0F, 4, 1, 7, 0.0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render (entity,f,f1,f2,f3,f4,f5);
		this.leftTemp.render(f5);
		this.rightTemp.render(f5);
	}
}
