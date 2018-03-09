package famar.tirepressuremonitoringsystem.Settings.MVPCarLogoConfig;

import android.util.Log;

public class PressenterCarLogoConfig
{
    private String TAG = "TagPressenter";
    private ModelCarLogoConfig modelCarLogoConfig;
    private iViewCarLogoConfig viewCarLogoConfig;

    public PressenterCarLogoConfig(ModelCarLogoConfig modelCarLogoConfig, iViewCarLogoConfig viewCarLogoConfig)
    {
        Log.d(TAG, "PressenterSensorIDConfig");
        this.modelCarLogoConfig = modelCarLogoConfig;
        this.viewCarLogoConfig = viewCarLogoConfig;
    }

    public void initViewObjects()
    {
        Log.d(TAG, "initViewObjects");
        updateViewCarLogoIndex();
    }

    public void updateViewCarLogoIndex()
    {
        Log.d(TAG, "updateViewCarLogoIndex");
        viewCarLogoConfig.viewUpdateCarLogo(modelCarLogoConfig.getCarLogoIndex());
    }
    
    public void setCarLogoIndex(int carLogoIndex)
    {
        Log.d(TAG, "setValueSensorIdFL");
        modelCarLogoConfig.setCarLogoIndex(carLogoIndex);
    }
}
