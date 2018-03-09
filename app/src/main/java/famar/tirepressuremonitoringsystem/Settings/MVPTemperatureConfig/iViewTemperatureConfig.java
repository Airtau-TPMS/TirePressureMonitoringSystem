package famar.tirepressuremonitoringsystem.Settings.MVPTemperatureConfig;

import famar.tirepressuremonitoringsystem.pojo.MyStdDefinitions;

public interface iViewTemperatureConfig
{
    void viewSetTemperatureUnit(MyStdDefinitions.TemperatureUnit temperatureUnit);
    void viewSetTxtUpperLimitTemperature(float value, String unit_str);
    void viewSetSeekbarUpperLimitTemperature(int value);
}
