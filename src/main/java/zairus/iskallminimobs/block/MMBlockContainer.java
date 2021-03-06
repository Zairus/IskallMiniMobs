package zairus.iskallminimobs.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import zairus.iskallminimobs.IskallMiniMobs;

public abstract class MMBlockContainer
	extends BlockContainer
	implements IMMSpecialRenderedBlock
{
	protected int renderType = 0;
	protected int guiId = 0;
	
	protected MMBlockContainer()
	{
		super(Material.iron);
	}
	
	public abstract int getTextureIndex();
	
	public void setRenderId(int id)
	{
		this.renderType = id;
	}
	
	protected abstract TileEntity getBlockTileEntity();
	
	@Override
	public TileEntity createNewTileEntity(World world, int p_149915_2_)
	{
		return this.getBlockTileEntity();
	}
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z)
	{
		super.onBlockAdded(world, x, y, z);
		setFacing(world, x, y, z);
	}
	
	private void setFacing(World world, int x, int y, int z)
	{
		if (!world.isRemote)
		{
			Block block = world.getBlock(x, y, z - 1);
			Block block1 = world.getBlock(x, y, z + 1);
			Block block2 = world.getBlock(x - 1, y, z);
			Block block3 = world.getBlock(x + 1, y, z);
			byte b0 = 3;
			
			if (block.func_149730_j() && !block1.func_149730_j())
			{
				b0 = 3;
			}
			
			if (block1.func_149730_j() && !block.func_149730_j())
			{
				b0 = 2;
			}
			
			if (block2.func_149730_j() && !block3.func_149730_j())
			{
				b0 = 5;
			}
			
			if (block3.func_149730_j() && !block2.func_149730_j())
			{
				b0 = 4;
			}
			
			world.setBlockMetadataWithNotify(x, y, z, b0, 3);
		}
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
	{
		byte b0 = 0;
		int l = MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		
		if (l == 0)
		{
			b0 = 2;
		}
		
		if (l == 1)
		{
			b0 = 5;
		}
		
		if (l == 2)
		{
			b0 = 3;
		}
		
		if (l == 3)
		{
			b0 = 4;
		}
		
		world.setBlockMetadataWithNotify(x, y, z, b0, 3);
		
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("MMEntity"))
		{
			NBTTagCompound entityData = stack.getTagCompound().getCompoundTag("MMEntity");
			TileEntity tileEntity = world.getTileEntity(x, y, z);
			
			if (entityData != null)
			{
				tileEntity.readFromNBT(entityData);
				tileEntity.xCoord = x;
				tileEntity.yCoord = y;
				tileEntity.zCoord = z;
			}
		}
	}
	
	public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	public int getRenderType()
    {
        return this.renderType;
    }
	
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	public void breakBlock(World world, int x, int y, int z, Block block, int id)
	{
		super.breakBlock(world, x, y, z, block, id);
	}
	
	protected void playGuiSound(World world, EntityPlayer player)
	{
		;
	}
	
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int id, float f1, float f2, float f3)
	{
		if (world.isRemote)
		{
			return true;
		}
		else
		{
			this.playGuiSound(world, player);
			player.openGui(IskallMiniMobs.instance, this.guiId, world, x, y, z);
			return true;
		}
	}
	
	@Override
    public boolean removedByPlayer (World world, EntityPlayer player, int x, int y, int z, boolean harvest)
    {
		//return super.removedByPlayer(world, player, x, y, z);
		
		if (!world.isRemote && world.getGameRules().getGameRuleBooleanValue("doTileDrops"))
		{
			ItemStack stack = new ItemStack(this, 1);
			NBTTagCompound entityData = new NBTTagCompound();
			TileEntity entity = world.getTileEntity(x, y, z);
			entity.writeToNBT(entityData);
			NBTTagCompound stackData = new NBTTagCompound();
			stackData.setTag("MMEntity", entityData);
			stack.setTagCompound(stackData);
			EntityItem entityitem = new EntityItem(world, x, y, z, stack);
			world.spawnEntityInWorld(entityitem);
		}
		
		return world.setBlockToAir(x, y, z);
    }
	
	@Override
    public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int i)
    {
		//super.harvestBlock(world, player, x, y, z, i);
    }
}
