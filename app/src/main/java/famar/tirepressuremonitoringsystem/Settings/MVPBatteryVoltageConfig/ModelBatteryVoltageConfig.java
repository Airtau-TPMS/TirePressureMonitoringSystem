package famar.tirepressuremonitoringsystem.Settings.MVPBatteryVoltageConfig;

import android.content.Context;

import famar.tirepressuremonitoringsystem.DataStorage.SharedPreferenceHelper;

public class ModelBatteryVoltageConfig
{
    private SharedPreferenceHelper sharedPreferenceHelper;
    private Context context;

    private int maxLowerLimitBatteryVoltage;
    private int minLowerLimitBatteryVoltage;
    private int lowerLimitBatteryVoltage;

    private int MAX_LOWER_LIMIT_BATTERY_VOLTAGE = 3000;
    private int MIN_LOWER_LIMIT_BATTERY_VOLTAGE = 0;

    public ModelBatteryVoltageConfig(Context context)
    {
        this.context = context;
        sharedPreferenceHelper = new SharedPreferenceHelper(context);

        // Get the stored value
        this.lowerLimitBatteryVoltage = sharedPreferenceHelper.getLowerLimitBatteryVoltage();
        this.maxLowerLimitBatteryVoltage = MAX_LOWER_LIMIT_BATTERY_VOLTAGE;
        this.minLowerLimitBatteryVoltage = MIN_LOWER_LIMIT_BATTERY_VOLTAGE;
    }

    public int getLowerLimitBatteryVoltage()
    {
        return lowerLimitBatteryVoltage;
    }

    public void setLowerLimitBatteryVoltage(int lowerLimitBatteryVoltage)
    {
        this.lowerLimitBatteryVoltage = lowerLimitBatteryVoltage;

        // Store the value
        sharedPreferenceHelper.setLowerLimitBatteryVoltage(lowerLimitBatteryVoltage);
    }

    public int getMaxLowerLimitBatteryVoltage()
    {
        return maxLowerLimitBatteryVoltage;
    }

    public int getMinLowerLimitBatteryVoltage()
    {
        return minLowerLimitBatteryVoltage;
    }
}
