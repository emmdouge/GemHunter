package com.douge.gdx.game.enemystate;

import com.badlogic.gdx.math.MathUtils;
import com.douge.gdx.game.VIEW_DIRECTION;
import com.douge.gdx.game.assets.Assets;
import com.douge.gdx.game.objects.Platform;
import com.douge.gdx.game.objects.enemy.Bat;
import com.douge.gdx.game.objects.enemy.Enemy;

public class EnemyMovingState extends EnemyState
{

	public EnemyMovingState(Enemy enemy, EnemyStateContext context) 
	{
		super(enemy, context);
	}

	@Override
	public void execute(float deltaTime) 
	{

		enemy.currentAnimation = Assets.instance.survivor.standingAnimation;
		enemy.currentVelocity.y = 0;
		enemy.currentVelocity.x = enemy.moveSpeed*VIEW_DIRECTION.getInt(enemy.viewDirection);
		
		if (enemy.currentVelocity.x != 0) 
		{
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
			
			if((int)(Math.random()*500) == 1)
			{
				enemy.currentVelocity.x *= -1;
			}
			if((int)(Math.random()*5000) == 2)
			{
				enemy.stateTime = 0;
				context.setEnemyState(context.getAttackingState());
			}
			
			enemy.viewDirection = enemy.currentVelocity.x < 0 ? VIEW_DIRECTION.LEFT : VIEW_DIRECTION.RIGHT;
			enemy.currentAnimation = enemy.assets.movingAnimation;
		}
		
		enemy.currentVelocity.y += enemy.currentGravity * deltaTime;
		
		// Make sure the object's velocity does not exceed the
		// positive or negative terminal velocity
		enemy.currentVelocity.y = MathUtils.clamp(enemy.currentVelocity.y, -enemy.maxVelocity.y, enemy.maxVelocity.y);
		
		// Move to new position
		enemy.position.x += enemy.currentVelocity.x * deltaTime;
		enemy.position.y += enemy.currentVelocity.y * deltaTime;
		

		
	}

	@Override
	public void onCollisionWith(Platform rock) 
	{
		if(enemy.position.x+enemy.bounds.x < rock.position.x || (enemy.position.x + enemy.bounds.x + enemy.bounds.width) > (rock.position.x + rock.bounds.width))
		{
			enemy.viewDirection = VIEW_DIRECTION.opposite(enemy.viewDirection);
			float offset = enemy.viewDirection == VIEW_DIRECTION.LEFT? -.1f : .1f;
			enemy.position.x += offset;
		}
	}

	@Override
	public void noRockCollision() 
	{	
		if(!enemy.canFly)
		{
			context.noRockCollision();
			context.setEnemyState(context.getFallingState());
		}
	}

}
