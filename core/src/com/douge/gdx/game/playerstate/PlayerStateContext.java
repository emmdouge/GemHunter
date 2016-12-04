package com.douge.gdx.game.playerstate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.douge.gdx.game.assets.Assets;
import com.douge.gdx.game.objects.Fireball;
import com.douge.gdx.game.objects.Player;
import com.douge.gdx.game.objects.VIEW_DIRECTION;
import com.douge.gdx.game.objects.enemy.Enemy;
import com.douge.gdx.game.objects.platform.Platform;
import com.douge.gdx.game.utils.AudioManager;

public class PlayerStateContext 
{
	private FallingState fallingState;
//	private GroundedState groundedState;
	private JumpFallingState jumpFallingState;
	private JumpRisingState jumpRisingState;
	private DashState dashState;
	private HurtState hurtState;
	private JumpAttackState jumpAttackState;
	private GroundedAttackState groundedAttackState;
	private PlayerState currentState;
	private Player player;
	
	public PlayerStateContext(Player player)
	{
		this.player = player;
		fallingState = new FallingState(this);
//		groundedState = new GroundedState(this);
		jumpFallingState = new JumpFallingState(this);
		jumpRisingState = new JumpRisingState(this);
		dashState = new DashState(this);
		hurtState = new HurtState(this);
		jumpAttackState = new JumpAttackState(this);
		groundedAttackState = new GroundedAttackState(this);
		
		currentState = fallingState;
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
	
//	public GroundedState getGroundState()
//	{
//		return groundedState;
//	}
	
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
	
	public JumpAttackState getJumpAttackState()
	{
		return jumpAttackState;
	}
	
	public void execute(float deltaTime)
	{
		currentState.execute(deltaTime);
	}
	
	public Player getPlayer()
	{
		return player;
	}

	public void jump(boolean jumpKeyPressed) 
	{
		if(jumpKeyPressed)
		{
			if(currentState == fallingState)
			{
				AudioManager.instance.play(Assets.instance.sounds.jump);
				setPlayerState(jumpRisingState);
			}
			else if(currentState == jumpFallingState)
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
					setPlayerState(jumpFallingState);
				}
				else
				{
					player.currentVelocity.y = player.maxVelocity.y;
				}
			}
		}
		else //if button is not being held anymore
		{
			if(currentState == jumpRisingState)
			{
				player.timeJumping = player.JUMP_TIME_MAX;
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
				//makes sure you cant jump after dashing
				player.timeJumping = player.JUMP_TIME_MAX;
			}
			if(player.timeDashing < player.DASH_TIME_MAX)
			{
				AudioManager.instance.play(Assets.instance.sounds.dash);
				setPlayerState(dashState);
			}
		}
	}
	
	public void attack(boolean attackKeyPressed)
	{
		if(attackKeyPressed && player.numFireballs > 0)
		{
			if(player.timeAttacking < player.TIME_BETWEEN_ATTACKS)
			{
				AudioManager.instance.playUntilDone(Assets.instance.music.throwingSound);
				float xOffset = player.viewDirection == VIEW_DIRECTION.LEFT? -1 : 1;
				float yOffset = .2f;
				boolean inAir =  player.currentVelocity.y != 0;
				if(currentState == jumpRisingState || currentState == jumpFallingState || inAir)
				{
					//only get called the before we enter the attack states
					jumpAttackState.stateTime = 0f;
					Fireball fireball = new Fireball(player.viewDirection);
					fireball.position.x = player.position.x + xOffset;
					fireball.position.y = player.position.y + yOffset; 
					player.fireballs.add(fireball);
					player.numFireballs--;
					setPlayerState(jumpAttackState);
				}
				else if(!inAir)
				{
					groundedAttackState.stateTime = 0f;
					Fireball fireball = new Fireball(player.viewDirection);
					fireball.position.x = player.position.x + xOffset;
					fireball.position.y = player.position.y + yOffset; 
					player.fireballs.add(fireball);
					player.numFireballs--;
					setPlayerState(groundedAttackState);
				}
				else if(currentState == groundedAttackState || currentState == jumpAttackState)
				{
					
				}
			}
		}
	}
	
	public void setStateBasedOnCollisionWithPlatform(Platform platform)
	{
		player.inContactWithPlatform = true;
		currentState.onCollisionWith(platform);
	}

	public void setPlayerStateBasedOnInput(boolean jumpKeyPressed, boolean dashKeyPressed, boolean attackKeyPressed) 
	{
		if(currentState != hurtState && currentState != dashState)
		{
			jump(jumpKeyPressed);
			dash(dashKeyPressed);
			attack(attackKeyPressed);
		}
	}
	
	public void noPlatformCollision()
	{
		player.currentGravity = player.gravity;
		player.currentFriction = player.friction;
		player.currentParticleEffect.allowCompletion();
		player.inContactWithPlatform = false;
	}
	
	public void onCollisionWith(Enemy enemy)
	{
		float diffBetweenBottomOfPlayerAndTopOfEnemy = enemy.position.y + enemy.bounds.height - player.position.y;
		boolean landOnTop =  diffBetweenBottomOfPlayerAndTopOfEnemy <= 0.07f;
		if(!enemy.hasBeenKilled && currentState != hurtState && !player.isInvincible)
		{
			if(landOnTop && enemy.isHurtable && !enemy.isHorse)
			{
				enemy.stateTime = 0f;
				enemy.hasBeenKilled = true;
				enemy.context.setEnemyState(enemy.context.getDeadState());
				player.timeJumping = 0;
				player.timeDashing = 0;
				setPlayerState(jumpRisingState);
				jump(true);
			}
			else if(enemy.isHorse)
			{
				//do nothing
			}
			else
			{
				AudioManager.instance.play(Assets.instance.sounds.liveLost);
				setPlayerState(hurtState);			
			}
		}
	}
}
