package jp.gr.java_conf.hasenpfote;

import java.awt.Graphics2D;
import jp.gr.java_conf.hasenpfote.framework.KeyboardInput;
import jp.gr.java_conf.hasenpfote.math.Vector2;

/**
 *
 * @author Hasenpfote
 */
public class CircularPlate{

	private CircularPlateInputComponent ic = null;
	private CircularPlatePhysicsComponent pc = null;
	private CircularPlateRenderComponent rc = null;

	private final Vector2 position = new Vector2();
	private final Vector2 linear_velocity = new Vector2();
	private final Vector2 force = new Vector2();
	private float mass, inv_mass;
	private float radius;

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

	public Vector2 getPosition(){
		return position;
	}

	public Vector2 getLinearVelocity(){
		return linear_velocity;
	}

	public Vector2 getForce(){
		return force;
	}

	public float getMass(){
		return mass;
	}

	public void setMass(float mass){
		this.mass = mass;
		inv_mass = 1.0f / mass;
	}

	public float getInvMass(){
		return inv_mass;
	}

	public float getRadius(){
		return radius;
	}

	public void setRadius(float radius){
		this.radius = radius;
	}
}
