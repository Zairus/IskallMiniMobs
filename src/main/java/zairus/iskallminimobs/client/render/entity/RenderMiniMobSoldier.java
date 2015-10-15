package zairus.iskallminimobs.client.render.entity;

import java.io.File;
import java.util.UUID;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;

import org.lwjgl.opengl.GL11;

import zairus.iskallminimobs.MMConstants;
import zairus.iskallminimobs.entity.minimob.EntityMiniMobSoldier;

import com.mojang.authlib.GameProfile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderMiniMobSoldier 
	extends RendererLivingEntity
{
	public static final ResourceLocation locationStevePng = new ResourceLocation("textures/entity/steve.png");
	
	public ModelBiped modelBipedMain;
	public ModelBiped modelArmorChestplate;
	public ModelBiped modelArmor;
	
	public RenderMiniMobSoldier()
	{
		super(new ModelBiped(0.0F), 0.5F);
		
		this.modelBipedMain = (ModelBiped) this.mainModel;
		this.modelArmorChestplate = new ModelBiped(1.0F);
		this.modelArmor = new ModelBiped(0.5F);
	}
	
	protected int shouldRenderPass(EntityMiniMobSoldier entity, int p_77032_2_, float p_77032_3_)
	{
		ItemStack itemstack = entity.getArmorItemStack(3 - p_77032_2_);
		
		if (itemstack != null)
		{
			Item item = itemstack.getItem();
			
			if (item instanceof ItemArmor)
			{
				ItemArmor itemarmor = (ItemArmor) item;
				this.bindTexture(RenderBiped.getArmorResource(entity, itemstack, p_77032_2_, null));
				ModelBiped modelbiped = p_77032_2_ == 2 ? this.modelArmor : this.modelArmorChestplate;
				modelbiped.bipedHead.showModel = p_77032_2_ == 0;
				modelbiped.bipedHeadwear.showModel = p_77032_2_ == 0;
				modelbiped.bipedBody.showModel = p_77032_2_ == 1 || p_77032_2_ == 2;
				modelbiped.bipedRightArm.showModel = p_77032_2_ == 1;
				modelbiped.bipedLeftArm.showModel = p_77032_2_ == 1;
				modelbiped.bipedRightLeg.showModel = p_77032_2_ == 2 || p_77032_2_ == 3;
				modelbiped.bipedLeftLeg.showModel = p_77032_2_ == 2 || p_77032_2_ == 3;
				modelbiped = net.minecraftforge.client.ForgeHooksClient.getArmorModel(entity, itemstack, p_77032_2_, modelbiped);
				
				this.setRenderPassModel(modelbiped);
				
				modelbiped.onGround = this.mainModel.onGround;
				modelbiped.isRiding = this.mainModel.isRiding;
				modelbiped.isChild = this.mainModel.isChild;
				
				// Move outside if to allow for more then just CLOTH
				int j = itemarmor.getColor(itemstack);
				if (j != -1)
				{
					float f1 = (float) (j >> 16 & 255) / 255.0F;
					float f2 = (float) (j >> 8 & 255) / 255.0F;
					float f3 = (float) (j & 255) / 255.0F;
					GL11.glColor3f(f1, f2, f3);
					
					if (itemstack.isItemEnchanted())
					{
						return 31;
					}
					
					return 16;
				}
				
				GL11.glColor3f(1.0F, 1.0F, 1.0F);
				
				if (itemstack.isItemEnchanted())
				{
					return 15;
				}
				
				return 1;
			}
		}
		
		return -1;
	}
	
	protected void func_82408_c(EntityMiniMobSoldier entity, int p_82408_2_, float p_82408_3_)
	{
		ItemStack itemstack = entity.getArmorItemStack(3 - p_82408_2_);
		
		if (itemstack != null)
		{
			Item item = itemstack.getItem();
			
			if (item instanceof ItemArmor)
			{
				this.bindTexture(RenderBiped.getArmorResource(entity, itemstack, p_82408_2_, "overlay"));
				GL11.glColor3f(1.0F, 1.0F, 1.0F);
			}
		}
	}
	
	public void doRender(EntityMiniMobSoldier entity, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		GL11.glColor3f(1.0F, 1.0F, 1.0F);
		ItemStack itemstack = entity.getHeldItem();
		this.modelArmorChestplate.heldItemRight = this.modelArmor.heldItemRight = this.modelBipedMain.heldItemRight = itemstack != null ? 1 : 0;
		
		this.modelArmorChestplate.isSneak = this.modelArmor.isSneak = this.modelBipedMain.isSneak = entity.isSneaking();
		double d3 = p_76986_4_ - (double) entity.yOffset;
		
		if (entity.isSneaking())
		{
			d3 -= 0.125D;
		}
		
		super.doRender((EntityLivingBase) entity, p_76986_2_, d3, p_76986_6_, p_76986_8_, p_76986_9_);
		
		this.modelArmorChestplate.aimedBow = this.modelArmor.aimedBow = this.modelBipedMain.aimedBow = false;
		this.modelArmorChestplate.isSneak = this.modelArmor.isSneak = this.modelBipedMain.isSneak = false;
		this.modelArmorChestplate.heldItemRight = this.modelArmor.heldItemRight = this.modelBipedMain.heldItemRight = 0;
	}
	
	protected ResourceLocation getEntityTexture(EntityMiniMobSoldier entity)
	{
		return getLocationSkin(entity.getMiniMobName(), entity.getMiniMobUUID());
	}
	
	private ResourceLocation getLocationSkin(String player_name, String uuid)
	{
		ResourceLocation loc = new ResourceLocation(MMConstants.MODID, "textures/entity/" + uuid + ".png");
		TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
		Object object = texturemanager.getTexture(loc);
		
		if (object == null)
		{
			object = new ThreadDownloadImageData(
					(File) null
					, String.format("http://skins.minecraft.net/MinecraftSkins/%s.png", new Object[] { StringUtils.stripControlCodes(player_name) })
					, locationStevePng
					, new ImageBufferDownload());
			
			texturemanager.loadTexture(loc, (ITextureObject)object);
		}
		
		return loc;
	}
	
	protected void renderEquippedItems(EntityMiniMobSoldier entity, float p_77029_2_)
	{
		GL11.glColor3f(1.0F, 1.0F, 1.0F);
		
		super.renderEquippedItems(entity, p_77029_2_);
		super.renderArrowsStuckInEntity(entity, p_77029_2_);
		
		ItemStack itemstack = entity.getArmorItemStack(3);
		
		if (itemstack != null)
		{
			GL11.glPushMatrix();
			this.modelBipedMain.bipedHead.postRender(0.0625F);
			float f1;
			
			if (itemstack.getItem() instanceof ItemBlock)
			{
				net.minecraftforge.client.IItemRenderer customRenderer = net.minecraftforge.client.MinecraftForgeClient.getItemRenderer(itemstack, net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED);
				boolean is3D = (customRenderer != null && customRenderer.shouldUseRenderHelper(net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED, itemstack, net.minecraftforge.client.IItemRenderer.ItemRendererHelper.BLOCK_3D));
				
				if (is3D || RenderBlocks.renderItemIn3d(Block.getBlockFromItem(itemstack.getItem()).getRenderType()))
				{
					f1 = 0.625F;
					GL11.glTranslatef(0.0F, -0.25F, 0.0F);
					GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
					GL11.glScalef(f1, -f1, -f1);
				}
				
				this.renderManager.itemRenderer.renderItem(entity, itemstack, 0);
			}
			else if (itemstack.getItem() == Items.skull)
			{
				f1 = 1.0625F;
				GL11.glScalef(f1, -f1, -f1);
				GameProfile gameprofile = null;
				
				if (itemstack.hasTagCompound())
				{
					NBTTagCompound nbttagcompound = itemstack.getTagCompound();
					
					if (nbttagcompound.hasKey("SkullOwner", 10))
					{
						gameprofile = NBTUtil.func_152459_a(nbttagcompound.getCompoundTag("SkullOwner"));
					} else if (nbttagcompound.hasKey("SkullOwner", 8) && !StringUtils.isNullOrEmpty(nbttagcompound.getString("SkullOwner"))) {
						gameprofile = new GameProfile((UUID) null, nbttagcompound.getString("SkullOwner"));
					}
				}
				
				TileEntitySkullRenderer.field_147536_b.func_152674_a(-0.5F, 0.0F, -0.5F, 1, 180.0F, itemstack.getItemDamage(), gameprofile);
			}
			
			GL11.glPopMatrix();
		}
		
		float f2;
		
		if (entity.getCommandSenderName().equals("deadmau5"))
		{
			this.bindTexture(getLocationSkin(entity.getMiniMobName(), entity.getUniqueID().toString()));
			
			for (int j = 0; j < 2; ++j)
			{
				float f9 = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * p_77029_2_ - (entity.prevRenderYawOffset + (entity.renderYawOffset - entity.prevRenderYawOffset) * p_77029_2_);
				float f10 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * p_77029_2_;
				
				GL11.glPushMatrix();
				GL11.glRotatef(f9, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(f10, 1.0F, 0.0F, 0.0F);
				GL11.glTranslatef(0.375F * (float) (j * 2 - 1), 0.0F, 0.0F);
				GL11.glTranslatef(0.0F, -0.375F, 0.0F);
				GL11.glRotatef(-f10, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(-f9, 0.0F, 1.0F, 0.0F);
				f2 = 1.3333334F;
				GL11.glScalef(f2, f2, f2);
				this.modelBipedMain.renderEars(0.0625F);
				GL11.glPopMatrix();
			}
		}
		
		float f4;
		
		ItemStack itemstack1 = entity.getHeldItem();
		
		if (itemstack1 != null)
		{
			GL11.glPushMatrix();
			this.modelBipedMain.bipedRightArm.postRender(0.025F);
			GL11.glTranslatef(-0.0625F, 0.7575F, 0.0625F);
			
			net.minecraftforge.client.IItemRenderer customRenderer = net.minecraftforge.client.MinecraftForgeClient.getItemRenderer(itemstack1, net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED);
			boolean is3D = (customRenderer != null && customRenderer.shouldUseRenderHelper(net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED, itemstack1, net.minecraftforge.client.IItemRenderer.ItemRendererHelper.BLOCK_3D));
			
			if (is3D || itemstack1.getItem() instanceof ItemBlock && RenderBlocks.renderItemIn3d(Block.getBlockFromItem(itemstack1.getItem()).getRenderType()))
			{
				f2 = 0.5F;
				GL11.glTranslatef(0.0F, 0.1875F, -0.3125F);
				f2 *= 0.75F;
				GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
				GL11.glScalef(-f2, -f2, f2);
			} else if (itemstack1.getItem() == Items.bow) {
				f2 = 0.425F;
				GL11.glTranslatef(0.0F, 0.225F, 0.3125F);
				GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
				GL11.glScalef(f2, -f2, f2);
				GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
			} else if (itemstack1.getItem().isFull3D()) {
				f2 = 0.35F;
				
				if (itemstack1.getItem().shouldRotateAroundWhenRendering())
				{
					GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
					GL11.glTranslatef(0.0F, -0.125F, 0.0F);
				}
				
				GL11.glTranslatef(0.05F, 0.1875F, 0.1F);
				GL11.glScalef(f2, -f2, f2);
				GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
			}
			else
			{
				f2 = 0.375F;
				GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
				GL11.glScalef(f2, f2, f2);
				GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
				GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
			}
			
			float f3;
			int k;
			float f12;
			
			if (itemstack1.getItem().requiresMultipleRenderPasses())
			{
				for (k = 0; k < itemstack1.getItem().getRenderPasses(itemstack1.getItemDamage()); ++k)
				{
					int i = itemstack1.getItem().getColorFromItemStack(itemstack1, k);
					f12 = (float) (i >> 16 & 255) / 255.0F;
					f3 = (float) (i >> 8 & 255) / 255.0F;
					f4 = (float) (i & 255) / 255.0F;
					GL11.glColor4f(f12, f3, f4, 1.0F);
					this.renderManager.itemRenderer.renderItem(entity, itemstack1, k);
				}
			}
			else
			{
				k = itemstack1.getItem().getColorFromItemStack(itemstack1, 0);
				float f11 = (float) (k >> 16 & 255) / 255.0F;
				f12 = (float) (k >> 8 & 255) / 255.0F;
				f3 = (float) (k & 255) / 255.0F;
				GL11.glColor4f(f11, f12, f3, 1.0F);
				this.renderManager.itemRenderer.renderItem(entity, itemstack1, 0);
			}
			
			GL11.glPopMatrix();
		}
	}
	
	protected void preRenderCallback(EntityMiniMobSoldier p_77041_1_, float p_77041_2_)
	{
		float f1 = 0.85F;
		GL11.glScalef(f1, f1, f1);
	}
	
	protected void func_96449_a(EntityMiniMobSoldier entity, double p_96449_2_, double p_96449_4_, double p_96449_6_, String p_96449_8_, float p_96449_9_, double p_96449_10_)
	{
		p_96449_4_ += 0.3D;
		super.func_96449_a(entity, p_96449_2_, p_96449_4_, p_96449_6_, p_96449_8_, p_96449_9_, p_96449_10_);
	}
	
	public void renderFirstPersonArm(EntityPlayer p_82441_1_)
	{
		float f = 1.0F;
		GL11.glColor3f(f, f, f);
		this.modelBipedMain.onGround = 0.0F;
		this.modelBipedMain.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, p_82441_1_);
		this.modelBipedMain.bipedRightArm.render(0.0625F);
	}
	
	protected void renderLivingAt(EntityMiniMobSoldier p_77039_1_, double p_77039_2_, double p_77039_4_, double p_77039_6_)
	{
		super.renderLivingAt(p_77039_1_, p_77039_2_, p_77039_4_, p_77039_6_);
	}
	
	protected void rotateCorpse(EntityMiniMobSoldier p_77043_1_, float p_77043_2_, float p_77043_3_, float p_77043_4_)
	{
		super.rotateCorpse(p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
	}
	
	protected void func_96449_a(EntityLivingBase p_96449_1_, double p_96449_2_, double p_96449_4_, double p_96449_6_, String p_96449_8_, float p_96449_9_, double p_96449_10_)
	{
		this.func_96449_a((EntityMiniMobSoldier) p_96449_1_, p_96449_2_, p_96449_4_, p_96449_6_, p_96449_8_, p_96449_9_, p_96449_10_);
	}
	
	protected void preRenderCallback(EntityLivingBase p_77041_1_, float p_77041_2_)
	{
		this.preRenderCallback((EntityMiniMobSoldier) p_77041_1_, p_77041_2_);
	}
	
	protected void func_82408_c(EntityLivingBase p_82408_1_, int p_82408_2_, float p_82408_3_)
	{
		this.func_82408_c((EntityMiniMobSoldier) p_82408_1_, p_82408_2_, p_82408_3_);
	}
	
	protected int shouldRenderPass(EntityLivingBase p_77032_1_, int p_77032_2_, float p_77032_3_)
	{
		return this.shouldRenderPass((EntityMiniMobSoldier) p_77032_1_, p_77032_2_, p_77032_3_);
	}
	
	protected void renderEquippedItems(EntityLivingBase p_77029_1_, float p_77029_2_)
	{
		this.renderEquippedItems((EntityMiniMobSoldier) p_77029_1_, p_77029_2_);
	}
	
	protected void rotateCorpse(EntityLivingBase p_77043_1_, float p_77043_2_, float p_77043_3_, float p_77043_4_)
	{
		this.rotateCorpse((EntityMiniMobSoldier) p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
	}
	
	protected void renderLivingAt(EntityLivingBase p_77039_1_, double p_77039_2_, double p_77039_4_, double p_77039_6_)
	{
		this.renderLivingAt((EntityMiniMobSoldier) p_77039_1_, p_77039_2_, p_77039_4_, p_77039_6_);
	}
	
	public void doRender(EntityLivingBase p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		this.doRender((EntityMiniMobSoldier) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}
	
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return this.getEntityTexture((EntityMiniMobSoldier) entity);
	}
	
	public void doRender(Entity entity, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		this.doRender((EntityMiniMobSoldier) entity, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}
}
