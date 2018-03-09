package famar.tirepressuremonitoringsystem.Settings.MVPSensorIDConfig;

import android.content.Context;

import famar.tirepressuremonitoringsystem.DataStorage.SharedPreferenceHelper;
import famar.tirepressuremonitoringsystem.pojo.MyStdDefinitions;

import static famar.tirepressuremonitoringsystem.pojo.MyStdDefinitions.NUM_SENSORS;
import static famar.tirepressuremonitoringsystem.pojo.MyStdDefinitions.SensorIndex.SENSOR_FL;
import static famar.tirepressuremonitoringsystem.pojo.MyStdDefinitions.SensorIndex.SENSOR_FR;
import static famar.tirepressuremonitoringsystem.pojo.MyStdDefinitions.SensorIndex.SENSOR_RL;
import static famar.tirepressuremonitoringsystem.pojo.MyStdDefinitions.SensorIndex.SENSOR_RR;

public class ModelSensorIDConfig
{
    private SharedPreferenceHelper sharedPreferenceHelper;
    private Context context;

    private String[] sensorID = new String[NUM_SENSORS];

    public ModelSensorIDConfig(Context context)
    {
        this.context = context;
        sharedPreferenceHelper = new SharedPreferenceHelper(context);

        // Get the stored value
        this.sensorID[SENSOR_FL.ordinal()] = sharedPreferenceHelper.getSensorID(SENSOR_FL);
        this.sensorID[SENSOR_FR.ordinal()] = sharedPreferenceHelper.getSensorID(SENSOR_FR);
        this.sensorID[SENSOR_RL.ordinal()] = sharedPreferenceHelper.getSensorID(SENSOR_RL);
        this.sensorID[SENSOR_RR.ordinal()] = sharedPreferenceHelper.getSensorID(SENSOR_RR);
    }

    public String getSensorID(MyStdDefinitions.SensorIndex index)
    {
        return sensorID[index.ordinal()];
    }

    public void setSensorID(MyStdDefinitions.SensorIndex index, String sensorID)
    {
        this.sensorID[index.ordinal()] = sensorID;
        sharedPreferenceHelper.setSensorID(index, sensorID);
    }
}
