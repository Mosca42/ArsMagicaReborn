package amreborn.teleporter;

import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.google.common.base.Predicate;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Sets;

import amreborn.ArsMagicaReborn;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public enum TeleportationManager {

	;

	private static final HashMap<Integer, Teleporter> TELEPORTERS = new HashMap<Integer, Teleporter>();
	private static final HashBasedTable<BlockPos, Integer, Integer> IDS_TABLE = HashBasedTable.create();
	private static final BitSet IDS = new BitSet(Long.SIZE << 4);

	public static void removeTeleporter(Teleporter teleporter) {
		synchronized (IDS) {
			TELEPORTERS.remove(teleporter.getId());
			IDS_TABLE.remove(teleporter.getPos(), teleporter.getDimension());
			IDS.clear(teleporter.getId());
		}
	}

	private synchronized static void addTeleporter(Teleporter teleporter) {
		IDS.set(teleporter.getId());
		IDS_TABLE.put(teleporter.getPos(), teleporter.getDimension(), teleporter.getId());
		TELEPORTERS.put(teleporter.getId(), teleporter);
	}

	public static Teleporter getTeleporterAt(BlockPos position, int dimension) {
		return TELEPORTERS.get(IDS_TABLE.get(position, dimension));
	}

	public static Teleporter getOrCreateTeleporterAt(BlockPos pos, int dimension) {
		if (IDS_TABLE.contains(pos, dimension))
			return getTeleporterAt(pos, dimension);

		synchronized (IDS) {
			Teleporter teleporter = new Teleporter(pos, dimension, IDS.nextClearBit(0));
			addTeleporter(teleporter);
			return teleporter;
		}
	}

	public static Teleporter getTeleporterForId(int id) {
		return TELEPORTERS.get(id);
	}

	public static Set<Teleporter> getTeleportersForDim(final int dimension) {
		Set<Teleporter> set = new HashSet<Teleporter>();
		for (Integer id : IDS_TABLE.column(dimension).values()) {
			set.add(TELEPORTERS.get(id));
		}
		return set;
	}

	public static Set<Teleporter> getPublicTeleporters(Set<Teleporter> teleporters) {
		return Sets.filter(teleporters, new Predicate<Teleporter>() {

			@Override
			public boolean apply(Teleporter teleporter) {
				return !teleporter.isPrivatized();
			}
		});
	}

	public static class Save extends WorldSavedData {

		private static WorldSavedData instance = null;
		private static final String ID = ArsMagicaReborn.MODID + ":teleporter";

		public static void save() {
			instance.markDirty();
		}

		public static void load(FMLServerStartingEvent event) {
			MapStorage storage = event.getServer().getEntityWorld().getMapStorage();
			instance = storage.getOrLoadData(Save.class, ID);
			if (instance == null) {
				instance = new Save(ID);
				storage.setData(ID, instance);
			}
		}

		public Save(String name) {
			super(name);
		}

		@Override
		public void readFromNBT(NBTTagCompound nbt) {
			synchronized (IDS) {
				IDS.clear();
				TELEPORTERS.clear();
				IDS_TABLE.clear();
				NBTTagList list = nbt.getTagList("teleporters", NBT.TAG_COMPOUND);
				for (int i = 0; i < list.tagCount(); i++) {
					addTeleporter(new Teleporter(list.getCompoundTagAt(i)));
				}
			}
		}

		@Override
		public NBTTagCompound writeToNBT(NBTTagCompound compound) {
			NBTTagList list = new NBTTagList();
			for (Teleporter teleporter : TELEPORTERS.values()) {
				list.appendTag(teleporter.serializeNBT());
			}
			compound.setTag("teleporters", list);
			IDS.clear();
			TELEPORTERS.clear();
			IDS_TABLE.clear();
			return compound;
		}

	}

}
