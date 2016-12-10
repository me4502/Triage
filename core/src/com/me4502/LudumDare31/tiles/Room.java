package com.me4502.LudumDare31.tiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.me4502.LudumDare31.LudumDare31;
import com.me4502.LudumDare31.entities.Patient.Patient;
import com.me4502.LudumDare31.entities.StaffType;

public class Room {

	public Sprite door;

	private Patient patient;

	private Sprite bar;

	public Room(Sprite door) {
		this.door = door;

		bar = new Sprite(new Texture("data/entities/bar.png"));

		bar.setSize(0.92f, 0.16f);
		bar.setPosition(door.getX() + 0.04f, door.getY() + 0.15f);
	}

	public void render(SpriteBatch batch) {
		if(patient != null) {
			if(patient.health < 0) {
				//They dead.
				LudumDare31.instance.map.score -= 25 * patient.injury.getSeverity().getScoreMultiplier();
				patient = null;
				return;
			} else if(patient.health > 1) {
				//They are cured.
				LudumDare31.instance.map.score += 10 * patient.injury.getSeverity().getScoreMultiplier();
				patient = null;
				return;
			}

			bar.setColor(MathUtils.lerp(1f, 0f, patient.getHealth()), MathUtils.lerp(0f, 1f, patient.getHealth()), 0f, 1f);
			bar.setBounds(0, 0, patient.getHealth() * 0.92f, 0.16f);
			bar.setPosition(door.getX() + 0.04f, door.getY() + 0.15f);
			bar.draw(batch);

			patient.health -= 0.001f;
		}
	}

	public void treat(StaffType type) {
		if(type == StaffType.NURSE) {
			patient.health += 0.001f;
		} else if(type == StaffType.DOCTOR) {
			patient.health += 0.002f;
		}
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public boolean isEmpty() {
		return patient == null;
	}
}