package com.me4502.LudumDare31.injuries;

import java.util.Random;

public class Injury {

	private static final Random random = new Random();

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

		InjuryType type = InjuryType.values()[random.nextInt(InjuryType.values().length)];
		InjurySeverity severity = InjurySeverity.convertFloatToSeverity(random.nextFloat());

		return new Injury(type, severity);
	}

	@Override
	public String toString() {

		return type.name() + "[" + severity.name() + "]";
	}
}