package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Link {
    private String server, database, user, password;
    private int gate;

    public Link(int status) {
        if(status == 0) {
            this.server = "localhost";
            this.database = "airport_graph";
            this.user = "giuliobronson";
            this.password = "bronson_IME24";
            this.gate = 3306;
        }
    }

    public Connection connect() throws SQLException {
        String url = "jdbc:mysql://" + this.server + ":" + this.gate + "/" + this.database;
        return DriverManager.getConnection(url, user, password);
    }
}
