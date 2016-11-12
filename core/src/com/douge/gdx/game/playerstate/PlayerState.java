package com.douge.gdx.game.playerstate;

import com.douge.gdx.game.objects.Player;
import com.douge.gdx.game.objects.platform.Platform;

public abstract class PlayerState 
{
	public Player player;
	public float stateTime = 0f;
	public PlayerStateContext context;
	public String tag;
	
	public PlayerState(PlayerStateContext context)
	{
		this.context = context;
		this.player = context.getPlayer();
	}

	public abstract void execute(float deltaTime);

	public abstract void onCollisionWith(Platform platform);
	public abstract void noPlatformCollision();

}
