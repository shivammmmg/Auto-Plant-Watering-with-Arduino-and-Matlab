import org.firmata4j.IODevice;
import org.firmata4j.Pin;
import org.firmata4j.firmata.FirmataDevice;
import org.firmata4j.I2CDevice;
import org.firmata4j.ssd1306.SSD1306;
import org.junit.Test;
import org.junit.Assert;

import java.io.IOException;

/**
 * The Watering class represents a system that automates the watering process based on the moisture level of a plant.
 * It interacts with an Arduino board, a moisture sensor, and an OLED display to monitor and control the watering process.
 */
public class Watering {

    private MoistureControllerChart moistureControllerChart;
    private boolean buttonPressed = false;
    private int wateringTime = 0;

    /**
     * Constructor to initialize the Watering process.
     */
    public Watering() {
        moistureControllerChart = new MoistureControllerChart("Moisture Level", "Time", "Moisture", MoistureControllerChart.ChartType.CURVE);
        try {
            process();
        } catch (IOException e) {
            System.out.println("IOException occurred: " + e.getMessage());
        }
    }

    /**
     * Starts the watering process.
     *
     * @throws IOException If there is an error in the I/O operations.
     */
    public void process() throws IOException {
        String port = "/dev/cu.SLAB_USBtoUART";
        IODevice groveBoard = new FirmataDevice(port);

        try {
            groveBoard.start();
            Logger.log("Arduino board started for the watering process.");
            groveBoard.ensureInitializationIsDone();
            performWatering(groveBoard);
            Logger.log("WATERING COMPLETED");
        } catch (InterruptedException ex) {
            System.out.println("Couldn't connect to the Arduino board.");
        } catch (Exception e) {
            System.out.println("Unexpected error occurred.");
            e.printStackTrace();
        } finally {
            groveBoard.stop();
            System.out.println("Arduino board stopped. End of the watering process.");
        }
    }

    /**
     * Performs the watering process.
     *
     * @param groveBoard The IODevice representing the Arduino board.
     * @throws IOException If there is an error in the I/O operations.
     * @throws InterruptedException If the thread is interrupted during sleep.
     */
    public void performWatering(IODevice groveBoard) throws IOException, InterruptedException {
        int moistureSensorPinNumber = 16;
        Pin moistureSensorPin = groveBoard.getPin(moistureSensorPinNumber);
        moistureSensorPin.setMode(Pin.Mode.ANALOG);

        Pin waterPumpPin = groveBoard.getPin(7);
        waterPumpPin.setMode(Pin.Mode.OUTPUT);

        I2CDevice i2cDevice = groveBoard.getI2CDevice((byte) 0x3C);
        OledDisplay oledDisplay = new OledDisplay(i2cDevice, SSD1306.Size.SSD1306_128_64);

        Pin buttonPin = groveBoard.getPin(6); // Replace 2 with the actual pin number for the button
        buttonPin.setMode(Pin.Mode.INPUT);

// Timer-based approach for 18-26 times a day
        int wateringFrequency = 26; // Number of times to run the watering process in a day
        long intervalMillis = 24 * 60 * 60 * 1000 / wateringFrequency; // Interval between each run

        long startTime = System.currentTimeMillis();
        long nextWateringTime = startTime + intervalMillis;
        String oledLines[] = new String[4];
        while (System.currentTimeMillis() < nextWateringTime) {
            oledLines[0] = "";
            oledLines[1] = "";
            oledLines[2] = "";
            oledLines[3]="";
            if(buttonPin.getValue() == 1){
                System.out.println("Button Pressed!");
                oledLines[0] = "Button Pressed";
                oledLines[1] = "Exiting the System";
                oledLines[2] = "Thank you";
                oledDisplay.updateDisplayMultipleLines(oledLines);
                buttonPressed = true;
                break; // Exit the loop if the button is pressed
            }
            double moistureLevel = moistureSensorPin.getValue();
            moistureControllerChart.addMoistureDataPoint("Moisture", System.currentTimeMillis(), moistureLevel); // Add moisture level to the graph

            if (moistureLevel >= 650) {
                Logger.log("Soil is dry, Watering for 2 seconds");
                waterPumpPin.setValue(1);
                oledLines[0] = "Soil is dry";

                oledLines[3] = "Pump is On";
                oledDisplay.updateDisplayMultipleLines(oledLines);
                Thread.sleep(2000);
                wateringTime = wateringTime + 2;
                waterPumpPin.setValue(0);
                oledLines[3] = "Pump is Off";
                oledLines[0] = "Waiting";
                oledLines[2] = "Moisture: "+ String.valueOf(moistureSensorPin.getValue());
                oledDisplay.updateDisplayMultipleLines(oledLines);
                Thread.sleep(3000);
            }
            else if (moistureLevel >= 500 && moistureLevel < 650)
            {
                waterPumpPin.setValue(1);
                Logger.log("Soil is not adequately Wet , Watering for 1 second");
                oledLines[0] = "Not Adequately Wet";
                oledLines[3] = "Pump is on";
                oledDisplay.updateDisplayMultipleLines(oledLines);
                oledLines[2] = "Moisture: "+ String.valueOf(moistureSensorPin.getValue());
                oledDisplay.updateDisplayMultipleLines(oledLines);
                Thread.sleep(1000);
                wateringTime = wateringTime + 1;
                waterPumpPin.setValue(0);
                oledLines[0] = "Pump is Off";
                oledLines[1] = "Waiting";
                oledLines[2] = "Moisture: "+ String.valueOf(moistureSensorPin.getValue());
                oledLines[3] = "";
                oledDisplay.updateDisplayMultipleLines(oledLines);
                Thread.sleep(1000);
                oledLines[2] = "Moisture: "+ String.valueOf(moistureSensorPin.getValue());
                oledDisplay.updateDisplayMultipleLines(oledLines);
                Thread.sleep(2000);

            } else {
                oledLines[0] = "Soil is Wet";
                oledLines[1] = "no more water needed";
                oledLines[2] = "Moisture: "+ String.valueOf(moistureSensorPin.getValue());
                oledLines[3] = "Pump is Off";
                Logger.log("Soil is now wet after "+wateringTime+ "seconds of watering");
                oledDisplay.updateDisplayMultipleLines(oledLines);
                Thread.sleep(5000);
            }
        }
        oledDisplay.clearDisplay();
        String log = Logger.getLog();
        System.out.println("********* Watering Process Log: *********");
        System.out.println(log);
        Logger.emptyLog();
        Logger.log("Total Watering Time was "+wateringTime+" Seconds");
        System.exit(0);
    }

    /**
     * Main method to start the watering process.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        try {
            new Watering();
        } finally {
            System.out.println("Inside finally");
            String log = Logger.getLog();
            if (log != null && !log.isEmpty()) {
                System.out.println("********* Watering Process Log: *********");
                System.out.println(log);
            }
            Logger.emptyLog();
        }
    }

    /**
     * JUnit test for MoistureControllerChart.
     */
    @Test
    public void testMoistureControllerChart() {
        MoistureControllerChart chart = new MoistureControllerChart("Test Chart", "X", "Y", MoistureControllerChart.ChartType.CURVE);
        chart.addMoistureDataPoint("TestSeries", System.currentTimeMillis(), 300);
        chart.addMoistureDataPoint("TestSeries", System.currentTimeMillis(), 400);
        Assert.assertEquals(2, chart.getDataWindow("TestSeries").size());
    }
}