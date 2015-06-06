package zairus.iskallminimobs.client.render.entity;

import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import zairus.iskallminimobs.entity.minimob.EntityMiniMobSkeleton;
import zairus.iskallminimobs.model.ModelMiniMobSkeleton;

@SideOnly(Side.CLIENT)
public class RenderMiniMobSkeleton
	extends RenderBiped
{
	private static final ResourceLocation skeletonTextures = new ResourceLocation("textures/entity/skeleton/skeleton.png");
	
	public RenderMiniMobSkeleton()
	{
		super(new ModelMiniMobSkeleton(), 0.5F);
	}
	
	protected void preRenderCallback(EntityMiniMobSkeleton skeleton, float p_77041_2_)
	{
		GL11.glScalef(0.5F, 0.5F, 0.5F);
	}
	
	protected void func_82422_c()
	{
		GL11.glTranslatef(0.09375F, 0.1875F, 0.0F);
	}
	
	protected ResourceLocation getEntityTexture(EntityMiniMobSkeleton skeleton)
	{
		return skeletonTextures;
	}
	
	protected ResourceLocation getEntityTexture(EntityLiving entity)
	{
		return this.getEntityTexture((EntityMiniMobSkeleton)entity);
	}
	
	protected void preRenderCallback(EntityLivingBase entity, float p_77041_2_)
	{
		this.preRenderCallback((EntityMiniMobSkeleton)entity, p_77041_2_);
	}
	
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return this.getEntityTexture((EntityMiniMobSkeleton)entity);
	}
}
