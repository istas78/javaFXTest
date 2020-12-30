package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Data
@Getter
@Setter
public class Button {

    private String name;
    private String title;
    private String code;
    private String description;

    public Button(String name, String title, String code, String description) {
        this.name = name;
        this.title = title;
        this.code = code;
        this.description = description;
    }
}