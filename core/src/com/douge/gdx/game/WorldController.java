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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.douge.gdx.game.assets.Assets;
import com.douge.gdx.game.enemy.Enemy;
import com.douge.gdx.game.objects.NullGameObject;
import com.douge.gdx.game.objects.Player;
import com.douge.gdx.game.objects.Coin;
import com.douge.gdx.game.objects.JumpPotion;
import com.douge.gdx.game.objects.Platform;
import com.douge.gdx.game.objects.RedCoin;
import com.douge.gdx.game.screen.GameScreen;
import com.douge.gdx.game.screen.MenuScreen;
import com.douge.gdx.game.screen.transition.DirectedGame;
import com.douge.gdx.game.screen.transition.Fade;
import com.douge.gdx.game.screen.transition.ScreenTransition;
import com.douge.gdx.game.utils.AudioManager;

public class WorldController extends InputAdapter
{
	private static final String TAG = WorldController.class.getName();
	
	private DirectedGame game;
	
	public LevelLoader levelLoader;
	public int score;
	public float scoreVisual;
	public float livesVisual;
	
	public CameraHelper cameraHelper;
	
	private Rectangle r1 = new Rectangle(); 
	private Rectangle r2 = new Rectangle(); 
	   
	private float timeLeftGameOverDelay; 
	

	public Message message;
	
