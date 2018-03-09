package famar.tirepressuremonitoringsystem.Settings.MVPTemperatureConfig;

import android.util.Log;

import famar.tirepressuremonitoringsystem.ConversionTables.ConversionTablesTemperature;
import famar.tirepressuremonitoringsystem.pojo.MyStdDefinitions;

public class PressenterTemperatureConfig
{
    private String TAG = "TagPressenter";
    private ModelTemperatureConfig modelTemperatureConfig;
    private iViewTemperatureConfig viewTemperatureConfig;
    private ConversionTablesTemperature conversionTablesTemperature;

    public PressenterTemperatureConfig(ModelTemperatureConfig modelTemperatureConfig, iViewTemperatureConfig viewTemperatureConfig)
    {
        Log.d(TAG, "PressenterTemperatureConfig");
        this.modelTemperatureConfig = modelTemperatureConfig;
        this.viewTemperatureConfig = viewTemperatureConfig;
        conversionTablesTemperature = new ConversionTablesTemperature();
    }

    public void initViewObjects()
    {
        Log.d(TAG, "initViewObjects");
        updateViewTemperatureUnit();
        updateViewUpperLimitTemperature();
    }

    public void updateViewTemperatureUnit()
    {
        Log.d(TAG, "updateViewTemperatureUnit");
        viewTemperatureConfig.viewSetTemperatureUnit(modelTemperatureConfig.getTemperatureUnit());
    }

    public void updateViewUpperLimitTemperature()
    {
        Log.d(TAG, "updateViewUpperLimitTemperature");
        int value = conversionTablesTemperature.convert_to_percentage(modelTemperatureConfig.getUpperLimitTemperature(), modelTemperatureConfig.getTemperatureUnit(), modelTemperatureConfig.getMaxUpperLimitTemperature(), modelTemperatureConfig.getMinUpperLimitTemperature());
        viewTemperatureConfig.viewSetSeekbarUpperLimitTemperature(value);
        switch(modelTemperatureConfig.getTemperatureUnit())
        {
            case UNIT_C:
                viewTemperatureConfig.viewSetTxtUpperLimitTemperature((float)modelTemperatureConfig.getUpperLimitTemperature(), modelTemperatureConfig.getTemperatureUnitStr());
                break;
            case UNIT_F:
                viewTemperatureConfig.viewSetTxtUpperLimitTemperature((float)modelTemperatureConfig.getUpperLimitTemperature(), modelTemperatureConfig.getTemperatureUnitStr());
                break;
        }

    }

    public void updateValueUpperLimitTemperature(int percentage)
    {
        Log.d(TAG, "updateValueUpperLimitTemperature");
        int value = conversionTablesTemperature.convert_from_percentage(percentage, modelTemperatureConfig.getTemperatureUnit(), modelTemperatureConfig.getMaxUpperLimitTemperature(), modelTemperatureConfig.getMinUpperLimitTemperature());
        switch(modelTemperatureConfig.getTemperatureUnit())
        {
            case UNIT_C:
                viewTemperatureConfig.viewSetTxtUpperLimitTemperature((float)value, modelTemperatureConfig.getTemperatureUnitStr());
                break;
            case UNIT_F:
                viewTemperatureConfig.viewSetTxtUpperLimitTemperature((float)value, modelTemperatureConfig.getTemperatureUnitStr());
                break;
        }
    }

    public void setTemperatureUnit(MyStdDefinitions.TemperatureUnit temperatureUnit)
    {
        Log.d(TAG, "setTemperatureUnit");
        modelTemperatureConfig.setTemperatureUnit(temperatureUnit);
        //updateViewTemperatureUnit();
        updateViewUpperLimitTemperature();
    }

    public void setValueUpperLimitTemperature(int percentage)
    {
        Log.d(TAG, "setValueUpperLimitTemperature");
        int value = conversionTablesTemperature.convert_from_percentage(percentage, modelTemperatureConfig.getTemperatureUnit(), modelTemperatureConfig.getMaxUpperLimitTemperature(), modelTemperatureConfig.getMinUpperLimitTemperature());
        modelTemperatureConfig.setUpperLimitTemperature(value);
    }
}
