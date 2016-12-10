package com.me4502.LudumDare31.entities.Patient;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.me4502.LudumDare31.entities.Entity;
import com.me4502.LudumDare31.injuries.Injury;

public class Patient extends Entity {
	private PatientType type;
	public Injury injury;

	public float health = 1f;

	public Patient(PatientType type, Injury injury) {
		super(type.getTexture());

		this.type = type;
		this.injury = injury;

		setSize(2, 1);

		health *= injury.getSeverity().getHealthMultiplier();
	}

	public float getHealth() {
		return health;
	}

	@Override
	public void render(SpriteBatch top, SpriteBatch left, SpriteBatch right) {
		//Don't render.
	}
}