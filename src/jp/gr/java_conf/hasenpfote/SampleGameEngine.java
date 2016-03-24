package jp.gr.java_conf.hasenpfote;

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
import jp.gr.java_conf.hasenpfote.math.Vector2f;
import jp.gr.java_conf.hasenpfote.physics.Physics;


public final class SampleGameEngine extends GameEngine{

	private static final int DEFAULT_FPS = 60;
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

	private static final int NUM_OBJECTS = 50;
	private final ArrayList<CircularPlate> objects = new ArrayList<>();

	private Point2D.Float wall_min = null;
	private Point2D.Float wall_max = null;

	private boolean gravity_enabled = false;

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
		logger.info("initialize start.");
		GameSystem.getInstance().setScreenSize(screen_width, screen_height);
		//GameSystem.getInstance().setPixelsPerUnit((int)PIXELS_PER_METER);
		GameSystem.getInstance().updateMarix();

		wall_min = new Point2D.Float();
		wall_max = new Point2D.Float();
		Point2D.Float min = new Point2D.Float(0.0f, screen_height);
		Point2D.Float max = new Point2D.Float(screen_width, 0.0f);
		AffineTransform wtos = GameSystem.getInstance().getWorldToScreenMatrix();
		try{
			wtos.inverseTransform(min, wall_min);
			wtos.inverseTransform(max, wall_max);
		}catch(NoninvertibleTransformException e){
		}
		float hw = (wall_max.x - wall_min.x) * 0.5f;
		float hh = (wall_max.y - wall_min.y) * 0.5f;

		CircularPlateInputComponent cp_ip = new CircularPlateInputComponent();
		CircularPlatePhysicsComponent cp_pc = new CircularPlatePhysicsComponent();
		CircularPlateRenderComponent cp_rc = new CircularPlateRenderComponent();

		for(int i = 0; i < NUM_OBJECTS; i++){
			CircularPlate cp = new CircularPlate(cp_ip, cp_pc, cp_rc);
			float radius = rnd.nextFloat() * 0.5f + 0.5f;
			cp.setRadius(radius);
			cp.getPosition().set((rnd.nextFloat() * 2.0f - 1.0f) * (hw - radius),
								 (rnd.nextFloat() * 2.0f - 1.0f) * (hh - radius));
			cp.getLinearVelocity().set((rnd.nextFloat() - 0.5f) * 2.0f * 10.0f + 10.0f,
									   (rnd.nextFloat() - 0.5f) * 2.0f * 10.0f + 10.0f);
			cp.setMass((float)Math.PI * cp.getRadius() * cp.getRadius());

			objects.add(cp);
		}
		logger.info("initialize end.");
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

		if(keyboard.isKeyDownOnce(KeyEvent.VK_F2))
			gravity_enabled = !gravity_enabled;

