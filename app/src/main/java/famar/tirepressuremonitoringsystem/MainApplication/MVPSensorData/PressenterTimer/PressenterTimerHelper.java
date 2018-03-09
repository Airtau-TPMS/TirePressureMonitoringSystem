package famar.tirepressuremonitoringsystem.MainApplication.MVPSensorData.PressenterTimer;

import android.os.Handler;
import android.util.Log;

import famar.tirepressuremonitoringsystem.pojo.MyStdDefinitions;

public class PressenterTimerHelper
{
    private String TAG = "PressenterTimerHelper";

    private iPressenterTimerCallback pressenterTimerCallback;
    private Handler[] timersHandlers = new Handler[MyStdDefinitions.NUM_SENSORS];
    private Runnable[] timersRunnables = new Runnable[MyStdDefinitions.NUM_SENSORS];

    private Runnable createRunnable(final MyStdDefinitions.SensorIndex index)
    {
        Runnable runnable = new Runnable()
        {
            public void run()
            {
                Log.d(TAG, "-----MsgTimeout-----");
                pressenterTimerCallback.pressenterTimerCallback(index);
                //modelSensorData.setValidity(index, false);
                //updateViewObject(index);
            }
        };
        return runnable;
    }

    public void resetMsgTimeout (MyStdDefinitions.SensorIndex index)
    {
        timersHandlers[index.ordinal()].removeCallbacks(timersRunnables[index.ordinal()]);
        timersHandlers[index.ordinal()].postDelayed(timersRunnables[index.ordinal()], MyStdDefinitions.MSG_TIMEOUT);
    }

    public PressenterTimerHelper(iPressenterTimerCallback pressenterTimerCallback)
    {
        this.pressenterTimerCallback = pressenterTimerCallback;
        for(MyStdDefinitions.SensorIndex index : MyStdDefinitions.SensorIndex.values())
        {
            timersHandlers[index.ordinal()] = new Handler();
            timersRunnables[index.ordinal()] = createRunnable(index);
        }
    }
}
