package zzz.study.foundations.exception;
//: exception/StormyInning.java
// Overridden methods may throw only the exceptions
// specified in their base-class versions, or exceptions
// derived from the base-class exceptions.

class BaseballException extends Exception {}
class Foul extends BaseballException {}
class Strike extends BaseballException {}

abstract class Inning {
  public Inning() throws BaseballException, EatingFishException {}
  public void event() throws BaseballException {
    // Doesn't actually have to throw anything
  }
  public abstract void atBat() throws Strike, Foul;
  public void walk() {} // Throws no checked exceptions
  public void run() throws EatingFishException {}
}

class StormException extends Exception {}
class RainedOut extends StormException {}
class PopFoul extends Foul {}

interface Storm {
  public void event() throws RainedOut;
  public void rainHard() throws RainedOut;
}

public class StormyInning extends Inning implements Storm {
	
  // OK to add new exceptions for constructors, but you
  // must deal with the base constructor exceptions:
     public StormyInning() throws RainedOut, BaseballException, EatingFishException {}
     public StormyInning(String msg) throws Foul, BaseballException, EatingFishException {}
     
  // 没有包含基类构造器的异常声明 BaseballException 
  //! public StormyInning() throws RainedOut, EatingFishException {}
     
  // Regular methods must conform to base class:
  //! public void walk() throws PopFoul {} //Compile error
        
  // Interface CANNOT add exceptions to existing
  // methods from the base class:
//! public void event() throws RainedOut {}
     
  // If the method doesn't already exist in the
  // base class, the exception is OK:
  public void rainHard() throws RainedOut {}
  
  // You can choose to not throw any exceptions,
  // even if the base version does:
  public void event()  {}
  
  // Overridden methods can throw inherited exceptions:
  public void atBat() throws PopFoul { 
	  throw new PopFoul();
  }
  
  public static void main(String[] args) {
	  
    try {
      StormyInning si = new StormyInning();
      si.atBat();
    } catch(PopFoul e) {
      System.out.println("Pop foul");
    } catch(RainedOut e) {
      System.out.println("Rained out");
    } catch(BaseballException e) {
      System.out.println("Generic baseball exception");
    } catch(EatingFishException e) {
      System.out.println("my exception");
    }
    // Strike not thrown in derived version.
    try {
      // What happens if you upcast?
      Inning i = new StormyInning();
      i.atBat();
      // You must catch the exceptions from the
      // base-class version of the method:
    } catch(Strike e) {
      System.out.println("Strike");
    } catch(Foul e) {
      System.out.println("Foul");
    } catch(RainedOut e) {
      System.out.println("Rained out");
    } catch(BaseballException e) {
      System.out.println("Generic baseball exception");
    } catch(EatingFishException e) {
      System.out.println("my exception");
    }
  }
} ///:~


/*
 * Note: 关于继承的异常语法
 * 
 * 1. 派生类的方法的异常说明列表：
 * 
 *    ①  构造器： 是基类异常说明列表的超集；
 *    即：若基类构造器的异常说明列表为 BS = {A,B,C}，
 *    则 派生类构造器的异常说明列表 DS = BS ∪ {otherExceptions} 
 *    
 *    ②  普通覆盖方法：是 基类异常说明列表的子集；
 *    若基类的异常说明列表为BS = {A,B,C}，
 *    则派生类的异常说明列表 DS = {},{A},{B},{c},{A,B},{A,C},{B,C},{A,B,C}
 *    
 * 2. 派生类实现接口中的方法时, 其异常说明列表为基类异常说明列表的子集。 
 *  
 * 3. 异常说明不是方法类型的一部分，不能基于异常说明来重载方法；
 * 
 * 
 */
