package zairus.iskallminimobs.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import zairus.iskallminimobs.entity.minimob.EntityMiniMobBase;
import zairus.iskallminimobs.entity.minimob.EntityMiniMobCreeper;
import zairus.iskallminimobs.entity.minimob.EntityMiniMobPig;
import zairus.iskallminimobs.entity.minimob.EntityMiniMobSkeleton;
import zairus.iskallminimobs.entity.minimob.EntityMiniMobSoldier;
import zairus.iskallminimobs.entity.minimob.EntityMiniMobSpider;
import zairus.iskallminimobs.entity.minimob.EntityMiniMobZombie;
import zairus.iskallminimobs.entity.minimob.MiniMobData;
import zairus.iskallminimobs.item.MMItems;

public class EntityMMPellet
	extends EntityThrowable
{
	public NBTTagCompound miniMobData;
	
	public EntityMMPellet(World world)
	{
		super(world);
	}
	
	public EntityMMPellet(World world, EntityLivingBase entity, NBTTagCompound data)
	{
		this(world, entity);
		this.miniMobData = data;
	}
	
	public EntityMMPellet(World world, EntityLivingBase entity)
	{
		super(world, entity);
		
		this.setLocationAndAngles(entity.posX, entity.posY + (double)entity.getEyeHeight(), entity.posZ, entity.rotationYaw, entity.rotationPitch);
		this.posX -= (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
		this.posY -= 0.10000000149011612D;
		this.posZ -= (double)(MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
		this.setPosition(this.posX, this.posY, this.posZ);
		this.yOffset = 0.0F;
		float f = 1.0F;
		this.motionX = (double)(-MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * f);
		this.motionZ = (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * f);
		this.motionY = (double)(-MathHelper.sin((this.rotationPitch + this.func_70183_g()) / 180.0F * (float)Math.PI) * f);
		this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, func_70182_d(), 1.0F);
	}
	
	public EntityMMPellet(World world, double x, double y, double z)
	{
		super(world, x, y, z);
	}
	
	@Override
	protected float func_70182_d()
	{
		return 0.5F;
	}
	
	@Override
	protected void onImpact(MovingObjectPosition movingObjectPosition)
	{
		for (int i = 0; i < 8; ++i)
			worldObj.spawnParticle("snowballpoof", posX, posY, posZ, 0.0D, 0.0D, 0.0D);
		
		if (!worldObj.isRemote)
		{
			EntityMiniMobBase miniMob;
			
			switch(miniMobData.getInteger(MiniMobData.MOBTYPE_KEY))
			{
			case 1:
				miniMob = new EntityMiniMobZombie(worldObj);
				break;
			case 2:
				miniMob = new EntityMiniMobSkeleton(worldObj);
				break;
			case 3:
				miniMob = new EntityMiniMobCreeper(worldObj);
				break;
			case 4:
				miniMob = new EntityMiniMobSpider(worldObj);
				break;
			case 5:
				miniMob = new EntityMiniMobSoldier(worldObj);
				break;
			default:
				miniMob = new EntityMiniMobPig(worldObj);
				break;
			}
			
			miniMob.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
			miniMob.equip();
			
			if (miniMobData != null)
				miniMob.applyAttributesFromNBT(miniMobData);
			
			playSound("iskallminimobs:pellet_crack", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
			
			worldObj.spawnEntityInWorld(miniMob);
			
			dropItem(MMItems.mm_pellet, 1);
			setDead();
		}
	}
}
