package zairus.iskallminimobs.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import zairus.iskallminimobs.IskallMiniMobs;
import zairus.iskallminimobs.MMConstants;
import zairus.iskallminimobs.tileentity.TileEntityMMCreativeCell;

public class MMCreativeCell
	extends Block
	implements ITileEntityProvider
{
	protected MMCreativeCell(String unlocalizedName)
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
		return new TileEntityMMCreativeCell();
	}
}
