package zairus.iskallminimobs.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelMiniMobZombie extends ModelBiped
{
	public ModelMiniMobZombie()
	{
		this(0.0F, false);
	}
	
	protected ModelMiniMobZombie(float p_i1167_1_, float p_i1167_2_, int p_i1167_3_, int p_i1167_4_)
	{
		super(p_i1167_1_, p_i1167_2_, p_i1167_3_, p_i1167_4_);
	}
	
	public ModelMiniMobZombie(float p_i1168_1_, boolean p_i1168_2_)
	{
		super(p_i1168_1_, 0.0F, 64, p_i1168_2_ ? 32 : 64);
	}
	
	public void render(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_)
	{
		this.setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
		
		if (this.isChild)
		{
			float f6 = 3.5F;
			GL11.glPushMatrix();
			GL11.glScalef(1.5F / f6, 1.5F / f6, 1.5F / f6);
			GL11.glTranslatef(0.0F, (16.0F * p_78088_7_) + 1.4F, 0.0F);
			this.bipedHead.render(p_78088_7_);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glScalef(1.0F / f6, 1.0F / f6, 1.0F / f6);
			GL11.glTranslatef(0.0F, (24.0F * p_78088_7_) + 2.1F, 0.0F);
			this.bipedBody.render(p_78088_7_);
			this.bipedRightArm.render(p_78088_7_);
			this.bipedLeftArm.render(p_78088_7_);
			this.bipedRightLeg.render(p_78088_7_);
			this.bipedLeftLeg.render(p_78088_7_);
			this.bipedHeadwear.render(p_78088_7_);
			GL11.glPopMatrix();
		} else
		{
			this.bipedHead.render(p_78088_7_);
			this.bipedBody.render(p_78088_7_);
			this.bipedRightArm.render(p_78088_7_);
			this.bipedLeftArm.render(p_78088_7_);
			this.bipedRightLeg.render(p_78088_7_);
			this.bipedLeftLeg.render(p_78088_7_);
			this.bipedHeadwear.render(p_78088_7_);
		}
	}
	
	public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_)
	{
		super.setRotationAngles(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_);
		
		float f6 = MathHelper.sin(this.onGround * (float) Math.PI);
		float f7 = MathHelper.sin((1.0F - (1.0F - this.onGround)
				* (1.0F - this.onGround))
				* (float) Math.PI);
		
		this.bipedRightArm.rotateAngleZ = 0.0F;
		this.bipedLeftArm.rotateAngleZ = 0.0F;
		this.bipedRightArm.rotateAngleY = -(0.1F - f6 * 0.6F);
		this.bipedLeftArm.rotateAngleY = 0.1F - f6 * 0.6F;
		this.bipedRightArm.rotateAngleX = -((float) Math.PI / 2F);
		this.bipedLeftArm.rotateAngleX = -((float) Math.PI / 2F);
		this.bipedRightArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
		this.bipedLeftArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
		this.bipedRightArm.rotateAngleZ += MathHelper.cos(p_78087_3_ * 0.09F) * 0.05F + 0.05F;
		this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(p_78087_3_ * 0.09F) * 0.05F + 0.05F;
		this.bipedRightArm.rotateAngleX += MathHelper.sin(p_78087_3_ * 0.067F) * 0.05F;
		this.bipedLeftArm.rotateAngleX -= MathHelper.sin(p_78087_3_ * 0.067F) * 0.05F;
	}
}
