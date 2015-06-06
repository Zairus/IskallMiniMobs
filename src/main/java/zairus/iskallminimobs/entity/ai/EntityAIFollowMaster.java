package zairus.iskallminimobs.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import zairus.iskallminimobs.entity.minimob.EntityMiniMobBase;

public class EntityAIFollowMaster extends EntityAIBase
{
	private EntityMiniMobBase thePet;
	private EntityLivingBase theOwner;
	World theWorld;
	private double field_75336_f;
	private PathNavigate petPathfinder;
	private int field_75343_h;
	float maxDist;
	float minDist;
	private boolean field_75344_i;
	
	public EntityAIFollowMaster(EntityMiniMobBase minimob, double p_i1625_2_, float minimumDistance, float maximumDistance)
	{
		this.thePet = minimob;
		this.theWorld = minimob.worldObj;
		this.field_75336_f = p_i1625_2_;
		this.petPathfinder = minimob.getNavigator();
		this.minDist = minimumDistance;
		this.maxDist = maximumDistance;
		this.setMutexBits(3);
	}
	
	@Override
	public boolean shouldExecute()
	{
		EntityLivingBase entitylivingbase = this.thePet.getOwner();
		
		if (entitylivingbase == null)
		{
			return false;
		} else if (this.thePet.getDistanceSqToEntity(entitylivingbase) < (double) (this.minDist * this.minDist))
		{
			return false;
		} else {
			this.theOwner = entitylivingbase;
			return true;
		}
	}
	
	public boolean continueExecuting()
	{
		return !this.petPathfinder.noPath() && this.thePet.getDistanceSqToEntity(this.theOwner) > (double) (this.maxDist * this.maxDist);
	}
	
	public void startExecuting()
	{
		this.field_75343_h = 0;
		this.field_75344_i = this.thePet.getNavigator().getAvoidsWater();
		this.thePet.getNavigator().setAvoidsWater(false);
	}
	
	public void resetTask()
	{
		this.theOwner = null;
		this.petPathfinder.clearPathEntity();
		this.thePet.getNavigator().setAvoidsWater(this.field_75344_i);
	}
	
	public void updateTask()
	{
		this.thePet.getLookHelper().setLookPositionWithEntity(this.theOwner, 10.0F, (float) this.thePet.getVerticalFaceSpeed());
		
		if (--this.field_75343_h <= 0)
		{
			this.field_75343_h = 10;
			
			if (!this.petPathfinder.tryMoveToEntityLiving(this.theOwner, this.field_75336_f))
			{
				if (!this.thePet.getLeashed())
				{
					if (this.thePet.getDistanceSqToEntity(this.theOwner) >= 144.0D)
					{
						int i = MathHelper.floor_double(this.theOwner.posX) - 2;
						int j = MathHelper.floor_double(this.theOwner.posZ) - 2;
						int k = MathHelper.floor_double(this.theOwner.boundingBox.minY);
						
						for (int l = 0; l <= 4; ++l)
						{
							for (int i1 = 0; i1 <= 4; ++i1)
							{
								if ((l < 1 || i1 < 1 || l > 3 || i1 > 3)
										&& World.doesBlockHaveSolidTopSurface(
												this.theWorld, i + l, k - 1, j
														+ i1)
										&& !this.theWorld.getBlock(i + l, k,
												j + i1).isNormalCube()
										&& !this.theWorld.getBlock(i + l,
												k + 1, j + i1).isNormalCube())
								{
									this.thePet.setLocationAndAngles(
											(double) ((float) (i + l) + 0.5F),
											(double) k,
											(double) ((float) (j + i1) + 0.5F),
											this.thePet.rotationYaw,
											this.thePet.rotationPitch);
									this.petPathfinder.clearPathEntity();
									return;
								}
							}
						}
					}
				}
			}
		}
	}
}
