package com.douge.gdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;

public class Level
{
	public int back;
	public String filepath;
	public Messages messages;
	
	public Level(int color, String filepath, Messages messages)
	{
		this.back = color;
		this.filepath = filepath;
		this.messages = messages;
	}
}
