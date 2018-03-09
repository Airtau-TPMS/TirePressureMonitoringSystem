package famar.tirepressuremonitoringsystem.Settings.MVPPressureConfig;

import android.content.Context;

import famar.tirepressuremonitoringsystem.ConversionTables.ConversionTablesPressure;
import famar.tirepressuremonitoringsystem.pojo.MyStdDefinitions;
import famar.tirepressuremonitoringsystem.DataStorage.SharedPreferenceHelper;

public class ModelPressureConfig
{
    private SharedPreferenceHelper sharedPreferenceHelper;
    private Context context;

    private int maxUpperLimitPressure;
    private int minUpperLimitPressure;
    private int maxLowerLimitPressure;
    private int minLowerLimitPressure;
    private int upperLimitPressure;
    private int lowerLimitPressure;
    private boolean playAlarm;

    private int MAX_UPPER_LIMIT_PRESSURE_BARx10 = 64;
    private int MIN_UPPER_LIMIT_PRESSURE_BARx10 = 28;
    private int MAX_LOWER_LIMIT_PRESSURE_BARx10 = 25;
    private int MIN_LOWER_LIMIT_PRESSURE_BARx10 = 1;

    MyStdDefinitions.PressureUnit pressureUnit;
    ConversionTablesPressure conversionTablesPressure;

    public ModelPressureConfig(Context context)
    {
        this.context = context;
        conversionTablesPressure = new ConversionTablesPressure();
        sharedPreferenceHelper = new SharedPreferenceHelper(context);

        // Get the current config unit
        String pressureUnitStr = sharedPreferenceHelper.getPressureUnitStr();
        if(pressureUnitStr.equals(MyStdDefinitions.PressureUnit.UNIT_PSI.toString()))
        {
            setPressureUnit(MyStdDefinitions.PressureUnit.UNIT_PSI);
        }
        else if(pressureUnitStr.equals(MyStdDefinitions.PressureUnit.UNIT_KPA.toString()))
        {
            setPressureUnit(MyStdDefinitions.PressureUnit.UNIT_KPA);
        }
        else if(pressureUnitStr.equals(MyStdDefinitions.PressureUnit.UNIT_BAR.toString()))
        {
            setPressureUnit(MyStdDefinitions.PressureUnit.UNIT_BAR);
        }
        else        /* The default value */
        {
            setPressureUnit(MyStdDefinitions.PressureUnit.UNIT_BAR);
        }

        // Get the stored pressure limits (in Bar)
        int upperLimitPressureBARx10 = sharedPreferenceHelper.getUpperLimitPressureBARx10();
        int lowerLimitPressureBARx10 = sharedPreferenceHelper.getLowerLimitPressureBARx10();

        if (upperLimitPressureBARx10 == 0)
        {
            upperLimitPressureBARx10 = MAX_UPPER_LIMIT_PRESSURE_BARx10;
            setUpperLimitPressure(upperLimitPressureBARx10);
        }
        if (lowerLimitPressureBARx10 == 0)
        {
            lowerLimitPressureBARx10 = MAX_LOWER_LIMIT_PRESSURE_BARx10;
            setLowerLimitPressure(lowerLimitPressureBARx10);
        }

        this.upperLimitPressure = conversionTablesPressure.unit_conversion(upperLimitPressureBARx10, MyStdDefinitions.PressureUnit.UNIT_BAR, pressureUnit);
        this.lowerLimitPressure = conversionTablesPressure.unit_conversion(lowerLimitPressureBARx10, MyStdDefinitions.PressureUnit.UNIT_BAR, pressureUnit);

        this.playAlarm = sharedPreferenceHelper.getPlayAlarm();
    }

    public int getUpperLimitPressure()
    {
        return upperLimitPressure;
    }

    public void setUpperLimitPressure(int upperLimitPressure)
    {
        this.upperLimitPressure = upperLimitPressure;

        // Store the value always in Bar
        int upperLimitPressureBar = conversionTablesPressure.unit_conversion(upperLimitPressure, pressureUnit, MyStdDefinitions.PressureUnit.UNIT_BAR);
        sharedPreferenceHelper.setUpperLimitPressureBar(upperLimitPressureBar);
    }

    public int getLowerLimitPressure()
    {
        return lowerLimitPressure;
    }

    public void setLowerLimitPressure(int lowerLimitPressure)
    {
        this.lowerLimitPressure = lowerLimitPressure;

        // Store the value always in Bar
        int lowerLimitPressureBar = conversionTablesPressure.unit_conversion(lowerLimitPressure, pressureUnit, MyStdDefinitions.PressureUnit.UNIT_BAR);
        sharedPreferenceHelper.setLowerLimitPressureBar(lowerLimitPressureBar);
    }

    public MyStdDefinitions.PressureUnit getPressureUnit()
    {
        return pressureUnit;
    }

    public String getPressureUnitStr()
    {
        switch (pressureUnit)
        {
            case UNIT_PSI:
                return("psi");
            case UNIT_KPA:
                return("kpa");
            case UNIT_BAR:
                return("bar");
        }
        return null;
    }

    public void setPressureUnit(MyStdDefinitions.PressureUnit pressureUnit)
    {
        this.pressureUnit = pressureUnit;

        // Update the max and min values
        maxUpperLimitPressure = conversionTablesPressure.unit_conversion(MAX_UPPER_LIMIT_PRESSURE_BARx10, MyStdDefinitions.PressureUnit.UNIT_BAR, pressureUnit);
        minUpperLimitPressure = conversionTablesPressure.unit_conversion(MIN_UPPER_LIMIT_PRESSURE_BARx10, MyStdDefinitions.PressureUnit.UNIT_BAR, pressureUnit);
        maxLowerLimitPressure = conversionTablesPressure.unit_conversion(MAX_LOWER_LIMIT_PRESSURE_BARx10, MyStdDefinitions.PressureUnit.UNIT_BAR, pressureUnit);
        minLowerLimitPressure = conversionTablesPressure.unit_conversion(MIN_LOWER_LIMIT_PRESSURE_BARx10, MyStdDefinitions.PressureUnit.UNIT_BAR, pressureUnit);

        // Update the current limit values
        int upperLimitPressureBar = sharedPreferenceHelper.getUpperLimitPressureBARx10();
        int lowerLimitPressureBar = sharedPreferenceHelper.getLowerLimitPressureBARx10();
        upperLimitPressure = conversionTablesPressure.unit_conversion(upperLimitPressureBar, MyStdDefinitions.PressureUnit.UNIT_BAR, pressureUnit);
        lowerLimitPressure = conversionTablesPressure.unit_conversion(lowerLimitPressureBar, MyStdDefinitions.PressureUnit.UNIT_BAR, pressureUnit);

        // Store the selected unit
        sharedPreferenceHelper.setPressureUnitStr(pressureUnit.toString());
    }

    public int getMaxUpperLimitPressure()
    {
        return maxUpperLimitPressure;
    }

    public int getMinUpperLimitPressure()
    {
        return minUpperLimitPressure;
    }

    public int getMaxLowerLimitPressure()
    {
        return maxLowerLimitPressure;
    }

    public int getMinLowerLimitPressure()
    {
        return minLowerLimitPressure;
    }

    public boolean getPlayAlarm()
    {
        return playAlarm;
    }

    public void setPlayAlarm(boolean playAlarm)
    {
        this.playAlarm = playAlarm;
        sharedPreferenceHelper.setPlayAlarm(playAlarm);
    }
}
