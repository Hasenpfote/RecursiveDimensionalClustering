import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
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
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Random;
import java.util.Vector;

import jp.gr.java_conf.hasenpfote.collide.Ball;
import jp.gr.java_conf.hasenpfote.collide.IDebugRender;
import jp.gr.java_conf.hasenpfote.collide.Rdc;
import jp.gr.java_conf.hasenpfote.collide.RdcDebugImpl;
import jp.gr.java_conf.hasenpfote.collide.Shape;
import jp.gr.java_conf.hasenpfote.framework.GameEngine;
import jp.gr.java_conf.hasenpfote.framework.GameSystem;
import jp.gr.java_conf.hasenpfote.framework.KeyboardInput;
import jp.gr.java_conf.hasenpfote.framework.MouseInput;


public final class SampleGameEngine extends GameEngine{

	private static final int DEFAULT_FPS = 60;

	public enum InputDevice{
		KEYBOARD,
		MOUSE
	}

	private static final double PIXELS_PER_METER = 100.0;
	private int screen_width;
	private int screen_height;
	private KeyboardInput keyboard = null;
	private MouseInput mouse = null;
	private boolean pause = false;
	private boolean debug = true;
	private Font font = null;

	private static final double G = 9.80665;

	// 外力[N]
	private double u_f = 0.0;
	private double d_f = 0.0;
	private double l_f = 0.0;
	private double r_f = 0.0;

	private static int NUM_BALLS = 20;
	private ArrayList<Shape> objects = null;
	private Random rnd = new Random();
	private Rdc rdc = null;
	private ArrayList<IDebugRender> debug_render_list = null;
	private RdcDebugImpl rdc_debug = null;

	private Point2D.Double wall_min = null;
	private Point2D.Double wall_max = null;


	//Vector< Point > lines = new Vector< Point >();
	//boolean drawingLine;

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
		font = new Font(Font.SERIF, Font.BOLD, 18);
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

		objects = new ArrayList<Shape>();
		debug_render_list = new ArrayList<IDebugRender>();
		rdc_debug = new RdcDebugImpl();
		debug_render_list.add(rdc_debug);
		rdc = new Rdc(rdc_debug);

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
		for(int i = 0; i < NUM_BALLS; i++){
			Ball ball = new Ball((rnd.nextDouble() - 0.5) * wall_width,
								 (rnd.nextDouble() - 0.5) * wall_height,
								 rnd.nextInt(40) - 20,
								 rnd.nextInt(40) - 20,
								 rnd.nextDouble() * 0.25 + 0.75
								);
			objects.add(ball);
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

		if(keyboard.isKeyDown(KeyEvent.VK_UP)){
			u_f = 100.0;
		}
		if(keyboard.isKeyUp(KeyEvent.VK_UP)){
			u_f = 0.0;
		}
		if(keyboard.isKeyDown(KeyEvent.VK_DOWN)){
			d_f = -100.0;
		}
		if(keyboard.isKeyUp(KeyEvent.VK_DOWN)){
			d_f = 0.0;
		}
		if(keyboard.isKeyDown(KeyEvent.VK_LEFT)){
			l_f = -100.0;
		}
		if(keyboard.isKeyUp(KeyEvent.VK_LEFT)){
			l_f = 0.0;
		}
		if(keyboard.isKeyDown(KeyEvent.VK_RIGHT)){
			r_f = 100.0;
		}
		if(keyboard.isKeyUp(KeyEvent.VK_RIGHT)){
			r_f = 0.0;
		}

		// if button pressed for first time,
		// start drawing lines
		/*
		if( mouse.isButtonDownOnce( 1 ) ) {
			drawingLine = true;
		}
		// if the button is down, add line point
		if( mouse.isButtonDown( 1 ) ) {
			lines.add( (Point) mouse.getPosition().clone() );
			// if the button is not down but we were drawing,
			// add a null to break up the lines
		} else if( drawingLine ){
			lines.add( null );
			drawingLine = false;
		}
		*/
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

		double right  = (double)screen_width / PIXELS_PER_METER;
		double top = (double)screen_height / PIXELS_PER_METER;


		// 反発係数
		double e = 0.25;

		// 合力
		double fx = l_f + r_f;
		double fy = u_f + d_f;

		double m = 1000.0;

		for(Shape object : objects){
			Ball o = (Ball)object;

			o.fx += fx;
			o.fy += fy;
			// 加速度
			double ax = o.fx * o.inv_m;
			double ay = o.fy * o.inv_m;
			ay -= G;
			// 速度
			o.vx += ax * dt;
			o.vy += ay * dt;
			// 変位
			o.px += o.vx * dt;
			o.py += o.vy * dt;

			// 壁との衝突処理
			if(o.px < (wall_min.x + o.r)){
				o.px = wall_min.x + o.r;
				double I = calcImpulse(e, o.m, m, o.vx, 0.0);
				o.vx += I * o.inv_m;
			}
			if(o.px > (wall_max.x - o.r)){
				o.px = wall_max.x - o.r;
				double I = calcImpulse(e, o.m, m, o.vx, 0.0);
				o.vx += I * o.inv_m;
			}
			if(o.py < (wall_min.y + o.r)){
				o.py = wall_min.y + o.r;
				double I = calcImpulse(e, o.m, m, o.vy, 0.0);
				o.vy += I * o.inv_m;
			}
			if(o.py > (wall_max.y - o.r)){
				o.py = wall_max.y - o.r;
				double I = calcImpulse(e, o.m, m, o.vy, 0.0);
				o.vy += I * o.inv_m;
			}
		}

		for(Shape object : objects){
			Ball o = (Ball)object;
			o.clearForce();
		}

		rdc.recursiveClustering(objects);
	}

	@Override
	protected void renderFrame(Graphics2D g2d){
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		// 背景のクリア
		g2d.clearRect(0, 0, screen_width, screen_height);

		{
			g2d.setColor(Color.WHITE);
			for(Shape object : objects){
				object.render(g2d);
			}

			if(debug){
				for(IDebugRender dr : debug_render_list){
					dr.render(g2d);
				}
			}
/*
			// Set line color
			g2d.setColor(  Color.WHITE );
			// If just one line, draw a point
			if( lines.size() == 1 ) {
				Point p = lines.get( 0 );
				if( p != null )
					g2d.drawLine( p.x, p.y, p.x, p.y );
			} else {
				// Draw all the lines
				for( int i = 0; i < lines.size()-1; ++i ) {
					Point p1 = lines.get( i );
					Point p2 = lines.get( i+1 );
					// Adding a null into the list is used
					// for breaking up the lines when
					// there are two or more lines
					// that are not connected
					if( !(p1 == null || p2 == null) )
						g2d.drawLine( p1.x, p1.y, p2.x, p2.y );
				}
			}
*/
		}
		// FPSの表示
		g2d.setFont(font);
		FontMetrics metrics = g2d.getFontMetrics();
		g2d.setColor(Color.YELLOW);


		String render_fps = String.format("%.2f", getFps());
		String update_fps = String.format("%.2f", getUps());
		g2d.drawString("fps : ups =" + render_fps + " : " + update_fps, 0, metrics.getAscent());
		//g2d.drawString("fps=" + Double.toString(getRenderFps()) + " / " + Double.toString(getUpdateFps()), 0, metrics.getAscent());
	}
}
