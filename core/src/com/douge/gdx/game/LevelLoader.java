package com.douge.gdx.game;

import java.util.ArrayList;

import com.aurelienribon.bodyeditor.BodyEditorLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
import com.douge.gdx.game.assets.Assets;
import com.douge.gdx.game.effect.HealthBoostEffect;
import com.douge.gdx.game.effect.JumpBoostEffect;
import com.douge.gdx.game.enemy.Bat;
import com.douge.gdx.game.enemy.Enemy;
import com.douge.gdx.game.enemy.BigGoblin;
import com.douge.gdx.game.enemy.Skeleton;
import com.douge.gdx.game.enemy.Slime;
import com.douge.gdx.game.objects.AbstractGameObject;
import com.douge.gdx.game.objects.BackgroundRock;
import com.douge.gdx.game.objects.BackgroundStar;
import com.douge.gdx.game.objects.Crow;
import com.douge.gdx.game.objects.Fireball;
import com.douge.gdx.game.objects.NullGameObject;
import com.douge.gdx.game.objects.PlatformSnow;
import com.douge.gdx.game.objects.Player;
import com.douge.gdx.game.objects.Clouds;
import com.douge.gdx.game.objects.Coin;
import com.douge.gdx.game.objects.Gem;
import com.douge.gdx.game.objects.BackgroundTile;
import com.douge.gdx.game.objects.PlatformRock;
import com.douge.gdx.game.objects.RedCoin;
import com.douge.gdx.game.objects.Trees;
import com.douge.gdx.game.objects.Platform;
import com.douge.gdx.game.objects.BlackOverlay;
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

	public static LevelLoader instance = new LevelLoader();
	
	//objects
	public Player player; 
	public Crow crow;
	public Array<Coin> coins;
	public RedCoin redCoin;
	public Array<Gem> gems; 
	public Array<Platform> platforms;
	public Array<NullGameObject> reversingBoxes;	
	public Array<BackgroundTile> backgroundTiles;
	public Array<Enemy> enemies;
	public ArrayList<Level> levels;
	public Level currentLevel;
	public int currentLevelIndex = 0;
	
	// decoration
	public Clouds clouds;
	public Trees trees;
	public BlackOverlay blackOverlay;

	public DirectedGame game;
	
	private LevelLoader () 
	{

	}
	
	public LevelLoader init(DirectedGame game)
	{
		instance.game = game;
		instance.initLevels();
		instance.initCurrentLevel(instance.currentLevelIndex);	
		return instance;
	}
	
	private void initLevels()
	{	    
		MessageQueue messages = new MessageQueue();
	    levels = new ArrayList<Level>();
	    
		Level level1 = new Level(BLOCK_TYPE.STAR_BACK.getColor(), new PlatformSnow(), "../core/assets/levels/level01.png", messages, Assets.instance.snow);

		messages.enqueue(new NullMessage());
		
		messages = new MessageQueue();
		Level level2 = new Level(BLOCK_TYPE.ROCK_BACK.getColor(), new PlatformRock(), "../core/assets/levels/level02.png", messages, Assets.instance.forest);
		messages.enqueue(new Message("Press F to Attack!", new Vector2(0, 0), Assets.instance.survivor.survivor));
		messages.enqueue(new Message("Press Left Shift to Dash!", new Vector2(0, 0), Assets.instance.survivor.survivor));
		messages.enqueue(new NullMessage());
		
		levels.add(level1);
		levels.add(level2);
	}
	
	public void nextLevel()
	{
		currentLevelIndex++;
		initCurrentLevel(currentLevelIndex);	
	}
	
	private void initCurrentLevel(int currentLevelIndex) 
	{
		this.currentLevelIndex  = currentLevelIndex;
	    currentLevel = levels.get(currentLevelIndex);
	    
	    // objects 
	    coins = new Array<Coin>(); 
	    gems = new Array<Gem>(); 
		platforms = new Array<Platform>();
		reversingBoxes = new Array<NullGameObject>();
		backgroundTiles = new Array<BackgroundTile>();
		enemies = new Array<Enemy>();
		
		// load image file that represents the level data
		//**had to remove underscore in filename to get it to load
		Pixmap pixmap = new Pixmap(Gdx.files.internal(currentLevel.filepath));
		
		float yOffset = 1.5f;
		
		// scan pixels from top-left to bottom-right
		int lastPixel = -1;
		for (int pixelY = 0; pixelY < pixmap.getHeight(); pixelY++) 
		{
			for (int pixelX = 0; pixelX < pixmap.getWidth(); pixelX++) 
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
						obj = new Platform();
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
						obj = new Platform();
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
			          obj = new Gem(Assets.instance.gems.jumpGem, new JumpBoostEffect()); 
			          obj.position.set(pixelX, baseHeight + expOffset); 
			          gems.add((Gem)obj); 
				}
		
				// gold coin
				else if (BLOCK_TYPE.ITEM_GOLD_COIN.sameColor(currentPixel)) 
				{
			          obj = new Coin(); 
			          obj.position.set(pixelX + obj.origin.x,baseHeight + expOffset); 
			          coins.add((Coin)obj); 
				}
				
				// gold coin
				else if (BLOCK_TYPE.ITEM_RED_COIN.sameColor(currentPixel)) 
				{
			          obj = new RedCoin(); 
			          obj.position.set(pixelX + obj.origin.x,baseHeight + expOffset); 
			          coins.add((RedCoin)obj);
			          redCoin = (RedCoin)obj;
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
		
		for(Coin goldCoin : coins)
		goldCoin.update(deltaTime);
		
		for(Gem greenHeart : gems)
		greenHeart.update(deltaTime);
		
		for(int i = 0; i < enemies.size; i++)
		enemies.get(i).update(deltaTime);
		
		for(int i = 0; i < player.fireballs.size; i++)
		player.fireballs.get(i).update(deltaTime);
		
		int enemyIndex = 0;
		for(Enemy enemy: enemies)
		{
			if(enemy.isDead)
			{
				Coin coin;
				if(enemy.isBoss)
				{
					coin = new RedCoin();
				}
				else
				{
					coin = new Coin();
				}
				
				BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("../core/assets/physics/coin.json"));
				
				FixtureDef fixtureDef = new FixtureDef();
				fixtureDef.density = 100;
				fixtureDef.restitution = 0.3f;
				fixtureDef.friction = 0.5f;

				BodyDef bodyDef = new BodyDef();	
				bodyDef.type = BodyType.DynamicBody;
				bodyDef.linearVelocity.x = MathUtils.random(-3, 3);
				bodyDef.linearVelocity.y = MathUtils.random(1, 4);
				bodyDef.position.set(enemy.position);

				coin.body = WorldController.box2DWorld.createBody(bodyDef);
				//must manually define the name in the json file 
				coin.origin = loader.getOrigin("coinBody", coin.dimension.x);
				loader.attachFixture(coin.body, "coinBody", fixtureDef, coin.origin.x);
				coins.add(coin);
				
				if(enemy.dropsHealth && !enemy.droppedHealth)	
				{
					Gem healthGem = new Gem(Assets.instance.gems.heartGem, new HealthBoostEffect());
					healthGem.position.x = enemy.position.x + (enemy.origin.x/2);
					healthGem.position.y = enemy.position.y;
					gems.add(healthGem);
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
		
		clouds.update(deltaTime);
		crow.update(deltaTime);
		
		currentLevel.particleEffect.update(deltaTime);
	}
	
	/**
	 * Draws the level
	 * @param batch the batch from the worldRenderer
	 */
	public void render (SpriteBatch batch) 
	{
		for(BackgroundTile backTile: backgroundTiles)
		backTile.render(batch);
		
		// Draw trees
		trees.render(batch);
		
	    // Draw Gold Coins 
	    for (Coin goldCoin : coins) 
	    goldCoin.render(batch); 
	     
	    // Draw Feathers 
	    for (Gem gem : gems) 
	    gem.render(batch); 
	     
	    batch.setColor(Color.WHITE);
	    
	    // Draw Player Character 
	    player.render(batch); 
		
		// Draw Water Overlay
		blackOverlay.render(batch);
		
		// Draw Rocks
		for (Platform platform : platforms)
		platform.render(batch);
		
		for(int fireballIndex = 0; fireballIndex < player.fireballs.size; fireballIndex++)
		{
			Fireball fireball = player.fireballs.get(fireballIndex);
			fireball.render(batch);
		}
		
		for(int enemyIndex = 0; enemyIndex < enemies.size; enemyIndex++)
		{
			Enemy enemy = enemies.get(enemyIndex);
			enemy.render(batch);
		}
		
		crow.render(batch);
		
		// Draw Clouds
		clouds.render(batch);
		
		currentLevel.particleEffect.start();
		currentLevel.particleEffect.draw(batch);

		batch.setColor(Color.WHITE);
	}
}
