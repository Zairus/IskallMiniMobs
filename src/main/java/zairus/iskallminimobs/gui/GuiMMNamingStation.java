package zairus.iskallminimobs.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import zairus.iskallminimobs.IskallMiniMobs;
import zairus.iskallminimobs.MMConstants;
import zairus.iskallminimobs.inventory.ContainerMMBase;
import zairus.iskallminimobs.inventory.ContainerMMNamingStation;
import zairus.iskallminimobs.tileentity.TileEntityMMNamingStation;
import zairus.iskallminimobs.util.network.MMNamingStationPacket;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiMMNamingStation
	extends GuiContainer
{
	private static final ResourceLocation guiTextures = new ResourceLocation(MMConstants.MODID, "textures/gui/gui_namingstation.png");
	
	private TileEntityMMNamingStation inventory;
	private int currentScreen = 0;
	private int color = 16;
	private boolean obfuscated = false;
	private boolean bold = false;
	private boolean strike = false;
	private boolean underline = false;
	private boolean italic = false;
	private boolean reset = false;
	private boolean textFieldActive = false;
	private char[] colors = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
	
	private GuiMMManual manual;
	private GuiMMManual.GuiMMButton buttonManual;
	private GuiMMManual.GuiMMButton buttonManualExit;
	private GuiMMManual.GuiMMButton buttonPrev;
	private GuiMMManual.GuiMMButton buttonNext;
	
	private GuiMMNamingStation.GuiColorSelectorButton buttonC0;
	private GuiMMNamingStation.GuiColorSelectorButton buttonC1;
	private GuiMMNamingStation.GuiColorSelectorButton buttonC2;
	private GuiMMNamingStation.GuiColorSelectorButton buttonC3;
	private GuiMMNamingStation.GuiColorSelectorButton buttonC4;
	private GuiMMNamingStation.GuiColorSelectorButton buttonC5;
	private GuiMMNamingStation.GuiColorSelectorButton buttonC6;
	private GuiMMNamingStation.GuiColorSelectorButton buttonC7;
	private GuiMMNamingStation.GuiColorSelectorButton buttonC8;
	private GuiMMNamingStation.GuiColorSelectorButton buttonC9;
	private GuiMMNamingStation.GuiColorSelectorButton buttonCa;
	private GuiMMNamingStation.GuiColorSelectorButton buttonCb;
	private GuiMMNamingStation.GuiColorSelectorButton buttonCc;
	private GuiMMNamingStation.GuiColorSelectorButton buttonCd;
	private GuiMMNamingStation.GuiColorSelectorButton buttonCe;
	private GuiMMNamingStation.GuiColorSelectorButton buttonCf;
	
	private GuiMMNamingStation.GuiColorSelectorButton buttonF0;
	private GuiMMNamingStation.GuiColorSelectorButton buttonF1;
	private GuiMMNamingStation.GuiColorSelectorButton buttonF2;
	private GuiMMNamingStation.GuiColorSelectorButton buttonF3;
	private GuiMMNamingStation.GuiColorSelectorButton buttonF4;
	private GuiMMNamingStation.GuiColorSelectorButton buttonF5;
	
	private GuiTextField nameField;
	
	public GuiMMNamingStation(InventoryPlayer playerInv, TileEntityMMNamingStation inv, World world)
	{
		super(new ContainerMMNamingStation(playerInv, inv, world));
		
		this.inventory = inv;
		this.xSize = 176;
		this.ySize = 174;
		
		manual = new GuiMMManual(Minecraft.getMinecraft().fontRenderer);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initGui()
	{
		super.initGui();
		
		manual.width = this.width;
		manual.height = this.height;
		manual.xSize = this.xSize;
		manual.ySize = this.ySize;
		
		int left = (this.width - this.xSize) / 2;
		int top = (this.height - this.ySize) / 2;
		
		nameField = new GuiTextField(this.fontRendererObj, left + 7, top + 11, 161, 14);
		nameField.setCanLoseFocus(false);
		nameField.setFocused(true);
		nameField.setEnableBackgroundDrawing(false);
		nameField.setMaxStringLength(100);
		
		if (this.inventory.getItemName() != "")
			nameField.setText(this.inventory.getItemName());
		
		//nameField.setText("§0c§1c§2c§3c§4c§5c§6c§7c§8c§9c§ac§bc§cc§dc§ec§fc-§r-§kobf§r-§lbold§r-§mstrike§r-§nunder§r-§oitalic§r");
		
		//http://minecraft.gamepedia.com/Formatting_codes
		//§0c§1c§2c§3c§4c§5c§6c§7c§8c§9c§ac§bc§cc§dc§ec§fc
		//§k ob
		//§l bold
		//§m stri
		//§n under
		//§o ital
		//§r reset
		
		this.buttonList.clear();
		Keyboard.enableRepeatEvents(true);
		
		this.buttonList.add(this.buttonManual = new GuiMMManual.GuiMMButton(0, left - 13, top + 5));
		this.buttonList.add(this.buttonManualExit = new GuiMMManual.GuiMMButton(1, left - 13, top + 5 + 17));
		this.buttonList.add(this.buttonPrev = new GuiMMManual.GuiMMButton(2, left + 130, top + 155));
		this.buttonList.add(this.buttonNext = new GuiMMManual.GuiMMButton(3, left + 150, top + 155));
		
		int palletButtonLeft = 40;
		
		this.buttonList.add(this.buttonC0 = new GuiMMNamingStation.GuiColorSelectorButton(4, left + (palletButtonLeft + (6 * 0)), top + 35));
		this.buttonList.add(this.buttonC1 = new GuiMMNamingStation.GuiColorSelectorButton(5, left + (palletButtonLeft + (6 * 1)), top + 35));
		this.buttonList.add(this.buttonC2 = new GuiMMNamingStation.GuiColorSelectorButton(6, left + (palletButtonLeft + (6 * 2)), top + 35));
		this.buttonList.add(this.buttonC3 = new GuiMMNamingStation.GuiColorSelectorButton(7, left + (palletButtonLeft + (6 * 3)), top + 35));
		this.buttonList.add(this.buttonC4 = new GuiMMNamingStation.GuiColorSelectorButton(8, left + (palletButtonLeft + (6 * 4)), top + 35));
		this.buttonList.add(this.buttonC5 = new GuiMMNamingStation.GuiColorSelectorButton(9, left + (palletButtonLeft + (6 * 5)), top + 35));
		this.buttonList.add(this.buttonC6 = new GuiMMNamingStation.GuiColorSelectorButton(10, left + (palletButtonLeft + (6 * 6)), top + 35));
		this.buttonList.add(this.buttonC7 = new GuiMMNamingStation.GuiColorSelectorButton(11, left + (palletButtonLeft + (6 * 7)), top + 35));
		this.buttonList.add(this.buttonC8 = new GuiMMNamingStation.GuiColorSelectorButton(12, left + (palletButtonLeft + (6 * 8)), top + 35));
		this.buttonList.add(this.buttonC9 = new GuiMMNamingStation.GuiColorSelectorButton(13, left + (palletButtonLeft + (6 * 9)), top + 35));
		this.buttonList.add(this.buttonCa = new GuiMMNamingStation.GuiColorSelectorButton(14, left + (palletButtonLeft + (6 * 10)), top + 35));
		this.buttonList.add(this.buttonCb = new GuiMMNamingStation.GuiColorSelectorButton(15, left + (palletButtonLeft + (6 * 11)), top + 35));
		this.buttonList.add(this.buttonCc = new GuiMMNamingStation.GuiColorSelectorButton(16, left + (palletButtonLeft + (6 * 12)), top + 35));
		this.buttonList.add(this.buttonCd = new GuiMMNamingStation.GuiColorSelectorButton(17, left + (palletButtonLeft + (6 * 13)), top + 35));
		this.buttonList.add(this.buttonCe = new GuiMMNamingStation.GuiColorSelectorButton(18, left + (palletButtonLeft + (6 * 14)), top + 35));
		this.buttonList.add(this.buttonCf = new GuiMMNamingStation.GuiColorSelectorButton(19, left + (palletButtonLeft + (6 * 15)), top + 35));
		
		this.buttonList.add(this.buttonF0 = new GuiMMNamingStation.GuiColorSelectorButton(20, left + 39, top + 40));
		this.buttonList.add(this.buttonF1 = new GuiMMNamingStation.GuiColorSelectorButton(21, left + 54, top + 40));
		this.buttonList.add(this.buttonF2 = new GuiMMNamingStation.GuiColorSelectorButton(22, left + 72, top + 40));
		this.buttonList.add(this.buttonF3 = new GuiMMNamingStation.GuiColorSelectorButton(23, left + 91, top + 40));
		this.buttonList.add(this.buttonF4 = new GuiMMNamingStation.GuiColorSelectorButton(24, left + 110, top + 40));
		this.buttonList.add(this.buttonF5 = new GuiMMNamingStation.GuiColorSelectorButton(25, left + 127, top + 40));
		
		this.updateButtons();
	}
	
	@Override
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
		
		this.buttonC0.offset = (this.color == 0)? 1 : 0;
		this.buttonC1.offset = (this.color == 1)? 1 : 0;
		this.buttonC2.offset = (this.color == 2)? 1 : 0;
		this.buttonC3.offset = (this.color == 3)? 1 : 0;
		this.buttonC4.offset = (this.color == 4)? 1 : 0;
		this.buttonC5.offset = (this.color == 5)? 1 : 0;
		this.buttonC6.offset = (this.color == 6)? 1 : 0;
		this.buttonC7.offset = (this.color == 7)? 1 : 0;
		this.buttonC8.offset = (this.color == 8)? 1 : 0;
		this.buttonC9.offset = (this.color == 9)? 1 : 0;
		this.buttonCa.offset = (this.color == 10)? 1 : 0;
		this.buttonCb.offset = (this.color == 11)? 1 : 0;
		this.buttonCc.offset = (this.color == 12)? 1 : 0;
		this.buttonCd.offset = (this.color == 13)? 1 : 0;
		this.buttonCe.offset = (this.color == 14)? 1 : 0;
		this.buttonCf.offset = (this.color == 15)? 1 : 0;
		
		this.buttonF0.offset = (this.obfuscated)? 1 : 0;
		this.buttonF1.offset = (this.bold)? 1 : 0;
		this.buttonF2.offset = (this.strike)? 1 : 0;
		this.buttonF3.offset = (this.underline)? 1 : 0;
		this.buttonF4.offset = (this.italic)? 1 : 0;
		this.buttonF5.offset = (this.reset)? 1 : 0;
	}
	
	@Override
	protected void actionPerformed(GuiButton button)
	{
		if (button.enabled)
		{
			switch(button.id)
			{
			case 1: // Hide the manual
				this.currentScreen = 0;
				((ContainerMMBase)this.inventorySlots).moveSlots(false, 800);
				for (int i = 4; i < this.buttonList.size(); ++i)
				{
					((GuiButton)this.buttonList.get(i)).yPosition -= 800;
				}
				this.nameField.yPosition -= 800;
				break;
			case 2:
				manual.pagePrev();
				break;
			case 3:
				manual.pageNext();
				break;
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
			case 11:
			case 12:
			case 13:
			case 14:
			case 15:
			case 16:
			case 17:
			case 18:
			case 19:
				this.color = button.id - 4;
				
				this.setNameFormat();
				
				nameField.setFocused(true);
				nameField.setCursorPositionEnd();
				break;
			case 20:
				this.obfuscated = !this.obfuscated;
				this.setNameFormat();
				break;
			case 21:
				this.bold = !this.bold;
				this.setNameFormat();
				break;
			case 22:
				this.strike = !this.strike;
				this.setNameFormat();
				break;
			case 23:
				this.underline = !this.underline;
				this.setNameFormat();
				break;
			case 24:
				this.italic = !this.italic;
				this.setNameFormat();
				break;
			case 25:
				this.resetName();
				break;
			default: // 0 - Show the manual
				this.currentScreen = 1;
				((ContainerMMBase)this.inventorySlots).moveSlots(true, 800);
				for (int i = 4; i < this.buttonList.size(); ++i)
				{
					((GuiButton)this.buttonList.get(i)).yPosition += 800;
				}
				this.nameField.yPosition += 800;
				break;
			}
			this.updateButtons();
		}
	}
	
	private void setNameFormat()
	{
		String name = this.nameField.getText();
		
		name += MMConstants.colorChar + "r";
		if (this.color < 16)
			name += MMConstants.colorChar + "" + colors[this.color];
		
		if (this.obfuscated)
			name += MMConstants.colorChar + "k";
		if (this.bold)
			name += MMConstants.colorChar + "l";
		if (this.strike)
			name += MMConstants.colorChar + "m";
		if (this.underline)
			name += MMConstants.colorChar + "n";
		if (this.italic)
			name += MMConstants.colorChar + "o";
		
		this.nameField.setText(name);
		this.updateNameToServer(name);
	}
	
	private void resetName()
	{
		this.color = 16;
		this.obfuscated = false;
		this.bold = false;
		this.strike = false;
		this.underline = false;
		this.italic = false;
		this.nameField.setText("");
		
		this.updateNameToServer("");
	}
	
	private void updateNameToServer(String name)
	{
		this.inventory.setItemName(name);
		IskallMiniMobs.packetPipeline.sendToServer(new MMNamingStationPacket(this.inventory.xCoord, this.inventory.yCoord, this.inventory.zCoord, name));
	}
	
	@Override
	public void updateScreen()
	{
		super.updateScreen();
		this.nameField.updateCursorCounter();
	}
	
	@Override
	protected void keyTyped(char keyChar, int keyCode)
	{
		if (keyCode == 1 || (!this.textFieldActive && keyCode == this.mc.gameSettings.keyBindInventory.getKeyCode()))
		{
			Keyboard.enableRepeatEvents(false);
			this.mc.theWorld.playSoundAtEntity(this.mc.thePlayer, "iskallminimobs:namestation_close", 1.0F, 1.2F / (this.mc.theWorld.rand.nextFloat() * 0.2F + 0.9F));
			this.mc.thePlayer.closeScreen();
		} else if (this.textFieldActive) {
			nameField.textboxKeyTyped(keyChar, keyCode);
			String name = nameField.getText();
			this.updateNameToServer(name);
		}
	}
	
	@Override
	public void mouseClicked(int i, int j, int k)
	{
		super.mouseClicked(i, j, k);
		
		this.textFieldActive = i > this.nameField.xPosition 
				&& i < this.nameField.xPosition + this.nameField.width 
				&& j > this.nameField.yPosition
				&& j < this.nameField.yPosition + this.nameField.height;
		
		if (this.textFieldActive)
			nameField.mouseClicked(i, j, k);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float prop, int x, int y)
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
	
	@Override
	public void drawScreen(int x, int y, float s)
	{
		super.drawScreen(x, y, s);
		
		nameField.drawTextBox();
	}
	
	private void renderWorkGUI()
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(guiTextures);
		
		int left = (this.width - this.xSize) / 2;
		int top = (this.height - this.ySize) / 2;
		
		this.drawTexturedModalRect(left, top, 0, 0, this.xSize, this.ySize);
		
		if (!this.inventory.canName())
			this.drawTexturedModalRect(left + 155, top + 10, 10, 174, 14, 189 - 174);
	}
	
	private void renderUserManual()
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(GuiMMManual.guiTexturesManual);
		
		int left = (this.width - this.xSize) / 2;
		int top = (this.height - this.ySize) / 2;
		
		this.drawTexturedModalRect(left, top, 0, 0, this.xSize, this.ySize);
		
		String s = "";
		manual.totalManualPages = 3;
		s = "Naming Station User's Guide";
		this.fontRendererObj.drawString(s, left + 5, top + 5, 0);
		
		switch (manual.currentManualPage)
		{
		case 1:
			manual.addLine("You'll need to add some colors", 2, 0);
			manual.addLine("in order to stamp the names to", 3, 0);
			manual.addLine("yout items.", 4, 0);
			
			manual.addLine("Put some dyes in the 16 slots", 6, 0);
			manual.addLine("available, then select color", 7, 0);
			manual.addLine("and format from the buttons", 8, 0);
			manual.addLine("above.", 9, 0);
			break;
		case 2:
			manual.addLine("If you see a red X, it", 2, 0);
			manual.addLine("means you don't have enough", 3, 0);
			manual.addLine("color to name the item.", 4, 0);
			manual.addLine("", 5, 0);
			manual.addLine("Taking the named item will", 6, 0);
			manual.addLine("consume the necesary colors,", 7, 0);
			manual.addLine("1 dye per letter and 1 extra", 8, 0);
			manual.addLine("per formating per letter.", 9, 0);
			break;
		case 3:
			manual.addLine("For example 1 bold, italic,", 2, 0);
			manual.addLine("underline red letter, consumes", 3, 0);
			manual.addLine("4 red dye, 1 for the letter,", 4, 0);
			manual.addLine("and 1 extra for each", 5, 0);
			manual.addLine("formatting.", 6, 0);
			
			manual.addLine("Enjoy!", 8, 0);
			break;
		default:
			manual.addLine("Congratulations on your new", 2, 0);
			manual.addLine("Naming Station!", 3, 0);
			
			manual.addLine("We belive experience is not", 5, 0);
			manual.addLine("needed for naming your stuff.", 6, 0);
			manual.addLine("Instead just use resources", 7, 0);
			manual.addLine("for the matter.", 8, 0);
			break;
		}
		
		s = "Page: " + (manual.currentManualPage + 1);
		this.fontRendererObj.drawString(s, left + 129, top + 144, 0);
	}
	
	@SideOnly(Side.CLIENT)
	static class GuiColorSelectorButton
		extends GuiButton
	{
		public int offset = 0;
		public int offsetValue = 5;
		
		public GuiColorSelectorButton(int buttonId, int x, int y)
		{
			super(buttonId, x, y, 16, 13, "");
			this.width = 5;
			this.height = 3;
		}
		
		@Override
		public void drawButton(Minecraft minecraft, int mouseX, int mouseY)
		{
			if (this.visible)
			{
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				minecraft.getTextureManager().bindTexture(GuiMMNamingStation.guiTextures);
				
				int k = 0 + (offset * offsetValue);
				int l = 174;
				
				this.drawTexturedModalRect(this.xPosition, this.yPosition, k, l, this.width, this.height);
			}
		}
	}
}
