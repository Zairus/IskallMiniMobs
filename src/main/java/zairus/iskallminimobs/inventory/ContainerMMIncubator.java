package zairus.iskallminimobs.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import zairus.iskallminimobs.item.MMItems;
import zairus.iskallminimobs.tileentity.TileEntityMMIncubator;

public class ContainerMMIncubator
	extends Container
{
	private final TileEntityMMIncubator inventory;
	
	public ContainerMMIncubator(InventoryPlayer playerInv, TileEntityMMIncubator inv, World world)
	{
		this.inventory = inv;
		
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
	
	private int placeSlotGrid(IInventory inv, int iIndex, int gridX, int gridY, int gridCols, int gridRows)
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
					
					this.addSlotToContainer(new Slot(inv, iIndex, gridX + (j * 18), gridY + (i * 18)));
					++iIndex;
				}
			}
		
		return iIndex;
	}
	
	private void bindPlayerInventory(InventoryPlayer playerinv, int posX, int posY)
	{
		int iIndex = 0;
		
		//hotbar :from 0 to 8
		int gridX = posX;
		int gridY = posY + 58;
		int gridCols = 9;
		int gridRows = 1;
		iIndex = placeSlotGrid(playerinv, iIndex, gridX, gridY, gridCols, gridRows);
		
		//player's :from 9 to 35
		gridX = posX;
		gridY = posY;
		gridCols = 9;
		gridRows = 3;
		iIndex = placeSlotGrid(playerinv, iIndex, gridX, gridY, gridCols, gridRows);
	}
	
	public TileEntityMMIncubator getIncubatorInventory()
	{
		return this.inventory;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return this.inventory.isUseableByPlayer(player);
	}
	
	@Override
	public ItemStack slotClick(int slot, int slotX, int slotY, EntityPlayer player)
	{
		ItemStack stack = super.slotClick(slot, slotX, slotY, player);
		
		return stack;
	}
	
	@Override
	protected void retrySlotClick(int slotNumber, int p_75133_2_, boolean p_75133_3_, EntityPlayer player)
    {
		/*if (slotNumber < 9*3+9)
			this.slotClick(slotNumber, p_75133_2_, 1, player);*/
    }
	
	@Override
	public void addCraftingToCrafters(ICrafting crafting)
	{
		super.addCraftingToCrafters(crafting);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void detectAndSendChanges()
	{
		for (int i = 0; i < this.inventorySlots.size(); ++i)
		{
			ItemStack itemstack = ((Slot)this.inventorySlots.get(i)).getStack();
			ItemStack itemstack1 = (ItemStack)this.inventoryItemStacks.get(i);
			
			if (!ItemStack.areItemStacksEqual(itemstack1, itemstack))
			{
				itemstack1 = itemstack == null ? null : itemstack.copy();
				this.inventoryItemStacks.set(i, itemstack1);
				
				for (int j = 0; j < this.crafters.size(); ++j)
				{
					((ICrafting)this.crafters.get(j)).sendSlotContents(this, i, itemstack1);
				}
			}
		}
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
	
	@Override
	public void onContainerClosed(EntityPlayer player)
	{
		super.onContainerClosed(player);
		
		this.inventory.closeInventory();
	}
	
	@Override
	public void putStackInSlot(int slot, ItemStack stack)
	{
		this.getSlot(slot).putStack(stack);
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
