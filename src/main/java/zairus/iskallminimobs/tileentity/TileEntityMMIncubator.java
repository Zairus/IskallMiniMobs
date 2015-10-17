package zairus.iskallminimobs.tileentity;

import java.util.UUID;

import zairus.iskallminimobs.MMConstants;
import zairus.iskallminimobs.entity.minimob.MiniMobData;
import zairus.iskallminimobs.item.MMItems;
import cofh.api.energy.IEnergyHandler;
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

public class TileEntityMMIncubator
	extends TileEntity
	implements IInventory, IEnergyHandler
{
	private ItemStack[] contents = new ItemStack[totalInventory()];
	
	private String customName;
	private int numPlayersUsing;
	private int energyStored = 0;
	
	private final int maxEnergy = 5000;
	
	private final int totalInventory()
	{
		return 17;
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
							
							pelletEmbrio.getTagCompound().setTag(MiniMobData.MOBDATA_KEY, defaultMiniMobData(stack));
							
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
		
		if (stack.getItemDamage() == 5)
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
		return this.hasCustomInventoryName() ? this.customName : "container.studydesk";
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
	
	private void writeSyncableDataToNBT(NBTTagCompound syncData)
	{
		syncData.setInteger("energyStored", this.energyStored);
	}
	
	private void readSyncableDataFromNBT(NBTTagCompound syncData)
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
			
			this.energyStored += receivedEnergy;
			this.energyStored = MathHelper.clamp_int(this.energyStored, 0, 5000);
			
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
			
			this.energyStored -= extractedEnergy;
			this.energyStored = MathHelper.clamp_int(this.energyStored, 0, 5000);
			
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
