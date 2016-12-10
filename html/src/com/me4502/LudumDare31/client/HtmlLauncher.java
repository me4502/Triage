package com.me4502.LudumDare31.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.me4502.LudumDare31.LudumDare31;

public class HtmlLauncher extends GwtApplication {

	@Override
	public GwtApplicationConfiguration getConfig () {

		GwtApplicationConfiguration config = new GwtApplicationConfiguration(800,600);

		return config;
	}

	@Override
	public ApplicationListener createApplicationListener() {
		return new LudumDare31();
	}
}