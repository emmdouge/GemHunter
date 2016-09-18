package com.douge.gdx.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class AssetSurvivor
{

	public final AtlasRegion survivor;
	
	public AssetSurvivor(TextureAtlas atlas) 
	{
		survivor = atlas.findRegion("0");
	}

}
