package zairus.iskallminimobs.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import zairus.iskallminimobs.item.MMItems;
import zairus.iskallminimobs.tileentity.TileEntityMMIncubator;

public class ContainerMMIncubator
	extends ContainerMMBase
{
	//private final TileEntityMMIncubator inventory;
	
	public ContainerMMIncubator(InventoryPlayer playerInv, TileEntityMMIncubator inv, World world)
	{
		//this.inventory = inv;
		super(inv);
		
		inv.openInventory();
		
		bindPlayerInventory(playerInv, 49, 156);
		
		this.addSlotToContainer(new SlotEmbrio(inv, 0, 24, 55, SlotEmbrioBehavior.slot_pellet));
		
		int posX = 66;
		int posY = 21;
		
		int sIndex = 1;
		
		for (int sX = 0; sX < 4; sX++)
		{
			for (int sY = 0; sY < 4; sY++)
			{
				this.addSlotToContainer(new SlotEmbrio(inv, sIndex, posX + (35 * sX), posY + (34 * sY), SlotEmbrioBehavior.slot_embrio));
				++sIndex;
			}
		}
	}
	
	public TileEntityMMIncubator getIncubatorInventory()
	{
		return (TileEntityMMIncubator)this.inventory;
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
			
			// Player inventory
			if (slotNumber < 36)
			{
				if (itemstack1.getItem() == MMItems.mm_embrio)
					if (!this.mergeItemStack(itemstack1, 37, 53, false))
						return null;
				
				if (itemstack1.getItem() == MMItems.mm_pellet)
					if (!this.mergeItemStack(itemstack1, 36, 37, false))
						return null;
			}
			
			// Incubator
			if (slotNumber > 35 && slotNumber < 53)
			{
				if (!this.mergeItemStack(itemstack1, 0, 36, false))
					return null;
			}
			
			if (itemstack1.stackSize == 0)
				slot.putStack((ItemStack)null);
			else
				slot.onSlotChanged();
		}
		
		return itemstack;
	}
	
	public enum SlotEmbrioBehavior {slot_embrio, slot_pellet};
	
	private class SlotEmbrio
		extends Slot
	{
		private SlotEmbrioBehavior behavior;
		
		public SlotEmbrio(IInventory inventory, int slotNumber, int x, int y, SlotEmbrioBehavior b)
		{
			super(inventory, slotNumber, x, y);
			
			this.behavior = b;
		}
		
		@Override
		public boolean isItemValid(ItemStack stack)
		{
			return 
					behavior == SlotEmbrioBehavior.slot_embrio && stack.getItem() == MMItems.mm_embrio
					|| behavior == SlotEmbrioBehavior.slot_pellet && stack.getItem() == MMItems.mm_pellet;
		}
	}
}
