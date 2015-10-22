package zairus.iskallminimobs.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMMBase
	extends Container
{
	protected final IInventory inventory;
	
	public ContainerMMBase(IInventory inv)
	{
		inventory = inv;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return this.inventory.isUseableByPlayer(player);
	}
	
	public int placeSlotGrid(IInventory inv, int iIndex, int gridX, int gridY, int gridCols, int gridRows)
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
	
	public void bindPlayerInventory(InventoryPlayer playerinv, int posX, int posY)
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
	
	public void moveSlots(boolean down, int distance)
	{
		for (int i = 0; i < this.inventorySlots.size(); ++i)
		{
			if (down)
				((Slot)this.inventorySlots.get(i)).yDisplayPosition += distance;
			else
				((Slot)this.inventorySlots.get(i)).yDisplayPosition -= distance;
		}
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
		return null;
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
}
