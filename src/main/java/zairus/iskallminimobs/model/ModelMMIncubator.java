package zairus.iskallminimobs.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelMMIncubator
	extends ModelBase
{
	ModelRenderer Frame;
	ModelRenderer Encasing_Back;
	ModelRenderer Encasing_Front;
	ModelRenderer Encasing_Right;
	ModelRenderer Encasing_Left;
	ModelRenderer Cover;
	
	public ModelMMIncubator()
	{
		textureWidth = 64;
		textureHeight = 64;
		
		Frame = new ModelRenderer(this, 0, 38);
		Frame.mirror = false;
		Frame.addBox(0F, 0F, 0F, 16, 10, 16);
		Frame.setRotationPoint(-8F, 14F, -8F);
		Frame.setTextureSize(64, 64);
		setRotation(Frame, 0F, 0F, 0F);
		
		Encasing_Back = new ModelRenderer(this, 0, 20);
		Encasing_Back.mirror = false;
		Encasing_Back.addBox(0F, 0F, 0F, 16, 2, 1);
		Encasing_Back.setRotationPoint(-8F, 12F, 7F);
		Encasing_Back.setTextureSize(64, 64);
		setRotation(Encasing_Back, 0F, 0F, 0F);
		
		Encasing_Front = new ModelRenderer(this, 0, 20);
		Encasing_Front.mirror = false;
		Encasing_Front.addBox(0F, 0F, 0F, 16, 2, 1);
		Encasing_Front.setRotationPoint(-8F, 12F, -8F);
		Encasing_Front.setTextureSize(64, 64);
		setRotation(Encasing_Front, 0F, 0F, 0F);
		
		Encasing_Right = new ModelRenderer(this, 0, 23);
		Encasing_Right.mirror = false;
		Encasing_Right.addBox(0F, 0F, 0F, 14, 2, 1);
		Encasing_Right.setRotationPoint(-7F, 12F, -7F);
		Encasing_Right.setTextureSize(64, 64);
		setRotation(Encasing_Right, 0F, -1.570796F, 0F);
		
		Encasing_Left = new ModelRenderer(this, 0, 23);
		Encasing_Left.mirror = false;
		Encasing_Left.addBox(0F, 0F, 0F, 14, 2, 1);
		Encasing_Left.setRotationPoint(8F, 12F, -7F);
		Encasing_Left.setTextureSize(64, 64);
		setRotation(Encasing_Left, 0F, -1.570796F, 0F);
		
		Cover = new ModelRenderer(this, 0, 0);
		Cover.mirror = false;
		Cover.addBox(0F, 0F, 0F, 16, 4, 16);
		Cover.setRotationPoint(-8F, 8F, -8F);
		Cover.setTextureSize(64, 64);
		setRotation(Cover, 0F, 0F, 0F);
	}
	
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		
		Frame.render(f5);
		Encasing_Back.render(f5);
		Encasing_Front.render(f5);
		Encasing_Right.render(f5);
		Encasing_Left.render(f5);
		
		GL11.glEnable(GL11.GL_BLEND);
		Cover.render(f5);
		GL11.glDisable(GL11.GL_BLEND);
	}
	
	public void renderModel(float f5)
	{
		Frame.render(f5);
		Encasing_Back.render(f5);
		Encasing_Front.render(f5);
		Encasing_Right.render(f5);
		Encasing_Left.render(f5);
		
		GL11.glEnable(GL11.GL_BLEND);
		Cover.render(f5);
	}
	
	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
	
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
	{
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}
}
