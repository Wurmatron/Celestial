package com.wurmcraft.celestial.common.entity;

import com.wurmcraft.celestial.common.potion.ArcticPotion;
import com.wurmcraft.celestial.common.reference.NBT;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;


public class EntityArcticArrow extends EntityArrow {

	private int time;

	public EntityArcticArrow (World world) {
		super (world);
		setDamage (0);
	}

	public EntityArcticArrow (World world,double x,double y,double z) {
		super (world,x,y,z);
		setDamage (0);
	}

	public EntityArcticArrow (World world,EntityLivingBase shooter) {
		super (world,shooter);
		setDamage (0);
	}

	public EntityArcticArrow (World world,EntityLivingBase shooter,int time) {
		super (world,shooter);
		this.time = time;
		setDamage (0);
	}

	@Override
	protected ItemStack getArrowStack () {
		return ItemStack.EMPTY;
	}

	@Override
	protected void onHit (RayTraceResult rayTrace) {
		super.onHit (rayTrace);
		if (inGround) {
			EntityAreaEffectCloud cloud = new EntityAreaEffectCloud (world,posX,posY,posZ);
			cloud.addEffect (new PotionEffect (ArcticPotion.INSTANCE,20));
			cloud.setColor (0x42c5f4);
			cloud.setDuration (time);
			cloud.setRadius (5);
			cloud.setWaitTime (20);
			world.spawnEntity (cloud);
			setDead ();
		}
	}

	protected void arrowHit (EntityLivingBase living) {
		living.addPotionEffect (new PotionEffect (ArcticPotion.INSTANCE,time));
		setDead ();
	}

	@Override
	public void readEntityFromNBT (NBTTagCompound nbt) {
		super.readEntityFromNBT (nbt);
		time = nbt.getInteger (NBT.TIME);
	}

	@Override
	public void writeEntityToNBT (NBTTagCompound nbt) {
		super.writeEntityToNBT (nbt);
		nbt.setInteger (NBT.TIME,time);
	}
}
