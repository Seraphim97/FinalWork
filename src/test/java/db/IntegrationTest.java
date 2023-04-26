package db;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class IntegrationTest {


    @Test

    public void dummy() throws SQLException{

        DBmanager dBmanager = new DBmanager();

        Connection connection = dBmanager.connect();

        dBmanager.close(connection);
    }
}
