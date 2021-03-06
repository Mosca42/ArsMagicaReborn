package amreborn.spell.shape;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;

import amreborn.ArsMagicaReborn;
import amreborn.api.affinity.Affinity;
import amreborn.api.spell.SpellModifier;
import amreborn.api.spell.SpellModifiers;
import amreborn.api.spell.SpellShape;
import amreborn.defs.BlockDefs;
import amreborn.defs.ItemDefs;
import amreborn.items.ItemOre;
import amreborn.items.ItemSpellBase;
import amreborn.particles.AMBeam;
import amreborn.particles.AMParticle;
import amreborn.particles.AMParticleDefs;
import amreborn.particles.ParticleMoveOnHeading;
import amreborn.power.PowerTypes;
import amreborn.spell.SpellCastResult;
import amreborn.spell.modifier.Colour;
import amreborn.utils.AffinityShiftUtils;
import amreborn.utils.MathUtilities;
import amreborn.utils.SpellUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Beam extends SpellShape{

	private final HashMap<Integer, AMBeam> beams;

	public Beam(){
		beams = new HashMap<>();
	}
	@Override
	public SpellCastResult beginStackStage(ItemSpellBase item, ItemStack stack, EntityLivingBase caster, EntityLivingBase target, World world, double x, double y, double z, EnumFacing side, boolean giveXP, int useCount){
		boolean shouldApplyEffectBlock = useCount % 5 == 0;
		boolean shouldApplyEffectEntity = useCount % 10 == 0;

		double range = SpellUtils.getModifiedDouble_Add(stack, caster, target, world, SpellModifiers.RANGE);
		boolean targetWater = SpellUtils.modifierIsPresent(SpellModifiers.TARGET_NONSOLID_BLOCKS, stack);
		RayTraceResult mop = item.getMovingObjectPosition(caster, world, range, true, targetWater);

		SpellCastResult result = null;
		Vec3d beamHitVec = null;
		Vec3d spellVec = null;

		if (mop == null){
			beamHitVec = MathUtilities.extrapolateEntityLook(world, caster, range);
			spellVec = beamHitVec;
		}else if (mop.typeOfHit == RayTraceResult.Type.ENTITY){
			if (shouldApplyEffectEntity && !world.isRemote){
				Entity e = mop.entityHit;
				if (e instanceof EntityDragonPart && ((EntityDragonPart)e).entityDragonObj instanceof EntityLivingBase)
					e = (EntityLivingBase)((EntityDragonPart)e).entityDragonObj;
				result = SpellUtils.applyStageToEntity(stack, caster, world, e, giveXP);
				if (result != SpellCastResult.SUCCESS){
					return result;
				}
			}
			float rng = (float)mop.hitVec.distanceTo(new Vec3d(caster.posX, caster.posY, caster.posZ));
			beamHitVec = MathUtilities.extrapolateEntityLook(world, caster, rng);
			spellVec = beamHitVec;
		}else{
			if (shouldApplyEffectBlock && !world.isRemote){
				result = SpellUtils.applyStageToGround(stack, caster, world, mop.getBlockPos(), mop.sideHit, mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord, giveXP);
				if (result != SpellCastResult.SUCCESS){
					return result;
				}
			}
			beamHitVec = mop.hitVec;
			spellVec = new Vec3d(mop.getBlockPos());
		}

		if (world.isRemote && beamHitVec != null){
			AMBeam beam = beams.get(caster.getEntityId());
			double startX = caster.posX;
			double startY = caster.posY + caster.getEyeHeight() - 0.2f;
			double startZ = caster.posZ;
			Affinity affinity = AffinityShiftUtils.getMainShiftForStack(stack);

			int color = -1;
			if (SpellUtils.modifierIsPresent(SpellModifiers.COLOR, stack)){
				ArrayList<SpellModifier> mods = SpellUtils.getModifiersForStage(stack, -1);
				for (SpellModifier mod : mods){
					if (mod instanceof Colour){
						color = (int)mod.getModifier(SpellModifiers.COLOR, null, null, null, stack.getTagCompound());
					}
				}
			}

			if (beam != null){
				if (!beam.isAlive() || caster.getDistanceSq(beam.getPosX(), beam.getPosY(), beam.getPosZ()) > 4){
					beams.remove(caster.getEntityId());
				}else{
					beam.setBeamLocationAndTarget(startX, startY, startZ, beamHitVec.xCoord, beamHitVec.yCoord, beamHitVec.zCoord);
				}
			}else{
				if (affinity.equals(Affinity.LIGHTNING)){
					ArsMagicaReborn.proxy.particleManager.BoltFromEntityToPoint(world, caster, beamHitVec.xCoord, beamHitVec.yCoord, beamHitVec.zCoord, 1, color == -1 ? affinity.getColor() : color);
				}else{
					beam = (AMBeam)ArsMagicaReborn.proxy.particleManager.BeamFromEntityToPoint(world, caster, beamHitVec.xCoord, beamHitVec.yCoord, beamHitVec.zCoord, color == -1 ? affinity.getColor() : color);
					if (beam != null){
						if (Minecraft.getMinecraft().gameSettings.thirdPersonView == 0)
							beam.setFirstPersonPlayerCast();
						beams.put(caster.getEntityId(), beam);
					}
				}
			}
			for (int i = 0; i < ArsMagicaReborn.config.getGFXLevel() + 1; ++i){
				AMParticle particle = (AMParticle)ArsMagicaReborn.proxy.particleManager.spawn(world, AMParticleDefs.getParticleForAffinity(affinity), beamHitVec.xCoord, beamHitVec.yCoord, beamHitVec.zCoord);
				if (particle != null){
					particle.setMaxAge(2);
					particle.setParticleScale(0.1f);
					particle.setIgnoreMaxAge(false);
					if (color != -1)
						particle.setRGBColorI(color);
					particle.AddParticleController(new ParticleMoveOnHeading(particle, world.rand.nextDouble() * 360, world.rand.nextDouble() * 360, world.rand.nextDouble() * 0.2 + 0.02f, 1, false));
				}
			}
		}

		if (result != null && spellVec != null && (mop.typeOfHit == RayTraceResult.Type.ENTITY ? shouldApplyEffectEntity : shouldApplyEffectBlock)){
			//ItemStack newItemStack = SpellUtils.instance.popStackStage(stack);
			return SpellUtils.applyStackStage(stack, caster, target, spellVec.xCoord, spellVec.yCoord, spellVec.zCoord, mop != null ? mop.sideHit : null, world, true, giveXP, 0);
		}else{
			return SpellCastResult.SUCCESS_REDUCE_MANA;
		}
	}
	
	@Override
	public EnumSet<SpellModifiers> getModifiers() {
		return EnumSet.of(SpellModifiers.RANGE, SpellModifiers.TARGET_NONSOLID_BLOCKS);
	}
	
	@Override
	public boolean isChanneled(){
		return true;
	}

	@Override
	public Object[] getRecipe(){
		return new Object[]{
				ItemDefs.standardFocus,
				new ItemStack(ItemDefs.itemOre, 1, ItemOre.META_BLUE_TOPAZ),
				new ItemStack(ItemDefs.itemOre, 1, ItemOre.META_BLUE_TOPAZ),
				new ItemStack(ItemDefs.itemOre, 1, ItemOre.META_PURIFIED_VINTEUM),
				BlockDefs.aum,
				"E:" + PowerTypes.MANA.ID(), 500
		};
	}

	@Override
	public float manaCostMultiplier(ItemStack spellStack){
		return 0.1f;
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

	@Override
	public void encodeBasicData(NBTTagCompound tag, Object[] recipe) {}
}
