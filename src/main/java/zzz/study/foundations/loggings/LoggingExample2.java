package zzz.study.foundations.loggings;

import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.XMLFormatter;

public class LoggingExample2 {
	
	public static void main(String[] args) 
	{
		try 
		{
			LogManager lm = LogManager.getLogManager();
			Logger parentLogger, childLogger;
			FileHandler xmlHandler = new FileHandler("log_output.xml");
			FileHandler htmlHandler = new FileHandler("log_output.html");
			parentLogger = Logger.getLogger("ParentLogger");
			childLogger = Logger.getLogger("ParentLogger.ChildLogger");
			
			lm.addLogger(parentLogger);
			lm.addLogger(childLogger);
			parentLogger.setLevel(Level.WARNING);
			childLogger.setLevel(Level.ALL);
			xmlHandler.setFormatter(new XMLFormatter());
			htmlHandler.setFormatter(new HTMLFormatter());
			
			parentLogger.addHandler(xmlHandler);
			childLogger.addHandler(htmlHandler);
			
			childLogger.log(Level.FINE, "This is a fine log message");
			childLogger.log(Level.SEVERE, "This is a severe log message");
			xmlHandler.close();
			htmlHandler.close();
		}
		catch (Exception e) {
			System.err.println("Exception thrown: " + e);
			e.printStackTrace();
		}
	}
	

}

class HTMLFormatter extends java.util.logging.Formatter {
	
	public String format(LogRecord record)
	{
		return ("<tr><td>" + (new Date(record.getMillis())).toString() + "</td>" +
				"<td>" + record.getMessage() + "</td></tr>\n");
	}
	
	public String getHead(Handler h) 
	{
		return ("<html>\n<body>\n<table border>\n<tr><th>Time</th><th>Log Message</th></tr>\n");
	}
	
	public String getTail(Handler h)
	{
		return ("</table>\n</body></html>");
	}
}
