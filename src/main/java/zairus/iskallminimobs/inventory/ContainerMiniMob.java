package zairus.iskallminimobs.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;
import zairus.iskallminimobs.entity.minimob.EntityMiniMobBase;

public class ContainerMiniMob
	extends ContainerMMBase
{
	public ContainerMiniMob(InventoryPlayer playerInv, EntityMiniMobBase miniMob, World world)
	{
		super(miniMob.inventory);
		
		miniMob.inventory.openInventory();
		
		bindPlayerInventory(playerInv, 8, 92);
		
		int iIndex = 0;
		iIndex = placeSlotGrid(miniMob.inventory, iIndex, 116, 8, 3, 3);
		
		iIndex = 0;
		iIndex = placeSlotGrid(miniMob, iIndex, 79, 26, 1, 1);
		iIndex = placeSlotGrid(miniMob, iIndex, 8, 62, 1, 1);
		iIndex = placeSlotGrid(miniMob, iIndex, 8, 44, 1, 1);
		iIndex = placeSlotGrid(miniMob, iIndex, 8, 26, 1, 1);
		iIndex = placeSlotGrid(miniMob, iIndex, 8, 8, 1, 1);
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return true;
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotNumber)
	{
		ItemStack itemstack = null;
		Slot slot = (Slot)this.inventorySlots.get(slotNumber);
		
		if (slot != null)
		{
			if (slot.getHasStack())
			{
				ItemStack itemstack1 = slot.getStack();
				itemstack = itemstack1.copy();
				
				if (slotNumber < 36)
				{
					if (itemstack1.getItem() instanceof ItemSword || itemstack1.getItem() instanceof ItemBow || itemstack1.getItem() instanceof ItemArmor)
					{
						int pos = EntityMiniMobBase.getArmorPosition(itemstack1);
						
						if (!this.mergeItemStack(itemstack1, 45 + pos, 46 + pos, true))
						{
							if (!this.mergeItemStack(itemstack1, 36, 45, false))
								return null;
						}
					}else if (!this.mergeItemStack(itemstack1, 36, 45, false)) {
						return null;
					}
				}
				
				if (slotNumber > 35)
				{
					if (!this.mergeItemStack(itemstack1, 0, 36, false))
						return null;
				}
				
				if (itemstack1.stackSize == 0)
					slot.putStack((ItemStack)null);
				else
					slot.onSlotChanged();
			}
		}
		
		return itemstack;
	}
	
	public boolean addItemStackToInventory(ItemStack stack)
	{
		return this.mergeItemStack(stack, 36, 45, false);
	}
}
