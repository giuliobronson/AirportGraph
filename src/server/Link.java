package server;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Link {
    private String server, database, user, password;
    private int gate;

    public Link(int status) {
        if(status == 0) {
            this.server = "localhost";
            this.database = "classicmodels";
            this.user = "giuliobronson";
            this.password = "bronson_IME24";
            this.gate = 3306;
        }
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getGate() {
        return gate;
    }

    public void setGate(int gate) {
        this.gate = gate;
    }

    public Connection connect() throws SQLException {
        String url = "jdbc:mysql://" + this.server + ":" + this.gate + "/" + this.database;
        return (Connection) DriverManager.getConnection(url, user, password);
    }
}
