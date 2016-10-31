package com.douge.gdx.game.playerstate;

import com.douge.gdx.game.objects.Player;
import com.douge.gdx.game.objects.Platform;

public abstract class PlayerState 
{
	public Player player;
	public PlayerStateContext context;
	public String tag;
	
	public PlayerState(Player player, PlayerStateContext context)
	{
		this.player = player;
		this.context = context;
	}

	public abstract void execute(float deltaTime);

	public abstract void onCollisionWith(Platform platform);
	public abstract void noPlatformCollision();

}
