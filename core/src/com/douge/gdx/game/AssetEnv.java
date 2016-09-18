package com.douge.gdx.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class AssetEnv {
	public final AtlasRegion trees;
	public final AtlasRegion fog;
	public final AtlasRegion clouds;
	
	public AssetEnv(TextureAtlas atlas)
	{
		trees = atlas.findRegion("black_trees");
		fog = atlas.findRegion("fog");
		clouds = atlas.findRegion("whiteclouds");
	}
}
