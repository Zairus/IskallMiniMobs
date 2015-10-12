package zairus.iskallminimobs.handlers;

import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.terraingen.BiomeEvent.GetVillageBlockID;
import zairus.iskallminimobs.entity.minimob.EntityMiniMobBase;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class MMEventHandler
{
	@SubscribeEvent
	public void onGetVillageBlockID(GetVillageBlockID event)
	{
		;
	}
	
	@SubscribeEvent
	public void onEntitySpawn(EntityJoinWorldEvent ev)
	{
		if (
				ev.entity instanceof EntityMob 
				&& !(
						ev.entity instanceof EntityCreeper 
						|| ev.entity instanceof EntityGhast))
		{
			if (ev.entity instanceof EntitySkeleton)
			{
				if (((EntitySkeleton)ev.entity).getSkeletonType() == 0)
					return;
			}
			
			EntityMob entity = (EntityMob)ev.entity;
			entity.tasks.addTask(3, new EntityAIAttackOnCollide(entity, EntityMiniMobBase.class, 0.8F, true));
			entity.targetTasks.addTask(2, new EntityAINearestAttackableTarget(entity, EntityMiniMobBase.class, 0, true));
		}
	}
}
