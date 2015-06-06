package zairus.iskallminimobs.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import zairus.iskallminimobs.entity.minimob.EntityMiniMobBase;

public class EntityAIMasterHurtByTarget
	extends EntityAITarget
{
	public EntityMiniMobBase theDefendingMiniMob;
	public EntityLivingBase theMasterAttacker;
    private int revengeTimer;
	
	public EntityAIMasterHurtByTarget(EntityMiniMobBase minimob)
	{
		super(minimob, false);
		this.theDefendingMiniMob = minimob;
		this.setMutexBits(1);
	}
	
	@Override
	public boolean shouldExecute()
	{
		if (!this.theDefendingMiniMob.isTamed())
        {
            return false;
        }
		else
		{
			EntityLivingBase entitylivingbase = this.theDefendingMiniMob.getOwner();
			
			if (entitylivingbase == null)
			{
				return false;
			}
			else
			{
				this.theMasterAttacker = entitylivingbase.getAITarget();
				
				int i = entitylivingbase.func_142015_aE();
				
				return i != this.revengeTimer && this.isSuitableTarget(this.theMasterAttacker, false);
			}
		}
	}
	
	public void startExecuting()
	{
		this.taskOwner.setAttackTarget(this.theMasterAttacker);
		EntityLivingBase entitylivingbase = this.theDefendingMiniMob.getOwner();
		
		if (entitylivingbase != null)
		{
			this.revengeTimer = entitylivingbase.func_142015_aE();
		}
		
		super.startExecuting();
	}
}
