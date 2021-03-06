package com.douge.gdx.game.level;

import java.util.ArrayList;

import com.aurelienribon.bodyeditor.BodyEditorLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
import com.douge.gdx.game.Constants;
import com.douge.gdx.game.WorldController;
import com.douge.gdx.game.WorldRenderer;
import com.douge.gdx.game.assets.Assets;
import com.douge.gdx.game.effect.HealthBoostEffect;
import com.douge.gdx.game.effect.JumpBoostEffect;
import com.douge.gdx.game.message.Message;
import com.douge.gdx.game.message.MessageQueue;
import com.douge.gdx.game.message.NullMessage;
import com.douge.gdx.game.objects.AbstractGameObject;
import com.douge.gdx.game.objects.Crow;
import com.douge.gdx.game.objects.Fireball;
import com.douge.gdx.game.objects.NullGameObject;
import com.douge.gdx.game.objects.Player;
import com.douge.gdx.game.objects.backgroundtile.BackgroundRock;
import com.douge.gdx.game.objects.backgroundtile.BackgroundStar;
import com.douge.gdx.game.objects.backgroundtile.BackgroundTile;
import com.douge.gdx.game.objects.collectible.Collectible;
import com.douge.gdx.game.objects.collectible.GoldCoin;
import com.douge.gdx.game.objects.collectible.LevelGem;
import com.douge.gdx.game.objects.enemy.Bat;
import com.douge.gdx.game.objects.enemy.BigGoblin;
import com.douge.gdx.game.objects.enemy.Enemy;
import com.douge.gdx.game.objects.enemy.Skeleton;
import com.douge.gdx.game.objects.enemy.Slime;
import com.douge.gdx.game.objects.environment.BlackOverlay;
import com.douge.gdx.game.objects.environment.Clouds;
import com.douge.gdx.game.objects.environment.Trees;
import com.douge.gdx.game.objects.platform.Platform;
import com.douge.gdx.game.objects.platform.PlatformRock;
import com.douge.gdx.game.objects.platform.PlatformSnow;
import com.douge.gdx.game.objects.platform.XMovingPlatform;
import com.douge.gdx.game.objects.platform.YMovingPlatform;
import com.douge.gdx.game.screen.GameScreen;
import com.douge.gdx.game.screen.transition.DirectedGame;

/**
 * Level 1 of the game
 * @author Emmanuel
 *
 */
public class LevelLoader 
{
	public static final String TAG = LevelLoader.class.getName();

	private static LevelLoader instance;
	
	//objects
	public Player player; 
	public Crow crow;
	public Array<Collectible> collectibles;
	public LevelGem redCoin;
	public Array<Platform> platforms;
	public Array<NullGameObject> reversingBoxes;	
	public Array<BackgroundTile> backgroundTiles;
	public Array<Enemy> enemies;
	public ArrayList<Level> levels;
	public Level currentLevel;
	public int currentLevelIndex = 0;
	private Rectangle view = new Rectangle(0, 0, 0, 0);
	private Rectangle object = new Rectangle(0, 0, 0, 0);
	// decoration
	public Clouds clouds;
	public Trees trees;
	public BlackOverlay blackOverlay;
	
	private LevelLoader () 
	{

	}
	
	private void initLevels()
	{	    
		MessageQueue messages = new MessageQueue();
	    
		Level level1 = new Level(BLOCK_TYPE.STAR_BACK.getColor(), new PlatformSnow(), "levels/level01.png", messages, Assets.instance.snow);

		messages.enqueue(new NullMessage());
		
		messages = new MessageQueue();
		Level level2 = new Level(BLOCK_TYPE.ROCK_BACK.getColor(), new PlatformRock(), "levels/level02.png", messages, Assets.instance.forest);
		messages.enqueue(new Message("Press F to Attack!", new Vector2(0, 0), Assets.instance.survivor.survivor));
		messages.enqueue(new Message("Press Left Shift to Dash!", new Vector2(0, 0), Assets.instance.survivor.survivor));
		messages.enqueue(new NullMessage());
		
		levels.add(level1);
		levels.add(level2);
	}
	
