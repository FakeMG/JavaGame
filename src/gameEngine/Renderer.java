package gameEngine;

import java.awt.image.DataBufferInt;

import gameEngine.gfx.Image;

public class Renderer {
	private int pW, pH;
	private int[] pixel;
	
	public Renderer(GameContainer gc) {
		pW = gc.getWidth();
		pH = gc.getHeight();
		pixel = ((DataBufferInt) gc.getWindow().getImage().getRaster().getDataBuffer()).getData();
	}
	
	public void clear() {
		for(int i=0; i<pixel.length; i++) {
			pixel[i] = 0xFFFFFFFF;
		}
	}
	
	public void setPixel(int x, int y, int value) {
		if((x < 0 || x >= pW || y < 0 || y >= pH)) return; //value = 0xffff00ff : transparent color
		pixel[x + y * pW] = value;
	}
	
	public void drawImage(Image image, int offX, int offY) {
		int newX = 0, newY = 0;
		int newW = image.getWidth();
		int newH = image.getHeight();
		
		//don't render
		if(offX < -newW || offY < -newH) return;
		if(offX >= pW || offY >= pH) return;
		
		//out of screen
		if(offX < 0) newX -= offX;
		if(offY < 0) newY -= offY;
		if(newW + offX >= pW) newW -= newW + offX - pW;
		if(newH + offY >= pH) newH -= newH + offY - pH;
		
		
		
		
		for(int x = newX; x < newW; x++) {
			for(int y = newY; y < newH; y++) {
				setPixel(x + offX, y + offY, image.getPixel()[x + y * image.getWidth()]);
			}
		}
	}
}
