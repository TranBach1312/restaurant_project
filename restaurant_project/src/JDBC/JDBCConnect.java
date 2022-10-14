package JDBC;

import common.Config;

import java.sql.*;

public class JDBCConnect {
    public Connection JDBCConnector() {
        Connection conn = null;
        String url = "jdbc:mysql://" + Config.HOST + ":" + Config.PORT + "/" + Config.DBNAME;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, Config.USERNAME, Config.PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void closeConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closePreparedStatement(PreparedStatement p) {
        try {
            if (p != null) {
                p.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeResultSet(ResultSet r) {
        try {
            r.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println((new JDBCConnect()).JDBCConnector());
    }
}
