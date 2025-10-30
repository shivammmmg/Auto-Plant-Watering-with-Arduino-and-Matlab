# 🌿 Auto Plant Watering System (Arduino + Java + MATLAB)

An automated plant watering system that monitors soil moisture levels and controls a water pump using **Arduino**, **Java**, and **MATLAB**.  
It features **real-time data visualization**, **OLED feedback**, and **automatic logging** of watering actions.

---
🎯 **Objective**
To design and implement an automated plant watering system that integrates **Arduino**, **Java**, and **MATLAB** for real-time soil moisture monitoring, intelligent pump control, and data visualization — demonstrating the fusion of software engineering and embedded systems.
---

## 🧩 Features
- 💧 Automatic watering based on soil moisture readings  
- 📊 Real-time graph of moisture levels using **JFreeChart**  
- 🖥️ OLED display with live system messages  
- 🧠 Smart control logic (multi-level thresholds for dry, moderate, and wet soil)  
- 🧾 Event logging with timestamps  
- 🧱 Modular design (Java + Firmata + I2C)  

---

## ⚙️ Hardware Components
| Component | Description |
|------------|-------------|
| **Arduino / Grove Board** | Core microcontroller |
| **Capacitive Moisture Sensor** | Measures soil moisture level |
| **MOSFET Module** | Drives the water pump |
| **Water Pump + Tubing** | Delivers water to the plant |
| **SSD1306 OLED Display (I2C)** | Displays status and logs |
| **Push Button** | Allows manual exit/shutdown |
| **Power Source & Battery** | For standalone operation |


---


## 🌿 Project Setup & Flow

Here’s the actual hardware setup of my **Auto Plant Watering System** and the **system flow diagram** that shows how it works.

<p align="center">
  <img src="https://github.com/shivammmmg/Auto-Plant-Watering-with-Arduino-and-Matlab/blob/main/Setup.jpg" width="600">
  <br>
  <em>Figure 1: Full hardware setup with Grove board, moisture sensor, pump, and MATLAB interface.</em>
</p>

<p align="center">
  <img src="https://github.com/shivammmmg/Auto-Plant-Watering-with-Arduino-and-Matlab/blob/main/Flowchart.png" width="600">
  <br>
  <em>Figure 2: System flowchart showing automated control logic.</em>
</p>


---

## 💻 Software Overview
**Languages & Libraries**
- Java (main logic + GUI)
- Firmata4j (Arduino communication)
- JFreeChart (graphing moisture trends)
- MATLAB (initial data testing & model verification)

**Key Files**
| File | Description |
|------|--------------|
| `Logger.java` | Handles timestamped logs |
| `OledDisplay.java` | Controls the OLED via I2C |
| `MoistureControllerChart.java` | Real-time plotting of sensor data |
| `Watering.java` | Core system logic (Arduino interface + watering routine) |
| `Minor_Project_EECS_1011_ShivamGupta_219923309.docx` | Original project report |

---

## 🧠 System Logic
1. The system reads the analog moisture sensor value.  
2. Based on the moisture level:
   - ≥ 650 → **Dry soil** → Pump ON for 2 seconds  
   - 500–649 → **Moderately dry** → Pump ON for 1 second  
   - < 500 → **Wet soil** → Pump OFF  
3. OLED displays system messages:
   - Soil condition  
   - Pump status  
   - Current moisture reading  
4. All actions are logged with timestamps in the console.
5. Data is plotted live using JFreeChart.
6. System stops when the button is pressed or process completes.

---
🖥️ Example Console Output
<pre> Arduino board started for the watering process. 
   Soil is dry, Watering for 2 seconds 
   Soil is now wet after 4 seconds of watering 
   
   ********* Watering Process Log: ********* 
   2023-04-23 15:10:22 Soil is dry, Watering for 2 seconds 
   2023-04-23 15:10:26 Soil is now wet after 4 seconds of watering 
   
   Total Watering Time was 4 Seconds </pre>


---

## 📈 MATLAB Integration
MATLAB was used for:
- Reading analog sensor values using `readVoltage()`
- Sending control signals via `writeDigitalPin()`
- Graphing voltage vs. time to validate moisture behavior
- Verifying sensor calibration and threshold performance

---

## 🧑‍💻 Author
**Shivam Gupta**  
🎓 B.Eng. Software Engineering @ York University   
📧 inbox11shivam@gmail.com  
🔗 [LinkedIn](https://linkedin.com/in/shivammmmg) • [Portfolio](https://shivammmmg.com)

---

## 📚 References
- [Grove Beginner Kit for Arduino](https://wiki.seeedstudio.com/Grove-Beginner-Kit-For-Arduino/)  
- [SSD1306 OLED (Firmata4j)](https://github.com/kurbatov/firmata4j)  
- [Capacitive Moisture Sensor](https://www.seeedstudio.com/Grove-Capacitive-Moisture-Sensor-Corrosion-Resistant.html)  
- [MATLAB Arduino I/O Library](https://www.mathworks.com/help/supportpkg/arduinoio/ref/readvoltage.html)

