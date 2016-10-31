package com.douge.gdx.game.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;

public class AssetMusic 
{
	public final Music song01;
	
	public AssetMusic (AssetManager assetManager) 
	{
		song01 = assetManager.get("../core/assets/music/latenights.mp3", Music.class);
	}
}
