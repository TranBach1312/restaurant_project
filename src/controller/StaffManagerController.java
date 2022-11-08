package controller;

import entity.Staff;
import entity.StaffPosition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import repository.StaffRepository;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class StaffManagerController implements Initializable {

    @FXML
    private TableView<Staff> staffTableView;

    @FXML
    private TableColumn<Staff, Date> startedWorkColumn;

    @FXML
    private TableColumn<Staff, String> addressColumn;

    @FXML
    private TableColumn<Staff, Date> dobColumn;

    @FXML
    private TableColumn<Staff, String> emailColumn;

    @FXML
    private TableColumn<Staff, Integer> idColumn;

    @FXML
    private TableColumn<Staff, String> nameColumn;

    @FXML
    private TableColumn<Staff, String> phoneColumn;

    @FXML
    private TableColumn<Staff, StaffPosition> positionColumn;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchTextField;

    @FXML
    private VBox staffManagerVBox;

    @FXML
    private Button addNewButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<Staff, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Staff, String>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<Staff, String>("email"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<Staff, String>("address"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<Staff, String>("phone"));
        dobColumn.setCellValueFactory(new PropertyValueFactory<Staff, Date>("birthDate"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<Staff, StaffPosition>("staffPosition"));
        startedWorkColumn.setCellValueFactory(new PropertyValueFactory<Staff, Date>("startWorkedAt"));
        staffTableView.setItems(StaffRepository.getStaffList(""));

        staffTableView.setRowFactory(tr -> {
            TableRow<Staff> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2 && !row.isEmpty()) {
                    if (row.getItem() != null) {
                        Staff staff = row.getItem();
                        toStaff(staff, true);
                    }
                }
            });
            return row;
        });

        addNewButton.setOnAction(actionEvent -> {
            toStaff(null, false);
        });
    }

    public void toStaff(Staff staff, boolean view) {
        Stage thisStage = (Stage) staffManagerVBox.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("../view/Staff.fxml"));
        DetailStaffController controller = new DetailStaffController();
        controller.setStaff(staff);
        controller.setView(view);
        loader.setController(controller);
        try {
            Parent billRoot = loader.load();
            Scene scene = new Scene(billRoot);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setX(300);
            stage.setY(50);
            stage.setScene(scene);
            stage.initOwner(thisStage);
            stage.setOnHidden(windowEvent -> {
                staffTableView.setItems(StaffRepository.getStaffList(""));
            });
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

