package zairus.iskallminimobs.entity.minimob;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityMiniMobSkeleton
	extends EntityMiniMobBase
	implements IRangedAttackMob
{
	private EntityAIArrowAttack aiArrowAttack = new EntityAIArrowAttack(this, 1.0D, 20, 60, 15.0F);
	private EntityAIAttackOnCollide aiAttackOnCollide = new EntityAIAttackOnCollide((EntityCreature)this, EntityMob.class, 1.2D, false);
	
	public EntityMiniMobSkeleton(World world) {
		super(world);
		
		this.setSize(0.3F, 0.5F);
		
		if (world != null && !world.isRemote)
			this.setCombatTask();
		
		this.canEquipArmor = true;
		this.canWeildWeapon = true;
	}
	
	public int getType()
	{
		return 2;
	}
	
	protected String getLivingSound()
	{
		return "mob.skeleton.say";
	}
	
	protected String getHurtSound()
	{
		return "mob.skeleton.hurt";
	}
	
	protected String getDeathSound()
	{
		return "mob.skeleton.death";
	}
	
	protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_)
	{
		this.playSound("mob.skeleton.step", 0.25F, 1.0F);
	}
	
	@Override
	public boolean attackEntityAsMob(Entity entity)
	{
		if (super.attackEntityAsMob(entity))
		{
			if (this.getSkeletonType() == 1 && entity instanceof EntityLivingBase)
			{
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.wither.id, 200));
			}
			
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public EnumCreatureAttribute getCreatureAttribute()
	{
		return EnumCreatureAttribute.UNDEAD;
	}
	
	public void updateRidden()
	{
		super.updateRidden();
		
		if (this.ridingEntity instanceof EntityCreature)
		{
			EntityCreature entitycreature = (EntityCreature) this.ridingEntity;
			this.renderYawOffset = entitycreature.renderYawOffset;
		}
	}
	
	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		
		if (this.getSkeletonType() == 0)
		{
			ItemStack head = this.getEquipmentInSlot(4);
			
			if (
					head != null 
					&& head.getItem() == Items.skull 
					&& head.getItemDamage() == 1)
			{
				this.playSound("mob.wither.idle", 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
				this.setSkeletonType(1);
				this.setInventorySlotContents(4, null);
			}
		}
	}
	
	public void onDeath(DamageSource p_70645_1_)
	{
		super.onDeath(p_70645_1_);
		
		if (p_70645_1_.getSourceOfDamage() instanceof EntityArrow && p_70645_1_.getEntity() instanceof EntityPlayer)
		{
			EntityPlayer entityplayer = (EntityPlayer) p_70645_1_.getEntity();
			
			double d0 = entityplayer.posX - this.posX;
			double d1 = entityplayer.posZ - this.posZ;
			
			if (d0 * d0 + d1 * d1 >= 2500.0D)
			{
				entityplayer.triggerAchievement(AchievementList.snipeSkeleton);
			}
		}
	}
	
	protected Item getDropItem()
	{
		return Items.arrow;
	}
	
	protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
	{
		int j;
		int k;
		
		if (this.getSkeletonType() == 1)
		{
			this.entityDropItem(new ItemStack(Items.skull, 1, 1), 1);
			
			j = this.rand.nextInt(3 + p_70628_2_) - 1;
			
			for (k = 0; k < j; ++k) {
				this.dropItem(Items.coal, 1);
			}
		} else {
			j = this.rand.nextInt(3 + p_70628_2_);
			
			for (k = 0; k < j; ++k) {
				this.dropItem(Items.arrow, 1);
			}
		}
		
		j = this.rand.nextInt(3 + p_70628_2_);
		
		for (k = 0; k < j; ++k)
		{
			this.dropItem(Items.bone, 1);
		}
	}
	
	protected void dropRareDrop(int p_70600_1_)
	{
		if (this.getSkeletonType() == 1)
		{
			this.entityDropItem(new ItemStack(Items.skull, 1, 1), 0.0F);
		}
	}
	
	protected void addRandomArmor()
	{
		this.setCurrentItemOrArmor(0, new ItemStack(Items.bow));
	}
	
	@Override
	public void equip()
	{
		if (this.getRNG().nextInt(5) > 500000)
		{
			this.tasks.addTask(4, this.aiAttackOnCollide);
			
			this.setSkeletonType(1);
			this.setCurrentItemOrArmor(0, new ItemStack(Items.stone_sword));
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0D);
		} else {
			this.tasks.addTask(4, this.aiArrowAttack);
		}
		
		this.setCanPickUpLoot(false);
	}
	
	public void setCombatTask()
	{
		this.tasks.removeTask(this.aiAttackOnCollide);
		this.tasks.removeTask(this.aiArrowAttack);
		
		ItemStack itemstack = this.getHeldItem();
		
		if (itemstack != null && itemstack.getItem() == Items.bow)
		{
			this.tasks.addTask(4, this.aiArrowAttack);
		} else {
			this.tasks.addTask(4, this.aiAttackOnCollide);
		}
	}
	
	public void attackEntityWithRangedAttack(EntityLivingBase entity, float p_82196_2_)
	{
		EntityArrow entityarrow = new EntityArrow(
				this.worldObj,
				this,
				entity,
				1.6F,
				(float) (14 - this.worldObj.difficultySetting.getDifficultyId() * 4));
		
		int i = EnchantmentHelper.getEnchantmentLevel(
				Enchantment.power.effectId, this.getHeldItem());
		
		int j = EnchantmentHelper.getEnchantmentLevel(
				Enchantment.punch.effectId, this.getHeldItem());
		
		entityarrow.setDamage((double) (p_82196_2_ * 2.0F)
				+ this.rand.nextGaussian()
				* 0.25D
				+ (double) ((float) this.worldObj.difficultySetting
						.getDifficultyId() * 0.11F));
		
		if (i > 0)
		{
			entityarrow.setDamage(entityarrow.getDamage() + (double) i * 0.5D + 0.5D);
		}
		
		if (j > 0)
		{
			entityarrow.setKnockbackStrength(j);
		}
		
		if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, this.getHeldItem()) > 0 || this.getSkeletonType() == 1)
		{
			entityarrow.setFire(100);
		}
		
		entityarrow.shootingEntity = this.getOwner();
		
		this.playSound("random.bow", 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
		this.worldObj.spawnEntityInWorld(entityarrow);
		
		if (entity instanceof EntityCreature)
		{
			((EntityCreature)entity).setTarget(this);
			((EntityCreature)entity).setLastAttacker(this);
		}
		
		gainExperience(1.5D);
	}
	
	public int getSkeletonType()
	{
		return this.dataWatcher.getWatchableObjectByte(13);
	}
	
	public void setSkeletonType(int type)
	{
		this.dataWatcher.updateObject(13, Byte.valueOf((byte) type));
		this.isImmuneToFire = type == 1;
		
		if (type == 1) {
			this.setSize(0.72F, 2.34F);
		} else {
			this.setSize(0.6F, 1.8F);
		}
	}
	
	public void readEntityFromNBT(NBTTagCompound tag)
	{
		super.readEntityFromNBT(tag);
		
		if (tag.hasKey("SkeletonType", 99))
		{
			byte b0 = tag.getByte("SkeletonType");
			this.setSkeletonType(b0);
		}
		
		this.setCombatTask();
	}
	
	public void writeEntityToNBT(NBTTagCompound tag)
	{
		super.writeEntityToNBT(tag);
		
		tag.setByte("SkeletonType", (byte) this.getSkeletonType());
	}
	
	@Override
	public void setCurrentItemOrArmor(int slot, ItemStack stack)
	{
		super.setCurrentItemOrArmor(slot, stack);
		
		if (!this.worldObj.isRemote && slot == 0)
		{
			this.setCombatTask();
		}
	}
	
	public double getYOffset()
	{
		return super.getYOffset() - 0.5D;
	}
	
	@Override
	public float getBrightness(float f)
	{
		return 0.1F;
	}
	
	@Override
	public NBTTagCompound getMiniMobData()
	{
		NBTTagCompound data = super.getMiniMobData();
		
		data.setInteger(MiniMobData.MOBTYPE_KEY, 2);
		data.setInteger(MiniMobData.SKELETONTYPE_KEY, this.getSkeletonType());
		
		return data;
	}
	
	@Override
	public void applyAttributesFromNBT(NBTTagCompound data)
	{
		super.applyAttributesFromNBT(data);
		this.setSkeletonType(data.getInteger(MiniMobData.SKELETONTYPE_KEY));
	}
	
	@Override
	protected NBTTagList getItemList()
	{
		NBTTagList nbttaglist = new NBTTagList();
		
		for (int i = 0; i < 5; ++i)
		{
			NBTTagCompound nbttagcompound1 = new NBTTagCompound();
			nbttagcompound1.setByte("Slot", (byte)i);
			
			if (this.getEquipmentInSlot(i) != null)
				this.getEquipmentInSlot(i).writeToNBT(nbttagcompound1);
			
			nbttaglist.appendTag(nbttagcompound1);
		}
		
		return nbttaglist;
	}
	
	@Override
	protected void setItemList(NBTTagList nbttaglist)
	{
		for (int i = 0; i < nbttaglist.tagCount(); ++i)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			if (nbttagcompound1 != null)
			{
				int j = nbttagcompound1.getByte("Slot") & 255;
				
				if (j >= 0 && j < 5)
				{
					this.setCurrentItemOrArmor(j, ItemStack.loadItemStackFromNBT(nbttagcompound1));
				}
			}
		}
	}
}
