package jp.gr.java_conf.hasenpfote;

import java.awt.event.KeyEvent;

import jp.gr.java_conf.hasenpfote.framework.KeyboardInput;
import jp.gr.java_conf.hasenpfote.math.Vector2f;

/**
 *
 * @author Hasenpfote
 */
public class CircularPlateInputComponent{

	public void update(CircularPlate circle, KeyboardInput key){
		if(key.isKeyDown(KeyEvent.VK_UP)){
			circle.getForce().madd(Vector2f.E2, 200.0f);
		}
		if(key.isKeyDown(KeyEvent.VK_DOWN)){
			circle.getForce().madd(Vector2f.E2,-200.0f);
		}
		if(key.isKeyDown(KeyEvent.VK_LEFT)){
			circle.getForce().madd(Vector2f.E1,-200.0f);
		}
		if(key.isKeyDown(KeyEvent.VK_RIGHT)){
			circle.getForce().madd(Vector2f.E1, 200.0f);
		}
	}
}
