package com.douge.gdx.game.enemy;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.douge.gdx.game.Constants;
import com.douge.gdx.game.VIEW_DIRECTION;
import com.douge.gdx.game.assets.Assets;
import com.douge.gdx.game.assets.enemy.AssetEnemy;
import com.douge.gdx.game.enemystate.EnemyStateContext;
import com.douge.gdx.game.objects.AbstractGameObject;
import com.douge.gdx.game.screens.CharacterSkin;
import com.douge.gdx.game.utils.GamePreferences;

public abstract class Enemy extends AbstractGameObject
{
	public static final String TAG = Enemy.class.getName();
	public EnemyStateContext context;
	public AssetEnemy assets;
	public Animation currentAnimation;
	public TextureRegion reg;
	public float stateTime;
	public float moveSpeed;
	
	public VIEW_DIRECTION viewDirection;
	
	//flag for level to clean up dead enemies
	public boolean isDead = false;
	
	protected VIEW_DIRECTION flip;
	
	//disables player ability to kill enemy
	public boolean hasBeenKilled = false;
	
	public Enemy(AssetEnemy assetEnemy, float moveSpeed, VIEW_DIRECTION direction) 
	{
		this.assets = assetEnemy;
		this.moveSpeed = moveSpeed;
		
		// View direction
		flip = direction;
		init();
	}
	public void init() 
	{
		dimension.set(1, 1);
		reg = assets.standingAnimation.getKeyFrame(0);
		
		viewDirection = VIEW_DIRECTION.LEFT;
		
		// Center image on game object
		origin.set(dimension.x / 2, dimension.y / 2);
		
		// Bounding box for collision detection
		bounds.set(0, 0, dimension.x, dimension.y);
		
		// Set physics values
		maxVelocity.set(3.0f, 4.0f);
		friction = 12.0f;
		gravity = -25.0f;
		currentGravity = gravity;
		currentFriction = friction;

		context = new EnemyStateContext(this);
		
		currentAnimation = assets.standingAnimation;
	};

	public void update(float deltaTime)
	{
		context.getCurrentState().execute(deltaTime);
		stateTime += deltaTime;
	}
	
	public void render(SpriteBatch batch)
	{	
		// Apply Skin Color
		batch.setColor(CharacterSkin.values()[GamePreferences.instance.charSkin].getColor());
		
		// Reset color to white
		batch.setColor(1, 1, 1, 1);
		reg = currentAnimation.getKeyFrame(stateTime, true);
		batch.draw(reg.getTexture(), 
				position.x, position.y, 
				origin.x, origin.y, 
				dimension.x, dimension.y, 
				scale.x, scale.y, 
				rotation,
				reg.getRegionX(), reg.getRegionY(), 
				reg.getRegionWidth(), reg.getRegionHeight(), 
				viewDirection == flip, false);
		
	}
	
	public TextureRegion getRegion() 
	{
		return reg;
	}
	
	
	public void killed() 
	{
		isDead = true;
	};
}
