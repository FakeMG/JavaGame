package gameEngine;

import java.awt.image.DataBufferInt;

import gameEngine.gfx.Image;
import gameEngine.gfx.ImageTile;

public class Renderer {
	private int pW, pH;
	private int[] pixel;
	private int[] zBuffer;
	
	private int zDepth = 0;
	
	public Renderer(GameContainer gc) {
		pW = gc.getWidth();
		pH = gc.getHeight();
		pixel = ((DataBufferInt) gc.getWindow().getImage().getRaster().getDataBuffer()).getData();
		zBuffer = new int[pixel.length];
	}
	
	public void clear() {
		for(int i=0; i<pixel.length; i++) {
			pixel[i] = 0xFFFFFFFF;
			zBuffer[i] = 0;
		}
	}
	
	public void setPixel(int x, int y, int value) {
		int alpha = (value >> 24) & 0xff;
		
		if((x < 0 || x >= pW || y < 0 || y >= pH) || alpha == 0) return; //value == 0xffff00ff : transparent color(pink)
		
		if(zBuffer[x + y * pW] > zDepth) return;
		
		if(alpha == 255) {
			pixel[x + y * pW] = value;
			
		} else {
			int pixelColor = pixel[x + y * pW];
			
			int newRed = ((pixelColor >> 16) & 0xff) - (int)( ( ((pixelColor >> 16) & 0xff) - ((value >> 16) & 0xff)) * (alpha / 255f) );
			int newGreen = (pixelColor >> 8) & 0xff - (int)( ( ((pixelColor >> 8) & 0xff) - ((value >> 8) & 0xff)) * (alpha / 255f) );
			int newBlue = (pixelColor & 0xff) - (int)( ((pixelColor & 0xff) - (value & 0xff)) * (alpha / 255f) );
			
			pixel[x + y + pW] = (255 << 24 | newRed << 16 | newGreen << 8 | newBlue);
		}
	}
	
	public void drawImage(Image image, int offX, int offY) {
		//don't render
		if(offX < -image.getWidth() || offY < -image.getHeight()) return;
		if(offX >= pW || offY >= pH) return;
				
		int newX = 0, newY = 0;
		int newW = image.getWidth();
		int newH = image.getHeight();
		
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
	
	public void drawImageTile(ImageTile image, int offX, int offY, int tileX, int tileY) {
		//don't render
		if(offX < -image.getTileW() || offY < -image.getTileH()) return;
		if(offX >= pW || offY >= pH) return;
				
		int newX = 0, newY = 0;
		int newW = image.getTileW();
		int newH = image.getTileH();
		
		//out of screen
		if(offX < 0) newX -= offX;
		if(offY < 0) newY -= offY;
		if(newW + offX >= pW) newW -= newW + offX - pW;
		if(newH + offY >= pH) newH -= newH + offY - pH;
		
		
		for(int x = newX; x < newW; x++) {
			for(int y = newY; y < newH; y++) {
				setPixel(x + offX, y + offY, image.getPixel()[(x + tileX * image.getTileW()) + (y + tileY * image.getTileH()) * image.getWidth()]);
			}
		}
	}

	public int getzDepth() {
		return zDepth;
	}

	public void setzDepth(int zDepth) {
		this.zDepth = zDepth;
	}
}
