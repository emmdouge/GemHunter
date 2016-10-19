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
import com.douge.gdx.game.enemy.Enemy;
import com.douge.gdx.game.objects.Player;
import com.douge.gdx.game.objects.GoldCoin;
import com.douge.gdx.game.objects.JumpPotion;
import com.douge.gdx.game.objects.Rock;
import com.douge.gdx.game.screens.MenuScreen;

public class WorldController extends InputAdapter
{
	private static final String TAG = WorldController.class.getName();
	
	private Game game;
	
	public Level level;
	public int score;
	public float scoreVisual;
	public float livesVisual;
	
	public CameraHelper cameraHelper;
	
	private Rectangle r1 = new Rectangle(); 
	private Rectangle r2 = new Rectangle(); 
	   
	private float timeLeftGameOverDelay; 
	
	/**
	 * initializes game and level
	 * @param game
	 */
	public WorldController(Game game)
	{
		this.game = game;
		init();
	}
	
	/**
	 * initializes level and camera helper
	 */
	private void init()
	{
		Gdx.input.setInputProcessor(this);
		cameraHelper = new CameraHelper();
	    timeLeftGameOverDelay = 0; 
	    livesVisual = Constants.LIVES_START;
		initLevel();
	}
	
	/**
	 * initializes level and sets cameraHelper's target to the player
	 */
	private void initLevel () 
	{
		score = 0;
		scoreVisual = 0;
		level = new Level(Constants.LEVEL_01_PATH);
		cameraHelper.setTarget(level.player); 
	}
	

