package com.douge.gdx.game.enemy;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.douge.gdx.game.VIEW_DIRECTION;
import com.douge.gdx.game.assets.enemy.AssetBat;
import com.douge.gdx.game.assets.enemy.AssetEnemy;
import com.douge.gdx.game.screens.CharacterSkin;
import com.douge.gdx.game.utils.GamePreferences;

public class Bat extends Enemy
{
	private final float flightHeight = 40;
	
	public Bat(AssetBat assets) 
	{
		super(assets, 1f, VIEW_DIRECTION.RIGHT);
	}
}
