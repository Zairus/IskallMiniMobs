package zairus.iskallminimobs.proxy;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.MinecraftForgeClient;
import zairus.iskallminimobs.block.MMBlocks;
import zairus.iskallminimobs.client.render.entity.RenderMMPellet;
import zairus.iskallminimobs.client.render.entity.RenderMiniMobCreeper;
import zairus.iskallminimobs.client.render.entity.RenderMiniMobPig;
import zairus.iskallminimobs.client.render.entity.RenderMiniMobSkeleton;
import zairus.iskallminimobs.client.render.entity.RenderMiniMobSoldier;
import zairus.iskallminimobs.client.render.entity.RenderMiniMobSpider;
import zairus.iskallminimobs.client.render.entity.RenderMiniMobZombie;
import zairus.iskallminimobs.client.render.item.RenderItemMMPellet;
import zairus.iskallminimobs.entity.minimob.EntityMiniMobCreeper;
import zairus.iskallminimobs.entity.minimob.EntityMiniMobPig;
import zairus.iskallminimobs.entity.minimob.EntityMiniMobSkeleton;
import zairus.iskallminimobs.entity.minimob.EntityMiniMobSoldier;
import zairus.iskallminimobs.entity.minimob.EntityMiniMobSpider;
import zairus.iskallminimobs.entity.minimob.EntityMiniMobZombie;
import zairus.iskallminimobs.entity.projectile.EntityMMPellet;
import zairus.iskallminimobs.item.MMItems;

public class ClientProxy
	extends CommonProxy
{
	public static Minecraft mc = Minecraft.getMinecraft();
	
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
		RenderingRegistry.registerEntityRenderingHandler(EntityMiniMobSoldier.class, new RenderMiniMobSoldier());
		
		RenderingRegistry.registerEntityRenderingHandler(EntityMMPellet.class, new RenderMMPellet());
		
		MinecraftForgeClient.registerItemRenderer(MMItems.mm_pellet, new RenderItemMMPellet());
	}
	
	@Override
	public void postInit(FMLPostInitializationEvent e)
	{
		super.postInit(e);
	}
}
