package amreborn.extensions.datamanager;

import amreborn.packet.AMDataReader;
import amreborn.packet.AMDataWriter;

public interface TypeSerializer<T> {
	
	public T deserialize(AMDataReader buf) throws Throwable;

	public void serialize(AMDataWriter buf, T value) throws Throwable;
}