	public static LevelLoader getInstance() 
	{
		if(instance == null)
		{
			instance = new LevelLoader();
			instance.collectibles = new Array<Collectible>(); 
			instance.platforms = new Array<Platform>();
			instance.reversingBoxes = new Array<NullGameObject>();
			instance.backgroundTiles = new Array<BackgroundTile>();
			instance.enemies = new Array<Enemy>();
			instance.levels = new ArrayList<Level>();
			instance.initLevels();
			instance.currentLevelIndex = 0;
			instance.currentLevel = instance.levels.get(instance.currentLevelIndex);
		}

		return instance;
	}
	
	public void nextLevel()
	{
		clearLevel();
		currentLevelIndex++;	
	}
	
	private void clearLevel() 
	{
		collectibles.clear();
		platforms.clear();
		reversingBoxes.clear();
		backgroundTiles.clear();
		enemies.clear();
		
	}

	public void initCurrentLevel(int currentLevelIndex) 
	{
		clearLevel();
		this.currentLevelIndex  = currentLevelIndex;
	    currentLevel = levels.get(currentLevelIndex);
		
		// load image file that represents the level data
		//**had to remove underscore in filename to get it to load
		Pixmap pixmap = new Pixmap(Gdx.files.internal(currentLevel.filepath));
		
		float yOffset = 1.5f;
		
		// scan pixels from top-left to bottom-right
		int lastPixel = -1;
		for (int pixelX = pixmap.getWidth(); pixelX >= 0; pixelX--) 
		{
			for (int pixelY = pixmap.getHeight(); pixelY >= 0; pixelY--) 
			{
				AbstractGameObject obj = null;
				
				//I want to set positions the way batch draws them -> starting from the bottom left
				//think of the pixels as starting from the top left in the level.png
				//so, the x doesn't have to be changed, but the y has to be adjusted to make it 
				//as if it starts at the bottom left
				float baseHeight = pixmap.getHeight() - pixelY - yOffset;
				
				//exponential offset
				float expOffset = -((pixelY+3f))*.025f;
				
				// get color of current pixel as 32-bit RGBA value
				int currentPixel = pixmap.getPixel(pixelX, pixelY);
				
				// find matching color value to identify block type at (x,y)
				// point and create the corresponding game object if there is a match
		
				if(BLOCK_TYPE.validColor(currentPixel))
				{
					// rock back
					if (BLOCK_TYPE.ROCK_BACK.sameColor(currentLevel.back)) 
					{
						obj = new BackgroundRock();
					}
					else if(BLOCK_TYPE.STAR_BACK.sameColor(currentLevel.back))
					{
						obj = new BackgroundStar();					
					}
					obj.position.set(pixelX, baseHeight);
					backgroundTiles.add((BackgroundTile)obj);
				}
				
				// rock
				if (BLOCK_TYPE.ROCK_PLATFORM.sameColor(currentPixel)) 
				{
					if (lastPixel != currentPixel) 
					{
						obj = new PlatformRock();
						obj.position.set(pixelX, baseHeight + expOffset);
						platforms.add((Platform)obj);
					} 
					else 
					{
						platforms.get(platforms.size - 1).increaseLength(1);
					}
				}
				
				else if (BLOCK_TYPE.SNOW_PLATFORM.sameColor(currentPixel)) 
				{
					if (lastPixel != currentPixel) 
					{
						obj = new PlatformSnow();
						obj.position.set(pixelX, baseHeight + expOffset);
						platforms.add((Platform)obj);
					} 
					else 
					{
						platforms.get(platforms.size - 1).increaseLength(1);
					}
				}
				
				else if(BLOCK_TYPE.X_MOVING_PLATFORM.sameColor(currentPixel)) 
				{
					if (lastPixel != currentPixel) 
					{
						obj = new XMovingPlatform();
						Platform platform = (Platform)obj;
						platform.regMiddle = currentLevel.platform.regMiddle;
						platform.position.set(pixelX, baseHeight + expOffset);
						platform.currentVelocity.x = 1.75f;
						platforms.add(platform);
					} 
					else 
					{
						platforms.get(platforms.size - 1).increaseLength(1);
					}
				}
				
				else if(BLOCK_TYPE.Y_MOVING_PLATFORM.sameColor(currentPixel)) 
				{
					if (lastPixel != currentPixel) 
					{
						obj = new YMovingPlatform();
						Platform platform = (Platform)obj;
						platform.regMiddle = currentLevel.platform.regMiddle;
						platform.position.set(pixelX, baseHeight + expOffset);
						platform.maxVelocity.y = 10f;
						platform.currentVelocity.y = -1.75f;
						platforms.add(platform);
					} 
					else 
					{
						platforms.get(platforms.size - 1).increaseLength(1);
					}
				}
				
				// reversing box
				else if (BLOCK_TYPE.REVERSE_PLATFORM_VELOCITY.sameColor(currentPixel)) 
				{
			          obj = new NullGameObject(); 
			          obj.position.set(pixelX + obj.origin.x, baseHeight + expOffset); 
			          reversingBoxes.add((NullGameObject)obj); 
				}
				
				// player spawn point
				else if (BLOCK_TYPE.PLAYER_SPAWNPOINT.sameColor(currentPixel)) 
				{
			          obj = new Player(); 
			          obj.position.set(pixelX, baseHeight + expOffset); 
			          player = (Player)obj; 
			          crow = new Crow(player);
				}
				
				// jump gem
				else if (BLOCK_TYPE.ITEM_JUMP_GEM.sameColor(currentPixel)) 
				{
			          obj = new Collectible(Assets.instance.gems.jumpGem, new JumpBoostEffect()); 
			          obj.position.set(pixelX, baseHeight + expOffset); 
			          collectibles.add((Collectible)obj); 
				}
		
				// gold coin
				else if (BLOCK_TYPE.ITEM_GOLD_COIN.sameColor(currentPixel)) 
				{
			          obj = new GoldCoin(); 
			          obj.position.set(pixelX + obj.origin.x,baseHeight + expOffset); 
			          collectibles.add((Collectible)obj); 
				}
				
				// gold coin
				else if (BLOCK_TYPE.ITEM_LEVEL_GEM.sameColor(currentPixel)) 
				{
			          obj = new LevelGem(); 
			          obj.position.set(pixelX + obj.origin.x,baseHeight + expOffset); 
			          collectibles.add((LevelGem)obj);
			          redCoin = (LevelGem)obj;
				}
				
				// slime
				else if(BLOCK_TYPE.ENEMY_SLIME.sameColor(currentPixel))
				{
			          obj = new Slime(Assets.instance.slime); 
			          obj.position.set(pixelX,baseHeight + expOffset); 
			          enemies.add((Slime)obj); 	
				}
				
				// skeleton
				else if(BLOCK_TYPE.ENEMY_SKELETON.sameColor(currentPixel))
				{
			          obj = new Skeleton(Assets.instance.skeleton); 
			          obj.position.set(pixelX, baseHeight + expOffset); 
			          enemies.add((Skeleton)obj); 	
				}
				
				// bat
				else if(BLOCK_TYPE.ENEMY_BAT.sameColor(currentPixel))
				{
			          obj = new Bat(Assets.instance.bat); 
			          obj.position.set(pixelX, baseHeight + expOffset);
			          enemies.add((Bat)obj); 	
				}
				
				// bat
				else if(BLOCK_TYPE.ENEMY_BIGGOBLIN.sameColor(currentPixel))
				{
			          obj = new BigGoblin(Assets.instance.goblin); 
			          obj.position.set(pixelX, baseHeight + expOffset);
			          enemies.add((BigGoblin)obj); 	
				}
				
				// unknown object/pixel color
				else 
				{
					int r = 0xff & (currentPixel >>> 24); //red color channel
					int g = 0xff & (currentPixel >>> 16); //green color channel
					int b = 0xff & (currentPixel >>> 8); //blue color channel
					int a = 0xff & currentPixel; //alpha channel
					Gdx.app.error(TAG, "Unknown object at " + 
						"x<" + pixelX + 
						"> y<" + pixelY + 
						">: r<" + r+ 
						"> g<" + g + 
						"> b<" + b + 
						"> a<" + a + ">");
				}
			lastPixel = currentPixel;
			}
		}
		//exponential offset
		float expOffsetEnv = -((pixmap.getHeight()+3f))*.025f;
		
		// decoration
		clouds = new Clouds(pixmap.getWidth());
		clouds.position.set(0, 2);

		int baseHeightOfBottomLeftPixelOfPixmap = 1;
		
		int lookGood = 1;
		trees = new Trees(pixmap.getWidth());
		trees.position.set(0, baseHeightOfBottomLeftPixelOfPixmap - yOffset + expOffsetEnv + lookGood);
		
		lookGood = -2;
		blackOverlay = new BlackOverlay(pixmap.getWidth());
		blackOverlay.position.set(0, baseHeightOfBottomLeftPixelOfPixmap - yOffset + expOffsetEnv + lookGood);
		
		// free memory
		pixmap.dispose();
		Gdx.app.debug(TAG, "level '" + currentLevel.filepath + "' loaded");
	}
	
