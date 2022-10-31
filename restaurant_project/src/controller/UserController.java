package controller;

import account.LoggedAccount;
import entity.Staff;
import entity.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import repository.StaffRepository;
import util.RegexTemplate;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserController implements Initializable {
    @FXML
    private Label staffname;
    @FXML
    private Label dob;
    @FXML
    private Label address;
    @FXML
    private Label position;
    @FXML
    private TextField phone;
    @FXML
    private TextField email;
    @FXML
    private Button saveButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Optional<User> user = LoggedAccount.getUserLogged();
        StaffRepository sr = new StaffRepository();
        Optional<Staff> staff = Optional.empty();
        if (user.isPresent()) {
            staff = sr.getStaffFromUser(user.get());
            if (staff.isPresent()) {
                staffname.setText(staff.get().getName());
                dob.setText(staff.get().getBirthDate().toString());
                address.setText(staff.get().getAddress());
                position.setText(String.valueOf((StaffRepository.getStaffPosition(staff.get().getStaffPositionId())).getName()));
                phone.setText(staff.get().getPhone());
                email.setText(staff.get().getEmail());
            }
        }

        Optional<Staff> finalStaff = staff;
        saveButton.setOnMouseClicked(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Notification");
            alert.setHeaderText("Update Notice");
            if(finalStaff.isPresent()){
                finalStaff.get().setPhone(phone.getText());
                finalStaff.get().setEmail(email.getText());
                Boolean isUpdate = sr.updateStaff(finalStaff.get());
                if(isUpdate){
                    alert.setContentText("Update Information Success!");
                }
                else{
                    alert.setContentText("Update Information Fail!");
                }
                alert.showAndWait();
            }
        });
        saveButton.setDisable(true);
        phone.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.matches(RegexTemplate.PHONE_REGEX)){
                saveButton.setDisable(false);
            }
            else{
                saveButton.setDisable(true);
            }
        });
        email.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.matches(RegexTemplate.EMAIL_REGEX)){
                saveButton.setDisable(false);
            }else {
                saveButton.setDisable(true);
            }
        });
    }
}
