package com.douge.gdx.game.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.douge.gdx.game.assets.Assets;

public class Wizard extends AbstractGameObject
{
	public Wizard()
	{
		dimension.set(1f, 1f);
		stateTime = 0f;
		origin.x = dimension.x/2;
		// Set bounding box for collision detection
		bounds.set(0, 0, dimension.x, dimension.y);
	}
	
	@Override
	public void update(float deltaTime)
	{
		stateTime += deltaTime;
	}
	
	@Override
	public void render(SpriteBatch batch) 
	{
		TextureRegion reg = Assets.instance.survivor.wizard.getKeyFrame(stateTime);
		batch.setColor(Color.WHITE);
		batch.draw(reg.getTexture(), 
				position.x, position.y+.5f,
				origin.x, origin.y, 
				dimension.x, dimension.y, 
				scale.x, scale.y,
				rotation, 
				reg.getRegionX(), reg.getRegionY(),
				reg.getRegionWidth(), reg.getRegionHeight(), 
				false, false);
	}

}
