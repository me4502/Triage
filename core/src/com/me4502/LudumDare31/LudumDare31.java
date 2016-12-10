package com.me4502.LudumDare31;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.me4502.LudumDare31.entities.Bubble;
import com.me4502.LudumDare31.entities.Patient.HospitalBed;
import com.me4502.LudumDare31.tiles.Map;
import com.me4502.LudumDare31.tiles.Room;

public class LudumDare31 extends ApplicationAdapter implements InputProcessor {

	public static LudumDare31 instance;

	public SpriteBatch floorBatch;
	public SpriteBatch leftWallBatch;
	public SpriteBatch rightWallBatch;
	public SpriteBatch bufferBatch;
	public SpriteBatch fontBatch;

	public Texture[] floors;
	public Texture wall_clean;
	public Texture reception_left;
	public Texture reception_right;
	public Texture poster;
	public Texture door_clean;
	public Texture bed_top;
	public Texture bed_side;
	public Texture bed_end;
	public Texture nurse_bubble;
	public Texture doctor_bubble;
	public Texture button;

	public Sound backgroundAudio;
	public Sound doorNoise;

	OrthographicCamera cam;

	final Matrix4 floorMatrix = new Matrix4();
	final Matrix4 wallMatrix = new Matrix4();
	final Matrix4 spriteMatrix = new Matrix4();

	FrameBuffer fbo;

	public Map map;

	public BitmapFont font;

	@Override
	public void create () {

		instance = this;

		floorBatch = new SpriteBatch();
		leftWallBatch = new SpriteBatch();
		rightWallBatch = new SpriteBatch();
		bufferBatch = new SpriteBatch();
		fontBatch = new SpriteBatch();

		fbo = new FrameBuffer(Format.RGBA8888, (int)(Gdx.graphics.getWidth() * 2f), (int)(Gdx.graphics.getHeight() * 2f), false);

		Texture cleanFloor = new Texture("data/tiles/floor_clean.png");

		floors = new Texture[]{cleanFloor,cleanFloor,cleanFloor,cleanFloor,cleanFloor,cleanFloor,cleanFloor,cleanFloor, new Texture("data/tiles/floor_green.png"), new Texture("data/tiles/floor_blood.png")};
		wall_clean = new Texture("data/tiles/wall_clean.png");
		reception_left = new Texture("data/tiles/reception_left.png");
		reception_right = new Texture("data/tiles/reception_right.png");
		door_clean = new Texture("data/tiles/door_clean.png");
		bed_top = new Texture("data/entities/bed_top.png");
		bed_side = new Texture("data/entities/bed_side.png");
		bed_end = new Texture("data/entities/bed_end.png");

		poster = new Texture("data/tiles/poster.png");

		nurse_bubble = new Texture("data/entities/nurse_cross.png");
		doctor_bubble = new Texture("data/entities/doctor_cross.png");
		button = new Texture("data/entities/button.png");

		backgroundAudio = Gdx.audio.newSound(Gdx.files.internal("data/audio/background_music.mp3"));
		doorNoise = Gdx.audio.newSound(Gdx.files.internal("data/audio/door.ogg"));

		font = new BitmapFont(Gdx.files.internal("data/fonts/mytype.fnt"), Gdx.files.internal("data/fonts/mytype.png"), false);

		cam = new OrthographicCamera(10, 10 * (Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth()));
		cam.position.set(9.5f, 5, 9.5f);
		cam.direction.set(-1, -1, -1);
		cam.near = 0.1f;
		cam.far = 100;

		floorMatrix.setToRotation(new Vector3(1, 0, 0), 90);
		wallMatrix.setToRotation(new Vector3(1, 0, 0), 180);

		spriteMatrix.setToRotation(new Vector3(0, 1, 0),  -Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth());

		Gdx.input.setInputProcessor(this);

		map = new Map(11,11);

		backgroundAudio.loop();

		cam.zoom = 1.6f;
	}

