package famar.tirepressuremonitoringsystem.MainApplication.MVPSensorData.PressenterTimer;

import famar.tirepressuremonitoringsystem.pojo.MyStdDefinitions;

/**
 * Created by jpablo on 07/03/2018.
 */

public interface iPressenterTimerCallback
{
    void pressenterTimerCallback(MyStdDefinitions.SensorIndex index);
}
