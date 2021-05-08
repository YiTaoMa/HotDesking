package main.model;

import main.SQLConnection;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GenerateReportEmployeeModel {
    Connection connection;
    public GenerateReportEmployeeModel(){
        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);
    }
    public void generateEmployeeReport(){
        String csvFilePath = "EmployeeReport.csv";
        try{
            String sql = "select * from Employee";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(csvFilePath));
            fileWriter.write("id,first_name,last_name,character_role,username,password,secret_question,answer_for_secret_question");
            while (resultSet.next()){
                Integer id = resultSet.getInt("id");
                String first_name = resultSet.getString("first_name");
                String last_name = resultSet.getString("last_name");
                String character_role= resultSet.getString("character_role");
                String username= resultSet.getString("username");
                String password= resultSet.getString("password");
                String secret_question= resultSet.getString("secret_question");
                String answer_for_secret_question= resultSet.getString("answer_for_secret_question");

                if (first_name == null){
                      first_name="";
                }
                else {
                    first_name = "\"" + first_name+"\"";
                }
                if (last_name == null){
                    last_name="";
                }
                else {
                    last_name = "\"" + last_name+"\"";
                }

                String line = String.format("%d,%s,%s,%s,%s,%s,%s,%s",id,first_name,last_name,character_role,username,password,secret_question,answer_for_secret_question);
                fileWriter.newLine();
                fileWriter.write(line);
            }
            statement.close();
            fileWriter.close();
        }
        catch (SQLException | IOException e){
            e.printStackTrace();
        }
    }
}
