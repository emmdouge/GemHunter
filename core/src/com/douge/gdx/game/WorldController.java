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
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.douge.gdx.game.assets.Assets;
import com.douge.gdx.game.collectible.Collectible;
import com.douge.gdx.game.collectible.Collectible;
import com.douge.gdx.game.collectible.LevelGem;
import com.douge.gdx.game.effect.NullEffect;
import com.douge.gdx.game.enemy.Enemy;
import com.douge.gdx.game.objects.Fireball;
import com.douge.gdx.game.objects.NullGameObject;
import com.douge.gdx.game.objects.Player;
import com.douge.gdx.game.objects.Platform;
import com.douge.gdx.game.objects.XMovingPlatform;
import com.douge.gdx.game.objects.YMovingPlatform;
import com.douge.gdx.game.screen.GameScreen;
import com.douge.gdx.game.screen.MenuScreen;
import com.douge.gdx.game.screen.transition.DirectedGame;
import com.douge.gdx.game.screen.transition.Fade;
import com.douge.gdx.game.screen.transition.ScreenTransition;
import com.douge.gdx.game.utils.AudioManager;

public class WorldController extends InputAdapter implements Disposable
{
	private static final String TAG = WorldController.class.getName();
	
	private DirectedGame game;
	
	public LevelLoader levelLoader;
	public int score;
	public float scoreVisual;
	public float livesVisual;
	public int fireballsVisual;
	
	public CameraHelper cameraHelper;
	
	private Rectangle r1 = new Rectangle(); 
	private Rectangle r2 = new Rectangle(); 
	   
	private float timeLeftGameOverDelay; 
	
	public Message message;

	public static World box2DWorld = null;
	
	/**
	 * initializes game and level
	 * @param game
	 */
	public WorldController(DirectedGame game)
	{
		this.game = game;
		levelLoader = LevelLoader.getInstance();
		init();
		initPhysics();
	}
	
