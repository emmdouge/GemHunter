package com.douge.gdx.game.playerstate;

import com.badlogic.gdx.Gdx;
import com.douge.gdx.game.objects.Survivor;
import com.douge.gdx.game.objects.Rock;

public class PlayerStateContext 
{
	private FallingState fallingState;
	private GroundedState groundedState;
	private JumpFallingState jumpFallingState;
	private JumpRisingState jumpRisingState;
	private DashState dashState;
	private SlideState slideState;
	private PlayerState currentState;
	private Survivor player;
	
	public PlayerStateContext(Survivor astronaut)
	{
		player = astronaut;
		fallingState = new FallingState(astronaut, this);
		groundedState = new GroundedState(astronaut, this);
		jumpFallingState = new JumpFallingState(astronaut, this);
		jumpRisingState = new JumpRisingState(astronaut, this);
		dashState = new DashState(astronaut, this);
		slideState = new SlideState(astronaut, this);
		
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
	
	public GroundedState getGroundState()
	{
		return groundedState;
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
				player.timeJumping = 0;
				setPlayerState(jumpRisingState);
			}
			else if(currentState == fallingState || currentState == jumpFallingState)
			{
				if(player.hasGreenHeartPowerup)
				{
					player.timeJumping = player.JUMP_TIME_OFFSET_FLYING;
					setPlayerState(jumpRisingState);
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
			setPlayerState(dashState);
		}
	}
	
	public void setStateBasedOnCollisionWithRock(Rock rock)
	{
		if(currentState == fallingState || currentState == jumpFallingState)
		{
			player.position.y = rock.position.y + player.bounds.height;
			setPlayerState(groundedState);
		}
		else if(currentState == jumpRisingState)
		{
			if(player.position.y < rock.position.y)
			{
				setPlayerState(fallingState);
			}
			//player.position.y = rock.position.y + player.bounds.height + player.origin.y;
		}
	}

	public void setPlayerStateBasedOnInput(boolean jumpKeyPressed, boolean dashKeyPressed, boolean slideKeyPressed) 
	{
		jump(jumpKeyPressed);
		dash(dashKeyPressed);
		slide(slideKeyPressed);
	}

	private void slide(boolean slideKeyPressed) 
	{
		if(slideKeyPressed)
		{
			if (currentState == groundedState)
			{
				setPlayerState(slideState);
			}
		}
	}
}
