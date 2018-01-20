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
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class Wall extends SpellShape{

	@Override
	public Object[] getRecipe(){
		return new Object[]{
				new ItemStack(ItemDefs.itemOre, 1, ItemOre.META_VINTEUM),
				BlockDefs.magicWall,
				Blocks.COBBLESTONE_WALL,
				Blocks.OAK_FENCE,
				BlockDefs.magicWall,
				"E:*", 2500
		};
	}

	@Override
	public SpellCastResult beginStackStage(ItemSpellBase item, ItemStack stack, EntityLivingBase caster, EntityLivingBase target, World world, double x, double y, double z, EnumFacing side, boolean giveXP, int useCount){
		if (world.isRemote) return SpellCastResult.SUCCESS;
		int radius = SpellUtils.getModifiedInt_Mul(3, stack, caster, target, world, SpellModifiers.RADIUS);
		double gravity = SpellUtils.getModifiedDouble_Add(0, stack, caster, target, world, SpellModifiers.GRAVITY);
		int duration = SpellUtils.getModifiedInt_Mul(100, stack, caster, target, world, SpellModifiers.DURATION);

		EntitySpellEffect wall = new EntitySpellEffect(world);
		wall.setRadius(radius);
		wall.setTicksToExist(duration);
		wall.setGravity(gravity);
		wall.SetCasterAndStack(caster, stack);
		wall.setPosition(x, y, z);
		wall.setWall(caster.rotationYaw);
		world.spawnEntity(wall);
		return SpellCastResult.SUCCESS;
	}
	
	@Override
	public EnumSet<SpellModifiers> getModifiers() {
		return EnumSet.of(SpellModifiers.RADIUS, SpellModifiers.GRAVITY, SpellModifiers.DURATION, SpellModifiers.COLOR, SpellModifiers.TARGET_NONSOLID_BLOCKS);
	}


	@Override
	public boolean isChanneled(){
		return false;
	}

	@Override
	public float manaCostMultiplier(ItemStack spellStack){
		return 2.5f;
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
