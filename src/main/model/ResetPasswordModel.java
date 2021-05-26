package main.model;

import main.DBUtils;
import main.SQLConnection;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResetPasswordModel {

    /**
     * Compare this user's entered answer with this user's answer in database, if we found there is a match of this user and the answer
     * he provided, it means correct answer for his SQ, then we give he a new random password
     */
    public Boolean isAnswerForSQCorrect(int id, String answerForSQ) throws SQLException {
        Connection connection;
        connection = SQLConnection.connect();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from Employee where id=? and answer_for_secret_question=?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, answerForSQ);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        } finally {
            DBUtils.closeResultSet(resultSet);
            DBUtils.closePrepareStatement(preparedStatement);
            DBUtils.closeConnection(connection);
        }
    }

    public String generateRandomPassword(int len) {
        // ASCII range – alphanumeric (0-9, a-z, A-Z)
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@!#$%^&*_+-=|~<>,.?/[}]{;:'`";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        // each iteration of the loop randomly chooses a character from the given
        // ASCII range and appends it to the `StringBuilder` instance
        for (int i = 0; i < len; i++) {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }
        return sb.toString();
    }

    public boolean updatePassword(int id, String newPassword) throws SQLException {
        Connection connection;
        connection = SQLConnection.connect();
        PreparedStatement preparedStatement = null;
        boolean result;
        String query = "update Employee set password=? where id=?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, newPassword);
            preparedStatement.setInt(2, id);
            result = preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            return false;
        } finally {
            DBUtils.closePrepareStatement(preparedStatement);
            DBUtils.closeConnection(connection);
        }
        return result;
    }
}
