package famar.tirepressuremonitoringsystem.DataStorage;

import android.content.Context;
import android.content.SharedPreferences;

import famar.tirepressuremonitoringsystem.R;
import famar.tirepressuremonitoringsystem.pojo.MyStdDefinitions;

import static famar.tirepressuremonitoringsystem.pojo.MyStdDefinitions.NUM_SENSORS;
import static famar.tirepressuremonitoringsystem.pojo.MyStdDefinitions.SensorIndex.SENSOR_FL;
import static famar.tirepressuremonitoringsystem.pojo.MyStdDefinitions.SensorIndex.SENSOR_FR;
import static famar.tirepressuremonitoringsystem.pojo.MyStdDefinitions.SensorIndex.SENSOR_RL;
import static famar.tirepressuremonitoringsystem.pojo.MyStdDefinitions.SensorIndex.SENSOR_RR;

public class SharedPreferenceHelper
{
    private SharedPreferences appPreferences;
    private SharedPreferences.Editor appPreferencesEditor;
    private Context context;

    private String pressureUnitStr;
    private int upperLimitPressureBARx10;
    private int lowerLimitPressureBARx10;
    private String temperatureUnitStr;
    private int upperLimitTemperatureC;
    private int lowerLimitBatteryVoltage;
    private String[] sensorID = new String[NUM_SENSORS];
    private int carLogoIndex;
    private boolean playAlarm;

    public SharedPreferenceHelper(Context context)
    {
        this.context = context;

        appPreferences = context.getSharedPreferences("appPreferences", Context.MODE_PRIVATE);
        appPreferencesEditor = appPreferences.edit();

        sensorID[SENSOR_FL.ordinal()] = appPreferences.getString(MyStdDefinitions.KEY_FL_ID, context.getResources().getString(R.string.id_value_default));
        sensorID[SENSOR_FR.ordinal()] = appPreferences.getString(MyStdDefinitions.KEY_FR_ID, context.getResources().getString(R.string.id_value_default));
        sensorID[SENSOR_RL.ordinal()] = appPreferences.getString(MyStdDefinitions.KEY_RL_ID, context.getResources().getString(R.string.id_value_default));
        sensorID[SENSOR_RR.ordinal()] = appPreferences.getString(MyStdDefinitions.KEY_RR_ID, context.getResources().getString(R.string.id_value_default));

        pressureUnitStr = appPreferences.getString(MyStdDefinitions.KEY_PRESSURE_UNIT, "-1");
        upperLimitPressureBARx10 = appPreferences.getInt(MyStdDefinitions.KEY_PRESSURE_LIMIT_UP, 0);
        lowerLimitPressureBARx10 = appPreferences.getInt(MyStdDefinitions.KEY_PRESSURE_LIMIT_LOW, 0);

        temperatureUnitStr = appPreferences.getString(MyStdDefinitions.KEY_TEMPERATURE_UNIT, "-1");
        upperLimitTemperatureC = appPreferences.getInt(MyStdDefinitions.KEY_TEMPERATURE_LIMIT_UP, 0);

        lowerLimitBatteryVoltage= appPreferences.getInt(MyStdDefinitions.KEY_BATTERY_LIMIT_LOW, 0);

        carLogoIndex = appPreferences.getInt(MyStdDefinitions.KEY_CAR_LOGO, 0);
        playAlarm = appPreferences.getBoolean(MyStdDefinitions.KEY_PLAY_ALARM, false);
    }

    public String getPressureUnitStr()
    {
        return pressureUnitStr;
    }

    public void setPressureUnitStr(String pressureUnitStr)
    {
        this.pressureUnitStr = pressureUnitStr;
        appPreferencesEditor.putString(MyStdDefinitions.KEY_PRESSURE_UNIT, pressureUnitStr);
        appPreferencesEditor.commit();
    }

    public int getUpperLimitPressureBARx10()
    {
        return upperLimitPressureBARx10;
    }

