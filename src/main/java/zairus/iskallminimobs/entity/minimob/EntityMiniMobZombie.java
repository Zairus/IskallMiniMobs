package zairus.iskallminimobs.entity.minimob;

import java.util.Calendar;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EnumCreatureAttribute;
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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
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
	
	public int getType()
	{
		return 1;
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
	
	@Override
	public boolean attackEntityAsMob(Entity entity)
	{
		return super.attackEntityAsMob(entity);
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
		;
	}
	
	@Override
	public void equip()
	{
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
	
	@Override
	public NBTTagCompound getMiniMobData()
	{
		NBTTagCompound data = super.getMiniMobData();
		
		data.setInteger(MiniMobData.MOBTYPE_KEY, 1);
		
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
	}
}
