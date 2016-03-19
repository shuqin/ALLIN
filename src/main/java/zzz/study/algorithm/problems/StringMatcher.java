package zzz.study.algorithm.problems;

public class StringMatcher {
	
	public static int simpleMatch(String given, String pattern)
	{
		int traverIndex = 0;
		int givenIndex = 0;
		int givenLen = given.length();
		int patLen = pattern.length();
		if (patLen > givenLen) {
			return -1;
		}
		
		for (givenIndex=0; givenIndex < givenLen;) { 
			for (traverIndex=0; traverIndex < patLen; traverIndex++) {
				if (given.charAt(traverIndex+givenIndex) != pattern.charAt(traverIndex)) {
					givenIndex++; 
					break;
				}
			}
			if (traverIndex == patLen) break;				
		}
		if (givenIndex == givenLen) return -1;
		return givenIndex; 
	}

}
