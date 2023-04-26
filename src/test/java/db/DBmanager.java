package db;

import helpers.SetupFunctions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBmanager {

    //private final String url = "jdbc:postgresql://rc1b-3vawdqzm9kwkzvqn.mdb.yandexcloud.net:6432/db1";
    //private final String user = "userqa08";
    //private final String password = "xItu8gj6xXfsz";

    public Connection connect(){

        SetupFunctions setupFunctions = new SetupFunctions();

        String host = setupFunctions.getDbhost();
        String port = setupFunctions.getDbport();
        String dbname = setupFunctions.getDbname();
        String dbusername = setupFunctions.getDbusername();
        String dbpassword = setupFunctions.getPassword();


        String connectionString = host + ":" + port + "/" + dbname;

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Can't load class");
        }


        Connection connection = null;
        //connection = DriverManager.getConnection(url, user, password);

        System.out.println("Connected to the PostgreSQL server successfully.");

        //Statement s = connection.createStatement();

        return connection;
        }

        public void close(Connection connection) {

        if (connection != null) {

            try {
                connection.close();
                System.out.println("Closed successfully");
            } catch (SQLException e) {
                System.out.println("error while closing connection:" + e);
            }

        } else {
            System.out.println("Connection does not exist");
        }

    }
}






