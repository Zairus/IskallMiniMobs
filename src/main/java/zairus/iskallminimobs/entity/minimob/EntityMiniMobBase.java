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
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import zairus.iskallminimobs.IskallMiniMobs;
import zairus.iskallminimobs.entity.ai.EntityAIFollowMaster;
import zairus.iskallminimobs.entity.ai.EntityAIMasterHurtByTarget;
import zairus.iskallminimobs.inventory.InventoryMiniMob;
import zairus.iskallminimobs.item.MMItems;

public abstract class EntityMiniMobBase
	extends EntityCreature
	implements IMob, IInventory
{
	public double speedCurrent = 0.20D;
	public double speedMax = 0.50D;
	public double healthCurrent = 10.0D;
	public double healthMax = 96.0D;
	public double followRangeCurrent = 15.0D;
	public double followRangeMax = 20.0D;
	public double attackDamageCurrent = 1.0D;
	public double attackDamageMax = 5.0D;
	
	public double experience = 0.0D;
	public int level = 0;
	public double nextLevelUp = 10.0D;
	public InventoryMiniMob inventory = new InventoryMiniMob();
	public int actionRequested = 0;
	
	private String miniMobUUID;
	
	protected boolean canEquipArmor = false;
	protected boolean canWeildWeapon = false;
	
	public EntityMiniMobBase(World world)
	{
		super(world);
		
		this.experienceValue = 0;
		
		this.getNavigator().setAvoidsWater(true);
		
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIFollowMaster(this, 1.0D, 15.0F, 2.0F));
		this.tasks.addTask(3, new EntityAIWander(this, 0.6D));
		
		this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityMob.class, 8.0F));
		this.tasks.addTask(10, new EntityAIWatchClosest(this, EntitySlime.class, 8.0F));
		this.tasks.addTask(11, new EntityAILookIdle(this));
		this.tasks.addTask(12, new EntityAINearestAttackableTarget(this, EntityMob.class, 0, true));
		this.tasks.addTask(13, new EntityAINearestAttackableTarget(this, EntitySlime.class, 0, true));
		
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		this.targetTasks.addTask(2, new EntityAIMasterHurtByTarget(this));
		this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityMob.class, 0, true));
		this.targetTasks.addTask(4, new EntityAINearestAttackableTarget(this, EntitySlime.class, 0, true));
		
		applyMiniMobAttributes();
	}
	
	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
	}
	
	protected void applyMiniMobAttributes()
	{
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(this.speedCurrent);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(this.healthCurrent);
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(this.followRangeCurrent);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(this.attackDamageCurrent);
	}
	
	public void applyAttributesFromNBT(NBTTagCompound data)
	{
		NBTTagList nbttaglist = data.getTagList(MiniMobData.INVENTORY_KEY, 10);
		NBTTagList inv = data.getTagList(MiniMobData.INVENTORY_EX_KEY, 10);
		
		this.miniMobUUID = data.getString(MiniMobData.UUID_KEY);
		
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
		
		this.dataWatcher.updateObject(20, Integer.valueOf(this.level));
		this.dataWatcher.updateObject(21, Float.valueOf((float)this.experience));
		
		this.setItemList(nbttaglist);
		this.setInventoryFromNBT(inv);
		this.applyMiniMobAttributes();
		
		this.setHealth((float)this.getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue());
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
		this.dataWatcher.addObject(13, new Byte((byte)0));
		this.dataWatcher.addObject(20, new Integer((int)0));
		this.dataWatcher.addObject(21, new Float((float)0));
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
	
	public String getMiniMobName()
	{
		String name;
		
		if (this.hasCustomNameTag())
			name = this.getCustomNameTag();
		else
		{
			switch (this.getType())
			{
			case 1:
				name = "Mini Zombie";
				break;
			case 2:
				name = "Mini Skeleton";
				break;
			case 3:
				name = "Mini Creeper";
				break;
			case 4:
				name = "Mini Spider";
				break;
			case 5:
				name = "Mini Soldier";
				break;
			default:
				name = "Mini Pig";
				break;
			}
		}
		
		return name;
	}
	
	public String getMiniMobUUID()
	{
		if (this.miniMobUUID == null || this.miniMobUUID == "")
			this.miniMobUUID = UUID.randomUUID().toString();
		
		return this.miniMobUUID;
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
		
		boolean flag = entity.attackEntityFrom(DamageSource.causeMobDamage(this), 0.001F);
		flag = entity.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)this.getOwner()), f);
		
		if (flag)
		{
			entity.hitByEntity(this);
			if (entity instanceof EntityCreature)
			{
				((EntityCreature)entity).setTarget(this);
				((EntityCreature)entity).setLastAttacker(this);
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
		this.dataWatcher.updateObject(21, Float.valueOf((float)this.experience));
		
		if (this.nextLevelUp == 0)
			this.nextLevelUp = 10.0D;
		
		if (this.experience >= this.nextLevelUp)
		{
			++this.level;
			this.dataWatcher.updateObject(20, Integer.valueOf(this.level));
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
	
	public int getCurrentLevel()
	{
		return this.dataWatcher.getWatchableObjectInt(20);
	}
	
	public double getCurrentExperience()
	{
		return (double)this.dataWatcher.getWatchableObjectFloat(21);
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
	
	public abstract int getType();
	
	@Override
	protected boolean interact(EntityPlayer player)
	{
		if (player.isSneaking())
		{
			this.actionRequested = 1;
			if (!this.worldObj.isRemote)
			{
				player.openGui(IskallMiniMobs.instance, 1, this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ);
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public void onDeath(DamageSource source)
	{
		super.onDeath(source);
		
		if (!worldObj.isRemote)
		{
			ItemStack corpse = new ItemStack(MMItems.mm_corpse, 1, getType());
			
			NBTTagCompound data = getMiniMobData();
			NBTTagCompound tag = corpse.getTagCompound();
			
			if (tag == null)
				tag = new NBTTagCompound();
			
			corpse.setTagCompound(tag);
			corpse.getTagCompound().setTag(MiniMobData.MOBDATA_KEY, data);
			
			if (this.hasCustomNameTag())
				corpse.setStackDisplayName(this.getCustomNameTag() + "'s corpse");
			
			this.entityDropItem(corpse, 1);
		}
	}
	
	protected NBTTagList getItemList()
	{
		return new NBTTagList();
	}
	
	protected void setItemList(NBTTagList nbttaglist)
	{
		;
	}
	
	protected NBTTagList getInventoryToNBT()
	{
		NBTTagList inv = new NBTTagList();
		
		this.inventory.writeToNBT(inv);
		
		return inv;
	}
	
	protected void setInventoryFromNBT(NBTTagList inv)
	{
		this.inventory.readFromNBT(inv);
	}
	
	public NBTTagCompound getMiniMobData()
	{
		NBTTagCompound data = new NBTTagCompound();
		NBTTagList nbttaglist = getItemList();
		
		data.setString("OwnerUUID", this.getOwnerUUID());
		
		if (this.miniMobUUID == null || this.miniMobUUID == "")
		{
			this.miniMobUUID = UUID.randomUUID().toString();
		}
		
		data.setString(MiniMobData.UUID_KEY, this.miniMobUUID);
		
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
		
		data.setInteger(MiniMobData.MOBTYPE_KEY, getType());
		
		data.setTag(MiniMobData.INVENTORY_KEY, nbttaglist);
		data.setTag(MiniMobData.INVENTORY_EX_KEY, this.getInventoryToNBT());
		
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
		
		tag.setString(MiniMobData.UUID_KEY, this.miniMobUUID);
		tag.setDouble(MiniMobData.SPEED_KEY, this.speedCurrent);
		tag.setDouble(MiniMobData.HEALTH_KEY, this.healthCurrent);
		tag.setDouble(MiniMobData.FOLLOW_KEY, this.followRangeCurrent);
		tag.setDouble(MiniMobData.ATTACK_KEY, this.attackDamageCurrent);
		tag.setDouble(MiniMobData.EXPERIENCE_KEY, this.experience);
		
		tag.setInteger(MiniMobData.LEVEL_KEY, this.level);
		tag.setDouble(MiniMobData.NEXTLEVEL_KEY, this.nextLevelUp);
		
		tag.setTag(MiniMobData.INVENTORY_KEY, nbttaglist);
		tag.setTag(MiniMobData.INVENTORY_EX_KEY, this.getInventoryToNBT());
	}
	
	public void readEntityFromNBT(NBTTagCompound tag)
	{
		super.readEntityFromNBT(tag);
		String s = "";
		NBTTagList nbttaglist = tag.getTagList(MiniMobData.INVENTORY_KEY, 10);
		NBTTagList inv = tag.getTagList(MiniMobData.INVENTORY_EX_KEY, 10);
		
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
		
		if (tag.hasKey(MiniMobData.UUID_KEY))
			this.miniMobUUID = tag.getString(MiniMobData.UUID_KEY);
		
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
		
		this.dataWatcher.updateObject(20, Integer.valueOf(this.level));
		this.dataWatcher.updateObject(21, Float.valueOf((float)this.experience));
		
		this.setItemList(nbttaglist);
		this.setInventoryFromNBT(inv);
	}
	
	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}
	
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack)
	{
		if (this.canWeildWeapon && slot == 0 && (stack.getItem() instanceof ItemBow || stack.getItem() instanceof ItemSword))
		{
			return true;
		} else if (this.canEquipArmor && stack.getItem() instanceof ItemArmor) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		if (this.getEquipmentInSlot(slot) != null)
		{
			ItemStack itemstack = this.getEquipmentInSlot(slot);
			this.setCurrentItemOrArmor(slot, null);
			return itemstack;
		}
		else
		{
			return null;
		}
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_)
	{
		return true;
	}
	private int numPlayersUsing = 0;
	@Override
	public void openInventory()
	{
		if (this.numPlayersUsing < 0)
		{
			this.numPlayersUsing = 0;
        }
		
		++this.numPlayersUsing;
	}
	
	@Override
	public void closeInventory()
	{
		--this.numPlayersUsing;
	}
	
	@Override
	public String getInventoryName()
	{
		return "minimob.inventory";
	}
	
	@Override
	public void setInventorySlotContents(int slot, ItemStack stack)
	{
		this.setCurrentItemOrArmor(slot, stack);
		
		if (stack != null && stack.stackSize > this.getInventoryStackLimit())
		{
			stack.stackSize = this.getInventoryStackLimit();
		}
		
        this.markDirty();
	}
	
	@Override
	public boolean hasCustomInventoryName()
	{
		return false;
	}
	
	@Override
	public void markDirty()
	{
	}
	
	@Override
	public int getSizeInventory()
	{
		return 5;
	}
	
	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return this.getEquipmentInSlot(slot);
	}
	
	@Override
	public ItemStack decrStackSize(int slot, int size)
	{
		if (this.getEquipmentInSlot(slot) != null)
		{
			ItemStack itemstack;
			
			if (this.getEquipmentInSlot(slot).stackSize <= size)
			{
				itemstack = this.getEquipmentInSlot(slot);
				this.setCurrentItemOrArmor(slot, null);
				this.markDirty();
				return itemstack;
			}
			else
			{
				itemstack = this.getEquipmentInSlot(slot).splitStack(size);
				
				if (this.getEquipmentInSlot(slot).stackSize == 0)
				{
					this.setCurrentItemOrArmor(slot, null);
				}
				
				this.markDirty();
				return itemstack;
			}
		}
		else
		{
			return null;
		}
	}
}
