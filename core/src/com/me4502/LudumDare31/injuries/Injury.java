package com.me4502.LudumDare31.injuries;

import java.util.concurrent.ThreadLocalRandom;

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
		InjuryType type = InjuryType.values()[ThreadLocalRandom.current().nextInt(InjuryType.values().length)];
		InjurySeverity severity = InjurySeverity.convertFloatToSeverity(ThreadLocalRandom.current().nextFloat());

		return new Injury(type, severity);
	}

	@Override
	public String toString() {
		return type.name() + "[" + severity.name() + "]";
	}
}