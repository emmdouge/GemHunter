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

	public final TextureRegion level5Ground1;
	public final TextureRegion level5Ground2;
	public final TextureRegion level5Sky;

	public final TextureRegion level6Ground;
	public final TextureRegion level6Sky;

	public final TextureRegion level7Ground;
	public final TextureRegion level7Sky;

	public final Animation goal1;
	public final Animation goal2;
	public final Animation goal3;
	public final Animation goal4;
	public final Animation goal5;
	public final Animation goal6;
	public final Animation goal7;
	
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
		
		level5Ground1 = atlas.findRegion("level5Ground1");
		level5Ground2 = atlas.findRegion("level5Ground2");
		level5Sky = atlas.findRegion("level5Sky");
		
		level6Ground = atlas.findRegion("level6Ground");
		level6Sky = atlas.findRegion("level6Sky");
		
		level7Ground = atlas.findRegion("level7Ground");
		level7Sky = atlas.findRegion("level7Sky");
		
		Array<AtlasRegion> regions = new Array<AtlasRegion>();
		regions.add(atlas.findRegion("goal1"));
		goal1 = new Animation(1.0f/10.0f, regions, Animation.PlayMode.LOOP);
		goal2 = null;
		regions = new Array<AtlasRegion>();
		regions.add(atlas.findRegion("goal3"));
		goal3 = new Animation(1.0f/10.0f, regions, Animation.PlayMode.LOOP);
		goal4 = null;
		regions = new Array<AtlasRegion>();
		regions.add(atlas.findRegion("goal5"));
		goal5 = new Animation(1.0f/10.0f, regions, Animation.PlayMode.LOOP);
		regions = new Array<AtlasRegion>();
		regions.add(atlas.findRegion("goal6"));
		goal6 = new Animation(1.0f/10.0f, regions, Animation.PlayMode.LOOP);
		goal7 = null;
		
		regions = new Array<AtlasRegion>();
		for(int i = 0; i <= 2; i++)
		{
			regions.add(atlas.findRegion("press"+i));
		}
		buttonPress = new Animation(1.0f / 10.0f, regions, Animation.PlayMode.LOOP);
	}

}
