package ex01E;

public class C extends B
{
	int x, a;
	void m() 
	{
		System.out.println("Je suis dans la méthode m d'une instance de A");
 	}
 	
	public void test() 
	{
    	a = super.x;
    	//a = super.super.x; 
    	a = ((B)this).x;
    	a = ((A)this).x;
    	super.m();				  /*this will indicates the parent class like where it comes from*/
    	//super.super.m();
    	((B)this).m(); // (1)    /*Mainly this keyword is for reffering the current class object*/
	}
 
	public static void main(String args[])
	{
		C obj = new C();
		obj.test();
		 
	}

}
