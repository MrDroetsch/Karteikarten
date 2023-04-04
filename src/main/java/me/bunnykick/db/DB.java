package me.bunnykick.db;

import me.bunnykick.sql.ConnectionPool;
import me.bunnykick.sql.ConnectionPoolException;
import me.bunnykick.sql.MyConnection;
import me.bunnykick.sql.MyStatement;
import me.bunnykick.utils.Tools;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DB {

    private final ConnectionPool conPool;

    private String version = "0.0";

    public DB(ConnectionPool conPool) throws ConnectionPoolException, SQLException {
        this.conPool = conPool;
        try {
            handleVersion();
            System.out.println("MySQL connection Version " + version + " established");
        } catch (SQLException e) {
            System.err.println("Error while handling db Version: " + e.getMessage());
            throw e;
        }
    }

    private void handleVersion() throws SQLException, ConnectionPoolException {

        try(MyConnection con = conPool.getConnection(); MyStatement stmt = con.createStatement()) {
            version = getVersion(stmt);
            boolean updated = false;

            if(version.equals("0.0")) {
                stmt.executeUpdate("CREATE TABLE infos(keyy VARCHAR(255) NOT NULL, value VARCHAR(255) NOT NULL);");
                stmt.executeUpdate("INSERT INTO infos (keyy, value) VALUES (" + Tools.getDbString("version") + "," + Tools.getDbString("1.0") + ")");
                version = "1.0";
                updated = true;
                System.out.println("Created DB and set Version to 1.0");
            }

            if(version.equals("1.0")) {
                stmt.executeUpdate("CREATE TABLE User(" +
                        "ID_User INT NOT NULL PRIMARY KEY," +
                        "FirstName TEXT NOT NULL," +
                        "LastName TEXT," +
                        "Username TEXT NOT NULL," +
                        "Password TEXT NOT NULL);");
                stmt.executeUpdate("INSERT INTO User (ID_User,FirstName,LastName,Username,Password) VALUES (1,'Cedric','Riechers','admin','admin');");

                stmt.executeUpdate("CREATE TABLE Note(" +
                        "ID_Note INT NOT NULL PRIMARY KEY," +
                        "Title TEXT NOT NULL," +
                        "Created DATETIME NOT NULL," +
                        "Edited DATETIME NOT NULL," +
                        "UserID INT NOT NULL CONSTRAINT Note_UserID REFERENCES User (ID_User));");

                stmt.executeUpdate("CREATE TABLE Note2Data(" +
                        "ID_Note2Data INT NOT NULL PRIMARY KEY," +
                        "NoteID INT NOT NULL CONSTRAINT Note2Data_NoteID REFERENCES Note (ID_Note)," +
                        "Value CLOB);");

                updated = true;
                setVersion("1.1", stmt);
            }

            if(updated) {
                con.commit();
            }
        }
    }

    private String getVersion(MyStatement stmt) {
        String query = "SELECT value FROM infos WHERE keyy = " + Tools.getDbString("version");
        try(ResultSet rslt = stmt.executeQueryWithoutError(query)) {
            if(rslt.next()) {
                return rslt.getString(1);
            }
        } catch(SQLException ignored) {}
        return "0.0";
    }

    private void setVersion(String version, MyStatement stmt) throws SQLException {
        String preVer = this.version;
        this.version = version;
        stmt.executeUpdate("UPDATE infos SET value = " + Tools.getDbString(version) + " WHERE keyy = " + Tools.getDbString("version"));
        System.out.println("Updated DB Version from " + preVer + " to " + version);
    }

    public String getVersion() {
        return version;
    }

    public static int getNextID(MyStatement stmt, String tableName) throws SQLException {
        String query = "SELECT MAX(ID_" + tableName + ") FROM " + tableName;
        try(ResultSet rslt = stmt.executeQuery(query)) {
            if(rslt.next()) {
                return rslt.getInt(1) + 1;
            }
        }
        throw new SQLException("Error while reading Max(ID) from " + tableName + " table");
    }

}
