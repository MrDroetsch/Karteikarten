package me.bunnykick.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MyStatement implements AutoCloseable {

    private MyConnection con;
    private Statement stmt;
    private List<String> updates;

    public MyStatement(Statement stmt, MyConnection con) {
        this.stmt = stmt;
        this.con = con;
        updates = new ArrayList<>();
    }

    public void addUpdate(String query) {
        updates.add(query);
    }

    public void executeUpdates() throws SQLException {
        for(String query : updates) {
            executeUpdate(query);
        }
    }

    public void executeUpdate(String query) throws SQLException {
        stmt.executeUpdate(query);
        con.removeStatement(this);
    }

    public ResultSet executeQuery(String query) throws SQLException {
        try {
            ResultSet rslt = stmt.executeQuery(query);
            return rslt;
        } catch(SQLException e) {
            System.err.println("Error while executing SQL Statement: " + query + ". Error: " + e.getMessage());
            throw e;
        }
    }

    public ResultSet executeQueryWithoutError(String query) throws SQLException {
        return stmt.executeQuery(query);
    }

    @Override
    public void close() throws SQLException {
        con.removeStatement(this);
        stmt.close();
    }

}
