package zairus.iskallminimobs.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import zairus.iskallminimobs.IskallMiniMobs;
import zairus.iskallminimobs.MMConstants;
import zairus.iskallminimobs.tileentity.TileEntityMMPowerProvider;

public class MMPowerProvider
	extends Block
	implements ITileEntityProvider, IMMSpecialRenderedBlock
{
	protected int renderType = 0;
	
	protected MMPowerProvider(String unlocalizedName)
	{
		super(Material.iron);
		
		this.setBlockName(unlocalizedName);
		this.setCreativeTab(IskallMiniMobs.tabIskallMinimobs);
		this.setBlockTextureName(MMConstants.MODID + ":" + "mm_cell");
		this.setStepSound(soundTypeGlass);
		this.setHardness(5.0F);
		this.setResistance(10.0F);
		this.setHarvestLevel("pickaxe", 5);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int p_149915_2_)
	{
		return new TileEntityMMPowerProvider();
	}
	
	@Override
	public void setRenderId(int id)
	{
		this.renderType = id;
	}
	
	public int getRenderType()
    {
        return this.renderType;
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
	}
	
	public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	public void breakBlock(World world, int x, int y, int z, Block block, int id)
	{
		super.breakBlock(world, x, y, z, block, id);
	}
}
