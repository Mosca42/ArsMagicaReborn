package amreborn.extensions.datamanager.serializer;

import java.util.HashMap;
import java.util.Map.Entry;

import amreborn.api.ArsMagicaAPI;
import amreborn.api.skill.Skill;
import amreborn.extensions.datamanager.TypeSerializer;
import amreborn.packet.AMDataReader;
import amreborn.packet.AMDataWriter;
import net.minecraft.util.ResourceLocation;

public class SkillMapSerializer implements TypeSerializer<HashMap<Skill, Boolean>> {
	
	public static SkillMapSerializer INSTANCE = new SkillMapSerializer();
	
	private SkillMapSerializer() {}
	
	@Override
	public void serialize(AMDataWriter buf, HashMap<Skill, Boolean> value) {
		if (value == null) return;
		buf.add(value.size());
		for (Entry<Skill, Boolean> entry : value.entrySet()) {
			buf.add(entry.getKey().getRegistryName().toString());
			buf.add(entry.getValue() == null ? false : entry.getValue().booleanValue());
		}
	}

	@Override
	public HashMap<Skill, Boolean> deserialize(AMDataReader buf) {
		int size = buf.getInt();
		HashMap<Skill, Boolean> retMap = new HashMap<>(size);
		for (int i = 0; i < size; i++) {
			Skill skill = ArsMagicaAPI.getSkillRegistry().getObject(new ResourceLocation(buf.getString()));
			boolean unlocked = buf.getBoolean();
			retMap.put(skill, unlocked);
		}
		return retMap;
	}

}
