package amreborn.buffs;

import amreborn.defs.PotionEffectsDefs;

public class BuffEffectTrueSight extends BuffEffectShield{

	public BuffEffectTrueSight(int duration, int amplifier) {
		super(PotionEffectsDefs.trueSight, duration, amplifier);
	}

	@Override
	protected String spellBuffName(){
		return "True Sight";
	}

}
