package com.douge.gdx.game.objects.collectible;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.douge.gdx.game.assets.Assets;
import com.douge.gdx.game.effect.Effect;
import com.douge.gdx.game.objects.AbstractGameObject;

public class Collectible extends AbstractGameObject
{
	private Animation animation;
	
	public boolean collected;
	public Effect effect; 
	protected Color color;
	
	public Collectible(Animation animation, Effect effect) 
	{
		this.animation = animation;
		this.effect = effect;
		init();
	}
	private void init () 
	{
		dimension.set(0.5f, 0.5f);
		stateTime = 0f;
		origin.x = dimension.x/2f;
		origin.y = dimension.x/2f;
		// Set bounding box for collision detection
		bounds.set(0, 0, dimension.x, dimension.y);
		color = Color.WHITE;
		collected = false;
	}
	
	public void render (SpriteBatch batch) 
	{
		batch.setColor(Color.YELLOW);
		if (collected) 
		{
			return;
		}
		batch.setColor(color);
		
		if(animation != null)
		batch.draw(animation.getKeyFrame(stateTime).getTexture(), 
				position.x, position.y,
				origin.x, origin.y, 
				dimension.x, dimension.y, 
				scale.x, scale.y,
				rotation, 
				animation.getKeyFrame(stateTime).getRegionX(), animation.getKeyFrame(stateTime).getRegionY(),
				animation.getKeyFrame(stateTime).getRegionWidth(), animation.getKeyFrame(stateTime).getRegionHeight(),
				false, false);
	}
	
	public int getScore() 
	{
		return 250;
	}
}
