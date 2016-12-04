package com.douge.gdx.game.assets;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;

public class AssetGem 
{
	public final Animation jumpGem;
	public final Animation heartGem;
	public final Animation magicGem;
	public final Animation levelGem;
	public AssetGem(TextureAtlas atlas) 
	{
		Array<AtlasRegion> regions = new Array<AtlasRegion>();
		regions.add(atlas.findRegion("gem3"));
		jumpGem = new Animation(1/10f, regions, Animation.PlayMode.LOOP);
		
		regions = new Array<AtlasRegion>();
		regions.add(atlas.findRegion("gem4"));
		heartGem = new Animation(1/10f, regions, Animation.PlayMode.LOOP);
		
		regions = new Array<AtlasRegion>();
		regions.add(atlas.findRegion("gem2"));
		magicGem = new Animation(1/10f, regions, Animation.PlayMode.LOOP);
		
		regions = new Array<AtlasRegion>();
		for(int i = 0; i <= 2; i++)
		{
			regions.add(atlas.findRegion("wizard"+i));
		}
		levelGem = new Animation(1/10f, regions, Animation.PlayMode.LOOP);
	}

}
