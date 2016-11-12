package com.douge.gdx.game.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.douge.gdx.game.assets.Assets;

public class Crow extends AbstractGameObject
{
	private Animation flying;
	private ParticleEffect featherEffect;
	private Player player;
	
	public Crow(Player player)
	{
		this.player = player;
		init();
	}
	
	private void init()
	{
		dimension.set(1f, 1f);
		flying = Assets.instance.crow.flying;
		stateTime = 0f;
		origin.x = dimension.x/2;
		// Set bounding box for collision detection
		bounds.set(0, 0, dimension.x, dimension.y);
		featherEffect = Assets.instance.crow.featherParticles;
	}
	
	@Override
	public void update(float deltaTime)
	{
		stateTime += deltaTime;
		float xOffset = player.viewDirection == VIEW_DIRECTION.RIGHT ? -dimension.x : player.dimension.x;
		Vector2 newPos = new Vector2(player.position.x + xOffset, player.position.y);
		position.lerp(newPos, 1.25f * deltaTime);
		featherEffect.setPosition(position.x+origin.x, position.y+1f);
		featherEffect.update(deltaTime);
		featherEffect.start();
	}
	
	@Override
	public void render(SpriteBatch batch) 
	{
		TextureRegion reg = flying.getKeyFrame(stateTime);
		batch.setColor(Color.WHITE);
		batch.draw(reg.getTexture(), 
				position.x, position.y+.5f,
				origin.x, origin.y, 
				dimension.x, dimension.y, 
				scale.x, scale.y,
				rotation, 
				reg.getRegionX(), reg.getRegionY(),
				reg.getRegionWidth(), reg.getRegionHeight(), 
				position.x + origin.x > player.position.x + player.origin.x, false);
		
		//Color color = Color.RED;
		//featherEffect.getEmitters().first().getTint().setColors(new float[]{color.r, color.g, color.b});
		featherEffect.draw(batch);
	}

}
