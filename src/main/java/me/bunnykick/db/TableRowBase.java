package me.bunnykick.db;

import me.bunnykick.sql.MyStatement;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class TableRowBase {

    protected int id;
    protected RowState state;

    public int getId()          { return id; }
    public RowState getState()  { return state; }

    protected void deleteRow(MyStatement stmt) throws SQLException {
        String tableName = getTableName();
        stmt.executeQuery("DELETE FROM " + tableName + " WHERE ID_" + tableName + " = '" + id + "'");
    }

    protected void resetState() {
        state = RowState.ROW_NONE;
    }

    public void setState(RowState state) {
        this.state = state;
    }

    protected abstract void save(MyStatement stmt) throws SQLException;
    protected abstract String getSelect();
    protected abstract void fill(ResultSet rslt) throws SQLException;
    protected abstract String getTableName();

}
