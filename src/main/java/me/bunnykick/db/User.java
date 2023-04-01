package me.bunnykick.db;

import me.bunnykick.sql.MyStatement;
import me.bunnykick.utils.Tools;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User extends TableRowBase {

    private String firstName;
    private String lastName;
    private String pw;

    public String getPassword()     { return pw; }
    public String getFirstName()    { return firstName; }
    public String getLastName()     { return lastName; }

    public User(ResultSet rslt) throws SQLException {
        fill(rslt);
    }

    public User(String firstName, String lastName, String pw) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.pw = pw;
        setState(RowState.ROW_ADD);
    }

    @Override
    protected void fill(ResultSet rslt) throws SQLException {
        id = rslt.getInt(1);
        firstName = rslt.getString(2);
        lastName = rslt.getString(3);
        pw = rslt.getString(4);
    }

    @Override
    protected void save(MyStatement stmt) throws SQLException {
        switch(state) {
            case ROW_NONE -> {}
            case ROW_ADD -> {
                id = DB.getNextID(stmt, "User");

                String query = "INSERT INTO User (ID_User, FirstName, LastName, Passwort) VALUES (" + id + "," + Tools.getDbString(firstName) + ","
                        + Tools.getDbString(lastName) + "," + Tools.getDbString(pw) + ")";
                stmt.executeUpdate(query);
            }
            case ROW_CHG -> {
                String query = "UPDATE User SET FirstName = " + Tools.getDbString(firstName) + ", LastName = " + Tools.getDbString(lastName) +
                        ", Passwort = " + Tools.getDbString(pw) + " WHERE ID_User = " + id;
                stmt.executeUpdate(query);
            }
            case ROW_DEL -> deleteRow(stmt);
        }
        resetState();
    }

    @Override
    protected String getSelect() {
        return "SELECT User_ID, FirstName, LastName, Password FROM User WHERE User_ID = " + id;
    }

    @Override
    protected String getTableName() {
        return "User";
    }

    public static User getUserFromDB(MyStatement stmt, String firstName, String lastName) throws SQLException {
        String query = "SELECT ID_User, FirstName, LastName, Password FROM User " +
                "WHERE FirstName = " + Tools.getDbString(firstName) + " AND LastName = " + Tools.getDbString(lastName);
        try(ResultSet rslt = stmt.executeQuery(query)) {
            if(rslt.next()) {
                return new User(rslt);
            }
        }
        return null;
    }

}
