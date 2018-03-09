package famar.tirepressuremonitoringsystem.MainApplication.MVPSensorData;

import famar.tirepressuremonitoringsystem.pojo.MyStdDefinitions;

public interface iViewSensorData
{
    void viewSetSensorID(MyStdDefinitions.SensorIndex index, String value, boolean validity);
    void viewSetPressure(MyStdDefinitions.SensorIndex index, float value,String unit_str, boolean alarm, boolean validity);
    void viewSetTemperature(MyStdDefinitions.SensorIndex index, float value, String unit_str, boolean alarm, boolean validity);
    void viewSetBatteryVoltage(MyStdDefinitions.SensorIndex index, float value, boolean alarm, boolean validity);
}
