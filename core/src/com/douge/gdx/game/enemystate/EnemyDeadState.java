package com.douge.gdx.game.enemystate;

import com.badlogic.gdx.Gdx;
import com.douge.gdx.game.objects.enemy.Enemy;
import com.douge.gdx.game.objects.platform.Platform;

public class EnemyDeadState extends EnemyState
{

	public EnemyDeadState(Enemy enemy, EnemyStateContext context) 
	{
		super(enemy, context);
	}

	@Override
	public void execute(float deltaTime) 
	{
		enemy.isHurtable = false;
		enemy.currentAnimation = enemy.assets.deadAnimation;
		if(enemy.currentAnimation.isAnimationFinished(enemy.stateTime+.05f))
		{
			enemy.killed();
		}
		enemy.position.y += enemy.currentVelocity.y * deltaTime;
	}

	@Override
	public void onCollisionWith(Platform rock) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void noRockCollision() 
	{
		if(!enemy.canFly)
		{
			context.noRockCollision();
		}
		else
		{
			enemy.currentVelocity.y = -1f;
		}
	}

}
