package zairus.iskallminimobs.client.render.entity;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import zairus.iskallminimobs.entity.minimob.EntityMiniMobPig;
import zairus.iskallminimobs.model.ModelMiniMobPig;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderMiniMobPig
	extends RenderLiving
{
	private static final ResourceLocation pigTextures = new ResourceLocation("textures/entity/pig/pig.png");
	
	public RenderMiniMobPig()
	{
		super(new ModelMiniMobPig(0.3F), 0.3F);
	}
	
	protected int shouldRenderPass(EntityMiniMobPig miniMobPog, int p_77032_2_, float p_77032_3_)
	{
		return -1;
	}
	
	protected ResourceLocation getEntityTexture(EntityMiniMobPig miniMobPig)
	{
		return pigTextures;
	}
	
	protected int shouldRenderPass(EntityLivingBase entity, int p_77032_2_, float p_77032_3_)
	{
		return this.shouldRenderPass((EntityMiniMobPig)entity, p_77032_2_, p_77032_3_);
	}
	
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return this.getEntityTexture((EntityMiniMobPig)entity);
	}
}
