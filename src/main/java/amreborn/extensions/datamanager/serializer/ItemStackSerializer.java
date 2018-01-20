package amreborn.extensions.datamanager.serializer;

import com.google.common.base.Optional;

import amreborn.extensions.datamanager.TypeSerializer;
import amreborn.packet.AMDataReader;
import amreborn.packet.AMDataWriter;
import net.minecraft.item.ItemStack;

public class ItemStackSerializer implements TypeSerializer<Optional<ItemStack>> {
	
	public static ItemStackSerializer INSTANCE = new ItemStackSerializer();
	
	private ItemStackSerializer() {}
	
	@Override
	public void serialize(AMDataWriter buf, Optional<ItemStack> value) {
		buf.add(value == null ? null : value.orNull());
	}

	@Override
	public Optional<ItemStack> deserialize(AMDataReader buf) throws Throwable {
		return Optional.fromNullable(buf.getItemStack());
	}

}
