package amreborn.extensions.datamanager.serializer;

import amreborn.extensions.datamanager.TypeSerializer;
import amreborn.packet.AMDataReader;
import amreborn.packet.AMDataWriter;

public class BooleanSerializer implements TypeSerializer<Boolean> {
	
	public static BooleanSerializer INSTANCE = new BooleanSerializer();
	
	private BooleanSerializer() {}
	
	@Override
	public void serialize(AMDataWriter buf, Boolean value) {
		buf.add(value == null ? false : value.booleanValue());
	}

	@Override
	public Boolean deserialize(AMDataReader buf) {
		return buf.getBoolean();
	}

}