    public void setUpperLimitPressureBar(int upperLimitPressureBARx10)
    {
        this.upperLimitPressureBARx10 = upperLimitPressureBARx10;

        appPreferencesEditor.putInt(MyStdDefinitions.KEY_PRESSURE_LIMIT_UP, upperLimitPressureBARx10);
        appPreferencesEditor.commit();
    }

    public int getLowerLimitPressureBARx10()
    {
        return lowerLimitPressureBARx10;
    }

    public void setLowerLimitPressureBar(int lowerLimitPressureBARx10)
    {
        this.lowerLimitPressureBARx10 = lowerLimitPressureBARx10;
        appPreferencesEditor.putInt(MyStdDefinitions.KEY_PRESSURE_LIMIT_LOW, lowerLimitPressureBARx10);
        appPreferencesEditor.commit();
    }

    public String getTemperatureUnitStr()
    {
        return temperatureUnitStr;
    }

    public void setTemperatureUnitStr(String temperatureUnitStr)
    {
        this.temperatureUnitStr = temperatureUnitStr;
        appPreferencesEditor.putString(MyStdDefinitions.KEY_TEMPERATURE_UNIT, temperatureUnitStr);
        appPreferencesEditor.commit();
    }

    public int getUpperLimitTemperatureC()
    {
        return upperLimitTemperatureC;
    }

    public void setUpperLimitTemperatureC(int upperLimitTemperatureC)
    {
        this.upperLimitTemperatureC = upperLimitTemperatureC;

        appPreferencesEditor.putInt(MyStdDefinitions.KEY_TEMPERATURE_LIMIT_UP, upperLimitTemperatureC);
        appPreferencesEditor.commit();
    }

    public int getLowerLimitBatteryVoltage()
    {
        return lowerLimitBatteryVoltage;
    }

    public void setLowerLimitBatteryVoltage(int lowerLimitBatteryVoltage)
    {
        this.lowerLimitBatteryVoltage = lowerLimitBatteryVoltage;

        appPreferencesEditor.putInt(MyStdDefinitions.KEY_BATTERY_LIMIT_LOW, lowerLimitBatteryVoltage);
        appPreferencesEditor.commit();
    }

    public String getSensorID(MyStdDefinitions.SensorIndex index)
    {
        return sensorID[index.ordinal()];
    }

    public void setSensorID(MyStdDefinitions.SensorIndex index, String sensorID)
    {
        this.sensorID[index.ordinal()] = sensorID;

        if(index.equals(SENSOR_FL))
        {
            appPreferencesEditor.putString(MyStdDefinitions.KEY_FL_ID, sensorID);
        }
        else if(index.equals(SENSOR_FR))
        {
            appPreferencesEditor.putString(MyStdDefinitions.KEY_FR_ID, sensorID);
        }
        else if(index.equals(SENSOR_RL))
        {
            appPreferencesEditor.putString(MyStdDefinitions.KEY_RL_ID, sensorID);
        }
        else if(index.equals(SENSOR_RR))
        {
            appPreferencesEditor.putString(MyStdDefinitions.KEY_RR_ID, sensorID);
        }

        appPreferencesEditor.commit();
    }

    public int getCarLogoIndex()
    {
        return carLogoIndex;
    }

    public void setCarLogoIndex(int carLogoIndex)
    {
        this.carLogoIndex = carLogoIndex;
        appPreferencesEditor.putInt(MyStdDefinitions.KEY_CAR_LOGO, carLogoIndex);
        appPreferencesEditor.commit();
    }

    public boolean getPlayAlarm()
    {
        return playAlarm;
    }

    public void setPlayAlarm(boolean muteAlarm)
    {
        this.playAlarm = muteAlarm;
        appPreferencesEditor.putBoolean(MyStdDefinitions.KEY_PLAY_ALARM, muteAlarm);
        appPreferencesEditor.commit();
    }


}
