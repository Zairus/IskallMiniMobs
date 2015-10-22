package zairus.iskallminimobs.block;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import zairus.iskallminimobs.IskallMiniMobs;
import zairus.iskallminimobs.MMConstants;
import zairus.iskallminimobs.tileentity.TileEntityMMNamingStation;

public class MMNamingStation
	extends MMBlockContainer
{
	public MMNamingStation(String unlocalizedName)
	{
		super();
		
		this.setRenderId(1);
		this.guiId = 3;
		
		this.setBlockName(unlocalizedName);
		this.setCreativeTab(IskallMiniMobs.tabIskallMinimobs);
		this.setBlockTextureName(MMConstants.MODID + ":" + "template_block");
		this.setStepSound(soundTypeWood);
		this.setHardness(2.0F);
		this.setResistance(5.0F);
		this.setHarvestLevel("axe", 0);
	}
	
	public int getTextureIndex()
	{
		return 1;
	}
	
	@Override
	protected TileEntity getBlockTileEntity()
	{
		return new TileEntityMMNamingStation();
	}
	
	@Override
	protected void playGuiSound(World world, EntityPlayer player)
	{
		world.playSoundAtEntity(player, "iskallminimobs:namestation_open", 1.0F, 1.2F / (world.rand.nextFloat() * 0.2F + 0.9F));
	}
}
