package com.me4502.LudumDare31.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.me4502.LudumDare31.LudumDare31;
import com.me4502.LudumDare31.entities.Staff.StaffType;
import com.me4502.LudumDare31.entities.Patient.HospitalBed;
import com.me4502.LudumDare31.tiles.Room;

public class Bubble extends Entity {

	StaffType type;

	Sprite button;

	Room boundRoom;
	Sprite boundSprite;

	float origX;

	public Bubble(StaffType type, int offset) {
		super(type == StaffType.DOCTOR ? LudumDare31.instance.doctor_bubble : LudumDare31.instance.nurse_bubble);

		setSize(0.8f, 0.8f);

		this.type = type;

		setY(-4);
		setX(origX = 4+offset);

		button = new Sprite(LudumDare31.instance.button);
		button.setSize(0.8f, 0.8f);

		button.setPosition(getX(), getY());
	}

	@Override
	public void render(SpriteBatch top, SpriteBatch left, SpriteBatch right) {

		//button.setPosition(getX(), getY());

		Matrix4 orig = right.getTransformMatrix().cpy();

		if(!equals(LudumDare31.instance.lastSelectedTile)) {
			if(boundRoom != null) {
				//setPosition(boundRoom.door.getX(), boundRoom.door.getY());
				button.setPosition(getX(), getY());
			} else if(boundSprite != null) {
				//setPosition(boundSprite.getX(), boundSprite.getY());
				button.setPosition(getX(), getY());
			}
		}

		if(boundSprite != null) {

			//Okay - Let's move.
			((HospitalBed) boundSprite).doMoveTick(this);

			if(boundSprite != null) {
				if(((HospitalBed) boundSprite).rotation == 0)
					setX(((HospitalBed) boundSprite).getX() - 7);
				right.getTransformMatrix().translate(0, 0, ((HospitalBed) boundSprite).leftDepthTransform + 7);

				if(((HospitalBed) boundSprite).passenger == null) {

					setStartPosition();
				}
			}
		}

		if(boundRoom != null) {

			if(!boundRoom.isEmpty()) {

				boundRoom.treat(type);
			} else
				setStartPosition();
		}

		right.begin();
		button.draw(right);
		this.draw(right);
		right.end();

		right.setTransformMatrix(orig);
	}

	public void snapBack() {

		setPosition(button.getX(), button.getY());
	}

	public void setStartPosition() {

		boundSprite = null;
		boundRoom = null;
		setY(-4);
		setX(origX);

		button.setPosition(getX(), getY());
	}

	public void setRoom(Room boundRoom) {

		boundSprite = null;
		this.boundRoom = boundRoom;

		button.setPosition(getX(), getY());
	}

	public void setBed(Sprite boundSprite) {

		boundRoom = null;
		this.boundSprite = boundSprite;

		button.setPosition(getX(), getY());
	}
}