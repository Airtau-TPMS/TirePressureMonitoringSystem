package famar.tirepressuremonitoringsystem.MainApplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Handler;
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
import famar.tirepressuremonitoringsystem.MainApplication.BLEService.BLEServiceController;
import famar.tirepressuremonitoringsystem.MainApplication.MVPSensorData.PressenterSensorData;
import famar.tirepressuremonitoringsystem.MainApplication.MVPSensorData.iViewSensorData;
import famar.tirepressuremonitoringsystem.R;
import famar.tirepressuremonitoringsystem.Settings.SettingsActivity;
import famar.tirepressuremonitoringsystem.pojo.MyStdDefinitions;

import static famar.tirepressuremonitoringsystem.pojo.MyStdDefinitions.SensorIndex.SENSOR_FL;
import static famar.tirepressuremonitoringsystem.pojo.MyStdDefinitions.SensorIndex.SENSOR_FR;
import static famar.tirepressuremonitoringsystem.pojo.MyStdDefinitions.SensorIndex.SENSOR_RL;
import static famar.tirepressuremonitoringsystem.pojo.MyStdDefinitions.SensorIndex.SENSOR_RR;


public class MainActivity extends AppCompatActivity implements iViewSensorData
{
    private BLEServiceController bleServiceController;
    private PressenterSensorData pressenterSensorData;
    private MyBeep mBeep;

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
                intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.mHelp:
                intent = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(intent);
                break;
            case R.id.mAbout:
                intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle(getResources().getString(R.string.titleMainActivity));
        checkPermissions();

