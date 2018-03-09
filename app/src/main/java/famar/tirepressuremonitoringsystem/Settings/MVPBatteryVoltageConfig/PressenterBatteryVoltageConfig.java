package famar.tirepressuremonitoringsystem.Settings.MVPBatteryVoltageConfig;

import android.util.Log;

import famar.tirepressuremonitoringsystem.ConversionTables.ConversionTablesBatteryVoltage;

public class PressenterBatteryVoltageConfig
{
    private String TAG = "TagPressenter";
    private ModelBatteryVoltageConfig modelBatteryVoltageConfig;
    private iViewBatteryVoltageConfig viewBatteryVoltageConfig;
    private ConversionTablesBatteryVoltage conversionTablesBatteryVoltage;

    public PressenterBatteryVoltageConfig(ModelBatteryVoltageConfig modelBatteryVoltageConfig, iViewBatteryVoltageConfig viewBatteryVoltageConfig)
    {
        Log.d(TAG, "PressenterBatteryVoltageConfig");
        this.modelBatteryVoltageConfig = modelBatteryVoltageConfig;
        this.viewBatteryVoltageConfig = viewBatteryVoltageConfig;
        conversionTablesBatteryVoltage = new ConversionTablesBatteryVoltage();
    }

    public void initViewObjects()
    {
        Log.d(TAG, "initViewObjects");
        updateViewLowerLimitBatteryVoltage();
    }


    public void updateViewLowerLimitBatteryVoltage()
    {
        Log.d(TAG, "updateViewLowerLimitBatteryVoltage");
        int value = conversionTablesBatteryVoltage.convert_to_percentage(modelBatteryVoltageConfig.getLowerLimitBatteryVoltage(), modelBatteryVoltageConfig.getMaxLowerLimitBatteryVoltage(), modelBatteryVoltageConfig.getMinLowerLimitBatteryVoltage());
        viewBatteryVoltageConfig.viewSetSeekbarLowerLimitBatteryVoltage(value);
        viewBatteryVoltageConfig.viewSetTxtLowerLimitBatteryVoltage((float)modelBatteryVoltageConfig.getLowerLimitBatteryVoltage()/1000, "v");
    }
    
    public void updateValueLowerLimitBatteryVoltage(int percentage)
    {
        Log.d(TAG, "updateValueLowerLimitBatteryVoltage");
        int value = conversionTablesBatteryVoltage.convert_from_percentage(percentage, modelBatteryVoltageConfig.getMaxLowerLimitBatteryVoltage(), modelBatteryVoltageConfig.getMinLowerLimitBatteryVoltage());
        viewBatteryVoltageConfig.viewSetTxtLowerLimitBatteryVoltage((float)value/1000, "v");
    }

    public void setValueLowerLimitBatteryVoltage(int percentage)
    {
        Log.d(TAG, "setValueLowerLimitBatteryVoltage");
        int value = conversionTablesBatteryVoltage.convert_from_percentage(percentage, modelBatteryVoltageConfig.getMaxLowerLimitBatteryVoltage(), modelBatteryVoltageConfig.getMinLowerLimitBatteryVoltage());
        modelBatteryVoltageConfig.setLowerLimitBatteryVoltage(value);
    }
}
