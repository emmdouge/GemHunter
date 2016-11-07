package com.douge.gdx.game.assets;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;

public class AssetCrow 
{
	public final Animation flying;
	
	protected AssetCrow(TextureAtlas atlas)
	{
		Array<AtlasRegion> regions = new Array<AtlasRegion>();
		for(int i = 0; i <= 4; i++)
		{
			regions.add(atlas.findRegion("crow"+i));
		}
		flying = new Animation(1/15f, regions, Animation.PlayMode.LOOP);
	}
}
