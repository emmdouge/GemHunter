package com.douge.gdx.game.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.douge.gdx.game.VIEW_DIRECTION;
import com.douge.gdx.game.assets.Assets;

public class Fireball extends AbstractGameObject
{
	public Animation currentAnimation;
	public VIEW_DIRECTION viewDirection;
	public boolean hitEnemy = false;
	
	public Fireball(VIEW_DIRECTION viewDirection)
	{
		super();
		this.viewDirection = viewDirection;
		currentVelocity.x = 6f*VIEW_DIRECTION.getInt(viewDirection);
		maxVelocity.x = 6f;
		currentAnimation = Assets.instance.fireball.movingAnimation;
	}
	
	
	
	@Override
	public void render(SpriteBatch batch) 
	{
		TextureRegion reg = currentAnimation.getKeyFrame(stateTime);
		batch.setColor(Color.WHITE);
		batch.draw(reg.getTexture(), 
				position.x, position.y,
				origin.x, origin.y, 
				dimension.x, dimension.y, 
				scale.x, scale.y,
				rotation, 
				reg.getRegionX(), reg.getRegionY(),
				reg.getRegionWidth(), reg.getRegionHeight(), 
				viewDirection == VIEW_DIRECTION.LEFT, false);
	}

	public void hit()
	{
		currentAnimation = Assets.instance.fireball.hitAnimation;
	}
}
