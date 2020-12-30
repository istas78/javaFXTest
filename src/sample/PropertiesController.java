package sample;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.util.*;
import java.util.prefs.Preferences;

public class PropertiesController {

    private List<Button> buttonList = new ArrayList();
    private List<String> columnNames = Arrays.asList("Button Name", "Button Title", "Code", "Description");


    public VBox getVbox() {
        VBox vbox = new VBox();
        TableColumn<Map, String> firstDataColumn = new TableColumn<>(columnNames.get(0));
        TableColumn<Map, String> secondDataColumn = new TableColumn<>(columnNames.get(1));
        TableColumn<Map, String> ferdDataColumn = new TableColumn<>(columnNames.get(2));
        TableColumn<Map, String> forthDataColumn = new TableColumn<>(columnNames.get(3));
        firstDataColumn.setCellValueFactory(new MapValueFactory(columnNames.get(0)));
        secondDataColumn.setCellValueFactory(new MapValueFactory(columnNames.get(1)));
        ferdDataColumn.setCellValueFactory(new MapValueFactory(columnNames.get(2)));
        forthDataColumn.setCellValueFactory(new MapValueFactory(columnNames.get(3)));
        firstDataColumn.setMinWidth(130);
        secondDataColumn.setMinWidth(130);
        ferdDataColumn.setMinWidth(130);
        forthDataColumn.setMinWidth(130);

        TableView table_view = new TableView<>(generateDataInMap());

        table_view.setEditable(true);
        table_view.getSelectionModel().setCellSelectionEnabled(true);
        table_view.getColumns().setAll(firstDataColumn, secondDataColumn, ferdDataColumn, forthDataColumn);
        Callback<TableColumn<Map, String>, TableCell<Map, String>>
                cellFactoryForMap = new Callback<TableColumn<Map, String>,
                TableCell<Map, String>>() {
            @Override
            public TableCell call(TableColumn p) {
                return new TextFieldTableCell(new StringConverter() {
                    @Override
                    public String toString(Object t) {
                        return t.toString();
                    }

                    @Override
                    public Object fromString(String string) {
                        return string;
                    }
                });
            }
        };
        firstDataColumn.setOnEditCommit((TableColumn.CellEditEvent<Map, String> t) -> {
            ((Map<String, String>) t.getTableView().getItems().get(t.getTablePosition().getRow())).put(columnNames.get(0), t.getNewValue());
        });
        secondDataColumn.setOnEditCommit((TableColumn.CellEditEvent<Map, String> t) -> {
            ((Map<String, String>) t.getTableView().getItems().get(t.getTablePosition().getRow())).put(columnNames.get(1), t.getNewValue());
        });
        ferdDataColumn.setOnEditCommit((TableColumn.CellEditEvent<Map, String> t) -> {
            ((Map<String, String>) t.getTableView().getItems().get(t.getTablePosition().getRow())).put(columnNames.get(2), t.getNewValue());
        });
        forthDataColumn.setOnEditCommit((TableColumn.CellEditEvent<Map, String> t) -> {
            ((Map<String, String>) t.getTableView().getItems().get(t.getTablePosition().getRow())).put(columnNames.get(3), t.getNewValue());
        });
        firstDataColumn.setCellFactory(cellFactoryForMap);
        secondDataColumn.setCellFactory(cellFactoryForMap);
        ferdDataColumn.setCellFactory(cellFactoryForMap);
        forthDataColumn.setCellFactory(cellFactoryForMap);
        ChangeListener<Object> listener = (obs, oldValue, newValue) -> {
            if (oldValue != newValue) {
                System.out.println(oldValue.toString() + "-----"+ newValue.toString());
            }
        };
        table_view.focusedProperty().addListener(listener);
        table_view.getSelectionModel().selectedItemProperty().addListener(listener);

        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(table_view);
        return vbox;
    }

    private ObservableList<Map> generateDataInMap() {
        init();
        ObservableList<Map> allData = FXCollections.observableArrayList();
        for (Button button : buttonList) {
            Map<String, String> dataRow = new HashMap<>();
            dataRow.put(columnNames.get(0), button.getName());
            dataRow.put(columnNames.get(1), button.getTitle());
            dataRow.put(columnNames.get(2), button.getCode());
            dataRow.put(columnNames.get(3), button.getDescription());
            allData.add(dataRow);
        }
        return allData;
    }

    public void init() {
        Preferences store = Preferences.userRoot();
        if(!readPreferences(store)) {
            buttonList.add(new Button("Button1", "Button1", "1", "Button1 function is ..."));
            buttonList.add(new Button("Button1", "Button1", "2", "Button1 function is ..."));
            buttonList.add(new Button("Button1", "Button1", "3", "Button1 function is ..."));
            buttonList.add(new Button("Button1", "Button1", "4", "Button1 function is ..."));
            buttonList.add(new Button("Button1", "Button1", "5", "Button1 function is ..."));
            buttonList.add(new Button("Button1", "Button1", "6", "Button1 function is ..."));
        }
    }
    public  boolean readPreferences(Preferences store){
        try {
            deserializeJsonConfig(store.get("jsonConfig"));
        }
        catch ();
        }

    };


}