package com.me4502.LudumDare31.entities.Patient;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.me4502.LudumDare31.LudumDare31;
import com.me4502.LudumDare31.entities.Bubble;
import com.me4502.LudumDare31.entities.Entity;
import com.me4502.LudumDare31.tiles.Room;

public class HospitalBed extends Entity {

	public Patient passenger;

	private Sprite back_side, back_end;
	private Sprite side, end;

	public int rotation = 0;

	private Sprite bar;

	public HospitalBed(Texture texture, Patient passenger) {

		super(texture);
		this.passenger = passenger;

		passenger.setSize(2, 1);

		setSize(2, 1);

		side = new Sprite(LudumDare31.instance.bed_side);
		side.setSize(2, 0.9f);
		side.flip(false, true);

		end = new Sprite(LudumDare31.instance.bed_end);
		end.setSize(1, 0.9f);
		end.flip(false, true);

		back_side = new Sprite(LudumDare31.instance.bed_side);
		back_side.setSize(2, 0.9f);
		back_side.flip(false, true);

		back_end = new Sprite(LudumDare31.instance.bed_end);
		back_end.setSize(1, 0.9f);
		back_end.flip(false, true);

		setRotation(270);

		bar = new Sprite(new Texture("data/entities/bar.png"));

		bar.setSize(0.92f, 0.16f);
		bar.setPosition(side.getX() + 0.04f, side.getY() + 0.15f);
	}

	public void setRotation(int rotation) {

		this.rotation = rotation % 360;

		if(this.rotation >= 0 && this.rotation < 90) {
			side.setPosition(0, 0+1f);

			end.setPosition(0 + 2f, 0 + 2 + 1f);

			back_side.setPosition(0 + 1f, 0);

			back_end.setPosition(0 + 0f, 0 + 1f);
		} else if(this.rotation >= 90 && this.rotation < 180) {
			side.setPosition(0, 0+1f);

			end.setPosition(0 - 3f, 0 + 2 + 1f);

			back_side.setPosition(0 + 1f, 0);

			back_end.setPosition(0 - 1f, 0 + 1f);
		} else if(this.rotation >= 180 && this.rotation < 270) {

			side.setPosition(0, 0+1f);

			end.setPosition(0 + 2f, 0 + 2 + 1f);

			back_side.setPosition(0 + 1f, 0);

			back_end.setPosition(0 + 0f, 0 + 1f);
		} else if(this.rotation >= 270 && this.rotation < 360) {
			side.setPosition(0, 0+1f);

			end.setPosition(0 + 2f, 0 + 2 + 1f);

			back_side.setPosition(0 - 1f, 0);

			back_end.setPosition(0 + 0f, 0 + 1f);
		}
	}

	private Room destination = null;

	public void doMoveTick(final Bubble staff) {
		passenger.health += 0.001f;

		if(destination == null || !destination.isEmpty()) {

			for(Room room : LudumDare31.instance.map.rooms) {
				if(room.isEmpty()) {
					destination = room;
					break;
				}
			}
		}

		if(destination == null) return; //No Room.

		if(leftDepthTransform - destination.door.getY() < 1.2f && rotation == 270) {

			setX(getX() + 0.1f);
		} else {

			//Okay - so we are at the turn point.
			if(getX() - destination.door.getX() < (rotation == 270 ? -3.11f : -1.2f)) {

				setRotation(0);

				setX(getX() + 0.1f);
			} else {

				setRotation(270);

				if(leftDepthTransform - destination.door.getY() < 5) {

					setX(getX() + 0.1f);
				} else {

					LudumDare31.instance.doorNoise.play();

					destination.setPatient(passenger);
					//staff.setStartPosition();

					staff.setX(destination.door.getX() + 0.04f);
					staff.setY(destination.door.getY() + 0.5f);
					staff.setRoom(destination);

					passenger = null;
				}
			}
		}
	}

	public float leftDepthTransform = 0f;

