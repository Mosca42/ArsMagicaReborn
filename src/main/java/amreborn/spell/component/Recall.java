package amreborn.spell.component;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.Sets;

import amreborn.ArsMagicaReborn;
import amreborn.api.affinity.Affinity;
import amreborn.api.extensions.IEntityExtension;
import amreborn.api.spell.SpellComponent;
import amreborn.api.spell.SpellModifiers;
import amreborn.defs.ItemDefs;
import amreborn.defs.PotionEffectsDefs;
import amreborn.extensions.EntityExtension;
import amreborn.items.ItemOre;
import amreborn.particles.AMParticle;
import amreborn.particles.ParticleExpandingCollapsingRingAtPoint;
import amreborn.utils.SelectionUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class Recall extends SpellComponent {

	@Override
	public boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, EnumFacing blockFace, double impactX, double impactY, double impactZ, EntityLivingBase caster) {
		return false;
	}

	@Override
	public boolean applyEffectEntity(ItemStack stack, World world, EntityLivingBase caster, Entity target) {

		if (!(target instanceof EntityLivingBase)) {
			return false;
		}

		if (caster.isPotionActive(PotionEffectsDefs.astralDistortion) || ((EntityLivingBase) target).isPotionActive(PotionEffectsDefs.astralDistortion)) {
			if (caster instanceof EntityPlayer)
				((EntityPlayer) caster).sendMessage(new TextComponentString(I18n.translateToLocal("am2.tooltip.cantTeleport")));
			return false;
		}

		IEntityExtension casterProperties = EntityExtension.For(caster);
		if (casterProperties.getMarkDimensionID() == -512) {
			if (caster instanceof EntityPlayer && !world.isRemote)
				((EntityPlayer) caster).sendMessage(new TextComponentString(I18n.translateToLocal("am2.tooltip.noMark")));
			return false;
		} else if (casterProperties.getMarkDimensionID() != caster.dimension) {
			if (caster instanceof EntityPlayer && !world.isRemote)
				((EntityPlayer) caster).sendMessage(new TextComponentString(I18n.translateToLocal("am2.tooltip.diffDimMark")));
			return false;
		}
		if (!world.isRemote) {
			((EntityLivingBase) target).setPositionAndUpdate(casterProperties.getMarkX(), casterProperties.getMarkY(), casterProperties.getMarkZ());
		}
		return true;
	}

	@Override
	public EnumSet<SpellModifiers> getModifiers() {
		return EnumSet.noneOf(SpellModifiers.class);
	}

	private boolean handleRitualReagents(ItemStack[] ritualRunes, World world, BlockPos pos, EntityLivingBase caster, Entity target) {

		boolean hasVinteumDust = false;
		for (ItemStack stack : ritualRunes) {
			if (stack.getItem() == ItemDefs.itemOre && stack.getItemDamage() == ItemOre.META_VINTEUM) {
				hasVinteumDust = true;
				break;
			}
		}

		if (hasVinteumDust) {
			ArrayList<Integer> copy = new ArrayList<Integer>();
			for (ItemStack stack : ritualRunes) {
				if (stack.getItem() == ItemDefs.rune && stack.getItemDamage() <= 16) {
					copy.add(stack.getItemDamage());
				}
			}
			int[] newRunes = new int[copy.size()];
			for (int i = 0; i < copy.size(); i++) {
				newRunes[i] = copy.get(i);
			}
			EntityPlayer player = SelectionUtils.getPlayersForRuneSet(newRunes);

			if (player == null) {
				if (caster instanceof EntityPlayer && !world.isRemote)
					((EntityPlayer) caster).sendMessage(new TextComponentString("am2.tooltip.noMatchingPlayer"));
				return false;
			} else if (player == caster) {
				if (caster instanceof EntityPlayer && !world.isRemote)
					((EntityPlayer) caster).sendMessage(new TextComponentString("am2.tooltip.cantSummonSelf"));
				return false;
			}
		}
		return false;
	}

	@Override
	public float manaCost(EntityLivingBase caster) {
		return 500;
	}

	@Override
	public ItemStack[] reagents(EntityLivingBase caster) {
		return null;
	}

	@Override
	public void spawnParticles(World world, double x, double y, double z, EntityLivingBase caster, Entity target, Random rand, int colorModifier) {
		for (int i = 0; i < 25; ++i) {
			AMParticle particle = (AMParticle) ArsMagicaReborn.proxy.particleManager.spawn(world, "arcane", x, y - 1, z);
			if (particle != null) {
				particle.addRandomOffset(1, 0, 1);
				particle.AddParticleController(new ParticleExpandingCollapsingRingAtPoint(particle, x, y - 1, z, 0.1, 3, 0.3, 1, false).setCollapseOnce());
				particle.setMaxAge(20);
				particle.setParticleScale(0.2f);
				if (colorModifier > -1) {
					particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255.0f, ((colorModifier >> 8) & 0xFF) / 255.0f, (colorModifier & 0xFF) / 255.0f);
				}
			}
		}
	}

	@Override
	public Set<Affinity> getAffinity() {
		return Sets.newHashSet(Affinity.ARCANE);
	}

	@Override
	public Object[] getRecipe() {
		return new Object[] { new ItemStack(ItemDefs.rune, 1, EnumDyeColor.ORANGE.getDyeDamage()), Items.COMPASS, new ItemStack(Items.MAP), Items.ENDER_PEARL };
	}

	@Override
	public float getAffinityShift(Affinity affinity) {
		return 0.1f;
	}

	@Override
	public void encodeBasicData(NBTTagCompound tag, Object[] recipe) {
	}
}
