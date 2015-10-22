package zairus.iskallminimobs.client.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import zairus.iskallminimobs.MMConstants;
import zairus.iskallminimobs.block.MMBlockContainer;
import zairus.iskallminimobs.block.MMBlocks;
import zairus.iskallminimobs.block.MMIncubator;
import zairus.iskallminimobs.model.ModelMMIncubator;
import zairus.iskallminimobs.tileentity.TileEntityMMBase;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderMMIncubator
	extends TileEntitySpecialRenderer
	implements ISimpleBlockRenderingHandler
{
	ResourceLocation[] textures = {
			new ResourceLocation(MMConstants.MODID, "textures/models/mm_incubator.png")
			,new ResourceLocation(MMConstants.MODID, "textures/models/mm_dnaextractor.png")};
	
	private ModelMMIncubator model;
	
	public RenderMMIncubator()
	{
		this.model = new ModelMMIncubator();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float f)
	{
		renderIncubator(x, y, z, 0.5F, 1.5F, 0.5F, entity, ((TileEntityMMBase)entity).getTextureIndex());
	}
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
	{
		renderIncubator(0.0D, 0.0D, 0.0D, 0.0F, 1.0F, 0.0F, null, ((MMBlockContainer)block).getTextureIndex());
	}
	
	private void renderIncubator(double x, double y, double z, float xIncrement, float yIncrement, float zIncrement, TileEntity entity, int textureIndex)
	{
		GL11.glPushMatrix();
		GL11.glTranslatef((float)x + xIncrement, (float)y + yIncrement, (float)z + zIncrement);
		
		GL11.glRotatef(180, 0.0F, 0.0F, 1.0F);
		
		if (entity != null)
		{
			int renderFacing = entity.getWorldObj().getBlockMetadata(entity.xCoord, entity.yCoord, entity.zCoord);
			
			switch(renderFacing)
			{
			case 3:
				GL11.glRotatef(180.0F, 1.0F, 800.0F, 1.0F);
				break;
			case 4:
				GL11.glRotatef(270.0F, 1.0F, 800.0F, 1.0F);
				break;
			case 5:
				GL11.glRotatef(90.0F, 0.0F, 10.0F, 0.0F);
				break;
			}
		}
		else
		{
			GL11.glRotatef(180.0F, 1.0F, 800.0F, 1.0F);
		}
		
		this.bindTexture(textures[textureIndex]);
		
		GL11.glPushMatrix();
		this.model.renderModel(0.0625F);
		GL11.glPopMatrix();
		
		GL11.glPopMatrix();
	}
	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		return false;
	}
	
	@Override
	public boolean shouldRender3DInInventory(int modelId)
	{
		return true;
	}
	
	@Override
	public int getRenderId()
	{
		return ((MMIncubator)MMBlocks.mm_incubator).getRenderType();
	}
	
}
