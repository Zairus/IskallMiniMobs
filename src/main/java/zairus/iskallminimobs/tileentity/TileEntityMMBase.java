package zairus.iskallminimobs.tileentity;

import zairus.iskallminimobs.helpers.IMMEnergy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyHandler;
import cpw.mods.fml.common.Optional;

@Optional.Interface(iface = "cofh.api.energy.IEnergyHandler", modid = "CoFHCore")
public abstract class TileEntityMMBase
	extends TileEntity
	implements IInventory, IEnergyHandler, IMMEnergy
{
	protected final int totalInventory = 27;
	protected ItemStack[] contents = new ItemStack[totalInventory];
	protected String customName;
	protected int numPlayersUsing;
	protected int stackLimit = 64;
	
	protected int energyStored = 0;
	protected final int maxEnergy = 5000;
	
	public abstract int getTextureIndex();
	
	@Override
	public void updateEntity()
	{
		;
	}
	
	@Override
	public int getSizeInventory()
	{
		return this.totalInventory;
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
		return this.hasCustomInventoryName() ? this.customName : "container.mm";
	}
	
	@Override
	public boolean hasCustomInventoryName()
	{
		return this.customName != null && this.customName.length() > 0;
	}
	
	@Override
	public int getInventoryStackLimit()
	{
		return this.stackLimit;
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
	}
	
	@Override
	public void openInventory()
	{
		if (this.numPlayersUsing < 0)
		{
			this.numPlayersUsing = 0;
        }
		
		++this.numPlayersUsing;
		this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType(), 1, this.numPlayersUsing);
		this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.getBlockType());
		this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord - 1, this.zCoord, this.getBlockType());
	}
	
	@Override
	public void closeInventory()
	{
		--this.numPlayersUsing;
		this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType(), 1, this.numPlayersUsing);
		this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.getBlockType());
		this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord - 1, this.zCoord, this.getBlockType());
	}
	
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack)
	{
		return true;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		NBTTagList nbttaglist = new NBTTagList();
		
		writeSyncableDataToNBT(tag);
		
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
		
		tag.setTag("Items", nbttaglist);
		tag.setInteger("renderFacing", this.blockMetadata);
		
		if (this.hasCustomInventoryName())
		{
			tag.setString("CustomName", this.customName);
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		NBTTagList nbttaglist = tag.getTagList("Items", 10);
		
		readSyncableDataFromNBT(tag);
		
		this.contents = new ItemStack[this.getSizeInventory()];
		this.blockMetadata = tag.getInteger("renderFacing");
		
		if (tag.hasKey("CustomName", 8))
		{
			this.customName = tag.getString("CustomName");
		}
		
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
	
	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound syncData = new NBTTagCompound();
		writeSyncableDataToNBT(syncData);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, syncData);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
	{
		readSyncableDataFromNBT(pkt.func_148857_g());
	}
	
	protected void writeSyncableDataToNBT(NBTTagCompound syncData)
	{
		syncData.setInteger("energyStored", this.energyStored);
	}
	
	protected void readSyncableDataFromNBT(NBTTagCompound syncData)
	{
		this.energyStored = syncData.getInteger("energyStored");
	}
	
	private void updateMe()
	{
		this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
	}
	
	//## CofhCore Implementation
	
	@Override
	public boolean canConnectEnergy(ForgeDirection from)
	{
		return this.energyStored < this.maxEnergy;
	}
	
	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate)
	{
		if (this.energyStored < this.maxEnergy)
		{
			int receivedEnergy = MathHelper.clamp_int(maxReceive, 0, 5);
			
			receivedEnergy = MathHelper.clamp_int(receivedEnergy, 0, 5000);
			
			if (!simulate)
				this.energyStored += receivedEnergy;
			
			updateMe();
			
			return receivedEnergy;
		}
		else
		{
			return 0;
		}
	}
	
	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate)
	{
		if (this.energyStored > 0)
		{
			int extractedEnergy = MathHelper.clamp_int(maxExtract, 0, 5);
			
			extractedEnergy = MathHelper.clamp_int(extractedEnergy, 0, 5000);
			
			if (!simulate)
				this.energyStored -= extractedEnergy;
			
			updateMe();
			
			return extractedEnergy;
		}
		else
		{
			return 0;
		}
	}
	
	@Override
	public int getEnergyStored(ForgeDirection from)
	{
		return this.energyStored;
	}
	
	@Override
	public int getMaxEnergyStored(ForgeDirection from)
	{
		return this.maxEnergy;
	}
	
	//## CofhCore Implementation
}
