package controller;

import entity.*;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import repository.OrderRepository;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class OrderController implements Initializable {
    private DinnerTable dinnerTable;
    private ObservableMap<Dish, Integer> orderedItems = FXCollections.observableHashMap();
    private Integer tempId = 0;
    @FXML
    private Label totalLabel;

    @FXML
    private Button payButton;

    @FXML
    private TableView<Dish> dishTableView;
    @FXML
    private TableColumn<Dish, ImageView> imageColumn;

    @FXML
    private TableColumn<Dish, String> nameColumn;

    @FXML
    private TableColumn<Dish, Double> priceColumn;


    @FXML
    private Label discountLabel;


    @FXML
    private ScrollPane dishCategoryPane;

    @FXML
    private BorderPane orderBorderPane;

    @FXML
    private TextField searchTextField;

    @FXML
    private Button saveButton;


    @FXML
    private VBox orderedVBox;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //add dish category
        ObservableList<DishCategory> dishCategories = OrderRepository.getDishCategory();
        //
        GridPane dishCategoryGridPane = new GridPane();
        dishCategoryGridPane.setPrefWidth(300);
        dishCategoryGridPane.setVgap(50);
        AtomicInteger row = new AtomicInteger();
        //
        dishCategories.forEach(e -> {
            Button button = new Button(e.getName());
            button.setPrefWidth(150);
            button.setPrefHeight(50);
            button.setOnMouseClicked(event -> {
                ObservableList<Dish> dishes = OrderRepository.getDishList(e);
                updateDishTableView(dishes);
            });
            dishCategoryGridPane.add(button, 0, row.getAndIncrement());
        });
        dishCategoryGridPane.setPadding(new Insets(50, 0, 0, 0));
        dishCategoryGridPane.setAlignment(Pos.CENTER);
        dishCategoryPane.setContent(dishCategoryGridPane);

        //


        //search
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.trim().equals("") && !newValue.isEmpty()) {
                ObservableList<Dish> dishes = OrderRepository.getDishList(newValue.trim());
                if (dishes.isEmpty()) {
                    updateDishTableView(null);
                } else {
                    updateDishTableView(dishes);
                }
            }
        });

        //table of dish

        imageColumn.setCellValueFactory(new PropertyValueFactory<Dish, ImageView>("image"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Dish, String>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Dish, Double>("price"));
        dishTableView.setPlaceholder(new Label("There are no matching dishes"));

        ObservableList<Dish> dishes = OrderRepository.getDishList();
        updateDishTableView(dishes);
        //

        //get old data if exists
        Optional<Order> oldOrder = Optional.empty();
        if(dinnerTable.getId() != -1){
            oldOrder = OrderRepository.getOrderFromTable(dinnerTable);
        }
        if (oldOrder.isPresent()) {
            ArrayList<OrderItem> oldOrderItem = oldOrder.get().getOrderItems();
            oldOrderItem.forEach(orderItem -> {
                dishes.forEach(dish -> {
                    if (dish.getId() == orderItem.getDish().getId()) {
                        orderedItems.put(dish, orderItem.getQuantity());
                    }
                });
            });
            orderedItems.forEach((k, v) -> {
                orderedVBox.getChildren().add(createNewOrderHBox(k));
            });
        }
        //
        //order
        dishTableView.setOnMouseClicked(event -> {
            Dish selectedDish = dishTableView.getSelectionModel().getSelectedItem();
            if (selectedDish != null) {
                updateOrdered(selectedDish);
            }
        });


        //

        updateTotal();
        //
        orderedItems.addListener((MapChangeListener<? super Dish, ? super Integer>) (observable) -> {
            if (!orderedItems.isEmpty()) {
                if(dinnerTable.getId() != -1){
                    saveButton.setDisable(false);
                }
                payButton.setDisable(false);
                updateTotal();
            } else {
                saveButton.setDisable(true);
                payButton.setDisable(true);
            }
        });
        //save
        saveButton.setDisable(true);
        Optional<Order> finalOldOrder = oldOrder;
        saveButton.setOnMouseClicked(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Notification");
            alert.setHeaderText("Save Order Notice");
            if (orderProcess(finalOldOrder, false)) {
                alert.setContentText("Save success");
            } else {
                alert.setContentText("Save False");
            }
            alert.showAndWait();
            Stage stage = (Stage) (orderBorderPane.getScene().getWindow());
            stage.close();
        });

        //pay
        if (orderedItems.isEmpty()) {
            payButton.setDisable(true);
        }
        payButton.setOnMouseClicked(event -> {
            orderProcess(finalOldOrder, true);
        });
    }

    public void updateDishTableView(ObservableList<Dish> dishes) {
        dishTableView.setItems(dishes);
    }

    public void updateOrdered(Dish selectedDish) {
        if (orderedItems.containsKey(selectedDish)) {
            orderedItems.put(selectedDish, orderedItems.get(selectedDish) + 1);
            for (int i = 0; i < orderedVBox.getChildren().size(); i++) {
                HBox nowHBox = (HBox) orderedVBox.getChildren().get(i);
                Label nowLabel = (Label) nowHBox.getChildren().get(0);
                int nowDishId = Integer.valueOf(nowLabel.getId());
                if (selectedDish.getId() == nowDishId) {
                    ((TextField) nowHBox.getChildren().get(2)).setText(String.valueOf(orderedItems.get(selectedDish)));
                }
            }
            updateTotal();
        } else {
            orderedItems.put(selectedDish, 1);
            HBox newOrderHBox = createNewOrderHBox(selectedDish);
            orderedVBox.getChildren().add(newOrderHBox);
        }
    }

    public HBox createNewOrderHBox(Dish selectedDish) {
        HBox newOrderHBox = new HBox();

        newOrderHBox.setPrefWidth(300);
        newOrderHBox.setPrefHeight(100);
        newOrderHBox.setAlignment(Pos.CENTER_LEFT);

        Label newDishName = new Label(selectedDish.getName());
        newDishName.setId(String.valueOf(selectedDish.getId()));

        TextField quantity = new TextField(String.valueOf(orderedItems.get(selectedDish)));
        quantity.setPrefWidth(30);
        quantity.setPrefHeight(30);
        quantity.textProperty().addListener((observable, oldValue, newValue) -> {
            if (Integer.valueOf(newValue) <= 0) {
                orderedVBox.getChildren().remove(newOrderHBox);
                orderedItems.remove(selectedDish);
            }
            updateTotal();
        });

        Button reduce = new Button("-");
        reduce.setPrefWidth(40);
        reduce.setPrefHeight(30);
        reduce.setOnMouseClicked(event -> {
            if (orderedItems.get(selectedDish) > 0) {
                orderedItems.put(selectedDish, orderedItems.get(selectedDish) - 1);
            }
            quantity.setText(String.valueOf(orderedItems.get(selectedDish)));
        });


        Button increase = new Button("+");
        increase.setPrefWidth(40);
        increase.setPrefHeight(30);
        increase.setOnMouseClicked(event -> {
            orderedItems.put(selectedDish, orderedItems.get(selectedDish) + 1);
            quantity.setText(String.valueOf(orderedItems.get(selectedDish)));
        });

        newOrderHBox.getChildren().addAll(newDishName, reduce, quantity, increase);
        return newOrderHBox;
    }

    public Double updateTotal() {
        AtomicReference<Double> total = new AtomicReference<>(0.0);
        orderedItems.forEach((k, v) -> {
            total.updateAndGet(v1 -> v1 + k.getPrice() * v);
        });
        totalLabel.setText("$" + String.valueOf(total.get()));
        return total.get();
    }

    public boolean orderProcess(Optional<Order> oldOrder, Boolean pay) {
        Boolean flag = false;
        ArrayList<OrderItem> orderItemList = new ArrayList<>();
        orderedItems.forEach((dish, quantity) -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setDish(dish);
            orderItem.setQuantity(quantity);
            orderItem.updateTotal();
            orderItemList.add(orderItem);
        });
        Order order = new Order();
        order.setTable(this.dinnerTable);
        order.setOrderItems(orderItemList);
        Date date = new Date();
        order.setUpdateAt(new Timestamp(date.getTime()));
        order.setTotalMoney(updateTotal());
        order.setStatus(pay);
        if (!oldOrder.isPresent()) {
            flag = OrderRepository.insertNewOrder(order);
        } else {
            order.setId(oldOrder.get().getId());
            flag = OrderRepository.updateOrder(order);
        }
        if(dinnerTable.getId() != -1){
            dinnerTable.setInUse(!pay);
        }
        else{
            dinnerTable.setInUse(false);
        }
        if (pay) {
            toBill(order);
        }
        OrderRepository.updateTable(dinnerTable);
        return flag;
    }

    public void toBill(Order thisOrder) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("../view/Bill.fxml"));
        BillController controller = new BillController();
        controller.setThisOrder(thisOrder);
        loader.setController(controller);
        try {
            Parent billRoot = loader.load();
            Scene scene = new Scene(billRoot);
            Stage curStage = (Stage) orderBorderPane.getScene().getWindow();
            curStage.setX(500);
            curStage.setY(50);
            curStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDinnerTable(DinnerTable dinnerTable) {
        this.dinnerTable = dinnerTable;
    }
}