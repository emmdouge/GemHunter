package com.douge.gdx.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class AssetRock {
	public final AtlasRegion edge;
	public final AtlasRegion middle;
	
	public AssetRock(TextureAtlas atlas)
	{
		edge = atlas.findRegion("rock_edge");
		middle = atlas.findRegion("rock_middle");
	}
}
