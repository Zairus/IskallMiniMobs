package zairus.iskallminimobs.proxy;

import zairus.iskallminimobs.block.MMBlocks;
import zairus.iskallminimobs.client.render.entity.RenderMMPellet;
import zairus.iskallminimobs.client.render.entity.RenderMiniMobCreeper;
import zairus.iskallminimobs.client.render.entity.RenderMiniMobPig;
import zairus.iskallminimobs.client.render.entity.RenderMiniMobSkeleton;
import zairus.iskallminimobs.client.render.entity.RenderMiniMobSpider;
import zairus.iskallminimobs.client.render.entity.RenderMiniMobZombie;
import zairus.iskallminimobs.entity.minimob.EntityMiniMobCreeper;
import zairus.iskallminimobs.entity.minimob.EntityMiniMobPig;
import zairus.iskallminimobs.entity.minimob.EntityMiniMobSkeleton;
import zairus.iskallminimobs.entity.minimob.EntityMiniMobSpider;
import zairus.iskallminimobs.entity.minimob.EntityMiniMobZombie;
import zairus.iskallminimobs.entity.projectile.EntityMMPellet;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy
	extends CommonProxy
{
	@Override
	public void preInit(FMLPreInitializationEvent e)
	{
		super.preInit(e);
		MMBlocks.initSpecialRenderers();
	}
	
	@Override
	public void init(FMLInitializationEvent e)
	{
		super.init(e);
		
		RenderingRegistry.registerEntityRenderingHandler(EntityMiniMobPig.class, new RenderMiniMobPig());
		RenderingRegistry.registerEntityRenderingHandler(EntityMiniMobZombie.class, new RenderMiniMobZombie());
		RenderingRegistry.registerEntityRenderingHandler(EntityMiniMobSkeleton.class, new RenderMiniMobSkeleton());
		RenderingRegistry.registerEntityRenderingHandler(EntityMiniMobCreeper.class, new RenderMiniMobCreeper());
		RenderingRegistry.registerEntityRenderingHandler(EntityMiniMobSpider.class, new RenderMiniMobSpider());
		
		RenderingRegistry.registerEntityRenderingHandler(EntityMMPellet.class, new RenderMMPellet());
	}
	
	@Override
	public void postInit(FMLPostInitializationEvent e)
	{
		super.postInit(e);
	}
}
