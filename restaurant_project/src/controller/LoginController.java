package controller;

import entity.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import repository.StaffRepository;
import util.SHA256;

public class LoginController {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Label loginMsg;
    public boolean checkLogin(){
        boolean flag = false;
        String passwordHashed = new SHA256(password.getText()).getSHA256();
        StaffRepository staffRepository = new StaffRepository();
        User user = staffRepository.getUser(username.getText(), passwordHashed);
        if(user == null){
            loginMsg.setText("Login failed, check your username and password!");
        }
        else if(!user.isActive()){
            loginMsg.setText("Your account has been disabled!");
        }
        else{
            loginMsg.setText("Login Successful!");
            flag = true;
        }
        return flag;
    }
}
