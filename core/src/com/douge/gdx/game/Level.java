package com.douge.gdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;

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
