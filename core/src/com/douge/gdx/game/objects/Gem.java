package com.douge.gdx.game.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.douge.gdx.game.assets.Assets;
import com.douge.gdx.game.effect.Effect;

public class Gem extends AbstractGameObject
{
	private TextureRegion regGem;
	
	public boolean collected;
	public Effect effect; 
	
	public Gem(TextureRegion gem, Effect effect) 
	{
		regGem = gem;
		this.effect = effect;
		init();
	}
	private void init () 
	{
		dimension.set(0.5f, 0.5f);
	
		// Set bounding box for collision detection
		bounds.set(0, 0, dimension.x, dimension.y);
		collected = false;
	}
	
	public void render (SpriteBatch batch) 
	{
		batch.setColor(Color.YELLOW);
		if (collected) 
		{
			return;
		}
		
		batch.draw(regGem.getTexture(), 
				position.x, position.y,
				origin.x, origin.y, 
				dimension.x, dimension.y, 
				scale.x, scale.y,
				rotation, 
				regGem.getRegionX(), regGem.getRegionY(),
				regGem.getRegionWidth(), regGem.getRegionHeight(),
				false, false);
	}
	
	public int getScore() 
	{
		return 250;
	}
}
