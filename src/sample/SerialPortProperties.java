package sample;

import jssc.SerialPort;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.util.List;
@Getter
@Setter
public class SerialPortProperties {
    private String port;
    private int baudRate;
    private int dataBit;
    private int stopBit;
    private int parity;

    public SerialPortProperties() {
    }
}