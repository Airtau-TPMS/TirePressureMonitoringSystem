package famar.tirepressuremonitoringsystem.MainApplication.MVPSensorData;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

import famar.tirepressuremonitoringsystem.DataStorage.SharedPreferenceHelper;
import famar.tirepressuremonitoringsystem.pojo.MyStdDefinitions;

import static famar.tirepressuremonitoringsystem.pojo.MyStdDefinitions.NUM_SENSORS;

public class ModelSensorData
{
    private String TAG = "ModelSensorData";
    class SensorData
    {
        String sensorID;
        boolean validity;
        int pressureKPAx10; //Same as using hPA
        int temperatureC;
        int batteryVoltage;

        public SensorData()
        {
            this.sensorID = "";
            this.validity = false;
            this.pressureKPAx10 = 0;
            this.temperatureC = 0;
            this.batteryVoltage = 0;
        }
    }
    ArrayList<SensorData> sensorData = new ArrayList<SensorData>();

    public ModelSensorData()
    {
        /* Initialize the array */
        for(int i=0; i<NUM_SENSORS; i++)
        {
            sensorData.add(new SensorData());
        }
    }

    public void cleanSensorData(MyStdDefinitions.SensorIndex index)
    {
        sensorData.get(index.ordinal()).sensorID = "";
        sensorData.get(index.ordinal()).validity = false;
        sensorData.get(index.ordinal()).pressureKPAx10 = 0;
        sensorData.get(index.ordinal()).temperatureC = 0;
        sensorData.get(index.ordinal()).batteryVoltage = 0;
    }

    public void setSensorID(MyStdDefinitions.SensorIndex index, String sensorID)
    {
        sensorData.get(index.ordinal()).sensorID = sensorID;
    }

    public String getSensorID(MyStdDefinitions.SensorIndex index)
    {
        return sensorData.get(index.ordinal()).sensorID;
    }

    public void setValidity(MyStdDefinitions.SensorIndex index, boolean validity)
    {
        sensorData.get(index.ordinal()).validity = validity;
    }

    public boolean getValidity(MyStdDefinitions.SensorIndex index)
    {
        return sensorData.get(index.ordinal()).validity;
    }

    public float getPressureKPA(MyStdDefinitions.SensorIndex index)
    {
        float pressureHPA = (float) sensorData.get(index.ordinal()).pressureKPAx10;
        return (float)(pressureHPA/10);
    }

    public float getPressureBAR(MyStdDefinitions.SensorIndex index)
    {
        float pressureHPA = (float) sensorData.get(index.ordinal()).pressureKPAx10;
        return (float) (pressureHPA/1000);
    }

    public float getPressurePSI(MyStdDefinitions.SensorIndex index)
    {
        float pressureHPA = (float) sensorData.get(index.ordinal()).pressureKPAx10;
        return (float) (pressureHPA/68.9);
    }

    public void setPressureKPAx10(MyStdDefinitions.SensorIndex index, int pressureKPAx10)
    {
        sensorData.get(index.ordinal()).pressureKPAx10 = pressureKPAx10;
    }

    public int getTemperatureC(MyStdDefinitions.SensorIndex index)
    {
        return sensorData.get(index.ordinal()).temperatureC;
    }

    public int getTemperatureF(MyStdDefinitions.SensorIndex index)
    {
        float temperatureC = sensorData.get(index.ordinal()).temperatureC;
        return (int) (temperatureC*1.8+32);
    }

    public void setTemperatureC(MyStdDefinitions.SensorIndex index, int temperatureC)
    {
        sensorData.get(index.ordinal()).temperatureC = temperatureC;
    }

    public int getBatteryVoltage(MyStdDefinitions.SensorIndex index)
    {
        return sensorData.get(index.ordinal()).batteryVoltage;
    }

    public int setBatteryVoltage(MyStdDefinitions.SensorIndex index, int batteryVoltage)
    {
        return sensorData.get(index.ordinal()).batteryVoltage = batteryVoltage;
    }

}
