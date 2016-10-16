package com.douge.gdx.game.enemystate;

import com.douge.gdx.game.enemy.Enemy;
import com.douge.gdx.game.objects.Rock;

public class EnemyAttackingState extends EnemyState
{

	public EnemyAttackingState(Enemy enemy, EnemyStateContext context) 
	{
		super(enemy, context);
	}

	@Override
	public void execute(float deltaTime) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCollisionWith(Rock rock) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void noRockCollision() 
	{
		// TODO Auto-generated method stub
		
	}

}
