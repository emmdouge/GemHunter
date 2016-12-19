package com.douge.gdx.game.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;

public class AssetMusic 
{
	public final Music song01;
	public final Music throwingSound;
	
	public AssetMusic (AssetManager assetManager) 
	{
		song01 = assetManager.get("music/latenights.mp3", Music.class);
		throwingSound = assetManager.get("music/throwingSound.mp3", Music.class);
	}
}
