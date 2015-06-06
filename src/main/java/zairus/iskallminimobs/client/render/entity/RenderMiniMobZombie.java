package zairus.iskallminimobs.client.render.entity;

import java.util.UUID;

import org.lwjgl.opengl.GL11;

import com.mojang.authlib.GameProfile;

import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import zairus.iskallminimobs.entity.minimob.EntityMiniMobZombie;
import zairus.iskallminimobs.model.ModelMiniMobZombie;

public class RenderMiniMobZombie extends RenderBiped
{
	private static final ResourceLocation zombieTextures = new ResourceLocation("textures/entity/zombie/zombie.png");
	
	private ModelBiped field_82434_o;
	
	public RenderMiniMobZombie()
	{
		super(new ModelMiniMobZombie(), 0.5F, 1.0F);
		this.field_82434_o = this.modelBipedMain;
	}
	
	protected void func_82421_b()
	{
		this.field_82423_g = new ModelMiniMobZombie(1.0F, true);
		this.field_82425_h = new ModelMiniMobZombie(0.5F, true);
	}
	
	protected int shouldRenderPass(EntityMiniMobZombie p_77032_1_, int p_77032_2_, float p_77032_3_)
	{
		this.func_82427_a(p_77032_1_);
		return super.shouldRenderPass((EntityLiving) p_77032_1_, p_77032_2_, p_77032_3_);
	}
	
