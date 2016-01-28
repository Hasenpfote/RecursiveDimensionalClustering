
import java.awt.Graphics2D;
import jp.gr.java_conf.hasenpfote.framework.KeyboardInput;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hasenpfote
 */
public class CircularPlate {

	public double px, py;
	public double vx, vy;
	public double r;
	public double m, inv_m;
	public double fx, fy;

	private CircularPlateInputComponent ic = null;
	private CircularPlatePhysicsComponent pc = null;
	private CircularPlateRenderComponent rc = null;

	public CircularPlate(CircularPlateInputComponent ic,
						 CircularPlatePhysicsComponent pc,
						 CircularPlateRenderComponent rc){
		this.ic = ic;
		this.pc = pc;
		this.rc = rc;
	}

	public void updateInputComponent(KeyboardInput key){
		if(ic != null)
			ic.update(this, key);
	}

	public void updatePhysicsComponent(double dt){
		if(pc != null)
			pc.update(this, dt);
	}

	public void updateRenderComponent(Graphics2D g2d){
		if(rc != null)
			rc.update(this, g2d);
	}

}
