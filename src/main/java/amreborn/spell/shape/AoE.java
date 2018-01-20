package amreborn.spell.shape;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import amreborn.ArsMagicaReborn;
import amreborn.api.affinity.Affinity;
import amreborn.api.spell.SpellModifier;
import amreborn.api.spell.SpellModifiers;
import amreborn.api.spell.SpellShape;
import amreborn.defs.ItemDefs;
import amreborn.entity.EntitySpellProjectile;
import amreborn.items.ItemOre;
import amreborn.items.ItemSpellBase;
import amreborn.particles.AMParticle;
import amreborn.particles.AMParticleDefs;
import amreborn.particles.ParticleFadeOut;
import amreborn.particles.ParticleLeaveParticleTrail;
import amreborn.particles.ParticleMoveOnHeading;
import amreborn.power.PowerTypes;
import amreborn.spell.SpellCastResult;
import amreborn.spell.modifier.Colour;
import amreborn.utils.AffinityShiftUtils;
import amreborn.utils.SpellUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AoE extends SpellShape{

	@Override
	public SpellCastResult beginStackStage(ItemSpellBase item, ItemStack stack, EntityLivingBase caster, EntityLivingBase target, World world, double x, double y, double z, EnumFacing side, boolean giveXP, int useCount){
		double radius = SpellUtils.getModifiedDouble_Add(1, stack, caster, target, world, SpellModifiers.RADIUS);
		List<Entity> entities = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(x - radius, y - radius, z - radius, x + radius, y + radius, z + radius));

		boolean appliedToAtLeastOneEntity = false;

		for (Entity e : entities){
			if (e == caster || e instanceof EntitySpellProjectile) continue;
			if (e instanceof EntityDragonPart && ((EntityDragonPart)e).entityDragonObj instanceof EntityLivingBase)
				e = (EntityLivingBase)((EntityDragonPart)e).entityDragonObj;
			if (SpellUtils.applyStageToEntity(stack, caster, world, e, giveXP) == SpellCastResult.SUCCESS)
				appliedToAtLeastOneEntity = true;
		}
		
		BlockPos pos = new BlockPos(x, y, z);
		
		if (side != null) {
			switch (side) {
			case UP:
			case DOWN:
				if (world.isRemote)
					spawnAoEParticles(stack, caster, world, x + 0.5f, y + ((side.equals(EnumFacing.DOWN)) ? 0.5f : (target != null ? target.getEyeHeight() : -2.0f)), z + 0.5f, (int)radius);
				int gravityMagnitude = SpellUtils.countModifiers(SpellModifiers.GRAVITY, stack);
				return applyStageHorizontal(stack, caster, world, pos, side, (int)Math.floor(radius), gravityMagnitude, giveXP);
			case NORTH:
			case SOUTH:
				if (world.isRemote)
					spawnAoEParticles(stack, caster, world, x + 0.5f, y - 1, z + 0.5f, (int)radius);
				return applyStageVerticalZ(stack, caster, world, pos, side, (int)Math.floor(radius), giveXP);
			case EAST:
			case WEST:
				if (world.isRemote)
					spawnAoEParticles(stack, caster, world, x + 0.5f, y - 1, z + 0.5f, (int)radius);
				return applyStageVerticalX(stack, caster, world, pos, side, (int)Math.floor(radius), giveXP);
			}
		} else {
			if (world.isRemote)
				spawnAoEParticles(stack, caster, world, x, y - 1, z, (int)radius);
			int gravityMagnitude = SpellUtils.countModifiers(SpellModifiers.GRAVITY, stack);
			return applyStageHorizontal(stack, caster, world, pos, null, (int)Math.floor(radius), gravityMagnitude, giveXP);
		}

		if (appliedToAtLeastOneEntity){
			if (world.isRemote)
				spawnAoEParticles(stack, caster, world, x, y + 1, z, (int)radius);
			return SpellCastResult.SUCCESS;
		}

		return SpellCastResult.EFFECT_FAILED;
	}
	
	@Override
	public EnumSet<SpellModifiers> getModifiers() {
		return EnumSet.of(SpellModifiers.RADIUS, SpellModifiers.GRAVITY);
	}


	private void spawnAoEParticles(ItemStack stack, EntityLivingBase caster, World world, double x, double y, double z, int radius){
		String pfxName = AMParticleDefs.getParticleForAffinity(AffinityShiftUtils.getMainShiftForStack(stack));
		float speed = 0.08f * radius;

		int color = 0xFFFFFF;
		if (SpellUtils.modifierIsPresent(SpellModifiers.COLOR, stack)){
			ArrayList<SpellModifier> mods = SpellUtils.getModifiersForStage(stack, -1);
			for (SpellModifier mod : mods){
				if (mod instanceof Colour){
					color = (int)mod.getModifier(SpellModifiers.COLOR, null, null, null, stack.getTagCompound());
				}
			}
		}

		for (int i = 0; i < 360; i += ArsMagicaReborn.config.FullGFX() ? 20 : ArsMagicaReborn.config.LowGFX() ? 40 : 60){
			AMParticle effect = (AMParticle)ArsMagicaReborn.proxy.particleManager.spawn(world, pfxName, x, y + 1.5f, z);
			if (effect != null){
				effect.setIgnoreMaxAge(true);
				effect.AddParticleController(new ParticleMoveOnHeading(effect, i, 0, speed, 1, false));
				effect.setRGBColorI(color);
				effect.AddParticleController(new ParticleFadeOut(effect, 1, false).setFadeSpeed(0.05f).setKillParticleOnFinish(true));
				effect.AddParticleController(
						new ParticleLeaveParticleTrail(effect, pfxName, false, 5, 1, false)
								.addControllerToParticleList(new ParticleFadeOut(effect, 1, false).setFadeSpeed(0.1f).setKillParticleOnFinish(true))
								.setParticleRGB_I(color)
								.addRandomOffset(0.2f, 0.2f, 0.2f)
				);
			}
		}
	}

	private SpellCastResult applyStageHorizontal(ItemStack stack, EntityLivingBase caster, World world, BlockPos pos, EnumFacing face, int radius, int gravityMagnitude, boolean giveXP){

		for (int i = -radius; i <= radius; ++i){
			for (int j = -radius; j <= radius; ++j){
				BlockPos lookPos = pos.add(i, 0, j);
				int searchDist = 0;
				if (gravityMagnitude > 0){
					while (world.isAirBlock(lookPos) && searchDist < gravityMagnitude){
						pos.down();
						searchDist++;
					}
				}
				if (world.isAirBlock(lookPos)) continue;
				SpellCastResult result = SpellUtils.applyStageToGround(stack, caster, world, lookPos, face == null ? EnumFacing.UP : face, lookPos.getX(), lookPos.getY(), lookPos.getZ(), giveXP);
				if (result != SpellCastResult.SUCCESS)
					return result;
			}
		}
		return SpellCastResult.SUCCESS;
	}

	private SpellCastResult applyStageVerticalX(ItemStack stack, EntityLivingBase caster, World world, BlockPos pos, EnumFacing face, int radius, boolean giveXP){
		for (int i = -radius; i <= radius; ++i){
			for (int j = -radius; j <= radius; ++j){
				BlockPos lookPos = pos.add(0, j, i);
				if (world.isAirBlock(lookPos)) continue;
				SpellCastResult result = SpellUtils.applyStageToGround(stack, caster, world, lookPos, face, lookPos.getX(), lookPos.getY(), lookPos.getZ(), giveXP);
				if (result != SpellCastResult.SUCCESS)
					return result;
			}
		}
		return SpellCastResult.SUCCESS;
	}

	private SpellCastResult applyStageVerticalZ(ItemStack stack, EntityLivingBase caster, World world, BlockPos pos, EnumFacing face, int radius, boolean giveXP){
		for (int i = -radius; i <= radius; ++i){
			for (int j = -radius; j <= radius; ++j){
				BlockPos lookPos = pos.add(i, j, 0);
				if (world.isAirBlock(lookPos)) continue;
				SpellCastResult result = SpellUtils.applyStageToGround(stack, caster, world, lookPos, face, lookPos.getX(), lookPos.getY(), lookPos.getZ(), giveXP);
				if (result != SpellCastResult.SUCCESS)
					return result;
			}
		}
		return SpellCastResult.SUCCESS;
	}

	@Override
	public boolean isChanneled(){
		return false;
	}

	@Override
	public Object[] getRecipe(){
		return new Object[]{
				new ItemStack(ItemDefs.itemOre, 1, ItemOre.META_MOONSTONE),
				AffinityShiftUtils.getEssenceForAffinity(Affinity.AIR),
				String.format("E:%d", PowerTypes.MANA.ID()), 1000,
				Blocks.TNT
		};
	}

	@Override
	public float manaCostMultiplier(ItemStack spellStack){
		int multiplier = 2;
		int radiusMods = 0;
		int stages = SpellUtils.numStages(spellStack);
		for (int i = SpellUtils.currentStage(spellStack); i < stages; ++i){
			if (!SpellUtils.getShapeForStage(spellStack, i).equals(this)) continue;

			ArrayList<SpellModifier> mods = SpellUtils.getModifiersForStage(spellStack, i);
			for (SpellModifier modifier : mods){
				if (modifier.getAspectsModified().contains(SpellModifiers.RADIUS)){
					radiusMods++;
				}
			}
		}
		return multiplier * (radiusMods + 1);
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
