package main.controller;

import javafx.event.ActionEvent;
import main.model.GenerateReportEmployeeModel;

public class GenerateReportEmployeeController {
    GenerateReportEmployeeModel generateReportEmployeeModel =  new GenerateReportEmployeeModel();

    public void generateReportForEmployee(ActionEvent event) {
        // only generated 1 row instead of all
       generateReportEmployeeModel.generateEmployeeReport();
    }
}
