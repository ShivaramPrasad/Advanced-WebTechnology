package jdbc;
import java.sql.*; 

/*
 * import java.sql package
 * load and register the driver -->com.mysql.jdbc.Driver
 * create connection --> Connection
 * then create statement --> Statement
 * execute the query -->
 * process the results -->
 * close
 * */
public class JavaDB {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		String url="jdbc:mysql://localhost:3306/sakila";
		String uname ="root";
		String pass="";
		
		//String query="insert into student values(" + userid +",'"+ username +"')"; // v cant pass int as it is so v used the concatenation here
		String query="select last_name from actor"; 

		Class.forName("com.mysql.jdbc.Driver"); //forName is to register the driver
		Connection con = DriverManager.getConnection(url,uname,pass);
		
		Statement st = con.createStatement(); 	

		ResultSet rs = st.executeQuery(query); // for select v hav to use this executeQuery
		
		while(rs.next()){
		String userData = rs.getString("last_name"); //v can initialize the string userData ="";  in the beg also 
		System.out.println(userData);
		}
	
		st.close();
		con.close();
	}

}
