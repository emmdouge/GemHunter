package com.douge.gdx.game.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;

public class AssetSounds 
{
	public final Sound jump;
	public final Sound jumpWithPotion;
	public final Sound pickupCoin;
	public final Sound pickupPotion;
	public final Sound liveLost;
	public final Sound dash;
	
	protected AssetSounds(AssetManager assetManager)
	{
		jump = assetManager.get("../core/assets/sounds/jump.wav", Sound.class);		
		jumpWithPotion = assetManager.get("../core/assets/sounds/jump_with_potion.wav", Sound.class);
		pickupCoin = assetManager.get("../core/assets/sounds/pickup_coin.wav", Sound.class);
		pickupPotion = assetManager.get("../core/assets/sounds/pickup_potion.wav", Sound.class);
		liveLost = assetManager.get("../core/assets/sounds/live_lost.wav", Sound.class);
		dash = assetManager.get("../core/assets/sounds/dash.wav", Sound.class);
	}
}
