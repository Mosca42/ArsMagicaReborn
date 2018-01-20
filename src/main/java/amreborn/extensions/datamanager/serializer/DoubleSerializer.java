package amreborn.extensions.datamanager.serializer;

import amreborn.extensions.datamanager.TypeSerializer;
import amreborn.packet.AMDataReader;
import amreborn.packet.AMDataWriter;

public class DoubleSerializer implements TypeSerializer<Double> {
	
	public static DoubleSerializer INSTANCE = new DoubleSerializer();
	
	private DoubleSerializer() {}
	
	@Override
	public void serialize(AMDataWriter buf, Double value) {
		buf.add(value == null ? 0 : value.doubleValue());
	}

	@Override
	public Double deserialize(AMDataReader buf) {
		return buf.getDouble();
	}

}
