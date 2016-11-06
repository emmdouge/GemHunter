package com.douge.gdx.game.assets;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class AssetEnvironment {
	public final AtlasRegion trees;
	public final AtlasRegion fog;
	public final AtlasRegion clouds;
	public final AtlasRegion blackOverlay;
	protected AssetEnvironment(TextureAtlas atlas)
	{
		trees = atlas.findRegion("blacktrees");
		fog = atlas.findRegion("fog");
		clouds = atlas.findRegion("whiteclouds");
		blackOverlay = atlas.findRegion("blackOverlay");
	}
}
