package zzz.study.foundations.initialization;

public class StaticInitialization {

	   StaticInitialization() {System.out.println(" StaticInitialization Constructor. ");}    
	   
	   private Dog d = new Dog("gougou");
	   private Goose g;
	   // non-static initialization block
	   {
		  
		   System.out.println(" --- Enter the non-static block ---- ");
		   g = new Goose("initialize goose in non-static block.");
		   duck = new Duck("static duck in non-static block.");
		   System.out.println(" --- Quit the non-static block ---- "); 
	   }
	   
	   private Duck myduck = new Duck("my duck");
	   
	   // static initialization block1
	   static
	   {
		   System.out.println(" --- Enter the static block1 ---- ");
	       sheep = new Sheep("miemie");
	       // Error: d = new Dog("unable to instantiate dog in static block."); //
	       System.out.println(" --- Quit the static block1 ---- "); 
	   }

	   // static member initialization
	   private static Cat cat = new Cat("mimi"); 
	   private static Sheep sheep;
	   private static Dog  dog;    
	   private static Duck  duck;
	   private static Goose goose;
	   
	   // static initialization block1
	   static {
	       System.out.println(" --- Enter the static block2 ---- ");
           cat = new Cat("miaomiao");
           sheep = new Sheep("miemie");
           System.out.println(" --- Quit the static block2 ---- "); 
	   }
	   
	   public static void f() 
	   {
		   System.out.println(" --- Enter the static method ---- "); 
		   //Error: d = new Dog("unable to instantiate dog in static block."); //
		  
		   // 可以对静态成员进行初始化
		    dog = new Dog(" Initialized static dog in static method.");   
		   
		   // 可以在静态方法中创建新的实例成员；
		   Dog d = new Dog("Instantiated new dog in static method. "); 
		 
		   System.out.println(" --- Quit the static method ---- ");   
	   }
	   
}

class Dog
{
	private String name;
	Dog() {System.out.println("Dog"); }
	Dog(String name) {this.name = name; System.out.println("Dog: " + name);}
	public String toString() {return "my name is " + name;}
}

class Cat
{
	private String name;
	Cat() {System.out.println("Cat"); }
	Cat(String name) {this.name = name;System.out.println("Cat: " + name);}
	public String toString() {return "my name is " + name;}
}

class Sheep
{
	private String name;
	Sheep() {System.out.println("Sheep"); } 
	Sheep(String name) {this.name = name; System.out.println("Sheep: " + name);}
	public String toString() {return "my name is " + name;}
}

class Duck
{
	private String name;
	Duck() {System.out.println("Duck"); } 
	Duck(String name) {this.name = name; System.out.println("Duck: " + name);}
	public String toString() {return "my name is " + name;}
}

class Goose
{
	private String name;
	Goose() {System.out.println("Goose"); } 
	Goose(String name) {this.name = name; System.out.println("Goose: " + name);}
	public String toString() {return "my name is " + name;}
}