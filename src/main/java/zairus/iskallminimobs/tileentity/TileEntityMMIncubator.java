package zairus.iskallminimobs.tileentity;

import java.util.UUID;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.ForgeDirection;
import zairus.iskallminimobs.MMConstants;
import zairus.iskallminimobs.entity.minimob.MiniMobData;
import zairus.iskallminimobs.item.MMItems;

public class TileEntityMMIncubator
	extends TileEntityMMBase
{
	protected final int totalInventory = 17;
	
	public int getTextureIndex()
	{
		return 0;
	}
	
	@Override
	public void updateEntity()
	{
		if (this.energyStored > 0)
		{
			ItemStack stack;
			ItemStack pellets = this.getStackInSlot(0);
			ItemStack pelletEmbrio;
			
			float completion = 0;
			
			for (int i = 1; i < 17; ++i)
			{
				stack = this.getStackInSlot(i);
				if (stack != null && stack.getItem() == MMItems.mm_embrio && isStackReady(stack))
				{
					if (!stack.hasTagCompound())
						stack.setTagCompound(new NBTTagCompound());
					
					completion = stack.getTagCompound().getFloat(MMConstants.INCUBATOR_COMPLETION_KEY);
					
					if (completion < 100.0F)
					{
						completion += ((float)this.extractEnergy(ForgeDirection.UNKNOWN, 1, false)) / 10.0F;
						
						completion = ((float)MathHelper.floor_float(completion)) + (((float)MathHelper.floor_float((completion - ((float)MathHelper.floor_float(completion))) * 100)) / 100);
						
						if (completion > 100.0F)
							completion = 100.0F;
						
						stack.getTagCompound().setFloat(
								MMConstants.INCUBATOR_COMPLETION_KEY
								, completion);
					}
					else
					{
						if (pellets != null && pellets.stackSize > 0)
						{
							pellets.stackSize--;
							
							if (pellets.stackSize <= 0)
								this.setInventorySlotContents(0, (ItemStack)null);
							
							pelletEmbrio = new ItemStack(MMItems.mm_pellet, 1);
							
							if (!pelletEmbrio.hasTagCompound())
								pelletEmbrio.setTagCompound(new NBTTagCompound());
							
							NBTTagCompound d = (stack.getTagCompound().hasKey(MiniMobData.MOBDATA_KEY))? (NBTTagCompound)stack.getTagCompound().getTag(MiniMobData.MOBDATA_KEY) : defaultMiniMobData(stack);
							
							pelletEmbrio.getTagCompound().setTag(MiniMobData.MOBDATA_KEY, d);
							
							this.setInventorySlotContents(i, (ItemStack)null);
							this.setInventorySlotContents(i, pelletEmbrio);
						}
					}
				}
			}
		}
	}
	
	private boolean isStackReady(ItemStack stack)
	{
		if (stack.getItemDamage() == 5)
		{
			if (!stack.hasDisplayName())
				return false;
		}
		
		return true;
	}
	
	private NBTTagCompound defaultMiniMobData(ItemStack stack)
	{
		NBTTagCompound data = new NBTTagCompound();
		
		data.setInteger(MiniMobData.MOBTYPE_KEY, stack.getItemDamage());
		
		if (stack.getItemDamage() == 5 || stack.hasDisplayName())
		{
			data.setString(MiniMobData.CUSTOMNAME_KEY, stack.getDisplayName());
		}
		
		data.setString(MiniMobData.UUID_KEY, UUID.randomUUID().toString());
		
		data.setInteger(MiniMobData.LEVEL_KEY, 0);
		data.setDouble(MiniMobData.EXPERIENCE_KEY, 0);
		
		data.setDouble(MiniMobData.SPEED_KEY, 0.10D);
		data.setDouble(MiniMobData.HEALTH_KEY, 10.0D);
		data.setDouble(MiniMobData.FOLLOW_KEY, 15.0D);
		data.setDouble(MiniMobData.ATTACK_KEY, 1.0D);
		
		return data;
	}
}
