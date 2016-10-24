package com.douge.gdx.game.enemystate;

import com.badlogic.gdx.math.MathUtils;
import com.douge.gdx.game.VIEW_DIRECTION;
import com.douge.gdx.game.assets.Assets;
import com.douge.gdx.game.enemy.Bat;
import com.douge.gdx.game.enemy.Enemy;
import com.douge.gdx.game.objects.Rock;

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
			
			if((int)(Math.random()*100) == 1)
			{
				enemy.currentVelocity.x *= -1;
			}
			if((int)(Math.random()*100) == 2)
			{
				enemy.stateTime = 0;
				context.setEnemyState(context.getAttackingState());
			}
			
			enemy.viewDirection = enemy.currentVelocity.x < 0 ? VIEW_DIRECTION.LEFT : VIEW_DIRECTION.RIGHT;
			enemy.currentAnimation = enemy.assets.movingAnimation;
		}
		
		// Move to new position
		enemy.position.x += enemy.currentVelocity.x * deltaTime;
	}

	@Override
	public void onCollisionWith(Rock rock) 
	{
		//System.out.println("collided");
		if(enemy.position.x < rock.position.x || enemy.position.x + enemy.bounds.width > rock.position.x + rock.bounds.width)
		{
			enemy.viewDirection = VIEW_DIRECTION.opposite(enemy.viewDirection);
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
