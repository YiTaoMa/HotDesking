package main;

import java.sql.*;

public class DBUtils {
    public static void closeConnection(Connection con) {
        try {
            if (con != null)
                con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closePrepareStatement(PreparedStatement prs) {
        try {
            if (prs != null)
                prs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeResultSet(ResultSet rst) {
        try {
            if (rst != null)
                rst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeStatement(Statement statement) {
        try {
            if (statement != null)
                statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
