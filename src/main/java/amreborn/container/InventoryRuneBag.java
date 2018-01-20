package amreborn.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;

public class InventoryRuneBag implements IInventory {
	public static final int inventorySize = 16;
	private NonNullList<ItemStack> inventoryItems;

	public InventoryRuneBag() {
		inventoryItems = NonNullList.<ItemStack>withSize(inventorySize, ItemStack.EMPTY);
	}

	public void SetInventoryContents(ItemStack[] inventoryContents) {
		int loops = Math.min(inventorySize, inventoryContents.length);
		for (int i = 0; i < loops; ++i) {
			if (inventoryContents[i] != ItemStack.EMPTY)
				if (inventoryContents[i] != null)
					inventoryItems.set(i, inventoryContents[i]);
		}
	}

	@Override
	public int getSizeInventory() {
		return inventorySize;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		if (i < 0 || i > inventoryItems.size() - 1) {
			return ItemStack.EMPTY;
		}
		return inventoryItems.get(i);
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {

		if (inventoryItems.get(i) != ItemStack.EMPTY) {
			if (inventoryItems.get(i).getCount() <= j) {
				ItemStack itemstack = inventoryItems.get(i);
				inventoryItems.set(i, ItemStack.EMPTY);
				return itemstack;
			}
			ItemStack itemstack1 = inventoryItems.get(i).splitStack(j);
			if (inventoryItems.get(i).getCount() == 0) {
				inventoryItems.set(i, ItemStack.EMPTY);
			}
			return itemstack1;
		} else {
			return ItemStack.EMPTY;
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		inventoryItems.set(i, itemstack);
	}

	@Override
	public String getName() {
		return "Rune Bag";
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer entityplayer) {
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player) {
	}

	@Override
	public void closeInventory(EntityPlayer player) {
	}

	public NonNullList<ItemStack> GetInventoryContents() {
		return inventoryItems;
	}

	@Override
	public ItemStack removeStackFromSlot(int i) {
		if (inventoryItems.get(i) != ItemStack.EMPTY) {
			ItemStack itemstack = inventoryItems.get(i);
			inventoryItems.set(i, ItemStack.EMPTY);
			return itemstack;
		} else {
			return ItemStack.EMPTY;
		}
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return false;
	}

	@Override
	public void markDirty() {
	}

	@Override
	public ITextComponent getDisplayName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getField(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getFieldCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}
}
