package com.douge.gdx.game.assets;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;

public class AssetGoldCoin {
	public final AtlasRegion goldCoin;
	public final Animation spinning;
	
	protected AssetGoldCoin(TextureAtlas atlas)
	{
		goldCoin = atlas.findRegion("goldCoin5");
		Array<AtlasRegion> regions = new Array<AtlasRegion>();
		regions.add(atlas.findRegion("goldCoin1"));
		regions.add(atlas.findRegion("goldCoin2"));
		regions.add(atlas.findRegion("goldCoin3"));
		regions.add(atlas.findRegion("goldCoin5"));
		
		spinning = new Animation(1/15f, regions, Animation.PlayMode.LOOP);
	}
}