	/**
	 * initializes game and level
	 * @param game
	 */
	public WorldController(DirectedGame game)
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
		score = 0;
		scoreVisual = 0;
		initLevelLoader();
		message = levelLoader.currentLevel.messages.head;
	}
	
	/**
	 * initializes level and sets cameraHelper's target to the player
	 */
	private void initLevelLoader () 
	{
		levelLoader = LevelLoader.instance.init(game);
		cameraHelper.setTarget(levelLoader.player);
	}
	

	/**
	 * handles input, updates, and collisions
	 * @param deltaTime time elapsed since libgdx's last render call
	 */
	public void update(float deltaTime)
	{
		//Gdx.app.log(TAG, "player: " + level.player.context.getCurrentState());
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
		
		levelLoader.update(deltaTime);
		
		message.updateText(deltaTime, levelLoader.player);
		
		testCollisions();
		cameraHelper.update(deltaTime);
		if (!isGameOver() && isPlayerInWater()) 
		{
		    AudioManager.instance.play(Assets.instance.sounds.liveLost); 
			levelLoader.player.lives--;
			if (isGameOver())
			{
				timeLeftGameOverDelay = Constants.TIME_DELAY_GAME_OVER;
			}
			else
			{
				game.setScreen(new GameScreen(game));
			}
		}
		levelLoader.trees.updateScrollPosition(cameraHelper.getPosition());
		if (livesVisual> levelLoader.player.lives)
		{
			livesVisual = Math.max(levelLoader.player.lives, livesVisual - 1 * deltaTime);
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
		if (!cameraHelper.hasTarget(levelLoader.player));
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
	 * @param coin
	 */
	private void onCollisionPlayerWithCoin(Coin coin) 
	{
		AudioManager.instance.play(Assets.instance.sounds.pickupCoin); 
		coin.collected = true;
		score += coin.getScore();
		if(coin instanceof RedCoin)
		{
			levelLoader.nextLevel();
			cameraHelper.setTarget(levelLoader.player);
		}
		Gdx.app.log(TAG, "Gold coin collected");
	};
	
	/**
	 * turns on players jump powerup
	 * @param jumpPotion
	 */
	private void onCollisionPlayerWithJumpPotion(JumpPotion jumpPotion) 
	{
		AudioManager.instance.play(Assets.instance.sounds.pickupPotion);
		jumpPotion.collected = true;
		score += jumpPotion.getScore();
		levelLoader.player.setJumpPowerup(true);
		Gdx.app.log(TAG, "Jump Potion collected");
	};
	
	/**
	 * handles collisions between objects
	 */
	private void testCollisions () 
	{
		// Test collision: enemies <-> rocks
		boolean collided = false;
		for(Enemy enemy: levelLoader.enemies)
		{
			r1.set(enemy.position.x + enemy.bounds.x, enemy.position.y + enemy.bounds.y, enemy.bounds.width, enemy.bounds.height);
			collided = false;
			for(Platform rock: levelLoader.platforms)
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
		
		// Test collision: platforms <-> reversingBoxes
		for(Platform platform: levelLoader.platforms)
		{
			r1.set(platform.position.x + platform.bounds.x, platform.position.y + platform.bounds.y, platform.bounds.width, platform.bounds.height);
			for(NullGameObject reversingBox: levelLoader.reversingBoxes)
			{
				r2.set(reversingBox.position.x, reversingBox.position.y, reversingBox.bounds.width, reversingBox.bounds.height);
				if(r1.overlaps(r2))
				{
					platform.currentVelocity.x *= -1;
					platform.currentVelocity.y *= -1;
				}
			}
		}
		
		r1.set(levelLoader.player.position.x, levelLoader.player.position.y,
				levelLoader.player.bounds.width, levelLoader.player.bounds.height);
		
		// Test collision: Player <-> Rocks
		for (Platform rock : levelLoader.platforms) 
		{
			r2.set(rock.position.x, rock.position.y, 
					rock.bounds.width, rock.bounds.height);
			
			if (!r1.overlaps(r2)) 
			{
				//System.out.println("not overlapping rock");
				levelLoader.player.context.getCurrentState().noPlatformCollision();
				continue;
			}
		
			levelLoader.player.context.setStateBasedOnCollisionWithPlatform(rock);
			break;
		}
		
		// Test collision: player <-> enemies
		for (Enemy enemy : levelLoader.enemies) 
		{
			r2.set(enemy.position.x + enemy.bounds.x, enemy.position.y + enemy.bounds.y, enemy.bounds.width, enemy.bounds.height);
			if (r1.overlaps(r2) && !message.shouldBeRendered) 
			{
				levelLoader.player.context.onCollisionWith(enemy);
			}
		}
		
		// Test collision: player <-> Gold Coins
		for (Coin goldcoin : levelLoader.coins) 
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
			
			onCollisionPlayerWithCoin(goldcoin);
			break;
		}
		
		// Test collision: Bunny Head <-> Feathers
		for (JumpPotion greenHeart : levelLoader.jumpPotion) 
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
		if (cameraHelper.hasTarget(levelLoader.player) && !levelLoader.player.isStunned) 
		{
			// Execute auto-forward movement on non-desktop platform
			if (Gdx.app.getType() != ApplicationType.Desktop) 
			{
				levelLoader.player.currentVelocity.x = levelLoader.player.maxVelocity.x;
			}
			message = levelLoader.currentLevel.messages.head;
			// Bunny Jump
			boolean dashKeyPressed = Gdx.input.isKeyJustPressed(Keys.SHIFT_LEFT);
			boolean jumpKeyPressed = Gdx.input.justTouched() || Gdx.input.isKeyPressed(Keys.SPACE);
			if(message.textIsRendered && message.shouldBeRendered)
			{
				if(!message.playerSkipped && (jumpKeyPressed || dashKeyPressed))
				{
					message.playerSkipped = true;
					levelLoader.currentLevel.messages.dequeue();
				}
			}
			else if(!message.shouldBeRendered)
			{
				// Player Movement
				if (Gdx.input.isKeyPressed(Keys.LEFT)) 
				{
					levelLoader.player.currentVelocity.x = -levelLoader.player.maxVelocity.x;
					levelLoader.player.activeMovement = true;
				} 
				else if (Gdx.input.isKeyPressed(Keys.RIGHT)) 
				{
					levelLoader.player.currentVelocity.x = levelLoader.player.maxVelocity.x;
					levelLoader.player.activeMovement = true;
				} 
				levelLoader.player.context.setPlayerStateBasedOnInput(jumpKeyPressed, dashKeyPressed);
			}
			else
			{
				levelLoader.player.context.setPlayerStateBasedOnInput(false, false);
			}
		}
	}	
	
	/**
	 * if player has no lives left
	 * @return
	 */
	public boolean isGameOver () 
	{
		return levelLoader.player.lives <= 0;
	}
		
	/**
	 * If player fell to their doom
	 * @return
	 */
	public boolean isPlayerInWater () 
	{
		return levelLoader.player.position.y < -5;
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
	      cameraHelper.setTarget(cameraHelper.hasTarget() ? null: levelLoader.player); 
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
		ScreenTransition fade = Fade.init(0.75f);
		game.setScreen(new GameScreen(game), fade);
	}
}
