package zairus.iskallminimobs.entity.minimob;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class EntityMiniMobPenguin
	extends EntityMiniMobBase
{
	private EntityAIAttackOnCollide aiAttackOnCollide = new EntityAIAttackOnCollide((EntityCreature) this, EntityMob.class, 1.2D, false);
	private EntityAILeapAtTarget aiLeapAtTarget = new EntityAILeapAtTarget(this, 0.425F);
	
	private final List<Block> speedBlocks = new ArrayList<Block>();
	private boolean onIce = false;
	
	public EntityMiniMobPenguin(World world)
	{
		super(world);
		
		this.setSize(0.3F, 0.5F);
		
		this.setCombatTask();
		
		this.canEquipArmor = false;
		this.canWeildWeapon = false;
		
		speedBlocks.add(Blocks.snow);
		speedBlocks.add(Blocks.snow_layer);
		speedBlocks.add(Blocks.ice);
		speedBlocks.add(Blocks.packed_ice);
		speedBlocks.add(Blocks.water);
	}
	
	@Override
	public int getType()
	{
		return 6;
	}
	
	public boolean isOnIce()
	{
		return this.onIce;
	}
	
	@Override
	public boolean canBreatheUnderwater()
	{
		return true;
	}
	
	public void setCombatTask()
	{
		this.tasks.removeTask(aiLeapAtTarget);
		this.tasks.removeTask(aiAttackOnCollide);
		
		if (this.onIce)
		{
			this.tasks.addTask(4, aiLeapAtTarget);
		}
		
		this.tasks.addTask(5, this.aiAttackOnCollide);
	}
	
	protected String getLivingSound()
	{
		String sound = "iskallminimobs:penguin_idle";
		
		if (this.hasCustomNameTag())
		{
			if (this.getCustomNameTag().contains("Pingu"))
			{
				sound = "iskallminimobs:penguin_noot";
			}
		}
		
		return sound;
	}
	
	protected String getHurtSound()
	{
		return "iskallminimobs:penguin_death";
	}
	
	protected String getDeathSound()
	{
		return (this.rand.nextInt(200) == 0)? "iskallminimobs:penguin_noot" : "iskallminimobs:penguin_death";
	}
	
	protected Item getDropItem()
	{
		return Items.feather;
	}
	
	@Override
	public boolean attackEntityAsMob(Entity entity)
	{
		if (super.attackEntityAsMob(entity))
		{
			if (level > 6)
			{
				if (this.rand.nextInt(10) > 7)
				{
					((EntityLivingBase) entity).addPotionEffect(new PotionEffect(2, 200, 1, false));
				}
			}
			return true;
		}
		
		return false;
	}
	
	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		
		Block block = this.worldObj.getBlock((int)this.posX, (int)this.posY, (int)this.posZ);
		Block blockFloor = this.worldObj.getBlock((int)this.posX, (int)this.posY - 1, (int)this.posZ);
		
		if (speedBlocks.contains(block) || speedBlocks.contains(blockFloor))
		{
			addPotionEffect(new PotionEffect(1, 2, 2, true));
			if (!onIce)
			{
				onIce = true;
				setCombatTask();
			}
		}
		else
		{
			if (onIce)
			{
				onIce = false;
				setCombatTask();
			}
		}
	}
	
	@Override
	public NBTTagCompound getMiniMobData()
	{
		NBTTagCompound data = super.getMiniMobData();
		
		data.setInteger(MiniMobData.MOBTYPE_KEY, this.getType());
		
		return data;
	}
	
	@Override
	public boolean isInWater()
	{
		return this.worldObj.handleMaterialAcceleration(this.boundingBox.expand(0.0D, -0.6000000238418579D, 0.0D), Material.water, this);
	}
}
