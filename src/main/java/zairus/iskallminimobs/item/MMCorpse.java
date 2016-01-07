package zairus.iskallminimobs.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import zairus.iskallminimobs.MMConstants;
import zairus.iskallminimobs.entity.minimob.MiniMobData;
import zairus.iskallminimobs.item.MMEmbryo.EmbryoTypes;
import zairus.iskallminimobs.stats.MMAchievementList;

public class MMCorpse
	extends MMItemBase
{
	public static final String[] types = EmbryoTypes.nameArray();
	
	private IIcon[] icons;
	
	public MMCorpse()
	{
		super();
		
		this.setTextureName(MMConstants.MODID + ":mm_corpse");
		this.maxStackSize = 1;
		this.setHasSubtypes(true);
	}
	
	@SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int damage)
    {
		int j = MathHelper.clamp_int(damage, 0, types.length - 1);
		
		return icons[j];
    }
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		int i = MathHelper.clamp_int(stack.getItemDamage(), 0, types.length - 1);
		return super.getUnlocalizedName() + "." + types[i];
	}
	
	@SuppressWarnings("unchecked")
	@SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creativeTab, @SuppressWarnings("rawtypes") List list)
    {
		for (int i = 0; i < types.length; ++i)
		{
			list.add(new ItemStack(item, 1, i));
		}
    }
	
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
		icons = new IIcon[types.length];
		
		for (int i = 0; i < types.length; ++i)
		{
			this.icons[i] = iconRegister.registerIcon(this.getIconString() + "_" + types[i]);
		}
    }
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int i1, boolean b1)
	{
		((EntityPlayer)entity).triggerAchievement(MMAchievementList.ripMiniFriend);
	}
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_)
    {
		NBTTagCompound tag = stack.getTagCompound();
		
		if (tag != null)
		{
			if (tag.hasKey(MiniMobData.MOBDATA_KEY))
			{
				NBTTagCompound data = (NBTTagCompound)tag.getTag(MiniMobData.MOBDATA_KEY);
				
				String mobName;
				int mobType = (data.getInteger(MiniMobData.MOBTYPE_KEY));
				
				if (data.hasKey(MiniMobData.CUSTOMNAME_KEY))
				{
					mobName = data.getString(MiniMobData.CUSTOMNAME_KEY);
				}
				else
				{
					mobName = EmbryoTypes.descriptionArray()[mobType];
				}
				
				list.add(mobName + "'s corpse");
				
				list.add("level: " + data.getInteger(MiniMobData.LEVEL_KEY));
				list.add("xp: " + data.getDouble(MiniMobData.EXPERIENCE_KEY));
				list.add("speed: " + data.getDouble(MiniMobData.SPEED_KEY));
				list.add("health: " + data.getDouble(MiniMobData.HEALTH_KEY));
				list.add("range: " + data.getDouble(MiniMobData.FOLLOW_KEY));
				list.add("attack: " + data.getDouble(MiniMobData.ATTACK_KEY));
			}
		}
    }
}
