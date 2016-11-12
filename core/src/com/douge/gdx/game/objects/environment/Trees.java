package com.douge.gdx.game.objects.environment;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.douge.gdx.game.assets.*;
import com.douge.gdx.game.objects.AbstractGameObject;

public class Trees extends AbstractGameObject
{
	private TextureRegion regTreeLeft;
	private TextureRegion regTreeRight;
	
	private int length;
	
	public Trees (int length) 
	{
		this.length = length;
		init();
	}
	private void init () 
	{
		dimension.set(10, 3);
		regTreeLeft = Assets.instance.env.trees;
		regTreeRight = Assets.instance.env.trees;
		
		// shift mountain and extend length
		origin.x = -dimension.x * 2;
		length += dimension.x * 2;
	}
	
	public void updateScrollPosition (Vector2 camPosition) 
	{
		position.set(camPosition.x, position.y);
	}
	
	private void drawTrees(SpriteBatch batch, float offsetX, float offsetY, float tintColor, float parallaxSpeedX) 
	{
			TextureRegion reg = null;
			
			batch.setColor(tintColor, tintColor, tintColor, 1);
			
			float xRel = dimension.x * offsetX;
			float yRel = dimension.y * offsetY;
			
			// mountains span the whole level
			int mountainLength = 0;
			mountainLength += MathUtils.ceil(length / (2 * dimension.x) * (1 - parallaxSpeedX));
			mountainLength += MathUtils.ceil(length / (2 * dimension.x));
			mountainLength += MathUtils.ceil(0.5f + offsetX);
			
			for (int i = 0; i < mountainLength; i++) 
			{
				// mountain left
				reg = regTreeLeft;
				batch.draw(reg.getTexture(),
						origin.x + xRel + position.x * parallaxSpeedX,
						origin.y + yRel + position.y,
						origin.x, origin.y,
						dimension.x, dimension.y,
						scale.x, scale.y,
						rotation,
						reg.getRegionX(), reg.getRegionY(),
						reg.getRegionWidth(), reg.getRegionHeight(),
						false, false);
				xRel += dimension.x;
				
				// mountain right
				reg = regTreeRight;
				batch.draw(reg.getTexture(),
						origin.x + xRel + position.x * parallaxSpeedX,
						origin.y + yRel + position.y,
						origin.x, origin.y,
						dimension.x, dimension.y,
						scale.x, scale.y,
						rotation,
						reg.getRegionX(), reg.getRegionY(),
						reg.getRegionWidth(), reg.getRegionHeight(),
						false, false);
				xRel += dimension.x;
			}
			
			// reset color to white
			batch.setColor(1, 1, 1, 1);
	}
	
	@Override
	public void render(SpriteBatch batch) 
	{
		// 80% distant mountains (dark gray)
		drawTrees(batch, 0.5f, 0.05f, 0.5f, 0.8f);
		// 50% distant mountains (gray)
		drawTrees(batch, 0.25f, 0.025f, 0.7f, 0.5f);
		// 30% distant mountains (light gray)
		drawTrees(batch, 0.0f, 0.0f, 0.9f, 0.3f);
	}

}
