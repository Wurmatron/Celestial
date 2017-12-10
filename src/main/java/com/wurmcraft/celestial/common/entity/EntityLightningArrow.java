package com.wurmcraft.celestial.common.entity;

import com.wurmcraft.celestial.common.reference.NBT;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;


public class EntityLightningArrow extends EntityArrow {

	private int amount;

	public EntityLightningArrow (World world) {
		super (world);
	}

	public EntityLightningArrow (World world,double x,double y,double z) {
		super (world,x,y,z);
	}

	public EntityLightningArrow (World world,EntityLivingBase shooter) {
		super (world,shooter);
	}

	public EntityLightningArrow (World world,EntityLivingBase shooter,int amount) {
		super (world,shooter);
		this.amount = amount;
	}

	@Override
	protected ItemStack getArrowStack () {
		return ItemStack.EMPTY;
	}

	@Override
	protected void onHit (RayTraceResult rayTrace) {
		super.onHit (rayTrace);
		for (int a = 0; a < amount; a++)
			world.spawnEntity (new EntityLightningBolt (world,posX,posY + a,posZ,false));
		setDead ();
	}

	@Override
	public void readEntityFromNBT (NBTTagCompound nbt) {
		super.readEntityFromNBT (nbt);
		amount = nbt.getInteger (NBT.AMOUNT);
	}

	@Override
	public void writeEntityToNBT (NBTTagCompound nbt) {
		super.writeEntityToNBT (nbt);
		nbt.setInteger (NBT.AMOUNT,amount);
	}

	public int getAmount () {
		return amount;
	}

	public EntityLightningArrow setAmount (int amount) {
		getEntityData ().setFloat (NBT.AMOUNT,amount);
		return this;
	}
}
