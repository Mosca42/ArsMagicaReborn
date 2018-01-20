package amreborn.extensions;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.common.base.Optional;

import amreborn.api.affinity.Affinity;
import amreborn.api.skill.Skill;
import amreborn.api.skill.SkillPoint;
import amreborn.extensions.datamanager.ArsMagicaManager;
import amreborn.extensions.datamanager.SavedObject;
import amreborn.extensions.datamanager.serializer.AffinityMapSerializer;
import amreborn.extensions.datamanager.serializer.BooleanSerializer;
import amreborn.extensions.datamanager.serializer.DoubleSerializer;
import amreborn.extensions.datamanager.serializer.FloatSerializer;
import amreborn.extensions.datamanager.serializer.IntegerSerializer;
import amreborn.extensions.datamanager.serializer.ItemStackSerializer;
import amreborn.extensions.datamanager.serializer.SkillMapSerializer;
import amreborn.extensions.datamanager.serializer.SkillPointMapSerializer;
import amreborn.extensions.datamanager.serializer.StringArraySerializer;
import amreborn.extensions.datamanager.serializer.StringBooleanMapSerializer;
import amreborn.extensions.datamanager.serializer.StringFloatMapSerializer;
import amreborn.extensions.datamanager.serializer.StringIntegerMapSerializer;
import amreborn.extensions.datamanager.serializer.StringSerializer;
import net.minecraft.item.ItemStack;

public class DataDefinitions {

	static final SavedObject<Float> CURRENT_MANA = ArsMagicaManager.createSavedObject(FloatSerializer.INSTANCE);
	static final SavedObject<Float> CURRENT_MANA_FATIGUE = ArsMagicaManager.createSavedObject(FloatSerializer.INSTANCE);
	static final SavedObject<Integer> CURRENT_LEVEL = ArsMagicaManager.createSavedObject(IntegerSerializer.INSTANCE);
	static final SavedObject<Float> CURRENT_XP = ArsMagicaManager.createSavedObject(FloatSerializer.INSTANCE);
	static final SavedObject<Integer> CURRENT_SUMMONS = ArsMagicaManager.createSavedObject(IntegerSerializer.INSTANCE);
	static final SavedObject<Integer> HEAL_COOLDOWN = ArsMagicaManager.createSavedObject(IntegerSerializer.INSTANCE);
	static final SavedObject<Integer> AFFINITY_HEAL_COOLDOWN = ArsMagicaManager.createSavedObject(IntegerSerializer.INSTANCE);
	static final SavedObject<Double> MARK_X = ArsMagicaManager.createSavedObject(DoubleSerializer.INSTANCE);
	static final SavedObject<Double> MARK_Y = ArsMagicaManager.createSavedObject(DoubleSerializer.INSTANCE);
	static final SavedObject<Double> MARK_Z = ArsMagicaManager.createSavedObject(DoubleSerializer.INSTANCE);
	static final SavedObject<Integer> MARK_DIMENSION = ArsMagicaManager.createSavedObject(IntegerSerializer.INSTANCE);
	static final SavedObject<Optional<ItemStack>> CONTENGENCY_STACK = ArsMagicaManager.createSavedObject(ItemStackSerializer.INSTANCE);
	static final SavedObject<Boolean> IS_SHRUNK = ArsMagicaManager.createSavedObject(BooleanSerializer.INSTANCE);
	static final SavedObject<Boolean> IS_INVERTED = ArsMagicaManager.createSavedObject(BooleanSerializer.INSTANCE);
	static final SavedObject<Boolean> DISABLE_GRAVITY = ArsMagicaManager.createSavedObject(BooleanSerializer.INSTANCE);
	static final SavedObject<Float> FALL_PROTECTION = ArsMagicaManager.createSavedObject(FloatSerializer.INSTANCE);
	static final SavedObject<String> CONTENGENCY_TYPE = ArsMagicaManager.createSavedObject(StringSerializer.INSTANCE);
	
	static final SavedObject<String> AFFINITY = ArsMagicaManager.createSavedObject(StringSerializer.INSTANCE);
	static final SavedObject<HashMap<Affinity, Double>> AFFINITY_DATA = ArsMagicaManager.createSavedObject(AffinityMapSerializer.INSTANCE);
	static final SavedObject<HashMap<SkillPoint, Integer>> POINT_TIER = ArsMagicaManager.createSavedObject(SkillPointMapSerializer.INSTANCE);
	static final SavedObject<HashMap<Skill, Boolean>> SKILL = ArsMagicaManager.createSavedObject(SkillMapSerializer.INSTANCE);
	
	static final SavedObject<HashMap<String, Integer>> COOLDOWNS = ArsMagicaManager.createSavedObject(StringIntegerMapSerializer.INSTANCE);
	static final SavedObject<HashMap<String, Boolean>> ABILITY_BOOLEAN = ArsMagicaManager.createSavedObject(StringBooleanMapSerializer.INSTANCE);
	static final SavedObject<HashMap<String, Float>> ABILITY_FLOAT = ArsMagicaManager.createSavedObject(StringFloatMapSerializer.INSTANCE);
	
	static final SavedObject<Boolean> REVERSE_INPUT = ArsMagicaManager.createSavedObject(BooleanSerializer.INSTANCE);
	
	static final SavedObject<Float> SHRINK_PCT = ArsMagicaManager.createSavedObject(FloatSerializer.INSTANCE);
	static final SavedObject<Float> PREV_SHRINK_PCT = ArsMagicaManager.createSavedObject(FloatSerializer.INSTANCE);
	static final SavedObject<Float> FLIP_ROTATION = ArsMagicaManager.createSavedObject(FloatSerializer.INSTANCE);
	static final SavedObject<Float> PREV_FLIP_ROTATION = ArsMagicaManager.createSavedObject(FloatSerializer.INSTANCE);

	public static final SavedObject<ArrayList<String>> COMPENDIUM = ArsMagicaManager.createSavedObject(StringArraySerializer.INSTANCE);

	static final SavedObject<Float> MANA_SHIELD = ArsMagicaManager.createSavedObject(FloatSerializer.INSTANCE);
	static final SavedObject<Float> DIMINISHING_RETURNS = ArsMagicaManager.createSavedObject(FloatSerializer.INSTANCE);
	static final SavedObject<Float> TK_DISTANCE = ArsMagicaManager.createSavedObject(FloatSerializer.INSTANCE);
	
}