package zairus.iskallminimobs.entity.minimob;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityMiniMobCreeper extends EntityMiniMobBase
{
	private EntityAIAttackOnCollide aiAttackOnCollide = new EntityAIAttackOnCollide((EntityCreature) this, EntityMob.class, 1.2D, false);
	
	public EntityMiniMobCreeper(World world)
	{
		super(world);
		
		this.setSize(0.35F, 0.45F);
		
		this.setCombatTask();
	}
	
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.45D);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0D);
	}
	
	public void setCombatTask()
	{
		this.tasks.addTask(4, this.aiAttackOnCollide);
	}
	
	protected String getHurtSound()
	{
		return "mob.creeper.say";
	}
	
	protected String getDeathSound()
	{
		return "mob.creeper.death";
	}
	
	public void onDeath(DamageSource p_70645_1_)
	{
		super.onDeath(p_70645_1_);
		
		if (p_70645_1_.getEntity() instanceof EntitySkeleton)
		{
			int i = Item.getIdFromItem(Items.record_13);
			int j = Item.getIdFromItem(Items.record_wait);
			int k = i + this.rand.nextInt(j - i + 1);
			this.dropItem(Item.getItemById(k), 1);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public float getCreeperFlashIntensity(float p_70831_1_)
	{
		return 0.0F;
	}
	
	protected Item getDropItem()
	{
		return Items.gunpowder;
	}
	
	public boolean getPowered()
	{
		return false;
	}
}
