package com.douge.gdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.douge.gdx.game.assets.Assets;
import com.douge.gdx.game.enemy.Enemy;
import com.douge.gdx.game.enemy.Slime;
import com.douge.gdx.game.objects.AbstractGameObject;
import com.douge.gdx.game.objects.Player;
import com.douge.gdx.game.objects.Clouds;
import com.douge.gdx.game.objects.GoldCoin;
import com.douge.gdx.game.objects.JumpPotion;
import com.douge.gdx.game.objects.BackgroundRock;
import com.douge.gdx.game.objects.Trees;
import com.douge.gdx.game.objects.Rock;
import com.douge.gdx.game.objects.WaterOverlay;

/**
 * Level 1 of the game
 * @author Emmanuel
 *
 */
public class Level 
{
	public static final String TAG = Level.class.getName();
	
	public Player player; 
	public Array<GoldCoin> goldcoins; 
	public Array<JumpPotion> jumpPotion; 
	
	// objects
	public Array<Rock> rocks;
	public Array<BackgroundRock> backgroundRock;
	public Array<Enemy> enemies;
	
	// decoration
	public Clouds clouds;
	public Trees trees;
	public WaterOverlay waterOverlay;

	public ArrayList<Integer> enemiesToRemove;
	
	public Level (String filename) 
	{
		init(filename);
	}
	private void init (String filename) 
	{
	    // player character 
	    player = null; 
	     
	    // objects 
	    goldcoins = new Array<GoldCoin>(); 
	    jumpPotion = new Array<JumpPotion>(); 
		rocks = new Array<Rock>();
		backgroundRock = new Array<BackgroundRock>();
		enemies = new Array<Enemy>();
		enemiesToRemove = new ArrayList<Integer>();
		
		// load image file that represents the level data
		Pixmap pixmap = new Pixmap(Gdx.files.internal("../core/assets/levels/level-01.png"));
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
				
				// get color of current pixel as 32-bit RGBA value
				int currentPixel = pixmap.getPixel(pixelX, pixelY);
				
				// find matching color value to identify block type at (x,y)
				// point and create the corresponding game object if there is a match
		
				// rock back
				if (BLOCK_TYPE.ROCK_BACK.sameColor(currentPixel) || BLOCK_TYPE.validColor(currentPixel)) 
				{
						obj = new BackgroundRock();
						obj.position.set(pixelX, baseHeight);
						backgroundRock.add((BackgroundRock)obj);
				}
		
				// rock
				if (BLOCK_TYPE.ROCK.sameColor(currentPixel)) 
				{
					if (lastPixel != currentPixel) 
					{
						obj = new Rock();
						obj.position.set(pixelX, baseHeight);
						rocks.add((Rock)obj);
					} 
					else 
					{
						rocks.get(rocks.size - 1).increaseLength(1);
					}
				}
				
				// player spawn point
				else if (BLOCK_TYPE.PLAYER_SPAWNPOINT.sameColor(currentPixel)) 
				{
			          obj = new Player(); 
			          obj.position.set(pixelX, baseHeight); 
			          player = (Player)obj; 
				}
				
				// feather
				else if (BLOCK_TYPE.ITEM_JUMP_POTION.sameColor(currentPixel)) 
				{
			          obj = new JumpPotion(); 
			          obj.position.set(pixelX, baseHeight); 
			          jumpPotion.add((JumpPotion)obj); 
				}
		
				// gold coin
				else if (BLOCK_TYPE.ITEM_GOLD_COIN.sameColor(currentPixel)) 
				{
			          obj = new GoldCoin(); 
			          obj.position.set(pixelX,baseHeight); 
			          goldcoins.add((GoldCoin)obj); 
				}
				
				// slime
				else if(BLOCK_TYPE.ENEMY_SLIME.sameColor(currentPixel))
				{
			          obj = new Slime(Assets.instance.slime, enemies); 
			          obj.position.set(pixelX,baseHeight); 
			          enemies.add((Slime)obj); 	
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
		
		// decoration
		clouds = new Clouds(pixmap.getWidth());
		clouds.position.set(0, 2);

		int baseHeightOfBottomLeftPixelOfPixmap = 1;
		
		int lookGood = 1;
		trees = new Trees(pixmap.getWidth());
		trees.position.set(0, baseHeightOfBottomLeftPixelOfPixmap - yOffset + lookGood);
		
		lookGood = -2;
		waterOverlay = new WaterOverlay(pixmap.getWidth());
		waterOverlay.position.set(0, baseHeightOfBottomLeftPixelOfPixmap - yOffset + lookGood);
		
		// free memory
		pixmap.dispose();
		Gdx.app.debug(TAG, "level '" + filename + "' loaded");
	}
	
	public void update (float deltaTime) 
	{
		player.update(deltaTime);
		
		for(Rock rock : rocks)
		rock.update(deltaTime);
		
		for(GoldCoin goldCoin : goldcoins)
		goldCoin.update(deltaTime);
		
		for(JumpPotion greenHeart : jumpPotion)
		greenHeart.update(deltaTime);
		
		for(int i = 0; i < enemies.size; i++)
		enemies.get(i).update(deltaTime);
		
		clouds.update(deltaTime);
	}
	
	/**
	 * Draws the level
	 * @param batch the batch from the worldRenderer
	 */
	public void render (SpriteBatch batch) 
	{
		for(BackgroundRock rockBack: backgroundRock)
		rockBack.render(batch);
		
		// Draw trees
		trees.render(batch);
		
	    // Draw Gold Coins 
	    for (GoldCoin goldCoin : goldcoins) 
	    goldCoin.render(batch); 
	     
	    // Draw Feathers 
	    for (JumpPotion greenHeart : jumpPotion) 
	    greenHeart.render(batch); 
	     
	    batch.setColor(Color.WHITE);
	    
	    // Draw Player Character 
	    player.render(batch); 
		
		// Draw Water Overlay
		waterOverlay.render(batch);
		
		// Draw Rocks
		for (Rock rock : rocks)
		rock.render(batch);
		
		for(int enemyIndex = 0; enemyIndex < enemies.size; enemyIndex++)
		{
			Enemy enemy = enemies.get(enemyIndex);
			enemy.render(batch);
		}
		// Draw Clouds
		clouds.render(batch);
	}
}
