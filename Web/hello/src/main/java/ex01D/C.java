package ex01D;
public class C
{
	public static void method1(int i, StringBuffer s)
 	{
 		i++; 
 		s.append("d");									//append will add 'd' to that stringBuffer
 	}

 	public static void main(String [] args)
 	{
 		int i = 0;
 		StringBuffer s = new StringBuffer("abc");
 		method1(i, s);									//calling method func. 
 		System.out.println("i=" + i + ", s=" + s);		//Output: i=0, s=abcd
 	}
}