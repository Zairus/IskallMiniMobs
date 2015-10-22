package zairus.iskallminimobs.helpers;

import net.minecraftforge.common.util.ForgeDirection;

public interface IMMEnergy
{
	public boolean canConnectEnergy(ForgeDirection from);
	
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate);
	
	public int getEnergyStored(ForgeDirection from);
	
	public int getMaxEnergyStored(ForgeDirection from);
	
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate);
}
