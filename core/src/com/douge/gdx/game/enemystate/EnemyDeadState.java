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
	public void onCollisionWith(Platform platform) 
	{
		enemy.inContactWithPlatform = true;
		//drawn starting from bottom left
		float diffBetweenTopOfPlayerAndBottomOfPlatform = Math.abs(enemy.position.y + enemy.bounds.height + .001f - platform.position.y);
		float diffBetweenLeftSideOfPlayerAndRightSideOfPlatform = Math.abs(enemy.position.x  - platform.bounds.width - platform.position.x);
		float diffBetweenBottomOfPlayerAndTopOfPlatform = Math.abs(platform.position.y + platform.bounds.height - enemy.position.y);
		float diffBetweenRightSideOfPlayerAndLeftSideOfPlatform = Math.abs(enemy.position.x + enemy.bounds.width - platform.position.x);
		
		boolean hitTop =  diffBetweenTopOfPlayerAndBottomOfPlatform <= 0.1f;
		boolean landOnTop =  diffBetweenBottomOfPlayerAndTopOfPlatform <= 0.1f;
		boolean hitLeftEdge = diffBetweenRightSideOfPlayerAndLeftSideOfPlatform <= 0.3f;
		boolean hitRightEdge = diffBetweenLeftSideOfPlayerAndRightSideOfPlatform <= 0.3f;
		
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
