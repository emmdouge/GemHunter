package com.douge.gdx.game.enemystate;

import com.douge.gdx.game.objects.Platform;
import com.douge.gdx.game.objects.enemy.Enemy;

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
			context.setEnemyState(context.getMovingState());
		}
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
			context.setEnemyState(context.getFallingState());
		}
	}

}
