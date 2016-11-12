package com.douge.gdx.game.objects.environment;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.douge.gdx.game.assets.Assets;
import com.douge.gdx.game.objects.AbstractGameObject;

public class BlackOverlay extends AbstractGameObject
{
	private TextureRegion regBlackOverlay;
	
	private float length;
	
	public BlackOverlay (float length) 
	{
		this.length = length;
		init();
	}
	
	private void init () 
	{
		dimension.set(length * 10, 3);
		regBlackOverlay = Assets.instance.env.blackOverlay;
		origin.x = -dimension.x / 2;
	}
	
	@Override
	public void render (SpriteBatch batch) 
	{
		batch.setColor(Color.BLACK);
		
		TextureRegion reg = null;
		reg = regBlackOverlay;
		batch.draw(reg.getTexture(), 
				position.x + origin.x, position.y + origin.y, 
				origin.x, origin.y, 
				dimension.x, dimension.y, 
				scale.x, scale.y, 
				rotation, 
				reg.getRegionX(), reg.getRegionY(),
				reg.getRegionWidth(), reg.getRegionHeight(),
				false, true);
	}
}
