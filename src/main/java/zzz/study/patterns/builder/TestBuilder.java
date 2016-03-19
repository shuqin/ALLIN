package zzz.study.patterns.builder;

public class TestBuilder {
	
	public static void main(String[] args) {
		
		String sample = "Date, November 5, HeadCount, 50, City, Springfield, " +
		           "YuanPerHead, 25.0, HasSite, False";
		
		try {	
			  System.out.println("UnforgivingBuilder: ");
			  ReservationBuilder unforgivingbuilder = new UnforgivingBuilder();
			  new ReservationParser(unforgivingbuilder).parser(sample);
			  Reservation r = unforgivingbuilder.build();
			  System.out.println(r);
			  
			  
			  
	    } catch (ReservationException e) {
		      System.out.println(e.getMsg());
	    } catch (Exception e) {
	    	  e.printStackTrace();
	    }
	    
	    System.out.println("--------------------------- ");
	    
	    try {	
	    	  System.out.println("ForgivingBuilder: ");
			  ReservationBuilder forgivingbuilder = new ForgivingBuilder();
			  new ReservationParser(forgivingbuilder).parser(sample);
			  Reservation r2 = forgivingbuilder.build();
			  System.out.println(r2);		  
			  
	    } catch (ReservationException e) {
		      System.out.println(e.getMsg());
	    } catch (Exception e) {
	    	  e.printStackTrace();
	    }
	      
		
	}

}
