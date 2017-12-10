package com.wurmcraft.celestial.common.entity;

import com.wurmcraft.celestial.common.reference.NBT;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;


public class EntityRegularArrow extends EntityArrow {

	private boolean partOfRain;

	public EntityRegularArrow (World world) {
		super (world);
	}

	public EntityRegularArrow (World world,double x,double y,double z) {
		super (world,x,y,z);
	}

	public EntityRegularArrow (World world,double x,double y,double z,float damage) {
		super (world,x,y,z);
		setDamage (damage);
		partOfRain = true;
	}

	public EntityRegularArrow (World world,EntityLivingBase shooter) {
		super (world,shooter);
	}

	public EntityRegularArrow (World world,EntityLivingBase shooter,float damage) {
		super (world,shooter);
		setDamage (damage);
	}

	@Override
	protected ItemStack getArrowStack () {
		return ItemStack.EMPTY;
	}

	@Override
	public void readEntityFromNBT (NBTTagCompound nbt) {
		super.readEntityFromNBT (nbt);
		partOfRain = nbt.getBoolean (NBT.PARTOFRAIN);
	}

	@Override
	public void writeEntityToNBT (NBTTagCompound nbt) {
		super.writeEntityToNBT (nbt);
		nbt.setBoolean (NBT.PARTOFRAIN,partOfRain);
	}

	@Override
	public void onUpdate () {
		super.onUpdate ();
		if (partOfRain && ticksExisted > 200)
			setDead ();
	}
}
