package com.douge.gdx.game.assets;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class AssetEnv {
	public final AtlasRegion trees;
	public final AtlasRegion fog;
	public final AtlasRegion clouds;
	
	protected AssetEnv(TextureAtlas atlas)
	{
		trees = atlas.findRegion("blacktrees");
		fog = atlas.findRegion("fog");
		clouds = atlas.findRegion("whiteclouds");
	}
}
