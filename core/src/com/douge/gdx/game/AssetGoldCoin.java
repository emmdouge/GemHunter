package com.douge.gdx.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class AssetGoldCoin {
	public final AtlasRegion goldCoin;
	
	public AssetGoldCoin(TextureAtlas atlas)
	{
		goldCoin = atlas.findRegion("goldCoin5");
	}
}
