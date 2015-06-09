package zairus.iskallminimobs.entity.minimob;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class EntityMiniMobPig extends EntityMiniMobBase
{
	private EntityAIAttackOnCollide aiAttackOnCollide = new EntityAIAttackOnCollide((EntityCreature) this, EntityMob.class, 1.2D, false);
	private EntityAILeapAtTarget aiLeapAtTarget = new EntityAILeapAtTarget(this, 0.425F);
	
	public EntityMiniMobPig(World world)
	{
		super(world);
		
		this.setSize(0.45F, 0.45F);
		
		this.setCombatTask();
	}
	
	public void setCombatTask()
	{
		this.tasks.addTask(4, this.aiLeapAtTarget);
		this.tasks.addTask(5, this.aiAttackOnCollide);
	}
	
	protected String getLivingSound()
	{
		return "mob.pig.say";
	}
	
	protected String getHurtSound()
	{
		return "mob.pig.say";
	}
	
	protected String getDeathSound()
	{
		return "mob.pig.death";
	}
	
	protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_)
	{
		this.playSound("mob.pig.step", 0.15F, 1.0F);
	}
	
	protected Item getDropItem()
	{
		return this.isBurning() ? Items.cooked_porkchop : Items.porkchop;
	}
	
	protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
	{
		int j = this.rand.nextInt(3) + 1 + this.rand.nextInt(1 + p_70628_2_);
		
		for (int k = 0; k < j; ++k)
		{
			if (this.isBurning())
			{
				this.dropItem(Items.cooked_porkchop, 1);
			}
			else
			{
				this.dropItem(Items.porkchop, 1);
			}
		}
	}
}
