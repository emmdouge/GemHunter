package com.douge.gdx.game.assets;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class AssetGem 
{
	public final AtlasRegion jumpGem;
	public final AtlasRegion heartGem;
	
	public AssetGem(TextureAtlas atlas) 
	{
		jumpGem = atlas.findRegion("gem84");
		heartGem = atlas.findRegion("gem145");
	}

}
