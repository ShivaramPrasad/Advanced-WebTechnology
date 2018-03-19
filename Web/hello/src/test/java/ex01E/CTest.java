package ex01E;

import org.apache.log4j.*; 
import junit.framework.Assert.*;
import junit.framework.Test; 
import junit.framework.TestCase; 
import junit.framework.TestSuite; 


public class CTest extends TestCase
{  
	final static Logger log =  Logger.getLogger(CTest.class);
	
	public CTest( String testName )   
  	{       
    	super( testName );    
  	} 

  	public static Test suite()    
  	{             
    	return new TestSuite( CTest.class );   
  	} 

  	public void testApp() throws Exception   
	{
		C obj = new C();
		if(log.isDebugEnabled()){
			try{
				log.debug("Debug is Enabled");
				obj.test();
				log.info("AssertTrue");
			}
			catch(Exception e){
				log.warn(e.getMessage(), e);
			}
		}
		
	}
}