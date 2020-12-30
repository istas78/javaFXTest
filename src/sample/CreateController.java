package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class CreateController {
  /*  private Stage dialogStage;
    private Dog dog;

    @FXML
    private TextField nickName;

    @FXML
    private void ok() {
        if (nickName != null && !nickName.getText().isEmpty()) {
            dog.setName(new SimpleStringProperty(nickName.getText()));
            dialogStage.close();
        }
    }

    @FXML
    private void cansel() {
        dialogStage.close();
    }*/
}