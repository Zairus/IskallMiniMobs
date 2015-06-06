package zairus.iskallminimobs.proxy;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import zairus.iskallminimobs.IskallMiniMobs;
import zairus.iskallminimobs.block.MMBlocks;
import zairus.iskallminimobs.entity.minimob.EntityMiniMobCreeper;
import zairus.iskallminimobs.entity.minimob.EntityMiniMobPig;
import zairus.iskallminimobs.entity.minimob.EntityMiniMobSkeleton;
import zairus.iskallminimobs.entity.minimob.EntityMiniMobSpider;
import zairus.iskallminimobs.entity.minimob.EntityMiniMobZombie;
import zairus.iskallminimobs.item.MMItems;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;

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
		
		this.registerEntity(EntityMiniMobPig.class, "entity_minimob_pig", 0xeee8da, 0x8c8168);
		this.registerEntity(EntityMiniMobZombie.class, "entity_minimob_zombie", 0x0e4d1e, 0xa93232);
		this.registerEntity(EntityMiniMobSkeleton.class, "entity_minimob_skeleton", 0xffffff, 0xcccccc);
		this.registerEntity(EntityMiniMobCreeper.class, "entity_minimob_creeper", 0x4fc500, 0x7e8b75);
		this.registerEntity(EntityMiniMobSpider.class, "entity_minimob_spider", 0x21271d, 0xb70000);
	}
	
	public void postInit(FMLPostInitializationEvent e)
	{
		;
	}
	
	@SuppressWarnings("unchecked")
	private void registerEntity(Class <?extends Entity> clazz, String entityId, int eggBgColor, int eggFgColor)
	{
		int id = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(clazz, entityId, id);
		EntityRegistry.registerModEntity(clazz, entityId, id, IskallMiniMobs.instance, 64, 1, true);
		EntityList.IDtoClassMapping.put(id, clazz);
		EntityList.entityEggs.put(id, new EntityList.EntityEggInfo(id, eggBgColor, eggFgColor));
	}
}
