package game;

import gameEngine.AbstractGame;
import gameEngine.GameContainer;
import gameEngine.Renderer;
import gameEngine.gfx.Image;

public class GameManager extends AbstractGame{
	private Image testImg;
	
	public GameManager() {
		testImg = new Image("/FakeMG logo.jpg");
	}

	@Override
	public void update(GameContainer gc, float deltaTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(GameContainer gc, Renderer renderer) {
		renderer.drawImage(testImg, gc.getInput().getMouseX() - 800, gc.getInput().getMouseY() - 800);
		
	}

	public static void main(String[] args) {
		GameContainer gc = new GameContainer(new GameManager());
		gc.start();
	}
}
