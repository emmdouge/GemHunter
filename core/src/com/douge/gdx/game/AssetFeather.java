package com.douge.gdx.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class AssetFeather {
	public final AtlasRegion feather;
	
	public AssetFeather(TextureAtlas atlas)
	{
		feather = atlas.findRegion("item_feather");
	}
}
