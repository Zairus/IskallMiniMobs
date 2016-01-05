package zairus.iskallminimobs.gui;

import java.awt.Color;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import zairus.iskallminimobs.MMConstants;
import zairus.iskallminimobs.entity.minimob.EntityMiniMobBase;
import zairus.iskallminimobs.inventory.ContainerMiniMob;

@SideOnly(Side.CLIENT)
public class GuiMMInventory
	extends GuiContainer
{
	private static final ResourceLocation guiTextures = new ResourceLocation(MMConstants.MODID, "textures/gui/gui_minimob.png");
	private EntityMiniMobBase miniMob;
	private float x1;
	private float y1;
	
	public GuiMMInventory(InventoryPlayer playerInv, EntityMiniMobBase miniMob, World world)
	{
		super(new ContainerMiniMob(playerInv, miniMob, world));
		
		initValues(miniMob);
	}
	
	private void initValues(EntityMiniMobBase mm)
	{
		this.mc = Minecraft.getMinecraft();
		this.xSize = 176;
		this.ySize = 174;
		this.miniMob = mm;
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
		this.mc.getTextureManager().bindTexture(guiTextures);
		
		int left = (this.width - this.xSize) / 2;
		int top = (this.height - this.ySize) / 2;
		
		this.drawTexturedModalRect(left, top, 0, 0, this.xSize, this.ySize);
		
		int xpX = 85;
		int xpY = 71;
		float nextLev = this.miniMob.getDataValue(EntityMiniMobBase.DATA_XP_NEXTLEVELUP);
		float prevLev = nextLev / 1.9F;
		float progress = this.miniMob.getDataValue(EntityMiniMobBase.DATA_XP_CURRENT) - prevLev;
		float percent = progress / (nextLev - prevLev);
		int barProgress = ((int)(28.0F * percent));
		
		if (barProgress > 28)
			barProgress = 28;
		
		for (int i = 0; i < 28; ++i)
			this.drawTexturedModalRect((left + xpX) + (i * 3), top + xpY, 2, 175, 3, 4);
		
		for (int i = 0; i < barProgress; ++i)
			this.drawTexturedModalRect((left + xpX) + (i * 3), top + xpY, 5, 175, 3, 4);
		
		int healthX = 85;
		int healthY = 77;
		
		double h = this.miniMob.getEntityAttribute(SharedMonsterAttributes.maxHealth).getAttributeValue();
		
		for (int i = 0; i < 24; ++i)
		{
			this.drawTexturedModalRect((left + healthX) + (i * 7) - ((i > 11)? healthX-1 : 0), top + healthY + ((i > 11)? 6 : 0), 2, 180, ((h < 2.0D)? 4 : 7), 6);
			h -= 2.0D;
			if (h < 1.0D)
				break;
		}
		
		h = (double)this.miniMob.getHealth();
		
		int hX = 0;
		int hY = 0;
		int hLayer = 0;
		int hSize = 7;
		
		for (int i = 0; i < 48; ++i)
		{
			hX = left + healthX;
			hY = top + healthY;
			
			hX += (i * 7);
			
			if (i > 35 || (i > 11 && i < 24))
			{
				hY += 6;
				hX -= healthX - 1;
			}
			
			if (i > 23)
			{
				hX -= healthX - 1;
				hX -= healthX - 1;
			}
			
			if (h < 2.0D)
			{
				hSize = 4;
			}
			else
			{
				hSize = 7;
			}
			
			if (i > 23)
			{
				hLayer = 16;
			}
			else
			{
				hLayer = 9;
			}
			
			this.drawTexturedModalRect(hX, hY, hLayer, 180, hSize, 6);
			h -= 2.0D;
			if (h < 1.0D)
				break;
		}
		
		//this.miniMob.getDataValue(EntityMiniMobBase.DATA_SPEED);
		//this.miniMob.getDataValue(EntityMiniMobBase.DATA_DAMAGE);
		
		String s = "lv: " + ((int)this.miniMob.getDataValue(EntityMiniMobBase.DATA_LEVEL));
		this.fontRendererObj.drawString(s, left + 28, top + 10, Color.WHITE.getRGB());
		
		float scale = 0.75F;
		GL11.glScalef(scale, scale, scale);
		
		s = "xp: " + (Math.round(this.miniMob.getDataValue(EntityMiniMobBase.DATA_XP_CURRENT) * 100.0D) / 100.0D);
		this.fontRendererObj.drawString(s, (int)(left * (1 / scale)) + 115, (int)(top * (1 / scale)) + 86, 0);
		
		GL11.glScalef(1.0F / scale, 1.0F / scale, 1.0F / scale);
	}
	
	public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_)
	{
		super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
		x1 = (float)p_73863_1_;
		y1 = (float)p_73863_2_;
		entityArea(0.0F, 0, 0);
	}
	
	protected void entityArea(float p_146976_1_, int p_146976_2_, int p_146976_3_)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.0F);
		this.mc.getTextureManager().bindTexture(guiTextures);
		int left = (this.width - this.xSize) / 2;
		int top = (this.height - this.ySize) / 2;
		renderMini(left + 51, top + 75, 30, (float)(left + 51) - this.x1, (float)(top + 75 - 50) - this.y1, this.miniMob);
    }
	
	public static void renderMini(int p_147046_0_, int p_147046_1_, int p_147046_2_, float p_147046_3_, float p_147046_4_, EntityLivingBase entity)
	{
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glPushMatrix();
		GL11.glTranslatef((float)p_147046_0_, (float)p_147046_1_, 50.0F);
		GL11.glScalef((float)(-p_147046_2_), (float)p_147046_2_, (float)p_147046_2_);
		GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
		float var6 = entity.renderYawOffset;
		float var7 = entity.rotationYaw;
		float var8 = entity.rotationPitch;
		float var9 = entity.prevRotationYawHead;
		float var10 = entity.rotationYawHead;
		GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-((float)Math.atan((double)(p_147046_4_ / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
		entity.renderYawOffset = (float)Math.atan((double)(p_147046_3_ / 40.0F)) * 20.0F;
		entity.rotationYaw = (float)Math.atan((double)(p_147046_3_ / 40.0F)) * 40.0F;
		entity.rotationPitch = -((float)Math.atan((double)(p_147046_4_ / 40.0F))) * 20.0F;
		entity.rotationYawHead = entity.rotationYaw;
		entity.prevRotationYawHead = entity.rotationYaw;
		GL11.glTranslatef(0.0F, entity.yOffset, 0.0F);
		
		GL11.glScalef(1.5F, 1.5F, 1.5F);
		
		RenderManager.instance.playerViewY = 180.0F;
		RenderManager.instance.func_147939_a(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, true);
		RenderManager.instance.getEntityRenderObject(entity).doRender(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
		
		GL11.glScalef(-.5F, -1.5F, -1.5F);
		
		entity.renderYawOffset = var6;
		entity.rotationYaw = var7;
		entity.rotationPitch = var8;
		entity.prevRotationYawHead = var9;
		entity.rotationYawHead = var10;
		GL11.glPopMatrix();
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}
}
