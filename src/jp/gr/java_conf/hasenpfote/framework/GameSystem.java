/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.gr.java_conf.hasenpfote.framework;

import java.awt.geom.AffineTransform;

/**
 *
 * @author Hasenpfote
 */
public class GameSystem{

	private static class LazyHolder{
		private static final GameSystem INSTANCE = new GameSystem();
	}

	private GameSystem(){
	}

	public static GameSystem getInstance(){
		return LazyHolder.INSTANCE;
	}

	private int screen_width;
	private int screen_height;
	private int pixels_per_unit = 100;
	private double aspect;

	private final AffineTransform wtos = new AffineTransform();


	public void setScreenSize(int width, int height){
		screen_width = width;
		screen_height = height;
		aspect = (double)screen_width / (double)screen_height;
	}

	public int getScreenWidth(){
		return screen_width;
	}

	public int getScreenHeight(){
		return screen_height;
	}

	public void setPixelsPerUnit(int ppu){
		pixels_per_unit = ppu;
	}

	public int getPixelsPerUnit(){
		return pixels_per_unit;
	}

	public void updateMarix(){
		/*
			sx   [sw/2	0		sw/2]	[ppu/sh	0		0]	 [1/aspect	0	0]	 wx
			sy = [0		-sh/2	sh/2] *	[0		ppu/sh	0] * [0			1	0] * wy
			sw	 [0		0		1	]	[0		0		1]	 [0			0	1]	 1
		*/
		double hsw = (double)screen_width * 0.5;
		double hsh = (double)screen_height * 0.5;
		double unit = (double)pixels_per_unit / (double)screen_height;

		wtos.setTransform(	hsw * unit * (1.0 / aspect),
							0.0,
							0.0,
							-hsh * unit,
							hsw,
							hsh);
	}

	public AffineTransform getWorldToScreenMatrix(){
		return wtos;
	}

}
