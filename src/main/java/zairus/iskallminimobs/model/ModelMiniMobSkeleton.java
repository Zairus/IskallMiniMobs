package zairus.iskallminimobs.model;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import zairus.iskallminimobs.entity.minimob.EntityMiniMobSkeleton;

public class ModelMiniMobSkeleton
	extends ModelZombie
{
	public ModelMiniMobSkeleton()
	{
		this(0.0F);
	}
	
	public ModelMiniMobSkeleton(float p_i1156_1_)
	{
		super(p_i1156_1_, 0.0F, 64, 32);
		this.bipedRightArm = new ModelRenderer(this, 40, 16);
		this.bipedRightArm.addBox(-1.0F, -2.0F, -1.0F, 2, 12, 2, p_i1156_1_);
		this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
		this.bipedLeftArm = new ModelRenderer(this, 40, 16);
		this.bipedLeftArm.mirror = true;
		this.bipedLeftArm.addBox(-1.0F, -2.0F, -1.0F, 2, 12, 2, p_i1156_1_);
		this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
		this.bipedRightLeg = new ModelRenderer(this, 0, 16);
		this.bipedRightLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 2, p_i1156_1_);
		this.bipedRightLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);
		this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
		this.bipedLeftLeg.mirror = true;
		this.bipedLeftLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 2, p_i1156_1_);
		this.bipedLeftLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
	}
	
	public void setLivingAnimations(EntityLivingBase p_78086_1_, float p_78086_2_, float p_78086_3_, float p_78086_4_)
	{
		this.aimedBow = ((EntityMiniMobSkeleton) p_78086_1_).getSkeletonType() == 1;
		super.setLivingAnimations(p_78086_1_, p_78086_2_, p_78086_3_, p_78086_4_);
	}
	
	public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_)
	{
		super.setRotationAngles(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_);
	}
}
