package amreborn.spell.shape;

import java.util.EnumSet;

import amreborn.api.spell.SpellModifiers;
import amreborn.api.spell.SpellShape;
import amreborn.defs.BlockDefs;
import amreborn.defs.ItemDefs;
import amreborn.entity.EntitySpellEffect;
import amreborn.items.ItemOre;
import amreborn.items.ItemSpellBase;
import amreborn.spell.SpellCastResult;
import amreborn.utils.SpellUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class Wave extends SpellShape{

	@Override
	public Object[] getRecipe(){
		return new Object[]{
				new ItemStack(ItemDefs.itemOre, 1, ItemOre.META_VINTEUM),
				BlockDefs.magicWall,
				"E:*", 25000
		};
	}

	@Override
	public SpellCastResult beginStackStage(ItemSpellBase item, ItemStack stack, EntityLivingBase caster, EntityLivingBase target, World world, double x, double y, double z, EnumFacing side, boolean giveXP, int useCount){
		if (world.isRemote) return SpellCastResult.SUCCESS;
		double radius = SpellUtils.getModifiedDouble_Add(1, stack, caster, target, world, SpellModifiers.RADIUS);
		int duration = SpellUtils.getModifiedInt_Mul(20, stack, caster, target, world, SpellModifiers.DURATION);
		double speed = SpellUtils.getModifiedDouble_Add(1f, stack, caster, target, world, SpellModifiers.SPEED) * 0.5f;
		int gravityModifiers = SpellUtils.countModifiers(SpellModifiers.GRAVITY, stack);
		boolean hasPiercing = SpellUtils.modifierIsPresent(SpellModifiers.PIERCING, stack);

		EntitySpellEffect wave = new EntitySpellEffect(world);
		wave.setRadius((float)radius);
		wave.setTicksToExist(duration);
		wave.SetCasterAndStack(caster, stack);
		wave.setPosition(x, y + 1, z);
		wave.setWave(caster.rotationYaw, (float)speed);
		wave.noClip = hasPiercing;
		wave.setGravity(gravityModifiers * 0.5f);
		world.spawnEntity(wave);
		return SpellCastResult.SUCCESS;
	}
	
	@Override
	public EnumSet<SpellModifiers> getModifiers() {
		return EnumSet.of(SpellModifiers.RADIUS, SpellModifiers.GRAVITY, SpellModifiers.DURATION, SpellModifiers.COLOR, SpellModifiers.SPEED, SpellModifiers.PIERCING, SpellModifiers.TARGET_NONSOLID_BLOCKS);
	}

	@Override
	public boolean isChanneled(){
		return false;
	}

	@Override
	public float manaCostMultiplier(ItemStack spellStack){
		return 3f;
	}

	@Override
	public boolean isTerminusShape(){
		return false;
	}

	@Override
	public boolean isPrincipumShape(){
		return true;
	}

	@Override
	public void encodeBasicData(NBTTagCompound tag, Object[] recipe) {}

//	@Override
//	public String getSoundForAffinity(Affinity affinity, ItemStack stack, World world){
//		switch (affinity){
//		case AIR:
//			return ArsMagicaReborn.MODID + ":spell.cast.air";
//		case ARCANE:
//			return ArsMagicaReborn.MODID + ":spell.cast.arcane";
//		case EARTH:
//			return ArsMagicaReborn.MODID + ":spell.cast.earth";
//		case ENDER:
//			return ArsMagicaReborn.MODID + ":spell.cast.ender";
//		case FIRE:
//			return ArsMagicaReborn.MODID + ":spell.cast.fire";
//		case ICE:
//			return ArsMagicaReborn.MODID + ":spell.cast.ice";
//		case LIFE:
//			return ArsMagicaReborn.MODID + ":spell.cast.life";
//		case LIGHTNING:
//			return ArsMagicaReborn.MODID + ":spell.cast.lightning";
//		case NATURE:
//			return ArsMagicaReborn.MODID + ":spell.cast.nature";
//		case WATER:
//			return ArsMagicaReborn.MODID + ":spell.cast.water";
//		case NONE:
//		default:
//			return ArsMagicaReborn.MODID + ":spell.cast.none";
//		}
//	}
}
