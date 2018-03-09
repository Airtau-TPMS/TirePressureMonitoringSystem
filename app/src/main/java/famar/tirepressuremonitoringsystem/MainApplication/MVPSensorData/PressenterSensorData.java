package famar.tirepressuremonitoringsystem.MainApplication.MVPSensorData;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import famar.tirepressuremonitoringsystem.DataStorage.SharedPreferenceHelper;
import famar.tirepressuremonitoringsystem.MainApplication.BLEService.BLEServiceConstants;
import famar.tirepressuremonitoringsystem.MainApplication.MVPSensorData.PressenterTimer.PressenterTimerHelper;
import famar.tirepressuremonitoringsystem.MainApplication.MVPSensorData.PressenterTimer.iPressenterTimerCallback;
import famar.tirepressuremonitoringsystem.pojo.MyStdDefinitions;


/* The Pressenter object is created as a Singleton Pattern
* This will allow to create just one instance and do not loose the information already received */
public class PressenterSensorData implements iPressenterTimerCallback
{
    private String TAG = "PressenterSensorData";
    private static Context mCcontext;
    private ModelSensorData modelSensorData;
    private iViewSensorData viewSensorData;
    private SharedPreferenceHelper sharedPreferenceHelper;
    private PressenterTimerHelper pressenterTimerHelper = new PressenterTimerHelper(this);

    /************************************************/
    /**** SINGLETON PATTERN *************************/
    /************************************************/
    /* create an object of PressenterSensorData */
    private static PressenterSensorData uniqueInstance = null;

    /* The constructor is private so that this class cannot be instantiated */
    private PressenterSensorData()
    {
        ModelSensorData modelSensorData = new ModelSensorData();
        this.modelSensorData = modelSensorData;
        this.viewSensorData = null;
        this.sharedPreferenceHelper = null;
    }

    public static PressenterSensorData getInstance(Context context)
    {
        if(uniqueInstance == null)
        {
            uniqueInstance = new PressenterSensorData();
            mCcontext = context;
        }

        /* Return the only object available */
        return uniqueInstance;
    }
    /************************************************/
    /************************************************/
    /************************************************/

    public void refreshPresenterData(iViewSensorData viewSensorData)
    {
        if(mCcontext != null)
        {
            /* Update the sharedPreferenceHelper */
            sharedPreferenceHelper = new SharedPreferenceHelper(mCcontext);
            for (MyStdDefinitions.SensorIndex index : MyStdDefinitions.SensorIndex.values())
            {
                /* Check if there's any change in the available IDs in the model */
                if(!(modelSensorData.getSensorID(index).equals(sharedPreferenceHelper.getSensorID(index))))
                {
                    modelSensorData.cleanSensorData(index);
                    /* Set the IDs already configured */
                    modelSensorData.setSensorID(index, sharedPreferenceHelper.getSensorID(index));
                }
            }

            /* Register the broadcast receiver to receive Intents with action named "myMessage" */
            LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(mCcontext);
            lbm.registerReceiver(myBroadcastReceiver, new IntentFilter(BLEServiceConstants.KEY_MY_MESSAGE));

            /* Set the View component to interact with*/
            this.viewSensorData = viewSensorData;
        }
    }

