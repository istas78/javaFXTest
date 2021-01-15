package sample;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.beans.decoder.ValueObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.Value;

import java.beans.*;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.prefs.Preferences;

public class PropertiesController {

    private List<List<CommandButtonProperty>> buttonList = new ArrayList<>();
    private List<String> columnNames = Arrays.asList("Button Name", "Button Title", "Code", "Description");
    private Preferences store = Preferences.userRoot();
    private SerialPortProperties serialPortProperties = new SerialPortProperties();
    private GridPane mainGrid;
    private ObjectMapper mapper = new ObjectMapper();
    private ObservableList<String> serialPorts;
    private ObservableList<Integer> baudRates = FXCollections.observableArrayList(110,300,600,1200,4800,9600,14400,19200,38400,57600,115200,256000);
    private ObservableList<Integer> dataBits = FXCollections.observableArrayList(5,6,7,8);
    private ObservableList<Integer> stopBits = FXCollections.observableArrayList(1,2);
    private ObservableList<Integer> parities = FXCollections.observableArrayList(0,1,2,3,4);


    public TabPane getTabPane() throws IntrospectionException {
        init();
        TabPane tabPane = new TabPane();
        Tab mainTab = new Tab("Main");
        mainTab.setContent(makeMainPane(makeMainGrid(false)));
        mainTab.closableProperty().set(false);
        Tab propertiesTab = new Tab("Properties");
        propertiesTab.setContent(makePropertiesPane(makePropertiesGrid()));
        propertiesTab.closableProperty().set(false);
        tabPane.getTabs().addAll(mainTab,propertiesTab);
        return tabPane;
    }
    public AnchorPane makePropertiesPane(GridPane gridPane){
        AnchorPane propertiesPane = new AnchorPane();
        VBox serialPortPropertiesVBox = makeSerialPortPropertiesVbox();
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(new EventHandler<ActionEvent>() {

            @SneakyThrows
            @Override
            public void handle(ActionEvent event) {
                saveProperties(gridPane);
            }
        });
        Button addNewButtonButton = new Button("Add");
        addNewButtonButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                addNewButton(gridPane);
            }
        });
        Button removeButtonButton = new Button("Remove");
        removeButtonButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                removeButton(gridPane);
            }
        });

        propertiesPane.getChildren().add(0,gridPane);
        propertiesPane.getChildren().add(1,serialPortPropertiesVBox);
        propertiesPane.getChildren().add(2,addNewButtonButton);
        propertiesPane.getChildren().add(3,removeButtonButton);
        propertiesPane.getChildren().add(4,submitButton);
        AnchorPane.setRightAnchor(submitButton, 10.0);
        AnchorPane.setBottomAnchor(submitButton, 10.0);
        AnchorPane.setRightAnchor(serialPortPropertiesVBox, 10.0);
        AnchorPane.setTopAnchor(serialPortPropertiesVBox, 10.0);
        AnchorPane.setLeftAnchor(addNewButtonButton, 10.0);
        AnchorPane.setBottomAnchor(addNewButtonButton, 10.0);
        AnchorPane.setLeftAnchor(removeButtonButton, 70.0);
        AnchorPane.setBottomAnchor(removeButtonButton, 10.0);
        return propertiesPane;
    }

    private  VBox makeSerialPortPropertiesVbox() {
        VBox vBox = new VBox();
        Label titleLabel = new Label("COM port settings");
        Label portLabel = new Label("PORT");
        ComboBox<String> portComboBox = new ComboBox<String>(serialPorts);
        portComboBox.setValue(serialPortProperties.getPort()); // устанавливаем выбранный элемент по умолчанию
        Label baudRateLabel = new Label("BAUDRATE");
        ComboBox<Integer> baudRateComboBox = new ComboBox<Integer>(baudRates);
        baudRateComboBox.setValue(serialPortProperties.getBaudRate()); // устанавливаем выбранный элемент по умолчанию
        Label dataBitLabel = new Label("DATABITS");
        ComboBox<Integer> dataBitComboBox = new ComboBox<Integer>(dataBits);
        dataBitComboBox.setValue(serialPortProperties.getDataBit()); // устанавливаем выбранный элемент по умолчанию
        Label stopBitLabel = new Label("STOPBITS");
        ComboBox<Integer> stopBitComboBox = new ComboBox<Integer>(stopBits);
        stopBitComboBox.setValue(serialPortProperties.getStopBit()); // устанавливаем выбранный элемент по умолчанию
        Label parityLabel = new Label("PARITY");
        ComboBox<Integer> parityComboBox = new ComboBox<Integer>(parities);
        parityComboBox.setValue(serialPortProperties.getParity()); // устанавливаем выбранный элемент по умолчанию
        portComboBox.setOnAction(event -> serialPortProperties.setPort(portComboBox.getValue()));
        baudRateComboBox.setOnAction(event -> serialPortProperties.setBaudRate(baudRateComboBox.getValue()));
        dataBitComboBox.setOnAction(event -> serialPortProperties.setDataBit(dataBitComboBox.getValue()));
        stopBitComboBox.setOnAction(event -> serialPortProperties.setStopBit(stopBitComboBox.getValue()));
        parityComboBox.setOnAction(event -> serialPortProperties.setParity(parityComboBox.getValue()));
        vBox.getChildren().addAll(
                titleLabel,
                portLabel,
                portComboBox,
                baudRateLabel,
                baudRateComboBox,
                dataBitLabel,
                dataBitComboBox,
                stopBitLabel,
                stopBitComboBox,
                parityLabel,
                parityComboBox);
        return vBox;
    }

    private void removeButton(GridPane gridPane) {
        if(buttonList.size() > 0) {
            gridPane.getChildren().remove(buttonList.size() * 4, buttonList.size() * 4 + buttonList.get(0).size());
            buttonList.remove(buttonList.size() - 1);
        }
    }

    public AnchorPane makeMainPane(GridPane gridPane){
        AnchorPane mainPane = new AnchorPane();
        mainPane.getChildren().add(0,gridPane);
        return mainPane;
    }

    private void addNewButton(GridPane gridPane) {
        int buttonCount = buttonList.size() + 1;
        if ( buttonCount <= 14 ) {
            List<CommandButtonProperty> buttonPropertyList = Arrays.asList(new CommandButtonProperty("Button Name", "Button" + buttonCount),
                    new CommandButtonProperty("Button Title", "Button" + buttonCount),
                    new CommandButtonProperty("Code", "" + buttonCount),
                    new CommandButtonProperty("Description", "Button" + buttonCount + " function is ..."));
            buttonList.add(buttonPropertyList);
            int columnIndex = 0;
            int rowIndexText = buttonCount;
            for (CommandButtonProperty property : buttonPropertyList) {
                final TextField field = new TextField();
                field.setPrefColumnCount(10);
                field.setText(property.getPropertyValue());
                GridPane.setConstraints(field, columnIndex++, rowIndexText);
                gridPane.getChildren().add(field);

            }
        }
    }

    public void saveProperties(GridPane gridPane) throws IntrospectionException {
        int childrenIndex=0;
        for(List<CommandButtonProperty> button: buttonList){
            if ( childrenIndex ==0 ) childrenIndex = button.size();
            //int columnIndex=0;
            for (CommandButtonProperty property : button) {
                TextField field = (TextField) gridPane.getChildren().get(childrenIndex);
                property.setPropertyValue(field.getText());
                childrenIndex++;
            }
        }
        writePreferences(store);
        mainGrid.getChildren().clear();
        makeMainGrid(true);
    }


    public GridPane makePropertiesGrid() throws IntrospectionException {
        //Creating a GridPane container
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);
        int columnIndex = 0;
        int rowIndexText = 0;
        int rowIndexLabel = 0;
        for(  List<CommandButtonProperty> button: buttonList){
            rowIndexText++;
            columnIndex=0;
            if(rowIndexText <= 1) {
                for (CommandButtonProperty property : button) {
                    final Label label = new Label();
                    label.setText(property.getPropertyName());
                    //label.setPrefWidth(10);
                    GridPane.setConstraints(label, columnIndex++, rowIndexLabel);
                    grid.getChildren().add(label);
                }
            }
                columnIndex=0;
            for (CommandButtonProperty property : button) {
                final TextField field = new TextField();
                field.setPrefColumnCount(10);
                field.setText(property.getPropertyValue());
                GridPane.setConstraints(field, columnIndex++, rowIndexText);
                grid.getChildren().add(field);

            }
        }
        return grid;
    }
    public GridPane makeMainGrid(boolean rebuild) throws IntrospectionException {
        //Creating a GridPane container
        if(!rebuild) {
            mainGrid = new GridPane();
            mainGrid.setPadding(new Insets(10, 10, 10, 10));
            mainGrid.setVgap(5);
            mainGrid.setHgap(5);
        }
        int columnIndex = 0;
        int rowIndex = 0;
        for(  List<CommandButtonProperty> button: buttonList){
            Button commandButton = new Button(button.get(1).getPropertyValue());
            commandButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    sendCommand(button.get(2).getPropertyValue());
                }
            });
                    GridPane.setConstraints(commandButton, columnIndex, rowIndex++);
                    mainGrid.getChildren().add(commandButton);
        }
    return mainGrid;
    }

    private void sendCommand(String propertyValue) {
        SerialPort serialPort = new SerialPort(serialPortProperties.getPort());
        try {
            //Открываем порт
            serialPort.openPort();
            //Выставляем параметры
            serialPort.setParams(serialPortProperties.getBaudRate(),
                    serialPortProperties.getDataBit(),
                    serialPortProperties.getStopBit(),
                    serialPortProperties.getParity());
            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
            //Включаем аппаратное управление потоком
            /*serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN |
                    SerialPort.FLOWCONTROL_RTSCTS_OUT);
            *///Устанавливаем ивент лисенер и маску
           // serialPort.addEventListener(new PortReader(), SerialPort.MASK_RXCHAR);
            //Отправляем запрос устройству
            serialPort.writeByte(propertyValue.getBytes()[0]);
            //System.out.println(serialPort.read));
            serialPort.closePort();
        }
        catch (SerialPortException ex) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("COM Port Error");
                alert.setContentText(ex.toString());
                alert.showAndWait();
        }
    }


    public void init() {
        String[] portNames = SerialPortList.getPortNames();
        if (portNames.length > 0){
            serialPorts = FXCollections.observableArrayList(portNames);
            serialPorts.add("No com ports");
        }
        if(!readPreferences(store)) {
            buttonList.add(Arrays.asList(new CommandButtonProperty("Button Name", "Button1"),
                                                            new CommandButtonProperty("Button Title", "Button1"),
                                                            new CommandButtonProperty("Code", "1"),
                                                            new CommandButtonProperty("Description", "Button1 function is ...")));
            buttonList.add(Arrays.asList(new CommandButtonProperty("Button Name", "Button2"),
                                                            new CommandButtonProperty("Button Title", "Button2"),
                                                            new CommandButtonProperty("Code", "2"),
                                                            new CommandButtonProperty("Description", "Button2 function is ...")));
            buttonList.add(Arrays.asList(new CommandButtonProperty("Button Name", "Button3"),
                                                            new CommandButtonProperty("Button Title", "Button3"),
                                                            new CommandButtonProperty("Code", "3"),
                                                            new CommandButtonProperty("Description", "Button3 function is ...")));
            serialPortProperties.setBaudRate(9600);
            serialPortProperties.setDataBit(8);
            serialPortProperties.setStopBit(1);

            if (portNames.length > 0){
                serialPortProperties.setPort(portNames[0]);
                serialPorts = FXCollections.observableArrayList(portNames);
            }else {
                serialPortProperties.setPort("No com ports");
                serialPorts = FXCollections.observableArrayList("No com ports");
            }

            writePreferences(store);
        }
        addButtonsToMainTab(buttonList);
    }

    private void addButtonsToMainTab(List<List<CommandButtonProperty>> buttonList) {

    }

    public  boolean readPreferences(Preferences store){
        try {
            buttonList = (ArrayList) deserializeJsonConfig(store.get("jsonConfig", ""));
            serialPortProperties = mapper.readValue(store.get("jsonSerialPortConfig", ""), SerialPortProperties.class);
            return true;
        }
        catch (Exception e){
            buttonList.clear();
            return false;
        }


    }

    public  boolean writePreferences(Preferences store){
        try {
            store.put("jsonConfig", serializeJsonConfig(buttonList));
            store.put("jsonSerialPortConfig", mapper.valueToTree(serialPortProperties).toString());
            return true;
        }
        catch (Exception e){
            return false;
        }
    }


    public List<List<CommandButtonProperty>> deserializeJsonConfig(String json) throws JsonProcessingException {

        JsonNode tree = mapper.readTree(json);
        ArrayList list1 = new ArrayList();

        for (JsonNode jsonNode : tree) {
            JsonNode tree1 = mapper.readTree(jsonNode.toString());
            List list = new ArrayList<CommandButtonProperty>();
            for (JsonNode jsonNode1 : tree1) {
                CommandButtonProperty property =  mapper.readValue(jsonNode1.toString(), CommandButtonProperty.class);
                list.add(property);
            }
            list1.add(list);
        }
        return list1;
    }
   /* public List deserializeJsonConfig(String jsonString) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<CommandButton> myObjects = Arrays.asList(mapper.readValue(jsonString, CommandButton[].class));

        return myObjects;
    }*/
    public String serializeJsonConfig(List buttonList){
        ArrayNode arrayNode = mapper.createArrayNode();
        for (Object button : buttonList){
            arrayNode.addPOJO(button);
        }
        return arrayNode.toString();
    }



}