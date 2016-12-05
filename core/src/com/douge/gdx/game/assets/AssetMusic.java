package com.douge.gdx.game.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;

public class AssetMusic 
{
	public final Music song01;
	public final Music throwingSound;
	public final Music evilMonster;
	public final Music lvl1;
	public final Music lvl2;
	public final Music lvl3;
	public final Music lvl4;
	public final Music lvl5;
	public final Music lvl6;
	public final Music lvl7;
	public final Music intro;
	public final Music outro;
	
	public final Music lvl1music;
	public final Music lvl2music;
	public final Music lvl3music;
	public final Music lvl4music;
	public final Music lvl5music;
	public final Music lvl6music;
	public final Music lvl7music;
	
	public AssetMusic (AssetManager assetManager) 
	{
		song01 = assetManager.get("../core/assets/music/latenights.mp3", Music.class);
		throwingSound = assetManager.get("../core/assets/music/throwingSound.mp3", Music.class);
		evilMonster = assetManager.get("../core/assets/music/evilmonster.mp3", Music.class);
		
		lvl1 = assetManager.get("../core/assets/music/lvl1.mp3", Music.class);
		lvl2 = assetManager.get("../core/assets/music/lvl2.mp3", Music.class);
		lvl3 = assetManager.get("../core/assets/music/lvl3.mp3", Music.class);
		lvl4 = assetManager.get("../core/assets/music/lvl4.mp3", Music.class);
		lvl5 = assetManager.get("../core/assets/music/lvl5.mp3", Music.class);
		lvl6 = assetManager.get("../core/assets/music/lvl6.mp3", Music.class);
		lvl7 = assetManager.get("../core/assets/music/lvl7.mp3", Music.class);
		
		lvl1music = assetManager.get("../core/assets/music/lvl1music.mp3", Music.class);
		lvl2music = assetManager.get("../core/assets/music/lvl2music.mp3", Music.class);
		lvl3music = assetManager.get("../core/assets/music/lvl3music.mp3", Music.class);
		lvl4music = assetManager.get("../core/assets/music/lvl4music.mp3", Music.class);
		lvl5music = assetManager.get("../core/assets/music/lvl5music.mp3", Music.class);
		lvl6music = assetManager.get("../core/assets/music/lvl6music.mp3", Music.class);
		lvl7music = assetManager.get("../core/assets/music/lvl7music.mp3", Music.class);
		
		intro = assetManager.get("../core/assets/music/intro.mp3", Music.class);
		outro = assetManager.get("../core/assets/music/outro.mp3", Music.class);
	}
}
