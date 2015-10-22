package zairus.iskallminimobs.tileentity;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.ForgeDirection;
import zairus.iskallminimobs.helpers.IMMEnergy;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyProvider;
import cpw.mods.fml.common.Optional;

@Optional.InterfaceList({
	@Optional.Interface(iface = "cofh.api.energy.IEnergyHandler", modid = "CoFHCore"),
	@Optional.Interface(iface = "cofh.api.energy.IEnergyProvider", modid = "CoFHCore")
})
public class TileEntityMMPowerProvider
	extends TileEntity
	implements IEnergyProvider, IMMEnergy
{
	private int energyStored = 0;
	private final int maxEnergy = 10000;
	
	@Override
	public void updateEntity()
	{
		TileEntity tile;
		Block block;
		
		for (int i = 0; i < 6; i++)
		{
			block = this.worldObj.getBlock(
					xCoord + ForgeDirection.getOrientation(i).offsetX
					, yCoord + ForgeDirection.getOrientation(i).offsetY
					, zCoord + ForgeDirection.getOrientation(i).offsetZ);
			
			if (block == Blocks.redstone_block)
			{
				this.receiveEnergy(ForgeDirection.UNKNOWN, 1, false);
			}
			
			tile = worldObj.getTileEntity(
					xCoord + ForgeDirection.getOrientation(i).offsetX
					, yCoord + ForgeDirection.getOrientation(i).offsetY
					, zCoord + ForgeDirection.getOrientation(i).offsetZ);
			
			if (tile != null && tile instanceof IMMEnergy)
			{
				if (((IMMEnergy)tile).receiveEnergy(ForgeDirection.UNKNOWN, this.extractEnergy(ForgeDirection.UNKNOWN, 5, true), true) > 0)
					((IMMEnergy)tile).receiveEnergy(ForgeDirection.UNKNOWN, this.extractEnergy(ForgeDirection.UNKNOWN, 5, false), false);
			}else if (tile != null && tile instanceof IEnergyHandler)
			{
				if (((IEnergyHandler)tile).receiveEnergy(ForgeDirection.UNKNOWN, this.extractEnergy(ForgeDirection.UNKNOWN, 5, true), true) > 0)
					((IEnergyHandler)tile).receiveEnergy(ForgeDirection.UNKNOWN, this.extractEnergy(ForgeDirection.UNKNOWN, 5, false), false);
			}
		}
	}
	
	@Override
	public boolean canConnectEnergy(ForgeDirection from)
	{
		return this.energyStored > 0;
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
		return energyStored;
	}
	
	@Override
	public int getMaxEnergyStored(ForgeDirection from)
	{
		return maxEnergy;
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
	public Packet getDescriptionPacket()
	{
		NBTTagCompound syncData = new NBTTagCompound();
		this.writeToNBT(syncData);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, syncData);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
	{
		this.readFromNBT(pkt.func_148857_g());
	}
	
	private void updateMe()
	{
		this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
	}
}
