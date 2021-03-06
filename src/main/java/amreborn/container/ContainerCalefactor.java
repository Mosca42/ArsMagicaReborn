package amreborn.container;

import amreborn.blocks.tileentity.TileEntityCalefactor;
import amreborn.container.slot.AM2Container;
import amreborn.container.slot.SlotChargeManaFociOnly;
import amreborn.container.slot.SlotGhostRune;
import amreborn.container.slot.SlotPickupOnly;
import amreborn.items.ItemFocusCharge;
import amreborn.items.ItemFocusMana;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceOutput;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class ContainerCalefactor extends AM2Container{

	private TileEntityCalefactor calefactor;

	private static final int PLAYER_INVENTORY_START = 9;
	private static final int PLAYER_ACTION_BAR_START = 36;
	private static final int PLAYER_ACTION_BAR_END = 45;

	public ContainerCalefactor(EntityPlayer player, TileEntityCalefactor calefactor){
		this.calefactor = calefactor;
		//input slot
		addSlotToContainer(new Slot(calefactor, 0, 80, 48)); //inventory, index, x, y
		//output slot
		addSlotToContainer(new SlotFurnaceOutput(player, calefactor, 1, 80, 88));
		//focus slots
		addSlotToContainer(new SlotChargeManaFociOnly(calefactor, 2, 62, 28));
		addSlotToContainer(new SlotChargeManaFociOnly(calefactor, 3, 80, 28));
		addSlotToContainer(new SlotChargeManaFociOnly(calefactor, 4, 98, 28));
		//bonus smelt slot
		addSlotToContainer(new SlotPickupOnly(calefactor, 5, 110, 84));

		addSlotToContainer(new SlotGhostRune(calefactor, 6, 62, 8));
		addSlotToContainer(new SlotGhostRune(calefactor, 7, 80, 8));
		addSlotToContainer(new SlotGhostRune(calefactor, 8, 98, 8));

		//display player inventory
		for (int i = 0; i < 3; i++){
			for (int k = 0; k < 9; k++){
				addSlotToContainer(new Slot(player.inventory, k + i * 9 + 9, 8 + k * 18, 122 + i * 18));
			}
		}

		//display player action bar
		for (int j1 = 0; j1 < 9; j1++){
			addSlotToContainer(new Slot(player.inventory, j1, 8 + j1 * 18, 180));
		}

	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int i){
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = (Slot)inventorySlots.get(i);
		if (slot != null && slot.getHasStack()){
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (i < PLAYER_INVENTORY_START){
				if (!mergeItemStack(itemstack1, PLAYER_INVENTORY_START, PLAYER_ACTION_BAR_END, true)){
					return ItemStack.EMPTY;
				}
			}else if (i >= PLAYER_INVENTORY_START && i < PLAYER_ACTION_BAR_START) //from player inventory
			{
				if (!mergeSpecialItems(itemstack1, slot)){
					if (!mergeItemStack(itemstack1, PLAYER_ACTION_BAR_START, PLAYER_ACTION_BAR_END, false)){
						return ItemStack.EMPTY;
					}
				}else{
					return ItemStack.EMPTY;
				}
			}else if (i >= PLAYER_ACTION_BAR_START && i < PLAYER_ACTION_BAR_END){
				if (!mergeSpecialItems(itemstack1, slot)){
					if (!mergeItemStack(itemstack1, PLAYER_INVENTORY_START, PLAYER_ACTION_BAR_START - 1, false)){
						return ItemStack.EMPTY;
					}
				}else{
					return ItemStack.EMPTY;
				}
			}else if (!mergeItemStack(itemstack1, PLAYER_INVENTORY_START, PLAYER_ACTION_BAR_END, false)){
				return ItemStack.EMPTY;
			}

			if (itemstack1.getCount() == 0){
				slot.putStack(ItemStack.EMPTY);
			}else{
				slot.onSlotChanged();
			}

			if (itemstack1.getCount() != itemstack.getCount()){
				slot.onSlotChange(itemstack1, itemstack);
			}else{
				return ItemStack.EMPTY;
			}
		}
		return itemstack;
	}

	private boolean mergeSpecialItems(ItemStack stack, Slot slot){
		if (stack.getItem() instanceof ItemFocusCharge || stack.getItem() instanceof ItemFocusMana){
			for (int b = 2; b < 5; ++b){
				Slot focusSlot = (Slot)inventorySlots.get(b);
				if (focusSlot.getHasStack()) continue;

				focusSlot.putStack(new ItemStack(stack.getItem(), 1, stack.getItemDamage()));
				focusSlot.onSlotChanged();
				stack.shrink(1);
				if (stack.getCount() == 0){
					slot.putStack(ItemStack.EMPTY);
					slot.onSlotChanged();
				}
				return true;
			}
		}else if (FurnaceRecipes.instance().getSmeltingResult(stack) != ItemStack.EMPTY){
			boolean b = mergeItemStack(stack, 0, 1, false);
			if (stack.getCount() == 0){
				slot.putStack(ItemStack.EMPTY);
				slot.onSlotChanged();
			}
			return b;
		}
		return false;
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer){
		return calefactor.isUsableByPlayer(entityplayer);
	}
}
