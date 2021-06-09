package game;

import java.awt.event.KeyEvent;

import gameEngine.AbstractGame;
import gameEngine.GameContainer;
import gameEngine.Renderer;
import gameEngine.audio.SoundClip;
import gameEngine.gfx.Image;

public class GameManager extends AbstractGame{
	private Image testImg;
	private SoundClip audio;
	
	public GameManager() {
		testImg = new Image("/Arrow.png");
		audio = new SoundClip("/sfx_wpn_laser3.wav");
	}

	@Override
	public void update(GameContainer gc, float deltaTime) {
		if(gc.getInput().isKeyDown(KeyEvent.VK_A) || gc.getInput().isKeyDown(KeyEvent.VK_D)) {
			audio.play();
		}
		
	}

	@Override
	public void render(GameContainer gc, Renderer renderer) {
		renderer.drawImage(testImg, gc.getInput().getMouseX(), gc.getInput().getMouseY());
		
	}

	public static void main(String[] args) {
		GameContainer gc = new GameContainer(new GameManager());
		gc.start();
	}
}
