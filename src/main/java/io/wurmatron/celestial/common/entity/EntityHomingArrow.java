package io.wurmatron.celestial.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.vecmath.AxisAngle4d;
import javax.vecmath.Matrix4d;
import javax.vecmath.Vector3d;
import java.util.Comparator;
import java.util.List;

public class EntityHomingArrow extends EntityTippedArrow {

	private static final DataParameter <Integer> TARGET_ID = EntityDataManager.createKey (EntityHomingArrow.class,DataSerializers.VARINT);

	private int cooldown = 0;

	public EntityHomingArrow (World world) {
		super (world);
	}

	public EntityHomingArrow (World world,EntityLivingBase par2,float damage) {
		super (world,par2);
		setDamage (damage);
	}

	@Override
	public void entityInit () {
		super.entityInit ();
		dataManager.register (TARGET_ID,-1);
	}

	@Override
	public void onUpdate () {
		super.onUpdate ();
		if (!world.isRemote && ticksExisted > 3)
			if (inGround || getTarget () != null && (!getTarget ().isEntityAlive ()))
				dataManager.set (TARGET_ID,-1);
		if (getTarget () == null && !inGround && cooldown <= 0)
			findNewTarget ();
		else
			cooldown--;
		if (ticksExisted > 3 && getTarget () != null && !inGround) {
			for (int x = 0; x < 4; x++)
				getEntityWorld ().spawnParticle (EnumParticleTypes.FLAME,this.posX + this.motionX / 4.0D,this.posY + this.motionY / 4.0D,this.posZ + this.motionZ / 4.0D,-this.motionX / 2,-this.motionY / 2 + 0.2D,-this.motionZ / 2);
			Entity target = getTarget ();
			Vector3d lookVec = new Vector3d (target.posX,target.posY + target.height / 2,target.posZ);
			lookVec.sub (new Vector3d (posX,posY,posZ));
			Vector3d arrowMotion = new Vector3d (motionX,motionY,motionZ);
			double theta = wrap180Radian (arrowMotion.angle (lookVec));
			theta = clampAbs (theta,Math.PI / 2);
			Vector3d crossProduct = new Vector3d ();
			crossProduct.cross (arrowMotion,lookVec);
			crossProduct.normalize ();
			Matrix4d transform = new Matrix4d ();
			transform.set (new AxisAngle4d (crossProduct,theta));
			Vector3d adjustedLookVec = new Vector3d (arrowMotion);
			transform.transform (arrowMotion,adjustedLookVec);
			shoot (adjustedLookVec.x,adjustedLookVec.y,adjustedLookVec.z,1f,0);
		}
	}

	@Nonnull
	@Override
	protected ItemStack getArrowStack () {
		return ItemStack.EMPTY;
	}

	private void findNewTarget () {
		List <EntityLiving> entityList = world.getEntitiesWithinAABB (EntityLiving.class,this.getEntityBoundingBox ().grow (8,8,8));
		if (!entityList.isEmpty ()) {
			entityList.sort (Comparator.comparing (EntityHomingArrow.this :: getDistanceSq,Double:: compare));
			dataManager.set (TARGET_ID,entityList.get (0).getEntityId ());
			cooldown = 5;
		}
	}

	private EntityLiving getTarget () {
		return ((EntityLiving) world.getEntityByID (dataManager.get (TARGET_ID)));
	}

	private double wrap180Radian (double radian) {
		radian %= 2 * Math.PI;
		while (radian >= Math.PI)
			radian -= 2 * Math.PI;
		while (radian < -Math.PI)
			radian += 2 * Math.PI;
		return radian;
	}

	private double clampAbs (double param,double maxMagnitude) {
		if (Math.abs (param) > maxMagnitude)
			if (param < 0)
				param = -Math.abs (maxMagnitude);
			else
				param = Math.abs (maxMagnitude);
		return param;
	}
}
