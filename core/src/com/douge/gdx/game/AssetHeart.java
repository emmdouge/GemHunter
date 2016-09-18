package com.douge.gdx.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class AssetHeart {
	public final AtlasRegion heart;
	
	public AssetHeart(TextureAtlas atlas)
	{
		heart = atlas.findRegion("h1");
	}
}
