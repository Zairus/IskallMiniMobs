package zairus.iskallminimobs.client.render.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import zairus.iskallminimobs.entity.minimob.MiniMobData;
import zairus.iskallminimobs.helpers.ColorHelper;
import zairus.iskallminimobs.item.MMPellet;
import zairus.iskallminimobs.proxy.ClientProxy;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderItemMMPellet
	implements IItemRenderer
{
	private static final ResourceLocation ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");
	private static final Tessellator tessellator = Tessellator.instance;
	
	private static Minecraft mc = null;
	private static final RenderItem renderItem = new RenderItem();
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return (
				type.equals(IItemRenderer.ItemRenderType.EQUIPPED)) 
				|| (type.equals(IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON)) 
				|| (type.equals(IItemRenderer.ItemRenderType.INVENTORY)) 
				|| (type.equals(IItemRenderer.ItemRenderType.ENTITY));
	}
	
	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return (
				helper.equals(IItemRenderer.ItemRendererHelper.ENTITY_BOBBING)) 
				|| (helper.equals(IItemRenderer.ItemRendererHelper.ENTITY_ROTATION));
	}
	
	@Override
	public void renderItem(ItemRenderType type, ItemStack stack, Object... data) {	
		if (mc == null)
		{
			mc = ClientProxy.mc;
		}
		
		Item item = stack.getItem();
		int mmtype = -1;
		
		if (stack.hasTagCompound())
		{
			if (stack.getTagCompound().hasKey(MiniMobData.MOBDATA_KEY))
			{
				if (((NBTTagCompound)stack.getTagCompound().getTag(MiniMobData.MOBDATA_KEY)).hasKey(MiniMobData.MOBTYPE_KEY))
				{
					mmtype = ((NBTTagCompound)stack.getTagCompound().getTag(MiniMobData.MOBDATA_KEY)).getInteger(MiniMobData.MOBTYPE_KEY);
				}
			}
		}
		
		if (type.equals(IItemRenderer.ItemRenderType.INVENTORY))
		{
			GL11.glDisable(GL11.GL_LIGHTING);
			for (int pass = 0; pass < 2; pass++)
			{
				IIcon icon;
				int color;
				
				icon = ((MMPellet)stack.getItem()).getBaseIcon();
				
				if (icon != null)
				{
					color = item.getColorFromItemStack(stack, pass);
					ColorHelper.glSetColor(color, 1.0F);
					
					GL11.glDisable(GL11.GL_LIGHTING);
	                GL11.glEnable(GL11.GL_ALPHA_TEST);
					
		            renderItem.renderIcon(0, 0, icon, 16, 16);
					
		            GL11.glDisable(GL11.GL_ALPHA_TEST);
	                GL11.glEnable(GL11.GL_LIGHTING);
				}
				
				icon = ((MMPellet)item).getOverlay(mmtype);
				
				if (icon != null)
				{
					color = item.getColorFromItemStack(stack, pass);
					ColorHelper.glSetColor(color, 1.0F);
					
					GL11.glDisable(GL11.GL_LIGHTING);
	                GL11.glEnable(GL11.GL_ALPHA_TEST);
					
		            renderItem.renderIcon(0, 0, icon, 16, 16);
					
		            GL11.glDisable(GL11.GL_ALPHA_TEST);
	                GL11.glEnable(GL11.GL_LIGHTING);
				}
				
				if ((pass == 0) && (stack.hasEffect(pass)))
				{
					GL11.glPushMatrix();
					
					GL11.glDepthFunc(516);
					GL11.glDisable(2896);
					GL11.glDepthMask(false);
					GL11.glEnable(3042);
					GL11.glBlendFunc(774, 774);
					
					GL11.glColor4f(0.5F, 0.25F, 0.8F, 1.0F);
					mc.renderEngine.bindTexture(ITEM_GLINT);
					
					renderGlint(-2, -2, 20, 20);
					
					GL11.glDisable(3042);
					GL11.glDepthMask(true);
					
					GL11.glEnable(2896);
					GL11.glDepthFunc(515);
					
					GL11.glPopMatrix();
				}
			}
			GL11.glEnable(GL11.GL_LIGHTING);
		}
		else if (type.equals(IItemRenderer.ItemRenderType.ENTITY))
		{
			GL11.glTranslatef(-0.5F, -0.25F, 0.04F);
			
			GL11.glPushMatrix();
			for (int pass = 0; pass < 2; pass++)
			{
				int color;
				IIcon icon = ((MMPellet)stack.getItem()).getBaseIcon();
				if (icon != null)
				{
					color = item.getColorFromItemStack(stack, pass);
					ColorHelper.glSetColor(color);
					if (pass == 0)
					{
						drawItem(icon, 0.08F);
					}
					else
					{
						GL11.glTranslatef(0.0F, 0.0F, -0.01F);
						drawItem(icon, 0.06F);
					}
				}
				
				icon = ((MMPellet)item).getOverlay(mmtype);
				
				if (icon != null)
				{
					color = item.getColorFromItemStack(stack, pass);
					ColorHelper.glSetColor(color);
					if (pass == 0)
					{
						drawItem(icon, 0.08F);
					}
					else
					{
						GL11.glTranslatef(0.0F, 0.0F, -0.01F);
						drawItem(icon, 0.06F);
					}
				}
			}
			GL11.glPopMatrix();
		}
		else if ((type.equals(IItemRenderer.ItemRenderType.EQUIPPED)) || (type.equals(IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON)))
		{
			GL11.glPushMatrix();
			
			int iconOffset = 0;
			
			int size = 1;
			
			float scale = 1.0F;
			
			float px = 1.0F / (16 * size);
			
			float scaleOffsetX = 0.9F;
			float scaleOffsetY = 1.0F;
			
			boolean thirdPerson = !type.equals(IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON);
			boolean ifp = false;
			
			GL11.glPushMatrix();
			
			GL11.glTranslatef(scaleOffsetX, scaleOffsetY, 0.0F);
			GL11.glScalef(scale, scale, 1.0F);
			GL11.glTranslatef(-scaleOffsetX, -scaleOffsetY, 0.0F);
			
			IIcon icon = ((MMPellet)stack.getItem()).getBaseIcon();
			drawItem(icon, 0.09375F);
			
			icon = ((MMPellet)item).getOverlay(mmtype);
			drawItem(icon, 0.09375F);
			
			GL11.glPopMatrix();
			
			GL11.glTranslatef(scaleOffsetX, scaleOffsetY, 0.0F);
			GL11.glScalef(scale, scale, 1.0F);
			GL11.glTranslatef(-scaleOffsetX, -scaleOffsetY, 0.0F);
			
			GL11.glPushMatrix();
			if (iconOffset > 0)
			{
				if ((thirdPerson) && (ifp))
				{
					GL11.glRotatef(5.0F, 1.0F, -1.0F, 0.0F);
					GL11.glTranslatef(0.0F, 0.0F, -0.03F);
				}
				else
				{
					GL11.glRotatef(-5.0F, 1.0F, -1.0F, 0.0F);
					GL11.glTranslatef(0.0F, 0.0F, 0.03F);
				}
				float offset = -(iconOffset - 3) * px;
				GL11.glTranslatef(offset, offset, 0.0F);
			}
			GL11.glMatrixMode(5888);
			GL11.glPopMatrix();
			
			GL11.glPopMatrix();
		}
	}
	
	private void drawItem(IIcon icon, float thickness)
	{
		float xStart = icon.getMinU();
		float xEnd = icon.getMaxU();
		float yStart = icon.getMinV();
		float yEnd = icon.getMaxV();
		int height = icon.getIconHeight();
		int width = icon.getIconWidth();
		
		ItemRenderer.renderItemIn2D(tessellator, xEnd, yStart, xStart, yEnd, width, height, thickness);
	}
	
	private void renderGlint(int par2, int par3, int par4, int par5)
	{
		for (int j1 = 0; j1 < 2; j1++)
		{
			if (j1 == 0)
			{
				GL11.glBlendFunc(768, 1);
			}
			if (j1 == 1)
			{
				GL11.glBlendFunc(768, 1);
			}
			
			float f = 0.0039063F;
			float f1 = 0.0039063F;
			float f2 = (float)(Minecraft.getGLMaximumTextureSize() % (3000 + j1 * 1873)) / (3000.0F + j1 * 1873) * 256.0F;
			float f3 = 0.0F;
			
			Tessellator tessellator = Tessellator.instance;
			
			float f4 = 4.0F;
			
			if (j1 == 1)
			{
				f4 = -1.0F;
			}
			
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(par2 + 0, par3 + par5, -50.0D, (f2 + par5 * f4) * f, (f3 + par5) * f1);
			tessellator.addVertexWithUV(par2 + par4, par3 + par5, -50.0D, (f2 + par4 + par5 * f4) * f, (f3 + par5) * f1);
			tessellator.addVertexWithUV(par2 + par4, par3 + 0, -50.0D, (f2 + par4) * f, (f3 + 0.0F) * f1);
			tessellator.addVertexWithUV(par2 + 0, par3 + 0, -50.0D, (f2 + 0.0F) * f, (f3 + 0.0F) * f1);
			tessellator.draw();
		}
	}
}
