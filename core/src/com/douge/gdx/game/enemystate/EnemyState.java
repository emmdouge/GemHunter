package com.douge.gdx.game.enemystate;

import com.douge.gdx.game.enemy.Enemy;
import com.douge.gdx.game.objects.Rock;
import com.douge.gdx.game.objects.Player;

public abstract class EnemyState 
{
	public Enemy enemy;
	public EnemyStateContext context;
	public String tag;
	
	public EnemyState(Enemy enemy, EnemyStateContext context)
	{
		this.enemy = enemy;
		this.context = context;
	}

	public abstract void execute(float deltaTime);
	public abstract void onCollisionWith(Rock rock);
	public abstract void noRockCollision();
}
