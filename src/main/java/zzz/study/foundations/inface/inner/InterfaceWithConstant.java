package zzz.study.foundations.inface.inner;

import java.util.Random;

public interface InterfaceWithConstant {
	
	Random random = new Random(47);
	
	double PI = 3.1415926;
	double VARIABLE = random.nextDouble(); 

}
