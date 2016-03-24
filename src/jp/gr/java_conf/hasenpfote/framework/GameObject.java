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

	public float px, py;
	public float vx, vy;
	public float m, inv_m;
	public float r;

	public GameObject(RenderComponent render){
		this.render = render;
	}

	public RenderComponent getRenderComponent(){ return render; }

	private RenderComponent render = null;
}
