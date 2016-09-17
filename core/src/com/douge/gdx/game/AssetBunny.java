package com.douge.gdx.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class AssetBunny 
{
	public final AtlasRegion region;
	
	public AssetBunny(TextureAtlas atlas)
	{
		region = atlas.findRegion("bunny_head");
	}
}
