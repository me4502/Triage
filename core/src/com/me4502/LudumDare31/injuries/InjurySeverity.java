package com.me4502.LudumDare31.injuries;

public enum InjurySeverity {

	TRIVIAL("Trivial", 0.5f, 0.9f, 0.1f),
	MINOR("Minor", 1.0f, 0.6f, 0.6f),
	MAJOR("Major", 2.5f, 0.4f, 0.29f),
	CRITICAL("Critical", 5.0f, 0.2f, 0.01f);

	public String name;
	float multiplier;
	float healthMultiplier;
	float chance;

	InjurySeverity(String name, float multiplier, float healthMultiplier, float chance) {
		this.name = name;
		this.multiplier = multiplier;
		this.healthMultiplier = healthMultiplier;
		this.chance = chance;
	}

	public float getScoreMultiplier() {

		return multiplier;
	}

	public float getHealthMultiplier() {

		return healthMultiplier;
	}

	public static InjurySeverity convertFloatToSeverity(float input) {

		float tmp = 0f;

		for(InjurySeverity sev : values()) {
			if(input < tmp+sev.chance)
				return sev;

			tmp += sev.chance;
		}

		return values()[values().length-1];
	}
}