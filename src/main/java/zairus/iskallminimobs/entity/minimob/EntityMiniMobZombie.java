package zairus.iskallminimobs.entity.minimob;

import java.util.Calendar;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityMiniMobZombie extends EntityMiniMobBase
{
	private EntityAIAttackOnCollide aiAttackOnCollide = new EntityAIAttackOnCollide((EntityCreature) this, EntityMob.class, 1.2D, false);
	
	public EntityMiniMobZombie(World world)
	{
		super(world);
		
		this.setSize(0.3F, 0.5F);
		
		this.setCombatTask();
	}
	
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.45D);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0D);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.5D);
	}
	
	public int getTotalArmorValue()
	{
		int i = super.getTotalArmorValue() + 2;
		
		if (i > 20)
			i = 20;
		
		return i;
	}
	
	public void setCombatTask()
	{
		this.tasks.addTask(4, this.aiAttackOnCollide);
	}
	
	protected String getLivingSound()
	{
		return "mob.zombie.say";
	}
	
	protected String getHurtSound()
	{
		return "mob.zombie.hurt";
	}
	
	protected String getDeathSound()
	{
		return "mob.zombie.death";
	}
	
	protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_)
	{
		this.playSound("mob.zombie.step", 0.15F, 1.0F);
	}
	
	protected Item getDropItem()
	{
		return Items.rotten_flesh;
	}
	
	public EnumCreatureAttribute getCreatureAttribute()
	{
		return EnumCreatureAttribute.UNDEAD;
	}
	
	protected void dropRareDrop(int p_70600_1_)
	{
		switch (this.rand.nextInt(3))
		{
		case 0:
			this.dropItem(Items.iron_ingot, 1);
			break;
		case 1:
			this.dropItem(Items.carrot, 1);
			break;
		case 2:
			this.dropItem(Items.potato, 1);
		}
	}
	
	protected void addRandomArmor()
	{
		super.addRandomArmor();
		
		if (this.rand.nextFloat() < (this.worldObj.difficultySetting == EnumDifficulty.HARD ? 0.05F : 0.01F))
		{
			int i = this.rand.nextInt(3);
			
			if (i == 0)
			{
				this.setCurrentItemOrArmor(0, new ItemStack(Items.iron_sword));
			} else
			{
				this.setCurrentItemOrArmor(0, new ItemStack(Items.iron_shovel));
			}
		}
	}
	
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData p_110161_1_)
	{
		Object p_110161_1_1 = super.onSpawnWithEgg(p_110161_1_);
		
		this.setCanPickUpLoot(true);
		
		this.addRandomArmor();
		this.enchantEquipment();
		
		if (this.getEquipmentInSlot(4) == null)
		{
			Calendar calendar = this.worldObj.getCurrentDate();
			
			if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31
					&& this.rand.nextFloat() < 0.25F)
			{
				this.setCurrentItemOrArmor(
						4,
						new ItemStack(
								this.rand.nextFloat() < 0.1F ? Blocks.lit_pumpkin
										: Blocks.pumpkin));
				this.equipmentDropChances[4] = 0.0F;
			}
		}
		
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
		
		return (IEntityLivingData) p_110161_1_1;
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
		
		if (entity instanceof EntityPlayer && entity == this.getOwner() && this.getHeldItem() != null)
		{
			ItemStack stack = this.getHeldItem();
			
			this.setCurrentItemOrArmor(0, (ItemStack)null);
			
			if(!((EntityPlayer)entity).inventory.addItemStackToInventory(stack))
			{
				this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, entity.posX, entity.posY, entity.posZ, stack));
			}
		}
	}
}
