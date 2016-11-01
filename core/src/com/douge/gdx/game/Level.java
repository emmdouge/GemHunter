package com.douge.gdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.douge.gdx.game.objects.Platform;

public class Level
{
	public int back;
	public Platform platform;
	public String filepath;
	public MessageQueue messages;
	
	public Level(int backColor, Platform platform, String filepath, MessageQueue messages)
	{
		this.back = backColor;
		this.platform = platform;
		this.filepath = filepath;
		this.messages = messages;
	}
}