		//
		if(keyboard.isKeyDownOnce(KeyEvent.VK_F3)){
			float wall_width = wall_max.x - wall_min.x;
			float wall_height = wall_max.y - wall_min.y;
			for(CircularPlate cp : objects){
				cp.getPosition().set(0.0f, (rnd.nextFloat() - 0.5f) * wall_height);
				cp.getLinearVelocity().set(0.0f, 0.0f);
				//cp.getPosition().set((rnd.nextDouble() - 0.5) * wall_width, (rnd.nextDouble() - 0.5) * wall_height);
			}
		}
		if(keyboard.isKeyDownOnce(KeyEvent.VK_F4)){
			float wall_width = wall_max.x - wall_min.x;
			float wall_height = wall_max.y - wall_min.y;
			CircularPlate cp = objects.get(0);
			cp.getPosition().set(-wall_width * 0.25f, 0.0f);
			cp.getLinearVelocity().set(100.0f, 0.0f);

			cp = objects.get(1);
			cp.getPosition().set(wall_width * 0.25f, 0.0f);
			cp.getLinearVelocity().set(-100.0f, 0.0f);
		}
		//
		for(CircularPlate cp : objects){
			cp.updateInputComponent(keyboard);
		}
	}

	private final Vector2fPool vector2d_pool = new Vector2fPool();
	private final CollisionPairPool cpair_pool = new CollisionPairPool(1000);
	private final ArrayList<CollisionPair> cpairs = new ArrayList<>(1000);

	@Override
	protected void updateFrame(double dt){
		if(pause)
			return;

		final float cd = 1.0f / (float)dt * 0.5f;	// 0 < cd < 1/Δt

		// recursive dimensional clustering
		rdc.recursiveClustering(objects);

		// collision detection
		Vector2f rp = vector2d_pool.allocate();	// relative position
		Vector2f rv = vector2d_pool.allocate();	// relative velocity

		ArrayList<Rdc.Cluster> clusters = rdc.getClusters();
		for(Rdc.Cluster cluster : clusters){
			ArrayList<CircularPlate> group = cluster.getGroup();
			// brute force
			int size = group.size();
			for(int i = 0; i < (size-1); i++){
				CircularPlate first = group.get(i);
				float radius1 = first.getRadius();
				for(int j = (i+1); j < size; j++){
					CircularPlate second = group.get(j);
					float radius2 = second.getRadius();
					rp.subtract(second.getPosition(), first.getPosition());
					rv.subtract(second.getLinearVelocity(), first.getLinearVelocity());
					if(rp.inner(rv) >= 0.0f)
						continue;
					if(rp.length_squared() <= (radius1 + radius2) * (radius1 + radius2)){
						CollisionPair cpair = cpair_pool.allocate();
						cpair.set(first, second);
						cpairs.add(cpair);
					}
				}
			}
		}
		vector2d_pool.release(rp);

		Vector2f impulse = vector2d_pool.allocate();
		Vector2f normal = vector2d_pool.allocate();

		for(CollisionPair cpair : cpairs){
			CircularPlate first = cpair.getFirst();
			CircularPlate second = cpair.getSecond();

			normal.subtract(second.getPosition(), first.getPosition());
			float d = (first.getRadius() + second.getRadius()) - normal.length();
			normal.normalize();
			Physics.calcImpulse(impulse, normal, 0.5f, first.getMass(), second.getMass(), first.getLinearVelocity(), second.getLinearVelocity(), cd, d);

			first.getLinearVelocity().madd(impulse, first.getInvMass());
			second.getLinearVelocity().msub(impulse, second.getInvMass());

			cpair_pool.release(cpair);
		}
		cpairs.clear();

		//
		float e = 0.25f;	// 反発係数
		float mass = 1000.0f;
		for(CircularPlate cp : objects) {
			Vector2f position = cp.getPosition();
			float radius = cp.getRadius();

			if(position.x <= (wall_min.x + radius)){
				Vector2f linear_velocity = cp.getLinearVelocity();
				rv.negate(linear_velocity);
				normal.negate(Vector2f.E1);
				if(normal.inner(rv) < 0.0f){
					float d = radius - Math.abs(position.x - wall_min.x);
					Physics.calcImpulse(impulse, normal, e, cp.getMass(), mass, linear_velocity, Vector2f.ZERO, cd, d);
					linear_velocity.madd(impulse, cp.getInvMass());
				}
			}else if(position.x >= (wall_max.x - radius)){
				Vector2f linear_velocity = cp.getLinearVelocity();
				rv.negate(linear_velocity);
				normal.set(Vector2f.E1);
				if(normal.inner(rv) < 0.0f){
					float d = radius - Math.abs(position.x - wall_max.x);
					Physics.calcImpulse(impulse, normal, e, cp.getMass(), mass, linear_velocity, Vector2f.ZERO, cd, d);
					linear_velocity.madd(impulse, cp.getInvMass());
				}
			}
			if(position.y <= (wall_min.y + radius)){
				Vector2f linear_velocity = cp.getLinearVelocity();
				rv.negate(linear_velocity);
				normal.negate(Vector2f.E2);
				if(normal.inner(rv) < 0.0f){
					float d = radius - Math.abs(position.y - wall_min.y);
					Physics.calcImpulse(impulse, normal, e, cp.getMass(), mass, linear_velocity, Vector2f.ZERO, cd, d);
					linear_velocity.madd(impulse, cp.getInvMass());
				}
			}else if(position.y >= (wall_max.y - radius)){
				Vector2f linear_velocity = cp.getLinearVelocity();
				rv.negate(linear_velocity);
				normal.set(Vector2f.E2);
				if(normal.inner(rv) < 0.0f){
					float d = radius - Math.abs(position.y - wall_max.y);
					Physics.calcImpulse(impulse, normal, e, cp.getMass(), mass, linear_velocity, Vector2f.ZERO, cd, d);
					linear_velocity.madd(impulse, cp.getInvMass());
				}
			}
		}

		vector2d_pool.release(rv);
		vector2d_pool.release(impulse);
		vector2d_pool.release(normal);

		// Integrate
		float g = (gravity_enabled)? Physics.G : 0.0f;
		Vector2f linear_acceleration = vector2d_pool.allocate();
		for(CircularPlate cp : objects) {
			float inv_mass = cp.getInvMass();
			// acceleration
			Vector2f force = cp.getForce();
			linear_acceleration.multiply(force, inv_mass);
			linear_acceleration.y -= g;
			// velocity
			Vector2f linear_velocity = cp.getLinearVelocity();
			linear_velocity.madd(linear_acceleration, (float)dt);
			// position
			Vector2f position = cp.getPosition();
			position.madd(linear_velocity, (float)dt);
			// clear force
			force.set(Vector2f.ZERO);
		}

		vector2d_pool.release(linear_acceleration);


		/////
/*
		double e = 0.25;	// 反発係数
		double m = 1000.0;
		Vector2d linear_acceleration = vector2d_pool.allocate();
		Vector2d impulse = vector2d_pool.allocate();

		for(jp.gr.java_conf.hasenpfote.CircularPlate cp : objects){
			//cp.updatePhysicsComponent(dt);
			double inv_mass = cp.getInvMass();
			// acceleration
			Vector2d force = cp.getForce();
			linear_acceleration.mul(force, inv_mass);
			linear_acceleration.y -= G;
			// velocity
			Vector2d linear_velocity = cp.getLinearVelocity();
			linear_velocity.madd(linear_acceleration, dt);
			// position
			Vector2d position = cp.getPosition();
			position.madd(linear_velocity, dt);

			// collision for wall
			double radius = cp.getRadius();
			double mass = cp.getMass();
			if(position.x < (wall_min.x + radius)){
				position.x = wall_min.x + radius;
				double I = calcImpulse(e, mass, m, linear_velocity.x, 0.0);
				linear_velocity.x += I * inv_mass;
				//calcImpulse(impulse, e, mass, m, linear_velocity, Vector2d.ZERO);
				//linear_velocity.madd(impulse, inv_mass);
			}
			if(position.x > (wall_max.x - radius)){
				position.x = wall_max.x - radius;
				double I = calcImpulse(e, mass, m, linear_velocity.x, 0.0);
				linear_velocity.x += I * inv_mass;
				//calcImpulse(impulse, e, mass, m, linear_velocity, Vector2d.ZERO);
				//linear_velocity.madd(impulse, inv_mass);
			}
			if(position.y < (wall_min.y + radius)){
				position.y = wall_min.y + radius;
				double I = calcImpulse(e, mass, m, linear_velocity.y, 0.0);
				linear_velocity.y += I * inv_mass;
				//calcImpulse(impulse, e, mass, m, linear_velocity, Vector2d.ZERO);
				//linear_velocity.madd(impulse, inv_mass);
			}
			if(position.y > (wall_max.y - radius)){
				position.y = wall_max.y - radius;
				double I = calcImpulse(e, mass, m, linear_velocity.y, 0.0);
				linear_velocity.y += I * inv_mass;
				//calcImpulse(impulse, e, mass, m, linear_velocity, Vector2d.ZERO);
				//linear_velocity.madd(impulse, inv_mass);
			}
			// clear force
			force.set(Vector2d.ZERO);
		}
		vector2d_pool.release(linear_acceleration);
		vector2d_pool.release(impulse);
*/
	}

	@Override
	protected void renderFrame(Graphics2D g2d){
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setFont(font);
		// 背景のクリア
		g2d.clearRect(0, 0, screen_width, screen_height);
		// FPSの表示
		FontMetrics metrics = g2d.getFontMetrics();
		g2d.setColor(Color.YELLOW);
		String render_fps = String.format("%.2f", getFps());
		String update_fps = String.format("%.2f", getUps());
		g2d.drawString("fps : ups =" + render_fps + " : " + update_fps, 0, metrics.getAscent());
		//g2d.drawString("fps=" + Double.toString(getRenderFps()) + " / " + Double.toString(getUpdateFps()), 0, metrics.getAscent());
		g2d.drawString("gravity: " + ((gravity_enabled)? "On" : "Off"), 0, metrics.getHeight() + metrics.getAscent());
		{
			g2d.setColor(Color.WHITE);
			for(CircularPlate cp : objects){
				cp.updateRenderComponent(g2d);
			}
			if(debug){
				rdc_rc.update(rdc, g2d);
			}
		}
	}
}
