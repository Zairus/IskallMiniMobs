package zairus.iskallminimobs.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelQuadruped;
import net.minecraft.entity.Entity;

public class ModelMiniMobPig
	extends ModelQuadruped
{
	public ModelMiniMobPig()
	{
		this(0.0F);
	}
	
	public ModelMiniMobPig(float p_i1151_1_)
	{
		super(6, p_i1151_1_);
		this.head.setTextureOffset(16, 16).addBox(-2.0F, 0.0F, -9.0F, 4, 3, 1, p_i1151_1_);
        this.field_78145_g = 4.0F;
	}
	
	@Override
	public void render(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_)
	{
		float f6 = 3.5F;
		GL11.glScalef(1.5F / f6, 1.5F / f6, 1.5F / f6);
		GL11.glTranslatef(0.0F, (16.0F * p_78088_7_) + 1.1F, 0.0F);
		
		super.render(p_78088_1_, p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_);
	}
}
