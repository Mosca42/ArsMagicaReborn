package amreborn.spell.component;

import java.util.EnumSet;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.Sets;

import amreborn.api.affinity.Affinity;
import amreborn.api.spell.SpellComponent;
import amreborn.api.spell.SpellModifiers;
import amreborn.buffs.BuffEffectSilence;
import amreborn.defs.ItemDefs;
import amreborn.defs.PotionEffectsDefs;
import amreborn.items.ItemOre;
import amreborn.utils.SpellUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Silence extends SpellComponent{

	@Override
	public Object[] getRecipe(){
		return new Object[]{
				Blocks.WOOL,
				new ItemStack(ItemDefs.itemOre, 1, ItemOre.META_ARCANEASH),
				Blocks.JUKEBOX,
				Blocks.WOOL
		};
	}

	@Override
	public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, EnumFacing blockFace, double impactX, double impactY, double impactZ, EntityLivingBase caster){
		return false;
	}

	@Override
	public boolean applyEffectEntity(ItemStack stack, World world, EntityLivingBase caster, Entity target){
		if (target instanceof EntityLivingBase && target.isNonBoss()){
			if (!world.isRemote)
				((EntityLivingBase)target).addPotionEffect(new BuffEffectSilence(PotionEffectsDefs.default_buff_duration, SpellUtils.countModifiers(SpellModifiers.BUFF_POWER, stack)));
			return true;
		}
		return false;
	}
	
	@Override
	public EnumSet<SpellModifiers> getModifiers() {
		return EnumSet.of(SpellModifiers.BUFF_POWER);
	}

	@Override
	public float manaCost(EntityLivingBase caster){
		return 800;
	}

	@Override
	public ItemStack[] reagents(EntityLivingBase caster){
		return new ItemStack[]{
				new ItemStack(ItemDefs.itemOre, 1, ItemOre.META_PURIFIED_VINTEUM)
		};
	}

	@Override
	public void spawnParticles(World world, double x, double y, double z,
							   EntityLivingBase caster, Entity target, Random rand,
							   int colorModifier){

	}

	@Override
	public Set<Affinity> getAffinity(){
		return Sets.newHashSet(Affinity.WATER, Affinity.ENDER);
	}

	@Override
	public float getAffinityShift(Affinity affinity){
		return 0.01f;
	}
	
	@Override
	public void encodeBasicData(NBTTagCompound tag, Object[] recipe) {}

}