	public void doRender(EntityMiniMobZombie p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		this.func_82427_a(p_76986_1_);
		super.doRender((EntityLiving) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}
	
	protected ResourceLocation getEntityTexture(EntityMiniMobZombie entity)
	{
		return zombieTextures;
	}
	
	protected void renderEquippedItems(EntityMiniMobZombie p_77029_1_, float p_77029_2_)
	{
		this.func_82427_a(p_77029_1_);
		this.renderEquippedItems((EntityLiving) p_77029_1_, p_77029_2_);
	}
	
	private void func_82427_a(EntityMiniMobZombie p_82427_1_)
	{
		this.mainModel = this.field_82434_o;
		
		this.modelBipedMain = (ModelBiped) this.mainModel;
	}
	
	protected void rotateCorpse(EntityMiniMobZombie p_77043_1_, float p_77043_2_, float p_77043_3_, float p_77043_4_)
	{
		super.rotateCorpse(p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
	}
	/*
	protected void renderEquippedItems(EntityLiving p_77029_1_, float p_77029_2_)
	{
		this.renderEquippedItems((EntityMiniMobZombie) p_77029_1_, p_77029_2_);
	}
	*/
	protected ResourceLocation getEntityTexture(EntityLiving p_110775_1_)
	{
		return this.getEntityTexture((EntityMiniMobZombie) p_110775_1_);
	}
	
	public void doRender(EntityLiving p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		this.doRender((EntityMiniMobZombie) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}
	
	protected int shouldRenderPass(EntityLiving p_77032_1_, int p_77032_2_, float p_77032_3_)
	{
		return this.shouldRenderPass((EntityMiniMobZombie) p_77032_1_, p_77032_2_, p_77032_3_);
	}
	
	protected int shouldRenderPass(EntityLivingBase p_77032_1_, int p_77032_2_, float p_77032_3_)
	{
		return this.shouldRenderPass((EntityMiniMobZombie) p_77032_1_, p_77032_2_, p_77032_3_);
	}
	
	protected void renderEquippedItems(EntityLivingBase p_77029_1_, float p_77029_2_)
	{
		this.renderEquippedItems((EntityMiniMobZombie) p_77029_1_, p_77029_2_);
	}
	
	protected void rotateCorpse(EntityLivingBase p_77043_1_, float p_77043_2_, float p_77043_3_, float p_77043_4_)
	{
		this.rotateCorpse((EntityMiniMobZombie) p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
	}
	
	public void doRender(EntityLivingBase p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		this.doRender((EntityMiniMobZombie) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}
	
	protected ResourceLocation getEntityTexture(Entity p_110775_1_)
	{
		return this.getEntityTexture((EntityMiniMobZombie) p_110775_1_);
	}
	
	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		this.doRender((EntityMiniMobZombie) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}
	
	protected void renderEquippedItems(EntityLiving p_77029_1_, float p_77029_2_)
	{
		GL11.glColor3f(1.0F, 1.0F, 1.0F);
		ItemStack itemstack = p_77029_1_.getHeldItem();
		ItemStack itemstack1 = p_77029_1_.func_130225_q(3);
		Item item;
		float f1;
		
		if (itemstack1 != null)
		{
			GL11.glPushMatrix();
			this.modelBipedMain.bipedHead.postRender(0.0625F);
			item = itemstack1.getItem();
			
			net.minecraftforge.client.IItemRenderer customRenderer = net.minecraftforge.client.MinecraftForgeClient
					.getItemRenderer(
							itemstack1,
							net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED);
			boolean is3D = (customRenderer != null && customRenderer
					.shouldUseRenderHelper(
							net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED,
							itemstack1,
							net.minecraftforge.client.IItemRenderer.ItemRendererHelper.BLOCK_3D));
			
			if (item instanceof ItemBlock)
			{
				if (is3D
						|| RenderBlocks.renderItemIn3d(Block.getBlockFromItem(
								item).getRenderType()))
				{
					f1 = 0.625F;
					GL11.glTranslatef(0.0F, -0.25F, 0.0F);
					GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
					GL11.glScalef(f1, -f1, -f1);
				}
				
				this.renderManager.itemRenderer.renderItem(p_77029_1_,
						itemstack1, 0);
			} else if (item == Items.skull)
			{
				f1 = 1.0625F;
				GL11.glScalef(f1, -f1, -f1);
				GameProfile gameprofile = null;
				
				if (itemstack1.hasTagCompound())
				{
					NBTTagCompound nbttagcompound = itemstack1.getTagCompound();
					
					if (nbttagcompound.hasKey("SkullOwner", 10))
					{
						gameprofile = NBTUtil.func_152459_a(nbttagcompound
								.getCompoundTag("SkullOwner"));
					} else if (nbttagcompound.hasKey("SkullOwner", 8)
							&& !StringUtils.isNullOrEmpty(nbttagcompound
									.getString("SkullOwner")))
					{
						gameprofile = new GameProfile((UUID) null,
								nbttagcompound.getString("SkullOwner"));
					}
				}
				
				TileEntitySkullRenderer.field_147536_b.func_152674_a(-0.5F,
						0.0F, -0.5F, 1, 180.0F, itemstack1.getItemDamage(),
						gameprofile);
			}
			
			GL11.glPopMatrix();
		}
		
		if (itemstack != null && itemstack.getItem() != null)
		{
			item = itemstack.getItem();
			GL11.glPushMatrix();
			
			if (this.mainModel.isChild)
			{
				f1 = 0.4F;
				GL11.glTranslatef(0.125F, 0.925F, 0.0F);
				GL11.glRotatef(-20.0F, -1.0F, 0.0F, 0.0F);
				GL11.glScalef(f1, f1, f1);
			}
			
			this.modelBipedMain.bipedRightArm.postRender(0.0625F);
			GL11.glTranslatef(-0.0625F, 0.4375F, 0.0625F);
			
			net.minecraftforge.client.IItemRenderer customRenderer = net.minecraftforge.client.MinecraftForgeClient
					.getItemRenderer(
							itemstack,
							net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED);
			boolean is3D = (customRenderer != null && customRenderer
					.shouldUseRenderHelper(
							net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED,
							itemstack,
							net.minecraftforge.client.IItemRenderer.ItemRendererHelper.BLOCK_3D));
			
			if (item instanceof ItemBlock
					&& (is3D || RenderBlocks.renderItemIn3d(Block
							.getBlockFromItem(item).getRenderType())))
			{
				f1 = 0.5F;
				GL11.glTranslatef(0.0F, 0.1875F, -0.3125F);
				f1 *= 0.75F;
				GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
				GL11.glScalef(-f1, -f1, f1);
			} else if (item == Items.bow)
			{
				f1 = 0.625F;
				GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
				GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
				GL11.glScalef(f1, -f1, f1);
				GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
			} else if (item.isFull3D())
			{
				f1 = 0.625F;
				
				if (item.shouldRotateAroundWhenRendering())
				{
					GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
					GL11.glTranslatef(0.0F, -0.125F, 0.0F);
				}
				
				this.func_82422_c();
				GL11.glScalef(f1, -f1, f1);
				GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
			} else
			{
				f1 = 0.375F;
				GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
				GL11.glScalef(f1, f1, f1);
				GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
				GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
			}
			
			float f2;
			int i;
			float f5;
			
			if (itemstack.getItem().requiresMultipleRenderPasses())
			{
				for (i = 0; i < itemstack.getItem().getRenderPasses(
						itemstack.getItemDamage()); ++i)
				{
					int j = itemstack.getItem().getColorFromItemStack(
							itemstack, i);
					f5 = (float) (j >> 16 & 255) / 255.0F;
					f2 = (float) (j >> 8 & 255) / 255.0F;
					float f3 = (float) (j & 255) / 255.0F;
					GL11.glColor4f(f5, f2, f3, 1.0F);
					this.renderManager.itemRenderer.renderItem(p_77029_1_,
							itemstack, i);
				}
			} else
			{
				i = itemstack.getItem().getColorFromItemStack(itemstack, 0);
				float f4 = (float) (i >> 16 & 255) / 255.0F;
				f5 = (float) (i >> 8 & 255) / 255.0F;
				f2 = (float) (i & 255) / 255.0F;
				GL11.glColor4f(f4, f5, f2, 1.0F);
				this.renderManager.itemRenderer.renderItem(p_77029_1_,
						itemstack, 0);
			}
			
			GL11.glPopMatrix();
		}
	}
}
