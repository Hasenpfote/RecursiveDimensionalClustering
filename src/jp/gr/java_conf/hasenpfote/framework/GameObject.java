/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.gr.java_conf.hasenpfote.framework;

/**
 *
 * @author Hasenpfote
 */
public class GameObject{

	public double px, py;
	public double vx, vy;
	public double m, inv_m;
	public double r;

	public GameObject(RenderComponent render){
		this.render = render;
	}

	public RenderComponent getRenderComponent(){ return render; }

	private RenderComponent render = null;
}
