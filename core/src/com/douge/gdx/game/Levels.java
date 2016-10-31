package com.douge.gdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;

public class Levels 
{
	public class Level
	{
		public int back;
		public String filepath;
		
		public Level(int color, String filepath)
		{
			this.back = color;
			this.filepath = filepath;
		}
	}
	
	private ArrayList<Level> levels;
	
	public Levels()
	{
		levels = new ArrayList<Level>();
		init();
	}

	private void init() 
	{
		Level level1 = new Level(BLOCK_TYPE.ROCK_BACK.getColor(), "../core/assets/levels/level01.png");
		Level level2 = new Level(BLOCK_TYPE.STAR_BACK.getColor(), "../core/assets/levels/level02.png");
		
		levels.add(level2);
		levels.add(level1);
	}
	
	public Level getLevel(int index)
	{
		return levels.get(index);
	}
}
