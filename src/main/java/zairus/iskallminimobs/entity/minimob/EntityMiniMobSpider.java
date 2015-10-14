package zairus.iskallminimobs.entity.minimob;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class EntityMiniMobSpider extends EntityMiniMobBase
{
	private EntityAIAttackOnCollide aiAttackOnCollide = new EntityAIAttackOnCollide((EntityCreature) this, EntityMob.class, 1.2D, false);
	
	public EntityMiniMobSpider(World world)
	{
		super(world);
		
		this.setSize(0.35F, 0.45F);
		
		this.setCombatTask();
	}
	
	public int getType()
	{
		return 4;
	}
	
	public void setCombatTask()
	{
		this.tasks.addTask(4, this.aiAttackOnCollide);
	}
	
	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(14, new Byte((byte) 0));
	}
	
	public void onUpdate()
	{
		super.onUpdate();
		
		if (!this.worldObj.isRemote)
		{
			this.setBesideClimbableBlock(this.isCollidedHorizontally);
		}
	}
	
	protected String getLivingSound()
	{
		return "mob.spider.say";
	}
	
	protected String getHurtSound()
	{
		return "mob.spider.say";
	}
	
	protected String getDeathSound()
	{
		return "mob.spider.death";
	}
	
	protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_)
	{
		this.playSound("mob.spider.step", 0.15F, 1.0F);
	}
	
	protected Item getDropItem()
	{
		return Items.string;
	}
	
	protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
	{
		super.dropFewItems(p_70628_1_, p_70628_2_);
		
		if (p_70628_1_
				&& (this.rand.nextInt(3) == 0 || this.rand
						.nextInt(1 + p_70628_2_) > 0))
		{
			this.dropItem(Items.spider_eye, 1);
		}
	}
	
	public boolean isOnLadder()
	{
		return this.isBesideClimbableBlock();
	}
	
	public void setInWeb()
	{
	}
	
	@Override
	public boolean attackEntityAsMob(Entity entity)
	{
		if (super.attackEntityAsMob(entity))
		{
			if (level > 5)
			{
				if (this.rand.nextInt(10) > 7)
					((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.poison.id, 200));
			}
			return true;
		}
		
		return false;
	}
	
	public EnumCreatureAttribute getCreatureAttribute()
	{
		return EnumCreatureAttribute.ARTHROPOD;
	}
	
	public boolean isBesideClimbableBlock()
	{
		return (this.dataWatcher.getWatchableObjectByte(14) & 1) != 0;
	}
	
	public void setBesideClimbableBlock(boolean p_70839_1_)
	{
		byte b0 = this.dataWatcher.getWatchableObjectByte(14);
		
		if (p_70839_1_)
		{
			b0 = (byte) (b0 | 1);
		} else
		{
			b0 &= -2;
		}
		
		this.dataWatcher.updateObject(14, Byte.valueOf(b0));
	}
	
	@Override
	public NBTTagCompound getMiniMobData()
	{
		NBTTagCompound data = super.getMiniMobData();
		
		data.setInteger(MiniMobData.MOBTYPE_KEY, 4);
		
		return data;
	}
}
