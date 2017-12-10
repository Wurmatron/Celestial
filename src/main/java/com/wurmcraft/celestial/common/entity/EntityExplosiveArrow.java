package com.wurmcraft.celestial.common.entity;

import com.wurmcraft.celestial.common.reference.NBT;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;


public class EntityExplosiveArrow extends EntityArrow {

	private float explosionSize;

	public EntityExplosiveArrow (World world) {
		super (world);
	}

	public EntityExplosiveArrow (World world,double x,double y,double z) {
		super (world,x,y,z);
	}

	public EntityExplosiveArrow (World world,EntityLivingBase shooter) {
		super (world,shooter);
	}

	public EntityExplosiveArrow (World world,EntityLivingBase shooter,float explosionSize) {
		super (world,shooter);
		this.explosionSize = explosionSize;
	}

	@Override
	protected ItemStack getArrowStack () {
		return ItemStack.EMPTY;
	}

	@Override
	protected void onHit (RayTraceResult rayTrace) {
		super.onHit (rayTrace);
		if (inGround) {
			world.createExplosion (this,posX,posY,posZ,explosionSize,true);
			setDead ();
		}
	}

	protected void arrowHit (EntityLivingBase living) {
		world.createExplosion (this,posX,posY,posZ,explosionSize,true);
		setDead ();
	}

	@Override
	public void readEntityFromNBT (NBTTagCompound nbt) {
		super.readEntityFromNBT (nbt);
		explosionSize = nbt.getFloat (NBT.EXPLOSION_SIZE);
	}

	@Override
	public void writeEntityToNBT (NBTTagCompound nbt) {
		super.writeEntityToNBT (nbt);
		nbt.setFloat (NBT.EXPLOSION_SIZE,explosionSize);
	}

	public float getExplosionSize () {
		return explosionSize;
	}

	public void setExplosionSize (float size) {
		getEntityData ().setFloat (NBT.EXPLOSION_SIZE,size);
	}
}
