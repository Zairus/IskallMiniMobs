package zairus.iskallminimobs.handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import zairus.iskallminimobs.gui.GuiMMIncubator;
import zairus.iskallminimobs.inventory.ContainerMMIncubator;
import zairus.iskallminimobs.tileentity.TileEntityMMIncubator;
import cpw.mods.fml.common.network.IGuiHandler;

public class MMGuiHandler
	implements IGuiHandler
{
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		
		if (tileEntity instanceof TileEntityMMIncubator)
		{
			return new ContainerMMIncubator(player.inventory, (TileEntityMMIncubator)tileEntity, world);
		}
		
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		
		if (tileEntity instanceof TileEntityMMIncubator)
		{
			return new GuiMMIncubator(player.inventory, (TileEntityMMIncubator)tileEntity, world);
		}
		
		return null;
	}
}
