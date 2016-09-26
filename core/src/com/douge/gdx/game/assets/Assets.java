package com.douge.gdx.game.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.douge.gdx.game.Constants;

public class Assets implements Disposable, AssetErrorListener 
{
	public static final String TAG = Assets.class.getName();
	public static final Assets instance = new Assets();
	private AssetManager assetManager;
	
	public AssetFonts fonts;
	public AssetAstronaut astronaut;
	public AssetEnv env;
	public AssetGoldCoin goldCoin;
	public AssetHeart heart;
	public AssetLevelDecoration levelDecoration;
	public AssetSurvivor survivor;
	public AssetTiles tiles;
	
	
	private Assets()
	{
		//prevent instantiation from other classes
	}

	public void init (AssetManager assetManager)
	{
		this.assetManager = assetManager;
		
		assetManager.setErrorListener(this);
		
		//load texture atlas
		assetManager.load(Constants.ASTRONAUT_ATLAS_PATH, TextureAtlas.class);
		assetManager.load(Constants.COIN_ATLAS_PATH, TextureAtlas.class);
		assetManager.load(Constants.ENVIRONMENT_ATLAS_PATH, TextureAtlas.class);
		assetManager.load(Constants.HEART_ATLAS_PATH, TextureAtlas.class);
		assetManager.load(Constants.SURVIVOR_ATLAS_PATH, TextureAtlas.class);
		assetManager.load(Constants.TILE_ATLAS_PATH, TextureAtlas.class);
		assetManager.load(Constants.CANYONBUNNY_PATH, TextureAtlas.class);
		//start loading assets in directory specified by atlas path
		assetManager.finishLoading();
		
		Gdx.app.debug(TAG, "# of assets loaded: " + assetManager.getAssetNames().size);
		
		for(String assetName: assetManager.getAssetNames())
		{
			Gdx.app.debug(TAG, assetName);
		}

		//create game resource assets
		TextureAtlas atlas = assetManager.get(Constants.ASTRONAUT_ATLAS_PATH);
		astronaut = new AssetAstronaut(atlas);
		
		if(Assets.instance.astronaut.astronaut == null)
		System.out.println("astro is null");	
		
		atlas = assetManager.get(Constants.COIN_ATLAS_PATH);
		goldCoin = new AssetGoldCoin(atlas);
		
		atlas = assetManager.get(Constants.HEART_ATLAS_PATH);
		heart = new AssetHeart(atlas);
		
		atlas = assetManager.get(Constants.ENVIRONMENT_ATLAS_PATH);
		env = new AssetEnv(atlas);
		
		atlas = assetManager.get((Constants.SURVIVOR_ATLAS_PATH));
		survivor = new AssetSurvivor(atlas);
		
		atlas = assetManager.get(Constants.TILE_ATLAS_PATH);
		tiles = new AssetTiles(atlas);
		
		atlas = assetManager.get(Constants.CANYONBUNNY_PATH);
		levelDecoration = new AssetLevelDecoration(atlas);
		
		fonts = new AssetFonts();
		
		//enable texture smoothing for all textures in the atlas
		for(Texture texture: atlas.getTextures())
		{
			texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
		
		if(Assets.instance.astronaut.astronaut == null)
		System.out.println("astro is null");
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