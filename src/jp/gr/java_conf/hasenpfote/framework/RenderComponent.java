/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.gr.java_conf.hasenpfote.framework;

import java.awt.Graphics2D;

/**
 *
 * @author Hasenpfote
 */
public abstract class RenderComponent{

	public abstract void update(Graphics2D g2d, GameObject obj);

}
