package amreborn.spell.shape;

import java.util.EnumSet;

import amreborn.api.spell.SpellModifiers;
import amreborn.api.spell.SpellShape;
import amreborn.defs.ItemDefs;
import amreborn.items.ItemOre;
import amreborn.items.ItemSpellBase;
import amreborn.spell.SpellCastResult;
import amreborn.utils.SpellUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class Touch extends SpellShape{

	@Override
	public SpellCastResult beginStackStage(ItemSpellBase item, ItemStack stack, EntityLivingBase caster, EntityLivingBase target, World world, double x, double y, double z, EnumFacing side, boolean giveXP, int useCount){
		if (target != null){
			Entity e = target;
			if (e instanceof EntityDragonPart && ((EntityDragonPart)e).entityDragonObj instanceof EntityLivingBase)
				e = (EntityLivingBase)((EntityDragonPart)e).entityDragonObj;

			SpellCastResult result = SpellUtils.applyStageToEntity(stack, caster, world, e, giveXP);
			return result;
		}
		
		boolean targetWater = SpellUtils.modifierIsPresent(SpellModifiers.TARGET_NONSOLID_BLOCKS, stack);
		RayTraceResult mop = item.getMovingObjectPosition(caster, world, 2.5f, true, targetWater);
		if (mop == null){
			return SpellCastResult.EFFECT_FAILED;
		}else{
			if (mop.typeOfHit == RayTraceResult.Type.ENTITY){
				Entity e = mop.entityHit;
				if (e instanceof EntityDragonPart && ((EntityDragonPart)e).entityDragonObj instanceof EntityLivingBase)
					e = (EntityLivingBase)((EntityDragonPart)e).entityDragonObj;
				SpellCastResult result = SpellUtils.applyStageToEntity(stack, caster, world, (target == null) ? e : target, giveXP);
				if (result != SpellCastResult.SUCCESS){
					return result;
				}
				return SpellUtils.applyStackStage(stack, caster, target, mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord, null, world, true, giveXP, 0);
			}else{
				SpellCastResult result = SpellUtils.applyStageToGround(stack, caster, world, mop.getBlockPos(), mop.sideHit, mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord, giveXP);
				if (result != SpellCastResult.SUCCESS){
					return result;
				}
				return SpellUtils.applyStackStage(stack, caster, target, mop.getBlockPos().getX(), mop.getBlockPos().getY(), mop.getBlockPos().getZ(), mop.sideHit, world, true, giveXP, 0);
			}
		}
	}
	
	@Override
	public EnumSet<SpellModifiers> getModifiers() {
		return EnumSet.of(SpellModifiers.TARGET_NONSOLID_BLOCKS);
	}


	@Override
	public boolean isChanneled(){
		return false;
	}

	@Override
	public Object[] getRecipe(){
		return new Object[]{
				new ItemStack(ItemDefs.itemOre, 1, ItemOre.META_VINTEUM),
				Items.FEATHER,
				Items.FISH,
				Items.CLAY_BALL
		};
	}

	@Override
	public float manaCostMultiplier(ItemStack spellStack){
		return 1;
	}

	@Override
	public boolean isTerminusShape(){
		return false;
	}

	@Override
	public boolean isPrincipumShape(){
		return false;
	}

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

	@Override
	public void encodeBasicData(NBTTagCompound tag, Object[] recipe) {}
}
