package com.mironov.bmi.DAO;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionManager {
    private static String url = "jdbc:postgresql://localhost:5432/postgres";
    private static String driverName = "org.postgresql.Driver";
    private static String username = "postgres";
    private static String password = "postgres";
    private static Connection con;

    private static String crateTable=
            "DROP TABLE IF EXISTS bmi;\n" +
            "\n" +
            "CREATE TABLE \"bmi\" (\n" +
            "  \n" +
            "  bmi float DEFAULT NULL,\n" +
            "  \n" +
            "  name varchar DEFAULT NULL,\n" +
            "\n" +
            "  weight int  Default 1, \n" +
            "  \n" +
            "  height int Default 1,\n" +
            "\n" +
            "  dateTimeStep bigint Default 1\n" +
            ") ;\n" +
            "\n" +
            "INSERT INTO \"bmi\" VALUES \n" +
            "\t(2.0515087,'Vasja',65,178,1553518802929),\n" +
            "\t(2.3529413,'Petja',68,170,1553518802960);";

    public static Connection getConnection() {
        try {
            Class.forName(driverName);
            try {
                con = DriverManager.getConnection(url, username, password);
            } catch (SQLException ex) {
                // log an exception. fro example:
                System.out.println("Failed to create the database connection.");
            }
        } catch (ClassNotFoundException ex) {
            // log an exception. for example:
            System.out.println("Driver not found.");
        }
        return con;
    }

    public static void createTable() throws SQLException {
        Statement stmt = con.createStatement();
            // create a new table
            stmt.execute(crateTable);
    }
}
