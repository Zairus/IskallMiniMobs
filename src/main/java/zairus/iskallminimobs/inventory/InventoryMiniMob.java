package zairus.iskallminimobs.inventory;

import java.util.concurrent.Callable;

import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ReportedException;

public class InventoryMiniMob
	implements IInventory
{
	private ItemStack[] contents = new ItemStack[totalInventory()];
	private String customName;
	private int numPlayersUsing;
	
	private final int totalInventory()
	{
		return 9;
	}
	
	@Override
	public int getSizeInventory()
	{
		return totalInventory();
	}
	
	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return this.contents[slot];
	}
	
	@Override
	public ItemStack decrStackSize(int slot, int size)
	{
		if (this.contents[slot] != null)
		{
			ItemStack itemstack;
			
			if (this.contents[slot].stackSize <= size)
			{
				itemstack = this.contents[slot];
				this.contents[slot] = null;
				this.markDirty();
				return itemstack;
			}
			else
			{
				itemstack = this.contents[slot].splitStack(size);
				
				if (this.contents[slot].stackSize == 0)
				{
					this.contents[slot] = null;
				}
				
				this.markDirty();
				return itemstack;
			}
		}
		else
		{
			return null;
		}
	}
	
	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		if (this.contents[slot] != null)
		{
			ItemStack itemstack = this.contents[slot];
			this.contents[slot] = null;
			return itemstack;
		}
		else
		{
			return null;
		}
	}
	
	@Override
	public void setInventorySlotContents(int slot, ItemStack stack)
	{
		this.contents[slot] = stack;
		
		if (stack != null && stack.stackSize > this.getInventoryStackLimit())
		{
			stack.stackSize = this.getInventoryStackLimit();
		}
		
        this.markDirty();
	}
	
	@Override
	public String getInventoryName()
	{
		return this.hasCustomInventoryName() ? this.customName : "container.minimobInventory";
	}
	
	@Override
	public boolean hasCustomInventoryName()
	{
		return this.customName != null && this.customName.length() > 0;
	}
	
	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}
	
	@Override
	public void markDirty()
	{
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_)
	{
		return true;
	}
	
	@Override
	public void openInventory()
	{
		if (this.numPlayersUsing < 0)
		{
			this.numPlayersUsing = 0;
        }
		
		++this.numPlayersUsing;
	}
	
	@Override
	public void closeInventory()
	{
		--this.numPlayersUsing;
	}
	
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack)
	{
		return true;
	}
	
	public int getFirstEmptyStack()
	{
		for (int i = 0; i < this.contents.length; ++i)
		{
			if (this.contents[i] == null)
			{
				return i;
			}
		}
		
		return -1;
	}
	
	private int storeItemStack(ItemStack stack)
	{
		for (int i = 0; i < this.contents.length; ++i)
		{
			if (
					this.contents[i] != null 
					&& this.contents[i].getItem() == stack.getItem() 
					&& this.contents[i].isStackable() 
					&& this.contents[i].stackSize < this.contents[i].getMaxStackSize() 
					&& this.contents[i].stackSize < this.getInventoryStackLimit() 
					&& (!this.contents[i].getHasSubtypes() || this.contents[i].getItemDamage() == stack.getItemDamage()) 
					&& ItemStack.areItemStackTagsEqual(this.contents[i], stack))
			{
				return i;
			}
		}
		
		return -1;
    }
	
	private int storePartialItemStack(ItemStack stack)
	{
		Item item = stack.getItem();
		int i = stack.stackSize;
		int j;
		
		if (stack.getMaxStackSize() == 1)
		{
			j = this.getFirstEmptyStack();
			
			if (j < 0)
			{
				return i;
			}
			else
			{
				if (this.contents[j] == null)
				{
					this.contents[j] = ItemStack.copyItemStack(stack);
				}
				
				return 0;
			}
		}
		else
		{
			j = this.storeItemStack(stack);
			
			if (j < 0)
			{
				j = this.getFirstEmptyStack();
			}
			
			if (j < 0)
			{
				return i;
			}
			else
			{
				if (this.contents[j] == null)
				{
					this.contents[j] = new ItemStack(item, 0, stack.getItemDamage());
					
					if (stack.hasTagCompound())
					{
						this.contents[j].setTagCompound((NBTTagCompound)stack.getTagCompound().copy());
					}
				}
				
				int k = i;
				
				if (i > this.contents[j].getMaxStackSize() - this.contents[j].stackSize)
				{
					k = this.contents[j].getMaxStackSize() - this.contents[j].stackSize;
				}
				
				if (k > this.getInventoryStackLimit() - this.contents[j].stackSize)
				{
					k = this.getInventoryStackLimit() - this.contents[j].stackSize;
				}
				
				if (k == 0)
				{
					return i;
				}
				else
				{
					i -= k;
					this.contents[j].stackSize += k;
					this.contents[j].animationsToGo = 5;
					return i;
				}
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	public boolean addItemStackToInventory(final ItemStack stack)
	{
		if (stack != null && stack.stackSize != 0 && stack.getItem() != null)
		{
			try
			{
				int i;
				
				if (stack.isItemDamaged())
				{
					i = this.getFirstEmptyStack();
					
					if (i >= 0)
					{
						this.contents[i] = ItemStack.copyItemStack(stack);
						this.contents[i].animationsToGo = 5;
						stack.stackSize = 0;
						return true;
					}
					else
					{
						return false;
					}
				}
				else
				{
					do
					{
						i = stack.stackSize;
						stack.stackSize = this.storePartialItemStack(stack);
					}
					while (stack.stackSize > 0 && stack.stackSize < i);
					
					return stack.stackSize < i;
				}
			}
			catch (Throwable throwable)
			{
				CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Adding item to inventory");
				CrashReportCategory crashreportcategory = crashreport.makeCategory("Item being added");
				crashreportcategory.addCrashSection("Item ID", Integer.valueOf(Item.getIdFromItem(stack.getItem())));
				crashreportcategory.addCrashSection("Item data", Integer.valueOf(stack.getItemDamage()));
				crashreportcategory.addCrashSectionCallable("Item name", new Callable()
				{
					public String call()
					{
						return stack.getDisplayName();
					}
				});
				throw new ReportedException(crashreport);
			}
		}
		else
		{
			return false;
		}
	}
	
	public void writeToNBT(NBTTagList nbttaglist)
	{
		for (int i = 0; i < this.contents.length; ++i)
		{
			if (this.contents[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				this.contents[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
	}
	
	public void readFromNBT(NBTTagList nbttaglist)
	{
		this.contents = new ItemStack[this.getSizeInventory()];
		
		for (int i = 0; i < nbttaglist.tagCount(); ++i)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			int j = nbttagcompound1.getByte("Slot") & 255;
			
			if (j >= 0 && j < this.contents.length)
			{
				this.contents[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}
	}
}
