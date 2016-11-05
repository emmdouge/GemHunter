package com.douge.gdx.game.assets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class AssetUI 
{
	public final AtlasRegion leftEdgeOfBars;
	public final AtlasRegion block;
	public final AtlasRegion rightEnd;
	
//	public final AtlasRegion rightEdgesOfBars;
//	public final AtlasRegion missingLife;
//	public final AtlasRegion health;
	
	protected AssetUI(TextureAtlas atlas)
	{
		leftEdgeOfBars = atlas.findRegion("uiLeftbars");
		//leftEdgeOfBars.setRegion(atlas.findRegion("ui").getRegionX(), atlas.findRegion("ui").getRegionY(), 34, 65);
		
		block = atlas.findRegion("uiBlock");
		
		rightEnd = atlas.findRegion("uiRightEnd");
		
//		rightEdgesOfBars = atlas.findRegion("ui");
//		rightEdgesOfBars.setRegion(59f, 0, 44f, 60f);
//		
//		missingLife = atlas.findRegion("ui");
//		missingLife.setRegion(35f, 2f, 11f, 16f);
//		
//		health = atlas.findRegion("ui");
//		health.setRegion(115, 7, 11, 12);
	}
}
