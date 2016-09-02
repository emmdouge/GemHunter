package com.douge.gdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.douge.gdx.game.DougeGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.title = "Douge GDX Game";
		
		config.width = 1280;
		config.height = 720;
		
		new LwjglApplication(new DougeGdxGame(), config);
	}
}
