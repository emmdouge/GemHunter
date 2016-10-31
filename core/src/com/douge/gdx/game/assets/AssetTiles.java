package com.douge.gdx.game.assets;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetTiles {

	public final AtlasRegion chest;
	//public final AtlasRegion spikes;
	public final AtlasRegion tileRockTop;
	public final AtlasRegion tileRockBottom;
	public final AtlasRegion tileStar1;
	public final AtlasRegion tileStar2;
	public final AtlasRegion tileStar3;
	
	protected AssetTiles(TextureAtlas atlas) {
		chest = atlas.findRegion("Pixel Treasure Chest Closed");
		tileRockTop = atlas.findRegion("3");
		tileRockBottom = atlas.findRegion("14");
		//you can use set region clip texture if it is a sprite sheet
		//coordinates can be found within the atlas file
		//tile.setRegion(2, 72, 270, 258);
		tileStar1 = atlas.findRegion("13");
		tileStar2 = atlas.findRegion("15");
		tileStar3 = atlas.findRegion("16");
	}

}
