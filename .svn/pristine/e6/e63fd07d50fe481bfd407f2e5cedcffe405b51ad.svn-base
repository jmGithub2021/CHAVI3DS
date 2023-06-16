/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package root;

/**
 *
 * @author Surajit Kundu
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySqlCon{
    // init database constants
    private static String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
    private static String DATABASE_URL = "jdbc:mysql://localhost:3306/chaviro";
    private static String USERNAME = "root";
    private static String PASSWORD = "1234";
    private static String MAX_POOL = "550";

    // init connection object
    private Connection connection;
    // init properties object
    private Properties properties;
    
    public MySqlCon(){
        Crypto crpt = new Crypto();
        GetConfig gc = new GetConfig();
        DATABASE_DRIVER = gc.dbDriver;
        DATABASE_URL = gc.dbURL+gc.dbName;
        USERNAME = gc.dbUsername;
        try{
            PASSWORD = crpt.decryptString(gc.dbPassword); 
        }catch(Exception ex){System.err.println("Err083405 : "+ex.toString());}        
    }

    // create properties
    private Properties getProperties() {
        if (properties == null) {
            properties = new Properties();
            properties.setProperty("user", USERNAME);
            properties.setProperty("password", PASSWORD);
            properties.setProperty("MaxPooledStatements", MAX_POOL);
        }
        return properties;
    }

    // connect database
    public Connection connectMysql() {
        if (connection == null) {
            try {
                Class.forName(DATABASE_DRIVER);
                connection = DriverManager.getConnection(DATABASE_URL, getProperties());
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    // disconnect database
    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
