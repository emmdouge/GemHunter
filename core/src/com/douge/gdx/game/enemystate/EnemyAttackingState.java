package com.douge.gdx.game.enemystate;

import com.douge.gdx.game.objects.enemy.Enemy;
import com.douge.gdx.game.objects.platform.Platform;

public class EnemyAttackingState extends EnemyState
{

	public EnemyAttackingState(Enemy enemy, EnemyStateContext context) 
	{
		super(enemy, context);
	}

	@Override
	public void execute(float deltaTime) 
	{
		enemy.isHurtable = false;
		enemy.currentAnimation = enemy.assets.attackingAnimation;
		if(enemy.currentAnimation.isAnimationFinished(enemy.stateTime+.05f))
		{
			enemy.isHurtable = true;
			context.setEnemyState(context.getFallingState());
		}
	}

	@Override
	public void onCollisionWith(Platform platform) 
	{
		enemy.inContactWithPlatform = true;
		//drawn starting from bottom left
		float diffBetweenBottomOfPlayerAndTopOfPlatform = Math.abs(platform.position.y + platform.bounds.height - enemy.position.y);
		boolean landOnTop =  diffBetweenBottomOfPlayerAndTopOfPlatform <= 0.1f;
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
			enemy.currentGravity = enemy.gravity;
		}
	}

	@Override
	public void noRockCollision() 
	{
		if(!enemy.canFly)
		{
			context.noRockCollision();
			if(!enemy.hasBeenKilled)
			{
				context.setEnemyState(context.getFallingState());
			}
			else
			{
				context.setEnemyState(context.getDeadState());
			}
		}
	}

}
