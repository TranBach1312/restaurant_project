package controller;

import entity.Dish;
import entity.DishCategory;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import repository.OrderRepository;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProductController implements Initializable {

    @FXML
    private VBox productVBox;
    @FXML
    private Button addButton;

    @FXML
    private TableView<Dish> dishTableView;


    @FXML
    private TableColumn<Dish, String> nameColumn;

    @FXML
    private TableColumn<Dish, Double> priceColumn;

    @FXML
    private TableColumn<Dish, Double> averageCostPriceColumn;

    @FXML
    private TableColumn<Dish, DishCategory> categoryColumn;


    @FXML
    private TableColumn<Dish, Integer> totalSoldColumn;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchTextField;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        nameColumn.setCellValueFactory(new PropertyValueFactory<Dish, String>("name"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<Dish, DishCategory>("category"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Dish, Double>("price"));
        averageCostPriceColumn.setCellValueFactory(new PropertyValueFactory<Dish, Double>("averageCostPrice"));
        totalSoldColumn.setCellValueFactory(new PropertyValueFactory<Dish, Integer>("totalSold"));

        resetTable();


        searchButton.setOnMouseClicked(mouseEvent -> {
            ObservableList<Dish> dishes = OrderRepository.getDishList(searchTextField.getText().trim());
            dishTableView.setItems(dishes);
        });

        addButton.setOnMouseClicked(mouseEvent -> {
            Stage thisStage = (Stage) productVBox.getScene().getWindow();
            Stage stage = new Stage();
            stage.setTitle("Add a new Dish");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("../view/DishManage.fxml"));
            AddDishController controller = new AddDishController();
            loader.setController(controller);
            try {
                Parent addDishRoot = loader.load();
                Scene scene = new Scene(addDishRoot);
                stage.setScene(scene);
                stage.initOwner(thisStage);
                stage.setOnHidden(windowEvent -> {
                    resetTable();
                });
                stage.showAndWait();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
        searchTextField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                ObservableList<Dish> dishes = OrderRepository.getDishList(searchTextField.getText());
                dishTableView.setItems(dishes);
            }
        });


        dishTableView.setRowFactory(tr -> {

            TableRow<Dish> row = new TableRow<>(){
                @Override
                protected void updateItem(Dish item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null)
                        setStyle("");
                    else if (item.isDeleted())
                        setStyle("-fx-background-color: #bdc3c7;");
                    else if (item.isSoldOut())
                        setStyle("-fx-background-color: #f1c40f;");
                    else
                        setStyle("-fx-background-color: #3498db");
                }
            };
            row.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2 && !row.isEmpty()) {
                    Dish dish = row.getItem();
                    detailDish(dish);
                }
            });
            return row;
        });
    }

    public void detailDish(Dish dish) {
        Stage thisStage = (Stage) productVBox.getScene().getWindow();
        Stage stage = new Stage();
        stage.setTitle("Dish Detail");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("../view/DishManage.fxml"));
        DetailDishController controller = new DetailDishController();
        controller.setThisDish(dish);
        loader.setController(controller);
        try {
            Parent addDishRoot = loader.load();
            Scene scene = new Scene(addDishRoot);
            stage.setScene(scene);
            stage.initOwner(thisStage);
            stage.setOnHidden(windowEvent -> {
                resetTable();
            });
            stage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void resetTable() {
        ObservableList<Dish> dishObservableList = OrderRepository.getDishList("");
        dishTableView.setItems(dishObservableList);
    }
}
