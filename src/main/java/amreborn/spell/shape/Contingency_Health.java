package amreborn.spell.shape;

import java.util.EnumSet;

import amreborn.api.affinity.Affinity;
import amreborn.api.spell.SpellModifiers;
import amreborn.api.spell.SpellShape;
import amreborn.defs.BlockDefs;
import amreborn.defs.ItemDefs;
import amreborn.extensions.EntityExtension;
import amreborn.items.ItemOre;
import amreborn.items.ItemSpellBase;
import amreborn.spell.ContingencyType;
import amreborn.spell.SpellCastResult;
import amreborn.utils.AffinityShiftUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class Contingency_Health extends SpellShape{

	@Override
	public Object[] getRecipe(){
		return new Object[]{
				Items.CLOCK,
				PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.HEALING),
				BlockDefs.tarmaRoot,
				new ItemStack(ItemDefs.itemOre, 1, ItemOre.META_BLUE_TOPAZ),
				"E:*", 5000,
				AffinityShiftUtils.getEssenceForAffinity(Affinity.LIFE)
		};
	}

	@Override
	public SpellCastResult beginStackStage(ItemSpellBase item, ItemStack stack, EntityLivingBase caster, EntityLivingBase target, World world, double x, double y, double z, EnumFacing side, boolean giveXP, int useCount){
		EntityExtension.For(target != null ? target : caster).setContingency(ContingencyType.HEALTH, stack);
		return SpellCastResult.SUCCESS;
	}

	@Override
	public boolean isChanneled(){
		return false;
	}

	@Override
	public float manaCostMultiplier(ItemStack spellStack){
		return 10;
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
	public EnumSet<SpellModifiers> getModifiers() {
		return EnumSet.noneOf(SpellModifiers.class);
	}
	
//
//	@Override
//	public String getSoundForAffinity(Affinity affinity, ItemStack stack, World world){
//		return ArsMagicaReborn.MODID + ":spell.contingency.contingency";
//	}

	@Override
	public void encodeBasicData(NBTTagCompound tag, Object[] recipe) {}
}