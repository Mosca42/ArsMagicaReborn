package amreborn.extensions.datamanager.serializer;

import amreborn.extensions.datamanager.TypeSerializer;
import amreborn.packet.AMDataReader;
import amreborn.packet.AMDataWriter;

public class StringSerializer implements TypeSerializer<String> {
	
	public static StringSerializer INSTANCE = new StringSerializer();
	
	private StringSerializer() {}
	
	@Override
	public void serialize(AMDataWriter buf, String value) {
		buf.add(value);
	}

	@Override
	public String deserialize(AMDataReader buf) {
		return buf.getString();
	}

}
