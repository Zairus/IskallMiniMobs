package zairus.iskallminimobs.handlers;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import zairus.iskallminimobs.block.MMBlocks;
import zairus.iskallminimobs.item.MMItems;
import cpw.mods.fml.common.registry.GameRegistry;

public class MMCraftingHandler
{
	public static void addCraftingRecipes()
	{
		GameRegistry.addShapedRecipe(
				new ItemStack(MMItems.mm_pellet, 2)
				, new Object[] {
					"sis"
					,"iri"
					,"sis"
					,'s'
					,Items.slime_ball
					,'i'
					,Items.iron_ingot
					,'r'
					,Items.redstone
				});
		
		GameRegistry.addShapedRecipe(
				new ItemStack(MMItems.mm_pelletholder, 1)
				, new Object[] {
					"pbp"
					,"brb"
					,"pbp"
					,'p'
					,MMItems.mm_pellet
					,'b'
					,Blocks.iron_bars
					,'r'
					,Items.redstone
				});
		
		GameRegistry.addShapelessRecipe(
				new ItemStack(MMItems.mm_pelletpanel)
				, new Object[] {
					MMItems.mm_pelletholder
					,MMItems.mm_pelletholder
					,MMItems.mm_pelletholder
					,MMItems.mm_pelletholder
				});
		
		GameRegistry.addShapedRecipe(
				new ItemStack(MMItems.mm_termicunit, 1)
				, new Object[] {
					"ibi"
					,"bfb"
					,"pdp"
					,'i'
					,Items.iron_ingot
					,'b'
					,Blocks.iron_bars
					,'f'
					,Blocks.furnace
					,'p'
					,Blocks.glass_pane
					,'d'
					,Items.redstone
				});
		
		GameRegistry.addShapedRecipe(
				new ItemStack(MMBlocks.mm_incubator)
				, new Object[] {
					"ggg"
					,"ipi"
					,"iti"
					,'g'
					,Blocks.glass
					,'i'
					,Items.iron_ingot
					,'p'
					,MMItems.mm_pelletpanel
					,'t'
					,MMItems.mm_termicunit
				});
		
		GameRegistry.addShapedRecipe(
				new ItemStack(MMBlocks.mm_dnaextractor)
				, new Object[] {
					"ggg"
					,"ppp"
					,"iti"
					,'g'
					,Blocks.glass
					,'i'
					,Items.iron_ingot
					,'p'
					,MMItems.mm_pelletholder
					,'t'
					,MMItems.mm_termicunit
				});
		
		GameRegistry.addShapedRecipe(
				new ItemStack(MMBlocks.mm_namingstation)
				, new Object[] {
					"fop"
					,"sss"
					,"wbw"
					,'f'
					,Items.feather
					,'o'
					,Items.flower_pot
					,'p'
					,Items.paper
					,'s'
					,Blocks.wooden_slab
					,'w'
					,Blocks.planks
					,'b'
					,Items.bucket
				});
		
		GameRegistry.addShapedRecipe(
				new ItemStack(MMBlocks.mm_powerprovider)
				, new Object[] {
					"rgr"
					,"idi"
					,"iti"
					,'r'
					,Items.redstone
					,'g'
					,Blocks.glass
					,'i'
					,Blocks.iron_block
					,'d'
					,Blocks.glowstone
					,'t'
					,MMItems.mm_termicunit
				});
	}
}
