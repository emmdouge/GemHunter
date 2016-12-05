package com.douge.gdx.game.level;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.douge.gdx.game.message.MessageQueue;
import com.douge.gdx.game.objects.platform.Platform;

public class Level
{
	public int back;
	public Platform platform;
	public String filepath;
	public MessageQueue messages;
	public ParticleEffect particleEffect;
	public Music levelSong;
	public Animation goal;
	
	public Level(int backColor, Platform platform, String filepath, MessageQueue messages, ParticleEffect particleEffect, Music levelSong, Animation goal)
	{
		this.back = backColor;
		this.platform = platform;
		this.filepath = filepath;
		this.messages = messages;
		this.particleEffect = particleEffect;
		this.particleEffect.setPosition(0f, 32f);
		this.levelSong = levelSong;
		this.goal = goal;
	}
}
