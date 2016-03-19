package zzz.study.foundations.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

public class LoggingException extends Exception {
	
	private static Logger logger = Logger.getLogger("LoggingException");
	
	public LoggingException() {
		super();
		StringWriter trace = new StringWriter();
		printStackTrace(new PrintWriter(trace));
		logger.severe(trace.toString());
	}
	
	public static void logException(Exception e) {
		StringWriter trace = new StringWriter();
		e.printStackTrace(new PrintWriter(trace));
		logger.severe(trace.toString());
	}
	
	public static void f() throws LoggingException {
		System.out.println("Throw LoggingException in f()");
		throw new LoggingException();
	}
	
	public static void g() throws LoggingException {
		System.out.println("Throw LoggingException in g()");
		throw new LoggingException();
	}
	
	public static void main(String[] args) {
		
		try {
			f();
		}
		catch (LoggingException e) {
			System.err.println("Caught " + e + " from f()");
			System.err.println("栈信息:");
			for (StackTraceElement ste: e.getStackTrace()) {
				System.err.println(ste);
				
			}
		}

		
		try {
			g();
		}
		catch (LoggingException e) {
			System.err.println("Caught " + e + " from g()");
			e.printStackTrace(System.err);
		}

		
		try {
			throw new NullPointerException();
		}
		catch (NullPointerException e) {
			logException(e);
		}
		
	}
	
}
