package zairus.iskallminimobs.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import zairus.iskallminimobs.item.MMItems;
import zairus.iskallminimobs.tileentity.TileEntityMMDNAExtractor;

public class ContainerMMDNAExtractor
	extends ContainerMMBase
{
	public ContainerMMDNAExtractor(InventoryPlayer playerInv, TileEntityMMDNAExtractor inv, World world)
	{
		super(inv);
		
		inv.openInventory();
		
		bindPlayerInventory(playerInv, 8, 92);
		
		int iIndex = 0;
		iIndex += placeSlotGrid(inv, iIndex, 8, 53, 2, 2);
		
		this.addSlotToContainer(new SlotDNA(inv, 4, 12, 20, SlotDNA.SLOT_CORPSE));
		this.addSlotToContainer(new SlotDNA(inv, 5, 71, 20, SlotDNA.SLOT_EMBRYO));
		this.addSlotToContainer(new SlotDNA(inv, 6, 130, 20, SlotDNA.SLOT_RESULT));
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotNumber)
	{
		ItemStack itemstack = null;
		Slot slot = (Slot)this.inventorySlots.get(slotNumber);
		
		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			
			if (slotNumber < 36)
			{
				if (itemstack1.getItem() == MMItems.mm_corpse)
				{
					if (!this.mergeItemStack(itemstack1, 40, 41, false))
						return null;
				} else if (itemstack1.getItem() == MMItems.mm_embrio) {
					if (!this.mergeItemStack(itemstack1, 41, 42, false))
						return null;
				} else if (!this.mergeItemStack(itemstack1, 36, 40, false)) {
					return null;
				}
			}
			
			if (slotNumber > 35)
			{
				if (!this.mergeItemStack(itemstack1, 0, 36, false))
					return null;
			}
			
			if (itemstack1.stackSize == 0)
				slot.putStack((ItemStack)null);
			else
				slot.onSlotChanged();
		}
		
		return itemstack;
	}
	
	private class SlotDNA
		extends Slot
	{
		public static final int SLOT_CORPSE = 0;
		public static final int SLOT_EMBRYO = 1;
		public static final int SLOT_RESULT = 2;
		
		private final int slotType;
		
		public SlotDNA(IInventory inventory, int slotNumber, int x, int y, int type)
		{
			super(inventory, slotNumber, x, y);
			
			this.slotType = type;
		}
		
		@Override
		public boolean isItemValid(ItemStack stack)
		{
			return (
					(this.slotType == SlotDNA.SLOT_CORPSE && stack.getItem() == MMItems.mm_corpse)
					|| (this.slotType == SlotDNA.SLOT_EMBRYO && stack.getItem() == MMItems.mm_embrio));
		}
	}
}
