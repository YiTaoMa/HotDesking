package main.model;

import main.DBUtils;
import main.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdminDeactivateAccountPromptModel {

    public boolean updateDeactivateStatus(int empId) throws SQLException {
        Connection connection;
        connection = SQLConnection.connect();
        String query = "update Employee set is_deactivated=true where id=?";
        PreparedStatement prst = null;
        boolean result = false;
        try {
            prst = connection.prepareStatement(query);
            prst.setInt(1, empId);
            result = prst.executeUpdate() > 0;
        } catch (Exception e) {
            return false;
        } finally {
            DBUtils.closePrepareStatement(prst);
            DBUtils.closeConnection(connection);
        }
        return result;
    }
}
