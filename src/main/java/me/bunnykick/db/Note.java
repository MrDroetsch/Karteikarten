package me.bunnykick.db;

import me.bunnykick.sql.MyStatement;
import me.bunnykick.utils.Tools;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Note extends TableRowBase {

    protected String title;
    private Date created;
    private Date edited;
    private int userID;

    public String getTitle()    { return title; }
    public Date getCreateDate() { return created; }
    public Date getEditDate()   { return edited; }
    public int getUserID()      { return userID; }

    public Note(String title, Date created, int userID) {
        this.title = title;
        this.created = created;
        edited = Tools.cloneDate(created);
        this.userID = userID;
        setState(RowState.ROW_ADD);
    }

    public Note(ResultSet rslt) throws SQLException {
        fill(rslt);
    }

    public String getContent() {
        String day = Tools.getDay(created);
        String date = Tools.getDateData(created);
        return day + " " + date;
    }

    @Override
    public void save(MyStatement stmt) throws SQLException {
        String query;
        switch(state) {
            case ROW_NONE -> {}
            case ROW_ADD -> {
                id = DB.getNextID(stmt, getTableName());

                query = "INSERT INTO Note (ID_Note,Title,Created,Edited,UserID) " +
                        "VALUES (" + id + "," + Tools.getDbString(title) + "," + Tools.getDBDate(created) + "," + Tools.getDBDate(edited) + "," + userID + ");";
                System.out.println(query);
                // INSERT INTO Note (ID_Note,Title,Created,Edited,UserID) VALUES (1,'Titel',#2023.04.03 18:23:48#,#2023/04/03 18:23:48#,1);
                stmt.executeUpdate(query);

                /*
                Liebes Diary
                Today habe 🥚 SQLite gelearned!!!!!!!1111elf
                It was EasyER as 🥚 thoughededed
                 */
            }
            case ROW_CHG -> {
                query = "UPDATE Note SET ID_Note = " + id + ", Title = " + Tools.getDbString(title) + ", Created = " + Tools.getDBDate(created) +
                        ", Edited = " + Tools.getDBDate(edited) + ", UserID = " + userID;
                stmt.executeUpdate(query);
            }
            case ROW_DEL -> deleteRow(stmt);
        }
        resetState();
    }

    @Override
    public String getSelect() {
        return "SELECT ID_Note, Title, Created, Edited, UserID FROM Note WHERE ID_NOTE = " + id;
    }

    @Override
    protected void fill(ResultSet rslt) throws SQLException {
        id = rslt.getInt(1);
        title = rslt.getString(2);
        created = rslt.getDate(3);
        edited = rslt.getDate(4);
        userID = rslt.getInt(5);
    }

    @Override
    protected String getTableName() {
        return "Note";
    }

    @Override
    public String toString() {
        return "Note[ID=" + id + ",Title=" + title + ",Created=" + Tools.getDBDate(created) + ",Edited=" + Tools.getDBDate(edited) + ",UserID=" + userID + "]";
    }
}
