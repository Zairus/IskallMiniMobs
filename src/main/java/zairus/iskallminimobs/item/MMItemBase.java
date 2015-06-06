package zairus.iskallminimobs.item;

import zairus.iskallminimobs.IskallMiniMobs;
import net.minecraft.item.Item;

public class MMItemBase
	extends Item
{
	public MMItemBase()
	{
		setCreativeTab(IskallMiniMobs.tabIskallMinimobs);
		this.maxStackSize = 64;
	}
	
	@Override
	public MMItemBase setUnlocalizedName(String unlocalizedName)
	{
		super.setUnlocalizedName(unlocalizedName);
		
		return this;
	}
}
