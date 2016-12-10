package com.me4502.LudumDare31.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Staff extends Entity {

	StaffType type;

	public Staff(Texture texture, StaffType type) {
		super(texture);

		this.type = type;
	}

	@Override
	public void render(SpriteBatch top, SpriteBatch left, SpriteBatch right) {
		//TODO
	}

	public static enum StaffType {

		DOCTOR,
		NURSE;
	}
}