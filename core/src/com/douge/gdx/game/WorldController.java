package com.douge.gdx.game;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.douge.gdx.game.assets.Assets;
import com.douge.gdx.game.objects.Survivor;
import com.douge.gdx.game.objects.GoldCoin;
import com.douge.gdx.game.objects.JumpDiamond;
import com.douge.gdx.game.objects.Rock;
import com.douge.gdx.game.screens.MenuScreen;

public class WorldController extends InputAdapter
{
	private static final String TAG = WorldController.class.getName();
	
	private Game game;
	
	public Level level;
	public int lives;
	public int score;
	public CameraHelper cameraHelper;
	
	private Rectangle r1 = new Rectangle(); 
	private Rectangle r2 = new Rectangle(); 
	   
	  private float timeLeftGameOverDelay; 
	
	public WorldController(Game game)
	{
		this.game = game;
		init();
	}
	
	private void init()
	{
		Gdx.input.setInputProcessor(this);
		cameraHelper = new CameraHelper();
		lives = Constants.LIVES_START;
	    timeLeftGameOverDelay = 0; 
		initLevel();
	}
	
	private void initLevel () 
	{
		score = 0;
		level = new Level(Constants.LEVEL_01_PATH);
		cameraHelper.setTarget(level.survivor); 
	}
	

	public void update(float deltaTime)
	{
		Gdx.app.log(TAG, "" + level.survivor.context.getCurrentState());
		//Gdx.app.log(TAG, "player: " + level.survivor.position.y + " " + level.survivor.currentVelocity.y);
		handleDebugInput(deltaTime);
		if (isGameOver()) 
		{
			timeLeftGameOverDelay -= deltaTime;
			if (timeLeftGameOverDelay < 0) 
			{
				 backToMenu();
			} 
		}
		else 
		{
			handleInputGame(deltaTime);
		}
		
		level.update(deltaTime);
		testCollisions();
		cameraHelper.update(deltaTime);
		if (!isGameOver() && isPlayerInWater()) 
		{
			lives--;
			if (isGameOver())
			{
				timeLeftGameOverDelay = Constants.TIME_DELAY_GAME_OVER;
			}
			else
			{
				initLevel();
			}
		}
	}

