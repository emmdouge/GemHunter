package com.douge.gdx.game.assets;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetTiles {

	public final AtlasRegion chest;
	//public final AtlasRegion spikes;
	public final AtlasRegion tileTop;
	public final AtlasRegion tileBot;
	
	protected AssetTiles(TextureAtlas atlas) {
		chest = atlas.findRegion("Pixel Treasure Chest Closed");
		tileTop = atlas.findRegion("3");
		tileBot = atlas.findRegion("14");
		//you can use set region clip texture if it is a sprite sheet
		//coordinates can be found within the atlas file
		//tile.setRegion(2, 72, 270, 258);
	}

}
