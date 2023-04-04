package me.bunnykick.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {

    private static ConnectionPool instance = null;

    private static int MAX_CONNECTIONS = 10;

    private final String URL = "E://IntelliJ//Hobby_Programming//demo//sqlite.sqlite";

    List<MyConnection> cons = new ArrayList<>();

    public MyConnection getConnection() throws ConnectionPoolException, SQLException {
//        cleanUp();
        if(cons.size() >= MAX_CONNECTIONS) {
            throw new ConnectionPoolException("Max count of Connections are in Use: " + cons.size() + "/" + MAX_CONNECTIONS);
        }
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite://" + URL);
            MyConnection con = new MyConnection(connection);
            cons.add(con);
            return con;
        } catch(SQLException e) {
            System.err.println("Error whilst connecting to database: " + e.getMessage());
            throw e;
        }
    }

    private void cleanUp() {
        for(MyConnection con : cons) {
            try {
                if(con.isClosed()) cons.remove(con);
            } catch (SQLException e) { /* ignred */ }
        }
    }

    public static ConnectionPool getInstance() {
        if(instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    private ConnectionPool() {}

}
