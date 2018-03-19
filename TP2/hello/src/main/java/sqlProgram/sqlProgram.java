package sqlProgram;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
import java.lang.String.*;
import java.util.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class sqlProgram {
 
	static Connection con = null;
	//static PreparedStatement sakilaPrepareStat = null;
	
 
	public static void main(String[] argv) throws Exception{
 
		try {
			JDBCConnection();
			System.out.println("Database connection established ... \n");
			DataFromDB();         
			con.close(); // connection close
 
		} catch (Exception e) {
 
			e.printStackTrace();
		}
	}
	
	private static void JDBCConnection() {
 
		try {
			Class.forName("com.mysql.jdbc.Driver");
			log("Congrats - Seems your MySQL JDBC Driver Registered!");
		} catch (ClassNotFoundException e) {
			log("Sorry, Try again ...");
			e.printStackTrace();
			return;
		}
 
		try {
			// DriverManager: The basic service for managing a set of JDBC drivers.
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sakila", "root", "");
			if (con != null) {
				log("Connected Succesfully!!");
			} else {
				log("Failed Connection");
			}
		} catch (SQLException e) {
			log("MySQL Connection Failed!");
			e.printStackTrace();
			return;
		}
 
	}
	
	private static void DataFromDB() {
		 
		try {
			
			//MetaData connection
			DatabaseMetaData md = con.getMetaData();
			String   catalog           = null;
			String   schemaPattern     = null;
			String   tableNamePattern  = null;
			String   schema    		= null;
			String columnNamePattern = null;
			String[] types            = null;
			
			deleteFile();
			ResultSet result = md.getTables(catalog, schemaPattern, tableNamePattern, types);
			String tableName ="";
			while(result.next()) {
					
					String tableType = result.getString("TABLE_TYPE");
					String sqlInstructions = (tableType.equals("VIEW")) ? "CREATE VIEW " : "CREATE TABLE ";
					
					tableName = result.getString(3);
					
					sqlInstructions += (tableType.equals("VIEW"))? tableName + " AS \n" : tableName + " ( \n";
					
					ResultSet result1 = md.getColumns(catalog, schemaPattern,  tableName, columnNamePattern);
					
					if (tableType.equals("VIEW")) { 
						sqlInstructions += "SELECT ";
						int j = 0;
						while(result1.next()){
							String columnName = result1.getString(4);
							sqlInstructions += (j == 0)? columnName : ", " + columnName ;
							j++;
						}
						sqlInstructions += "\n";
						
					}
					else {
						while(result1.next()){
							String columnName = result1.getString(4);
							String datatype = result1.getString("DATA_TYPE");
							String typeName = result1.getString("TYPE_NAME");
						    String columnsize = result1.getString("COLUMN_SIZE");
						    String decimaldigits = result1.getString("DECIMAL_DIGITS");
						    String typeNameNumbers = (columnsize != null)? "(" + columnsize + ")" : "";
						    String isNullable = result1.getString("IS_NULLABLE");
						    String isNullableBool = (isNullable == "YES") ? "NULL" : "NOT NULL";
						    String is_autoIncrment = result1.getString("IS_AUTOINCREMENT");
						    String isAutoIncrementBool = (is_autoIncrment == "YES")? " AUTO_INCREMENT": "";
							
							sqlInstructions += columnName + " " + typeName + typeNameNumbers + " " + isNullableBool + isAutoIncrementBool + ", \n";
	
						}
					
					ResultSet result2 = md.getPrimaryKeys(catalog, schema, tableName);
					ResultSet foreignKey = md.getImportedKeys(catalog, schema, tableName);
					ResultSet uniqueKey = md.getIndexInfo(catalog, schema, tableName, true, true);
					
					//PRIMARY KEYS
					int i = 0; 
					sqlInstructions += "PRIMARY KEY (";
					while(result2.next()){
						String columnNamep = result2.getString(4);
						sqlInstructions += (i == 0)? columnNamep : ", " + columnNamep;
						i++;
					}	
					sqlInstructions += "), \n";
					
					//UNIQUE KEYS
					int j = 0; 
					sqlInstructions += "UNIQUE (";
					
					while(uniqueKey.next()){
						String uniqueColumnName = uniqueKey.getString("COLUMN_NAME");
						sqlInstructions += (j == 0)? uniqueColumnName : ", " + uniqueColumnName;
						j++;
					}
					sqlInstructions += "), \n";
					
					//FOREIGN KEYS
					int k = 0; 
					while(foreignKey.next()){
						String uniqueColumnName = "FOREIGN KEY ("+ foreignKey.getString("FKCOLUMN_NAME") + ") REFERENCES " + foreignKey.getString("PKTABLE_NAME") + "(" + foreignKey.getString("PKCOLUMN_NAME") + ")";
					    sqlInstructions += (k == 0)? uniqueColumnName : ", \n" + uniqueColumnName;
						k++;
					}
					sqlInstructions += "\n); \n";
					}
					writeFile(sqlInstructions);
					System.out.println(sqlInstructions);
			}
			
		} catch (
 
		SQLException e) {
			e.printStackTrace();
		}
 
	}
	
	public static void writeFile(String sqlInstructions)//saving the output in the file
    {
        try
        {
            File archivo = new File("storeData.sql");
            FileWriter fw = new FileWriter(archivo.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.newLine();
            bw.write(sqlInstructions);
            
            bw.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
	public static void deleteFile()//checking whethere there is any file if exits it will replace the old with the new file
    {
		try {
        File file = new File("storeData.sql");
		if (file.exists()) {
		    file.delete();
		    }
		}
		catch(Exception e) {
			e.printStackTrace();
		}
    }
	private static void log(String string) {
		System.out.println(string);
 
	}

}