    public BroadcastReceiver myBroadcastReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            if (intent != null)
            {
                //String str = intent.getStringExtra(BLEServiceConstants.KEY_MY_MESSAGE_DATA);
                Bundle b = intent.getBundleExtra(BLEServiceConstants.KEY_MY_TPMS_MESSAGE_DATA);
                processIncomingData(b.getString("id_data"),
                        b.getString("pressure_data"),
                        b.getString("temperature_data"),
                        b.getString("battery_data")
                );
            }
        }
    };

    private void processIncomingData (String id_data, String pressure_data, String temperature_data, String battery_data)
    {
        /* Process the SensorID value */
        String sensorID = id_data.substring(6,12);

        for(MyStdDefinitions.SensorIndex index : MyStdDefinitions.SensorIndex.values())
        {
            if(modelSensorData.getSensorID(index).equals(sensorID))
            {
                /* Reset the msg timeout */
                pressenterTimerHelper.resetMsgTimeout(index);

                /* Set the validity bit */
                modelSensorData.setValidity(index, true);

                /* Process the Pressure value */
                String pressure_data_hex = pressure_data.substring(6,8) + pressure_data.substring(4,6) + pressure_data.substring(2,4) + pressure_data.substring(0,2);
                long pressure = Long.parseLong(pressure_data_hex, 16);
                int pressureHPA = (int) (pressure/100);
                modelSensorData.setPressureKPAx10(index, pressureHPA);

                /* Process the Temperature value */
                String temperature_data_hex = temperature_data.substring(6,8) + temperature_data.substring(4,6) + temperature_data.substring(2,4) + temperature_data.substring(0,2);
                long temperature = Long.parseLong(temperature_data_hex, 16);
                modelSensorData.setTemperatureC(index, (int) (temperature/100));

                /* Process the BatteryVoltage value */
                String battery_data_hex = battery_data.substring(0,2);
                long battery = Long.parseLong(battery_data_hex, 16);
                modelSensorData.setBatteryVoltage(index, (int) ((2990-2590)*battery/100+2590));

                Log.d(TAG, "id: " + modelSensorData.getSensorID(index));
                Log.d(TAG, "pressureKPA: " + modelSensorData.getPressureKPA(index));
                Log.d(TAG, "temperatureC: " + modelSensorData.getTemperatureC(index));
                Log.d("SensorDataHelper", "batteryVoltage: " + modelSensorData.getBatteryVoltage(index));

                updateViewObject(index);
                break;
            }
        }
    }

    public void initViewObjects()
    {
        Log.d(TAG, "initViewObjects");
        updateViewObjects();
    }

    public void updateViewObjects()
    {
        for(MyStdDefinitions.SensorIndex index : MyStdDefinitions.SensorIndex.values())
        {
            updateViewObject(index);
        }
    }

    public  void updateViewObject(MyStdDefinitions.SensorIndex index)
    {
        if(viewSensorData != null && sharedPreferenceHelper != null)
        {
            viewSensorData.viewSetSensorID(index, modelSensorData.getSensorID(index), modelSensorData.getValidity(index));

            /************************************************************/
            /*********** PRESSURE DATA **********************************/
            /************************************************************/
            float pressureBAR = modelSensorData.getPressureBAR(index);
            float pressureBARx10 = pressureBAR * 10;
            boolean alarmPressure = false;
            if (pressureBARx10 <= sharedPreferenceHelper.getLowerLimitPressureBARx10() ||
                    pressureBARx10 >= sharedPreferenceHelper.getUpperLimitPressureBARx10())
            {
                alarmPressure = true;
            }

            if (sharedPreferenceHelper.getPressureUnitStr().equals(MyStdDefinitions.PressureUnit.UNIT_PSI.toString()))
            {
                viewSensorData.viewSetPressure(index, modelSensorData.getPressurePSI(index), getPressureUnitStr(), alarmPressure, modelSensorData.getValidity(index));
            } else if (sharedPreferenceHelper.getPressureUnitStr().equals(MyStdDefinitions.PressureUnit.UNIT_KPA.toString()))
            {
                viewSensorData.viewSetPressure(index, modelSensorData.getPressureKPA(index), getPressureUnitStr(), alarmPressure, modelSensorData.getValidity(index));
            } else
            {
                viewSensorData.viewSetPressure(index, pressureBAR, getPressureUnitStr(), alarmPressure, modelSensorData.getValidity(index));
            }

            /************************************************************/
            /*********** TEMPERATURE DATA *******************************/
            /************************************************************/
            int temperatureC = modelSensorData.getTemperatureC(index);
            boolean alarmTemperature = false;
            if ((temperatureC>0) && (temperatureC >= sharedPreferenceHelper.getUpperLimitTemperatureC()))
            {
                alarmTemperature = true;
            }

            if (sharedPreferenceHelper.getTemperatureUnitStr().equals(MyStdDefinitions.TemperatureUnit.UNIT_F.toString()))
            {
                viewSensorData.viewSetTemperature(index, modelSensorData.getTemperatureF(index), getTemperatureUnitStr(), alarmTemperature, modelSensorData.getValidity(index));
            } else
            {
                viewSensorData.viewSetTemperature(index, temperatureC, getTemperatureUnitStr(), alarmTemperature, modelSensorData.getValidity(index));
            }

            /************************************************************/
            /*********** BATTERY VOLTAGE DATA ***************************/
            /************************************************************/
            int batteryVoltage = modelSensorData.getBatteryVoltage(index);
            boolean alarmBatteryVoltage = false;
            if ((batteryVoltage>0) && (batteryVoltage <= sharedPreferenceHelper.getLowerLimitBatteryVoltage()))
            {
                alarmBatteryVoltage = true;
            }
            viewSensorData.viewSetBatteryVoltage(index, (float) batteryVoltage / 1000, alarmBatteryVoltage, modelSensorData.getValidity(index));
        }
    }

    private String getPressureUnitStr()
    {
        if(sharedPreferenceHelper != null)
        {
            String pressureUnitStr = sharedPreferenceHelper.getPressureUnitStr();
            if (pressureUnitStr.equals(MyStdDefinitions.PressureUnit.UNIT_PSI.toString()))
            {
                return ("psi");
            } else if (pressureUnitStr.equals(MyStdDefinitions.PressureUnit.UNIT_KPA.toString()))
            {
                return ("kpa");
            } else if (pressureUnitStr.equals(MyStdDefinitions.PressureUnit.UNIT_BAR.toString()))
            {
                return ("bar");
            }
        }
        return ("");
    }

    private String getTemperatureUnitStr()
    {
        if(sharedPreferenceHelper != null)
        {
            String temperatureUnitStr = sharedPreferenceHelper.getTemperatureUnitStr();
            if (temperatureUnitStr.equals(MyStdDefinitions.TemperatureUnit.UNIT_C.toString()))
            {
                return ("ºC");
            } else if (temperatureUnitStr.equals(MyStdDefinitions.TemperatureUnit.UNIT_F.toString()))
            {
                return ("ºF");
            }
        }
        return ("");
    }

    @Override
    public void pressenterTimerCallback(MyStdDefinitions.SensorIndex index)
    {
        modelSensorData.setValidity(index, false);
        updateViewObject(index);
    }
}
