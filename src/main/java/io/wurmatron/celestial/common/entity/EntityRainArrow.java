package io.wurmatron.celestial.common.entity;

import io.wurmatron.celestial.common.reference.NBT;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;


public class EntityRainArrow extends EntityArrow {

	private static final int ARROW_DAMAGE = 10;
	private int radius;

	public EntityRainArrow (World world) {
		super (world);
	}

	public EntityRainArrow (World world,double x,double y,double z) {
		super (world,x,y,z);
	}

	public EntityRainArrow (World world,EntityLivingBase shooter) {
		super (world,shooter);
	}

	public EntityRainArrow (World world,EntityLivingBase shooter,int radius) {
		super (world,shooter);
		this.radius = radius;
	}

	@Override
	protected ItemStack getArrowStack () {
		return ItemStack.EMPTY;
	}

	@Override
	protected void onHit (RayTraceResult rayTrace) {
		super.onHit (rayTrace);
		int y = world.getTopSolidOrLiquidBlock (new BlockPos (posX,posY,posZ)).getY () + 40;
		for (int x = 0; x < radius; x++)
			for (int z = 0; z < radius; z++) {
				world.spawnEntity (new EntityRegularArrow (world,posX + x,y,posZ + z,ARROW_DAMAGE));
				world.spawnEntity (new EntityRegularArrow (world,posX - x,y,posZ + z,ARROW_DAMAGE));
				world.spawnEntity (new EntityRegularArrow (world,posX + x,y,posZ - z,ARROW_DAMAGE));
				world.spawnEntity (new EntityRegularArrow (world,posX - x,y,posZ - z,ARROW_DAMAGE));
			}
		setDead ();
	}

	@Override
	public void readEntityFromNBT (NBTTagCompound nbt) {
		super.readEntityFromNBT (nbt);
		radius = nbt.getInteger (NBT.RADIUS);
	}

	@Override
	public void writeEntityToNBT (NBTTagCompound nbt) {
		super.writeEntityToNBT (nbt);
		nbt.setInteger (NBT.RADIUS,radius);
	}

	public int getRadius () {
		return radius;
	}

	public void setRadius (int radius) {
		getEntityData ().setFloat (NBT.RADIUS,radius);
	}
}
