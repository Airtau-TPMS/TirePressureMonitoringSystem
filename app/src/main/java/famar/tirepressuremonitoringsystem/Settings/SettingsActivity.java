package famar.tirepressuremonitoringsystem.Settings;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import famar.tirepressuremonitoringsystem.About.AboutActivity;
import famar.tirepressuremonitoringsystem.Help.HelpActivity;
import famar.tirepressuremonitoringsystem.R;
import famar.tirepressuremonitoringsystem.Settings.MVPBatteryVoltageConfig.ModelBatteryVoltageConfig;
import famar.tirepressuremonitoringsystem.Settings.MVPBatteryVoltageConfig.PressenterBatteryVoltageConfig;
import famar.tirepressuremonitoringsystem.Settings.MVPBatteryVoltageConfig.iViewBatteryVoltageConfig;
import famar.tirepressuremonitoringsystem.Settings.MVPCarLogoConfig.ModelCarLogoConfig;
import famar.tirepressuremonitoringsystem.Settings.MVPCarLogoConfig.PressenterCarLogoConfig;
import famar.tirepressuremonitoringsystem.Settings.MVPCarLogoConfig.iViewCarLogoConfig;
import famar.tirepressuremonitoringsystem.Settings.MVPPressureConfig.ModelPressureConfig;
import famar.tirepressuremonitoringsystem.Settings.MVPPressureConfig.PressenterPressureConfig;
import famar.tirepressuremonitoringsystem.Settings.MVPPressureConfig.iViewPressureConfig;
import famar.tirepressuremonitoringsystem.Settings.MVPSensorIDConfig.ModelSensorIDConfig;
import famar.tirepressuremonitoringsystem.Settings.MVPSensorIDConfig.PressenterSensorIDConfig;
import famar.tirepressuremonitoringsystem.Settings.MVPSensorIDConfig.iViewSensorIDConfig;
import famar.tirepressuremonitoringsystem.Settings.MVPTemperatureConfig.ModelTemperatureConfig;
import famar.tirepressuremonitoringsystem.Settings.MVPTemperatureConfig.PressenterTemperatureConfig;
import famar.tirepressuremonitoringsystem.Settings.MVPTemperatureConfig.iViewTemperatureConfig;
import famar.tirepressuremonitoringsystem.pojo.MyStdDefinitions;

import static famar.tirepressuremonitoringsystem.pojo.MyStdDefinitions.SensorIndex.SENSOR_FL;
import static famar.tirepressuremonitoringsystem.pojo.MyStdDefinitions.SensorIndex.SENSOR_FR;
import static famar.tirepressuremonitoringsystem.pojo.MyStdDefinitions.SensorIndex.SENSOR_RL;
import static famar.tirepressuremonitoringsystem.pojo.MyStdDefinitions.SensorIndex.SENSOR_RR;

