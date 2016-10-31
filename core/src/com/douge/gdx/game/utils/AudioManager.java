package com.douge.gdx.game.utils;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * Manages all the sounds and music
 * @author Emmanuel
 *
 */
public class AudioManager 
{
		public static final AudioManager instance = new AudioManager();
		private Music playingMusic;
		
		// singleton: prevent instantiation from other classes
		private AudioManager () { }
		
		/**
		 * plays sounds at volume 1
		 * @param sound
		 */
		public void play (Sound sound) 
		{
			play(sound, 1);
		}
		
		/**
		 * plays sound at at pitch 1
		 * @param sound
		 * @param volume
		 */
		public void play (Sound sound, float volume) 
		{
			play(sound, volume, 1);
		}
		
		/**
		 * plays sounds at pan 0
		 * @param sound
		 * @param volume
		 * @param pitch
		 */
		public void play (Sound sound, float volume, float pitch) 
		{
			play(sound, volume, pitch, 0);
		}
		
		/**
		 * plays sound if it is on in game preferences
		 * @param sound
		 * @param volume
		 * @param pitch
		 * @param pan
		 */
		public void play (Sound sound, float volume, float pitch, float pan) 
		{
			if (!GamePreferences.instance.sound) 
			{
				return;
			}
			sound.play(GamePreferences.instance.volSound * volume, pitch, pan);
		}
		
		/**
		 * plays music if it on in game preferences
		 * @param music
		 */
		public void play (Music music) 
		{
			stopMusic();
			playingMusic = music;
			if (GamePreferences.instance.music) 
			{
				music.setLooping(true);
				music.setVolume(GamePreferences.instance.volMusic);
				music.play();
			}
		}
			
		/**
		 * stops music
		 */
		public void stopMusic () 
		{
			if (playingMusic != null) 
			{
				playingMusic.stop();
			}
		}
			
		
		/**
		 * plays music or not depending on settings after it has been updated
		 */
		public void onSettingsUpdated () 
		{
			if (playingMusic == null) 
			{
				return;
			}
			
			playingMusic.setVolume(GamePreferences.instance.volMusic);
			
			if (GamePreferences.instance.music) 
			{
				if (!playingMusic.isPlaying()) 
				{
					playingMusic.play();
				}
			} 
			else 
			{
				playingMusic.pause();
			}
		}
}
