package me.bunnykick.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MyConnection implements AutoCloseable {

    private Connection con;
    private List<MyStatement> stmts;

    public MyConnection(Connection con) throws SQLException {
        this.con = con;
        stmts = new ArrayList<>();
        con.setAutoCommit(false);
    }

    public MyStatement createStatement() throws SQLException {
        MyStatement stmt = new MyStatement(con.createStatement(), this);
        stmts.add(stmt);
        return stmt;
    }

    public boolean isClosed() throws SQLException {
        return con.isClosed();
    }

    void removeStatement(MyStatement myStatement) {
        stmts.remove(myStatement);
    }

    public void commit() throws SQLException {
        if(stmts.size() > 0) {
            for(MyStatement stmt : stmts) {
                stmt.close();
            }
            stmts.clear();
        }
        con.commit();
    }

    public void rollback() throws SQLException {
        con.rollback();
    }

    public void commitAndClose() throws SQLException {
        commit();
        close();
    }

    @Override
    public void close() throws SQLException {
        for(MyStatement stmt : stmts) {
            stmt.close();
        }
        con.close();
    }
}
