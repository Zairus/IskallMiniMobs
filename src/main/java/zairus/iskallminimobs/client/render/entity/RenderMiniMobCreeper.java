package zairus.iskallminimobs.client.render.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import zairus.iskallminimobs.entity.minimob.EntityMiniMobCreeper;
import zairus.iskallminimobs.model.ModelMiniMobCreeper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderMiniMobCreeper
	extends RenderLiving
{
	private static final ResourceLocation creeperTextures = new ResourceLocation("textures/entity/creeper/creeper.png");
	private final ModelBase creeperModel;
	
	public RenderMiniMobCreeper()
	{
		super(new ModelMiniMobCreeper(0.5F), 0.25F);
		creeperModel = this.mainModel;
	}
	
	public void doRender(Entity entity, double d1, double d2, double d3, float f1, float f2)
	{
		super.doRender(entity, d1, d2, d3, f1, f2);
	}
	
	protected void preRenderCallback(EntityMiniMobCreeper entity, float frame)
	{
		float f1 = entity.getCreeperFlashIntensity(frame);
		float f2 = 1.0F + MathHelper.sin(f1 * 100.0F) * f1 * 0.01F;
		
		if (f1 < 0.0F)
		{
			f1 = 0.0F;
		}
		
		if (f1 > 1.0F)
		{
			f1 = 1.0F;
		}
		
		f1 *= f1;
		f1 *= f1;
		float f3 = (1.0F + f1 * 0.4F) * f2;
		float f4 = (1.0F + f1 * 0.1F) / f2;
		GL11.glScalef(f3, f4, f3);
	}
	
	protected int getColorMultiplier(EntityMiniMobCreeper entity, float brightness, float frame)
	{
		float f2 = entity.getCreeperFlashIntensity(frame);
		
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
	
	protected int shouldRenderPass(EntityMiniMobCreeper entity, int type, float frame)
	{
		if (entity.getPowered())
		{
			if (entity.isInvisible())
			{
				GL11.glDepthMask(false);
			} else
			{
				GL11.glDepthMask(true);
			}
			
			if (type == 1)
			{
				float f1 = (float) entity.ticksExisted + frame;
				this.bindTexture(creeperTextures);
				GL11.glMatrixMode(GL11.GL_TEXTURE);
				GL11.glLoadIdentity();
				float f2 = f1 * 0.01F;
				float f3 = f1 * 0.01F;
				GL11.glTranslatef(f2, f3, 0.0F);
				this.setRenderPassModel(this.creeperModel);
				GL11.glMatrixMode(GL11.GL_MODELVIEW);
				GL11.glEnable(GL11.GL_BLEND);
				float f4 = 0.5F;
				GL11.glColor4f(f4, f4, f4, 1.0F);
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
				return 1;
			}
			
			if (type == 2)
			{
				GL11.glMatrixMode(GL11.GL_TEXTURE);
				GL11.glLoadIdentity();
				GL11.glMatrixMode(GL11.GL_MODELVIEW);
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glDisable(GL11.GL_BLEND);
			}
		}
		
		return -1;
	}
	
	protected int inheritRenderPass(EntityMiniMobCreeper entity, int i1, float f1)
	{
		return -1;
	}
	
	protected ResourceLocation getEntityTexture(EntityMiniMobCreeper entity)
	{
		return creeperTextures;
	}
	
	@Override
	protected void preRenderCallback(EntityLivingBase entity, float f1)
	{
		this.preRenderCallback((EntityMiniMobCreeper) entity, f1);
	}
	
	@Override
	protected int getColorMultiplier(EntityLivingBase entity, float f1, float f2)
	{
		return this.getColorMultiplier((EntityMiniMobCreeper) entity, f1, f2);
	}
	
	@Override
	protected int shouldRenderPass(EntityLivingBase entity, int i1, float f1)
	{
		return this.shouldRenderPass((EntityMiniMobCreeper) entity, i1, f1);
	}
	
	@Override
	protected int inheritRenderPass(EntityLivingBase entity, int i1, float f1)
	{
		return this.inheritRenderPass((EntityMiniMobCreeper) entity, i1, f1);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return this.getEntityTexture((EntityMiniMobCreeper) entity);
	}
}
