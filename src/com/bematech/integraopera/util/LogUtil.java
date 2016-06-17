package com.bematech.integraopera.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

public class LogUtil {

	private Logger log = Logger.getLogger(LogUtil.class.getName());
	private static LogUtil instance;
	private LogUtilInterface logUtilInterface;
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private boolean isDebug = false;
	
	public LogUtilInterface getLogUtilInterface() {
		return logUtilInterface;
	}

	public void setLogUtilInterface(LogUtilInterface logUtilInterface) {
		this.logUtilInterface = logUtilInterface;
	}

	private LogUtil() {
	}

	public static LogUtil getInstance() {
		if (instance == null) {
			instance = new LogUtil();
		}
		return instance;
	}
	
	public void logMensagem(String msg) {
		if (logUtilInterface != null) {
			logUtilInterface.logarMensagem(df.format(new Date()) + " - " + msg);
		}
		log.info(msg);
		if (isDebug) {
			System.out.println(msg);
		}
	}
	
	public void logErro(String msg) {
		if (logUtilInterface != null) {
			logUtilInterface.logarErro(df.format(new Date()) + " - " + msg);
		}
		log.error(msg);
		System.err.println(msg);
	}
	
}
