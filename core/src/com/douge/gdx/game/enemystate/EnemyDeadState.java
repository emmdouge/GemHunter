package com.douge.gdx.game.enemystate;

import com.douge.gdx.game.enemy.Enemy;
import com.douge.gdx.game.objects.Rock;

public class EnemyDeadState extends EnemyState
{

	public EnemyDeadState(Enemy enemy, EnemyStateContext context) 
	{
		super(enemy, context);
	}

	@Override
	public void execute(float deltaTime) 
	{
		enemy.currentAnimation = enemy.assets.deadAnimation;
		if(enemy.currentAnimation.isAnimationFinished(enemy.stateTime+.05f))
		{
			enemy.killed();
		}
	}

	@Override
	public void onCollisionWith(Rock rock) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void noRockCollision() 
	{
		context.noRockCollision();
		context.setEnemyState(context.getFallingState());
	}

}
