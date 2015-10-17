package zairus.iskallminimobs.handlers;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import zairus.iskallminimobs.entity.minimob.EntityMiniMobBase;
import zairus.iskallminimobs.gui.GuiMMIncubator;
import zairus.iskallminimobs.gui.GuiMMInventory;
import zairus.iskallminimobs.inventory.ContainerMMIncubator;
import zairus.iskallminimobs.inventory.ContainerMiniMob;
import zairus.iskallminimobs.tileentity.TileEntityMMIncubator;
import cpw.mods.fml.common.network.IGuiHandler;

public class MMGuiHandler
	implements IGuiHandler
{
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		
		if (tileEntity != null && tileEntity instanceof TileEntityMMIncubator)
		{
			return new ContainerMMIncubator(player.inventory, (TileEntityMMIncubator)tileEntity, world);
		}
		
		if (ID == 1)
		{
			@SuppressWarnings("unchecked")
			List<Object> entities = world.getEntitiesWithinAABB(EntityMiniMobBase.class, AxisAlignedBB.getBoundingBox(((double)x)-1.0D, ((double)y)-1.0D, ((double)z)-1.0D, ((double)x)+1.0D, ((double)y)+1.0D, ((double)z)+1.0D));
			
			for (int i = 0; i < entities.size(); ++i)
			{
				Object e = entities.get(i);
				if (e != null && e instanceof EntityMiniMobBase && ((EntityMiniMobBase)e).actionRequested == 1)
				{
					((EntityMiniMobBase)e).actionRequested = 0;
					return new ContainerMiniMob(player.inventory, (EntityMiniMobBase)e, world);
				}
			}
		}
		
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		
		if (tileEntity != null && tileEntity instanceof TileEntityMMIncubator)
		{
			return new GuiMMIncubator(player.inventory, (TileEntityMMIncubator)tileEntity, world);
		}
		
		if (ID == 1)
		{
			@SuppressWarnings("unchecked")
			List<Object> entities = world.getEntitiesWithinAABB(EntityMiniMobBase.class, AxisAlignedBB.getBoundingBox(((double)x)-1.0D, ((double)y)-1.0D, ((double)z)-1.0D, ((double)x)+1.0D, ((double)y)+1.0D, ((double)z)+1.0D));
			
			for (int i = 0; i < entities.size(); ++i)
			{
				Object e = entities.get(i);
				if (e != null && e instanceof EntityMiniMobBase && ((EntityMiniMobBase)e).actionRequested == 1)
				{
					((EntityMiniMobBase)e).actionRequested = 0;
					return new GuiMMInventory(player.inventory, (EntityMiniMobBase)e, world);
				}
			}
		}
		
		return null;
	}
}
