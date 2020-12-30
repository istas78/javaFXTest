package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

/*public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}*/
@Data
@Setter
@Getter
public class AppFX {

    private Stage primaryStage;
    private AnchorPane rootLayout;
    private ObservableList listButton = FXCollections.observableArrayList();
    public ObservableList getListButton() {
        return listButton;
    }

    public AppFX() {
        listButton.add(new Button("Button1", "Button1", "1", "Button1 function is ..."));
        listButton.add(new Button("Button1", "Button1", "2", "Button1 function is ..."));
        listButton.add(new Button("Button1", "Button1", "3", "Button1 function is ..."));
        listButton.add(new Button("Button1", "Button1", "4", "Button1 function is ..."));
        listButton.add(new Button("Button1", "Button1", "5", "Button1 function is ..."));
        listButton.add(new Button("Button1", "Button1", "6", "Button1 function is ..."));
    }


   /* @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Buttons application");
        showBaseWindow();
    }*/
    public void showBaseWindow() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AppFX.class.getResource("properties.fxml"));
            rootLayout = loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            PropertiesController controller = loader.getController();
          //  controller.setAppFX(this);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
  /*  public void showCreateWindow(Button Button) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AppFX.class.getResource("new.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Wow Wow Wow");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.setScene(new Scene(page));
            CreateController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setButton(Button);
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}
