package zairus.iskallminimobs.tileentity;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import zairus.iskallminimobs.entity.minimob.MiniMobData;

public class TileEntityMMDNAExtractor
	extends TileEntityMMBase
{
	protected final int totalInventory = 7;
	public float progress = 0.0F;
	
	public int getTextureIndex()
	{
		return 1;
	}
	
	public boolean canProcess()
	{
		boolean canProcess = true;
		ItemStack corpse = this.getStackInSlot(4);
		ItemStack embryo = this.getStackInSlot(5);
		ItemStack result = this.getStackInSlot(6);
		
		if (result != null || corpse == null || embryo == null || (corpse != null && embryo != null && (embryo.getItemDamage() != corpse.getItemDamage())))
		{
			canProcess = false;
		}
		
		if (embryo != null)
		{
			if (embryo.hasTagCompound())
			{
				if (embryo.getTagCompound().hasKey(MiniMobData.MOBDATA_KEY))
					canProcess = false;
			}
		}
		
		return canProcess;
	}
	
	@Override
	public void updateEntity()
	{
		if (this.energyStored > 0 && this.canProcess())
		{
			ItemStack corpse = this.getStackInSlot(4);
			
			float value = this.consumeValuable(false);
			float value2 = this.consumeMobSource(corpse.getItemDamage(), false);
			
			if (value > 0.0F && value2 > 0.0F)
			{
				this.consumeValuable(true);
				this.consumeMobSource(corpse.getItemDamage(), true);
				
				progress += (((float)this.extractEnergy(ForgeDirection.UNKNOWN, 5, false)) / 10.0F) + value + value2;
				
				if (progress >= 100.0F)
				{
					ItemStack embryo = this.getStackInSlot(5);
					
					NBTTagCompound data = (NBTTagCompound)corpse.getTagCompound().getTag(MiniMobData.MOBDATA_KEY);
					
					data.removeTag(MiniMobData.INVENTORY_KEY);
					data.removeTag(MiniMobData.INVENTORY_EX_KEY);
					
					embryo.setTagCompound(new NBTTagCompound());
					embryo.getTagCompound().setTag(MiniMobData.MOBDATA_KEY, data);
					
					if (data.hasKey(MiniMobData.CUSTOMNAME_KEY))
					{
						embryo.setStackDisplayName(data.getString(MiniMobData.CUSTOMNAME_KEY));
					}
					
					this.setInventorySlotContents(6, embryo);
					this.setInventorySlotContents(5, null);
					this.setInventorySlotContents(4, null);
					
					progress = 0.0F;
				}
			}
		}
	}
	
	private float consumeMobSource(int mobType, boolean consume)
	{
		float value = 0.0F;
		
		for (int i = 0; i < 4; ++i)
		{
			switch (mobType)
			{
			case 1:
				if (this.contents[i] != null && this.contents[i].getItem() == Items.rotten_flesh)
					value = 0.05F;
				break;
			case 2:
				if (this.contents[i] != null && this.contents[i].getItem() == Items.bone)
					value = 0.05F;
				break;
			case 3:
				if (this.contents[i] != null && this.contents[i].getItem() == Items.gunpowder)
					value = 0.05F;
				break;
			case 4:
				if (this.contents[i] != null && (this.contents[i].getItem() == Items.string || this.contents[i].getItem() == Items.spider_eye))
					value = 0.05F;
				break;
			case 5:
				if (this.contents[i] != null && (this.contents[i].getItem() == Items.rotten_flesh || this.contents[i].getItem() == Items.bone || this.contents[i].getItem() == Items.apple))
					value = 0.05F;
				break;
			default:
				if (this.contents[i] != null && this.contents[i].getItem() == Items.porkchop)
					value = 0.05F;
				break;
			}
			
			if (value > 0.0F)
			{
				if (consume)
					this.decrStackSize(i, 1);
				
				break;
			}
		}
		
		return value;
	}
	
	private float consumeValuable(boolean consume)
	{
		float value = 0;
		
		for (int i = 0; i < 4; ++i)
		{
			if (this.contents[i] != null)
			{
				if (this.contents[i].getItem() == Items.iron_ingot)
					value = 0.15F;
				if (this.contents[i].getItem() == Items.gold_ingot)
					value = 0.30F;
				if (this.contents[i].getItem() == Items.diamond)
					value = 0.50F;
				if (this.contents[i].getItem() == Items.emerald)
					value = 0.60F;
				
				if (value > 0.0F)
				{
					if (consume)
						this.decrStackSize(i, 1);
					
					break;
				}
			}
		}
		
		return value;
	}
}
