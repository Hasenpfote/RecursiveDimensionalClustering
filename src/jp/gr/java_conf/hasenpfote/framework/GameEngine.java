package jp.gr.java_conf.hasenpfote.framework;

import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;


/**
 * メインループ.
 * @author Hasenpfote
 */
public abstract class GameEngine implements Runnable{

	// for log
	protected final Logger logger;

	/** 1秒[ns] */
	private static final long ONE_SECOND = (long)1E9;

	/** 1frameの期間[ns] */
	private final double FRAME_PERIOD;
	private final double UPDATE_PERIOD;

	private BufferStrategy buffer = null;
	public Thread thread = null;
	private volatile boolean active;
	private double fps = 0.0;
	private double ups = 0.0;

	static{
	}

	/**
	 * コンストラクタ.
	 * @param fps default fps
	 */
	public GameEngine(int fps, BufferStrategy buffer){
		FRAME_PERIOD = (double)ONE_SECOND / fps;
		UPDATE_PERIOD = FRAME_PERIOD * 0.5;
		this.buffer = buffer;
		active = true;
		thread = new Thread(this);
		logger = Logger.getLogger(this.getClass().getName());
	}

	protected final double getFps(){ return fps; }
	protected final double getUps(){ return ups; }

	/**
	 * 起動.
	 * @return 成否
	 */
	public final boolean boot(){
		if((thread == null) || (thread.isAlive()))
			return false;
		logger.info("boot.");
		thread.start();
		return true;
	}

	/**
	 * 終了.
	 */
	public final void shutdown(){
		if((thread == null) || (!thread.isAlive()))
			return;
		logger.info("shutdown.");
		active = false;
		thread.interrupt();
		try{
			thread.join();
		}catch (InterruptedException e){
		}
		thread = null;
	}

	/**
	 * メインループ.
	 */
	@Override
	public final void run(){
		try{
			initialize();

			long previous;
			double lag = 0.0;
			long sleep_error = 0;
			long frame_count = 0;
			long update_count = 0;
			long measure_start;

			previous = System.nanoTime();
			measure_start = previous;

			while(active){

				long current = System.nanoTime();
				lag += (double)(current - previous);
				previous = current;

				// fps の算出
				long elapsed = System.nanoTime() - measure_start;
				if(elapsed >= ONE_SECOND){
					double correction_value = (double)ONE_SECOND / elapsed;
					fps = (double)frame_count * correction_value;
					ups = (double)update_count * correction_value;
					frame_count = update_count = 0;
					measure_start = System.nanoTime();
				}

				processInput();

				while(lag >= UPDATE_PERIOD){
					updateFrame(UPDATE_PERIOD / (double)ONE_SECOND);
					lag -= UPDATE_PERIOD;
					update_count++;
				}
				present();
				frame_count++;

				long end = System.nanoTime();
				long sleep = (long)FRAME_PERIOD - (end - previous) - sleep_error;
				if(sleep > 0){
					TimeUnit.NANOSECONDS.sleep(sleep);
					// sleepの誤差は次フレームで吸収
					sleep_error = (System.nanoTime() - end) - sleep;
				}
				else{
					sleep_error = 0;
				}
			}
		}
		catch(InterruptedException e){
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			cleanup();
		}

		/*
		try{
			long begin, end, last, sleep, dispersion = 0;
			long prev, cur;
			int frame = 0;
			boolean skip = false;

			initialize();
			prev = last = begin = System.nanoTime();
			while(active){
				processInput();
				updateFrame((double)(begin - last) / ONE_SECOND);
				if(!skip)
					present();
				//
				end = System.nanoTime();
				sleep = PERIOD - (end - begin) - dispersion;
				if(sleep > 0){
					TimeUnit.NANOSECONDS.sleep(sleep);
					// sleepのばらつきは次フレームで吸収
					dispersion = System.nanoTime() - end - sleep;
					skip = false;
				}
				else{
					dispersion = 0;
					skip = true;	// 間に合わないので次フレームの描画を飛ばす.
					System.out.println("skip the next frame.");
				}
				// fpsの算出
				frame++;
				cur = System.nanoTime();
				if((cur - prev) >= ONE_SECOND){
					fps = (double)frame / (cur - prev) * ONE_SECOND;
					prev = cur;
					frame = 0;
				}
				//
				last = begin;
				begin = System.nanoTime();
			}
		}
		catch(InterruptedException e){

		}
		finally{
			cleanup();
		}
		*/
		logger.info("bye.");
	}

	/**
	 * スクリーンへの描画処理.
	 */
	private final void present(){
		Graphics2D g2d = (Graphics2D)buffer.getDrawGraphics();
		renderFrame(g2d);
		if(!buffer.contentsLost())
			buffer.show();
		Toolkit.getDefaultToolkit().sync();
		g2d.dispose();
	}

	/**
	 * 初期化処理.
	 */
	protected abstract void initialize();

	/**
	 * 破棄処理.
	 */
	protected abstract void cleanup();

	/**
	 * 入力処理.
	 */
	protected abstract void processInput();

	/**
	 * フレームの更新処理.
	 * @param dt 直前のフレームからの経過時間[sec]
	 */
	protected abstract void updateFrame(double dt);

	/**
	 * フレームの描画処理.
	 * @param g2d
	 */
	protected abstract void renderFrame(Graphics2D g2d);
}
