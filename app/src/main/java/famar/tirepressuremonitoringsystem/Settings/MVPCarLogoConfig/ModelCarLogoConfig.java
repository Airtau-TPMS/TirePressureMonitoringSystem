package famar.tirepressuremonitoringsystem.Settings.MVPCarLogoConfig;

import android.content.Context;

import famar.tirepressuremonitoringsystem.DataStorage.SharedPreferenceHelper;

public class ModelCarLogoConfig
{
    private SharedPreferenceHelper sharedPreferenceHelper;
    private Context context;

    private int carLogoIndex;

    public ModelCarLogoConfig(Context context)
    {
        this.context = context;
        sharedPreferenceHelper = new SharedPreferenceHelper(context);

        // Get the stored value
        this.carLogoIndex = sharedPreferenceHelper.getCarLogoIndex();
    }

    public int getCarLogoIndex()
    {
        return carLogoIndex;
    }

    public void setCarLogoIndex(int carLogoIndex)
    {
        this.carLogoIndex = carLogoIndex;
        sharedPreferenceHelper.setCarLogoIndex(carLogoIndex);
    }

}
