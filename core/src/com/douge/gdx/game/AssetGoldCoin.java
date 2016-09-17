package com.douge.gdx.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class AssetGoldCoin {
	public final AtlasRegion region;
	
	public AssetGoldCoin(TextureAtlas atlas)
	{
		region = atlas.findRegion("item_gold_coin");
	}
}
