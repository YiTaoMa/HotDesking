package main.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.DBUtils;
import main.SQLConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdminManageAccountModel {
    ObservableList<String> accounts = FXCollections.observableArrayList();

    public ObservableList<String> getAllAccountsDetail() throws SQLException {
        accounts.clear();
        Connection connection;
        connection = SQLConnection.connect();
        Statement statement = connection.createStatement();
        String query = "select * from Employee";
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                accounts.add("Employee ID---" + resultSet.getInt("id") + "---First name---" + resultSet.getString("first_name") + "---Last name---" + resultSet.getString("last_name") + "---Role---" + resultSet.getString("character_role") + "---Username---" + resultSet.getString("username") + "---Password---" + resultSet.getString("password") + "---Secret question---" + resultSet.getString("secret_question") + "---Answer for Secret question---" + resultSet.getString("answer_for_secret_question") + "---Is Deactivated---" + resultSet.getBoolean("is_deactivated"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeResultSet(resultSet);
            DBUtils.closeStatement(statement);
            DBUtils.closeConnection(connection);
        }
        return accounts;
    }
}
