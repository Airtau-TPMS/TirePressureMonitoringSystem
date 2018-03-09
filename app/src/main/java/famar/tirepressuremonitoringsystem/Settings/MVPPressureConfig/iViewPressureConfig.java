package famar.tirepressuremonitoringsystem.Settings.MVPPressureConfig;

import famar.tirepressuremonitoringsystem.pojo.MyStdDefinitions;

public interface iViewPressureConfig
{
    void viewSetPressureUnit(MyStdDefinitions.PressureUnit pressureUnit);
    void viewSetTxtUpperLimitPressure(float value, String unit_str);
    void viewSetSeekbarUpperLimitPressure(int value);
    void viewSetTxtLowerLimitPressure(float value, String unit_str);
    void viewSetSeekbarLowerLimitPressure(int value);
    void viewSetPlayAlarm(boolean playAlarm);
}
