package com.douge.gdx.game.assets.enemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public abstract class AssetEnemy 
{
	public Animation movingAnimation;
	public Animation standingAnimation;
	public Animation fallingAnimation;
	public Animation attackingAnimation;
	public Animation deadAnimation;
	
	
	public Animation getMovingAnimation()
	{
		return movingAnimation;
	}
	
	public Animation getStandingAnimation()
	{
		return standingAnimation;
	}
	
	public Animation getFallingAnimation()
	{
		return fallingAnimation;
	}
	
	public Animation getAttackingAnimation()
	{
		return attackingAnimation;
	}
	
	public Animation getDeadAnimation()
	{
		return deadAnimation;
	}
}
