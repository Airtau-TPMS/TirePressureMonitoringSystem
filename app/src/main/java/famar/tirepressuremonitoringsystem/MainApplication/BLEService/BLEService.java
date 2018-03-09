package famar.tirepressuremonitoringsystem.MainApplication.BLEService;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class BLEService extends Service implements iBLEProcessMessageCallback
{
    String MyTag = "BLEService";
    BLEServiceCommHelper bleCommHelper;
    /*****************************************************************************
     * @fn         onCreate
     *
     * @brief      This method is called when the activity is first created and
     *             the ideal location for most initialization tasks to be performed.
     *             The methis is passed an argument in the form of a Bundle object that
     *             may contain dynamic state information (typically relating to the state
     *             of the user interface) from a prior invocation of the activity.
     *
     * @param
     *
     * @return     void
     ******************************************************************************/
    @Override
    public void onCreate()
    {
        bleCommHelper = new BLEServiceCommHelper(this, this);
        bleCommHelper.openBLEComm();
    }

    @Override
    public void onDestroy()
    {
        bleCommHelper.closeBLEComm();
        super.onDestroy();
    }

    /*****************************************************************************
     * @fn         onStartCommand
     *
     * @brief      We want this service to continue running until it is explicitly
     *             stopped, so return sticky.
     *
     * @param
     *
     * @return     void
     ******************************************************************************/
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        return Service.START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    public void sendMessage(String str)
    {
        Log.d(MyTag, "sendMessage");
        Intent intent = new Intent(BLEServiceConstants.KEY_MY_MESSAGE);

        if(str.length() >= 32)
        {
            /* Create a Bundle object */
            Bundle b = new Bundle();

            /* associate the uuid string data stored with bundle key "uuid" */
            b.putString("id_data", str.substring(0, 12));
            b.putString("pressure_data", str.substring(12, 20));
            b.putString("temperature_data", str.substring(20, 28));
            b.putString("battery_data", str.substring(28, 32));

            /* Pass bundle object b to the intent */
            intent.putExtra(BLEServiceConstants.KEY_MY_TPMS_MESSAGE_DATA, b);

            /* Send the intent */
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        }
    }
}
