package zairus.iskallminimobs.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MMEmbrio
	extends MMItemBase
{
	public static final String[] embrio_types = new String[] {"pig", "zombie", "skeleton", "creeper", "spider"};
	
	private IIcon[] embrioIcons;
	
	public MMEmbrio()
	{
		super();
		
		this.setTextureName("iskallminimobs:mm_embrio");
		this.maxStackSize = 1;
		this.setHasSubtypes(true);
	}
	
	@SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int damage)
    {
		int j = MathHelper.clamp_int(damage, 0, embrio_types.length - 1);
		
		return embrioIcons[j];
    }
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		int i = MathHelper.clamp_int(stack.getItemDamage(), 0, embrio_types.length - 1);
		return super.getUnlocalizedName() + "." + embrio_types[i];
	}
	
	@SuppressWarnings("unchecked")
	@SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creativeTab, @SuppressWarnings("rawtypes") List list)
    {
		for (int i = 0; i < embrio_types.length; ++i)
		{
			list.add(new ItemStack(item, 1, i));
		}
    }
	
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
		embrioIcons = new IIcon[embrio_types.length];
		
		for (int i = 0; i < embrio_types.length; ++i)
		{
			this.embrioIcons[i] = iconRegister.registerIcon(this.getIconString()); // + "_" + embrio_types[i]
		}
    }
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_)
    {
		NBTTagCompound tag = stack.getTagCompound();
		String completionKey = "Gestation Completion";
		
		if (tag != null)
		{
			if (tag.hasKey(completionKey))
				list.add(completionKey + ": " + tag.getFloat(completionKey) + "%");
		}
    }
}
