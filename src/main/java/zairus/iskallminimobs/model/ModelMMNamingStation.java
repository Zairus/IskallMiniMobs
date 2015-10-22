package zairus.iskallminimobs.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelMMNamingStation
	extends ModelBase
{
	ModelRenderer Base;
	ModelRenderer Base2;
	ModelRenderer Support1;
	ModelRenderer Surface1;
	ModelRenderer Surface2;
	ModelRenderer Plate;
	ModelRenderer Pot1;
	ModelRenderer Pen1;
	ModelRenderer Can1;
	ModelRenderer Can2;
	ModelRenderer Can3;
	ModelRenderer Can4;
	ModelRenderer Back1;
	ModelRenderer Back2;
	ModelRenderer Back3;
	
	public ModelMMNamingStation()
	{
		textureWidth = 128;
		textureHeight = 128;
		
		Base = new ModelRenderer(this, 0, 0);
		Base.addBox(0F, 0F, 0F, 16, 2, 16);
		Base.setRotationPoint(-8F, 22F, -8F);
		Base.setTextureSize(128, 128);
		Base.mirror = true;
		setRotation(Base, 0F, 0F, 0F);
		
		Base2 = new ModelRenderer(this, 0, 18);
		Base2.addBox(0F, 0F, 0F, 16, 2, 8);
		Base2.setRotationPoint(-8F, 20F, -4F);
		Base2.setTextureSize(128, 128);
		Base2.mirror = true;
		setRotation(Base2, 0F, 0F, 0F);
		
		Support1 = new ModelRenderer(this, 0, 28);
		Support1.addBox(0F, 0F, 0F, 14, 4, 2);
		Support1.setRotationPoint(-7F, 16F, -1F);
		Support1.setTextureSize(128, 128);
		Support1.mirror = true;
		setRotation(Support1, 0F, 0F, 0F);
		
		Surface1 = new ModelRenderer(this, 0, 34);
		Surface1.addBox(0F, 0F, 0F, 14, 4, 14);
		Surface1.setRotationPoint(-7F, 12F, -7F);
		Surface1.setTextureSize(128, 128);
		Surface1.mirror = true;
		setRotation(Surface1, 0F, 0F, 0F);
		
		Surface2 = new ModelRenderer(this, 0, 52);
		Surface2.addBox(0F, 0F, 0F, 16, 1, 16);
		Surface2.setRotationPoint(-8F, 11F, -8F);
		Surface2.setTextureSize(128, 128);
		Surface2.mirror = true;
		setRotation(Surface2, 0F, 0F, 0F);
		
		Plate = new ModelRenderer(this, 0, 69);
		Plate.addBox(0F, 0F, 0F, 8, 1, 3);
		Plate.setRotationPoint(-4F, 10F, 2F);
		Plate.setTextureSize(128, 128);
		Plate.mirror = true;
		setRotation(Plate, 0F, 0F, 0F);
		
		Pot1 = new ModelRenderer(this, 22, 69);
		Pot1.addBox(0F, 0F, 0F, 2, 2, 2);
		Pot1.setRotationPoint(-6F, 9F, -7F);
		Pot1.setTextureSize(128, 128);
		Pot1.mirror = true;
		setRotation(Pot1, 0F, 0F, 0F);
		
		Pen1 = new ModelRenderer(this, 30, 69);
		Pen1.addBox(0F, 0F, 0F, 1, 1, 1);
		Pen1.setRotationPoint(-5.5F, 8F, -6.5F);
		Pen1.setTextureSize(128, 128);
		Pen1.mirror = true;
		setRotation(Pen1, 0F, 0F, 0F);
		
		Can1 = new ModelRenderer(this, 64, 0);
		Can1.addBox(0F, 0F, 0F, 3, 4, 3);
		Can1.setRotationPoint(-8F, 18F, -7F);
		Can1.setTextureSize(128, 128);
		Can1.mirror = true;
		setRotation(Can1, 0F, 0F, 0F);
		
		Can2 = new ModelRenderer(this, 76, 7);
		Can2.addBox(0F, 0F, 0F, 3, 4, 3);
		Can2.setRotationPoint(5F, 18F, -7F);
		Can2.setTextureSize(128, 128);
		Can2.mirror = true;
		setRotation(Can2, 0F, 0F, 0F);
		
		Can3 = new ModelRenderer(this, 64, 7);
		Can3.addBox(0F, 0F, 0F, 3, 4, 3);
		Can3.setRotationPoint(1F, 18F, -7F);
		Can3.setTextureSize(128, 128);
		Can3.mirror = true;
		setRotation(Can3, 0F, 0F, 0F);
		
		Can4 = new ModelRenderer(this, 76, 0);
		Can4.addBox(-4F, 0F, 0F, 3, 4, 3);
		Can4.setRotationPoint(0F, 18F, -7F);
		Can4.setTextureSize(128, 128);
		Can4.mirror = true;
		setRotation(Can4, 0F, 0F, 0F);
		
		Back1 = new ModelRenderer(this, 64, 14);
		Back1.addBox(0F, 0F, 0F, 3, 8, 1);
		Back1.setRotationPoint(3F, 12F, 7F);
		Back1.setTextureSize(128, 128);
		Back1.mirror = true;
		setRotation(Back1, 0F, 0F, 0F);
		
		Back2 = new ModelRenderer(this, 72, 14);
		Back2.addBox(0F, 0F, 0F, 3, 8, 1);
		Back2.setRotationPoint(-1.5F, 12F, 7F);
		Back2.setTextureSize(128, 128);
		Back2.mirror = true;
		setRotation(Back2, 0F, 0F, 0F);
		
		Back3 = new ModelRenderer(this, 80, 14);
		Back3.addBox(0F, 0F, 0F, 3, 8, 1);
		Back3.setRotationPoint(-6F, 12F, 7F);
		Back3.setTextureSize(128, 128);
		Back3.mirror = true;
		setRotation(Back3, 0F, 0F, 0F);
	}
	
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		
		Base.render(f5);
		Base2.render(f5);
		Support1.render(f5);
		Surface1.render(f5);
		Surface2.render(f5);
		Plate.render(f5);
		Pot1.render(f5);
		Pen1.render(f5);
		Can1.render(f5);
		Can2.render(f5);
		Can3.render(f5);
		Can4.render(f5);
		Back1.render(f5);
		Back2.render(f5);
		Back3.render(f5);
	}
	
	public void renderModel(float f5)
	{
		Base.render(f5);
		Base2.render(f5);
		Support1.render(f5);
		Surface1.render(f5);
		Surface2.render(f5);
		Plate.render(f5);
		Pot1.render(f5);
		Pen1.render(f5);
		Can1.render(f5);
		Can2.render(f5);
		Can3.render(f5);
		Can4.render(f5);
		Back1.render(f5);
		Back2.render(f5);
		Back3.render(f5);
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
