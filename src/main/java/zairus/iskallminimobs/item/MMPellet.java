package zairus.iskallminimobs.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import zairus.iskallminimobs.MMConstants;
import zairus.iskallminimobs.entity.minimob.EntityMiniMobBase;
import zairus.iskallminimobs.entity.minimob.MiniMobData;
import zairus.iskallminimobs.entity.projectile.EntityMMPellet;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MMPellet
	extends MMItemBase
{
	private IIcon[] mobIconOverlay;
	private IIcon emptyOverlay;
	private IIcon baseIcon;
	
	public MMPellet()
	{
		super();
		
		setTextureName(MMConstants.MODID + ":mm_pellet");
	}
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_)
    {
		NBTTagCompound tag = stack.getTagCompound();
		
		String[] mobDescriptions = {
				"Pig Mini Mob"
				, "Zombie Mini Mob"
				, "Skeleton Mini Mob"
				, "Creeper Mini Mob"
				, "Spider Mini Mob"
				, "Soldier Mini Mob"
				, "Penguin Mini Mob"};
		
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
					mobName = mobDescriptions[mobType];
				}
				
				list.add("Contains: " + mobName);
				
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
		if (containsMiniMob(stack) || player.isSneaking())
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
			
			player.inventory.decrStackSize(player.inventory.currentItem, 1);
			player.inventory.markDirty();
			
			entity.setDead();
			
			if (!player.inventory.addItemStackToInventory(miniMobPellet))
			{
				player.worldObj.spawnEntityInWorld(new EntityItem(player.worldObj, player.posX, player.posY, player.posZ, miniMobPellet));
			}
		}
		
		return true;
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getBaseIcon()
	{
		return this.baseIcon;
	}
	
	public IIcon getOverlay(int index)
	{
		if (index == -1)
		{
			return this.emptyOverlay;
		}
		else
		{
			return this.mobIconOverlay[index];
		}
	}
	
	//@SideOnly(Side.CLIENT)
	public IIcon getOverlay(ItemStack stack)
	{
		NBTTagCompound tag = stack.getTagCompound();
		
		if (tag != null && tag.hasKey(MiniMobData.MOBTYPE_KEY))
		{
			int mobType = (tag.getInteger(MiniMobData.MOBTYPE_KEY));
			return mobIconOverlay[mobType];
		}
		else
		{
			return emptyOverlay;
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister)
	{
		this.mobIconOverlay = new IIcon[7];
		
		this.mobIconOverlay[0] = iconRegister.registerIcon(MMConstants.MODID + ":mm_pellet_pig");
		this.mobIconOverlay[1] = iconRegister.registerIcon(MMConstants.MODID + ":mm_pellet_zombie");
		this.mobIconOverlay[2] = iconRegister.registerIcon(MMConstants.MODID + ":mm_pellet_skeleton");
		this.mobIconOverlay[3] = iconRegister.registerIcon(MMConstants.MODID + ":mm_pellet_creeper");
		this.mobIconOverlay[4] = iconRegister.registerIcon(MMConstants.MODID + ":mm_pellet_spider");
		this.mobIconOverlay[5] = iconRegister.registerIcon(MMConstants.MODID + ":mm_pellet_soldier");
		this.mobIconOverlay[6] = iconRegister.registerIcon(MMConstants.MODID + ":mm_pellet_penguin");
		
		this.emptyOverlay = iconRegister.registerIcon(MMConstants.MODID + ":mm_pellet_empty");
		this.baseIcon = iconRegister.registerIcon(MMConstants.MODID + ":mm_pellet");
	}
}
