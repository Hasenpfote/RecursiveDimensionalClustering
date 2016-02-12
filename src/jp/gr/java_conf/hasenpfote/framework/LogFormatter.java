package jp.gr.java_conf.hasenpfote.framework;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * Created by Hasenpfote on 2016/02/12.
 */
public class LogFormatter extends Formatter{

	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");

	@Override
	public String format(LogRecord record){
		final StringBuffer sb = new StringBuffer();

		sb.append(sdf.format(new Date(record.getMillis())));
		sb.append(" ");

		final int value = record.getLevel().intValue();
		if(value == Level.FINEST.intValue()){
			sb.append("FINEST ");
		}
		else
		if(value == Level.FINER.intValue()){
			sb.append("FINER  ");
		}
		else
		if(value == Level.FINE.intValue()){
			sb.append("FINE   ");
		}
		else
		if(value == Level.CONFIG.intValue()){
			sb.append("CONFIG ");
		}
		else
		if(value == Level.INFO.intValue()){
			sb.append("INFO   ");
		}
		else
		if(value == Level.WARNING.intValue()){
			sb.append("WARNING");
		}
		else
		if(value == Level.SEVERE.intValue()){
			sb.append("SEVERE ");
		}
		else{
			sb.append(Integer.toString(value));
			sb.append(" ");
		}

		sb.append(" ");
		sb.append(record.getLoggerName());
		sb.append(" - ");
		sb.append(record.getMessage());
		sb.append("\n");

		return sb.toString();
	}
}
