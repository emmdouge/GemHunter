package com.douge.gdx.game.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.douge.gdx.game.Constants;
import com.douge.gdx.game.assets.enemy.AssetBat;
import com.douge.gdx.game.assets.enemy.AssetGoblin;
import com.douge.gdx.game.assets.enemy.AssetRanger;
import com.douge.gdx.game.assets.enemy.AssetSkeleton;
import com.douge.gdx.game.assets.enemy.AssetSlime;

public class Assets implements Disposable, AssetErrorListener 
{
	public static final String TAG = Assets.class.getName();
	public static final Assets instance = new Assets();
	private AssetManager assetManager;
	
	public AssetFonts fonts;
	public AssetEnvironment env;
	public AssetGoldCoin goldCoin;
	public AssetSurvivor survivor;
	public AssetTiles tiles;
	public AssetGem gems;
	public AssetBat bat;
	public AssetSkeleton skeleton;
	public AssetRanger ranger;
	public AssetSlime slime;
	public AssetFireball fireball;
	public AssetGoblin goblin;
	public AssetSounds sounds; 
	public AssetMusic music; 
	public AssetUI ui;
	public AssetCrow crow;
	public ParticleEffect snow;
	
	private Assets()
	{
		//prevent instantiation from other classes
	}

	public void init (AssetManager assetManager)
	{
		this.assetManager = assetManager;
		
		assetManager.setErrorListener(this);
		
		//load texture atlases
		assetManager.load(Constants.GEM_HUNTER_ATLAS_PATH, TextureAtlas.class);
	     
	    // load sounds 
	    assetManager.load("../core/assets/sounds/jump.wav", Sound.class); 
	    assetManager.load("../core/assets/sounds/jump_with_potion.wav", Sound.class); 
	    assetManager.load("../core/assets/sounds/pickup_coin.wav", Sound.class); 
	    assetManager.load("../core/assets/sounds/pickup_potion.wav", Sound.class); 
	    assetManager.load("../core/assets/sounds/live_lost.wav", Sound.class); 
	    assetManager.load("../core/assets/sounds/dash.wav", Sound.class);
	    
	    // load music 
	    assetManager.load("../core/assets/music/latenights.mp3", Music.class); 
	    assetManager.load("../core/assets/music/throwingSound.mp3", Music.class);
	    
		assetManager.finishLoading();
		
		Gdx.app.debug(TAG, "# of assets loaded: " + assetManager.getAssetNames().size);
		
		for(String assetName: assetManager.getAssetNames())
		{
			Gdx.app.debug(TAG, assetName);
		}

		//create game resource assets
		TextureAtlas atlas = assetManager.get(Constants.GEM_HUNTER_ATLAS_PATH);
		survivor = new AssetSurvivor(atlas);
		gems = new AssetGem(atlas);
		slime = new AssetSlime(atlas);
		skeleton = new AssetSkeleton(atlas);
		ranger = new AssetRanger(atlas);
		bat = new AssetBat(atlas);
		fireball = new AssetFireball(atlas);
		goldCoin = new AssetGoldCoin(atlas);
		goblin = new AssetGoblin(atlas);
		env = new AssetEnvironment(atlas);
		tiles = new AssetTiles(atlas);
		crow = new AssetCrow(atlas);
		ui = new AssetUI(atlas);
		fonts = new AssetFonts();
	    music = new AssetMusic(assetManager); 
	    sounds = new AssetSounds(assetManager); 
		snow = new ParticleEffect();
		snow.load(Gdx.files.internal("../core/assets/particles/snow.pfx"), Gdx.files.internal("../core/assets/particles"));
		
		//enable texture smoothing for all textures in the atlas
		for(Texture texture: atlas.getTextures())
		{
			texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		}
	}
	
	@Override
	public void dispose() 
	{
		assetManager.dispose();
	}

	@Override
	public void error(AssetDescriptor asset, Throwable throwable) {
		Gdx.app.error(TAG, "Couldn't load: " + asset.fileName, (Exception)throwable); 
	}
}