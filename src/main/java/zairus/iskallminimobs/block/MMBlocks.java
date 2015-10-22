package zairus.iskallminimobs.block;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import zairus.iskallminimobs.client.render.RenderMMIncubator;
import zairus.iskallminimobs.client.render.RenderMMNamingStation;
import zairus.iskallminimobs.client.render.RenderMMPowerProvider;
import zairus.iskallminimobs.tileentity.TileEntityMMCreativeCell;
import zairus.iskallminimobs.tileentity.TileEntityMMDNAExtractor;
import zairus.iskallminimobs.tileentity.TileEntityMMIncubator;
import zairus.iskallminimobs.tileentity.TileEntityMMNamingStation;
import zairus.iskallminimobs.tileentity.TileEntityMMPowerProvider;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MMBlocks
{
	public static Block mm_incubator;
	public static Block mm_creativecell;
	public static Block mm_dnaextractor;
	public static Block mm_namingstation;
	public static Block mm_powerprovider;
	
	public static final void init()
	{
		mm_incubator = new MMIncubator("mm_incubator");
		mm_creativecell = new MMCreativeCell("mm_creativecell");
		mm_dnaextractor = new MMDNAExtractor("mm_dnaextractor");
		mm_namingstation = new MMNamingStation("mm_namingstation");
		mm_powerprovider = new MMPowerProvider("mm_powerprovider");
		
		registerBlock(mm_incubator, TileEntityMMIncubator.class, "tileEntityMMIncubator");
		registerBlock(mm_creativecell, TileEntityMMCreativeCell.class, "tileEntityMMCreativeCell");
		registerBlock(mm_dnaextractor, TileEntityMMDNAExtractor.class, "tileEntityMMDNAExtractor");
		registerBlock(mm_namingstation, TileEntityMMNamingStation.class, "tileEntityMMNamingStation");
		registerBlock(mm_powerprovider, TileEntityMMPowerProvider.class, "tileEntityMMPowerProvider");
	}
	
	@SideOnly(Side.CLIENT)
	public static final void initSpecialRenderers()
	{
		registerBlock(mm_incubator, new RenderMMIncubator(), TileEntityMMIncubator.class, "tileEntityMMIncubator");
		registerBlock(mm_dnaextractor, new RenderMMIncubator(), TileEntityMMDNAExtractor.class, "tileEntityMMDNAExtractor");
		registerBlock(mm_namingstation, new RenderMMNamingStation(), TileEntityMMNamingStation.class, "tileEntityMMNamingStation");
		registerBlock(mm_powerprovider, new RenderMMPowerProvider(), TileEntityMMPowerProvider.class, "tileEntityMMPowerProvider");
	}
	
	@SuppressWarnings("unused")
	private static void registerBlock(Block block)
	{
		doRegisterBlock(block);
	}
	
	private static void registerBlock(Block block, Class<? extends TileEntity> tileEntityClass, String tileEntityId)
	{
		doRegisterBlock(block);
		GameRegistry.registerTileEntity(tileEntityClass, tileEntityId);
	}
	
	private static void registerBlock(Block block, TileEntitySpecialRenderer specialRenderer, Class<? extends TileEntity> tileEntityClass, String tileEntityId)
	{
		if (block instanceof IMMSpecialRenderedBlock)
		{
			registerBlockTileEntityWithRenderer(specialRenderer, (IMMSpecialRenderedBlock)block, tileEntityClass, tileEntityId);
		}
	}
	
	private static void doRegisterBlock(Block block)
	{
		GameRegistry.registerBlock(block, block.getUnlocalizedName().replaceAll("tile.", ""));
	}
	
	@SideOnly(Side.CLIENT)
	private static void registerBlockTileEntityWithRenderer(TileEntitySpecialRenderer specialRenderer, IMMSpecialRenderedBlock specialRenderedBlock, Class<? extends TileEntity> tileEntityClass, String tileEntityId)
	{
		specialRenderedBlock.setRenderId(RenderingRegistry.getNextAvailableRenderId());
		
		ClientRegistry.bindTileEntitySpecialRenderer(tileEntityClass, specialRenderer);
		
		RenderingRegistry.registerBlockHandler((ISimpleBlockRenderingHandler)specialRenderer);
		RenderingRegistry.registerBlockHandler(((Block)specialRenderedBlock).getRenderType(), (ISimpleBlockRenderingHandler)specialRenderer);
	}
}
