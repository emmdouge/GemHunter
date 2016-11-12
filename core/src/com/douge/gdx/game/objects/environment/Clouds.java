package com.douge.gdx.game.objects.environment;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.douge.gdx.game.Constants;
import com.douge.gdx.game.assets.Assets;
import com.douge.gdx.game.objects.AbstractGameObject;

public class Clouds extends AbstractGameObject
{
	private class Cloud extends AbstractGameObject 
	{
		private TextureRegion regCloud;
		
		public Cloud () 
		{
			
		}
		public void setRegion (TextureRegion region) 
		{
			regCloud = region;
		}
		@Override
		public void render (SpriteBatch batch) 
		{
			TextureRegion reg = regCloud;
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
	}
	
	private Array<Cloud> clouds;
	
	private float levelPixelWidth;
	
	private Array<TextureRegion> regClouds;
	
	public Clouds (float length) 
	{
		this.levelPixelWidth = length;
		init();
	}
	
	private void init () 
	{
		dimension.set(3.0f, 1.5f);
		
		regClouds = new Array<TextureRegion>();
		regClouds.add(Assets.instance.env.clouds);
		
		int	distanceBetweenEachCloud = 5;
		int numClouds = (int)(levelPixelWidth / distanceBetweenEachCloud);
		clouds = new Array<Cloud>(numClouds);
		for (int i = 0; i < numClouds; i++) 
		{
			Cloud cloud = spawnCloud();
			cloud.position.x = i * distanceBetweenEachCloud;
			clouds.add(cloud);
			cloud.currentVelocity.x = 2;
			cloud.maxVelocity = new Vector2(3, 0);
		}
	}
	
	public void update(float deltaTime)
	{
		for(int i = 0; i < clouds.size; i++)
		{
			if(clouds.get(i).position.x > 128 + clouds.get(i).dimension.x)
			{
				clouds.get(i).position.x = 0 - clouds.get(i).dimension.x;
			}
			clouds.get(i).update(deltaTime);
		}
	}
	private Cloud spawnCloud () 
	{
		Cloud cloud = new Cloud();
		cloud.dimension.set(dimension);
		
		// select random cloud image
		cloud.setRegion(regClouds.random());
		
		// position
		float yPos = 1.75f; // base position
		yPos += MathUtils.random(0.0f, 16.0f); 
		
		// random additional position
		cloud.position.y = yPos;
		
		return cloud;
	}
	
	@Override
	public void render (SpriteBatch batch) 
	{
		batch.setColor(Color.WHITE);
		for (Cloud cloud : clouds)
		{
			cloud.render(batch);
		}
	}
}
