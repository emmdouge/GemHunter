package com.douge.gdx.game.enemystate;

import com.badlogic.gdx.Gdx;
import com.douge.gdx.game.assets.enemy.AssetBat;
import com.douge.gdx.game.enemystate.EnemyFallingState;
import com.douge.gdx.game.objects.enemy.Bat;
import com.douge.gdx.game.objects.enemy.Enemy;

public class EnemyStateContext
{
	private EnemyFallingState fallingState;
	private EnemyDeadState deadState;
	private EnemyAttackingState attackingState;
	private EnemyState currentState;
	private Enemy enemy;
	
	public EnemyStateContext(Enemy enemy)
	{
		this.enemy = enemy;
		fallingState = new EnemyFallingState(enemy, this);
		deadState = new EnemyDeadState(enemy, this);
		attackingState = new EnemyAttackingState(enemy, this);
		currentState = fallingState;
	}
	
	public EnemyState getCurrentState()
	{
		return currentState;
	}
	
	public void setEnemyState(EnemyState nextState)
	{
		currentState = nextState;
	}
	
	public EnemyFallingState getFallingState()
	{
		return fallingState;
	}
	
	public EnemyAttackingState getAttackingState()
	{
		return attackingState;
	}
	
	public EnemyDeadState getDeadState()
	{
		return deadState;
	}

	public void execute(float deltaTime)
	{
		currentState.execute(deltaTime);
	}

	public void noRockCollision()
	{
		enemy.currentGravity = enemy.gravity;
		enemy.currentFriction = enemy.friction;
		enemy.inContactWithPlatform = false;
	}
}
