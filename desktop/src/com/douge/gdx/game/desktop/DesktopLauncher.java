package com.douge.gdx.game.desktop;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.douge.gdx.game.DougeGdxGame;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;
//import org.objectweb.asm.ClassAdapter;

public class DesktopLauncher 
{
	public static void main (String[] arg) throws IOException, ClassNotFoundException 
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
//		File file = new File("../core/assets/images/gemhunter.atlas");
//		if (!file.exists()) 
//		{
//			Settings settings = new Settings();
//			settings.maxWidth = 2048;
//			settings.maxHeight = 2048;
//			settings.duplicatePadding = false;
//			settings.debug = drawDebugOutline;
//			TexturePacker.process(settings, "assets-raw/images/all", "../core/assets/images", "gemhunter");
//		}
		
//		String pathToJar = "./gemhunter.jar";
//		JarFile jarFile = new JarFile(pathToJar);
//		Enumeration<JarEntry> e = jarFile.entries();
//
//		URL[] urls = { new URL("jar:file:" + pathToJar+"!/") };
//		URLClassLoader.newInstance(urls);
//		while (e.hasMoreElements()) {
//		    JarEntry je = e.nextElement();
//		    if(je.isDirectory() || !je.getName().endsWith(".class")){
//		        continue;
//		    }
//		    // -6 because of .class
//		    String className = je.getName().substring(0,je.getName().length()-6);
//		    System.out.println(className);
//		    className = className.replace('/', '.');
//		}
		
		
		new LwjglApplication(new DougeGdxGame(), config);
	}
}
