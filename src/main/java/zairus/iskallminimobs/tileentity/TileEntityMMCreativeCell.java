package zairus.iskallminimobs.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyProvider;

public class TileEntityMMCreativeCell
	extends TileEntity
	implements IEnergyProvider
{
	private static final int energy = 10000000;
	
	@Override
	public void updateEntity()
	{
		TileEntity tile;
		
		for (int i = 0; i < 6; i++)
		{
			tile = worldObj.getTileEntity(
					xCoord + ForgeDirection.getOrientation(i).offsetX
					, yCoord + ForgeDirection.getOrientation(i).offsetY
					, zCoord + ForgeDirection.getOrientation(i).offsetZ);
			
			if (tile != null && tile instanceof IEnergyHandler)
			{
				((IEnergyHandler)tile).receiveEnergy(ForgeDirection.UNKNOWN, 5, false);
			}
		}
	}
	
	@Override
	public boolean canConnectEnergy(ForgeDirection from)
	{
		return true;
	}
	
	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate)
	{
		return 1;
	}
	
	@Override
	public int getEnergyStored(ForgeDirection from)
	{
		return energy;
	}
	
	@Override
	public int getMaxEnergyStored(ForgeDirection from)
	{
		return energy;
	}
}
