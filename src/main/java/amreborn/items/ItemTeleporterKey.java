package amreborn.items;

import java.util.List;

import amreborn.ArsMagicaReborn;
import amreborn.defs.CreativeTabsDefs;
import amreborn.teleporter.TeleportationManager;
import amreborn.teleporter.Teleporter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemTeleporterKey extends Item {

	public ItemTeleporterKey() {
		setMaxStackSize(1);
		setCreativeTab(CreativeTabsDefs.tabAM2Items);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		if (!stack.hasTagCompound())
			return;
		NBTTagCompound nbt = stack.getTagCompound();
		if (nbt.hasKey("teleporters")) {
			NBTTagList l = stack.getTagCompound().getTagList("teleporters", NBT.TAG_COMPOUND);
			for (int i = 0; i < l.tagCount(); i++) {
				NBTTagCompound c = l.getCompoundTagAt(i);
				tooltip.add(c.getString("name") + "#" + c.getInteger("id"));
			}
		} else if (nbt.hasKey("teleportersNames")) {
			NBTTagList list = nbt.getTagList("teleportersNames", NBT.TAG_STRING);
			for (int i = 0; i < list.tagCount(); i++) {
				tooltip.add(list.getStringTagAt(i));
			}
		}
	}

	@Override
	public boolean getShareTag() {
		return true;
	}

	@Override
	public NBTTagCompound getNBTShareTag(ItemStack stack) {
		if (!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());

		NBTTagCompound ret = new NBTTagCompound();

		NBTTagCompound nbt = stack.getTagCompound();
		if (nbt.hasKey("teleporters")) {
			NBTTagList list = nbt.getTagList("teleporters", NBT.TAG_COMPOUND);
			NBTTagList newList = new NBTTagList();
			for (int i = 0; i < list.tagCount(); i++) {
				System.out.println(i);
				NBTTagCompound compound = list.getCompoundTagAt(i);
				newList.appendTag(new NBTTagString(compound.getString("name") + "#" + compound.getInteger("id")));
			}
			ret.setTag("teleporters", newList);
		}
		return ret;
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
		if (!world.isRemote) {
			if (!stack.hasTagCompound())
				stack.setTagCompound(new NBTTagCompound());

			NBTTagCompound nbt = stack.getTagCompound();
			if (nbt.hasKey("teleporters")) {
				NBTTagList list = nbt.getTagList("teleporters", NBT.TAG_COMPOUND);
				NBTTagList newList = new NBTTagList();
				for (int i = 0; i < list.tagCount(); i++) {
					NBTTagCompound compound = list.getCompoundTagAt(i);
					Teleporter t = new Teleporter(compound);
					Teleporter tel = TeleportationManager.getTeleporterForId(t.getId());
					if (t.strictlyEquals(tel)) {
						newList.appendTag(tel.serializeNBT());
					}
				}
				nbt.setTag("teleporters", newList);
			}
		}
	}

	public Item registerAndName(String name) {
		this.setUnlocalizedName(new ResourceLocation(ArsMagicaReborn.MODID, name).toString());
		GameRegistry.register(this, new ResourceLocation(ArsMagicaReborn.MODID, name));
		return this;
	}
}