	/**
	 * initializes level and camera helper
	 */
	private void init()
	{
		levelLoader.initCurrentLevel(levelLoader.currentLevelIndex);	
		Gdx.input.setInputProcessor(this);
		cameraHelper = new CameraHelper();
		cameraHelper.setTarget(levelLoader.player);
		cameraHelper.setPosition(levelLoader.redCoin.position.x, levelLoader.redCoin.position.y);
	    timeLeftGameOverDelay = 0; 
	    livesVisual = Constants.MAX_LIVES;
	    fireballsVisual = Constants.FIREBALLS_START;
		scoreVisual = 0;
		score = 0;
		message = levelLoader.currentLevel.messages.head;
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
		
		handleCollisions();
		
		if(box2DWorld != null)
		box2DWorld.step(deltaTime, 24, 3);
			
		cameraHelper.update(deltaTime);
		if (!isGameOver() && isPlayerInWater()) 
		{
		    AudioManager.instance.play(Assets.instance.sounds.liveLost); 
			levelLoader.player.numLives--;
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
		if (livesVisual> levelLoader.player.numLives)
		{
			livesVisual = Math.max(levelLoader.player.numLives, livesVisual-deltaTime);
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
	 * @param collectible
	 */
	private void onCollisionPlayerWithCollectible(Collectible collectible) 
	{
		if(collectible.effect instanceof NullEffect)
		{
			AudioManager.instance.play(Assets.instance.sounds.pickupCoin);
		}
		else
		{
			AudioManager.instance.play(Assets.instance.sounds.pickupGem);
		}
		collectible.collected = true;
		collectible.effect.activate(LevelLoader.getInstance().player);
		score += collectible.getScore();
		if(collectible instanceof LevelGem)
		{
			if(levelLoader.currentLevelIndex+1 < levelLoader.levels.size())
			{
				levelLoader.nextLevel();
				GameScreen gameScreen = new GameScreen(game);
				game.setScreen(gameScreen);
			}
			else
			{
				game.setScreen(new MenuScreen(game));			
			}
		}
		Gdx.app.log(TAG, "Collectible collected");
	};
	
	/**
	 * handles collisions between objects
	 */
	private void handleCollisions () 
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
		
		for(Enemy enemy: levelLoader.enemies)
		{
			r1.set(enemy.position.x + enemy.bounds.x, enemy.position.y + enemy.bounds.y, enemy.bounds.width, enemy.bounds.height);
			for(Fireball fireball: levelLoader.player.fireballs)
			{
				r2.set(fireball.position.x, fireball.position.y, fireball.bounds.width, fireball.bounds.height);
				if(r1.overlaps(r2) && !enemy.hasBeenKilled && !fireball.hitEnemy)
				{
					fireball.hitEnemy = true;
					fireball.stateTime = 0f;
					fireball.currentVelocity.x = fireball.currentVelocity.x < 0 ? -1 : 1;
					fireball.hit();
					enemy.stateTime = 0f;
					enemy.hasBeenKilled = true;
					enemy.context.setEnemyState(enemy.context.getDeadState());
				}
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
					platform.body.setLinearVelocity(platform.body.getLinearVelocity().x*-1, platform.body.getLinearVelocity().y*-1);
					float offset = platform.body.getLinearVelocity().x < 0 || platform.body.getLinearVelocity().y < 0? -.1f : .1f;
					platform.body.getPosition().x += offset;
					platform.body.getPosition().y += offset;
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
		for (Collectible collectible : levelLoader.collectibles) 
		{
			if (collectible.collected) 
			{
				continue;
			}
		
			r2.set(collectible.position.x, collectible.position.y,
					collectible.bounds.width, collectible.bounds.height);
			
			if (!r1.overlaps(r2)) 
			{
				continue;
			}
			
			onCollisionPlayerWithCollectible(collectible);
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
			boolean dashKeyPressed = Gdx.input.isKeyJustPressed(Keys.SHIFT_LEFT) || Gdx.input.justTouched();
			boolean attackKeyPressed = Gdx.input.isKeyJustPressed(Keys.F);
			boolean jumpKeyPressed = Gdx.input.isKeyPressed(Keys.SPACE);
			boolean crowAttackKeyPressed = Gdx.input.isKeyJustPressed(Keys.Q);
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
				levelLoader.player.context.setPlayerStateBasedOnInput(jumpKeyPressed, dashKeyPressed, attackKeyPressed);
			}
			else
			{
				levelLoader.player.context.setPlayerStateBasedOnInput(false, false, false);
			}
		}
	}	
	
	/**
	 * if player has no lives left
	 * @return
	 */
	public boolean isGameOver () 
	{
		return levelLoader.player.numLives <= 0;
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
			initPhysics();
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
	
	private void initPhysics () 
	{
		initBox2D();

		// Rocks
		for (Platform platform : levelLoader.platforms) 
		{
			BodyDef bodyDef = new BodyDef();
			bodyDef.position.set(platform.position);
			
			if(platform instanceof XMovingPlatform || platform instanceof YMovingPlatform)
			{
				bodyDef.type = BodyType.KinematicBody;
				bodyDef.position.set(platform.position);
				bodyDef.linearVelocity.set(platform.currentVelocity);
			}
			else
			{	
				bodyDef.type = BodyType.StaticBody;
			}	

			
			PolygonShape polygonShape = new PolygonShape();
			polygonShape.setAsBox(platform.origin.x, platform.origin.y, platform.origin, 0);
			
			FixtureDef fixtureDef = new FixtureDef();
			fixtureDef.shape = polygonShape;
			platform.body = box2DWorld.createBody(bodyDef);
			platform.body.createFixture(fixtureDef);
		}
	}

	@Override
	public void dispose() {
		if (box2DWorld != null) 
		{
			box2DWorld.dispose();
		}
	}

	public static void initBox2D() 
	{
		if (box2DWorld != null) 
		{
			box2DWorld.dispose();
		}

		box2DWorld = new World(new Vector2(0, -9.81f), true);
	}
}
