package com.me4502.LudumDare31.tiles;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.me4502.LudumDare31.LudumDare31;
import com.me4502.LudumDare31.entities.Bubble;
import com.me4502.LudumDare31.entities.Entity;
import com.me4502.LudumDare31.entities.Staff.StaffType;
import com.me4502.LudumDare31.entities.Patient.HospitalBed;
import com.me4502.LudumDare31.entities.Patient.Patient;
import com.me4502.LudumDare31.entities.Patient.PatientType;
import com.me4502.LudumDare31.injuries.Injury;

public class Map {

	Sprite[][] floorTiles;
	Sprite[] leftWallTiles;
	Sprite[] rightWallTiles;

	public List<Entity> entities;

	public Room[] rooms;

	public int score;

	public boolean hasLost = false;

	Random random;

	GlyphLayout glyphLayout = new GlyphLayout();

	public Map(int width, int height) {

		random = new Random();

		entities = new ArrayList<Entity>();

		floorTiles = new Sprite[width][height];

		for(int x = 0; x < floorTiles.length; x++) {
			for(int y = 0; y < floorTiles[x].length; y++) {

				floorTiles[x][y] = new Sprite(LudumDare31.instance.floors[random.nextInt(LudumDare31.instance.floors.length)]);
				floorTiles[x][y].setSize(1, 1);
				floorTiles[x][y].setPosition(x, y);
			}
		}

		rightWallTiles = new Sprite[width];

		rooms = new Room[rightWallTiles.length/2 ];

		for(int x = 0; x < rightWallTiles.length; x++) {

			if(x > 0 && x % 2 == 1) {
				rightWallTiles[x] = new Sprite(LudumDare31.instance.door_clean);
				rightWallTiles[x].setSize(1, 2.6f);
				rightWallTiles[x].setPosition(x, 0-2.6f);
				rightWallTiles[x].flip(false, true);
				rooms[(int) (Math.ceil(x/2f) - 1)] = new Room(rightWallTiles[x]);
			} else {
				rightWallTiles[x] = new Sprite(LudumDare31.instance.wall_clean);
				rightWallTiles[x].setSize(1, 2.6f);
				rightWallTiles[x].setPosition(x, 0-2.6f);
				rightWallTiles[x].flip(false, true);
			}
		}

		leftWallTiles = new Sprite[height];

		for(int x = 0; x < leftWallTiles.length; x++) {

			if(x == 1 || x == 2 || x == 6) {
				leftWallTiles[x] = new Sprite(x == 1 ? LudumDare31.instance.reception_left : x == 6 ? LudumDare31.instance.poster : LudumDare31.instance.reception_right);
				leftWallTiles[x].setSize(1, 2.6f);
				leftWallTiles[x].setPosition(x-leftWallTiles.length, 0-2.6f);
				leftWallTiles[x].flip(false, true);
			} else {
				leftWallTiles[x] = new Sprite(LudumDare31.instance.wall_clean);
				leftWallTiles[x].setSize(1, 2.6f);
				leftWallTiles[x].setPosition(x-leftWallTiles.length, 0-2.6f);
				leftWallTiles[x].flip(false, true);
			}
		}

		Entity ent = new HospitalBed(LudumDare31.instance.bed_top, new Patient(PatientType.getRandom(), Injury.generateInjury()));

		ent.setPosition(-8 + 0.1f,0 + 0.1f);

		entities.add(ent);

		for(int i = 0; i < 2; i++)
			entities.add(new Bubble(StaffType.DOCTOR, i));
		for(int i = 0; i < 2; i++)
			entities.add(new Bubble(StaffType.NURSE, i+2));
	}

	public void render(SpriteBatch floorBatch, SpriteBatch leftWallBatch, SpriteBatch rightWallBatch) {

		floorBatch.begin();
		for(int x = 0; x < floorTiles.length; x++) {
			for(int y = 0; y < floorTiles[x].length; y++) {

				Sprite tile = floorTiles[x][y];
				if(tile == null) continue;

				tile.draw(floorBatch);
			}
		}
		floorBatch.end();

		leftWallBatch.begin();

		for(int x = 0; x < leftWallTiles.length; x++) {

			leftWallTiles[x].draw(leftWallBatch);
		}

		leftWallBatch.end();

		rightWallBatch.begin();

		for(int x = 0; x < rightWallTiles.length; x++) {

			rightWallTiles[x].draw(rightWallBatch);
		}

		rightWallBatch.end();

		for(Entity ent : entities) {

			ent.render(floorBatch, leftWallBatch, rightWallBatch);
		}

		rightWallBatch.begin();

		for(Room room : rooms) {
			room.render(rightWallBatch);
		}

		rightWallBatch.end();

		Iterator<Entity> iter = entities.iterator();

		int bedCount = 0;

		while(iter.hasNext()) {

			Entity ent = iter.next();
			if(ent instanceof HospitalBed)
				if(((HospitalBed) ent).passenger == null)
					iter.remove();
				else
					bedCount ++;
		}

		if(!hasLost && bedCount == 0 && Math.random() > 0.95f) {
			Entity ent = new HospitalBed(LudumDare31.instance.bed_top, new Patient(PatientType.getRandom(), Injury.generateInjury()));

			ent.setPosition(-8 + 0.1f,0 + 0.1f);

			entities.add(0, ent);
		}

		LudumDare31.instance.fontBatch.begin();

		if(hasLost) {
			LudumDare31.instance.font.setColor(Color.WHITE);
			glyphLayout.setText(LudumDare31.instance.font, "Click to Replay");
			LudumDare31.instance.font.draw(LudumDare31.instance.fontBatch, "Click to Replay", Gdx.graphics.getWidth() / 2 - glyphLayout.width/2, 35);

			LudumDare31.instance.fontBatch.end();
		} else {
			LudumDare31.instance.font.setColor(Color.WHITE);
			glyphLayout.setText(LudumDare31.instance.font, "Score: " + score);
			LudumDare31.instance.font.draw(LudumDare31.instance.fontBatch, "Score: " + score, Gdx.graphics.getWidth() / 2 - glyphLayout.width/2, 35);

			LudumDare31.instance.fontBatch.end();
		}

		if(score < 0) {
			hasLost = true;
		}
	}
}