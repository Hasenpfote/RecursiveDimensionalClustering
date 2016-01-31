
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
public class CircularPlateInputComponent{

	public void update(CircularPlate circle, KeyboardInput key){
		if(key.isKeyDown(KeyEvent.VK_UP)){
			circle.getForce().madd(Vector2d.E2, 200.0);
		}
		if(key.isKeyDown(KeyEvent.VK_DOWN)){
			circle.getForce().madd(Vector2d.E2,-200.0);
		}
		if(key.isKeyDown(KeyEvent.VK_LEFT)){
			circle.getForce().madd(Vector2d.E1,-200.0);
		}
		if(key.isKeyDown(KeyEvent.VK_RIGHT)){
			circle.getForce().madd(Vector2d.E1, 200.0);
		}
	}
}
