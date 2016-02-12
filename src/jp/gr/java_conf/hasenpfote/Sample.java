package jp.gr.java_conf.hasenpfote;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.EnumSet;
import java.util.logging.LogManager;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Sample{

	private static final String ROOT_DIR = System.getProperty("user.dir");
	private static final String LOGGING_PROPERTIES = "logging.properties";

	private static final int SCREEN_WIDTH = 1280;
	private static final int SCREEN_HEIGHT = 960;
	private static final int NUM_BUFFERS = 2;
	private SampleGameEngine ge = null;

	public Sample(){
		// for log
		String filePath = ROOT_DIR + File.separator + LOGGING_PROPERTIES;
		try{
			InputStream inStream = new FileInputStream(filePath);
			LogManager.getLogManager().readConfiguration(inStream);
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		//
		JFrame frame = new JFrame("Recursive Dimensional Clustering");
		frame.setLayout(null);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setBounds(0, 0, SCREEN_WIDTH + frame.getInsets().left + frame.getInsets().right, SCREEN_HEIGHT + frame.getInsets().top + frame.getInsets().bottom);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e){
				if(ge != null){
					ge.shutdown();
				}
			}
		});

		JPanel panel = (JPanel)frame.getContentPane();
		panel.setLayout(null);
		panel.setIgnoreRepaint(true);

		Canvas canvas = new Canvas();
		canvas.setBackground(Color.BLACK);
		canvas.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		canvas.setIgnoreRepaint(true);

		panel.add(canvas);
		canvas.createBufferStrategy(NUM_BUFFERS);

		EnumSet<SampleGameEngine.InputDevice> flag = EnumSet.allOf(SampleGameEngine.InputDevice.class);
		ge = new SampleGameEngine(canvas, flag);

		// set listener
		frame.addKeyListener(ge.getKeyListener());
		canvas.addKeyListener(ge.getKeyListener());

		frame.addMouseListener(ge.getMouseListener());
		canvas.addMouseListener(ge.getMouseListener());

		frame.addMouseMotionListener(ge.getMouseMotionListener());
		canvas.addMouseMotionListener(ge.getMouseMotionListener());

		frame.addMouseWheelListener(ge.getMouseWheelListener());
		canvas.addMouseWheelListener(ge.getMouseWheelListener());

		ge.boot();
	}

	public static void main(String[] args){
		Sample sample = new Sample();
	}
}
