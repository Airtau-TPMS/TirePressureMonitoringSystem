package famar.tirepressuremonitoringsystem.pojo;

/* An utility class mimicking static class behavior:
Declare your class final - Prevents extension of the class since extending a static class makes no sense
Make the constructor private - Prevents instantiation by client code as it makes no sense to instantiate a static class
Make all the members and functions of the class static - Since the class cannot be instantiated no instance methods can be called or instance fields accessed
Note that the compiler will not prevent you from declaring an instance (non-static) member. The issue will only show up if you attempt to call the instance member
*/
public final class MyStdDefinitions
{
    private MyStdDefinitions()
    {}

    public enum PressureUnit
    {
        UNIT_PSI,
        UNIT_KPA,
        UNIT_BAR
    }

    public enum TemperatureUnit
    {
        UNIT_C,
        UNIT_F
    }

    public enum SensorIndex
    {
        SENSOR_FL,
        SENSOR_FR,
        SENSOR_RL,
        SENSOR_RR,
    }
    public static int NUM_SENSORS = 4;

    public static final int CODE_GRANT_PERMISSION_BT = 1;
    public static final int CODE_GRANT_PERMISSION_COARSE_LOCATION = 2;
    public static final int CODE_REQUEST_BT_ENABLE = 0;

    public static final String KEY_MORE_INFORMATION = "KEY_MORE_INFORMATION";
    public static final String KEY_FL_ID = "KEY_FL_ID";
    public static final String KEY_FR_ID = "KEY_FR_ID";
    public static final String KEY_RL_ID = "KEY_RL_ID";
    public static final String KEY_RR_ID = "KEY_RR_ID";

    public static final String KEY_PRESSURE_UNIT = "KEY_PRESSURE_UNIT";
    public static final String KEY_TEMPERATURE_UNIT = "KEY_TEMPERATURE_UNIT";

    public static final String KEY_CAR_LOGO = "KEY_CAR_LOGO";
    public static final String KEY_PLAY_ALARM = "KEY_PLAY_ALARM";

    public static final String KEY_PRESSURE_LIMIT_UP = "KEY_PRESSURE_LIMIT_UP";
    public static final String KEY_PRESSURE_LIMIT_LOW = "KEY_PRESSURE_LIMIT_LOW";
    public static final String KEY_TEMPERATURE_LIMIT_UP = "KEY_TEMPERATURE_LIMIT_UP";
    public static final String KEY_BATTERY_LIMIT_LOW = "KEY_BATTERY_LIMIT_LOW";

    public static final int MSG_TIMEOUT = 30000;
}
