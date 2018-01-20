package amreborn.extensions.datamanager.serializer;

import amreborn.extensions.datamanager.TypeSerializer;
import amreborn.packet.AMDataReader;
import amreborn.packet.AMDataWriter;

public class IntegerSerializer implements TypeSerializer<Integer> {
	
	public static IntegerSerializer INSTANCE = new IntegerSerializer();
	
	private IntegerSerializer() {}
	
	@Override
	public void serialize(AMDataWriter buf, Integer value) {
		buf.add(value == null ? 0 : value.intValue());
	}

	@Override
	public Integer deserialize(AMDataReader buf) {
		return buf.getInt();
	}

}
