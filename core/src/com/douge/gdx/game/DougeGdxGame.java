package com.douge.gdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.douge.gdx.game.assets.Assets;
import com.douge.gdx.game.screens.MenuScreen;

public class DougeGdxGame extends Game
{

	private WorldController worldController;
	private WorldRenderer worldRenderer;
	private boolean paused;

	@Override
	public void create () 
	{
		// Set Libgdx log level
		
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		// Load assets
		
		Assets.instance.init(new AssetManager());
		// Start game at menu screen
		setScreen(new MenuScreen(this));
	}
}
