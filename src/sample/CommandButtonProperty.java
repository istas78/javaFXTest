package sample;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
@JsonDeserialize(as = CommandButtonProperty.class)
public class CommandButtonProperty {
    private String propertyName;
    private String propertyValue;

    public CommandButtonProperty() {
    }

    public CommandButtonProperty(String propertyName, String propertyValue) {
        this.propertyName = propertyName;
        this.propertyValue = propertyValue;
    }
}
