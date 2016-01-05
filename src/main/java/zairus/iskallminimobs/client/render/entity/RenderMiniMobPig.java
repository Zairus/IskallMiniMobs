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
	
	protected int shouldRenderPass(EntityMiniMobPig miniMobPig, int type, float frame)
	{
		return -1;
	}
	
	protected ResourceLocation getEntityTexture(EntityMiniMobPig miniMobPig)
	{
		return pigTextures;
	}
	
	@Override
	protected int shouldRenderPass(EntityLivingBase entity, int type, float frame)
	{
		return this.shouldRenderPass((EntityMiniMobPig)entity, type, frame);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return this.getEntityTexture((EntityMiniMobPig)entity);
	}
}
