package darthchest.main;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class MySQLConnection {

	
	private Connection connection;
    private String host, database, username, password;
    private int port;
	
	public MySQLConnection(String Host, int Port, String Database, String Username, String Password){
		host = Host;
	    port = Port;
	    database = Database;
	    username = Username;
	    password = Password;	    
	}
	
	public void connect(){
		try {     
            openConnection();
            Statement statement = connection.createStatement();    
            
            String sql = "CREATE TABLE IF NOT EXISTS DarthChest_items " +
                    "(ItemType VARCHAR(255) NOT NULL, "  + 
                    " Cost DOUBLE NOT NULL)"; 
            
            statement.execute(sql);
            
            sql = "CREATE TABLE IF NOT EXISTS DarthChest_autoChests " +
                    "(World VARCHAR(255) NOT NULL, "  + 
                    " signX DOUBLE NOT NULL, "
                    + "signY DOUBLE NOT NULL, "
                    + "signZ DOUBLE NOT NULL, "
                    + "chestX DOUBLE NOT NULL, "
                    + "chestY DOUBLE NOT NULL,"
                    + "chestZ DOUBLE NOT NULL, "
                    + "recipient VARCHAR(255) NOT NULL)"; 
            statement.execute(sql);
            
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	public void disconnect(){
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	       
	   


	    public void openConnection() throws SQLException, ClassNotFoundException {
	    if (connection != null && !connection.isClosed()) {
	        return;
	    }

	    synchronized (this) {
	        if (connection != null && !connection.isClosed()) {
	            return;
	        }
	        Class.forName("com.mysql.jdbc.Driver");
	        connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database, this.username, this.password);
	    }
	}
	 
	
}
