package zairus.iskallminimobs.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import zairus.iskallminimobs.MMConstants;
import zairus.iskallminimobs.entity.minimob.MiniMobData;
import zairus.iskallminimobs.inventory.ContainerMMBase;
import zairus.iskallminimobs.inventory.ContainerMMDNAExtractor;
import zairus.iskallminimobs.item.MMCorpse;
import zairus.iskallminimobs.tileentity.TileEntityMMDNAExtractor;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiMMDNAExtractor
	extends GuiContainer
{
	private static final ResourceLocation guiTextures = new ResourceLocation(MMConstants.MODID, "textures/gui/gui_dnaextractor.png");
	
	private TileEntityMMDNAExtractor inventory;
	private float phase = 0.0F;
	private int currentScreen = 0;
	
	private GuiMMManual manual;
	private GuiMMManual.GuiMMButton buttonManual;
	private GuiMMManual.GuiMMButton buttonManualExit;
	private GuiMMManual.GuiMMButton buttonPrev;
	private GuiMMManual.GuiMMButton buttonNext;
	
	public GuiMMDNAExtractor(InventoryPlayer playerInv, TileEntityMMDNAExtractor inv, World world)
	{
		super(new ContainerMMDNAExtractor(playerInv, inv, world));
		
		this.inventory = inv;
		this.xSize = 176;
		this.ySize = 174;
		
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
		manual.xSize = this.xSize;
		manual.ySize = this.ySize;
		
		this.buttonList.clear();
		Keyboard.enableRepeatEvents(true);
		
		int left = (this.width - this.xSize) / 2;
		int top = (this.height - this.ySize) / 2;
		
		this.buttonList.add(this.buttonManual = new GuiMMManual.GuiMMButton(0, left - 13, top + 5));
		this.buttonList.add(this.buttonManualExit = new GuiMMManual.GuiMMButton(1, left - 13, top + 5 + 17));
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
	
	public void drawScreen(int x, int y, float s)
	{
		super.drawScreen(x, y, s);
	}
	
	private void renderUserManual()
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(GuiMMManual.guiTexturesManual);
		
		int left = (this.width - this.xSize) / 2;
		int top = (this.height - this.ySize) / 2;
		
		this.drawTexturedModalRect(left, top, 0, 0, this.xSize, this.ySize);
		
		String s = "";
		manual.totalManualPages = 5;
		s = "Your DNA Extractor User's Guide";
		this.fontRendererObj.drawString(s, left + 5, top + 5, 0);
		
		switch (manual.currentManualPage)
		{
		case 1:
			manual.addLine("You will need to put your", 2, 0);
			manual.addLine("mini friend's corpse in", 3, 0);
			manual.addLine("the top left slot, and an", 4, 0);
			manual.addLine("embryo of the corresponding", 5, 0);
			manual.addLine("type in the middle section.", 6, 0);
			
			manual.addLine("Then use elements that will", 8, 0);
			manual.addLine("be consumed in the process", 9, 0);
			manual.addLine("as well as RF power.", 10, 0);
			break;
		case 2:
			manual.addLine("In the source elements slots", 2, 0);
			manual.addLine("(2x2 grid below the corpse),", 3, 0);
			manual.addLine("you neeed 2 types of items.", 4, 0);
			
			manual.addLine("-Value Items.", 6, 0);
			manual.addLine("-Essence Items.", 7, 0);
			break;
		case 3:
			manual.addLine("Value items are:", 2, 0);
			manual.addLine("- Iron Ingots: Less value", 3, 0);
			manual.addLine("- Gold Ingots: Moderate value", 4, 0);
			manual.addLine("- Diamonds: High value", 5, 0);
			manual.addLine("- Emeralds: Most value", 6, 0);
			
			manual.addLine("The greater value, the", 8, 0);
			manual.addLine("fastest the process,", 9, 0);
			manual.addLine("and consumes less.", 10, 0);
			break;
		case 4:
			manual.addLine("Essence items depend on", 2, 0);
			manual.addLine("the species of your friend.", 3, 0);
			manual.addLine("- For Pig, porkchop", 4, 0);
			manual.addLine("- For Zombie, rotten flesh", 5, 0);
			manual.addLine("- For Skeleton, bones", 6, 0);
			manual.addLine("- For Creeper, gun powder", 7, 0);
			manual.addLine("- For Spider, string", 8, 0);
			manual.addLine("- For Soldier, flesh or bones", 9, 0);
			break;
		case 5:
			manual.addLine("The result will be an embryo", 2, 0);
			manual.addLine("with all the stats the dead", 3, 0);
			manual.addLine("mini mob had, so you can", 4, 0);
			manual.addLine("incubate it again.", 5, 0);
			
			manual.addLine("Enjoy having your friend back.", 7, 0);
			
			manual.addLine("Warranty void if seal is broken", 9, 0);
			manual.addLine("or damaged.", 10, 0);
			break;
		default:
			manual.addLine("With the use of this machine,", 2, 0);
			manual.addLine("you'll be able to bring back", 3, 0);
			manual.addLine("your beloved dead mini friends.", 4, 0);
			
			manual.addLine("In the unfortunate case of", 6, 0);
			manual.addLine("death, keep your friends DNA", 7, 0);
			manual.addLine("stored so you can make use", 8, 0);
			manual.addLine("of it.", 9, 0);
			break;
		}
		
		s = "Page: " + (manual.currentManualPage + 1);
		this.fontRendererObj.drawString(s, left + 129, top + 144, 0);
	}
	
	private void renderWorkGUI()
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(guiTextures);
		
		int left = (this.width - this.xSize) / 2;
		int top = (this.height - this.ySize) / 2;
		
		phase += 0.03F;
		if (phase >= 100.0F)
			phase = 0.0F;
		
		this.drawTexturedModalRect(left, top, 0, 0, this.xSize, this.ySize);
		
		this.drawTexturedModalRect(left + 34, top + 21, 178, 88, 209 - 178, 102 - 88);
		this.drawTexturedModalRect(left + 93, top + 21, 178, 88, 209 - 178, 102 - 88);
		
		int powerXPos = 155;
		int powerYPos = 6;
		
		int rf = this.inventory.getEnergyStored(ForgeDirection.UNKNOWN);
		
		int powerBarSize = 81 - (int)(((float)rf / 5000.0F) * 81.0F);
		
		this.drawTexturedModalRect(left + powerXPos, top + powerYPos + powerBarSize, 178, 6, 190 - 178, 87 - 6 - powerBarSize);
		
		ItemStack corpse = this.inventory.getStackInSlot(4);
		
		String s = "";
		
		if (corpse != null && corpse.getItem() instanceof MMCorpse)
		{
			if (corpse.hasTagCompound())
			{
				if (corpse.getTagCompound().hasKey(MiniMobData.MOBDATA_KEY))
				{
					NBTTagCompound mmdata = (NBTTagCompound)corpse.getTagCompound().getTag(MiniMobData.MOBDATA_KEY);
					
					int xpX = 50;
					int xpY = 70;
					
					float nextLev = (float)mmdata.getDouble(MiniMobData.NEXTLEVEL_KEY);
					float prevLev = nextLev / 1.9F;
					float progress = (float)mmdata.getDouble(MiniMobData.EXPERIENCE_KEY);
					float percent = progress / (nextLev - prevLev);
					int barProgress = ((int)(28.0F * percent));
					
					if (barProgress > 28)
						barProgress = 28;
					
					for (int i = 0; i < 28; ++i)
						this.drawTexturedModalRect((left + xpX) + (i * 3), top + xpY, 2, 175, 3, 4);
					
					for (int i = 0; i < barProgress; ++i)
						this.drawTexturedModalRect((left + xpX) + (i * 3), top + xpY, 5, 175, 3, 4);
					
					int healthX = 50;
					int healthY = 75;
					
					double h = mmdata.getDouble(MiniMobData.HEALTH_KEY);
					
					for (int i = 0; i < 24; ++i)
					{
						this.drawTexturedModalRect((left + healthX) + (i * 7) - ((i > 11)? (12*7) : 0), top + healthY + ((i > 11)? 6 : 0), 2, 180, ((h < 2.0D)? 4 : 7), 6);
						h -= 2.0D;
						if (h < 1.0D)
							break;
					}
					
					if (this.inventory.canProcess())
					{
						int p = (int)(31 * (this.inventory.progress / 100.0F));
						this.drawTexturedModalRect(left + 34, top + 21, 178, 102, p, 15);
						this.drawTexturedModalRect(left + 93, top + 21, 178, 102, p, 15);
					}
					else
					{
						this.drawTexturedModalRect(left + 102, top + 20, 178, 117, 191 - 178, 132 - 117);
					}
					
					float scale = 0.9F;
					GL11.glScalef(scale, scale, scale);
					s = "lv: " + mmdata.getInteger(MiniMobData.LEVEL_KEY) + ", xp: " + (Math.round(progress * 100.0D) / 100.0D);
					this.fontRendererObj.drawString(s, (int)(left * (1 / scale)) + 57, (int)(top * (1 / scale)) + 68, 0);
					GL11.glScalef(1.0F / scale, 1.0F / scale, 1.0F / scale);
				}
			}
		}
		
		s = "DNA Extractor";
		this.fontRendererObj.drawString(s, left + 5, top + 5, 0);
		
		s = "RF";
		this.fontRendererObj.drawString(s, left + 140, top + 5, 0);
	}
}
