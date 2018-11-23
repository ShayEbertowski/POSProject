/*
 * 
 *  This class connects to the database and has several generic methods to run queries based on the context
 */

package util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class SQLAccess {
    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    
    
    // this method runs any update method needed
    public void runUpdateQuery(String query) throws Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://45.33.16.155/Candy Shop?user=ng8542gd&password=1206310sS!");
            statement = connect.createStatement();
            statement.executeUpdate(query);
        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }
    }
    
    // this method gets an item from the database and returns it
    String resultString;
    public String runFetchQuery(String query) throws Exception {
    	 try {
             Class.forName("com.mysql.jdbc.Driver");
             connect = DriverManager.getConnection("jdbc:mysql://45.33.16.155/Candy Shop?user=ng8542gd&password=1206310sS!");
             statement = connect.createStatement();
             resultSet = statement.executeQuery(query);
             while (resultSet.next()) {
            	 resultString = String.valueOf(resultSet.getObject(1));
             }
             return resultString;
         } catch (Exception e) {
             throw e;
         } finally {
             close();
         }
    }
    
    // this method either inserts something or deletes something depending on the query passed in
    public void runInsertOrDeleteQuery(String query) throws Exception {
    	try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://45.33.16.155/Candy Shop?user=ng8542gd&password=1206310sS!");
            statement = connect.createStatement();
            statement.execute(query);
        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }
    }

   
    // this method gets all of the information from a given table so that it can be converted to a table and displayed within the app
    ArrayList<Object[]> tempObjectArray;
    Object[][] resultObjectArray;
    public Object[][] getTable(String query) throws Exception {
    	tempObjectArray = new ArrayList<Object[]>();
    	 try {
             Class.forName("com.mysql.jdbc.Driver");
             connect = DriverManager.getConnection("jdbc:mysql://45.33.16.155/Candy Shop?user=ng8542gd&password=1206310sS!");
             statement = connect.createStatement();
             resultSet = statement.executeQuery(query);
             
             int numberOfColumns = resultSet.getMetaData().getColumnCount();
         	 while(resultSet.next()) {
         		Object[] temp = new Object[numberOfColumns];
         		
         		// note that result set begins at column 1, not 0
         		for (int i = 1; i <= numberOfColumns; i++) {
         			temp[i-1] = resultSet.getObject(i);
         		}
         		tempObjectArray.add(temp);
         	 }
         	 
         	 resultObjectArray = new Object[tempObjectArray.size()][numberOfColumns];
         	 for (int i = 0; i < tempObjectArray.size(); i++) {
         		 resultObjectArray[i] = tempObjectArray.get(i);
         	 }
         
         	return resultObjectArray;

         } catch (Exception e) {
             throw e;
         } finally {
             close();
         }
	}
    


    // close the resultSet
    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {

        }
    }

}






