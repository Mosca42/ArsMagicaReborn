package amreborn.extensions.datamanager.serializer;

import java.util.ArrayList;

import amreborn.extensions.datamanager.TypeSerializer;
import amreborn.packet.AMDataReader;
import amreborn.packet.AMDataWriter;

public class StringArraySerializer implements TypeSerializer<ArrayList<String>> {
	
	public static StringArraySerializer INSTANCE = new StringArraySerializer();
	
	private StringArraySerializer() {}
	
	@Override
	public void serialize(AMDataWriter buf, ArrayList<String> value) {
		if (value == null) return;
		buf.add(value.size());
		for (String entry : value) {
			buf.add(entry);
		}
	}

	@Override
	public ArrayList<String> deserialize(AMDataReader buf) {
		int size = buf.getInt();
		ArrayList<String> retMap = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			retMap.add(buf.getString());
		}
		return retMap;
	}

}
