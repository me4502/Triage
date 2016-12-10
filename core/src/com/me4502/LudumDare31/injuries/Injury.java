package com.me4502.LudumDare31.injuries;

import com.me4502.LudumDare31.LudumDare31;

public class Injury {
	private final InjuryType type;
	private final InjurySeverity severity;

	private Injury(InjuryType type, InjurySeverity severity) {
		this.type = type;
		this.severity = severity;
	}

	public InjuryType getType() {
		return type;
	}

	public InjurySeverity getSeverity() {
		return severity;
	}

	public static Injury generateInjury() {
		InjuryType type = InjuryType.values()[LudumDare31.RANDOM.nextInt(InjuryType.values().length)];
		InjurySeverity severity = InjurySeverity.convertFloatToSeverity(LudumDare31.RANDOM.nextFloat());

		return new Injury(type, severity);
	}

	@Override
	public String toString() {
		return type.name() + "[" + severity.name() + "]";
	}
}