package jp.gr.java_conf.hasenpfote.framework;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


/**
 * キーボード入力.
 * @author Hasenpfote
 */
public final class KeyboardInput implements KeyListener{
	/** 0-255 のキーコードを格納 */
	private static final int KEY_COUNT = 256;

	private enum KeyState{
		RELEASED,	// 押されていない
		PRESSED,	// 押されている
		ONCE		// 押された瞬間
	}

	private boolean[] states = null;
	private KeyState[] polled_states = null;

	public KeyboardInput(){
		states = new boolean[KEY_COUNT];
		polled_states = new KeyState[KEY_COUNT];
		for(int i = 0; i < KEY_COUNT; i++){
			polled_states[i] = KeyState.RELEASED;
		}
	}

	/**
	 * 指定されたキーが離されているか.
	 * @param keycode
	 * @return
	 * @see java.awt.event.KeyEvent
	 */
	public boolean isKeyUp(int keycode){
		return polled_states[keycode] == KeyState.RELEASED;
	}

	/**
	 * 指定されたキーが押されているか.
	 * @param keycode
	 * @return
	 * @see java.awt.event.KeyEvent
	 */
	public boolean isKeyDown(int keycode){
		return polled_states[keycode] == KeyState.ONCE || polled_states[keycode] == KeyState.PRESSED;
	}

	/**
	 * 指定されたキーが押された瞬間か.
	 * @param keycode
	 * @return
	 * @see java.awt.event.KeyEvent
	 */
	public boolean isKeyDownOnce(int keycode){
		return polled_states[keycode] == KeyState.ONCE;
	}

	/**
	 * 入力のポーリングを行う.
	 */
	public synchronized void poll(){
		for(int i = 0; i < KEY_COUNT; i++){
			if(states[i]){
				polled_states[i] = (polled_states[i] == KeyState.RELEASED)? KeyState.ONCE : KeyState.PRESSED;
			}
			else{
				polled_states[i] = KeyState.RELEASED;
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e){
		// TODO 自動生成されたメソッド・スタブ
	}

	@Override
	public synchronized void keyPressed(KeyEvent e){
		int keycode = e.getKeyCode();
		if(keycode < 0 || keycode >= KEY_COUNT)
			return;
		states[keycode] = true;
	}

	@Override
	public synchronized void keyReleased(KeyEvent e){
		int keycode = e.getKeyCode();
		if(keycode < 0 || keycode >= KEY_COUNT)
			return;
		states[keycode] = false;
	}
}
