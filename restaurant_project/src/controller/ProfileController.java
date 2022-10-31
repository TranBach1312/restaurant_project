package controller;

import account.LoggedAccount;
import entity.Staff;
import entity.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import repository.StaffRepository;
import util.RegexTemplate;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
    @FXML
    private BorderPane profileBorderPane;
    @FXML
    private HBox userHBox;
    @FXML
    private Button orderManageButton;
    @FXML
    private Button staffManageButton;
    @FXML
    private Button statsButton;
    @FXML
    private Button tradingButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        changePage("../view/DinnerTable.fxml", null);

        //user page
        userHBox.setOnMouseClicked(event -> {
            changePage("../view/User.fxml", null);
        });

        //order manage page
        orderManageButton.setOnMouseClicked(event -> {
            changePage("../view/OrderManage.fxml", null);
        });

        // staff manage page
        staffManageButton.setOnMouseClicked(event -> {
            changePage("../view/StaffManage.fxml", null);
        });

        //stats manage page
        statsButton.setOnMouseClicked(event -> {
            changePage("../view/stats.fxml", null);
        });

        //trading manage page
        tradingButton.setOnMouseClicked(event -> {
            changePage("../view/DinnerTable.fxml", null);
        });
    }

    public void changePage(String resource, Object controller) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(resource));
            if(controller != null){
                loader.setController(controller);
            }
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        profileBorderPane.setCenter(root);
    }

}
