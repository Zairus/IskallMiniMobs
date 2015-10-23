package zairus.iskallminimobs.tileentity;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import zairus.iskallminimobs.MMConstants;

public class TileEntityMMNamingStation
	extends TileEntityMMBase
{
	public boolean nameChanged = true;
	
	protected final int totalInventory = 18;
	protected ItemStack[] contents = new ItemStack[totalInventory];
	
	private String itemName = "";
	private int[] colorAmounts = new int[16];
	private int[] colorAmountsRequired = new int[16];
	private boolean hasResult = false;
	
	private int getCodeByDamage(int damage)
	{
		/*
		Match Codes = Damage
		0 = 0 ink sack
		1 = 4 lapis
		2 = 2 cactus green
		3 = 6 cyan dye
		4 = 3 cocoa
		5 = 5 purple dye
		6 = 14 orange dye
		7 = 7 light gray dye
		8 = 8 gray dye
		9 = 13 magenta dye ****
		10 = 10 lime dye
		11 = 12 light blue dye
		12 = 1 rose red
		13 = 9 pink dye
		14 = 11 dandelion yellow
		15 = 15 bone meal
		*/
		
		if (damage == 1) return 12;
		if (damage == 2) return 2;
		if (damage == 3) return 4;
		if (damage == 4) return 1;
		if (damage == 5) return 5;
		if (damage == 6) return 3;
		if (damage == 7) return 7;
		if (damage == 8) return 8;
		if (damage == 9) return 13;
		if (damage == 10) return 10;
		if (damage == 11) return 14;
		if (damage == 12) return 11;
		if (damage == 13) return 9;
		if (damage == 14) return 6;
		if (damage == 15) return 15;
		
		return 0;
	}
	
	@Override
	public int getTextureIndex()
	{
		return 1;
	}
	
	@Override
	public void updateEntity()
	{
		ItemStack subject = this.getStackInSlot(16);
		
		if (subject != null && nameChanged)
		{
			this.nameChanged = false;
			
			ItemStack named = subject.copy();
			String name = this.getItemName();
			
			if (name != "")
				named.setStackDisplayName(name);
			
			this.setInventorySlotContents(17, named);
			this.hasResult = true;
		}
		
		if (this.hasResult && (this.getStackInSlot(16) == null || this.getStackInSlot(17) == null))
		{
			this.setInventorySlotContents(16, null);
			this.setInventorySlotContents(17, null);
			
			this.hasResult = false;
		}
	}
	
	public void consumeDye()
	{
		this.calculateColorAmounts();
		this.calculateColorAmountsRequired();
		
		for (int i = 0; i < 16; ++i)
		{
			ItemStack stack = this.getStackInSlot(i);
			if (stack != null)
			{
				int color = this.getCodeByDamage(stack.getItemDamage());
				int required = this.colorAmountsRequired[color];
				if (required > 0)
				{
					if (required >= stack.stackSize)
					{
						this.setInventorySlotContents(i, null);
						required -= stack.stackSize;
					}
					else
					{
						this.decrStackSize(i, required);
						required = 0;
					}
					this.colorAmountsRequired[color] = required;
				}
			}
		}
		
		this.worldObj.playSound(this.xCoord, this.yCoord, this.zCoord, "iskallminimobs:namestation_stamp", 1.0F, 1.2F / (this.worldObj.rand.nextFloat() * 0.2F + 0.9F), true);
	}
	
	public boolean canName()
	{
		boolean enough = true;
		
		this.calculateColorAmounts();
		this.calculateColorAmountsRequired();
		
		for (int i = 0; i <16; ++i)
		{
			if (this.colorAmountsRequired[i] > this.colorAmounts[i])
			{
				enough = false;
				break;
			}
		}
		
		return enough;
	}
	
	private void calculateColorAmountsRequired()
	{
		for (int i = 0; i < 16; ++i)
			this.colorAmountsRequired[i] = 0;
		
		if (this.itemName == "")
			return;
		
		Map<Character, Integer> colorValues = new HashMap<Character, Integer>();
		
		colorValues.put('0', 0);
		colorValues.put('1', 1);
		colorValues.put('2', 2);
		colorValues.put('3', 3);
		colorValues.put('4', 4);
		colorValues.put('5', 5);
		colorValues.put('6', 6);
		colorValues.put('7', 7);
		colorValues.put('8', 8);
		colorValues.put('9', 9);
		colorValues.put('a', 10);
		colorValues.put('b', 11);
		colorValues.put('c', 12);
		colorValues.put('d', 13);
		colorValues.put('e', 14);
		colorValues.put('f', 15);
		
		char[] nameChars = this.itemName.toCharArray();
		
		if (nameChars == null)
			return;
		
		int color = 0;
		int extra = 0;
		int stackMultiplier = 1;
		ItemStack result = this.getStackInSlot(17);
		
		if (result != null)
		{
			stackMultiplier = result.stackSize;
		}
		
		for (int i = 0; i < nameChars.length; ++i)
		{
			if (nameChars[i] == MMConstants.colorChar && i < nameChars.length - 1)
			{
				if (nameChars[i+1] == 'k' || nameChars[i+1] == 'l' || nameChars[i+1] == 'm' || nameChars[i+1] == 'n' || nameChars[i+1] == 'o')
				{
					extra++;
				} else if (nameChars[i+1] == 'r') {
					color = 15;
					extra = 0;
				}
				else
				{
					color = (colorValues.containsKey(nameChars[i+1]))? colorValues.get(nameChars[i+1]) : 15;
					extra = 0;
				}
				i++;
			}
			else
			{
				this.colorAmountsRequired[color] += ((1 + extra) * stackMultiplier);
			}
		}
	}
	
	private void calculateColorAmounts()
	{
		for (int i = 0; i < 16; ++i)
			this.colorAmounts[i] = 0;
		
		for (int i = 0; i < 16; ++i)
		{
			ItemStack cur = this.getStackInSlot(i);
			if (cur != null && cur.getItem() instanceof ItemDye)
			{
				this.colorAmounts[this.getCodeByDamage(cur.getItemDamage())] += cur.stackSize;
			}
		}
	}
	
	@Override
	public void setInventorySlotContents(int slot, ItemStack stack)
	{
		super.setInventorySlotContents(slot, stack);
		
		if (slot == 16 && stack != null && this.itemName == "" && stack.hasDisplayName())
		{
			this.setItemName(stack.getDisplayName());
		}
		
		if (slot == 16 && stack != null && this.itemName != "")
		{
			this.setItemName(this.itemName);
		}
	}
	
	public void setItemName(String name)
	{
		this.itemName = name;
		this.nameChanged = true;
		this.worldObj.playSound(this.xCoord, this.yCoord, this.zCoord, "iskallminimobs:namestation_type", 1.0F, 1.2F / (this.worldObj.rand.nextFloat() * 0.2F + 0.9F), true);
	}
	
	public String getItemName()
	{
		this.nameChanged = false;
		return this.itemName;
	}
	
	public void playCloseSound()
	{
		this.worldObj.playSound(this.xCoord, this.yCoord, this.zCoord, "iskallminimobs:namestation_close", 1.0F, 1.2F / (this.worldObj.rand.nextFloat() * 0.2F + 0.9F), true);
	}
}
