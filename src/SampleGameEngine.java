import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Random;

import jp.gr.java_conf.hasenpfote.framework.GameEngine;
import jp.gr.java_conf.hasenpfote.framework.GameSystem;
import jp.gr.java_conf.hasenpfote.framework.KeyboardInput;
import jp.gr.java_conf.hasenpfote.framework.MouseInput;


public final class SampleGameEngine extends GameEngine{

	private static final int DEFAULT_FPS = 60;
	private static final double PIXELS_PER_METER = 100.0;
	private static final Font font = new Font(Font.SERIF, Font.BOLD, 18);
	private static final Random rnd = new Random();

	public enum InputDevice{
		KEYBOARD,
		MOUSE
	}

	private int screen_width;
	private int screen_height;
	private KeyboardInput keyboard = null;
	private MouseInput mouse = null;
	private boolean pause = false;
	private boolean debug = true;

	private static final double G = 9.80665;

	private static final int NUM_OBJECTS = 10;
	private final ArrayList<CircularPlate> objects = new ArrayList<>();

	private Point2D.Double wall_min = null;
	private Point2D.Double wall_max = null;

	private final Rdc rdc = new Rdc();
	private static final RdcRenderComponent rdc_rc = new RdcRenderComponent();

	public SampleGameEngine(Canvas canvas, EnumSet<InputDevice> flag){
		super(DEFAULT_FPS, canvas.getBufferStrategy());
		Rectangle rect = canvas.getBounds();
		screen_width = rect.width;
		screen_height = rect.height;
		if(flag.contains(InputDevice.KEYBOARD)){
			keyboard = new KeyboardInput();
		}
		if(flag.contains(InputDevice.MOUSE)){
			mouse = new MouseInput();
		}
	}

	public KeyListener getKeyListener(){ return keyboard; }
	public MouseListener getMouseListener(){ return mouse; }
	public MouseMotionListener getMouseMotionListener(){ return mouse; }
	public MouseWheelListener getMouseWheelListener(){ return mouse; }

	@Override
	protected void initialize(){
		GameSystem.getInstance().setScreenSize(screen_width, screen_height);
		GameSystem.getInstance().setPixelsPerUnit((int)PIXELS_PER_METER);
		GameSystem.getInstance().updateMarix();

		wall_min = new Point2D.Double();
		wall_max = new Point2D.Double();
		Point2D.Double min = new Point2D.Double(0.0, screen_height);
		Point2D.Double max = new Point2D.Double(screen_width, 0.0);
		AffineTransform wtos = GameSystem.getInstance().getWorldToScreenMatrix();
		try{
			wtos.inverseTransform(min, wall_min);
			wtos.inverseTransform(max, wall_max);
		}catch(NoninvertibleTransformException e){
		}

		double wall_width = wall_max.x - wall_min.x;
		double wall_height = wall_max.y - wall_min.y;

		CircularPlateInputComponent cp_ip = new CircularPlateInputComponent();
		CircularPlatePhysicsComponent cp_pc = new CircularPlatePhysicsComponent();
		CircularPlateRenderComponent cp_rc = new CircularPlateRenderComponent();

		for(int i = 0; i < NUM_OBJECTS; i++){
			CircularPlate cp = new CircularPlate(cp_ip, cp_pc, cp_rc);
			cp.px = (rnd.nextDouble() - 0.5) * wall_width;
			cp.py = (rnd.nextDouble() - 0.5) * wall_height;
			cp.vx = (rnd.nextDouble() - 0.5) * 2.0 * 10.0 + 10.0;
			cp.vy = (rnd.nextDouble() - 0.5) * 2.0 * 10.0 + 10.0;
			cp.r = rnd.nextDouble() * 0.5 + 0.5;
			cp.m = Math.PI * cp.r * cp.r;
			cp.inv_m = 1.0 / cp.m;

			objects.add(cp);
		}
	}

	@Override
	protected void cleanup() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	protected void processInput(){
		if(keyboard != null)
			keyboard.poll();
		if(mouse != null)
			mouse.poll();

		if(keyboard.isKeyDownOnce(KeyEvent.VK_SPACE))
			pause = !pause;

		if(keyboard.isKeyDownOnce(KeyEvent.VK_F1))
			debug = !debug;

		for(CircularPlate cp : objects){
			cp.updateInputComponent(keyboard);
		}
	}

	/**
	 * 衝突応答
	 * @param e		反発係数
	 * @param m1	剛体1の質量
	 * @param m2	剛体2の質量
	 * @param v1	剛体1の速度
	 * @param v2	剛体2の速度
	 * @return 力積
	 */
	double calcImpulse(double e, double m1, double m2, double v1, double v2){
		double I = (1.0 + e) * (m1 * m2) / (m1 + m2) * (v2 - v1);
		return I;
	}

	@Override
	protected void updateFrame(double dt){
		if(pause)
			return;

		rdc.recursiveClustering(objects);

		double e = 0.25;	// 反発係数
		double m = 1000.0;

		for(CircularPlate cp : objects){
			//cp.updatePhysicsComponent(dt);

			// acceleration
			double ax = cp.fx * cp.inv_m;
			double ay = cp.fy * cp.inv_m - G;
			// velocity
			cp.vx += ax * dt;
			cp.vy += ay * dt;
			// position
			cp.px += cp.vx * dt;
			cp.py += cp.vy * dt;

			// collison for wall
			if(cp.px < (wall_min.x + cp.r)){
				cp.px = wall_min.x + cp.r;
				double I = calcImpulse(e, cp.m, m, cp.vx, 0.0);
				cp.vx += I * cp.inv_m;
			}
			if(cp.px > (wall_max.x - cp.r)){
				cp.px = wall_max.x - cp.r;
				double I = calcImpulse(e, cp.m, m, cp.vx, 0.0);
				cp.vx += I * cp.inv_m;
			}
			if(cp.py < (wall_min.y + cp.r)){
				cp.py = wall_min.y + cp.r;
				double I = calcImpulse(e, cp.m, m, cp.vy, 0.0);
				cp.vy += I * cp.inv_m;
			}
			if(cp.py > (wall_max.y - cp.r)){
				cp.py = wall_max.y - cp.r;
				double I = calcImpulse(e, cp.m, m, cp.vy, 0.0);
				cp.vy += I * cp.inv_m;
			}

			// clear force
			cp.fx = cp.fy = 0.0;
		}
	}

	@Override
	protected void renderFrame(Graphics2D g2d){
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setFont(font);
		// 背景のクリア
		g2d.clearRect(0, 0, screen_width, screen_height);
		{
			g2d.setColor(Color.WHITE);

			for(CircularPlate cp : objects){
				cp.updateRenderComponent(g2d);
			}
			if(debug){
				rdc_rc.update(rdc, g2d);
			}
		}
		// FPSの表示
		FontMetrics metrics = g2d.getFontMetrics();
		g2d.setColor(Color.YELLOW);
		String render_fps = String.format("%.2f", getFps());
		String update_fps = String.format("%.2f", getUps());
		g2d.drawString("fps : ups =" + render_fps + " : " + update_fps, 0, metrics.getAscent());
		//g2d.drawString("fps=" + Double.toString(getRenderFps()) + " / " + Double.toString(getUpdateFps()), 0, metrics.getAscent());
	}
}
