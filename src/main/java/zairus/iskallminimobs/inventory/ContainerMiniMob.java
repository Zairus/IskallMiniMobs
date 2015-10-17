package zairus.iskallminimobs.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import zairus.iskallminimobs.IskallMiniMobs;
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
		IskallMiniMobs.log("s: " + slotNumber);
		
		ItemStack itemstack = null;
		Slot slot = (Slot)this.inventorySlots.get(slotNumber);
		
		if (slot != null)
		{
			IskallMiniMobs.log("s: " + slotNumber + ", not null");
			if (slot.getHasStack())
			{
				IskallMiniMobs.log("s: " + slotNumber + ", has stack");
				
				ItemStack itemstack1 = slot.getStack();
				itemstack = itemstack1.copy();
				
				if (itemstack1.stackSize == 0)
					slot.putStack((ItemStack)null);
				else
					slot.onSlotChanged();
			}
		}
		
		return itemstack;
	}
}
