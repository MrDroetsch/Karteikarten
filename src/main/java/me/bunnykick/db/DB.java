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
            System.out.println("MySQL connection established");
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
                stmt.addUpdate("CREATE TABLE User(ID_User INT NOT NULL PRIMARY KEY, " +
                        "Vorname VARCHAR(255) NOT NULL, " +
                        "Nachname VARCHAR(255) NOT NULL);");

                stmt.addUpdate("CREATE TABLE Fragebogen(ID_Fragebogen INT NOT NULL PRIMARY KEY, " +
                        "UserID INT NOT NULL, " +
                        "Titel VARCHAR(255) NOT NULL);");
                stmt.addUpdate("ALTER TABLE Fragebogen ADD CONSTRAINT Fragebogen_UserID FOREIGN KEY (UserID) REFERENCES User(ID_User);");

                stmt.addUpdate("CREATE TABLE Fragen(ID_Fragen INT NOT NULL PRIMARY KEY, " +
                        "FragebogenID INT NOT NULL, " +
                        "Frage VARCHAR(500) NOT NULL, " +
                        "Antwort VARCHAR(500) NOT NULL, " +
                        "Tipp VARCHAR(500) NOT NULL, " +
                        "Erklaerung VARCHAR(500) NOT NULL);");
                stmt.addUpdate("ALTER TABLE Fragen ADD CONSTRAINT Fragen_FragebogenID FOREIGN KEY (FragebogenID) REFERENCES Fragebogen(ID_Fragebogen);");

                stmt.addUpdate("CREATE TABLE User2Frage(UserID INT NOT NULL, " +
                        "FragenID INT NOT NULL, " +
                        "AnzahlRichtig INT NOT NULL DEFAULT 0, " +
                        "AnzahlFalsch INT NOT NULL DEFAULT 0);");
                stmt.addUpdate("ALTER TABLE User2Frage ADD CONSTRAINT User2Frage_UserID FOREIGN KEY (UserID) REFERENCES User(ID_User);");
                stmt.addUpdate("ALTER TABLE User2Frage ADD CONSTRAINT User2Frage_FragenID FOREIGN KEY (FragenID) REFERENCES Fragen(ID_Fragen);");

                stmt.executeUpdates();

                setVersion("1.1", stmt);

                updated = true;
            }

            if(version.equals("1.1")) {
                stmt.addUpdate("ALTER TABLE User CHANGE Vorname FirstName VARCHAR(255) NOT NULL;");
                stmt.addUpdate("ALTER TABLE User CHANGE Nachname LastName VARCHAR(255) NOT NULL;");
                stmt.addUpdate("ALTER TABLE User ADD CONSTRAINT unique_firstLastName UNIQUE(FirstName, LastName);");
                stmt.addUpdate("ALTER TABLE User ADD Password VARCHAR(255) NOT NULL;");

                stmt.executeUpdates();

                setVersion("1.2", stmt);

                updated = true;
            }

            if(version.equals("1.2")) {
                stmt.executeUpdate("INSERT INTO User (ID_User, FirstName, LastName, Password) VALUES (1, " + Tools.getDbString("sys") + ", "
                        + Tools.getDbString("admin") + ", " + Tools.getDbString("sysadmin") + ");");

                setVersion("1.2.1", stmt);

                updated = true;
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
                return rslt.getInt(1);
            }
        }
        throw new SQLException("Error while reading Max(ID) from " + tableName + " table");
    }

}
