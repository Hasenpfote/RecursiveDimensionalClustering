
import java.awt.Graphics2D;
import jp.gr.java_conf.hasenpfote.framework.KeyboardInput;
import jp.gr.java_conf.hasenpfote.math.Vector2d;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hasenpfote
 */
public class CircularPlate{

	private CircularPlateInputComponent ic = null;
	private CircularPlatePhysicsComponent pc = null;
	private CircularPlateRenderComponent rc = null;

	private final Vector2d position = new Vector2d();
	private final Vector2d linear_velocity = new Vector2d();
	private final Vector2d force = new Vector2d();
	private double mass, inv_mass;
	private double radius;

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

	public Vector2d getPosition(){
		return position;
	}

	public Vector2d getLinearVelocity(){
		return linear_velocity;
	}

	public Vector2d getForce(){
		return force;
	}

	public double getMass(){
		return mass;
	}

	public void setMass(double mass){
		this.mass = mass;
		inv_mass = 1.0 / mass;
	}

	public double getInvMass(){
		return inv_mass;
	}

	public double getRadius(){
		return radius;
	}

	public void setRadius(double radius){
		this.radius = radius;
	}
}
