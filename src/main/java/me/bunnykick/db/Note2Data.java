package me.bunnykick.db;

import me.bunnykick.sql.MyStatement;

import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Note2Data extends TableRowBase {

    private int noteID;
    private Clob value;

    public Note2Data(int noteID, Clob value) {
        this.noteID = noteID;
        this.value = value;
        setState(RowState.ROW_ADD);
    }

    public Note2Data(ResultSet rslt) throws SQLException {
        fill(rslt);
    }

    @Override
    public void save(MyStatement stmt) throws SQLException {
        // TODO: implement
    }

    @Override
    public String getSelect() {
        return "SELECT ID_Note2Data, NoteID, Value WHERE ID_Note2Data = " + id;
    }

    @Override
    protected void fill(ResultSet rslt) throws SQLException {
        id = rslt.getInt(1);
        noteID = rslt.getInt(2);
        value = rslt.getClob(3);
    }

    @Override
    protected String getTableName() {
        return "Note2Data";
    }
}
