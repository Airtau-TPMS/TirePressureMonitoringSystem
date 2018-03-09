package famar.tirepressuremonitoringsystem.ConversionTables;

/* An utility class mimicking static class behavior:
Declare your class final - Prevents extension of the class since extending a static class makes no sense
Make the constructor private - Prevents instantiation by client code as it makes no sense to instantiate a static class
Make all the members and functions of the class static - Since the class cannot be instantiated no instance methods can be called or instance fields accessed
Note that the compiler will not prevent you from declaring an instance (non-static) member. The issue will only show up if you attempt to call the instance member
*/
public final class ConversionTablesBatteryVoltage
{
    public ConversionTablesBatteryVoltage()
    {}

    public int convert_to_percentage (int value, int max, int min)
    {
        return (int) (((value-min)*100)/(max-min));
    }

    public int convert_from_percentage (int value, int max, int min)
    {
        return ((((max-min)*value)/100)+min);
    }
}
