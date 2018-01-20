package amreborn.spell.component;

import java.util.EnumSet;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.Sets;

import amreborn.api.affinity.Affinity;
import amreborn.api.spell.SpellComponent;
import amreborn.api.spell.SpellModifiers;
import amreborn.defs.ItemDefs;
import amreborn.items.ItemCore;
import amreborn.items.ItemOre;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class Daylight extends SpellComponent{

	@Override
	public Object[] getRecipe(){
		return new Object[]{
				new ItemStack(ItemDefs.itemOre, 1, ItemOre.META_SUNSTONE),
				Items.CLOCK,
				new ItemStack(ItemDefs.core, 1, ItemCore.META_PURE)
		};
	}

	private boolean setDayTime(World world){
		if (world.isDaytime())
			return false;
		if (!world.isRemote){
			long curTime = ((WorldServer)world).getWorldTime();
			int day = (int)Math.ceil((curTime / 24000));
			((WorldServer)world).setWorldTime(day * 24000);
		}
		return true;
	}
	
	@Override
	public EnumSet<SpellModifiers> getModifiers() {
		return EnumSet.noneOf(SpellModifiers.class);
	}

	@Override
	public boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, EnumFacing blockFace, double impactX, double impactY, double impactZ, EntityLivingBase caster){
		return setDayTime(world);
	}

	@Override
	public boolean applyEffectEntity(ItemStack stack, World world, EntityLivingBase caster, Entity target){
		return setDayTime(world);
	}

	@Override
	public float manaCost(EntityLivingBase caster){
		return 25000;
	}

	@Override
	public ItemStack[] reagents(EntityLivingBase caster){
		return null;
	}

	@Override
	public void spawnParticles(World world, double x, double y, double z, EntityLivingBase caster, Entity target, Random rand, int colorModifier){

	}

	@Override
	public Set<Affinity> getAffinity(){
		return Sets.newHashSet(Affinity.NONE);
	}

	@Override
	public float getAffinityShift(Affinity affinity){
		return 0;
	}

	@Override
	public void encodeBasicData(NBTTagCompound tag, Object[] recipe) {
		// TODO Auto-generated method stub
		
	}

}
