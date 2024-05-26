

import org.firmata4j.I2CDevice;
import org.firmata4j.ssd1306.MonochromeCanvas;
import org.firmata4j.ssd1306.SSD1306;

import java.awt.*;

/**

 The OledDisplay class represents an OLED display.

 It is responsible for updating and displaying information on the display.
 */
public class OledDisplay {
    private SSD1306 display;
   // MonochromeCanvas canvas;

    /**

     Constructs an OledDisplay object with the specified I2C device and display size.
     @param i2cDevice The I2C device for communication.
     @param size The size of the display.
     */
    public OledDisplay(I2CDevice i2cDevice, SSD1306.Size size) {
        display = new SSD1306(i2cDevice, size);
        display.init();
       // MonochromeCanvas canvas = display.getCanvas();
        System.out.println("Got the object of canvas");
    }
    /**

     Clears the display and updates it with the given message.
     @param message The message to be displayed on the OLED display.
     */
    public void updateDisplay(String message) {
        clearDisplay();
        drawMessage(message);
        display.display();
    }
    /**

     Clears the display and updates it with the given message.
     @param message The message to be displayed on the OLED display.
     */
    public void updateDisplayMultipleLines(String[] message) {
        clearDisplay();
        drawMessageMultipleLines(message);
        display.display();
    }
    /**

     Clears the display by filling it with blank content.
     */
    public void clearDisplay() {
        MonochromeCanvas canvas = display.getCanvas();
        canvas.clear();
        display.clear();
        display.display();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    /**

     Draws the given message on the display.
     @param message The message to be displayed on the OLED display.
     */
    private void drawMessage(String message) {
        MonochromeCanvas canvas = display.getCanvas();
        canvas.write(message);
        canvas.setTextsize(1);
        //canvas.drawHorizontalLine(0, 40, 100, MonochromeCanvas.Color.BRIGHT);
        //canvas.drawCircle(25, 25, 5, MonochromeCanvas.Color.BRIGHT);
    }
    private void drawMessageMultipleLines(String[] message) {
        MonochromeCanvas canvas = display.getCanvas();
        canvas.setTextsize(1);
        if(message[0] != null && !message[0].equals(""))
            canvas.drawString(0,0, message[0]);
        if(message[1] != null && !message[1].equals(""))
            canvas.drawString(0,10, message[1]);
        if(message[2] != null && !message[2].equals(""))
            canvas.drawString(0,20, message[2]);
        if(message[3] != null && !message[3].equals(""))
            canvas.drawString(20,40, message[3]);
    }
}