package com.douge.gdx.game.enemystate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.douge.gdx.game.VIEW_DIRECTION;
import com.douge.gdx.game.assets.Assets;
import com.douge.gdx.game.enemy.Enemy;
import com.douge.gdx.game.objects.Platform;

public class EnemyFallingState extends EnemyState
{

	public EnemyFallingState(Enemy enemy, EnemyStateContext context) 
	{
		super(enemy, context);
	}

	@Override
	public void execute(float deltaTime)
	{

		enemy.currentAnimation = enemy.assets.fallingAnimation;
		if (enemy.currentVelocity.x != 0) 
		{
			// Apply friction
			if (enemy.currentVelocity.x > 0) 
			{
				enemy.currentVelocity.x = Math.max(enemy.currentVelocity.x - enemy.friction * deltaTime, 0);
			} 
			else 
			{
				enemy.currentVelocity.x = Math.min(enemy.currentVelocity.x + enemy.friction * deltaTime, 0);
			}
			enemy.viewDirection = enemy.currentVelocity.x < 0 ? VIEW_DIRECTION.LEFT : VIEW_DIRECTION.RIGHT;
		}

		// Make sure the object's velocity does not exceed the
		// positive or negative terminal velocity
		enemy.currentVelocity.x = MathUtils.clamp(enemy.currentVelocity.x, -enemy.maxVelocity.x, enemy.maxVelocity.x);
		
		// Apply acceleration
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
		//drawn starting from bottom left
		float diffBetweenBottomOfPlayerAndTopOfRock = rock.position.y + rock.bounds.height - enemy.position.y;
		float diffBetweenLeftSideOfPlayerAndRightSideOfRock = rock.position.x + rock.bounds.x - enemy.position.x;
		float diffBetweenTopOfPlayerAndBottomOfRock = enemy.position.y + enemy.bounds.height + .001f - rock.position.y;
		float diffBetweenRightSideOfPlayerAndLeftSideOfRock = enemy.position.x + enemy.bounds.width + .001f - rock.position.x;
		
		boolean hitTop =  diffBetweenTopOfPlayerAndBottomOfRock <= 0.07f;
		boolean landOnTop =  diffBetweenBottomOfPlayerAndTopOfRock <= 0.07f;
		boolean hitLeftEdge = diffBetweenRightSideOfPlayerAndLeftSideOfRock <= 0.07f;
		boolean hitRightEdge = diffBetweenLeftSideOfPlayerAndRightSideOfRock <= 0.07f;
		
		if(landOnTop)
		{
			//Gdx.app.log(tag, "enemy: " + enemy.position.y + " " + (rock.position.y + rock.bounds.height));
			enemy.currentGravity = 0;
			enemy.currentVelocity.y = 0;
			enemy.position.y = rock.position.y + rock.bounds.height - .001f;
			//Gdx.app.log(tag, "enemy: " + enemy.position.y + " ");
			context.setEnemyState(context.getMovingState());
			//Gdx.app.log(tag, "player: " + player.position.y + " " + player.currentVelocity.y);
		}
		else if(hitTop)
		{
			//Gdx.app.log(tag, "player: " + player.position.y + " " + rock.position.y);
			enemy.currentGravity = 0;
			enemy.currentVelocity.y = 0;
			enemy.position.y = rock.position.y - enemy.bounds.height - .001f;
			context.setEnemyState(context.getFallingState());
			//Gdx.app.log(tag, "player: " + player.position.y + " " + player.currentVelocity.y);
		}
		else if(hitLeftEdge)
		{
			//Gdx.app.log(tag, "rock: " + rock.position.x + "+" + rock.bounds.height + "=" + (rock.position.y+rock.bounds.height) + ", player: " + player.position.y + " " +(4.5-player.position.y) );
			enemy.currentFriction = 0;
			enemy.currentVelocity.x = 0;
			
			//since the rocks are all linked together, rock's bound witdth is the entire platform
			enemy.position.x = rock.position.x - 1;
		}
		else if(hitRightEdge)
		{
			//Gdx.app.log(tag, "rock: " + rock.position.x + "+" + rock.bounds.width + "=" + (rock.position.x+rock.bounds.width) + ", player: " + player.position.y + " " +(4.5-player.position.y) );
			enemy.currentFriction = 0;
			enemy.currentVelocity.x = 0;
			enemy.position.x = rock.position.x + rock.bounds.width;
		}
	}

	@Override
	public void noRockCollision() 
	{
		context.noRockCollision();
	}

}
