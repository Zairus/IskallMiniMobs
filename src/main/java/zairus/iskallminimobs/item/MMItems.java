package zairus.iskallminimobs.item;

import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import cpw.mods.fml.common.registry.GameRegistry;

public class MMItems
{
	public static MMItemBase mm_embrio;
	public static MMItemBase mm_pellet;
	public static MMItemBase mm_termicunit;
	public static MMItemBase mm_pelletholder;
	public static MMItemBase mm_pelletpanel;
	
	public static final void init()
	{
		mm_embrio = new MMEmbrio().setUnlocalizedName("mm_embrio");
		mm_pellet = new MMPellet().setUnlocalizedName("mm_pellet");
		mm_termicunit = new MMTermicUnit().setUnlocalizedName("mm_termicunit");
		mm_pelletholder = new MMPelletHolder().setUnlocalizedName("mm_pelletholder");
		mm_pelletpanel = new MMPelletPanel().setUnlocalizedName("mm_pelletpanel");
	}
	
	public static final void register()
	{
		GameRegistry.registerItem(mm_embrio, mm_embrio.getUnlocalizedName());
		GameRegistry.registerItem(mm_pellet, mm_pellet.getUnlocalizedName());
		GameRegistry.registerItem(mm_termicunit, mm_termicunit.getUnlocalizedName());
		GameRegistry.registerItem(mm_pelletholder, mm_pelletholder.getUnlocalizedName());
		GameRegistry.registerItem(mm_pelletpanel, mm_pelletpanel.getUnlocalizedName());
	}
	
	public static final void addLoot()
	{
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(mm_embrio, 1, 0), 1, 1, 2));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(mm_embrio, 1, 1), 1, 1, 2));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(mm_embrio, 1, 2), 1, 1, 2));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(mm_embrio, 1, 3), 1, 1, 2));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(mm_embrio, 1, 4), 1, 1, 2));
		
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_JUNGLE_CHEST, new WeightedRandomChestContent(new ItemStack(mm_embrio, 1, 0), 1, 1, 2));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_JUNGLE_CHEST, new WeightedRandomChestContent(new ItemStack(mm_embrio, 1, 1), 1, 1, 2));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_JUNGLE_CHEST, new WeightedRandomChestContent(new ItemStack(mm_embrio, 1, 2), 1, 1, 2));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_JUNGLE_CHEST, new WeightedRandomChestContent(new ItemStack(mm_embrio, 1, 3), 1, 1, 2));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_JUNGLE_CHEST, new WeightedRandomChestContent(new ItemStack(mm_embrio, 1, 4), 1, 1, 2));
		
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(mm_embrio, 1, 0), 1, 1, 2));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(mm_embrio, 1, 1), 1, 1, 2));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(mm_embrio, 1, 2), 1, 1, 2));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(mm_embrio, 1, 3), 1, 1, 2));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(mm_embrio, 1, 4), 1, 1, 2));
	}
}