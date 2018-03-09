package famar.tirepressuremonitoringsystem.MainApplication;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import famar.tirepressuremonitoringsystem.R;
import famar.tirepressuremonitoringsystem.pojo.MyStdDefinitions;

public class PermissionsManager extends AppCompatActivity
{
    private Activity activity;

    private int checkFinishCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this;

        checkFinishCounter = 4; /* Count the number of permission/enable function calls */
        grantPermission_Location();
        grantPermission_Bluetooth();
        enableDevice_Bluetooth();
        verify_BLE_availability();
    }

    void checkFinishActivity()
    {
        if(checkFinishCounter>0)
        {
            checkFinishCounter--;
            if(0 == checkFinishCounter)
            {
                finish();
            }
        }
    }

    public void grantPermission_Location()
    {
        /**
         * In order to receive location updates from NETWORK_PROVIDER or GPS_PROVIDER, you must
         * request the user's permission
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (activity.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MyStdDefinitions.CODE_GRANT_PERMISSION_COARSE_LOCATION);
            }
            else
            {
                checkFinishActivity();
            }
        }
        else
        {
            checkFinishActivity();
        }
    }

    public void grantPermission_Bluetooth()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (activity.checkSelfPermission(Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.BLUETOOTH}, MyStdDefinitions.CODE_GRANT_PERMISSION_BT);
            }
            else
            {
                checkFinishActivity();
            }
        }
        else
        {
            checkFinishActivity();
        }
    }

    public void enableDevice_Bluetooth()
    {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(mBluetoothAdapter == null)
        {
            final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle(R.string.functionality_limited);
            builder.setMessage(R.string.grant_permissions_error2);
            builder.setPositiveButton(R.string.close_string, null);
            builder.setOnDismissListener(new DialogInterface.OnDismissListener()
            {
                @Override
                public void onDismiss(DialogInterface dialog)
                {
                    checkFinishActivity();
                }
            });
            builder.show();
        }
        else
        {
            if (!mBluetoothAdapter.isEnabled())
            {
                Intent BTDeviceEnableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                activity.startActivityForResult(BTDeviceEnableIntent, MyStdDefinitions.CODE_REQUEST_BT_ENABLE);
            }
            else
            {
                checkFinishActivity();
            }
        }
    }

    public void verify_BLE_availability()
    {
        /* Verify if the device has BLE capabilities */
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE))
        {
            final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle(R.string.functionality_limited);
            builder.setMessage(R.string.grant_permissions_error4);
            builder.setPositiveButton(R.string.close_string, null);
            builder.setOnDismissListener(new DialogInterface.OnDismissListener()
            {
                @Override
                public void onDismiss(DialogInterface dialog)
                {
                    checkFinishActivity();
                }
            });
            builder.show();
        }
        checkFinishActivity();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == MyStdDefinitions.CODE_REQUEST_BT_ENABLE)
        {
            if (resultCode == RESULT_OK)
            {
                checkFinishActivity();
            }
            else
            {
                Log.d("MyTag","BT DISABLE");
                final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
                builder.setTitle(R.string.functionality_limited);
                builder.setMessage(R.string.grant_permissions_error3);
                builder.setPositiveButton(R.string.close_string, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener()
                {
                    @Override
                    public void onDismiss(DialogInterface dialog)
                    {
                        checkFinishActivity();
                    }
                });
                builder.show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        switch (requestCode)
        {
            case MyStdDefinitions.CODE_GRANT_PERMISSION_COARSE_LOCATION:
            {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    checkFinishActivity();
                }
                else
                {
                    Log.d("MyTag","PERMISSION NOT GRANTED");
                    final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
                    builder.setTitle(R.string.functionality_limited);
                    builder.setMessage(R.string.grant_permissions_error1);
                    builder.setPositiveButton(R.string.close_string, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener()
                    {
                        @Override
                        public void onDismiss(DialogInterface dialog)
                        {
                            checkFinishActivity();
                        }
                    });
                    builder.show();
                }
            }

            case MyStdDefinitions.CODE_GRANT_PERMISSION_BT:
            {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    checkFinishActivity();
                }
                else
                {
                    final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
                    builder.setTitle(R.string.functionality_limited);
                    builder.setMessage(R.string.grant_permissions_error2);
                    builder.setPositiveButton(R.string.close_string, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener()
                    {
                        @Override
                        public void onDismiss(DialogInterface dialog)
                        {
                            checkFinishActivity();
                        }
                    });
                    builder.show();
                }
            }
        }
    }
}
