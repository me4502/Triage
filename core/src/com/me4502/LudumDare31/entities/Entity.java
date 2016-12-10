package com.me4502.LudumDare31.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Entity extends Sprite {

	public Entity(Texture texture) {

		super(texture);
	}

	public abstract void render(SpriteBatch top, SpriteBatch left, SpriteBatch right);
}