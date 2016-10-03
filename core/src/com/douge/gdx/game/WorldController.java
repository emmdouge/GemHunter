package com.douge.gdx.game;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.douge.gdx.game.assets.Assets;
import com.douge.gdx.game.objects.Astronaut;
import com.douge.gdx.game.objects.Astronaut.JUMP_STATE;
import com.douge.gdx.game.objects.GoldCoin;
import com.douge.gdx.game.objects.GreenHeart;
import com.douge.gdx.game.objects.Rock;

public class WorldController extends InputAdapter
{
	private static final String TAG = WorldController.class.getName();
	public Level level;
	public int lives;
	public int score;
	public CameraHelper cameraHelper;
	
	private Rectangle r1 = new Rectangle(); 
	private Rectangle r2 = new Rectangle(); 
	   
	  private float timeLeftGameOverDelay; 
	
	public WorldController()
	{
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
		cameraHelper.setTarget(level.astronaut); 
	}
	

	public void update(float deltaTime)
	{
		handleDebugInput(deltaTime);
		if (isGameOver()) 
		{
			timeLeftGameOverDelay -= deltaTime;
			if (timeLeftGameOverDelay < 0) 
			{
				 init();
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
		if (!cameraHelper.hasTarget(level.astronaut));
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
	
	private void onCollisionBunnyHeadWithRock(Rock rock) 
	{
		Astronaut astronaut = level.astronaut;
		
		float heightDifference = Math.abs(astronaut.position.y - ( rock.position.y + rock.bounds.height));
		if (heightDifference > 0.25f) 
		{
			boolean hitRightEdge = astronaut.position.x > (rock.position.x + rock.bounds.width / 2.0f);
		
			if (hitRightEdge) 
			{
				astronaut.position.x = rock.position.x + rock.bounds.width;
			} 
			else 
			{
				astronaut.position.x = rock.position.x - astronaut.bounds.width;
			}
			return;
		}
		
		switch (astronaut.jumpState) 
		{
			case GROUNDED:
				break;
			case FALLING:
			case JUMP_FALLING:
				astronaut.position.y = rock.position.y + astronaut.bounds.height + astronaut.origin.y;
				astronaut.jumpState = JUMP_STATE.GROUNDED;
				break;
			case JUMP_RISING:
				astronaut.position.y = rock.position.y + astronaut.bounds.height + astronaut.origin.y;
				break;
		}
	};
	
	private void onCollisionBunnyWithGoldCoin(GoldCoin goldcoin) 
	{
		goldcoin.collected = true;
		score += goldcoin.getScore();
		Gdx.app.log(TAG, "Gold coin collected");
	};
	
	private void onCollisionBunnyWithFeather(GreenHeart greenHeart) 
	{
		greenHeart.collected = true;
		score += greenHeart.getScore();
		level.astronaut.setGreenHeartPowerup(true);
		Gdx.app.log(TAG, "Feather collected");
	};
	
	private void testCollisions () 
	{
		r1.set(level.astronaut.position.x, level.astronaut.position.y,
				level.astronaut.bounds.width, level.astronaut.bounds.height);
		
		// Test collision: Bunny Head <-> Rocks
		for (Rock rock : level.rocks) 
		{
			r2.set(rock.position.x, rock.position.y, 
					rock.bounds.width, rock.bounds.height);
			
			if (!r1.overlaps(r2)) 
			{
				continue;
			}
		
			onCollisionBunnyHeadWithRock(rock);
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
			
			onCollisionBunnyWithGoldCoin(goldcoin);
			break;
		}
		// Test collision: Bunny Head <-> Feathers
		for (GreenHeart greenHeart : level.greenHearts) 
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
			onCollisionBunnyWithFeather(greenHeart);
			break;
		}
	}
	
	private void handleInputGame (float deltaTime) 
	{
		if (cameraHelper.hasTarget(level.astronaut)) 
		{
			// Player Movement
			if (Gdx.input.isKeyPressed(Keys.LEFT)) 
			{
				level.astronaut.velocity.x = -level.astronaut.terminalVelocity.x;
			} 
			else if (Gdx.input.isKeyPressed(Keys.RIGHT)) 
			{
				level.astronaut.velocity.x = level.astronaut.terminalVelocity.x;
			} 
			else 
			{
				// Execute auto-forward movement on non-desktop platform
				if (Gdx.app.getType() != ApplicationType.Desktop) 
				{
					level.astronaut.velocity.x = level.astronaut.terminalVelocity.x;
				}
			}
			
			// Bunny Jump
			if (Gdx.input.isTouched() || Gdx.input.isKeyPressed(Keys.SPACE)) 
			{
				level.astronaut.setJumping(true);
			} 
			else 
			{
				level.astronaut.setJumping(false);
			}
		}
	}
	
	public boolean isGameOver () 
	{
		return lives < 0;
	}
		
	public boolean isPlayerInWater () 
	{
		return level.astronaut.position.y < -5;
	}
	
	private void moveCamera (float x, float y) 
	{
		Gdx.app.log(TAG, "(" + cameraHelper.getPosition().x + ", " + cameraHelper.getPosition().y + ")");

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
	      cameraHelper.setTarget(cameraHelper.hasTarget() ? null: level.astronaut); 
	      Gdx.app.debug(TAG, "Camera follow enabled: " + cameraHelper.hasTarget()); 
	    } 
	
		return false;
	}
	

}
