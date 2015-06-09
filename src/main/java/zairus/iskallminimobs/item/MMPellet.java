package zairus.iskallminimobs.item;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import zairus.iskallminimobs.entity.minimob.EntityMiniMobBase;
import zairus.iskallminimobs.entity.minimob.MiniMobData;
import zairus.iskallminimobs.entity.projectile.EntityMMPellet;
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
		
		String[] mobDescriptions = {"Pig Mini Mob", "Zombie Mini Mob", "Skeleton Mini Mob", "Creeper Mini Mob", "Spider Mini Mob"};
		
		if (tag != null)
		{
			if (tag.hasKey(MiniMobData.MOBDATA_KEY))
			{
				NBTTagCompound data = (NBTTagCompound)tag.getTag(MiniMobData.MOBDATA_KEY);
				
				int mobType = (data.getInteger(MiniMobData.MOBTYPE_KEY));
				list.add("Contains: " + mobDescriptions[mobType]);
				
				list.add("level: " + data.getInteger(MiniMobData.LEVEL_KEY));
				list.add("xp: " + data.getDouble(MiniMobData.EXPERIENCE_KEY));
				list.add("speed: " + data.getDouble(MiniMobData.SPEED_KEY));
				list.add("health: " + data.getDouble(MiniMobData.HEALTH_KEY));
				list.add("range: " + data.getDouble(MiniMobData.FOLLOW_KEY));
				list.add("attack: " + data.getDouble(MiniMobData.ATTACK_KEY));
			}
		}
    }
	
	public boolean containsMiniMob(ItemStack stack)
	{
		NBTTagCompound tag = stack.getTagCompound();
		
		return (tag == null)? false : tag.hasKey(MiniMobData.MOBDATA_KEY);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if (containsMiniMob(stack))
		{
			player.inventory.decrStackSize(player.inventory.currentItem, 1);
			
			world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
			
			if (!world.isRemote)
			{
				world.spawnEntityInWorld(new EntityMMPellet(world, player, (NBTTagCompound)stack.getTagCompound().getTag(MiniMobData.MOBDATA_KEY)));
			}
		}
		
		return stack;
	}
	
	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity)
	{
		if (containsMiniMob(stack))
			return false;
		
		if (entity instanceof EntityMiniMobBase)
		{
			NBTTagCompound data = ((EntityMiniMobBase)entity).getMiniMobData();
			NBTTagCompound tag = stack.getTagCompound();
			
			if (tag == null)
				tag = new NBTTagCompound();
			
			ItemStack miniMobPellet = new ItemStack(MMItems.mm_pellet, 1);
			miniMobPellet.setTagCompound(tag);
			miniMobPellet.getTagCompound().setTag(MiniMobData.MOBDATA_KEY, data);
			
			player.inventory.consumeInventoryItem(MMItems.mm_pellet);
			entity.setDead();
			
			if (!player.inventory.addItemStackToInventory(miniMobPellet))
			{
				player.worldObj.spawnEntityInWorld(new EntityItem(player.worldObj, player.posX, player.posY, player.posZ, miniMobPellet));
			}
		}
		
		return true;
	}
}
