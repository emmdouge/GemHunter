package com.douge.gdx.game.level;

import java.util.ArrayList;

import com.aurelienribon.bodyeditor.BodyEditorLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
import com.douge.gdx.game.WorldController;
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
import com.douge.gdx.game.objects.Wizard;
import com.douge.gdx.game.objects.backgroundtile.BackgroundLevel1;
import com.douge.gdx.game.objects.backgroundtile.BackgroundLevel3;
import com.douge.gdx.game.objects.backgroundtile.BackgroundLevel4;
import com.douge.gdx.game.objects.backgroundtile.BackgroundLevel5;
import com.douge.gdx.game.objects.backgroundtile.BackgroundLevel6;
import com.douge.gdx.game.objects.backgroundtile.BackgroundLevel7;
import com.douge.gdx.game.objects.backgroundtile.BackgroundRock;
import com.douge.gdx.game.objects.backgroundtile.BackgroundLevel2;
import com.douge.gdx.game.objects.backgroundtile.BackgroundTile;
import com.douge.gdx.game.objects.collectible.Collectible;
import com.douge.gdx.game.objects.collectible.GoldCoin;
import com.douge.gdx.game.objects.collectible.LevelGem;
import com.douge.gdx.game.objects.enemy.Bat;
import com.douge.gdx.game.objects.enemy.BigNote;
import com.douge.gdx.game.objects.enemy.Enemy;
import com.douge.gdx.game.objects.enemy.Mouse;
import com.douge.gdx.game.objects.enemy.Horse;
import com.douge.gdx.game.objects.enemy.Rock;
import com.douge.gdx.game.objects.enemy.Shadow;
import com.douge.gdx.game.objects.environment.BlackOverlay;
import com.douge.gdx.game.objects.environment.Clouds;
import com.douge.gdx.game.objects.environment.Trees;
import com.douge.gdx.game.objects.platform.Platform;
import com.douge.gdx.game.objects.platform.PlatformLevel1;
import com.douge.gdx.game.objects.platform.PlatformLevel2;
import com.douge.gdx.game.objects.platform.PlatformLevel3;
import com.douge.gdx.game.objects.platform.PlatformLevel4;
import com.douge.gdx.game.objects.platform.PlatformLevel5;
import com.douge.gdx.game.objects.platform.PlatformLevel6;
import com.douge.gdx.game.objects.platform.PlatformLevel7;
import com.douge.gdx.game.objects.platform.XMovingPlatform;
import com.douge.gdx.game.objects.platform.YMovingPlatform;
import com.douge.gdx.game.screen.GameScreen;
import com.douge.gdx.game.screen.transition.DirectedGame;
import com.douge.gdx.game.utils.AudioManager;

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
	public Array<Wizard> wizards = new Array<Wizard>();
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
	    
		Level level1 = new Level(BLOCK_TYPE.LEVEL1_BACK.getColor(), new PlatformLevel1(), "../core/assets/levels/level01.png", messages, Assets.instance.forest, Assets.instance.music.lvl1music, Assets.instance.tiles.goal1);
		messages.enqueue(new Message(Assets.instance.music.intro, new Vector2(0, 0)));
		messages.enqueue(new Message(Assets.instance.music.lvl1, new Vector2(125, 0)));
		messages.enqueue(new NullMessage());
		
		messages = new MessageQueue();
		Level level2 = new Level(BLOCK_TYPE.LEVEL2_BACK.getColor(), new PlatformLevel2(), "../core/assets/levels/level02.png", messages, Assets.instance.snow, Assets.instance.music.lvl2music, Assets.instance.tiles.goal2);
		messages.enqueue(new Message(Assets.instance.music.lvl2, new Vector2(122, -50)));
		messages.enqueue(new NullMessage());
		
		messages = new MessageQueue();
		Level level3 = new Level(BLOCK_TYPE.LEVEL3_BACK.getColor(), new PlatformLevel3(), "../core/assets/levels/level03.png", messages, Assets.instance.forest, Assets.instance.music.lvl3music, Assets.instance.tiles.goal3);
		messages.enqueue(new Message(Assets.instance.music.lvl3, new Vector2(123, 0)));
		messages.enqueue(new NullMessage());
		
		messages = new MessageQueue();
		Level level4 = new Level(BLOCK_TYPE.LEVEL4_BACK.getColor(), new PlatformLevel4(), "../core/assets/levels/level04.png", messages, Assets.instance.forest, Assets.instance.music.lvl4music, Assets.instance.tiles.goal4);
		messages.enqueue(new Message(Assets.instance.music.lvl4, new Vector2(123, -50)));
		messages.enqueue(new NullMessage());
		
		messages = new MessageQueue();
		Level level5 = new Level(BLOCK_TYPE.LEVEL5_BACK.getColor(), new PlatformLevel5(), "../core/assets/levels/level05.png", messages, Assets.instance.forest, Assets.instance.music.lvl5music, Assets.instance.tiles.goal5);
		messages.enqueue(new Message(Assets.instance.music.lvl5, new Vector2(123, -50)));
		messages.enqueue(new NullMessage());
		
		messages = new MessageQueue();
		Level level6 = new Level(BLOCK_TYPE.LEVEL6_BACK.getColor(), new PlatformLevel6(), "../core/assets/levels/level06.png", messages, Assets.instance.forest, Assets.instance.music.lvl6music, Assets.instance.tiles.goal6);
		messages.enqueue(new Message(Assets.instance.music.lvl6, new Vector2(124, -50)));
		messages.enqueue(new NullMessage());
		
		messages = new MessageQueue();
		Level level7 = new Level(BLOCK_TYPE.LEVEL7_BACK.getColor(), new PlatformLevel7(), "../core/assets/levels/level07.png", messages, Assets.instance.forest, Assets.instance.music.lvl7music, Assets.instance.tiles.goal7);
		messages.enqueue(new Message(Assets.instance.music.lvl7, new Vector2(0, 0)));
		messages.enqueue(new Message(Assets.instance.music.outro, new Vector2(0, 0)));
		messages.enqueue(new NullMessage());
		
		levels.add(level1);
		levels.add(level2);
		levels.add(level3);
		levels.add(level4);
		levels.add(level5);
		levels.add(level6);
		levels.add(level7);
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
		wizards.clear();
		
	}

	public void initCurrentLevel(int currentLevelIndex) 
	{
		clearLevel();
		this.currentLevelIndex  = currentLevelIndex;
	    currentLevel = levels.get(currentLevelIndex);
	    
	    AudioManager.instance.play(currentLevel.levelSong); 
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
					if (BLOCK_TYPE.LEVEL1_BACK.sameColor(currentLevel.back)) 
					{
						obj = new BackgroundLevel1();
					}
					else if(BLOCK_TYPE.LEVEL2_BACK.sameColor(currentLevel.back))
					{
						obj = new BackgroundLevel2();					
					}
					else if(BLOCK_TYPE.LEVEL3_BACK.sameColor(currentLevel.back))
					{
						obj = new BackgroundLevel3();					
					}
					else if(BLOCK_TYPE.LEVEL4_BACK.sameColor(currentLevel.back))
					{
						obj = new BackgroundLevel4();					
					}
					else if(BLOCK_TYPE.LEVEL5_BACK.sameColor(currentLevel.back))
					{
						obj = new BackgroundLevel5();					
					}
					else if(BLOCK_TYPE.LEVEL6_BACK.sameColor(currentLevel.back))
					{
						obj = new BackgroundLevel6();					
					}
					else if(BLOCK_TYPE.LEVEL7_BACK.sameColor(currentLevel.back))
					{
						obj = new BackgroundLevel7();					
					}
					obj.position.set(pixelX, baseHeight);
					backgroundTiles.add((BackgroundTile)obj);
				}
				
				// rock
				if (BLOCK_TYPE.LEVEL1_PLATFORM.sameColor(currentPixel)) 
				{
					if (lastPixel != currentPixel) 
					{ 
						obj = new PlatformLevel1();
						obj.position.set(pixelX, baseHeight + expOffset);
						platforms.add((Platform)obj);
					} 
					else 
					{
						platforms.get(platforms.size - 1).increaseLength(1);
					}
				}
				
				else if (BLOCK_TYPE.LEVEL2_PLATFORM.sameColor(currentPixel)) 
				{
					if (lastPixel != currentPixel) 
					{
						obj = new PlatformLevel2();
						obj.position.set(pixelX, baseHeight + expOffset);
						platforms.add((Platform)obj);
					} 
					else 
					{
						platforms.get(platforms.size - 1).increaseLength(1);
					}
				}
				
				else if (BLOCK_TYPE.LEVEL3_PLATFORM.sameColor(currentPixel)) 
				{
					if (lastPixel != currentPixel) 
					{
						obj = new PlatformLevel3();
						obj.position.set(pixelX, baseHeight + expOffset);
						platforms.add((Platform)obj);
					} 
					else 
					{
						platforms.get(platforms.size - 1).increaseLength(1);
					}
				}
				
				else if (BLOCK_TYPE.LEVEL4_PLATFORM.sameColor(currentPixel)) 
				{
					if (lastPixel != currentPixel) 
					{
						obj = new PlatformLevel4();
						obj.position.set(pixelX, baseHeight + expOffset);
						platforms.add((Platform)obj);
					} 
					else 
					{
						platforms.get(platforms.size - 1).increaseLength(1);
					}
				}
				
				else if (BLOCK_TYPE.LEVEL5_PLATFORM.sameColor(currentPixel)) 
				{
					if (lastPixel != currentPixel) 
					{
						obj = new PlatformLevel5();
						obj.position.set(pixelX, baseHeight + expOffset);
						platforms.add((Platform)obj);
					} 
					else 
					{
						platforms.get(platforms.size - 1).increaseLength(1);
					}
				}
				else if (BLOCK_TYPE.LEVEL6_PLATFORM.sameColor(currentPixel)) 
				{
					if (lastPixel != currentPixel) 
					{
						obj = new PlatformLevel6();
						obj.position.set(pixelX, baseHeight + expOffset);
						platforms.add((Platform)obj);
					} 
					else 
					{
						platforms.get(platforms.size - 1).increaseLength(1);
					}
				}
				else if (BLOCK_TYPE.LEVEL7_PLATFORM.sameColor(currentPixel)) 
				{
					if (lastPixel != currentPixel) 
					{
						obj = new PlatformLevel7();
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
			  	    if(currentLevel.platform instanceof PlatformLevel7)
				    {
				    	player.isHuman = true;
				    }
				}
				
				else if (BLOCK_TYPE.WIZARD.sameColor(currentPixel)) 
				{
			          obj = new Wizard(); 
			          obj.position.set(pixelX, baseHeight + expOffset); 
			          wizards.add((Wizard)obj); 
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
			          obj = new LevelGem(currentLevel); 
			          obj.position.set(pixelX + obj.origin.x,baseHeight + expOffset); 
			          collectibles.add((LevelGem)obj);
			          redCoin = (LevelGem)obj;
				}
				
				// slime
				else if(BLOCK_TYPE.ENEMY_HORSE.sameColor(currentPixel))
				{
			          obj = new Horse(Assets.instance.horse); 
			          obj.position.set(pixelX,baseHeight + expOffset); 
			          enemies.add((Horse)obj); 	
				}
				
				// skeleton
				else if(BLOCK_TYPE.ENEMY_MOUSE.sameColor(currentPixel))
				{
			          obj = new Mouse(Assets.instance.mouse); 
			          obj.position.set(pixelX, baseHeight + expOffset); 
			          enemies.add((Mouse)obj); 	
				}
				
				// bat
				else if(BLOCK_TYPE.ENEMY_BAT.sameColor(currentPixel))
				{
			          obj = new Bat(Assets.instance.bat); 
			          obj.position.set(pixelX, baseHeight + expOffset);
			          enemies.add((Bat)obj); 	
				}
				
				// bat
				else if(BLOCK_TYPE.ENEMY_BIGNOTE.sameColor(currentPixel))
				{
			          obj = new BigNote(Assets.instance.note); 
			          obj.position.set(pixelX, baseHeight + expOffset);
			          enemies.add((BigNote)obj); 	
				}
				
				// bat
				else if(BLOCK_TYPE.ENEMY_ROCK.sameColor(currentPixel))
				{
			          obj = new Rock(Assets.instance.rock); 
			          obj.position.set(pixelX, baseHeight + expOffset);
			          enemies.add((Rock)obj); 	
				}
				
				// bat
				else if(BLOCK_TYPE.ENEMY_SHADOW.sameColor(currentPixel))
				{
			          obj = new Shadow(Assets.instance.shadow); 
			          obj.position.set(pixelX, baseHeight + expOffset);
			          enemies.add((Shadow)obj); 	
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
		//crow.update(deltaTime);
		
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
		//trees.render(batch);
		
	    // Draw Gold Coins 
	    for (Collectible collectible : collectibles) 
	    collectible.render(batch); 
	     
	    batch.setColor(Color.WHITE);
		
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
		
		//crow.render(batch);
		
		// Draw Clouds
		clouds.render(batch);
		
		currentLevel.particleEffect.start();
		currentLevel.particleEffect.draw(batch);

		batch.setColor(Color.WHITE);
		
	    // Draw Player Character 
	    player.render(batch); 
	    
	    for(Wizard wizard: wizards)
	    wizard.render(batch);
	}


}
