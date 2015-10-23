package zairus.iskallminimobs.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import zairus.iskallminimobs.MMConstants;
import zairus.iskallminimobs.inventory.ContainerMMBase;
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
	private int currentScreen = 0;
	
	private GuiMMManual manual;
	private GuiMMManual.GuiMMButton buttonManual;
	private GuiMMManual.GuiMMButton buttonManualExit;
	private GuiMMManual.GuiMMButton buttonPrev;
	private GuiMMManual.GuiMMButton buttonNext;
	
	public GuiMMIncubator(InventoryPlayer playerInv, TileEntityMMIncubator incubatorInv, World world)
	{
		super(new ContainerMMIncubator(playerInv, incubatorInv, world));
		
		this.xSize = 256;
		this.ySize = 240;
		
		this.inv = incubatorInv;
		
		manual = new GuiMMManual(Minecraft.getMinecraft().fontRenderer);
	}
	
	public void updateScreen()
	{
		super.updateScreen();
	}
	
	@SuppressWarnings("unchecked")
	public void initGui()
	{
		super.initGui();
		
		manual.width = this.width;
		manual.height = this.height;
		manual.xSize = 176;
		manual.ySize = 174;
		
		int left = (this.width - this.xSize) / 2;
		int top = (this.height - this.ySize) / 2;
		
		this.buttonList.clear();
		Keyboard.enableRepeatEvents(true);
		
		this.buttonList.add(this.buttonManual = new GuiMMManual.GuiMMButton(0, left - 9, top + 10));
		this.buttonList.add(this.buttonManualExit = new GuiMMManual.GuiMMButton(1, left - 9, top + 10 + 17));
		this.buttonList.add(this.buttonPrev = new GuiMMManual.GuiMMButton(2, left + 130, top + 155));
		this.buttonList.add(this.buttonNext = new GuiMMManual.GuiMMButton(3, left + 150, top + 155));
		
		this.updateButtons();
	}
	
	public void onGuiClosed()
	{
		Keyboard.enableRepeatEvents(false);
	}
	
	private void updateButtons()
	{
		this.buttonManual.visible = (this.currentScreen == 0);
		this.buttonManual.enabled = (this.currentScreen == 0);
		this.buttonManualExit.visible = (this.currentScreen == 1);
		this.buttonManualExit.enabled = (this.currentScreen == 1);
		this.buttonManualExit.offset = 1;
		this.buttonPrev.visible = (this.currentScreen == 1 && manual.currentManualPage > 0);
		this.buttonPrev.enabled = (this.currentScreen == 1 && manual.currentManualPage > 0);
		this.buttonPrev.offset = 2;
		this.buttonNext.visible = (this.currentScreen == 1 && manual.currentManualPage < manual.totalManualPages);
		this.buttonNext.enabled = (this.currentScreen == 1 && manual.currentManualPage < manual.totalManualPages);
		this.buttonNext.offset = 3;
	}
	
	protected void actionPerformed(GuiButton button)
	{
		if (button.enabled)
		{
			switch(button.id)
			{
			case 1: // Hide the manual
				this.currentScreen = 0;
				((ContainerMMBase)this.inventorySlots).moveSlots(false, 800);
				break;
			case 2:
				manual.pagePrev();
				break;
			case 3:
				manual.pageNext();
				break;
			default: // 0 - Show the manual
				this.currentScreen = 1;
				((ContainerMMBase)this.inventorySlots).moveSlots(true, 800);
				break;
			}
			this.updateButtons();
		}
	}
	
	protected void keyTyped(char keyChar, int p_73869_2_)
	{
		super.keyTyped(keyChar, p_73869_2_);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
	{
		switch (this.currentScreen)
		{
		case 1:
			this.renderUserManual();
			break;
		default: // 0
			this.renderWorkGUI();
			break;
		}
	}
	
	public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_)
	{
		super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
	}
	
	private void renderWorkGUI()
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
	
	private void renderUserManual()
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(GuiMMManual.guiTexturesManual);
		
		int left = (this.width - manual.xSize) / 2;
		int top = (this.height - manual.ySize) / 2;
		
		this.drawTexturedModalRect(left, top, 0, 0, manual.xSize, manual.ySize);
		
		this.buttonManualExit.xPosition = left - 13;
		this.buttonManualExit.yPosition = top + 5;
		this.buttonPrev.xPosition = left + 130;
		this.buttonPrev.yPosition = top + 155;
		this.buttonNext.xPosition = left + 150;
		this.buttonNext.yPosition = top + 155;
		
		String s = "";
		manual.totalManualPages = 5;
		
		s = "MM Incubator User's Guide";
		this.fontRendererObj.drawString(s, left + 5, top + 5, 0);
		
		switch (manual.currentManualPage)
		{
		case 1:
			manual.addLine("... in a chest.", 2, 0);
			manual.addLine("Use this machine to incubate", 3, 0);
			manual.addLine("these embryos. You need to", 4, 0);
			manual.addLine("supply power and have ready", 5, 0);
			manual.addLine("some pellets to get your friend", 6, 0);
			manual.addLine("in it.", 7, 0);
			manual.addLine("", 8, 0);
			manual.addLine("Mini mobs are different types.", 9, 0);
			break;
		case 2:
			manual.addLine("- Pig", 2, 0);
			manual.addLine("- Zombie", 3, 0);
			manual.addLine("- Skeleton", 4, 0);
			manual.addLine("- Creeper", 5, 0);
			manual.addLine("- Spider", 6, 0);
			manual.addLine("- Soldier", 7, 0);
			manual.addLine("", 8, 0);
			manual.addLine("Soldiers are special.", 9, 0);
			break;
		case 3:
			manual.addLine("In order to incubate a soldier,", 2, 0);
			manual.addLine("you must first give a name to", 3, 0);
			manual.addLine("the embryo (A naming station", 4, 0);
			manual.addLine("would be useful). Try using", 5, 0);
			manual.addLine("real player names!", 6, 0);
			manual.addLine("", 7, 0);
			manual.addLine("Once the gestation process is", 8, 0);
			manual.addLine("complete, you'll get a pellet ...", 9, 0);
			break;
		case 4:
			manual.addLine("... with the mini mob inside.", 2, 0);
			manual.addLine("", 3, 0);
			manual.addLine("Throw this pellet to the ground", 4, 0);
			manual.addLine("near you, and your friend will", 5, 0);
			manual.addLine("spawn.", 6, 0);
			manual.addLine("", 7, 0);
			manual.addLine("Sneak right click the mini mob", 8, 0);
			manual.addLine("to bring up it's interface.", 9, 0);
			break;
		case 5:
			manual.addLine("Right click the mini mob with", 2, 0);
			manual.addLine("an empty pellet to collect it.", 3, 0);
			manual.addLine("", 4, 0);
			manual.addLine("You can give armor and weapon", 5, 0);
			manual.addLine("to zombies, skeleton and", 6, 0);
			manual.addLine("soldiers.", 7, 0);
			manual.addLine("", 8, 0);
			manual.addLine("", 9, 0);
			break;
		default:
			manual.addLine("Congratulations!", 2, 0);
			manual.addLine("You are getting into the world", 3, 0);
			manual.addLine("of mini mobs.", 4, 0);
			manual.addLine("They are little friends that", 5, 0);
			manual.addLine("will help you fight enemies.", 6, 0);
			manual.addLine("", 7, 0);
			manual.addLine("At some point you will find", 8, 0);
			manual.addLine("a rare embryo somewhere ...", 9, 0);
			break;
		}
		
		s = "Page: " + (manual.currentManualPage + 1);
		this.fontRendererObj.drawString(s, left + 129, top + 144, 0);
	}
}
