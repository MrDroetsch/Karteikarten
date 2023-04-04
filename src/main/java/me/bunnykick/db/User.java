package me.bunnykick.db;

import me.bunnykick.sql.MyStatement;
import me.bunnykick.utils.Tools;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class User extends TableRowBase {

    private String firstName;
    private String lastName;
    private String userName;
    private String password;

    public String getPassword()     { return password; }
    public String getFirstName()    { return firstName; }
    public String getLastName()     { return lastName; }
    public String getUserName()     { return userName; }

    public User(ResultSet rslt) throws SQLException {
        fill(rslt);
    }

    public User(String firstName, String lastName, String userName, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        setState(RowState.ROW_ADD);
    }

    @Override
    protected void fill(ResultSet rslt) throws SQLException {
        id = rslt.getInt(1);
        firstName = rslt.getString(2);
        lastName = rslt.getString(3);
        userName = rslt.getString(4);
        password = rslt.getString(5);
    }

    @Override
    public void save(MyStatement stmt) throws SQLException {
        switch(state) {
            case ROW_NONE -> {}
            case ROW_ADD -> {
                id = DB.getNextID(stmt, "User");

                String query = "INSERT INTO User (ID_User, FirstName, LastName, Username, Password) VALUES (" + id + "," + Tools.getDbString(firstName) + ","
                        + Tools.getDbString(lastName) + "," + Tools.getDbString(userName) + "," + Tools.getDbString(password) + ")";
                stmt.executeUpdate(query);
            }
            case ROW_CHG -> {
                String query = "UPDATE User SET FirstName = " + Tools.getDbString(firstName) + ", LastName = " + Tools.getDbString(lastName) +
                        ", Username = " + Tools.getDbString(userName) + ", Passwort = " + Tools.getDbString(password) + " WHERE ID_User = " + id;
                stmt.executeUpdate(query);
            }
            case ROW_DEL -> deleteRow(stmt);
        }
        resetState();
    }

    public boolean isCorrectPW(String toCheck) {
        return password.equals(toCheck);
    }

    public String getSelect() {
        return "SELECT User_ID, FirstName, LastName, Username, Password FROM User WHERE User_ID = " + id;
    }

    @Override
    protected String getTableName() {
        return "User";
    }

    public static User getUserFromDB(MyStatement stmt, String userName) throws SQLException {
        String query = "SELECT ID_User, FirstName, LastName, Username, Password FROM User " +
                "WHERE Username = " + Tools.getDbString(userName);
        try(ResultSet rslt = stmt.executeQuery(query)) {
            if(rslt.next()) {
                return new User(rslt);
            }
        }
        return null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, userName, password);
    }

    @Override
    public String toString() {
        return "User[ID=" + id + ",firstName=" + firstName + ",lastName=" + lastName + ",userName=" + userName + ",password=" + password + "]";
    }
}
