package jp.gr.java_conf.hasenpfote.framework;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;


/**
 * マウス入力.
 * @author Hasenpfote
 */
public final class MouseInput implements MouseListener, MouseMotionListener, MouseWheelListener{

	private static final int BUTTON_COUNT = 3;

	private enum ButtonState{
		RELEASED,	// 押されていない
		PRESSED,	// 押されている
		ONCE		// 押された瞬間
	}

	private boolean[] states = null;
	private ButtonState[] polled_states = null;
	private Point pos = null;
	private Point polled_pos = null;

	public MouseInput(){
		states = new boolean[BUTTON_COUNT];
		polled_states = new ButtonState[BUTTON_COUNT];
		for(int i = 0; i < BUTTON_COUNT; i++){
			polled_states[i] = ButtonState.RELEASED;
		}
		pos = new Point(0, 0);
		polled_pos = new Point(0, 0);
	}

	public Point getPosition(){ return polled_pos; }

	/**
	 * 指定されたボタンが離されているか.
	 * @param button
	 * @return
	 * @see java.awt.event.MouseEvent
	 */
	public boolean isButtonUp(int button){
		return polled_states[button-1] == ButtonState.RELEASED;
	}

	/**
	 * 指定されたボタンが押されているか.
	 * @param button
	 * @return
	 * @see java.awt.event.MouseEvent
	 */
	public boolean isButtonDown(int button){
		return polled_states[button-1] == ButtonState.ONCE || polled_states[button-1] == ButtonState.PRESSED;
	}

	/**
	 * 指定されたボタンが押された瞬間か.
	 * @param button
	 * @return
	 * @see java.awt.event.MouseEvent
	 */
	public boolean isButtonDownOnce(int button){
		return polled_states[button-1] == ButtonState.ONCE;
	}

	/**
	 * 入力のポーリングを行う.
	 */
	public synchronized void poll(){
		for(int i = 0; i < BUTTON_COUNT; i++){
			if(states[i]){
				polled_states[i] = (polled_states[i] == ButtonState.RELEASED)? ButtonState.ONCE : ButtonState.PRESSED;
			}
			else{
				polled_states[i] = ButtonState.RELEASED;
			}
		}
		polled_pos.setLocation(pos);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ
	}

	@Override
	public synchronized void mousePressed(MouseEvent e){
		states[e.getButton()-1] = true;
	}

	@Override
	public synchronized void mouseReleased(MouseEvent e){
		states[e.getButton()-1] = false;
	}

	@Override
	public synchronized void mouseEntered(MouseEvent e){
		mouseMoved(e);
	}

	@Override
	public synchronized void mouseExited(MouseEvent e) {
		mouseMoved(e);
	}

	@Override
	public synchronized void mouseDragged(MouseEvent e) {
		mouseMoved(e);
	}

	@Override
	public synchronized void mouseMoved(MouseEvent e) {
		pos.setLocation(e.getPoint());
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		System.out.println(e.paramString());
	}
}
