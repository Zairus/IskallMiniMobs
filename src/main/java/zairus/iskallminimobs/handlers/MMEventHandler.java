package zairus.iskallminimobs.handlers;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.terraingen.BiomeEvent.GetVillageBlockID;
import zairus.iskallminimobs.entity.minimob.EntityMiniMobBase;
import zairus.iskallminimobs.item.MMItems;
import zairus.iskallminimobs.stats.MMAchievementList;

public class MMEventHandler
{
	@SubscribeEvent
	public void onGetVillageBlockID(GetVillageBlockID event)
	{
		;
	}
	
	@SubscribeEvent
	public void onItemCrafted(ItemCraftedEvent ev)
	{
		ItemStack crafted = ev.crafting;
		EntityPlayer player = ev.player;
		
		if (crafted.getItem() == MMItems.mm_pellet)
			player.triggerAchievement(MMAchievementList.inYourPocket);
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
				{
					EntitySkeleton entity = (EntitySkeleton)ev.entity;
					entity.targetTasks.addTask(2, new EntityAINearestAttackableTarget(entity, EntityMiniMobBase.class, 5, false));
					return;
				}
			}
			
			EntityMob entity = (EntityMob)ev.entity;
			entity.tasks.addTask(3, new EntityAIAttackOnCollide(entity, EntityMiniMobBase.class, 0.8F, false));
			entity.targetTasks.addTask(2, new EntityAINearestAttackableTarget(entity, EntityMiniMobBase.class, 5, false));
		}
	}
}
