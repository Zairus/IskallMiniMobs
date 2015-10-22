package zairus.iskallminimobs.block;

import net.minecraft.tileentity.TileEntity;
import zairus.iskallminimobs.IskallMiniMobs;
import zairus.iskallminimobs.MMConstants;
import zairus.iskallminimobs.tileentity.TileEntityMMDNAExtractor;

public class MMDNAExtractor
	extends MMBlockContainer
{
	protected MMDNAExtractor(String unlocalizedName)
	{
		super();
		
		this.setRenderId(0);
		this.guiId = 2;
		
		this.setBlockName(unlocalizedName);
		this.setCreativeTab(IskallMiniMobs.tabIskallMinimobs);
		this.setBlockTextureName(MMConstants.MODID + ":" + "template_block");
		this.setStepSound(soundTypeGlass);
		this.setHardness(2.0F);
		this.setResistance(7.0F);
		this.setHarvestLevel("pickaxe", 0);
	}
	
	public int getTextureIndex()
	{
		return 1;
	}
	
	@Override
	protected TileEntity getBlockTileEntity()
	{
		return new TileEntityMMDNAExtractor();
	}
}
