package com.douge.gdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.douge.gdx.game.assets.Assets;
import com.douge.gdx.game.screen.GameScreen;
import com.douge.gdx.game.screen.MenuScreen;
import com.douge.gdx.game.screen.transition.DirectedGame;
import com.douge.gdx.game.screen.transition.Fade;
import com.douge.gdx.game.screen.transition.ScreenTransition;
import com.douge.gdx.game.utils.AudioManager;
import com.douge.gdx.game.utils.GamePreferences;

public class DougeGdxGame extends DirectedGame
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
		
	    // Load preferences for audio settings and start playing music 
	    GamePreferences.instance.load(); 
	    AudioManager.instance.play(Assets.instance.music.song01); 
	    
		// Start game at menu screen
		ScreenTransition fade = Fade.init(0.75f);
		setScreen(new MenuScreen(this), fade);
	}
}
