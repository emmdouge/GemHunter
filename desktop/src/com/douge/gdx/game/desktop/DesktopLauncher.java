package com.douge.gdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.douge.gdx.game.DougeGdxGame;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;

public class DesktopLauncher 
{
	private static final boolean rebuildAtlas = true;
	private static final boolean drawDebugOutline = true;
	
	public static void main (String[] arg) 
	{
		
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		if (rebuildAtlas) 
		{
			Settings settings = new Settings();
			settings.maxWidth = 1024;
			settings.maxHeight = 1024;
			settings.duplicatePadding = false;
			settings.debug = drawDebugOutline;
			TexturePacker.process(settings, "assets-raw/images/survivor", "../core/assets/images", "survivor");
		}
		new LwjglApplication(new DougeGdxGame(), config);
	}
}
