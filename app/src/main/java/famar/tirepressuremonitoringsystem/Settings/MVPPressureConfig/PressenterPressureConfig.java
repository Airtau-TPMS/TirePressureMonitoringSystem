package famar.tirepressuremonitoringsystem.Settings.MVPPressureConfig;

import android.util.Log;

import famar.tirepressuremonitoringsystem.ConversionTables.ConversionTablesPressure;
import famar.tirepressuremonitoringsystem.pojo.MyStdDefinitions;

public class PressenterPressureConfig
{
    private String TAG = "TagPressenter";
    private ModelPressureConfig modelPressureConfig;
    private iViewPressureConfig viewPressureConfig;
    private ConversionTablesPressure conversionTablesPressure;

    public PressenterPressureConfig(ModelPressureConfig modelPressureConfig, iViewPressureConfig viewPressureConfig)
    {
        Log.d(TAG, "PressenterPressureConfig");
        this.modelPressureConfig = modelPressureConfig;
        this.viewPressureConfig = viewPressureConfig;
        conversionTablesPressure = new ConversionTablesPressure();
    }

    public void initViewObjects()
    {
        Log.d(TAG, "initViewObjects");
        updateViewPressureUnit();
        updateViewUpperLimitPressure();
        updateViewLowerLimitPressure();
        updateViewPlayAlarm();
    }

    public void updateViewPressureUnit()
    {
        Log.d(TAG, "updateViewPressureUnit");
        viewPressureConfig.viewSetPressureUnit(modelPressureConfig.getPressureUnit());
    }

    public void updateViewPlayAlarm()
    {
        viewPressureConfig.viewSetPlayAlarm(modelPressureConfig.getPlayAlarm());
    }

    public void updateViewUpperLimitPressure()
    {
        Log.d(TAG, "updateViewUpperLimitPressure");
        int value = conversionTablesPressure.convert_to_percentage(modelPressureConfig.getUpperLimitPressure(), modelPressureConfig.getPressureUnit(), modelPressureConfig.getMaxUpperLimitPressure(), modelPressureConfig.getMinUpperLimitPressure());
        viewPressureConfig.viewSetSeekbarUpperLimitPressure(value);
        switch(modelPressureConfig.getPressureUnit())
        {
            case UNIT_BAR:
                /* The value in BAR is managed into the model with a x10 factor */
                viewPressureConfig.viewSetTxtUpperLimitPressure((float)modelPressureConfig.getUpperLimitPressure()/10, modelPressureConfig.getPressureUnitStr());
                break;
            case UNIT_PSI:
                /* The value in PSI is managed into the model with a x10 factor */
                viewPressureConfig.viewSetTxtUpperLimitPressure((float)modelPressureConfig.getUpperLimitPressure()/10, modelPressureConfig.getPressureUnitStr());
                break;
            case UNIT_KPA:
                /* The value in KPA is managed into the model with a x1 factor */
                viewPressureConfig.viewSetTxtUpperLimitPressure((float)modelPressureConfig.getUpperLimitPressure(), modelPressureConfig.getPressureUnitStr());
                break;
        }

    }

    public void updateViewLowerLimitPressure()
    {
        Log.d(TAG, "updateViewLowerLimitPressure");
        int value = conversionTablesPressure.convert_to_percentage(modelPressureConfig.getLowerLimitPressure(), modelPressureConfig.getPressureUnit(), modelPressureConfig.getMaxLowerLimitPressure(), modelPressureConfig.getMinLowerLimitPressure());
        viewPressureConfig.viewSetSeekbarLowerLimitPressure(value);
        switch(modelPressureConfig.getPressureUnit())
        {
            case UNIT_BAR:
                /* The value in BAR is managed into the model withg a x10 factor */
                viewPressureConfig.viewSetTxtLowerLimitPressure((float)modelPressureConfig.getLowerLimitPressure()/10, modelPressureConfig.getPressureUnitStr());
                break;
            case UNIT_PSI:
                /* The value in PSI is managed into the model withg a x10 factor */
                viewPressureConfig.viewSetTxtLowerLimitPressure((float)modelPressureConfig.getLowerLimitPressure()/10, modelPressureConfig.getPressureUnitStr());
                break;
            case UNIT_KPA:
                /* The value in KPA is managed into the model withg a x1 factor */
                viewPressureConfig.viewSetTxtLowerLimitPressure((float)modelPressureConfig.getLowerLimitPressure(), modelPressureConfig.getPressureUnitStr());
                break;
        }
    }

