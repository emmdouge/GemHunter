package com.douge.gdx.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.MathUtils;
import com.douge.gdx.game.Constants;

public class GamePreferences 
{
	public static final String TAG = GamePreferences.class.getName();
			
	public static final GamePreferences instance = new GamePreferences();
			
	public boolean sound;
	public boolean music;
	public float volSound;
	public float volMusic;
	public int charSkin;
	public String name;
	public boolean showFpsCounter;
	private Preferences prefs;
			
	// singleton: prevent instantiation from other classes
	private GamePreferences () 
	{
		prefs = Gdx.app.getPreferences(Constants.PREFERENCES);
	}
		
	public void load () 
	{
		sound = prefs.getBoolean("sound", true);
		music = prefs.getBoolean("music", true);
		volSound = MathUtils.clamp(prefs.getFloat("volSound", 0.5f), 0.0f, 1.0f);
		volMusic = MathUtils.clamp(prefs.getFloat("volMusic", 0.5f), 0.0f, 1.0f);
		charSkin = MathUtils.clamp(prefs.getInteger("charSkin", 0), 0, 2);
		showFpsCounter = prefs.getBoolean("showFpsCounter", false);
		name = prefs.getString("name");
	}
	public void save () 
	{
		prefs.putBoolean("sound", sound);
		prefs.putBoolean("music", music);
		prefs.putFloat("volSound", volSound);
		prefs.putFloat("volMusic", volMusic);
		prefs.putInteger("charSkin", charSkin);
		prefs.putBoolean("showFpsCounter", showFpsCounter);
		prefs.putString("name", name);
		prefs.flush();
	}
}