	private void handleDebugInput(float deltaTime) 
	{
		if (Gdx.app.getType() != ApplicationType.Desktop) return;

		// Camera Controls (move)
		if (!cameraHelper.hasTarget(level.survivor));
		{
			float camMoveSpeed = 5 * deltaTime;
			float camMoveSpeedAccelerationFactor = 5;
			if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT))
			{
				camMoveSpeed *=camMoveSpeedAccelerationFactor;
			}
			if (Gdx.input.isKeyPressed(Keys.LEFT)) 
			{
				moveCamera(-camMoveSpeed,0);
			}
			if (Gdx.input.isKeyPressed(Keys.RIGHT)) 
			{
				moveCamera(camMoveSpeed,0);
			}
			if (Gdx.input.isKeyPressed(Keys.UP)) 
			{
				moveCamera(0, camMoveSpeed);
			}
			if (Gdx.input.isKeyPressed(Keys.DOWN)) 
			{
				moveCamera(0,-camMoveSpeed);
			}
			if (Gdx.input.isKeyPressed(Keys.BACKSPACE))
			{
				cameraHelper.setPosition(0, 0);
			}
		}
		
		// Camera Controls (zoom)
		float camZoomSpeed = 1 * deltaTime;
		float camZoomSpeedAccelerationFactor = 5;
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) 
		{
			camZoomSpeed *= camZoomSpeedAccelerationFactor;
		}
		if (Gdx.input.isKeyPressed(Keys.COMMA))
		{
			cameraHelper.addZoom(camZoomSpeed);
		}
		if (Gdx.input.isKeyPressed(Keys.PERIOD)) 
		{
			cameraHelper.addZoom(-camZoomSpeed);
		}	
		if (Gdx.input.isKeyPressed(Keys.SLASH)) 
		{
			cameraHelper.setZoom(1);
		}
	}
	
	private void onCollisionSurvivorWithRock(Rock rock) 
	{
		Survivor astronaut = level.survivor;
		astronaut.context.setStateBasedOnCollisionWithRock(rock);
	};
	
	private void onCollisionAstronautWithGoldCoin(GoldCoin goldcoin) 
	{
		goldcoin.collected = true;
		score += goldcoin.getScore();
		Gdx.app.log(TAG, "Gold coin collected");
	};
	
	private void onCollisionAstronautWithGreenHeart(JumpDiamond greenHeart) 
	{
		greenHeart.collected = true;
		score += greenHeart.getScore();
		level.survivor.setGreenHeartPowerup(true);
		Gdx.app.log(TAG, "Feather collected");
	};
	
	private void testCollisions () 
	{
		r1.set(level.survivor.position.x, level.survivor.position.y,
				level.survivor.bounds.width, level.survivor.bounds.height);
		
		// Test collision: Bunny Head <-> Rocks
		for (Rock rock : level.rocks) 
		{
			r2.set(rock.position.x, rock.position.y, 
					rock.bounds.width, rock.bounds.height);
			
			if (!r1.overlaps(r2)) 
			{
				//System.out.println("not overlapping rock");
				level.survivor.context.getCurrentState().noRockCollision();
				continue;
			}
		
			onCollisionSurvivorWithRock(rock);
			break;
			//System.out.println("im here");
			// IMPORTANT: must do all collisions for valid
			// edge testing on rocks.
		}
		
		// Test collision: Bunny Head <-> Gold Coins
		for (GoldCoin goldcoin : level.goldcoins) 
		{
			if (goldcoin.collected) 
			{
				continue;
			}
		
			r2.set(goldcoin.position.x, goldcoin.position.y,
					goldcoin.bounds.width, goldcoin.bounds.height);
			
			if (!r1.overlaps(r2)) 
			{
				continue;
			}
			
			onCollisionAstronautWithGoldCoin(goldcoin);
			break;
		}
		// Test collision: Bunny Head <-> Feathers
		for (JumpDiamond greenHeart : level.jumpDiamond) 
		{
			if (greenHeart.collected) 
			{
				continue;
			}
			r2.set(greenHeart.position.x, greenHeart.position.y,
					greenHeart.bounds.width, greenHeart.bounds.height);
			if (!r1.overlaps(r2)) 
			{
				continue;
			}
			onCollisionAstronautWithGreenHeart(greenHeart);
			break;
		}
	}
	
	private void handleInputGame (float deltaTime) 
	{
		if (cameraHelper.hasTarget(level.survivor)) 
		{
			// Player Movement
			if (Gdx.input.isKeyPressed(Keys.LEFT)) 
			{
				level.survivor.currentVelocity.x = -level.survivor.maxVelocity.x;
			} 
			else if (Gdx.input.isKeyPressed(Keys.RIGHT)) 
			{
				level.survivor.currentVelocity.x = level.survivor.maxVelocity.x;
			} 
			else 
			{
				// Execute auto-forward movement on non-desktop platform
				if (Gdx.app.getType() != ApplicationType.Desktop) 
				{
					level.survivor.currentVelocity.x = level.survivor.maxVelocity.x;
				}
			}
			
			// Bunny Jump
			boolean dashKeyPressed = Gdx.input.isKeyJustPressed(Keys.SHIFT_LEFT);
			boolean jumpKeyPressed = Gdx.input.justTouched() || Gdx.input.isKeyPressed(Keys.SPACE);
			level.survivor.context.setPlayerStateBasedOnInput(jumpKeyPressed, dashKeyPressed);

		}
	}
	
	public boolean isGameOver () 
	{
		return lives < 0;
	}
		
	public boolean isPlayerInWater () 
	{
		return level.survivor.position.y < -5;
	}
	
	private void moveCamera (float x, float y) 
	{
		//Gdx.app.log(TAG, "(" + cameraHelper.getPosition().x + ", " + cameraHelper.getPosition().y + ")");

		x += cameraHelper.getPosition().x;
		y += cameraHelper.getPosition().y;
		
		cameraHelper.setPosition(x, y);
	}

	@Override
	public boolean keyUp (int keycode) 
	{
		// Reset game world
		if (keycode == Keys.R) 
		{
			init();
			Gdx.app.debug(TAG, "Game world resetted");
		}
	    // Toggle camera follow 
	    else if (keycode == Keys.ENTER)  
	    { 
	      cameraHelper.setTarget(cameraHelper.hasTarget() ? null: level.survivor); 
	      Gdx.app.debug(TAG, "Camera follow enabled: " + cameraHelper.hasTarget()); 
	    } 
	
		// Back to Menu
		else if (keycode == Keys.ESCAPE || keycode == Keys.BACK)
		{
			backToMenu();
		}
		
		return false;
	}
	
	private void backToMenu () 
	{
		// switch to menu screen
		game.setScreen(new MenuScreen(game));
	}
}
