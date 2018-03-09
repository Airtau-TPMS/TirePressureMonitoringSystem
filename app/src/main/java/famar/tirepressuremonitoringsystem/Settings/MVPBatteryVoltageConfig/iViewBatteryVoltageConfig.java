package famar.tirepressuremonitoringsystem.Settings.MVPBatteryVoltageConfig;

public interface iViewBatteryVoltageConfig
{
    void viewSetTxtLowerLimitBatteryVoltage(float value, String unit_str);
    void viewSetSeekbarLowerLimitBatteryVoltage(int value);
}
