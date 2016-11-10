package zzz.study.datastructure.vector;

/**
 * 实现 n 维位向量 
 *
 */
public class NBitsVector {
	
	 private static final int BITS_PER_INT = 32;
	 private static final int SHIFT = 5;
	
	 // 将一个整型数组中的所有整数的位串联成一个位向量
	 private int[] bitsVector;
	 
	 // 位向量的总位数
	 private long bitsLength;
	 
	 public NBitsVector(int n) {
		 int i = 1;
		 // 考虑到左移位可能导致溢出, 从而陷入死循环
		 while ((i<<SHIFT) > 0 && (i<<SHIFT) < n) {
			 i++;
		 }
		 this.bitsLength = i * (long)BITS_PER_INT;
		 if (bitsVector == null) {
			 bitsVector = new int[i];
		 }
		 
	 }
	 
	 /**
	  * setBit: 将位向量的第 i 位置一
	  * @param i  要置位的位置
	  */
	 public void setBit(int i) {
		 bitsVector[i >> SHIFT] |= 1 << (i & 0x1f);
	 }
	 
	 /**
	  * clrBit: 将位向量的第 i 位清零
	  * @param i 要清零的位置
	  */
	 public void clrBit(int i) {
		 bitsVector[i >> SHIFT] &= ~(1 << (i & 0x1f));
	 }
	 
	 /**
	  * testBit: 测试位向量的第 i 位是否为 1
	  * @param i 测试位的位置
	  * @return 若位向量的第 i 位为 1, 则返回true, 否则返回 false
	  */
     public boolean testBit(int i) {
    	 return (bitsVector[i >> SHIFT] & 1 << (i & 0x1f)) != 0;
     }
     
     
     /**
      * clr: 位向量全部清零
      */
     public void clr() {
    	int vecLen = bitsVector.length;
    	for (int i = 0; i < vecLen; i++) {
    		bitsVector[i] = 0;
    	}
     }
     
     /**
      * getBitsLength: 获取位向量的总位数
      */
     public long getBitsLength() {
		return bitsLength;
	}

	/**
      * 获取给定整数 i 的二进制表示， 若高位若不为 1 则补零。 
      * @param i 给定整数 i
      */
     public String intToBinaryStringWithHighZero(int i) {
    	 String basicResult = Integer.toBinaryString(i); 
    	 int bitsForZero = BITS_PER_INT - basicResult.length();
    	 StringBuilder sb =  new StringBuilder("");
    	 while (bitsForZero-- > 0) {
    		 sb.append('0');
    	 }
    	 sb.append(basicResult);
    	 return sb.toString();
     }
     
     public String toString() {
    	 StringBuilder sb = new StringBuilder("");
    	 for (int i = bitsVector.length-1; i >=0 ; i--) {
    		 sb.append(intToBinaryStringWithHighZero(bitsVector[i]));
    		 sb.append(" ");
    	 }
    	 return sb.toString();
     }
     
     public static void main(String[] args) 
     {
    	 NBitsVector nbitsVector = new NBitsVector((int)Math.pow(2,16));
    	 nbitsVector.setBit(2);
    	 System.out.println(nbitsVector);
    	 nbitsVector.setBit(7);
    	 nbitsVector.setBit(18);
    	 nbitsVector.setBit(25);
    	 nbitsVector.setBit(36);
    	 nbitsVector.setBit(49);
    	 nbitsVector.setBit(52);
    	 nbitsVector.setBit(63);
    	 System.out.println(nbitsVector);
    	 nbitsVector.clrBit(36);
    	 nbitsVector.clrBit(35);
    	 System.out.println(nbitsVector);
    	 System.out.println("52: " + nbitsVector.testBit(52));
    	 System.out.println("42: " + nbitsVector.testBit(42));
    	 nbitsVector.clr();
    	 System.out.println(nbitsVector);
     }
	 
}
