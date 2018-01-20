package amreborn.spell.shape;

import java.util.EnumSet;

import amreborn.api.affinity.Affinity;
import amreborn.api.spell.SpellModifiers;
import amreborn.api.spell.SpellShape;
import amreborn.defs.BlockDefs;
import amreborn.defs.ItemDefs;
import amreborn.items.ItemOre;
import amreborn.items.ItemSpellBase;
import amreborn.spell.SpellCastResult;
import amreborn.spell.component.Attract;
import amreborn.spell.component.Repel;
import amreborn.spell.component.Telekinesis;
import amreborn.utils.AffinityShiftUtils;
import amreborn.utils.SpellUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class Channel extends SpellShape{

	@Override
	public SpellCastResult beginStackStage(ItemSpellBase item, ItemStack stack, EntityLivingBase caster, EntityLivingBase target, World world, double x, double y, double z, EnumFacing side, boolean giveXP, int useCount){
		boolean shouldApplyEffect = useCount % 10 == 0 || SpellUtils.componentIsPresent(stack, Telekinesis.class) || SpellUtils.componentIsPresent(stack, Attract.class) || SpellUtils.componentIsPresent(stack, Repel.class);
		if (shouldApplyEffect){
			SpellCastResult result = SpellUtils.applyStageToEntity(stack, caster, world, caster, giveXP);
			if (result != SpellCastResult.SUCCESS){
				return result;
			}
		}

		return SpellCastResult.SUCCESS;
	}

	@Override
	public boolean isChanneled(){
		return true;
	}

	@Override
	public Object[] getRecipe(){
		//Arcane Ash, Arcane Essence, Tarma Root, 500 any power
		return new Object[]{
				new ItemStack(ItemDefs.itemOre, 1, ItemOre.META_ARCANEASH),
				AffinityShiftUtils.getEssenceForAffinity(Affinity.ARCANE),
				BlockDefs.tarmaRoot,
				"E:*", 500
		};
	}

	@Override
	public float manaCostMultiplier(ItemStack spellStack){
		return 1;
	}

	@Override
	public boolean isTerminusShape(){
		return true;
	}

	@Override
	public boolean isPrincipumShape(){
		return false;
	}
	
	@Override
	public EnumSet<SpellModifiers> getModifiers() {
		return EnumSet.noneOf(SpellModifiers.class);
	}
	
	@Override
	public void encodeBasicData(NBTTagCompound tag, Object[] recipe) {}
	
//	@Override
//	public String getSoundForAffinity(Affinity affinity, ItemStack stack, World world){
//		switch (affinity){
//		case AIR:
//			return ArsMagicaReborn.MODID + ":spell.loop.air";
//		case ARCANE:
//			return ArsMagicaReborn.MODID + ":spell.loop.arcane";
//		case EARTH:
//			return ArsMagicaReborn.MODID + ":spell.loop.earth";
//		case ENDER:
//			return ArsMagicaReborn.MODID + ":spell.loop.ender";
//		case FIRE:
//			return ArsMagicaReborn.MODID + ":spell.loop.fire";
//		case ICE:
//			return ArsMagicaReborn.MODID + ":spell.loop.ice";
//		case LIFE:
//			return ArsMagicaReborn.MODID + ":spell.loop.life";
//		case LIGHTNING:
//			return ArsMagicaReborn.MODID + ":spell.loop.lightning";
//		case NATURE:
//			return ArsMagicaReborn.MODID + ":spell.loop.nature";
//		case WATER:
//			return ArsMagicaReborn.MODID + ":spell.loop.water";
//		case NONE:
//		default:
//			return ArsMagicaReborn.MODID + ":spell.loop.none";
//		}
//	}
}
