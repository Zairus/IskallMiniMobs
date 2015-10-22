package zairus.iskallminimobs.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import zairus.iskallminimobs.MMConstants;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiMMManual
{
	public static final ResourceLocation guiTexturesManual = new ResourceLocation(MMConstants.MODID, "textures/gui/gui_manual.png");
	
	public int width;
	public int height;
	public int xSize;
	public int ySize;
	public int currentManualPage = 0;
	public int totalManualPages = 3;
	
	private final FontRenderer fontRendererObj;
	
	public GuiMMManual(FontRenderer fontRenderer)
	{
		this.fontRendererObj = fontRenderer;
	}
	
	@SideOnly(Side.CLIENT)
	static class GuiMMButton
		extends GuiButton
	{
		public int offset = 0;
		public int offsetValue = 16;
		
		public GuiMMButton(int buttonId, int x, int y)
		{
			super(buttonId, x, y, 16, 13, "");
			this.width = 16;
			this.height = 17;
		}
		
		@Override
		public void drawButton(Minecraft minecraft, int mouseX, int mouseY)
		{
			if (this.visible)
			{
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				minecraft.getTextureManager().bindTexture(GuiMMManual.guiTexturesManual);
				
				int k = 0 + (offset * offsetValue);
				int l = 174;
				
				this.drawTexturedModalRect(this.xPosition, this.yPosition, k, l, this.width, this.height);
			}
		}
	}
	
	public void pagePrev()
	{
		this.currentManualPage--;
		if (this.currentManualPage < 0)
			this.currentManualPage = 0;
	}
	
	public void pageNext()
	{
		this.currentManualPage++;
		if (this.currentManualPage > this.totalManualPages)
			this.currentManualPage = this.totalManualPages;
	}
	
	public void addLine(String text, int lineNumber, int color)
	{
		int lineHeight = 13;
		
		int left = (this.width - this.xSize) / 2;
		int top = (this.height - this.ySize) / 2;
		
		this.fontRendererObj.drawString(text, left + 10, top + (lineHeight * lineNumber), color);
	}
}
