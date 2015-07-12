package zairus.iskallminimobs.proxy;

import zairus.iskallminimobs.IskallMiniMobs;
import zairus.iskallminimobs.block.MMBlocks;
import zairus.iskallminimobs.entity.MMEntityRegistry;
import zairus.iskallminimobs.entity.minimob.EntityMiniMobCreeper;
import zairus.iskallminimobs.entity.minimob.EntityMiniMobPig;
import zairus.iskallminimobs.entity.minimob.EntityMiniMobSkeleton;
import zairus.iskallminimobs.entity.minimob.EntityMiniMobSpider;
import zairus.iskallminimobs.entity.minimob.EntityMiniMobZombie;
import zairus.iskallminimobs.entity.projectile.EntityMMPellet;
import zairus.iskallminimobs.item.MMItems;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy
{
	public void preInit(FMLPreInitializationEvent e)
	{
		MMItems.init();
		MMBlocks.init();
	}
	
	public void init(FMLInitializationEvent e)
	{
		MMItems.register();
		MMItems.addLoot();
		
		MMEntityRegistry.registerEntity(EntityMiniMobPig.class, "entity_minimob_pig", IskallMiniMobs.instance, 64, 1, true, 0xeee8da, 0x8c8168);
		MMEntityRegistry.registerEntity(EntityMiniMobZombie.class, "entity_minimob_zombie", IskallMiniMobs.instance, 64, 1, true, 0x0e4d1e, 0xa93232); 
		MMEntityRegistry.registerEntity(EntityMiniMobSkeleton.class, "entity_minimob_skeleton", IskallMiniMobs.instance, 64, 1, true, 0xffffff, 0xcccccc); 
		MMEntityRegistry.registerEntity(EntityMiniMobCreeper.class, "entity_minimob_creeper", IskallMiniMobs.instance, 64, 1, true, 0x4fc500, 0x7e8b75); 
		MMEntityRegistry.registerEntity(EntityMiniMobSpider.class, "entity_minimob_spider", IskallMiniMobs.instance, 64, 1, true, 0x21271d, 0xb70000); 
		
		MMEntityRegistry.registerEntity(EntityMMPellet.class, "entity_mm_pellet", IskallMiniMobs.instance, 64, 1, true);
	}
	
	public void postInit(FMLPostInitializationEvent e)
	{
		;
	}
}
