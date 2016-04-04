package jp.gr.java_conf.hasenpfote;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hasenpfote on 2016/04/04.
 */
public final class DebugStrings{

	private static class LazyHolder{
		private static final DebugStrings INSTANCE = new DebugStrings();
	}

	private DebugStrings(){
	}

	public static DebugStrings getInstance(){
		return LazyHolder.INSTANCE;
	}

	public static final DecimalFormat FP_FORMAT = new DecimalFormat("0.00");
	public static final DecimalFormat MEM_FORMAT= new DecimalFormat("#,###KB");
	public static final DecimalFormat PERCENT_FORMAT = new DecimalFormat("##0.00%");

	private static final Font font = new Font(Font.DIALOG, Font.PLAIN, 18);
	private List<String> list = new ArrayList<>();

	public void add(String str){
		list.add(str);
	}

	public void clear(){
		list.clear();
	}

	public void render(Graphics2D g2d){
		if(list.isEmpty())
			return;
		final Color oldColor = g2d.getColor();
		final Font  oldFont = g2d.getFont();
		g2d.setColor(Color.YELLOW);
		g2d.setFont(font);
		final FontMetrics metrics = g2d.getFontMetrics();
		final int height = metrics.getHeight();
		final int ascent = metrics.getAscent();
		int row = 0;
		for(String str : list){
			g2d.drawString(str, 0, row * height + ascent);
			row++;
		}
		g2d.setFont(oldFont);
		g2d.setColor(oldColor);
	}
}
