package com.douge.gdx.game.assets;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class AssetTiles 
{
	public final Animation buttonPress;
	
	//public final AtlasRegion spikes;
	public final AtlasRegion tileRockTop;
	public final AtlasRegion tileRockBottom;
	public final AtlasRegion tileStar1;
	public final AtlasRegion tileStar2;
	public final AtlasRegion tileStar3;
	public final TextureRegion snow1;
	public final TextureRegion snow2;
	public final TextureRegion level1Ground;
	public final TextureRegion level1Sky;
	public final TextureRegion level2Ground;

	public final TextureRegion level3Ground1;
	public final TextureRegion level3Ground2;
	public final TextureRegion level3Sky;

	public final TextureRegion level4;
	
	protected AssetTiles(TextureAtlas atlas) 
	{
		tileRockTop = atlas.findRegion("rockPlat");
		tileRockBottom = atlas.findRegion("rockBack");
		//you can use set region clip texture if it is a sprite sheet
		//coordinates can be found within the atlas file
		//tile.setRegion(2, 72, 270, 258);
		tileStar1 = atlas.findRegion("star0");
		tileStar2 = atlas.findRegion("star1");
		tileStar3 = atlas.findRegion("star2");
		
		snow1 = atlas.findRegion("snow1");
		snow2 = atlas.findRegion("snow2");
		
		level1Ground = atlas.findRegion("level1Ground");
		level1Sky = atlas.findRegion("level1Sky");
		
		level2Ground = atlas.findRegion("level2Ground");
		
		level3Ground1 = atlas.findRegion("level3Ground1");
		level3Ground2 = atlas.findRegion("level3Ground2");
		level3Sky = atlas.findRegion("level3Sky");
		
		level4 = atlas.findRegion("level4");
		
		Array<AtlasRegion> regions = new Array<AtlasRegion>();
		for(int i = 0; i <= 2; i++)
		{
			regions.add(atlas.findRegion("press"+i));
		}
		buttonPress = new Animation(1.0f / 10.0f, regions, Animation.PlayMode.LOOP);
	}

}
