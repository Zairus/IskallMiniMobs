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
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
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
	public double speedCurrent = 0.20D;
	public double speedMax = 0.50D;
	public double healthCurrent = 10.0D;
	public double healthMax = 80.0D;
	public double followRangeCurrent = 15.0D;
	public double followRangeMax = 20.0D;
	public double attackDamageCurrent = 1.0D;
	public double attackDamageMax = 5.0D;
	
	public double experience = 0.0D;
	public int level = 0;
	public double nextLevelUp = 10.0D;
	
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
		
		applyMiniMobAttributes();
	}
	
	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
	}
	
	public void applyMiniMobAttributes()
	{
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(this.speedCurrent);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(this.healthCurrent);
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(this.followRangeCurrent);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(this.attackDamageCurrent);
	}
	
	public void applyAttributesFromNBT(NBTTagCompound data)
	{
		NBTTagList nbttaglist = data.getTagList(MiniMobData.INVENTORY_KEY, 10);
		
		if (data.hasKey(MiniMobData.CUSTOMNAME_KEY))
		{
			this.setCustomNameTag(data.getString(MiniMobData.CUSTOMNAME_KEY));
		}
		
		this.speedCurrent = data.getDouble(MiniMobData.SPEED_KEY);
		this.healthCurrent = data.getDouble(MiniMobData.HEALTH_KEY);
		this.followRangeCurrent = data.getDouble(MiniMobData.FOLLOW_KEY);
		this.attackDamageCurrent = data.getDouble(MiniMobData.ATTACK_KEY);
		
		this.experience = data.getDouble(MiniMobData.EXPERIENCE_KEY);
		this.level = data.getInteger(MiniMobData.LEVEL_KEY);
		this.nextLevelUp = data.getDouble(MiniMobData.NEXTLEVEL_KEY);
		
		setItemList(nbttaglist);
		
		applyMiniMobAttributes();
	}
	
	public void equip() {}
	
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
	
	public boolean attackEntityAsMob(Entity entity)
	{
		float f = (float) this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
		
		int i = 0;
		
		if (entity instanceof EntityLivingBase)
		{
			f += EnchantmentHelper.getEnchantmentModifierLiving(this, (EntityLivingBase) entity);
			i += EnchantmentHelper.getKnockbackModifier(this, (EntityLivingBase) entity);
		}
		
		boolean flag = entity.attackEntityFrom(DamageSource.causeMobDamage(this), f);
		
		if (flag)
		{
			entity.hitByEntity(this);
			if (entity instanceof EntityCreature)
			{
				((EntityCreature)entity).setTarget(this);
			}
			
			if (i > 0)
			{
				entity.addVelocity(
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
				entity.setFire(j * 4);
			}
			
			if (entity instanceof EntityLivingBase)
			{
				EnchantmentHelper.func_151384_a((EntityLivingBase) entity, this);
			}
			
			EnchantmentHelper.func_151385_b(this, entity);
			
			gainExperience(1.0D);
		}

		return flag;
	}
	
	public void gainExperience(double exp)
	{
		this.experience += exp;
		
		if (this.nextLevelUp == 0)
			this.nextLevelUp = 10.0D;
		
		if (this.experience >= this.nextLevelUp)
		{
			++this.level;
			this.nextLevelUp = this.nextLevelUp * 1.9D;
			
			if (!worldObj.isRemote)
			{
				worldObj.playSoundAtEntity(this, "random.levelup", 1.0F, 1.0F / (this.rand.nextFloat() * 0.4F + 0.8F));
				generateRandomParticles("happyVillager");
			}
			
			this.speedCurrent += 0.05D;
			if (this.speedCurrent > this.speedMax)
				this.speedCurrent = this.speedMax;
			
			this.healthCurrent += 1.0D;
			if (this.healthCurrent > this.healthMax)
				this.healthCurrent = this.healthMax;
			
			this.followRangeCurrent += 0.5D;
			if (this.followRangeCurrent > this.followRangeMax)
				this.followRangeCurrent = this.followRangeMax;
			
			this.attackDamageCurrent += 0.02D;
			if (this.attackDamageCurrent > this.attackDamageMax)
				this.attackDamageCurrent = this.attackDamageMax;
			
			applyMiniMobAttributes();
			
			int d = 400;
			
			this.addPotionEffect(new PotionEffect(Potion.regeneration.id, d));
			this.addPotionEffect(new PotionEffect(Potion.resistance.id, d));
			this.addPotionEffect(new PotionEffect(Potion.fireResistance.id, d));
			this.addPotionEffect(new PotionEffect(Potion.damageBoost.id, d));
		}
	}
	
    private void generateRandomParticles(String particleName)
    {
		if (worldObj.isRemote)
			return;
		
        for (int i = 0; i < 5; ++i)
        {
            double d0 = this.rand.nextGaussian() * 0.02D;
            double d1 = this.rand.nextGaussian() * 0.02D;
            double d2 = this.rand.nextGaussian() * 0.02D;
            
            this.worldObj.spawnParticle(
            		particleName, 
            		this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, 
            		this.posY + 1.0D + (double)(this.rand.nextFloat() * this.height), 
            		this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, 
            		d0, 
            		d1, 
            		d2);
        }
    }
	
	protected void attackEntity(Entity entity, float f)
	{
		if (this.attackTime <= 0 && f < 2.0F
				&& entity.boundingBox.maxY > this.boundingBox.minY
				&& entity.boundingBox.minY < this.boundingBox.maxY)
		{
			this.attackTime = 20;
			this.attackEntityAsMob(entity);
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
	protected boolean func_146066_aG()
	{
		return true;
	}
	
	protected NBTTagList getItemList()
	{
		return new NBTTagList();
	}
	
	protected void setItemList(NBTTagList nbttaglist)
	{
		;
	}
	
	public NBTTagCompound getMiniMobData()
	{
		NBTTagCompound data = new NBTTagCompound();
		NBTTagList nbttaglist = getItemList();
		
		data.setString("OwnerUUID", this.getOwnerUUID());
		
		if (this.hasCustomNameTag())
		{
			data.setString(MiniMobData.CUSTOMNAME_KEY, this.getCustomNameTag());
		}
		
		data.setDouble(MiniMobData.SPEED_KEY, this.speedCurrent);
		data.setDouble(MiniMobData.HEALTH_KEY, this.healthCurrent);
		data.setDouble(MiniMobData.FOLLOW_KEY, this.followRangeCurrent);
		data.setDouble(MiniMobData.ATTACK_KEY, this.attackDamageCurrent);
		
		data.setDouble(MiniMobData.EXPERIENCE_KEY, this.experience);
		data.setDouble(MiniMobData.LEVEL_KEY, this.level);
		data.setDouble(MiniMobData.NEXTLEVEL_KEY, this.nextLevelUp);
		
		data.setInteger(MiniMobData.MOBTYPE_KEY, 0);
		
		data.setTag(MiniMobData.INVENTORY_KEY, nbttaglist);
		
		return data;
	}
	
	public void writeEntityToNBT(NBTTagCompound tag)
	{
		super.writeEntityToNBT(tag);
		NBTTagList nbttaglist = getItemList();
		
		if (this.getOwnerUUID() == null)
		{
			tag.setString("OwnerUUID", "");
		} else
		{
			tag.setString("OwnerUUID", this.getOwnerUUID());
		}
		
		if (this.hasCustomNameTag())
		{
			tag.setString(MiniMobData.CUSTOMNAME_KEY, this.getCustomNameTag());
		}
		
		tag.setDouble(MiniMobData.SPEED_KEY, this.speedCurrent);
		tag.setDouble(MiniMobData.HEALTH_KEY, this.healthCurrent);
		tag.setDouble(MiniMobData.FOLLOW_KEY, this.followRangeCurrent);
		tag.setDouble(MiniMobData.ATTACK_KEY, this.attackDamageCurrent);
		tag.setDouble(MiniMobData.EXPERIENCE_KEY, this.experience);
		
		tag.setInteger(MiniMobData.LEVEL_KEY, this.level);
		tag.setDouble(MiniMobData.NEXTLEVEL_KEY, this.nextLevelUp);
		
		tag.setTag(MiniMobData.INVENTORY_KEY, nbttaglist);
	}
	
	public void readEntityFromNBT(NBTTagCompound tag)
	{
		super.readEntityFromNBT(tag);
		String s = "";
		NBTTagList nbttaglist = tag.getTagList(MiniMobData.INVENTORY_KEY, 10);
		
		if (tag.hasKey("OwnerUUID", 8))
		{
			s = tag.getString("OwnerUUID");
		} else
		{
			String s1 = tag.getString("Owner");
			s = PreYggdrasilConverter.func_152719_a(s1);
		}
		
		if (tag.hasKey(MiniMobData.CUSTOMNAME_KEY, 8))
		{
			this.setCustomNameTag(tag.getString(MiniMobData.CUSTOMNAME_KEY));
		}
		
		if (s.length() > 0)
		{
			this.setOwnerUUID(s);
			this.setTamed(true);
		}
		
		if (tag.hasKey(MiniMobData.SPEED_KEY))
			this.speedCurrent = tag.getDouble(MiniMobData.SPEED_KEY);
		
		if (tag.hasKey(MiniMobData.HEALTH_KEY))
			this.healthCurrent = tag.getDouble(MiniMobData.HEALTH_KEY);
		
		if (tag.hasKey(MiniMobData.FOLLOW_KEY))
			this.followRangeCurrent = tag.getDouble(MiniMobData.FOLLOW_KEY);
		
		if (tag.hasKey(MiniMobData.ATTACK_KEY))
			this.attackDamageCurrent = tag.getDouble(MiniMobData.ATTACK_KEY);
		
		if (tag.hasKey(MiniMobData.EXPERIENCE_KEY))
			this.experience = tag.getDouble(MiniMobData.EXPERIENCE_KEY);
		
		if (tag.hasKey(MiniMobData.LEVEL_KEY))
			this.level = tag.getInteger(MiniMobData.LEVEL_KEY);
		
		if(tag.hasKey(MiniMobData.NEXTLEVEL_KEY))
			this.nextLevelUp = tag.getDouble(MiniMobData.NEXTLEVEL_KEY);
		
		setItemList(nbttaglist);
	}
}