	@Override
	public void render () {

		spriteMatrix.setToRotation(new Vector3(0,1,0), 25);
		spriteMatrix.rotate(new Vector3(0,0,1), -10);

		cam.update();

		floorBatch.setProjectionMatrix(cam.combined);
		floorBatch.setTransformMatrix(floorMatrix);

		leftWallBatch.setProjectionMatrix(cam.combined);
		leftWallBatch.setTransformMatrix(wallMatrix.cpy().rotate(new Vector3(0,1,0), -90f));

		rightWallBatch.setProjectionMatrix(cam.combined);
		rightWallBatch.setTransformMatrix(wallMatrix);

		fbo.begin();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		map.render(floorBatch, leftWallBatch, rightWallBatch);

		fbo.end();

		bufferBatch.begin();

		TextureRegion region = new TextureRegion(fbo.getColorBufferTexture());

		region.flip(false, true);

		bufferBatch.disableBlending();
		bufferBatch.draw(region, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		bufferBatch.enableBlending();

		bufferBatch.end();
	}

	@Override
	public void dispose() {

		fbo.dispose();
		bufferBatch.dispose();
		rightWallBatch.dispose();
		leftWallBatch.dispose();
		floorBatch.dispose();
	}

	final Plane xzPlane = new Plane(new Vector3(0, 0, 1), 0);
	final Vector3 intersection = new Vector3();
	public Sprite lastSelectedTile = null;

	@Override
	public boolean keyDown(int keycode) {

		System.out.println(Gdx.graphics.getFramesPerSecond());
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

		if(map.hasLost) {

			map = new Map(11,11);
			return false;
		}

		Ray ray = cam.getPickRay(screenX, screenY);
		Intersector.intersectRayPlane(ray, xzPlane, intersection);

		float x = intersection.x;
		float y = -intersection.y;

		Sprite sprite = null;
		float shortest = 15f;

		for(Sprite sp : map.entities) {
			if(sp instanceof Bubble) {

				float dist = (float) Math.sqrt(Math.pow(Math.abs(x - sp.getX()), 2) + Math.pow(Math.abs(y - sp.getY()), 2));
				if(dist < shortest && dist < 1) {
					shortest = dist;
					sprite = sp;
				}
			}
		}

		if(sprite != null)
			lastSelectedTile = sprite;

		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {

		if(lastSelectedTile != null) {

			Ray ray = cam.getPickRay(screenX, screenY);
			Intersector.intersectRayPlane(ray, xzPlane, intersection);

			float x = intersection.x;
			float y = -intersection.y - 8;

			Sprite sprite = null;
			Room room = null;

			float shortest = 15f;

			for(Sprite sp : map.entities) {
				if(sp instanceof HospitalBed) {

					float dist = (float) Math.sqrt(Math.pow(Math.abs(x - sp.getX()), 2) + Math.pow(Math.abs(y - sp.getY()), 2));

					if(dist < shortest && dist < 3) {
						shortest = dist;
						sprite = sp;
					}
				}
			}

			for(Room sp : map.rooms) {

				float dist = (float) Math.sqrt(Math.pow(Math.abs(x - sp.door.getX()), 2) + Math.pow(Math.abs(7 + y - sp.door.getY()), 2));

				if(dist < shortest && dist < 1) {
					shortest = dist;
					room = sp;
					sprite = null;
				}
			}

			if(sprite != null) {
				((Bubble) lastSelectedTile).setBed(sprite);
			} else if(room != null) {
				((Bubble) lastSelectedTile).setRoom(room);
			} else {
				((Bubble) lastSelectedTile).snapBack();
			}
		}

		last.set(-1, -1, -1);
		lastSelectedTile = null;

		return false;
	}

	final Vector3 curr = new Vector3();
	final Vector3 last = new Vector3(-1, -1, -1);
	final Vector3 delta = new Vector3();

	@Override
	public boolean touchDragged(int x, int y, int pointer) {

		Ray pickRay = cam.getPickRay(x, y);
		Intersector.intersectRayPlane(pickRay, xzPlane, curr);

		if(!(last.x == -1 && last.y == -1 && last.z == -1)) {
			pickRay = cam.getPickRay(last.x, last.y);
			Intersector.intersectRayPlane(pickRay, xzPlane, delta);
			delta.sub(curr);
			if(lastSelectedTile != null)
				lastSelectedTile.setPosition(curr.x - 0.4f, -curr.y - 0.4f);
			//cam.position.add(delta.x, delta.y, delta.z);
		}
		last.set(x, y, 0);

		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {

		//cam.zoom += amount*0.1;

		if(cam.zoom < 0.1) cam.zoom = 0.1f;

		return false;
	}
}
