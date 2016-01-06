package zairus.iskallminimobs.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import zairus.iskallminimobs.MMConstants;
import zairus.iskallminimobs.entity.minimob.MiniMobData;

public class MMEmbryo
	extends MMItemBase
{
	public static final String[] embrio_types = EmbryoTypes.nameArray();
	
	private IIcon[] embrioIcons;
	
	public MMEmbryo()
	{
		super();
		
		this.setTextureName(MMConstants.MODID + ":mm_embrio");
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
			
			if (tag.hasKey(MiniMobData.MOBDATA_KEY))
			{
				NBTTagCompound data = (NBTTagCompound)tag.getTag(MiniMobData.MOBDATA_KEY);
				
				list.add("level: " + data.getInteger(MiniMobData.LEVEL_KEY));
				list.add("xp: " + data.getDouble(MiniMobData.EXPERIENCE_KEY));
				list.add("speed: " + data.getDouble(MiniMobData.SPEED_KEY));
				list.add("health: " + data.getDouble(MiniMobData.HEALTH_KEY));
				list.add("range: " + data.getDouble(MiniMobData.FOLLOW_KEY));
				list.add("attack: " + data.getDouble(MiniMobData.ATTACK_KEY));
			}
		}
    }
	
	public static enum EmbryoTypes
	{
		PIG(),
		ZOMBIE(),
		SKELETON(),
		CREEPER(),
		SPIDER(),
		SOLDIER(),
		PENGUIN();
		
		private EmbryoTypes()
		{
			;
		}
		
		public String toString()
		{
			return EmbryoTypes.nameArray()[ordinal()];
		}
		
		public static String[] nameArray()
		{
			String[] names = { "pig", "zombie", "skeleton", "creeper", "spider", "soldier", "penguin" };
			
			return names;
		}
		
		public static String[] descriptionArray()
		{
			String[] mobDescriptions = {"Pig Mini Mob", "Zombie Mini Mob", "Skeleton Mini Mob", "Creeper Mini Mob", "Spider Mini Mob", "Soldier Mini Mob", "Penguin Mini Mob"};
			
			return mobDescriptions;
		}
		
		public static HashMap<Integer, List<Item>> getEssenceItems()
		{
			HashMap<Integer, List<Item>> essenceItems = new HashMap<Integer, List<Item>>();
			
			essenceItems.put(0, EmbryoTypes.getItemList(Items.porkchop));
			essenceItems.put(1, EmbryoTypes.getItemList(Items.rotten_flesh));
			essenceItems.put(2, EmbryoTypes.getItemList(Items.bone));
			essenceItems.put(3, EmbryoTypes.getItemList(Items.gunpowder));
			essenceItems.put(4, EmbryoTypes.getItemList(Items.string, Items.spider_eye));
			essenceItems.put(5, EmbryoTypes.getItemList(Items.bone, Items.rotten_flesh, Items.apple));
			essenceItems.put(6, EmbryoTypes.getItemList(Items.feather));
			
			return essenceItems;
		}
		
		public static List<Item> getItemList(Item... items)
		{
			List<Item> iList = new ArrayList<Item>();
			
			for (int i = 0; i < items.length; ++i)
			{
				iList.add(items[i]);
			}
			
			return iList;
		}
	}
}
