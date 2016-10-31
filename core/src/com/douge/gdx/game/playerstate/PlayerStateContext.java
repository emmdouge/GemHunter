package com.douge.gdx.game.playerstate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.douge.gdx.game.VIEW_DIRECTION;
import com.douge.gdx.game.assets.Assets;
import com.douge.gdx.game.enemy.Enemy;
import com.douge.gdx.game.objects.Player;
import com.douge.gdx.game.objects.Rock;
import com.douge.gdx.game.utils.AudioManager;

public class PlayerStateContext 
{
	private FallingState fallingState;
	private GroundedState groundedState;
	private JumpFallingState jumpFallingState;
	private JumpRisingState jumpRisingState;
	private DashState dashState;
	private HurtState hurtState;
	private PlayerState currentState;
	private Player player;
	
	public PlayerStateContext(Player player)
	{
		this.player = player;
		fallingState = new FallingState(player, this);
		groundedState = new GroundedState(player, this);
		jumpFallingState = new JumpFallingState(player, this);
		jumpRisingState = new JumpRisingState(player, this);
		dashState = new DashState(player, this);
		hurtState = new HurtState(player, this);
		
		currentState = groundedState;
	}
	
	public PlayerState getCurrentState()
	{
		return currentState;
	}
	
	public void setPlayerState(PlayerState nextState)
	{
		currentState = nextState;
	}
	
	public FallingState getFallingState()
	{
		return fallingState;
	}
	
	public GroundedState getGroundState()
	{
		return groundedState;
	}
	
	public HurtState getHurtState()
	{
		return hurtState;
	}
	
	public JumpFallingState getJumpFallingState()
	{
		return jumpFallingState;
	}
	
	public DashState getDashState()
	{
		return dashState;
	}
	
	public JumpRisingState getJumpRisingState()
	{
		return jumpRisingState;
	}
	
	public void execute(float deltaTime)
	{
		Gdx.app.log(currentState.tag, "executed");
		currentState.execute(deltaTime);
	}

	public void jump(boolean jumpKeyPressed) 
	{
		if(jumpKeyPressed)
		{
			if(currentState == groundedState)
			{
				AudioManager.instance.play(Assets.instance.sounds.jump); 
				player.timeJumping = 0;
				setPlayerState(jumpRisingState);
			}
			else if(currentState == fallingState || currentState == jumpFallingState)
			{
				if(player.hasJumpPowerup && player.timeJumping < player.JUMP_TIME_MAX)
				{
			        AudioManager.instance.play(Assets.instance.sounds.jumpWithPotion, 1, MathUtils.random(1.0f, 1.1f));
					setPlayerState(jumpRisingState);
				}
			}
			//holding jump
			else if(currentState == jumpRisingState)
			{
				if(player.timeJumping > player.JUMP_TIME_MAX)
				{
					setPlayerState(fallingState);
				}
			}
		}
		else
		{
			if(currentState == jumpRisingState)
			{
				setPlayerState(jumpFallingState);
			}
		}
	}
	
	public void dash(boolean dashKeyPressed) 
	{
		if(dashKeyPressed)
		{
			if(currentState == jumpRisingState)
			{
				player.timeJumping = player.JUMP_TIME_MAX;
			}
			if(player.timeDashing < player.DASH_TIME_MAX)
			{
				AudioManager.instance.play(Assets.instance.sounds.dash);
				setPlayerState(dashState);
			}
		}
	}
	
	public void setStateBasedOnCollisionWithRock(Rock rock)
	{
		currentState.onCollisionWith(rock);
	}

	public void setPlayerStateBasedOnInput(boolean jumpKeyPressed, boolean dashKeyPressed) 
	{
		if(currentState != hurtState)
		{
			jump(jumpKeyPressed);
			dash(dashKeyPressed);
		}
	}
	
	public void noRockCollision()
	{
		player.currentGravity = player.gravity;
		player.currentFriction = player.friction;
		player.currentParticleEffect.allowCompletion();
	}
	
	public void onCollisionWith(Enemy enemy)
	{
		float diffBetweenBottomOfPlayerAndTopOfEnemy = enemy.position.y + enemy.bounds.height - player.position.y;
		boolean landOnTop =  diffBetweenBottomOfPlayerAndTopOfEnemy <= 0.07f;
		if(!enemy.hasBeenKilled && currentState != hurtState)
		{
			if(landOnTop && enemy.isHurtable)
			{
				enemy.stateTime = 0f;
				enemy.hasBeenKilled = true;
				enemy.context.setEnemyState(enemy.context.getDeadState());
				player.timeJumping = 0;
				player.context.jump(true);
				player.context.setPlayerState(player.context.getJumpRisingState());
			}
			else
			{
				AudioManager.instance.play(Assets.instance.sounds.liveLost);
				player.context.setPlayerState(player.context.getHurtState());			
			}
		}
	}
}
