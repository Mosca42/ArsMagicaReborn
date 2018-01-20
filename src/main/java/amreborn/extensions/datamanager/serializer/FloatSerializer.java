package amreborn.extensions.datamanager.serializer;

import amreborn.extensions.datamanager.TypeSerializer;
import amreborn.packet.AMDataReader;
import amreborn.packet.AMDataWriter;

public class FloatSerializer implements TypeSerializer<Float> {
	
	public static FloatSerializer INSTANCE = new FloatSerializer();
	
	private FloatSerializer() {}
	
	@Override
	public void serialize(AMDataWriter buf, Float value) {
		buf.add(value == null ? 0 : value.floatValue());
	}

	@Override
	public Float deserialize(AMDataReader buf) {
		return buf.getFloat();
	}

}
