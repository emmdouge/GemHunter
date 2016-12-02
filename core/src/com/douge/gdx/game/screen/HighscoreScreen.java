package com.douge.gdx.game.screen;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor; 
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.douge.gdx.game.Constants;
import com.douge.gdx.game.WorldRenderer;
import com.douge.gdx.game.message.Message;
import com.douge.gdx.game.message.MessageQueue;
import com.douge.gdx.game.message.NullMessage;
import com.douge.gdx.game.screen.transition.DirectedGame;
import com.douge.gdx.game.utils.GamePreferences;

public class HighscoreScreen extends AbstractGameScreen
{
	private MessageQueue highscoreList;
	private SpriteBatch batch;
	private OrthographicCamera cameraGUI;
	public HighscoreScreen(DirectedGame game) 
	{
		super(game);
		cameraGUI = new OrthographicCamera(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
		cameraGUI.position.set(0, 0, 0);
		cameraGUI.setToOrtho(true); // flip y-axis
		cameraGUI.update();
		batch = new SpriteBatch();
	}

	@Override
	public void render(float deltaTime) 
	{
		Gdx.gl.glClearColor(1f, 0f, 1.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(cameraGUI.combined);
		batch.begin();
		
		if(highscoreList.head != null)
		{
			highscoreList.head.updateText(deltaTime, null);
			highscoreList.head.renderText(batch);
			System.out.println(highscoreList.head.text + " " + highscoreList.head.stateTime);
			if(Gdx.input.isKeyPressed(Keys.SPACE))
			{
				highscoreList.dequeue();
				game.setScreen(new MenuScreen(game));
			}
		}
		
		batch.end();
	}

	@Override
	public void resize(int width, int height) 
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void show() 
	{
		String text = "";
        String playerName = "";
        
        try 
        {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader("../core/highscores.txt");

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);
			while((playerName = bufferedReader.readLine()) != null) 
			{
			    text += playerName;
			    text += "\n";
			}
	        // Always close files.
	        bufferedReader.close(); 
	        
	        String newScore = GamePreferences.instance.name + ":" + game.score + "\n";
	        FileWriter fw = new FileWriter("../core/highscores.txt", true);
	        BufferedWriter bw = new BufferedWriter(fw);
	        PrintWriter out = new PrintWriter(bw);
	        out.append(System.getProperty("line.separator"));
	        out.append(newScore);
	        out.close();
	        text += newScore;
	        

		} 
        catch (IOException e) 
        {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   

        String[] players = text.split("\n");
		for(int i = 0; i < players.length-1; i++)
		{
			for(int j = 0; j < players.length-1; j++)
			{
				System.out.println(players[j]);
				System.out.println(players[j+1]);
				int currentScore = Integer.parseInt(players[j].split(":")[1]);
				int nextScore = Integer.parseInt(players[j+1].split(":")[1]);
				if(currentScore < nextScore)
				{
					String temp = players[j];
					players[j] = players[j+1];
					players[j+1] = temp;
				}
			}
		}
		
		highscoreList = new MessageQueue();
		String combinedText = "HIGHSCORES: \n\n";
		for(int i = 0; i < players.length; i++)
		{
			combinedText += (i+1) + ". " + players[i] + "\n\n";
		}
		Message playerScore = new Message(combinedText, null, null, Constants.VIEWPORT_GUI_HEIGHT);
		playerScore.shouldBeRendered = true;
		playerScore.iconIsRendered = true;
		highscoreList.enqueue(playerScore);
	}

	@Override
	public void hide() 
	{

	}

	@Override
	public void pause() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public InputProcessor getInputProcessor() 
	{
		// TODO Auto-generated method stub
		return null;
	}

}
