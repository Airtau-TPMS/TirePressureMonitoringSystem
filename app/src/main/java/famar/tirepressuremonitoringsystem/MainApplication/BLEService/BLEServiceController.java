package famar.tirepressuremonitoringsystem.MainApplication.BLEService;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class BLEServiceController
{
    Activity activity;
    private static final String TAG = "BLEServiceController";

    public BLEServiceController(Activity activity)
    {
        this.activity = activity;
    }

    public void startMyService()
    {
        Log.d(TAG, "startMyService");

        /* Start the Service*/
        activity.startService(new Intent(activity.getApplicationContext(), BLEService.class));
    }

    public void pauseMyService()
    {
        Log.d(TAG, "pauseMyService");
        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(activity.getApplicationContext());
        //lbm.unregisterReceiver(myBroadcastReceiver);
    }

}