	public void update (float deltaTime) 
	{
		player.update(deltaTime);
		
		for(Platform rock : platforms)
		rock.update(deltaTime);
		
		for(Collectible collectible : collectibles)
		collectible.update(deltaTime);
		
		for(int i = 0; i < enemies.size; i++)
		enemies.get(i).update(deltaTime);
		
		for(int i = 0; i < player.fireballs.size; i++)
		player.fireballs.get(i).update(deltaTime);
		
		int enemyIndex = 0;
		for(Enemy enemy: enemies)
		{
			if(enemy.isDead)
			{
				Collectible coin;
				FixtureDef coinFixtureDef = new FixtureDef();
				if(enemy.isBoss)
				{
					coin = new LevelGem();
					coinFixtureDef.density = 0;
					coinFixtureDef.restitution = 0.15f;
					coinFixtureDef.friction = 4f;
				}
				else
				{
					coin = new GoldCoin();
					coinFixtureDef.density = 0;
					coinFixtureDef.restitution = 0.75f;
					coinFixtureDef.friction = 0.5f;
				}
				
				BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("physics/coin.json"));

				BodyDef coinBodyDef = new BodyDef();	
				coinBodyDef.type = BodyType.DynamicBody;
				coinBodyDef.linearVelocity.x = MathUtils.random(-2, 2);
				coinBodyDef.linearVelocity.y = MathUtils.random(4, 7);
				coinBodyDef.position.set(enemy.position);

				coin.body = WorldController.box2DWorld.createBody(coinBodyDef);
				//must name it in body editor or in json(manually)
				coin.origin = loader.getOrigin("coinBody", coin.dimension.x);
				loader.attachFixture(coin.body, "coinBody", coinFixtureDef, coin.origin.x);
				collectibles.add(coin);
				
				if(enemy.dropsHealth && !enemy.droppedHealth)	
				{
					Collectible healthGem = new Collectible(Assets.instance.gems.heartGem, new HealthBoostEffect());
					healthGem.position.x = enemy.position.x + (enemy.origin.x/2);
					healthGem.position.y = enemy.position.y;
					loader = new BodyEditorLoader(Gdx.files.internal("physics/heart.json"));
					
					FixtureDef heartFixtureDef = new FixtureDef();
					heartFixtureDef.density = 25;
					heartFixtureDef.restitution = 0.75f;
					heartFixtureDef.friction = 0.5f;

					BodyDef heartBodyDef = new BodyDef();	
					heartBodyDef.type = BodyType.DynamicBody;
					heartBodyDef.linearVelocity.x = MathUtils.random(-2, 2);
					heartBodyDef.linearVelocity.y = MathUtils.random(4, 7);
					heartBodyDef.position.set(healthGem.position);

					healthGem.body = WorldController.box2DWorld.createBody(heartBodyDef);
					//must manually define the name in the json file 
					healthGem.origin = loader.getOrigin("heartBody", healthGem.dimension.x);
					loader.attachFixture(healthGem.body, "heartBody", heartFixtureDef, healthGem.origin.x);
					collectibles.add(healthGem);
					enemy.droppedHealth = true;
				}


			}
			enemyIndex++;
		}
		
