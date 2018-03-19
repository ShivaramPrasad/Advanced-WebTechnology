package ex01D; 

import org.apache.log4j.*; 
import junit.framework.Assert.*;
import junit.framework.Test; 
import junit.framework.TestCase; 
import junit.framework.TestSuite; 

 
/* Unit test for simple App.  */ 

public class CTest extends TestCase
{  
   /**      * Create the test case      *      
   * @param testName name of the test case */

  final static Logger log =  Logger.getLogger(CTest.class); 
  public CTest( String testName )   
  {       
    super( testName );    
  } 

   /* @return the suite of tests being tested */   
 
  public static Test suite()    
  {       
    //System.out.println("COUCOU");      
    return new TestSuite( CTest.class );   
  } 
 
    /* Rigourous Test :-) */   
	
  public void testApp() throws Exception  
	{         
    int i = 0;
    StringBuffer s = new StringBuffer("abc");
    C.method1(i,s);
    log.debug("Method call");
    System.out.println("i="+i);
    assertEquals(i,0);
    System.out.println("s="+s);
    try
    {
      assertEquals(s.toString(),"abcd");
      log.info("AssertTrue");
    }
    catch(Exception e)
    {
      log.warn("AssertFalse", e);
    }

    assertTrue(true);
	}
} 