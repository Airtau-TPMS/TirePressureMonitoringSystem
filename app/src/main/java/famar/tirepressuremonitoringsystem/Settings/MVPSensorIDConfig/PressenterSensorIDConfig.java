package famar.tirepressuremonitoringsystem.Settings.MVPSensorIDConfig;

import android.util.Log;

import famar.tirepressuremonitoringsystem.pojo.MyStdDefinitions;

import static famar.tirepressuremonitoringsystem.pojo.MyStdDefinitions.SensorIndex.SENSOR_FL;
import static famar.tirepressuremonitoringsystem.pojo.MyStdDefinitions.SensorIndex.SENSOR_FR;
import static famar.tirepressuremonitoringsystem.pojo.MyStdDefinitions.SensorIndex.SENSOR_RL;
import static famar.tirepressuremonitoringsystem.pojo.MyStdDefinitions.SensorIndex.SENSOR_RR;

public class PressenterSensorIDConfig
{
    private String TAG = "TagPressenter";
    private ModelSensorIDConfig modelSensorIDConfig;
    private iViewSensorIDConfig viewSensorIDConfig;

    public PressenterSensorIDConfig(ModelSensorIDConfig modelSensorIDConfig, iViewSensorIDConfig viewSensorIDConfig)
    {
        Log.d(TAG, "PressenterSensorIDConfig");
        this.modelSensorIDConfig = modelSensorIDConfig;
        this.viewSensorIDConfig = viewSensorIDConfig;
    }

    public void initViewObjects()
    {
        Log.d(TAG, "initViewObjects");
        updateViewSensorID();
    }

    public void updateViewSensorID()
    {
        Log.d(TAG, "updateViewLowerLimitSensorID");
        viewSensorIDConfig.viewSetTxtSensorIdFL(modelSensorIDConfig.getSensorID(SENSOR_FL));
        viewSensorIDConfig.viewSetTxtSensorIdFR(modelSensorIDConfig.getSensorID(SENSOR_FR));
        viewSensorIDConfig.viewSetTxtSensorIdRL(modelSensorIDConfig.getSensorID(SENSOR_RL));
        viewSensorIDConfig.viewSetTxtSensorIdRR(modelSensorIDConfig.getSensorID(SENSOR_RR));
    }
    
    public void setValueSensorID(MyStdDefinitions.SensorIndex index, String sensorID)
    {
        Log.d(TAG, "setValueSensorID");
        modelSensorIDConfig.setSensorID(index, sensorID);
    }
}
