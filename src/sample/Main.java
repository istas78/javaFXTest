package sample;

import java.beans.IntrospectionException;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IntrospectionException {
        Scene scene = new Scene(new Group());
        stage.setTitle("Control APP v1.0");
        int width = 1000;
        int height = 600;
        stage.setWidth(width);
        stage.setHeight(height);
        PropertiesController controller = new PropertiesController();
        TabPane tabPane = controller.getTabPane();
        tabPane.setMinSize(width,height);
        ((Group) scene.getRoot()).getChildren().addAll(tabPane);

        stage.setScene(scene);

        stage.show();
    }


}
