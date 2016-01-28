
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
public class CircularPlateInputComponent {

	public void update(CircularPlate circle, KeyboardInput key){

		if(key.isKeyDown(KeyEvent.VK_UP)){
			circle.fy += 200.0;
		}
		if(key.isKeyDown(KeyEvent.VK_DOWN)){
			circle.fy -= 200.0;
		}
		if(key.isKeyDown(KeyEvent.VK_LEFT)){
			circle.fx -= 200.0;
		}
		if(key.isKeyDown(KeyEvent.VK_RIGHT)){
			circle.fx += 200.0;
		}
	}
}
