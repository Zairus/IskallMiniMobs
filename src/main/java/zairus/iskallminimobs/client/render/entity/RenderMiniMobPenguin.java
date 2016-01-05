package zairus.iskallminimobs.client.render.entity;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import zairus.iskallminimobs.MMConstants;
import zairus.iskallminimobs.entity.minimob.EntityMiniMobPenguin;
import zairus.iskallminimobs.model.ModelMiniMobPenguin;

public class RenderMiniMobPenguin
	extends RenderLiving
{
	private static final ResourceLocation penguinTextures = new ResourceLocation(MMConstants.MODID, "textures/entity/mm_penguin.png");
	
	public RenderMiniMobPenguin()
	{
		super(new ModelMiniMobPenguin(), 0.25F);
	}
	
	protected void preRenderCallback(EntityMiniMobPenguin entity, float time)
	{
		GL11.glScalef(0.5F, 0.5F, 0.5F);
	}
	
	protected int getColorMultiplier(EntityMiniMobPenguin entity, float brightness, float frame)
	{
		float f2 = 1.0F;
		
		if ((int) (f2 * 10.0F) % 2 == 0)
		{
			return 0;
		} else
		{
			int i = (int) (f2 * 0.2F * 255.0F);
			
			if (i < 0)
			{
				i = 0;
			}
			
			if (i > 255)
			{
				i = 255;
			}
			
			short short1 = 255;
			short short2 = 255;
			short short3 = 255;
			return i << 24 | short1 << 16 | short2 << 8 | short3;
		}
	}
	
	protected int shouldRenderPass(EntityMiniMobPenguin entity, int type, float frame)
	{
		return -1;
	}
	
	protected int inheritRenderPass(EntityMiniMobPenguin entity, int i1, float f1)
	{
		return -1;
	}
	
	protected ResourceLocation getEntityTexture(EntityMiniMobPenguin entity)
	{
		return penguinTextures;
	}
	
	@Override
	protected void preRenderCallback(EntityLivingBase entity, float f1)
	{
		preRenderCallback((EntityMiniMobPenguin) entity, f1);
	}
	
	@Override
	protected int getColorMultiplier(EntityLivingBase entity, float f1, float f2)
	{
		return getColorMultiplier((EntityMiniMobPenguin) entity, f1, f2);
	}
	
	@Override
	protected int shouldRenderPass(EntityLivingBase entity, int i1, float f1)
	{
		return shouldRenderPass((EntityMiniMobPenguin) entity, i1, f1);
	}
	
	@Override
	protected int inheritRenderPass(EntityLivingBase entity, int i1, float f1)
	{
		return inheritRenderPass((EntityMiniMobPenguin) entity, i1, f1);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return getEntityTexture((EntityMiniMobPenguin) entity);
	}
}
