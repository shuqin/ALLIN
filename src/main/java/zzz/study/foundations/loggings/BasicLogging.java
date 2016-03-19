package zzz.study.foundations.loggings;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class BasicLogging {
	
	public static void main(String[] args) 
	{
		try 
		{
			LogManager lm = LogManager.getLogManager();
			Logger logger = Logger.getLogger("BasicLogging");
			FileHandler fh = new FileHandler("log_test.txt");
			lm.addLogger(logger);
			logger.addHandler(fh);
			logger.log(Level.INFO, "test 1");
			logger.log(Level.INFO, "test 2");
			fh.close();
		}
		catch (Exception e) {
			System.err.println("Exception thrown: " + e);
			e.printStackTrace();
		}
	}

}
