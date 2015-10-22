package zairus.iskallminimobs.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelMMPowerProvider
	extends ModelBase
{
	ModelRenderer MainBase;
	ModelRenderer Connector1;
	ModelRenderer Connector2;
	ModelRenderer Cap;
	ModelRenderer Connector3;
	ModelRenderer Connector4;
	
	public ModelMMPowerProvider()
	{
		textureWidth = 64;
		textureHeight = 64;
		
		MainBase = new ModelRenderer(this, 0, 0);
		MainBase.addBox(0F, 0F, 0F, 8, 14, 8);
		MainBase.setRotationPoint(-4F, 10F, -4F);
		MainBase.setTextureSize(64, 64);
		MainBase.mirror = true;
		setRotation(MainBase, 0F, 0F, 0F);
		
		Connector1 = new ModelRenderer(this, 0, 22);
		Connector1.addBox(0F, 0F, 0F, 8, 3, 4);
		Connector1.setRotationPoint(-4F, 21F, -8F);
		Connector1.setTextureSize(64, 64);
		Connector1.mirror = true;
		setRotation(Connector1, 0F, 0F, 0F);
		
		Connector2 = new ModelRenderer(this, 0, 22);
		Connector2.addBox(0F, 0F, 0F, 8, 3, 4);
		Connector2.setRotationPoint(-4F, 21F, 4F);
		Connector2.setTextureSize(64, 64);
		Connector2.mirror = true;
		setRotation(Connector2, 0F, 0F, 0F);
		
		Cap = new ModelRenderer(this, 0, 29);
		Cap.addBox(0F, 0F, 0F, 6, 2, 6);
		Cap.setRotationPoint(-3F, 8F, -3F);
		Cap.setTextureSize(64, 64);
		Cap.mirror = true;
		setRotation(Cap, 0F, 0F, 0F);
		
		Connector3 = new ModelRenderer(this, 0, 22);
		Connector3.addBox(0F, 0F, 0F, 8, 3, 4);
		Connector3.setRotationPoint(4F, 21F, 4F);
		Connector3.setTextureSize(64, 64);
		Connector3.mirror = true;
		setRotation(Connector3, 0F, 1.570796F, 0F);
		
		Connector4 = new ModelRenderer(this, 0, 22);
		Connector4.addBox(0F, 0F, 0F, 8, 3, 4);
		Connector4.setRotationPoint(-8F, 21F, 4F);
		Connector4.setTextureSize(64, 64);
		Connector4.mirror = true;
		setRotation(Connector4, 0F, 1.570796F, 0F);
	}
	
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		
		MainBase.render(f5);
		Connector1.render(f5);
		Connector2.render(f5);
		Connector3.render(f5);
		Connector4.render(f5);
		
		GL11.glEnable(GL11.GL_BLEND);
		Cap.render(f5);
		GL11.glDisable(GL11.GL_BLEND);
	}
	
	public void renderModel(float f5)
	{
		MainBase.render(f5);
		Connector1.render(f5);
		Connector2.render(f5);
		Connector3.render(f5);
		Connector4.render(f5);
		
		GL11.glEnable(GL11.GL_BLEND);
		Cap.render(f5);
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
