package zairus.iskallminimobs.stats;

import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;
import zairus.iskallminimobs.MMConstants;
import zairus.iskallminimobs.block.MMBlocks;
import zairus.iskallminimobs.item.MMItems;

public class MMAchievementList
{
	public static Achievement miniFriendship = (new Achievement(MMConstants.MODID + ":" + "achievement.miniFriendship", "miniFriendship", 0, 0, MMItems.mm_embrio, (Achievement)null)).initIndependentStat().registerStat();
	public static Achievement inYourPocket = (new Achievement(MMConstants.MODID + ":" + "achievement.inYourPocket", "inYourPocket", 0, 1, MMItems.mm_pellet, miniFriendship)).registerStat();
	public static Achievement mmGestation = (new Achievement(MMConstants.MODID + ":" + "achievement.mmGestation", "mmGestation", 0, 2, MMBlocks.mm_incubator, inYourPocket)).registerStat();
	public static Achievement ripMiniFriend = (new Achievement(MMConstants.MODID + ":" + "achievement.ripMiniFriend", "ripMiniFriend", 0, 3, MMItems.mm_corpse, mmGestation)).registerStat();
	public static Achievement welcomeBack = (new Achievement(MMConstants.MODID + ":" + "achievement.welcomeBack", "welcomeBack", 0, 4, MMBlocks.mm_dnaextractor, ripMiniFriend)).registerStat();
	
	public static AchievementPage MMPage1 = new AchievementPage("Iskall Mini Mobs", miniFriendship, inYourPocket, mmGestation, ripMiniFriend, welcomeBack);
	
	public static void initPages()
	{
		AchievementPage.registerAchievementPage(MMPage1);
	}
}
