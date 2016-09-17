package com.douge.gdx.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class AssetFeather {
	public final AtlasRegion region;
	
	public AssetFeather(TextureAtlas atlas)
	{
		region = atlas.findRegion("item_feather");
	}
}
