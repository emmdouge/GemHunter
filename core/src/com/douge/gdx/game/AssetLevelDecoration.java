package com.douge.gdx.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class AssetLevelDecoration {
	public final AtlasRegion cloud01;
	public final AtlasRegion cloud02;
	public final AtlasRegion cloud03;
	
	public final AtlasRegion mountainLeft;
	public final AtlasRegion mountainRight;
	
	public final AtlasRegion waterOverlay;
	
	public AssetLevelDecoration(TextureAtlas atlas)
	{
		cloud01 = atlas.findRegion("cloud01");
		cloud02 = atlas.findRegion("cloud02");
		cloud03 = atlas.findRegion("cloud03");
		
		mountainLeft = atlas.findRegion("mountain_left");
		mountainRight = atlas.findRegion("mountain_right");
		
		waterOverlay = atlas.findRegion("water_overlay");
	}
}