		enemyIndex = 0;
		for(Enemy enemy: enemies)
		{
			if(enemy.isDead && enemy.currentAnimation.isAnimationFinished(enemy.stateTime))
			{
				enemies.removeIndex(enemyIndex);
			}
			enemyIndex++;
		}
		

		int fireballIndex = 0;
		for(Fireball fireball: player.fireballs)
		{  
			boolean farAway = Math.abs(player.position.x - fireball.position.x) >= 10f;
			if((fireball.hitEnemy && fireball.currentAnimation.isAnimationFinished(fireball.stateTime)) || farAway)
			{
				player.fireballs.removeIndex(fireballIndex);
			}
			fireballIndex++;
		}
		
		//clouds.update(deltaTime);
		crow.update(deltaTime);
		
		//currentLevel.particleEffect.update(deltaTime);
	}
	
	/**
	 * Draws the level
	 * @param batch the batch from the worldRenderer
	 */
	public void render (SpriteBatch batch) 
	{
		if(WorldRenderer.camera != null)
		{
			float x = WorldRenderer.camera.position.x - WorldRenderer.camera.viewportWidth/2;
			float y = WorldRenderer.camera.position.y - WorldRenderer.camera.viewportHeight/2 - 1;
			view.set(x, y, Constants.VIEWPORT_WIDTH+3, Constants.VIEWPORT_HEIGHT+2);
			int numBackTilesRendered = 0;
			for(BackgroundTile backTile: backgroundTiles)
			{
				object.set(backTile.position.x, backTile.position.y, backTile.dimension.x, backTile.dimension.y);
				if(view.overlaps(object))
				{
					backTile.render(batch);
					numBackTilesRendered++;
				}
			}
			System.out.println(numBackTilesRendered);
			
			// Draw trees
			trees.render(batch);
			
			// Draw Gold Coins 
			for (Collectible collectible : collectibles) 
			{
				object.set(collectible.position.x, collectible.position.y, collectible.dimension.x, collectible.dimension.y);
				if(view.overlaps(object))
					collectible.render(batch); 
			}    
		   batch.setColor(Color.WHITE);
			
		   object.set(blackOverlay.position.x, blackOverlay.position.y, blackOverlay.dimension.x, blackOverlay.dimension.y);
			if(view.overlaps(object))
		   blackOverlay.render(batch);
				
		   // Draw Rocks
		   for (Platform platform : platforms)
		   {
			   object.set(platform.position.x, platform.position.y, platform.dimension.x, platform.dimension.y);
				if(view.overlaps(object))
					platform.render(batch);
		   }	
		   for(int fireballIndex = 0; fireballIndex < player.fireballs.size; fireballIndex++)
		   {
			   Fireball fireball = player.fireballs.get(fireballIndex);
			   fireball.render(batch);
		   }
				
		   for(int enemyIndex = 0; enemyIndex < enemies.size; enemyIndex++)
			{
				Enemy enemy = enemies.get(enemyIndex);
				object.set(enemy.position.x, enemy.position.y, enemy.dimension.x, enemy.dimension.y);
				if(view.overlaps(object))
				enemy.render(batch);
			}
				
				crow.render(batch);
				
				//currentLevel.particleEffect.start();
				//currentLevel.particleEffect.draw(batch);
		
				batch.setColor(Color.WHITE);
				
				// Draw Player Character 
			    player.render(batch);
		}				
	}


}
