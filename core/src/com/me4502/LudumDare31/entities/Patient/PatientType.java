package com.me4502.LudumDare31.entities.Patient;

import com.badlogic.gdx.graphics.Texture;
import com.me4502.LudumDare31.LudumDare31;

public enum PatientType {
	DERPY("Derpy", new Texture("data/entities/patients/derpy.png")),
	AMBER("Amber", new Texture("data/entities/patients/amber.png")),
	LEE("Lee", new Texture("data/entities/patients/lee.png")),
	MEI("Mei", new Texture("data/entities/patients/mei.png")),
	T("T", new Texture("data/entities/patients/t.png")),
	BALDY("Baldy", new Texture("data/entities/patients/baldy.png"));

	private String name;
	private Texture texture;

	PatientType(String name, Texture texture) {
		this.name = name;
		this.texture = texture;
	}

	public Texture getTexture() {
		return texture;
	}

	public static PatientType getRandom() {
		return values()[LudumDare31.RANDOM.nextInt(values().length)];
	}
}