package com.douge.gdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.douge.gdx.game.assets.Assets;

public class GoldCoin extends AbstractGameObject
{
	private TextureRegion regGoldCoin;
	public boolean collected;
	private Animation spinning;
	private float stateTime;
	
	public GoldCoin () 
	{
		init();
	}
	
	private void init () 
	{
		dimension.set(0.5f, 0.5f);
		regGoldCoin = Assets.instance.goldCoin.goldCoin;
		spinning = Assets.instance.goldCoin.spinning;
		stateTime = 0f;
		// Set bounding box for collision detection
		bounds.set(0, 0, dimension.x, dimension.y);
		collected = false;
	}
	
	public void render (SpriteBatch batch) 
	{
		if (collected)
		{
			return;
		}
		
		//TextureRegion reg = regGoldCoin;
		stateTime += .05f;
		Gdx.app.log("", "" + stateTime);
		TextureRegion reg = spinning.getKeyFrame(stateTime);
		batch.draw(reg.getTexture(), 
				position.x, position.y,
				origin.x, origin.y, 
				dimension.x, dimension.y, 
				scale.x, scale.y,
				rotation, 
				reg.getRegionX(), reg.getRegionY(),
				reg.getRegionWidth(), reg.getRegionHeight(), 
				false, false);
	}
	
	public int getScore() 
	{
		return 100;
	}
}
