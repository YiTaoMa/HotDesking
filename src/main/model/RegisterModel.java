package main.model;

import main.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterModel {
    Connection connection;

    public RegisterModel() {
        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);
    }

    public Boolean isRegister(String username, String password, int id, String firstname, String lastname, String role, String secretQ, String answerForSecretQ) throws SQLException {
        //int resultRegister = -1;
        PreparedStatement prst = null;
        ResultSet resultSet = null;
        /**we only chek is id exist then not going to register user, an employee can only register once*/
        String sqlregister = "select * from Employee where id=? or username=?"; // as long as email is incorrect not going to register user that is why need only email
        //not password!!! important!!
        try {
            prst = connection.prepareStatement(sqlregister);
            prst.setInt(1, id);//represnet "?" place holder
            prst.setString(2, username);

            resultSet = prst.executeQuery(); // execute SQL statement and return results

            if (resultSet.next()) { //if the results have next
                return false;
            } else {//user name not match database means the user not register before, then register user
                //must in sequence corresponding to the database row sequence
                String sql = "insert into Employee (id,first_name,last_name,character_role,username,password,secret_question,answer_for_secret_question) values(?,?,?,?,?,?,?,?)"; // SQL statement

                prst = connection.prepareStatement(sql); // PS to SQL statement
                prst.setInt(1, id);
                prst.setString(2, firstname);
                prst.setString(3, lastname);
                prst.setString(4, role);
                prst.setString(5, username);
                prst.setString(6, password);
                prst.setString(7, secretQ);
                prst.setString(8, answerForSecretQ);

                return prst.executeUpdate() > 0;
            }
        } catch (Exception e) {
            return false;
        } finally {
            prst.close();
            resultSet.close();
        }
    }
}
