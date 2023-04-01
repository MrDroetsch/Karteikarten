package me.bunnykick.sql;

public class ConnectionPoolException extends Exception {

    public ConnectionPoolException() {
        super("Error with ConnectionPool");
    }

    public ConnectionPoolException(String msg) {
        super(msg);
    }

}
