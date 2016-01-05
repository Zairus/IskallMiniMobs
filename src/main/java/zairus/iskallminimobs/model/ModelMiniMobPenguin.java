package zairus.iskallminimobs.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import zairus.iskallminimobs.entity.minimob.EntityMiniMobPenguin;

public class ModelMiniMobPenguin
	extends ModelBase
{
	public ModelRenderer head;
	public ModelRenderer peak;
	public ModelRenderer body;
	public ModelRenderer rightarm;
	public ModelRenderer leftarm;
	public ModelRenderer rightleg;
	public ModelRenderer leftleg;
	
	public ModelMiniMobPenguin()
	{
		textureWidth = 64;
		textureHeight = 32;
		
		head = new ModelRenderer(this, 0, 0);
		head.addBox(-2F, -3F, -3F, 4, 4, 4);
		head.setRotationPoint(0F, 11F, 0F);
		head.setTextureSize(64, 32);
		head.mirror = true;
		setRotation(head, 0F, 0F, 0F);
		
		peak = new ModelRenderer(this, 16, 0);
		peak.addBox(-1F, 0F, -5F, 2, 1, 2);
		peak.setRotationPoint(0F, 11F, 0F);
		peak.setTextureSize(64, 32);
		peak.mirror = true;
		setRotation(peak, 0F, 0F, 0F);
		
		body = new ModelRenderer(this, 0, 16);
		body.addBox(-4F, 0F, -2F, 8, 10, 5);
		body.setRotationPoint(0F, 12F, 0F);
		body.setTextureSize(64, 32);
		body.mirror = true;
		setRotation(body, 0F, 0F, 0F);
		
		rightarm = new ModelRenderer(this, 20, 5);
		rightarm.addBox(-1F, -1F, -2F, 1, 7, 4);
		rightarm.setRotationPoint(-4F, 13F, 0F);
		rightarm.setTextureSize(64, 32);
		rightarm.mirror = true;
		setRotation(rightarm, 0F, 0F, 0F);
		
		leftarm = new ModelRenderer(this, 20, 5);
		leftarm.addBox(0F, -1F, -2F, 1, 7, 4);
		leftarm.setRotationPoint(4F, 13F, 0F);
		leftarm.setTextureSize(64, 32);
		leftarm.mirror = true;
		setRotation(leftarm, 0F, 0F, 0F);
		
		rightleg = new ModelRenderer(this, 0, 8);
		rightleg.addBox(-2F, 0F, -4F, 4, 2, 6);
		rightleg.setRotationPoint(-2F, 22F, -1F);
		rightleg.setTextureSize(64, 32);
		rightleg.mirror = true;
		setRotation(rightleg, 0F, 0F, 0F);
		
		leftleg = new ModelRenderer(this, 0, 8);
		leftleg.addBox(-2F, 0F, -4F, 4, 2, 6);
		leftleg.setRotationPoint(2F, 22F, -1F);
		leftleg.setTextureSize(64, 32);
		leftleg.mirror = true;
		setRotation(leftleg, 0F, 0F, 0F);
	}
	
	public void render(Entity entity, float time, float swing, float f2, float f3, float f4, float f5)
	{
		super.render(entity, time, swing, f2, f3, f4, f5);
		setRotationAngles(time, swing, f2, f3, f4, f5, entity);
		
		head.render(f5);
		body.render(f5);
		rightarm.render(f5);
		leftarm.render(f5);
		rightleg.render(f5);
		leftleg.render(f5);
		peak.render(f5);
	}
	
	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
	
	public void setRotationAngles(float time, float swingDist, float f2, float f3, float f4, float f5, Entity entity)
	{
		float angle = (MathHelper.cos(time * 0.6662F) * 0.3F * swingDist);
		
		head.setRotationPoint(0F, 11F, 0F);
		peak.setRotationPoint(0F, 11F, 0F);
		body.setRotationPoint(0F, 12F, 0F);
		rightarm.setRotationPoint(-4F, 13F, 0F);
		leftarm.setRotationPoint(4F, 13F, 0F);
		rightleg.setRotationPoint(-2F, 22F, -1F);
		leftleg.setRotationPoint(2F, 22F, -1F);
		
		head.rotateAngleX = f4 / (180F / (float)Math.PI);
		head.rotateAngleY = f3 / (180F / (float)Math.PI);
		head.rotateAngleZ = 0.0F;
		peak.rotateAngleX = this.head.rotateAngleX;
		peak.rotateAngleY = this.head.rotateAngleY;
		peak.rotateAngleZ = this.head.rotateAngleZ;
		
		body.rotateAngleX = 0.0F;
		body.rotateAngleY = 0.0F;
		body.rotateAngleZ = 0.0F;
		
		leftarm.rotateAngleX = 0.0F;
		leftarm.rotateAngleY = 0.0F;
		leftarm.rotateAngleZ = ((MathHelper.abs(MathHelper.cos(time * 0.6662F)) * 1.1F * swingDist) * -1);
		
		rightarm.rotateAngleX = 0.0F;
		rightarm.rotateAngleY = 0.0F;
		rightarm.rotateAngleZ = (MathHelper.abs(MathHelper.cos(time * 0.6662F)) * 1.1F * swingDist);
		
		leftleg.rotateAngleX = MathHelper.cos(time * 0.6662F) * 1.4F * swingDist;
		leftleg.rotateAngleY = 0.0F;
		leftleg.rotateAngleZ = 0.0F;
		
		rightleg.rotateAngleX = MathHelper.cos(time * 0.6662F + (float)Math.PI) * 1.4F * swingDist;
		rightleg.rotateAngleY = 0.0F;
		rightleg.rotateAngleZ = 0.0F;
		
		head.rotateAngleZ += angle;
		peak.rotateAngleZ += angle;
		body.rotateAngleZ += angle;
		rightarm.rotateAngleZ += angle;
		leftarm.rotateAngleZ += angle;
		rightleg.rotateAngleZ += angle;
		leftleg.rotateAngleZ += angle;
		
		if (entity instanceof EntityMiniMobPenguin)
		{
			if (((EntityMiniMobPenguin)entity).isOnIce())
			{
				head.offsetY = 0.5F;
				peak.offsetY = 0.5F;
				
				body.offsetY = 0.5F;
				body.rotateAngleX += 1.4F;
				
				rightarm.offsetY = 0.5F;
				rightarm.rotateAngleX += 1.2F;
				rightarm.rotateAngleZ += 1.2F;
				
				leftarm.offsetY = 0.5F;
				leftarm.rotateAngleX += 1.2F;
				leftarm.rotateAngleZ -= 1.2F;
				
				rightleg.offsetX = 0.0F;
				rightleg.offsetY = -0.0F;
				rightleg.offsetZ = 0.65F;
				rightleg.rotateAngleX += 1.4F;
				
				leftleg.offsetX = 0F;
				leftleg.offsetY = -0.0F;
				leftleg.offsetZ = 0.65F;
				leftleg.rotateAngleX += 1.4F;
			}
			else
			{
				head.offsetY = 0.0F;
				peak.offsetY = 0.0F;
				body.offsetY = 0.0F;
				rightarm.offsetY = 0.0F;
				leftarm.offsetY = 0.0F;
				rightleg.offsetX = 0.0F;
				rightleg.offsetY = 0.0F;
				rightleg.offsetZ = 0.0F;
				leftleg.offsetX = 0.0F;
				leftleg.offsetY = 0.0F;
				leftleg.offsetZ = 0.0F;
			}
		}
		else
		{
			head.offsetY = 0.0F;
			peak.offsetY = 0.0F;
			body.offsetY = 0.0F;
			rightarm.offsetY = 0.0F;
			leftarm.offsetY = 0.0F;
			rightleg.offsetX = 0.0F;
			rightleg.offsetY = 0.0F;
			rightleg.offsetZ = 0.0F;
			leftleg.offsetX = 0.0F;
			leftleg.offsetY = 0.0F;
			leftleg.offsetZ = 0.0F;
		}
	}
}
