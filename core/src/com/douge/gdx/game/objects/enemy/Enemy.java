package com.douge.gdx.game.objects.enemy;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Array;
import com.douge.gdx.game.Constants;
import com.douge.gdx.game.VIEW_DIRECTION;
import com.douge.gdx.game.assets.Assets;
import com.douge.gdx.game.assets.enemy.AssetEnemy;
import com.douge.gdx.game.enemystate.EnemyStateContext;
import com.douge.gdx.game.objects.AbstractGameObject;
import com.douge.gdx.game.screen.CharacterSkin;
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
	
	public boolean canFly = false;
	
	public boolean isHurtable = true;
	
	public boolean isBoss = false;
	
	public boolean dropsHealth = false;
	
	protected VIEW_DIRECTION flip;
	
	public ShapeRenderer shapeRenderer = new ShapeRenderer();
	
	//disables player ability to kill enemy
	public boolean hasBeenKilled = false;
	
	public boolean droppedHealth = false;
	
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
		
		
		batch.end();
		shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.rect(position.x + bounds.x, position.y + bounds.y, bounds.width, bounds.height);
		shapeRenderer.end();
		batch.begin();
		
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
