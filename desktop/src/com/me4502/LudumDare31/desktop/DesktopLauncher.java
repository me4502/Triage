package com.me4502.LudumDare31.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.me4502.LudumDare31.LudumDare31;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.width = 800;
		config.height = 600;

		config.resizable = false;

		new LwjglApplication(new LudumDare31(), config);
	}
}
