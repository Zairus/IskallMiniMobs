package zairus.iskallminimobs.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import zairus.iskallminimobs.IskallMiniMobs;
import zairus.iskallminimobs.MMConstants;
import zairus.iskallminimobs.tileentity.TileEntityMMIncubator;

public class MMIncubator
	extends BlockContainer
	implements IMMSpecialRenderedBlock
{
	private int renderType = 0;
	
	protected MMIncubator(String unlocalizedName)
	{
		super(Material.iron);
		
		this.setBlockName(unlocalizedName);
		this.setCreativeTab(IskallMiniMobs.tabIskallMinimobs);
		this.setBlockTextureName(MMConstants.MODID + ":" + "template_block");
		this.setStepSound(soundTypeGlass);
		this.setHardness(2.0F);
		this.setResistance(7.0F);
		this.setHarvestLevel("pickaxe", 0);
	}
	
	public void setRenderId(int id)
	{
		this.renderType = id;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int p_149915_2_)
	{
		return new TileEntityMMIncubator();
	}
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z)
	{
		super.onBlockAdded(world, x, y, z);
		func_149930_e(world, x, y, z);
	}
	
	private void func_149930_e(World p_149930_1_, int p_149930_2_, int p_149930_3_, int p_149930_4_)
	{
		if (!p_149930_1_.isRemote)
		{
			Block block = p_149930_1_.getBlock(p_149930_2_, p_149930_3_, p_149930_4_ - 1);
			Block block1 = p_149930_1_.getBlock(p_149930_2_, p_149930_3_, p_149930_4_ + 1);
			Block block2 = p_149930_1_.getBlock(p_149930_2_ - 1, p_149930_3_, p_149930_4_);
			Block block3 = p_149930_1_.getBlock(p_149930_2_ + 1, p_149930_3_, p_149930_4_);
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
			
			p_149930_1_.setBlockMetadataWithNotify(p_149930_2_, p_149930_3_, p_149930_4_, b0, 3);
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
	
	public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
	{
		super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
	}
	
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
	{
		if (world.isRemote)
		{
			return true;
		}
		else
		{
			player.openGui(IskallMiniMobs.instance, 0, world, x, y, z);
			return true;
		}
	}
}
