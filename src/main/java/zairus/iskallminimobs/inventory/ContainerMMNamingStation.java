package zairus.iskallminimobs.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import zairus.iskallminimobs.tileentity.TileEntityMMNamingStation;

public class ContainerMMNamingStation
	extends ContainerMMBase
{
	private TileEntityMMNamingStation entityNameStation;
	
	public ContainerMMNamingStation(InventoryPlayer playerInv, TileEntityMMNamingStation inv, World world)
	{
		super(inv);
		
		bindPlayerInventory(playerInv, 8, 92);
		
		this.entityNameStation = inv;
		
		int iIndex = 0;
		iIndex = placeSlotGridDye(inv, iIndex, 16, 53, 8, 2);
		
		iIndex = placeSlotGrid(inv, iIndex, 16, 30, 1, 1);
		this.addSlotToContainer(new SlotNameResult(inv, 17, 142, 30));
	}
	
	public int placeSlotGridDye(IInventory inv, int iIndex, int gridX, int gridY, int gridCols, int gridRows)
	{
		gridDone:
			for (int i = 0; i < gridRows; ++i)
			{
				for (int j = 0; j < gridCols; ++j)
				{
					if (iIndex > inv.getSizeInventory())
					{
						break gridDone;
					}
					
					this.addSlotToContainer(new SlotDye(inv, iIndex, gridX + (j * 18), gridY + (i * 18)));
					++iIndex;
				}
			}
		
		return iIndex;
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotNumber)
	{
		ItemStack itemstack = null;
		Slot slot = (Slot)this.inventorySlots.get(slotNumber);
		
		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			
			if (slotNumber > 35)
			{
				if (!this.mergeItemStack(itemstack1, 0, 36, false))
					return null;
			}
			
			if (slotNumber < 36)
			{
				if (itemstack1.getItem() instanceof ItemDye)
				{
					if (!this.mergeItemStack(itemstack1, 36, 52, false))
						return null;
				} else if(!this.mergeItemStack(itemstack1, 52, 53, false)) {
					return null;
				}
			}
			
			if (itemstack1.stackSize == 0)
				slot.putStack((ItemStack)null);
			else
				slot.onSlotChanged();
		}
		
		return itemstack;
	}
	
	@Override
	public void onContainerClosed(EntityPlayer player)
	{
		super.onContainerClosed(player);
		this.entityNameStation.playCloseSound();
	}
	
	private class SlotDye 
		extends Slot
	{
		public SlotDye(IInventory inventory, int slotNumber, int x, int y)
		{
			super(inventory, slotNumber, x, y);
		}
		
		@Override
		public boolean isItemValid(ItemStack stack)
		{
			return (stack.getItem() instanceof ItemDye);
		}
	}
	
	private class SlotNameResult 
		extends Slot
	{
		public SlotNameResult(IInventory inventory, int slotNumber, int x, int y)
		{
			super(inventory, slotNumber, x, y);
		}
		
		@Override
		public boolean isItemValid(ItemStack stack)
		{
			return false;
		}
		
		@Override
		public boolean canTakeStack(EntityPlayer player)
		{
			return ((TileEntityMMNamingStation)this.inventory).canName();
		}
		
		@Override
		public void onPickupFromSlot(EntityPlayer player, ItemStack stack)
	    {
	        super.onPickupFromSlot(player, stack);
	        ((TileEntityMMNamingStation)this.inventory).consumeDye();
	    }
	}
}