    public void updateValueUpperLimitPressure(int percentage)
    {
        Log.d(TAG, "updateValueUpperLimitPressure");
        int value = conversionTablesPressure.convert_from_percentage(percentage, modelPressureConfig.getPressureUnit(), modelPressureConfig.getMaxUpperLimitPressure(), modelPressureConfig.getMinUpperLimitPressure());
        switch(modelPressureConfig.getPressureUnit())
        {
            case UNIT_BAR:
                /* The value in BAR is managed into the model withg a x10 factor */
                viewPressureConfig.viewSetTxtUpperLimitPressure((float)value/10, modelPressureConfig.getPressureUnitStr());
                break;
            case UNIT_PSI:
                /* The value in PSI is managed into the model withg a x10 factor */
                viewPressureConfig.viewSetTxtUpperLimitPressure((float)value/10, modelPressureConfig.getPressureUnitStr());
                break;
            case UNIT_KPA:
                /* The value in KPA is managed into the model withg a x1 factor */
                viewPressureConfig.viewSetTxtUpperLimitPressure((float)value, modelPressureConfig.getPressureUnitStr());
                break;
        }
    }

    public void updateValueLowerLimitPressure(int percentage)
    {
        Log.d(TAG, "updateValueLowerLimitPressure");
        int value = conversionTablesPressure.convert_from_percentage(percentage, modelPressureConfig.getPressureUnit(), modelPressureConfig.getMaxLowerLimitPressure(), modelPressureConfig.getMinLowerLimitPressure());
        switch(modelPressureConfig.getPressureUnit())
        {
            case UNIT_BAR:
                /* The value in BAR is managed into the model withg a x10 factor */
                viewPressureConfig.viewSetTxtLowerLimitPressure((float)value/10, modelPressureConfig.getPressureUnitStr());
                break;
            case UNIT_PSI:
                /* The value in PSI is managed into the model withg a x10 factor */
                viewPressureConfig.viewSetTxtLowerLimitPressure((float)value/10, modelPressureConfig.getPressureUnitStr());
                break;
            case UNIT_KPA:
                /* The value in KPA is managed into the model withg a x1 factor */
                viewPressureConfig.viewSetTxtLowerLimitPressure((float)value, modelPressureConfig.getPressureUnitStr());
                break;
        }
    }

    public void setPressureUnit(MyStdDefinitions.PressureUnit pressureUnit)
    {
        Log.d(TAG, "setPressureUnit");
        modelPressureConfig.setPressureUnit(pressureUnit);
        //updateViewPressureUnit();
        updateViewUpperLimitPressure();
        updateViewLowerLimitPressure();
    }

    public void setValueUpperLimitPressure(int percentage)
    {
        Log.d(TAG, "setValueUpperLimitPressure");
        int value = conversionTablesPressure.convert_from_percentage(percentage, modelPressureConfig.getPressureUnit(), modelPressureConfig.getMaxUpperLimitPressure(), modelPressureConfig.getMinUpperLimitPressure());
        modelPressureConfig.setUpperLimitPressure(value);
    }

    public void setValueLowerLimitPressure(int percentage)
    {
        Log.d(TAG, "setValueLowerLimitPressure");
        int value = conversionTablesPressure.convert_from_percentage(percentage, modelPressureConfig.getPressureUnit(), modelPressureConfig.getMaxLowerLimitPressure(), modelPressureConfig.getMinLowerLimitPressure());
        modelPressureConfig.setLowerLimitPressure(value);
    }

    public void setPlayAlarm(boolean playAlarm)
    {
        Log.d(TAG, "setPlayAlarm");
        modelPressureConfig.setPlayAlarm(playAlarm);
    }
}
