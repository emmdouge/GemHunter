package com.douge.gdx.game.assets;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class AssetGoldCoin {
	public final AtlasRegion goldCoin;
	
	protected AssetGoldCoin(TextureAtlas atlas)
	{
		goldCoin = atlas.findRegion("goldCoin5");
	}
}
