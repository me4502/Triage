package com.me4502.LudumDare31.injuries;

public enum InjuryType {

	SPRAIN("Sprain"),
	BROKEN_BONE("Broken Bone"),
	CUT("Cut"),
	DISMEMBERED("Dismembered"),
	EBOLA_SYMPTOMS("Ebola Symptoms"),
	HEART_ATTACK("Heart Attack"),
	ALLERGY("Allergic Reaction"),
	POISON("Poisoned"),
	OVERDOSE("Overdose"),
	STROKE("Stroke"),
	ASTHMA("Asthma"),
	BURN("Burn"),
	PAIN("Pain"),
	VOMITTING("Vomitting"),
	ALCOHOL("Alcohol"),
	HEMORRAGING("Hemorraging"),
	HEAD_TRAUMA("Head Trauma"),
	RESPIRATORY_ISSUES("Respiratory Issues"),
	DELIRIUM("Delirium"),
	GUN_WOUND("Gun Wound"),
	MISCELLANEOUS("Miscellaneous");

	public String name;

	InjuryType(String name) {
		this.name = name;
	}
}