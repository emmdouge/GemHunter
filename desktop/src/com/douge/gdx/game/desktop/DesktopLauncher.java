package com.douge.gdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;
import com.douge.gdx.game.DougeGdxGame;

public class DesktopLauncher 
{
	public static boolean rebuildAtlas = false;
	private static boolean drawDebugOutline = true;
	private static int maxTextureAtlasWidth = 1024;
	private static int maxTextureAtlasHeight = 1024;
	
	public static void main (String[] arg) 
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.title = "Douge GDX Game";
		
		config.width = 1280;
		config.height = 720;
		
		if(rebuildAtlas)
		{
			Settings settings = new Settings();
			settings.maxHeight = maxTextureAtlasHeight;
			settings.maxWidth = maxTextureAtlasWidth;
			settings.duplicatePadding = false;
			settings.debug = drawDebugOutline ;
			String source = "assets-raw/images";
			String destination = "../core/assets/images";
			TexturePacker.process(settings, source, destination, "canyonbunny.pack");
		}
		
		new LwjglApplication(new DougeGdxGame(), config);
	}
}
