package zzz.study.foundations.iolearn;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;

public class ImageDataReader {
	

	 public static void main(String args[]) {
		 
		  int[] rgb = new int[3];
		  
		  try {
			  
			  BufferedWriter outfile = new BufferedWriter(new FileWriter(new File("output.txt").getAbsoluteFile()));
			  File file = new File("lake.jpg");
			  BufferedImage bi=null;
			  bi = ImageIO.read(file);
			  
			  int width=bi.getWidth();
			  int height=bi.getHeight();
			  int minx=bi.getMinX();
			  int miny=bi.getMinY();
			  outfile.write("width="+width+",height="+height+"."+"\n");
			  outfile.write("minx="+minx+",miniy="+miny+"."+"\n");
		      
			  for(int i=minx;i<500;i++){
			   for(int j=miny;j<500;j++){
			    //System.out.print(bi.getRGB(jw, ih));
			    int pixel=bi.getRGB(i, j);
			    rgb[0] = (pixel & 0xff0000 ) >> 16 ;
			    rgb[1] = (pixel & 0xff00 ) >> 8 ;
			    rgb[2] = (pixel & 0xff );
			    outfile.write("("+rgb[0]+","+rgb[1]+","+rgb[2]+")");
			   }
			   outfile.write("\n");
			  }
			  
		  }
			
          catch(Exception e){
		       e.printStackTrace();
		  }
	
		  

	 }


}
