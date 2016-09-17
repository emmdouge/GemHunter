package com.douge.gdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable, AssetErrorListener 
{
	public static final Assets instance = new Assets();
	private AssetManager assetManager;
	
	public static final String TAG = Assets.class.getName();
	public static final String ATLAS_PATH = "../core/assets/images/canyonbunny.pack.atlas";
	public static final String LEVEL_01A_PATH = "../core/assets/levels/level_01a.png";
	public static final String LEVEL_01B_PATH = "../core/assets/levels/level_01b.png";
	
	public AssetBunny bunny;
	public AssetRock rock;
	public AssetGoldCoin goldCoin;
	public AssetFeather feather;
	public AssetLevelDecoration levelDecoration;
	
	
	private Assets()
	{
		//prevent instantiation from other classes
	}

	public void init (AssetManager assetManager)
	{
		this.assetManager = assetManager;
		
		assetManager.setErrorListener(this);
		
		assetManager.load(ATLAS_PATH, TextureAtlas.class);
		assetManager.finishLoading();
		
		Gdx.app.debug(TAG, "# of assets loaded: " + assetManager.getAssetNames().size);
		
		for(String assetName: assetManager.getAssetNames())
		{
			Gdx.app.debug(TAG, assetName);
		}
		
		TextureAtlas atlas = assetManager.get(ATLAS_PATH);
		
		//enable texture smoothing for all textures in the atlas
		for(Texture texture: atlas.getTextures())
		{
			texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
		
		//create game resource assets
		bunny = new AssetBunny(atlas);
		goldCoin = new AssetGoldCoin(atlas);
		feather = new AssetFeather(atlas);
		rock = new AssetRock(atlas);
		levelDecoration = new AssetLevelDecoration(atlas);
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