package zairus.iskallminimobs;

import java.util.logging.Logger;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import zairus.iskallminimobs.handlers.MMCraftingHandler;
import zairus.iskallminimobs.handlers.MMEventHandler;
import zairus.iskallminimobs.handlers.MMGuiHandler;
import zairus.iskallminimobs.item.MMItems;
import zairus.iskallminimobs.proxy.CommonProxy;
import zairus.iskallminimobs.util.network.PacketPipeline;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = MMConstants.MODID, name = MMConstants.MODNAME, version = MMConstants.VERSION)
public class IskallMiniMobs
{
	public static Logger logger;
    public static Configuration configuration;
    public static final PacketPipeline packetPipeline = new PacketPipeline();
    
	@SidedProxy(clientSide="zairus.iskallminimobs.proxy.ClientProxy", serverSide="zairus.iskallminimobs.proxy.ServerProxy")
	public static CommonProxy proxy;
	
	@Mod.Instance(MMConstants.MODID)
	public static IskallMiniMobs instance;
	
	public static CreativeTabs tabIskallMinimobs = new CreativeTabs("tabIskallMinimobs") {
		@Override
		public Item getTabIconItem() {
			return MMItems.mm_embrio;
		}
	};
    
    @Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
    	IskallMiniMobs.proxy.preInit(event);
    	
    	configuration = new Configuration(event.getSuggestedConfigurationFile());
    	
    	configuration.load();
    	
    	configuration.save();
	}
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	MMEventHandler eventHandler = new MMEventHandler();
    	
    	IskallMiniMobs.proxy.init(event);
    	packetPipeline.initalise();
    	
    	MMCraftingHandler.addCraftingRecipes();
    	
    	FMLCommonHandler.instance().bus().register(eventHandler);
    	MinecraftForge.EVENT_BUS.register(eventHandler);
    	MinecraftForge.TERRAIN_GEN_BUS.register(eventHandler);
    	
    	if (IskallMiniMobs.instance == null)
    		IskallMiniMobs.log("am I null?");
    	
    	NetworkRegistry.INSTANCE.registerGuiHandler(IskallMiniMobs.instance, new MMGuiHandler());
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    	IskallMiniMobs.proxy.postInit(event);
    	packetPipeline.postInitialise();
    }
    
    public static void log(String obj)
	{
		if (logger == null) {
			logger = Logger.getLogger("IskallMiniMobs");
		}
		if (obj == null) {
			obj = "null";
		}
		logger.info("[" + FMLCommonHandler.instance().getEffectiveSide() + "] " + obj);
	}
}
