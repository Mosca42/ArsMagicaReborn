package amreborn.buffs;

import amreborn.defs.PotionEffectsDefs;

public class BuffEffectMagicShield extends BuffEffectShield{

	public BuffEffectMagicShield(int duration,
								 int amplifier){
		super(PotionEffectsDefs.magicShield, duration, amplifier);
	}

	@Override
	protected String spellBuffName(){
		return "Magic Shield";
	}

}