        bleServiceController = new BLEServiceController(this);
        bleServiceController.startMyService();
    }

    @Override
    protected  void onResume()
    {
        super.onResume();  // Always call the superclass method first

        SharedPreferences appPreferences = getSharedPreferences("appPreferences", Context.MODE_PRIVATE);

        boolean playAlarm = appPreferences.getBoolean(MyStdDefinitions.KEY_PLAY_ALARM,false);
        mBeep = new MyBeep(playAlarm);

        View auxView;
        auxView = findViewById(R.id.viewFL);
        CardView cardFL = (CardView) auxView.findViewById(R.id.cardView);
        auxView = findViewById(R.id.viewFR);
        CardView cardFR = (CardView) auxView.findViewById(R.id.cardView);
        auxView = findViewById(R.id.viewRL);
        CardView cardRL = (CardView) auxView.findViewById(R.id.cardView);
        auxView = findViewById(R.id.viewRR);
        CardView cardRR = (CardView) auxView.findViewById(R.id.cardView);

        TextView txtTitle;
        txtTitle = (TextView)cardFL.findViewById(R.id.txtCardTitle);
        txtTitle.setText(getResources().getString(R.string.title_card_fl));
        txtTitle = (TextView)cardFR.findViewById(R.id.txtCardTitle);
        txtTitle.setText(getResources().getString(R.string.title_card_fr));
        txtTitle = (TextView)cardRL.findViewById(R.id.txtCardTitle);
        txtTitle.setText(getResources().getString(R.string.title_card_rl));
        txtTitle = (TextView)cardRR.findViewById(R.id.txtCardTitle);
        txtTitle.setText(getResources().getString(R.string.title_card_rr));

        int carLogoIndex = appPreferences.getInt(MyStdDefinitions.KEY_CAR_LOGO, 0);
        ImageView imgCarLogo = (ImageView)findViewById(R.id.imgCarLogo);
        // get resource ID by index
        TypedArray imgCars = getResources().obtainTypedArray(R.array.img_cars);
        //set the ImageView's resource to the id
        imgCarLogo.setImageResource(imgCars.getResourceId(carLogoIndex, 0));

        cardFL.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, MoreInformationActivity.class);
                intent.putExtra(MyStdDefinitions.KEY_MORE_INFORMATION, MyStdDefinitions.SensorIndex.SENSOR_FL.ordinal());
                //intent.putExtra(getResources().getString(R.string.KEY_TITLE), getResources().getString(R.string.title_card_fl));
                startActivity(intent);
            }
        });

        cardFR.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, MoreInformationActivity.class);
                intent.putExtra(MyStdDefinitions.KEY_MORE_INFORMATION, MyStdDefinitions.SensorIndex.SENSOR_FR.ordinal());
                //intent.putExtra(getResources().getString(R.string.KEY_TITLE), getResources().getString(R.string.title_card_fr));
                startActivity(intent);
            }
        });

        cardRL.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, MoreInformationActivity.class);
                intent.putExtra(MyStdDefinitions.KEY_MORE_INFORMATION, MyStdDefinitions.SensorIndex.SENSOR_RL.ordinal());
                //intent.putExtra(getResources().getString(R.string.KEY_TITLE), getResources().getString(R.string.title_card_rl));
                startActivity(intent);
            }
        });

        cardRR.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, MoreInformationActivity.class);
                intent.putExtra(MyStdDefinitions.KEY_MORE_INFORMATION, MyStdDefinitions.SensorIndex.SENSOR_RR.ordinal());
                //intent.putExtra(getResources().getString(R.string.KEY_TITLE), getResources().getString(R.string.title_card_rr));
                startActivity(intent);
            }
        });


        //pressenterSensorData = new PressenterSensorData(modelSensorData, this);
        pressenterSensorData = PressenterSensorData.getInstance(this);
        pressenterSensorData.refreshPresenterData(this);

        // ****************************************************************************************
        // ****************************************************************************************
        // ****************************************************************************************
        pressenterSensorData.initViewObjects();
        // ****************************************************************************************
        // ****************************************************************************************
        // ****************************************************************************************
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        mBeep.Beep(false);
        bleServiceController.pauseMyService();
    }

    void checkPermissions()
    {
        Intent intent = new Intent(MainActivity.this, PermissionsManager.class);
        startActivity(intent);
    }

    @Override
    public void viewSetSensorID(MyStdDefinitions.SensorIndex index, String value, boolean validity)
    {

    }

    @Override
    public void viewSetPressure(MyStdDefinitions.SensorIndex index, float value, String unit_str, boolean alarm, boolean validity)
    {
        View auxView = null;
        CardView cardView = null;

        if(index.equals(SENSOR_FL))
        {
            auxView = findViewById(R.id.viewFL);
            cardView = (CardView) auxView.findViewById(R.id.cardView);
        }
        else if(index.equals(SENSOR_FR))
        {
            auxView = findViewById(R.id.viewFR);
            cardView = (CardView) auxView.findViewById(R.id.cardView);
        }
        else if(index.equals(SENSOR_RL))
        {
            auxView = findViewById(R.id.viewRL);
            cardView = (CardView) auxView.findViewById(R.id.cardView);
        }
        else if(index.equals(SENSOR_RR))
        {
            auxView = findViewById(R.id.viewRR);
            cardView = (CardView) auxView.findViewById(R.id.cardView);
        }
        if(cardView!=null && auxView!=null)
        {
            TextView txtCardData = (TextView) auxView.findViewById(R.id.txtCardData);
            TextView txtCardDataAux = (TextView) auxView.findViewById(R.id.txtCardDataAux);
            txtCardData.setText(String.format ("%.2f ", value));
            txtCardDataAux.setText(unit_str);
            if(validity == true)
            {
                if (alarm == true)
                {
                    cardView.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorOutOfRange, null));
                    //beepAlarm = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
                    //beepAlarm.startTone(ToneGenerator.TONE_CDMA_PIP, 150);
                    mBeep.Beep(true);
                }
                else
                {
                    cardView.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorInRange, null));
                    mBeep.Beep(false);
                }
            }
            else
            {
                cardView.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorNoInformation, null));
                mBeep.Beep(false);
            }
        }
    }

    @Override
    public void viewSetTemperature(MyStdDefinitions.SensorIndex index, float value, String unit_str, boolean alarm, boolean validity)
    {
        View auxView = null;
        CardView cardView = null;
        ImageView imgIconTemperatureWarning = null;

        if(index.equals(SENSOR_FL))
        {
            auxView = findViewById(R.id.viewFL);
            cardView = (CardView) auxView.findViewById(R.id.cardView);
            imgIconTemperatureWarning = (ImageView) auxView.findViewById(R.id.imgIconTemperatureWarningLeft);
        }
        else if(index.equals(SENSOR_FR))
        {
            auxView = findViewById(R.id.viewFR);
            cardView = (CardView) auxView.findViewById(R.id.cardView);
            imgIconTemperatureWarning = (ImageView) auxView.findViewById(R.id.imgIconTemperatureWarningRight);
        }
        else if(index.equals(SENSOR_RL))
        {
            auxView = findViewById(R.id.viewRL);
            cardView = (CardView) auxView.findViewById(R.id.cardView);
            imgIconTemperatureWarning = (ImageView) auxView.findViewById(R.id.imgIconTemperatureWarningLeft);
        }
        else if(index.equals(SENSOR_RR))
        {
            auxView = findViewById(R.id.viewRR);
            cardView = (CardView) auxView.findViewById(R.id.cardView);
            imgIconTemperatureWarning = (ImageView) auxView.findViewById(R.id.imgIconTemperatureWarningRight);
        }

        if(cardView!=null && auxView!=null && imgIconTemperatureWarning!=null)
        {
            if (alarm == true)
            {
                imgIconTemperatureWarning.setVisibility(View.VISIBLE);
            }
            else
            {
                imgIconTemperatureWarning.setVisibility(View.INVISIBLE);
            }
        }
    }



    @Override
    public void viewSetBatteryVoltage(MyStdDefinitions.SensorIndex index, float value, boolean alarm, boolean validity)
    {
        View auxView = null;
        CardView cardView = null;
        ImageView imgIconBatteryWarning = null;

        if(index.equals(SENSOR_FL))
        {
            auxView = findViewById(R.id.viewFL);
            cardView = (CardView) auxView.findViewById(R.id.cardView);
            imgIconBatteryWarning = (ImageView) auxView.findViewById(R.id.imgIconBatteryWarningLeft);
        }
        else if(index.equals(SENSOR_FR))
        {
            auxView = findViewById(R.id.viewFR);
            cardView = (CardView) auxView.findViewById(R.id.cardView);
            imgIconBatteryWarning = (ImageView) auxView.findViewById(R.id.imgIconBatteryWarningRight);
        }
        else if(index.equals(SENSOR_RL))
        {
            auxView = findViewById(R.id.viewRL);
            cardView = (CardView) auxView.findViewById(R.id.cardView);
            imgIconBatteryWarning = (ImageView) auxView.findViewById(R.id.imgIconBatteryWarningLeft);
        }
        else if(index.equals(SENSOR_RR))
        {
            auxView = findViewById(R.id.viewRR);
            cardView = (CardView) auxView.findViewById(R.id.cardView);
            imgIconBatteryWarning = (ImageView) auxView.findViewById(R.id.imgIconBatteryWarningRight);
        }

        if(cardView!=null && auxView!=null && imgIconBatteryWarning!=null)
        {
            if (alarm == true)
            {
                imgIconBatteryWarning.setVisibility(View.VISIBLE);
            }
            else
            {
                imgIconBatteryWarning.setVisibility(View.INVISIBLE);
            }
        }
    }

    class MyBeep
    {
        boolean current_playing;
        boolean playAlarm;
        private ToneGenerator beepAlarm;
        private Handler alarmHandlers = new Handler();

        public MyBeep(boolean playAlarm)
        {
            this.current_playing = false;
            this.playAlarm = playAlarm;
            beepAlarm = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
            alarmHandlers = new Handler();
        }

        private Runnable startSoundRunnable = new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    if(current_playing)
                    {
                        beepAlarm.startTone(ToneGenerator.TONE_CDMA_PIP, 150);
                        /* Restart the handler */
                        alarmHandlers.postDelayed(startSoundRunnable, 2000);
                    }
                }
                catch (Exception ex) {}
            }
        };

        public void Beep (boolean play)
        {
            if(playAlarm)
            {
                if (play == true)
                {
                    if (current_playing == false)
                    {
                        alarmHandlers.removeCallbacks(startSoundRunnable);
                        alarmHandlers.postDelayed(startSoundRunnable, 2000);
                    }
                    current_playing = true;
                }
                else
                {
                    if (current_playing == true)
                    {
                        beepAlarm.stopTone();
                        alarmHandlers.removeCallbacks(startSoundRunnable);
                    }
                    current_playing = false;
                }
            }
        }
    }
}
