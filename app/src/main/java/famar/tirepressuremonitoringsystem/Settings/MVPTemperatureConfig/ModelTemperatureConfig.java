package famar.tirepressuremonitoringsystem.Settings.MVPTemperatureConfig;

import android.content.Context;

import famar.tirepressuremonitoringsystem.ConversionTables.ConversionTablesTemperature;
import famar.tirepressuremonitoringsystem.DataStorage.SharedPreferenceHelper;
import famar.tirepressuremonitoringsystem.pojo.MyStdDefinitions;

public class ModelTemperatureConfig
{
    private SharedPreferenceHelper sharedPreferenceHelper;
    private Context context;

    private int maxUpperLimitTemperature;
    private int minUpperLimitTemperature;
    private int upperLimitTemperature;

    private int MAX_UPPER_LIMIT_TEMPERATURE_C = 80;
    private int MIN_UPPER_LIMIT_TEMPERATURE_C = 60;

    MyStdDefinitions.TemperatureUnit temperatureUnit;
    ConversionTablesTemperature conversionTablesTemperature;

    public ModelTemperatureConfig(Context context)
    {
        this.context = context;
        conversionTablesTemperature = new ConversionTablesTemperature();
        sharedPreferenceHelper = new SharedPreferenceHelper(context);

        // Get the current config unit
        String temperatureUnitStr = sharedPreferenceHelper.getTemperatureUnitStr();
        if(temperatureUnitStr.equals(MyStdDefinitions.TemperatureUnit.UNIT_C.toString()))
        {
            setTemperatureUnit(MyStdDefinitions.TemperatureUnit.UNIT_C);
        }
        else if(temperatureUnitStr.equals(MyStdDefinitions.TemperatureUnit.UNIT_F.toString()))
        {
            setTemperatureUnit(MyStdDefinitions.TemperatureUnit.UNIT_F);
        }
        else        /* The default value */
        {
            setTemperatureUnit(MyStdDefinitions.TemperatureUnit.UNIT_C);
        }

        // Get the stored temperature limits (in Celsius)
        int upperLimitTemperature = sharedPreferenceHelper.getUpperLimitTemperatureC();

        if (upperLimitTemperature == 0)
        {
            upperLimitTemperature = MAX_UPPER_LIMIT_TEMPERATURE_C;
            setUpperLimitTemperature(upperLimitTemperature);
        }

        this.upperLimitTemperature = conversionTablesTemperature.unit_conversion(upperLimitTemperature, MyStdDefinitions.TemperatureUnit.UNIT_C, temperatureUnit);
    }

    public int getUpperLimitTemperature()
    {
        return upperLimitTemperature;
    }

    public void setUpperLimitTemperature(int upperLimitTemperature)
    {
        this.upperLimitTemperature = upperLimitTemperature;

        // Store the value always in Celsius
        int upperLimitTemperatureC = conversionTablesTemperature.unit_conversion(upperLimitTemperature, temperatureUnit, MyStdDefinitions.TemperatureUnit.UNIT_C);
        sharedPreferenceHelper.setUpperLimitTemperatureC(upperLimitTemperatureC);
    }


    public MyStdDefinitions.TemperatureUnit getTemperatureUnit()
    {
        return temperatureUnit;
    }

    public String getTemperatureUnitStr()
    {
        switch (temperatureUnit)
        {
            case UNIT_C:
                return("ºC");
            case UNIT_F:
                return("ºF");
        }
        return null;
    }

    public void setTemperatureUnit(MyStdDefinitions.TemperatureUnit temperatureUnit)
    {
        this.temperatureUnit = temperatureUnit;

        // Update the max and min values
        maxUpperLimitTemperature = conversionTablesTemperature.unit_conversion(MAX_UPPER_LIMIT_TEMPERATURE_C, MyStdDefinitions.TemperatureUnit.UNIT_C, temperatureUnit);
        minUpperLimitTemperature = conversionTablesTemperature.unit_conversion(MIN_UPPER_LIMIT_TEMPERATURE_C, MyStdDefinitions.TemperatureUnit.UNIT_C, temperatureUnit);

        // Update the current limit values
        int upperLimitTemperatureC = sharedPreferenceHelper.getUpperLimitTemperatureC();
        upperLimitTemperature = conversionTablesTemperature.unit_conversion(upperLimitTemperatureC, MyStdDefinitions.TemperatureUnit.UNIT_C, temperatureUnit);

        // Store the selected unit
        sharedPreferenceHelper.setTemperatureUnitStr(temperatureUnit.toString());
    }

    public int getMaxUpperLimitTemperature()
    {
        return maxUpperLimitTemperature;
    }

    public int getMinUpperLimitTemperature()
    {
        return minUpperLimitTemperature;
    }

}
