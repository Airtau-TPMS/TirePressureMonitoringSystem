package famar.tirepressuremonitoringsystem.MainApplication;

import android.content.Intent;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import famar.tirepressuremonitoringsystem.About.AboutActivity;
import famar.tirepressuremonitoringsystem.Help.HelpActivity;
import famar.tirepressuremonitoringsystem.MainApplication.MVPSensorData.PressenterSensorData;
import famar.tirepressuremonitoringsystem.MainApplication.MVPSensorData.iViewSensorData;
import famar.tirepressuremonitoringsystem.R;
import famar.tirepressuremonitoringsystem.Settings.SettingsActivity;
import famar.tirepressuremonitoringsystem.pojo.MyStdDefinitions;

import static famar.tirepressuremonitoringsystem.pojo.MyStdDefinitions.SensorIndex.SENSOR_FL;
import static famar.tirepressuremonitoringsystem.pojo.MyStdDefinitions.SensorIndex.SENSOR_FR;
import static famar.tirepressuremonitoringsystem.pojo.MyStdDefinitions.SensorIndex.SENSOR_RL;
import static famar.tirepressuremonitoringsystem.pojo.MyStdDefinitions.SensorIndex.SENSOR_RR;

public class MoreInformationActivity extends AppCompatActivity implements iViewSensorData
{
    PressenterSensorData pressenterSensorData;
    MyStdDefinitions.SensorIndex sensorIndex;

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
                intent = new Intent(MoreInformationActivity.this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.mHelp:
                intent = new Intent(MoreInformationActivity.this, HelpActivity.class);
                startActivity(intent);
                break;
            case R.id.mAbout:
                intent = new Intent(MoreInformationActivity.this, AboutActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_information);

        Intent intent = getIntent();
        String title = "";
        int sensor = intent.getIntExtra(MyStdDefinitions.KEY_MORE_INFORMATION, SENSOR_FL.ordinal());
        if(sensor == SENSOR_FL.ordinal())
        {
            sensorIndex = SENSOR_FL;
            title = getResources().getString(R.string.title_card_fl);
        }
        else if(sensor == SENSOR_FR.ordinal())
        {
            sensorIndex = SENSOR_FR;
            title = getResources().getString(R.string.title_card_fr);
        }
        else if(sensor == SENSOR_RL.ordinal())
        {
            sensorIndex = SENSOR_RL;
            title = getResources().getString(R.string.title_card_rl);
        }
        else if(sensor == SENSOR_RR.ordinal())
        {
            sensorIndex = SENSOR_RR;
            title = getResources().getString(R.string.title_card_rr);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onResume()
    {
        super.onResume();  // Always call the superclass method first

        View auxView;
        auxView = findViewById(R.id.viewIdInfo);
        CardView cardIdInfo = (CardView) auxView.findViewById(R.id.cardView);
        auxView = findViewById(R.id.viewBatteryInfo);
        CardView cardBatteryInfo = (CardView) auxView.findViewById(R.id.cardView);
        auxView = findViewById(R.id.viewPressureInfo);
        CardView cardPressureInfo = (CardView) auxView.findViewById(R.id.cardView);
        auxView = findViewById(R.id.viewTemperatureInfo);
        CardView cardTemperatureInfo = (CardView) auxView.findViewById(R.id.cardView);

        TextView txtTitle;
        txtTitle = (TextView)cardIdInfo.findViewById(R.id.txtCardTitle);
        txtTitle.setText(getResources().getString(R.string.title_card_sensor_identification));
        txtTitle = (TextView)cardBatteryInfo.findViewById(R.id.txtCardTitle);
        txtTitle.setText(getResources().getString(R.string.title_card_battery));
        txtTitle = (TextView)cardPressureInfo.findViewById(R.id.txtCardTitle);
        txtTitle.setText(getResources().getString(R.string.title_card_pressure));
        txtTitle = (TextView)cardTemperatureInfo.findViewById(R.id.txtCardTitle);
        txtTitle.setText(getResources().getString(R.string.title_card_temperature));

        ImageView imgIcon;
        imgIcon = (ImageView)cardIdInfo.findViewById(R.id.imgIcon);
        imgIcon.setImageResource(R.drawable.icon_id_tag);
        imgIcon = (ImageView)cardBatteryInfo.findViewById(R.id.imgIcon);
        imgIcon.setImageResource(R.drawable.icon_battery);
        imgIcon = (ImageView)cardPressureInfo.findViewById(R.id.imgIcon);
        imgIcon.setImageResource(R.drawable.tire_transparent);
        imgIcon = (ImageView)cardTemperatureInfo.findViewById(R.id.imgIcon);
        imgIcon.setImageResource(R.drawable.icon_thermometer);

        //ModelSensorData modelSensorData = new ModelSensorData();
        //pressenterSensorData = new PressenterSensorData(this.getApplicationContext(), modelSensorData, this);
        pressenterSensorData = PressenterSensorData.getInstance(this);
        pressenterSensorData.refreshPresenterData(this);

        // ****************************************************************************************
        // ****************************************************************************************
        // ****************************************************************************************
        pressenterSensorData.initViewObjects();
        // ****************************************************************************************
        // ****************************************************************************************
        // ****************************************************************************************

        /*
        TextView txtAux;
        txtAux = (TextView)cardIdInfo.findViewById(R.id.txtCardDataAux);
        txtAux.setText("");
        txtAux = (TextView)cardBatteryInfo.findViewById(R.id.txtCardDataAux);
        txtAux.setText("");
        txtAux = (TextView)cardPressureInfo.findViewById(R.id.txtCardDataAux);
        txtAux.setText("");
        txtAux = (TextView)cardTemperatureInfo.findViewById(R.id.txtCardDataAux);
        txtAux.setText("");

        TextView txtIdInfo          = (TextView)cardIdInfo.findViewById(R.id.txtCardData);
        TextView txtBatteryInfo     = (TextView)cardBatteryInfo.findViewById(R.id.txtCardData);
        TextView txtPressureInfo    = (TextView)cardPressureInfo.findViewById(R.id.txtCardData);
        TextView txtTemperatureInfo = (TextView)cardTemperatureInfo.findViewById(R.id.txtCardData);

        txtIdInfo.setText("#" + "1234");
        txtBatteryInfo.setText("2.7" + "v");
        txtPressureInfo.setText("25.3");
        txtTemperatureInfo.setText("25" + "ºC");
        */
    }

    @Override
    public void viewSetSensorID(MyStdDefinitions.SensorIndex index, String value, boolean validity)
    {
        if(sensorIndex.equals(index))
        {
            View auxView;
            auxView = findViewById(R.id.viewIdInfo);
            CardView cardIdInfo = (CardView) auxView.findViewById(R.id.cardView);

            TextView txtIdInfo = (TextView) cardIdInfo.findViewById(R.id.txtCardData);
            TextView txtIdInfoAux = (TextView) cardIdInfo.findViewById(R.id.txtCardDataAux);

            txtIdInfo.setText(value);
            txtIdInfoAux.setText("");

            if(validity == true)
            {
                cardIdInfo.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorInRange, null));
            }
            else
            {
                cardIdInfo.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorNoInformation, null));
            }
        }
    }

    @Override
    public void viewSetPressure(MyStdDefinitions.SensorIndex index, float value, String unit_str, boolean alarm, boolean validity)
    {
        if(sensorIndex.equals(index))
        {
            View auxView;
            auxView = findViewById(R.id.viewPressureInfo);
            CardView cardPressureInfo = (CardView) auxView.findViewById(R.id.cardView);

            TextView txtPressureInfo = (TextView) cardPressureInfo.findViewById(R.id.txtCardData);
            TextView txtPressureInfoAux = (TextView) cardPressureInfo.findViewById(R.id.txtCardDataAux);

            txtPressureInfo.setText(String.format ("%.2f ", value));
            txtPressureInfoAux.setText(unit_str);

            if(validity == true)
            {
                if (alarm == true)
                {
                    cardPressureInfo.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorOutOfRange, null));
                } else
                {
                    cardPressureInfo.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorInRange, null));
                }
            }
            else
            {
                cardPressureInfo.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorNoInformation, null));
            }
        }
    }

    @Override
    public void viewSetTemperature(MyStdDefinitions.SensorIndex index, float value, String unit_str, boolean alarm, boolean validity)
    {
        if(sensorIndex.equals(index))
        {
            View auxView;
            auxView = findViewById(R.id.viewTemperatureInfo);
            CardView cardTemperatureInfo = (CardView) auxView.findViewById(R.id.cardView);

            TextView txtTemperatureInfo = (TextView) cardTemperatureInfo.findViewById(R.id.txtCardData);
            TextView txtTemperatureInfoAux = (TextView) cardTemperatureInfo.findViewById(R.id.txtCardDataAux);

            txtTemperatureInfo.setText(String.format ("%.0f ", value));
            txtTemperatureInfoAux.setText(unit_str);

            if(validity)
            {
                if(alarm == true)
                {
                    cardTemperatureInfo.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorOutOfRange, null));
                }
                else
                {
                    cardTemperatureInfo.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorInRange, null));
                }
            }
            else
            {
                cardTemperatureInfo.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorNoInformation, null));
            }
        }
    }

    @Override
    public void viewSetBatteryVoltage(MyStdDefinitions.SensorIndex index, float value, boolean alarm, boolean validity)
    {
        if(sensorIndex.equals(index))
        {
            View auxView;
            auxView = findViewById(R.id.viewBatteryInfo);
            CardView cardBatteryInfo = (CardView) auxView.findViewById(R.id.cardView);

            TextView txtBatteryInfo = (TextView) cardBatteryInfo.findViewById(R.id.txtCardData);
            TextView txtBatteryInfoAux = (TextView) cardBatteryInfo.findViewById(R.id.txtCardDataAux);

            txtBatteryInfo.setText(String.format ("%.2f ", value));
            txtBatteryInfoAux.setText("VDC");

            if(validity)
            {
                if(alarm == true)
                {
                    cardBatteryInfo.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorOutOfRange, null));
                }
                else
                {
                    cardBatteryInfo.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorInRange, null));
                }
            }
            else
            {
                cardBatteryInfo.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorNoInformation, null));
            }
        }
    }
}
