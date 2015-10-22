package zairus.iskallminimobs.entity.minimob;

import java.util.UUID;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

import com.google.common.base.Charsets;
import com.mojang.authlib.GameProfile;

public class EntityMiniMobSoldier
	extends EntityMiniMobBase
	implements IRangedAttackMob
{
	public UUID playerUUID;
	
	private final GameProfile gameProfile;
	private EntityAIArrowAttack aiArrowAttack = new EntityAIArrowAttack(this, 1.0D, 20, 60, 15.0F);
	private EntityAIAttackOnCollide aiAttackOnCollide = new EntityAIAttackOnCollide((EntityCreature) this, EntityMob.class, 1.5D, true);
	private int combatStat = 0;
	
	public EntityMiniMobSoldier(World world)
	{
		super(world);
		
		this.gameProfile = new GameProfile(null, "Winter_Grave");
		this.playerUUID = EntityMiniMobSoldier.getPlayerUUID(gameProfile);
		
		this.setSize(0.6F, 0.5F);
		
		if (world != null && !world.isRemote)
			this.setCombatTask();
		
		this.canEquipArmor = true;
		this.canWeildWeapon = true;
	}
	
	public int getType()
	{
		return 5;
	}
	
	public static UUID getPlayerUUID(GameProfile profile)
	{
		UUID uuid = profile.getId();
		
		if (uuid == null)
		{
			uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + profile.getName()).getBytes(Charsets.UTF_8));
		}
		
		return uuid;
    }
	
	@Override
	public void applyMiniMobAttributes()
	{
		super.applyMiniMobAttributes();
		
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(this.followRangeCurrent * 2.0D);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(this.healthCurrent * 1.10D);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(this.attackDamageCurrent * 1.20D);
	}
	
	public int getTotalArmorValue()
	{
		int i = super.getTotalArmorValue() + 5;
		
		if (i > 20)
			i = 20;
		
		return i;
	}
	
	public void setCombatTask()
	{
		if (this.combatStat == 1)
			this.tasks.removeTask(this.aiAttackOnCollide);
		
		if (this.combatStat == 2)
			this.tasks.removeTask(this.aiArrowAttack);
		
		ItemStack itemstack = this.getHeldItem();
		
		if (itemstack != null && itemstack.getItem() == Items.bow)
		{
			this.tasks.addTask(4, this.aiArrowAttack);
			this.combatStat = 2;
		} else {
			this.tasks.addTask(4, this.aiAttackOnCollide);
			this.combatStat = 1;
		}
	}
	
	protected String getHurtSound()
	{
		return "game.player.hurt";
	}
	
	protected String getDeathSound()
	{
		return "game.player.die";
	}
	
	protected String getSwimSound()
	{
		return "game.player.swim";
	}
	
	protected String getSplashSound()
	{
		return "game.player.swim.splash";
	}
	
	@Override
	public boolean isChild()
	{
		return false;
	}
	
	@Override
	public boolean attackEntityAsMob(Entity entity)
	{
		return super.attackEntityAsMob(entity);
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
		
		if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, this.getHeldItem()) > 0)
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
	
	protected void dropRareDrop(int p_70600_1_)
	{
		switch (this.rand.nextInt(3))
		{
		case 0:
			this.dropItem(Items.iron_ingot, 1);
			break;
		case 1:
			this.dropItem(Items.flint_and_steel, 1);
			break;
		case 2:
			this.dropItem(Items.apple, 1);
		}
	}
	
	protected void addRandomArmor()
	{
		;
	}
	
	@Override
	public void equip()
	{
		this.setCanPickUpLoot(false);
		
		this.addRandomArmor();
		this.enchantEquipment();
		
		this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance)
				.applyModifier(
						new AttributeModifier("Random spawn bonus", this.rand
								.nextDouble() * 0.05000000074505806D, 0));
		
		double d0 = this.rand.nextDouble()
				* 1.5D
				* (double) this.worldObj.func_147462_b(this.posX, this.posY,
						this.posZ);
		
		if (d0 > 1.0D)
		{
			this.getEntityAttribute(SharedMonsterAttributes.followRange)
					.applyModifier(
							new AttributeModifier("Random zombie-spawn bonus",
									d0, 2));
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
	protected void collideWithEntity(Entity entity)
	{
		super.collideWithEntity(entity);
	}
	
	@Override
	public NBTTagCompound getMiniMobData()
	{
		NBTTagCompound data = super.getMiniMobData();
		
		data.setInteger(MiniMobData.MOBTYPE_KEY, 5);
		
		return data;
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
		
		if (this.worldObj != null && !this.worldObj.isRemote)
		{
			this.setCombatTask();
		}
	}
	
	public ItemStack getArmorItemStack(int slot)
	{
		ItemStack armor = null;
		
		armor = this.getEquipmentInSlot(slot + 1);
		
		return armor;
	}
}