public class SettingsActivity extends AppCompatActivity implements iViewPressureConfig, iViewTemperatureConfig, iViewBatteryVoltageConfig, iViewSensorIDConfig, iViewCarLogoConfig
{
    private Activity activity;
    private PressenterPressureConfig pressenterPressureConfig;
    private PressenterTemperatureConfig pressenterTemperatureConfig;
    private PressenterBatteryVoltageConfig pressenterBatteryVoltageConfig;
    private PressenterSensorIDConfig pressenterSensorIDConfig;
    private PressenterCarLogoConfig pressenterCarLogoConfig;

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Intent intent;
        switch(item.getItemId())
        {
            case R.id.mSettings:
                break;
            case R.id.mHelp:
                intent = new Intent(SettingsActivity.this, HelpActivity.class);
                startActivity(intent);
                break;
            case R.id.mAbout:
                intent = new Intent(SettingsActivity.this, AboutActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        activity = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.menu_settings));
    }

    @Override
    public void onResume()
    {
        super.onResume();  // Always call the superclass method first

        // Set the focus of the screen in the first element
        TextView tvFirstTitle = (TextView)findViewById(R.id.tvFirstTitle);
        tvFirstTitle.setFocusableInTouchMode(true);

        pressenterPressureConfig = new PressenterPressureConfig(new ModelPressureConfig(this.getApplicationContext()), this);
        pressenterTemperatureConfig = new PressenterTemperatureConfig(new ModelTemperatureConfig(this.getApplicationContext()), this);
        pressenterBatteryVoltageConfig = new PressenterBatteryVoltageConfig(new ModelBatteryVoltageConfig(this.getApplicationContext()), this);
        pressenterSensorIDConfig = new PressenterSensorIDConfig(new ModelSensorIDConfig(this.getApplicationContext()), this);
        pressenterCarLogoConfig = new PressenterCarLogoConfig(new ModelCarLogoConfig(this.getApplicationContext()), this);

        // *********** Sensor ID ***********
        EditText sensor_id_fl = (EditText) findViewById(R.id.SETTING_KEY_FL_ID);
        EditText sensor_id_fr = (EditText) findViewById(R.id.SETTING_KEY_FR_ID);
        EditText sensor_id_rl = (EditText) findViewById(R.id.SETTING_KEY_RL_ID);
        EditText sensor_id_rr = (EditText) findViewById(R.id.SETTING_KEY_RR_ID);

        sensor_id_fl.addTextChangedListener(onTxtSensorIdFLChangedListener);
        sensor_id_fr.addTextChangedListener(onTxtSensorIdFRChangedListener);
        sensor_id_rl.addTextChangedListener(onTxtSensorIdRLChangedListener);
        sensor_id_rr.addTextChangedListener(onTxtSensorIdRRChangedListener);

        // *********** Pressure Units ***********
        RadioGroup rgPressureUnit = (RadioGroup) findViewById(R.id.SETTING_KEY_PRESSURE_UNIT);
        rgPressureUnit.setOnCheckedChangeListener(onPressureUnitSelection);

        // *********** Pressure Sliders for warning limits and alarms ***********
        Switch alarmSwitch = (Switch)findViewById(R.id.SETTING_KEY_ALARM_SWITCH);
        alarmSwitch.setOnCheckedChangeListener(onCheckedChangeListener);
        SeekBar seekbarPressureLimitUp = (SeekBar) findViewById(R.id.SETTING_KEY_PRESSURE_LIMIT_UP);
        SeekBar seekbarPressureLimitLow = (SeekBar) findViewById(R.id.SETTING_KEY_PRESSURE_LIMIT_LOW);

        seekbarPressureLimitUp.setOnSeekBarChangeListener(onSeekBarChangeListener);
        seekbarPressureLimitLow.setOnSeekBarChangeListener(onSeekBarChangeListener);

        // *********** Temperature Units ***********
        RadioGroup rgTemperatureUnit = (RadioGroup) findViewById(R.id.SETTING_KEY_TEMPERATURE_UNIT);
        rgTemperatureUnit.setOnCheckedChangeListener(onTemperatureUnitSelection);

        // *********** Temperature Sliders for warning limits and alarms ***********
        SeekBar seekbarTemperatureLimitUp = (SeekBar) findViewById(R.id.SETTING_KEY_TEMPERATURE_LIMIT_UP);

        seekbarTemperatureLimitUp.setOnSeekBarChangeListener(onSeekBarChangeListener);

        // *********** BatteryVoltage Sliders for warning limits and alarms ***********
        SeekBar seekbarBatteryVoltageLimitLow = (SeekBar) findViewById(R.id.SETTING_KEY_BATTERY_LIMIT_LOW);

        seekbarBatteryVoltageLimitLow.setOnSeekBarChangeListener(onSeekBarChangeListener);

        // *********** Spinner and Car Logo ***********
        Spinner spinnerCar = (Spinner) findViewById(R.id.SETTING_KEY_SPINNER_CAR);
        spinnerCar.setOnItemSelectedListener(onSpinnerSelection);

        // ****************************************************************************************
        // ****************************************************************************************
        // ****************************************************************************************
        pressenterPressureConfig.initViewObjects();
        pressenterTemperatureConfig.initViewObjects();
        pressenterBatteryVoltageConfig.initViewObjects();
        pressenterSensorIDConfig.initViewObjects();
        pressenterCarLogoConfig.initViewObjects();
        // ****************************************************************************************
        // ****************************************************************************************
        // ****************************************************************************************

    }

    public void viewUpdateCarLogo(int carLogoIndex)
    {
        Spinner spinnerCar = (Spinner) findViewById(R.id.SETTING_KEY_SPINNER_CAR);
        ImageView imgCar = (ImageView)activity.findViewById(R.id.SETTING_KEY_IMG_CAR);

        // Set the car image and the spinner in the current selected logo
        TypedArray imgCars = getResources().obtainTypedArray(R.array.img_cars);
        imgCar.setImageResource(imgCars.getResourceId(carLogoIndex, 0));
        spinnerCar.setSelection(carLogoIndex);
    }

    public void viewSetPressureUnit(MyStdDefinitions.PressureUnit pressureUnit)
    {
        RadioButton rbPressureUnit;
        switch(pressureUnit)
        {
            case UNIT_PSI:
                rbPressureUnit = (RadioButton)findViewById(R.id.SETTING_KEY_PRESSURE_UNIT_PSI);
                rbPressureUnit.setChecked(true);
                break;
            case UNIT_KPA:
                rbPressureUnit = (RadioButton)findViewById(R.id.SETTING_KEY_PRESSURE_UNIT_KPA);
                rbPressureUnit.setChecked(true);
                break;
            case UNIT_BAR:
                rbPressureUnit = (RadioButton)findViewById(R.id.SETTING_KEY_PRESSURE_UNIT_BAR);
                rbPressureUnit.setChecked(true);
                break;
        }
    }

    public void viewSetTemperatureUnit(MyStdDefinitions.TemperatureUnit temperatureUnit)
    {
        RadioButton rbTemperatureUnit;
        switch(temperatureUnit)
        {
            case UNIT_C:
                rbTemperatureUnit = (RadioButton)findViewById(R.id.SETTING_KEY_TEMPERATURE_UNIT_C);
                rbTemperatureUnit.setChecked(true);
                break;
            case UNIT_F:
                rbTemperatureUnit = (RadioButton)findViewById(R.id.SETTING_KEY_TEMPERATURE_UNIT_F);
                rbTemperatureUnit.setChecked(true);
                break;
        }
    }

    public void viewSetPlayAlarm(boolean playAlarm)
    {
        Switch alarmSwitch = (Switch) findViewById(R.id.SETTING_KEY_ALARM_SWITCH);
        alarmSwitch.setChecked(playAlarm);
    }

    public void viewSetTxtUpperLimitPressure(float value, String unit_str)
    {
        TextView txtValue = (TextView) findViewById(R.id.SETTING_KEY_TXT_PRESSURE_LIMIT_UP);
        txtValue.setText(String.format ("%.2f ", value) + unit_str);
    }

    public void viewSetSeekbarUpperLimitPressure(int value)
    {
        SeekBar seekBar = (SeekBar) findViewById(R.id.SETTING_KEY_PRESSURE_LIMIT_UP);
        seekBar.setProgress(value);
    }

    public void viewSetTxtLowerLimitPressure(float value, String unit_str)
    {
        TextView txtValue = (TextView) findViewById(R.id.SETTING_KEY_TXT_PRESSURE_LIMIT_LOW);
        txtValue.setText(String.format ("%.2f ", value) + unit_str);
    }

    public void viewSetSeekbarLowerLimitPressure(int value)
    {
        SeekBar seekBar = (SeekBar) findViewById(R.id.SETTING_KEY_PRESSURE_LIMIT_LOW);
        seekBar.setProgress(value);
    }

    public void viewSetTxtUpperLimitTemperature(float value, String unit_str)
    {
        TextView txtValue = (TextView) findViewById(R.id.SETTING_KEY_TXT_TEMPERATURE_LIMIT_UP);
        txtValue.setText(String.format ("%.0f ", value) + unit_str);
    }

    public void viewSetSeekbarUpperLimitTemperature(int value)
    {
        SeekBar seekBar = (SeekBar) findViewById(R.id.SETTING_KEY_TEMPERATURE_LIMIT_UP);
        seekBar.setProgress(value);
    }

    public void viewSetTxtLowerLimitBatteryVoltage(float value, String unit_str)
    {
        TextView txtValue = (TextView) findViewById(R.id.SETTING_KEY_TXT_BATTERY_LIMIT_LOW);
        txtValue.setText(String.format ("%.2f ", value) + unit_str);
    }

    public void viewSetSeekbarLowerLimitBatteryVoltage(int value)
    {
        SeekBar seekBar = (SeekBar) findViewById(R.id.SETTING_KEY_BATTERY_LIMIT_LOW);
        seekBar.setProgress(value);
    }

    public void viewSetTxtSensorIdFL(String sensorID)
    {
        TextView txtValue = (TextView) findViewById(R.id.SETTING_KEY_FL_ID);
        txtValue.setText(sensorID);
    }

    public void viewSetTxtSensorIdFR(String sensorID)
    {
        TextView txtValue = (TextView) findViewById(R.id.SETTING_KEY_FR_ID);
        txtValue.setText(sensorID);
    }

    public void viewSetTxtSensorIdRL(String sensorID)
    {
        TextView txtValue = (TextView) findViewById(R.id.SETTING_KEY_RL_ID);
        txtValue.setText(sensorID);
    }

    public void viewSetTxtSensorIdRR(String sensorID)
    {
        TextView txtValue = (TextView) findViewById(R.id.SETTING_KEY_RR_ID);
        txtValue.setText(sensorID);
    }

    private RadioGroup.OnCheckedChangeListener onPressureUnitSelection = new RadioGroup.OnCheckedChangeListener()
    {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId)
        {
            switch(checkedId)
            {
                case R.id.SETTING_KEY_PRESSURE_UNIT_PSI:
                    pressenterPressureConfig.setPressureUnit(MyStdDefinitions.PressureUnit.UNIT_PSI);
                    break;
                case R.id.SETTING_KEY_PRESSURE_UNIT_KPA:
                    pressenterPressureConfig.setPressureUnit(MyStdDefinitions.PressureUnit.UNIT_KPA);
                    break;
                case R.id.SETTING_KEY_PRESSURE_UNIT_BAR:
                    pressenterPressureConfig.setPressureUnit(MyStdDefinitions.PressureUnit.UNIT_BAR);
                    break;
            }
        }
    };

    private RadioGroup.OnCheckedChangeListener onTemperatureUnitSelection = new RadioGroup.OnCheckedChangeListener()
    {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId)
        {
            switch(checkedId)
            {
                case R.id.SETTING_KEY_TEMPERATURE_UNIT_C:
                    pressenterTemperatureConfig.setTemperatureUnit(MyStdDefinitions.TemperatureUnit.UNIT_C);
                    break;
                case R.id.SETTING_KEY_TEMPERATURE_UNIT_F:
                    pressenterTemperatureConfig.setTemperatureUnit(MyStdDefinitions.TemperatureUnit.UNIT_F);
                    break;
            }
        }
    };

    private SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener()
    {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
        {
            float value;
            switch(seekBar.getId())
            {
                case R.id.SETTING_KEY_PRESSURE_LIMIT_UP:
                    pressenterPressureConfig.updateValueUpperLimitPressure(seekBar.getProgress());
                    break;
                case R.id.SETTING_KEY_PRESSURE_LIMIT_LOW:
                    pressenterPressureConfig.updateValueLowerLimitPressure(seekBar.getProgress());
                    break;
                case R.id.SETTING_KEY_TEMPERATURE_LIMIT_UP:
                    pressenterTemperatureConfig.updateValueUpperLimitTemperature(seekBar.getProgress());
                    break;
                case R.id.SETTING_KEY_BATTERY_LIMIT_LOW:
                    pressenterBatteryVoltageConfig.updateValueLowerLimitBatteryVoltage(seekBar.getProgress());
                    break;
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar)
        { }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar)
        {
            switch(seekBar.getId())
            {
                case R.id.SETTING_KEY_PRESSURE_LIMIT_UP:
                    pressenterPressureConfig.setValueUpperLimitPressure(seekBar.getProgress());
                    break;
                case R.id.SETTING_KEY_PRESSURE_LIMIT_LOW:
                    pressenterPressureConfig.setValueLowerLimitPressure(seekBar.getProgress());
                    break;
                case R.id.SETTING_KEY_TEMPERATURE_LIMIT_UP:
                    pressenterTemperatureConfig.setValueUpperLimitTemperature(seekBar.getProgress());
                    break;
                case R.id.SETTING_KEY_BATTERY_LIMIT_LOW:
                    pressenterBatteryVoltageConfig.setValueLowerLimitBatteryVoltage(seekBar.getProgress());
                    break;
            }
        }
    };

    private TextWatcher onTxtSensorIdFLChangedListener = new TextWatcher()
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
        }

        @Override
        public void afterTextChanged(Editable s)
        {
            pressenterSensorIDConfig.setValueSensorID(SENSOR_FL, s.toString());
        }
    };
    private TextWatcher onTxtSensorIdFRChangedListener = new TextWatcher()
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
        }

        @Override
        public void afterTextChanged(Editable s)
        {
            pressenterSensorIDConfig.setValueSensorID(SENSOR_FR, s.toString());
        }
    };
    private TextWatcher onTxtSensorIdRLChangedListener = new TextWatcher()
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
        }

        @Override
        public void afterTextChanged(Editable s)
        {
            pressenterSensorIDConfig.setValueSensorID(SENSOR_RL, s.toString());
        }
    };
    private TextWatcher onTxtSensorIdRRChangedListener = new TextWatcher()
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
        }

        @Override
        public void afterTextChanged(Editable s)
        {
            pressenterSensorIDConfig.setValueSensorID(SENSOR_RR, s.toString());
        }
    };

    private Switch.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener()
    {
        @Override
        public void onCheckedChanged (CompoundButton buttonView,boolean isChecked)
        {
            pressenterPressureConfig.setPlayAlarm(isChecked);
        }
    };

    private AdapterView.OnItemSelectedListener onSpinnerSelection = new AdapterView.OnItemSelectedListener()
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int carLogoIndex, long id)
        {
            //Toast.makeText(parent.getContext(), "" + position, Toast.LENGTH_SHORT).show();
            ImageView imgCar = (ImageView)activity.findViewById(R.id.SETTING_KEY_IMG_CAR);
            // get resource ID by index
            TypedArray imgCars = getResources().obtainTypedArray(R.array.img_cars);
            //set the ImageView's resource to the id
            imgCar.setImageResource(imgCars.getResourceId(carLogoIndex, 0));
            // recycle the array
            imgCars.recycle();

            pressenterCarLogoConfig.setCarLogoIndex(carLogoIndex);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent)
        {

        }
    };
}
