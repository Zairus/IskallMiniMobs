package zairus.iskallminimobs.entity.minimob;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import zairus.iskallminimobs.entity.ai.EntityAIFollowMaster;
import zairus.iskallminimobs.entity.ai.EntityAIMasterHurtByTarget;

public class EntityMiniMobBase
	extends EntityCreature
	implements IMob
{
	public EntityMiniMobBase(World world)
	{
		super(world);
		
		this.experienceValue = 0;
		
		this.getNavigator().setAvoidsWater(true);
		
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIFollowMaster(this, 1.0D, 15.0F, 2.0F));
		this.tasks.addTask(3, new EntityAIWander(this, 0.6D));
		
		this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(9, new EntityAILookIdle(this));
		this.tasks.addTask(10, new EntityAINearestAttackableTarget(this, EntityMob.class, 0, true));
		
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		this.targetTasks.addTask(2, new EntityAIMasterHurtByTarget(this));
		this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityMob.class, 0, true));
	}
	
	@SuppressWarnings({ "rawtypes" })
	public boolean canAttackClass(Class classEntity)
	{
		boolean canAttack = true;
		
		if (classEntity == EntityCreeper.class || classEntity == EntityGhast.class || EntityMiniMobBase.class.isAssignableFrom(classEntity))
		{
			canAttack = false;
		}
		
		return canAttack;
	}
	
	protected void entityInit()
	{
		super.entityInit();
		
		this.dataWatcher.addObject(16, Byte.valueOf((byte) 0));
		this.dataWatcher.addObject(17, "");
		
		this.dataWatcher.addObject(13, new Byte((byte) 0));
	}
	
	public boolean isAIEnabled()
	{
		return true;
	}
	
	@Override
	protected Entity findPlayerToAttack()
	{
		Entity ent = null;
		
		@SuppressWarnings("rawtypes")
		List list = this.worldObj.getEntitiesWithinAABB(EntityMob.class, this.boundingBox.expand(8.0D, 3.0D, 8.0D));
		@SuppressWarnings("rawtypes")
		Iterator iterator = list.iterator();
		
		if (iterator.hasNext())
			ent = (Entity)iterator.next();
		
		return ent;
	}
	
	public boolean attackEntityFrom(DamageSource source, float p_70097_2_)
	{
		if (this.isEntityInvulnerable())
		{
			return false;
		} else if (super.attackEntityFrom(source, p_70097_2_))
		{
			Entity entity = source.getEntity();
			
			if (this.riddenByEntity != entity && this.ridingEntity != entity)
			{
				if (entity != this)
				{
					this.entityToAttack = entity;
				}
				
				return true;
			} else
			{
				return true;
			}
		} else
		{
			return false;
		}
	}
	
	protected String getHurtSound()
	{
		return "game.hostile.hurt";
	}
	
	protected String getDeathSound()
	{
		return "game.hostile.die";
	}
	
	protected String func_146067_o(int p_146067_1_)
	{
		return p_146067_1_ > 4 ? "game.hostile.hurt.fall.big" : "game.hostile.hurt.fall.small";
	}
	
	public boolean attackEntityAsMob(Entity p_70652_1_)
	{
		float f = (float) this.getEntityAttribute(
				SharedMonsterAttributes.attackDamage).getAttributeValue();
		int i = 0;

		if (p_70652_1_ instanceof EntityLivingBase)
		{
			f += EnchantmentHelper.getEnchantmentModifierLiving(this,
					(EntityLivingBase) p_70652_1_);
			i += EnchantmentHelper.getKnockbackModifier(this,
					(EntityLivingBase) p_70652_1_);
		}

		boolean flag = p_70652_1_.attackEntityFrom(
				DamageSource.causeMobDamage(this), f);

		if (flag)
		{
			if (i > 0)
			{
				p_70652_1_.addVelocity(
						(double) (-MathHelper.sin(this.rotationYaw
								* (float) Math.PI / 180.0F)
								* (float) i * 0.5F),
						0.1D,
						(double) (MathHelper.cos(this.rotationYaw
								* (float) Math.PI / 180.0F)
								* (float) i * 0.5F));
				this.motionX *= 0.6D;
				this.motionZ *= 0.6D;
			}

			int j = EnchantmentHelper.getFireAspectModifier(this);

			if (j > 0)
			{
				p_70652_1_.setFire(j * 4);
			}

			if (p_70652_1_ instanceof EntityLivingBase)
			{
				EnchantmentHelper.func_151384_a((EntityLivingBase) p_70652_1_,
						this);
			}

			EnchantmentHelper.func_151385_b(this, p_70652_1_);
		}

		return flag;
	}
	
	protected void attackEntity(Entity p_70785_1_, float p_70785_2_)
	{
		if (this.attackTime <= 0 && p_70785_2_ < 2.0F
				&& p_70785_1_.boundingBox.maxY > this.boundingBox.minY
				&& p_70785_1_.boundingBox.minY < this.boundingBox.maxY)
		{
			this.attackTime = 20;
			this.attackEntityAsMob(p_70785_1_);
		}
	}
	
	public float getBlockPathWeight(int p_70783_1_, int p_70783_2_, int p_70783_3_)
	{
		return 0.5F - this.worldObj.getLightBrightness(p_70783_1_, p_70783_2_, p_70783_3_);
	}
	
	public void onLivingUpdate()
	{
		this.updateArmSwingProgress();
        
		super.onLivingUpdate();
		
		if (this.getOwner() == null)
		{
			EntityPlayer player = this.worldObj.getClosestPlayerToEntity(this, 64.0D);
			
			if (player != null)
			{
				this.setOwnerUUID(player.getUniqueID().toString());
				this.setTamed(true);
			}
		}
	}
	
	@Override
	public void onUpdate()
	{
		super.onUpdate();
	}
	
	protected String getSwimSound()
	{
		return "game.hostile.swim";
	}
	
	protected String getSplashSound()
	{
		return "game.hostile.swim.splash";
	}
	
	public void writeEntityToNBT(NBTTagCompound tag)
	{
		super.writeEntityToNBT(tag);
		
		if (this.getOwnerUUID() == null)
		{
			tag.setString("OwnerUUID", "");
		} else
		{
			tag.setString("OwnerUUID", this.getOwnerUUID());
		}
	}
	
	public void readEntityFromNBT(NBTTagCompound tag)
	{
		super.readEntityFromNBT(tag);
		String s = "";
		
		if (tag.hasKey("OwnerUUID", 8))
		{
			s = tag.getString("OwnerUUID");
		} else
		{
			String s1 = tag.getString("Owner");
			s = PreYggdrasilConverter.func_152719_a(s1);
		}
		
		if (s.length() > 0)
		{
			this.setOwnerUUID(s);
			this.setTamed(true);
		}
	}
	
	public boolean isTamed()
    {
        return (this.dataWatcher.getWatchableObjectByte(16) & 4) != 0;
    }
	
	public void setTamed(boolean tamed)
	{
		byte b0 = this.dataWatcher.getWatchableObjectByte(16);

		if (tamed)
		{
			this.dataWatcher.updateObject(16, Byte.valueOf((byte) (b0 | 4)));
		} else
		{
			this.dataWatcher.updateObject(16, Byte.valueOf((byte) (b0 & -5)));
		}
	}
	
	public String getOwnerUUID()
	{
		return this.dataWatcher.getWatchableObjectString(17);
	}
	
	public void setOwnerUUID(String uuid)
	{
		this.dataWatcher.updateObject(17, uuid);
	}
	
	public EntityLivingBase getOwner()
	{
		try
        {
            UUID uuid = UUID.fromString(this.getOwnerUUID());
            return uuid == null ? null : this.worldObj.func_152378_a(uuid);
        }
        catch (IllegalArgumentException illegalargumentexception)
        {
            return null;
        }
	}
	
	@Override
	public boolean isChild()
	{
		return true;
	}
	
	protected boolean isValidLightLevel()
	{
		return true;
	}
	
	@Override
	public boolean getCanSpawnHere()
	{
		return true;
	}
	
	@Override
	public boolean canDespawn()
	{
		return false;
	}
	
	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
	}
	
	@Override
	protected boolean func_146066_aG()
	{
		return true;
	}
}
