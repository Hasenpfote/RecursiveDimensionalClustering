/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hasenpfote
 */
public class CircularPlatePhysicsComponent {

	public void update(CircularPlate circle, double dt){

		double ax = circle.fx * circle.inv_m;
		double ay = circle.fy * circle.inv_m;

		circle.vx += ax * dt;
		circle.vy += ay * dt;

		circle.px += circle.vx * dt;
		circle.py += circle.vy * dt;

		circle.fx = circle.fy = 0.0;
	}

}