	@Override
	public void render(SpriteBatch top, SpriteBatch left, SpriteBatch right) {
		if(passenger == null) return;

		passenger.health -= 0.001f;

		if(passenger.health < 0) {
			//They dead.

			LudumDare31.instance.map.score -= 50 * passenger.injury.getSeverity().getScoreMultiplier();
			passenger = null;

			return;
		}

		setRotation(rotation);

		passenger.setPosition(getX(), getY());

		Matrix4 rightOrig = right.getTransformMatrix().cpy();
		Matrix4 leftOrig = left.getTransformMatrix().cpy();
		Matrix4 topOrig = top.getTransformMatrix().cpy();

		if(rotation >= 0 && rotation < 90) {
			right.getTransformMatrix().translate(getX()-1f,0,-getY()).rotate(new Vector3(0,1,0), rotation);
			left.getTransformMatrix().translate(leftDepthTransform = -getY(),0, -getX()+1f).rotate(new Vector3(0,1,0), rotation);
			top.getTransformMatrix().translate(0,0f,-0f).rotate(new Vector3(0,0,1), rotation);
		} else if(rotation >= 90 && rotation < 180) {
			right.getTransformMatrix().translate(getY(), 0, -getX()+1f).rotate(new Vector3(0,1,0), rotation);
			left.getTransformMatrix().translate(leftDepthTransform = -getX()+1f,0, -getY()).rotate(new Vector3(0,1,0), rotation);
			top.getTransformMatrix().translate(1f, -0.2f, 0.2f).rotate(new Vector3(0,0,1), rotation);
		} else if(rotation >= 180 && rotation < 270) {
			right.getTransformMatrix().translate(getY(), 0, -getX()+1+0.2f).rotate(new Vector3(0,1,0), rotation);
			left.getTransformMatrix().translate(leftDepthTransform = -getX()+1+0.2f,0, -getY()).rotate(new Vector3(0,1,0), rotation);
			top.getTransformMatrix().translate(1f, -0.4f, 0.2f).rotate(new Vector3(0,0,1), rotation);
		} else if(rotation >= 270 && rotation < 360) {
			right.getTransformMatrix().translate(getY(), 0, getX()-1-0.2f).rotate(new Vector3(0,1,0), rotation);
			left.getTransformMatrix().translate(leftDepthTransform = getX()+1-0.2f,0,-getY()).rotate(new Vector3(0,1,0), rotation);
			top.getTransformMatrix().translate(-1f, 1f+0.2f, 1).rotate(new Vector3(0,0,1), rotation);
		}

		left.begin();

		back_end.draw(left);

		left.end();

		right.begin();

		back_side.draw(right);

		right.end();

		top.begin();

		this.draw(top);

		top.end();

		left.begin();

		end.draw(left);

		left.end();

		right.begin();

		side.draw(right);

		bar.setColor(MathUtils.lerp(1f, 0f, passenger.getHealth()), MathUtils.lerp(0f, 1f, passenger.getHealth()), 0f, 1f);
		bar.setBounds(0, 0, passenger.getHealth() * 0.92f, 0.16f);
		bar.setPosition(side.getX() + 0.04f, side.getY() + 0.15f);
		bar.draw(right);

		right.end();

		if(passenger != null) {
			top.begin();

			passenger.draw(top);

			top.end();
		}

		right.setTransformMatrix(rightOrig);
		left.setTransformMatrix(leftOrig);
		top.setTransformMatrix(topOrig);

		LudumDare31.instance.fontBatch.begin();

		if(destination == null) {
			LudumDare31.instance.font.setColor(Color.BLACK);
			//LudumDare31.instance.font.setScale(0.1f);
			LudumDare31.instance.font.draw(LudumDare31.instance.fontBatch, passenger.injury.getType().name, 155, 275);
			LudumDare31.instance.font.draw(LudumDare31.instance.fontBatch, "Severity: " + passenger.injury.getSeverity().name, 155, 235);
		}

		LudumDare31.instance.fontBatch.end();

		//setY((float) (getY() + Math.random() - 0.5f));
		//setX((float) (getX() + Math.random() - 0.5f));
		//rotation += new Random().nextInt(15);
	}
}