	/**
	 * handles input, updates, and collisions
	 * @param deltaTime time elapsed since libgdx's last render call
	 */
	public void update(float deltaTime)
	{
		Gdx.app.log(TAG, "player: " + level.player.context.getCurrentState());
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
			level.player.lives--;
			if (isGameOver())
			{
				timeLeftGameOverDelay = Constants.TIME_DELAY_GAME_OVER;
			}
			else
			{
				initLevel();
			}
		}
		level.trees.updateScrollPosition(cameraHelper.getPosition());
		if (livesVisual> level.player.lives)
		{
			livesVisual = Math.max(level.player.lives, livesVisual - 1 * deltaTime);
		}
		if (scoreVisual< score)
		{
			scoreVisual = Math.min(score, scoreVisual + 250 * deltaTime);
		}
		
	}

	/**
	 * handles debug input
	 * @param deltaTime
	 */
	private void handleDebugInput(float deltaTime) 
	{
		if (Gdx.app.getType() != ApplicationType.Desktop) return;

		// Camera Controls (move)
		if (!cameraHelper.hasTarget(level.player));
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
	
	/**
	 * When player collects goldcoin, 
	 * stop rendering it and update score
	 * @param goldcoin
	 */
	private void onCollisionPlayerWithGoldCoin(GoldCoin goldcoin) 
	{
		goldcoin.collected = true;
		score += goldcoin.getScore();
		Gdx.app.log(TAG, "Gold coin collected");
	};
	
	/**
	 * turns on players jump powerup
	 * @param jumpPotion
	 */
	private void onCollisionPlayerWithJumpPotion(JumpPotion jumpPotion) 
	{
		jumpPotion.collected = true;
		score += jumpPotion.getScore();
		level.player.setJumpPowerup(true);
		Gdx.app.log(TAG, "Jump Potion collected");
	};
	
	/**
	 * handles collisions between objects
	 */
	private void testCollisions () 
	{
		r1.set(level.player.position.x, level.player.position.y,
				level.player.bounds.width, level.player.bounds.height);
		
		// Test collision: Player <-> Rocks
		for (Rock rock : level.rocks) 
		{
			r2.set(rock.position.x, rock.position.y, 
					rock.bounds.width, rock.bounds.height);
			
			if (!r1.overlaps(r2)) 
			{
				//System.out.println("not overlapping rock");
				level.player.context.getCurrentState().noRockCollision();
				continue;
			}
		
			level.player.context.setStateBasedOnCollisionWithRock(rock);
			break;
		}
	
		// Test collision: player <-> enemies
		for (Enemy enemy : level.enemies) 
		{
			r2.set(enemy.position.x, enemy.position.y, enemy.bounds.width, enemy.bounds.height);
			if (r1.overlaps(r2)) 
			{
				level.player.context.onCollisionWith(enemy);
			}
		}
		
		// Test collision: enemies <-> rocks
		boolean collided = false;
		for(Enemy enemy: level.enemies)
		{
			r1.set(enemy.position.x, enemy.position.y, enemy.bounds.width, enemy.bounds.height);
			collided = false;
			for(Rock rock: level.rocks)
			{
				r2.set(rock.position.x, rock.position.y, rock.bounds.width, rock.bounds.height);
				if(r1.overlaps(r2))
				{
					enemy.context.getCurrentState().onCollisionWith(rock);
					collided = true;
				}
			}
			if(collided == false)
			{
				enemy.context.getCurrentState().noRockCollision();
			}
		}
		
		r1.set(level.player.position.x, level.player.position.y,
				level.player.bounds.width, level.player.bounds.height);
		
		// Test collision: player <-> Gold Coins
		for (GoldCoin goldcoin : level.goldCoins) 
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
			
			onCollisionPlayerWithGoldCoin(goldcoin);
			break;
		}
		
		// Test collision: Bunny Head <-> Feathers
		for (JumpPotion greenHeart : level.jumpPotion) 
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
			onCollisionPlayerWithJumpPotion(greenHeart);
			break;
		}
	}
	
	/**
	 * handles input from the player
	 * @param deltaTime
	 */
	private void handleInputGame (float deltaTime) 
	{
		if (cameraHelper.hasTarget(level.player) && !level.player.isStunned) 
		{
			// Player Movement
			if (Gdx.input.isKeyPressed(Keys.LEFT)) 
			{
				level.player.currentVelocity.x = -level.player.maxVelocity.x;
			} 
			else if (Gdx.input.isKeyPressed(Keys.RIGHT)) 
			{
				level.player.currentVelocity.x = level.player.maxVelocity.x;
			} 
			else 
			{
				// Execute auto-forward movement on non-desktop platform
				if (Gdx.app.getType() != ApplicationType.Desktop) 
				{
					level.player.currentVelocity.x = level.player.maxVelocity.x;
				}
			}
			
			// Bunny Jump
			boolean dashKeyPressed = Gdx.input.isKeyJustPressed(Keys.SHIFT_LEFT);
			boolean jumpKeyPressed = Gdx.input.justTouched() || Gdx.input.isKeyPressed(Keys.SPACE);
			level.player.context.setPlayerStateBasedOnInput(jumpKeyPressed, dashKeyPressed);

		}
	}
	
	/**
	 * if player has no lives left
	 * @return
	 */
	public boolean isGameOver () 
	{
		return level.player.lives <= 0;
	}
		
	/**
	 * If player fell to their doom
	 * @return
	 */
	public boolean isPlayerInWater () 
	{
		return level.player.position.y < -5;
	}
	
	/**
	 * move camera
	 * @param x horizontal movement
	 * @param y veritcal movement
	 */
	private void moveCamera (float x, float y) 
	{
		//Gdx.app.log(TAG, "(" + cameraHelper.getPosition().x + ", " + cameraHelper.getPosition().y + ")");

		x += cameraHelper.getPosition().x;
		y += cameraHelper.getPosition().y;
		
		cameraHelper.setPosition(x, y);
	}

	/**
	 * when key is released
	 */
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
	      cameraHelper.setTarget(cameraHelper.hasTarget() ? null: level.player); 
	      Gdx.app.debug(TAG, "Camera follow enabled: " + cameraHelper.hasTarget()); 
	    } 
	
		// Back to Menu
		else if (keycode == Keys.ESCAPE || keycode == Keys.BACK)
		{
			backToMenu();
		}
		
		return false;
	}
	
	/**
	 * set game's screen to menu
	 */
	private void backToMenu () 
	{
		// switch to menu screen
		game.setScreen(new MenuScreen(game));
	}
}
