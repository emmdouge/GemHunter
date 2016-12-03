package com.douge.gdx.game.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;

public class AssetMusic 
{
	public final Music song01;
	public final Music throwingSound;
	public final Music evilMonster;
	public AssetMusic (AssetManager assetManager) 
	{
		song01 = assetManager.get("../core/assets/music/latenights.mp3", Music.class);
		throwingSound = assetManager.get("../core/assets/music/throwingSound.mp3", Music.class);
		evilMonster = assetManager.get("../core/assets/music/evilmonster.mp3", Music.class);
	}
}
