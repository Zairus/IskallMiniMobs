package zairus.iskallminimobs.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MMPellet
	extends MMItemBase
{
	public MMPellet()
	{
		super();
		
		setTextureName("iskallminimobs:mm_pellet");
	}
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_)
    {
		NBTTagCompound tag = stack.getTagCompound();
		String mobTypeKey = "mobType";
		
		String[] mobDescriptions = {"Pig Mini Mob", "Zombie Mini Mob", "Skeleton Mini Mob", "Creeper Mini Mob", "Spider Mini Mob"};
		
		if (tag != null)
		{
			if (tag.hasKey(mobTypeKey))
			{
				int mobType = tag.getInteger(mobTypeKey);
				list.add("Contains: " + mobDescriptions[mobType]);
			}
		}
    }
	
	public boolean containsMiniMob()
	{
		boolean contains = false;
		
		//NBTTagCompound tag = stack.getTagCompound();
		
		return contains;
	}
}
