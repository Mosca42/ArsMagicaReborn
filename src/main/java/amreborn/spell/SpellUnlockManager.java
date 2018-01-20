package amreborn.spell;


import java.util.ArrayList;

import amreborn.ArsMagicaReborn;
import amreborn.api.ArsMagicaAPI;
import amreborn.api.SpellRegistry;
import amreborn.api.event.SpellCastEvent;
import amreborn.api.skill.Skill;
import amreborn.api.spell.AbstractSpellPart;
import amreborn.api.spell.SpellComponent;
import amreborn.api.spell.SpellModifier;
import amreborn.api.spell.SpellModifiers;
import amreborn.defs.SkillDefs;
import amreborn.extensions.EntityExtension;
import amreborn.extensions.SkillData;
import amreborn.utils.SpellUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SpellUnlockManager{

	private ArrayList<UnlockEntry> entries;

	public SpellUnlockManager(){
		init();
	}

	@SubscribeEvent
	public void onSpellCast(SpellCastEvent.Pre event){
		if (event.entityLiving instanceof EntityPlayer){
			if (EntityExtension.For(event.entityLiving).getCurrentMana() < event.manaCost)
				return;
			for (UnlockEntry entry : entries){
				//check unlocks
				if (!event.entityLiving.world.isRemote){
					if (entry.willSpellUnlock(event.spell)){
						entry.unlockFor((EntityPlayer)event.entityLiving);
					}
				}
			}
		}
	}

	public void init(){
		entries = new ArrayList<UnlockEntry>();
		entries.add(new UnlockEntry(ArsMagicaAPI.getSkillRegistry().getObject(new ResourceLocation(ArsMagicaReborn.MODID + ":falling_star")),
				SpellRegistry.getComponentFromName(ArsMagicaReborn.MODID + ":magic_damage"),
				SpellRegistry.getModifierFromName(ArsMagicaReborn.MODID + ":gravity"),
				SpellRegistry.getComponentFromName(ArsMagicaReborn.MODID + ":astral_distortion")));
		entries.add(new UnlockEntry(ArsMagicaAPI.getSkillRegistry().getObject(new ResourceLocation(ArsMagicaReborn.MODID + ":blizzard")),
				SpellRegistry.getComponentFromName(ArsMagicaReborn.MODID + ":storm"),
				SpellRegistry.getComponentFromName(ArsMagicaReborn.MODID + ":frost_damage"),
				SpellRegistry.getComponentFromName(ArsMagicaReborn.MODID + ":freeze"),
				SpellRegistry.getModifierFromName(ArsMagicaReborn.MODID + ":damage")));
		entries.add(new UnlockEntry(ArsMagicaAPI.getSkillRegistry().getObject(new ResourceLocation(ArsMagicaReborn.MODID + ":fire_rain")),
				SpellRegistry.getComponentFromName(ArsMagicaReborn.MODID + ":storm"),
				SpellRegistry.getComponentFromName(ArsMagicaReborn.MODID + ":fire_damage"),
				SpellRegistry.getComponentFromName(ArsMagicaReborn.MODID + ":ignition"),
				SpellRegistry.getModifierFromName(ArsMagicaReborn.MODID + ":damage")));
		entries.add(new UnlockEntry(ArsMagicaAPI.getSkillRegistry().getObject(new ResourceLocation(ArsMagicaReborn.MODID + ":mana_blast")),
				SpellRegistry.getComponentFromName(ArsMagicaReborn.MODID + ":magic_damage"),
				SpellRegistry.getModifierFromName(ArsMagicaReborn.MODID + ":damage")));
		entries.add(new UnlockEntry(ArsMagicaAPI.getSkillRegistry().getObject(new ResourceLocation(ArsMagicaReborn.MODID + ":dismembering")),
				SpellRegistry.getModifierFromName(ArsMagicaReborn.MODID + ":piercing"),
				SpellRegistry.getModifierFromName(ArsMagicaReborn.MODID + ":damage")));

		entries.add(new UnlockEntry(ArsMagicaAPI.getSkillRegistry().getObject(new ResourceLocation(ArsMagicaReborn.MODID + ":mana_link")),
				SpellRegistry.getComponentFromName(ArsMagicaReborn.MODID + ":mana_drain"),
				SpellRegistry.getComponentFromName(ArsMagicaReborn.MODID + ":entangle")));
		entries.add(new UnlockEntry(ArsMagicaAPI.getSkillRegistry().getObject(new ResourceLocation(ArsMagicaReborn.MODID + ":mana_shield")),
				SpellRegistry.getComponentFromName(ArsMagicaReborn.MODID + ":shield"),
				SpellRegistry.getComponentFromName(ArsMagicaReborn.MODID + ":reflect"),
				SpellRegistry.getComponentFromName(ArsMagicaReborn.MODID + ":life_tap")));
		entries.add(new UnlockEntry(ArsMagicaAPI.getSkillRegistry().getObject(new ResourceLocation(ArsMagicaReborn.MODID + ":buff_power")),
				SpellRegistry.getComponentFromName(ArsMagicaReborn.MODID + ":haste"),
				SpellRegistry.getComponentFromName(ArsMagicaReborn.MODID + ":slowfall"),
				SpellRegistry.getComponentFromName(ArsMagicaReborn.MODID + ":swift_swim"),
				SpellRegistry.getComponentFromName(ArsMagicaReborn.MODID + ":gravity_well"),
				SpellRegistry.getComponentFromName(ArsMagicaReborn.MODID + ":leap")));

		entries.add(new UnlockEntry(ArsMagicaAPI.getSkillRegistry().getObject(new ResourceLocation(ArsMagicaReborn.MODID + ":daylight")),
				SpellRegistry.getComponentFromName(ArsMagicaReborn.MODID + ":true_sight"),
				SpellRegistry.getComponentFromName(ArsMagicaReborn.MODID + ":divine_intervention"),
				SpellRegistry.getComponentFromName(ArsMagicaReborn.MODID + ":light")));
		entries.add(new UnlockEntry(ArsMagicaAPI.getSkillRegistry().getObject(new ResourceLocation(ArsMagicaReborn.MODID + ":moonrise")),
				SpellRegistry.getComponentFromName(ArsMagicaReborn.MODID + ":night_vision"),
				SpellRegistry.getComponentFromName(ArsMagicaReborn.MODID + ":ender_intervention"),
				SpellRegistry.getModifierFromName(ArsMagicaReborn.MODID + ":lunar")));
		entries.add(new UnlockEntry(ArsMagicaAPI.getSkillRegistry().getObject(new ResourceLocation(ArsMagicaReborn.MODID + ":prosperity")),
				SpellRegistry.getComponentFromName(ArsMagicaReborn.MODID + ":dig"),
				SpellRegistry.getModifierFromName(ArsMagicaReborn.MODID + ":feather_touch"),
				SpellRegistry.getModifierFromName(ArsMagicaReborn.MODID + ":mining_power")));

		entries.add(new UnlockEntry(SkillDefs.SHIELD_OVERLOAD,
				SpellRegistry.getComponentFromName(ArsMagicaReborn.MODID + ":mana_shield"),
				SpellRegistry.getComponentFromName(ArsMagicaReborn.MODID + ":mana_drain")));
		
	}

	class UnlockEntry{
		private Skill unlock;
		private AbstractSpellPart[] requiredComponents;

		public UnlockEntry(Skill unlock, AbstractSpellPart... components){
			this.unlock = unlock;
			this.requiredComponents = components;
		}

		public boolean partIsInStage(ItemStack spell, AbstractSpellPart part, int stage){
			boolean bool = false;
			if (part == null)
				return false;
			for (SpellComponent comp : SpellUtils.getComponentsForStage(spell, -1))
				if (part.getClass().isInstance(comp))
					bool =  true;

			if (part instanceof SpellComponent && !bool){
				return false;
			}else if (part instanceof SpellModifier){
				for (SpellModifiers modifier : ((SpellModifier)part).getAspectsModified()){
					if (!SpellUtils.modifierIsPresent(modifier, spell)){
						return false;
					}
				}
			}
			return true;
		}

		public boolean willSpellUnlock(ItemStack spell){
			boolean found = true;
			for (AbstractSpellPart part : requiredComponents){
				if (!partIsInStage(spell, part, 0)){
					found = false;
					break;
				}
			}
			if (found)
				return true;
			return false;
		}

		public void unlockFor(EntityPlayer player){
			if (!player.world.isRemote){
				SkillData.For(player).unlockSkill(unlock.getID());
			}
		}
	}
}
