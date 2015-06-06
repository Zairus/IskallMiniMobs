package zairus.iskallminimobs.block;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import zairus.iskallminimobs.client.render.RenderMMIncubator;
import zairus.iskallminimobs.tileentity.TileEntityMMCreativeCell;
import zairus.iskallminimobs.tileentity.TileEntityMMIncubator;
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
	
	public static final void init()
	{
		registerBlock(mm_incubator = new MMIncubator("mm_incubator"), new RenderMMIncubator(), TileEntityMMIncubator.class, "tileEntityMMIncubator");
		registerBlock(mm_creativecell = new MMCreativeCell("mm_creativecell"), TileEntityMMCreativeCell.class, "tileEntityMMCreativeCell");
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
		doRegisterBlock(block);
		
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
		
		ClientRegistry.registerTileEntity(tileEntityClass, tileEntityId, specialRenderer);
		ClientRegistry.bindTileEntitySpecialRenderer(tileEntityClass, specialRenderer);
		
		RenderingRegistry.registerBlockHandler((ISimpleBlockRenderingHandler)specialRenderer);
		RenderingRegistry.registerBlockHandler(((Block)specialRenderedBlock).getRenderType(), (ISimpleBlockRenderingHandler)specialRenderer);
	}
}
