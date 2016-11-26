package com.douge.gdx.game.enemystate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.douge.gdx.game.assets.Assets;
import com.douge.gdx.game.objects.VIEW_DIRECTION;
import com.douge.gdx.game.objects.enemy.Enemy;
import com.douge.gdx.game.objects.platform.Platform;

public class EnemyFallingState extends EnemyState
{

	public EnemyFallingState(Enemy enemy, EnemyStateContext context) 
	{
		super(enemy, context);
	}

	@Override
	public void execute(float deltaTime)
	{
		enemy.isHurtable = true;
		enemy.currentVelocity.x = enemy.moveSpeed*VIEW_DIRECTION.getInt(enemy.viewDirection);
		if (enemy.currentVelocity.x != 0) 
		{
			enemy.viewDirection = enemy.currentVelocity.x < 0 ? VIEW_DIRECTION.LEFT : VIEW_DIRECTION.RIGHT;
			// Apply friction
			if (enemy.currentVelocity.x > 0) 
			{
				enemy.currentVelocity.x = Math.max(enemy.currentVelocity.x - enemy.currentFriction * deltaTime, 0);
			} 
			else 
			{
				enemy.currentVelocity.x = Math.min(enemy.currentVelocity.x + enemy.currentFriction * deltaTime, 0);
			}
			// Make sure the object's velocity does not exceed the
			// positive or negative terminal velocity
			enemy.currentVelocity.x = MathUtils.clamp(enemy.currentVelocity.x, -enemy.maxVelocity.x, enemy.maxVelocity.x);

			if(enemy.inContactWithPlatform)
			{
				enemy.currentAnimation = enemy.assets.movingAnimation;
			}
			else if(!enemy.inContactWithPlatform && enemy.currentVelocity.y != 0)
			{	
				enemy.currentAnimation = enemy.assets.fallingAnimation;
			}
		}
		else if(enemy.currentVelocity.y == 0)
		{
			enemy.currentAnimation = enemy.assets.standingAnimation;
		}
		
		// Apply acceleration
		enemy.currentVelocity.y += enemy.currentGravity * deltaTime;
		
		// Make sure the object's velocity does not exceed the
		// positive or negative terminal velocity
		enemy.currentVelocity.y = MathUtils.clamp(enemy.currentVelocity.y, -enemy.maxVelocity.y, enemy.maxVelocity.y);
		
		enemy.position.y += enemy.currentVelocity.y * deltaTime;
		enemy.position.x += enemy.currentVelocity.x * deltaTime;
		
		if((int)(Math.random()*100) == 1)
		{
			enemy.stateTime = 0;
			context.setEnemyState(context.getAttackingState());
		}
		else if((int)(Math.random()*100) == 2)
		{
			enemy.viewDirection = VIEW_DIRECTION.opposite(enemy.viewDirection);
		}
	}

	@Override
	public void onCollisionWith(Platform platform) 
	{
		enemy.inContactWithPlatform = true;
		//drawn starting from bottom left
		float diffBetweenTopOfPlayerAndBottomOfPlatform = Math.abs(enemy.position.y + enemy.bounds.height + .001f - platform.position.y);
		float diffBetweenLeftSideOfPlayerAndRightSideOfPlatform = Math.abs(enemy.position.x  - platform.bounds.width - platform.position.x);
		float diffBetweenBottomOfPlayerAndTopOfPlatform = Math.abs(platform.position.y + platform.bounds.height - enemy.position.y);
		float diffBetweenRightSideOfPlayerAndLeftSideOfPlatform = Math.abs(enemy.position.x + enemy.bounds.width - platform.position.x);
		
		boolean hitTop =  diffBetweenTopOfPlayerAndBottomOfPlatform <= 0.1f;
		boolean landOnTop =  diffBetweenBottomOfPlayerAndTopOfPlatform <= 0.1f;
		boolean hitLeftEdge = diffBetweenRightSideOfPlayerAndLeftSideOfPlatform <= 0.3f;
		boolean hitRightEdge = diffBetweenLeftSideOfPlayerAndRightSideOfPlatform <= 0.3f;
		
		if(landOnTop)
		{
			enemy.currentGravity = 0;
			enemy.position.y = platform.position.y + platform.bounds.height;
			enemy.friction = platform.body.getFixtureList().get(0).getFriction();
			enemy.currentVelocity.y = platform.currentVelocity.y;
			if(platform.body.getLinearVelocity().y < 0)
			{
				enemy.position.y -= .01f;
			}
			if(platform.body.getLinearVelocity().x != 0)
			{
				enemy.currentVelocity.x = platform.body.getLinearVelocity().x;
			}
		}
		else
		{
			if(!enemy.canFly)
			enemy.currentGravity = enemy.gravity;
		}
	}

	@Override
	public void noRockCollision() 
	{
		context.noRockCollision();
	}

}
