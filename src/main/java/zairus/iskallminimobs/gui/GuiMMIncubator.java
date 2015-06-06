package zairus.iskallminimobs.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import zairus.iskallminimobs.MMConstants;
import zairus.iskallminimobs.inventory.ContainerMMIncubator;
import zairus.iskallminimobs.tileentity.TileEntityMMIncubator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiMMIncubator
	extends GuiContainer
{
	private static final ResourceLocation guiMMIncubatorTextures = new ResourceLocation(MMConstants.MODID, "textures/gui/gui_incubator.png");
	private static final ResourceLocation guiMMIncubatorTexturesPower = new ResourceLocation(MMConstants.MODID, "textures/gui/gui_power_large.png");
	
	private TileEntityMMIncubator inv;
	
	public GuiMMIncubator(InventoryPlayer playerInv, TileEntityMMIncubator incubatorInv, World world)
	{
		super(new ContainerMMIncubator(playerInv, incubatorInv, world));
		
		this.xSize = 256;
		this.ySize = 240;
		
		this.inv = incubatorInv;
	}
	
	public void updateScreen()
	{
		super.updateScreen();
	}
	
	public void initGui()
	{
		super.initGui();
		
		this.buttonList.clear();
		Keyboard.enableRepeatEvents(true);
		
		this.updateButtons();
	}
	
	public void onGuiClosed()
	{
		Keyboard.enableRepeatEvents(false);
	}
	
	private void updateButtons()
	{
		;
	}
	
	protected void actionPerformed(GuiButton button)
	{
		;
	}
	
	protected void keyTyped(char keyChar, int p_73869_2_)
	{
		super.keyTyped(keyChar, p_73869_2_);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(guiMMIncubatorTextures);
		
		int left = (this.width - this.xSize) / 2;
		int top = (this.height - this.ySize) / 2;
		
		this.drawTexturedModalRect(left, top, 0, 0, this.xSize, this.ySize);
		
		String s = "Incubator";
		this.fontRendererObj.drawString(s, left + 10, top + 16, 0);
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(guiMMIncubatorTexturesPower);
		
		int powerXPos = 200;
		int powerYPos = 16;
		
		this.drawTexturedModalRect(left + powerXPos, top + powerYPos, 0, 0, 14, 128);
		
		int rf = inv.getEnergyStored(ForgeDirection.UNKNOWN);
		
		int powerBarSize = 126 - (int)(((float)rf / 5000.0F) * 125.0F);
		
		this.drawTexturedModalRect(left + powerXPos + 1, top + powerYPos + 1 + powerBarSize, 17, 1 + powerBarSize, 12, 126 - powerBarSize);
		
		s = "RF:";
		this.fontRendererObj.drawString(s, left + powerXPos + 14 + 3, top + 16, 0);
		
		s = "" + rf;
		this.fontRendererObj.drawString(s, left + powerXPos + 14 + 3, top + 16 + 8, 0);
	}
	
	public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_)
	{
		super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
	}
}
