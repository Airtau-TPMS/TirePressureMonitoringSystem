package famar.tirepressuremonitoringsystem.ConversionTables;

import famar.tirepressuremonitoringsystem.pojo.MyStdDefinitions;

/* An utility class mimicking static class behavior:
Declare your class final - Prevents extension of the class since extending a static class makes no sense
Make the constructor private - Prevents instantiation by client code as it makes no sense to instantiate a static class
Make all the members and functions of the class static - Since the class cannot be instantiated no instance methods can be called or instance fields accessed
Note that the compiler will not prevent you from declaring an instance (non-static) member. The issue will only show up if you attempt to call the instance member
*/
public final class ConversionTablesTemperature
{
    public ConversionTablesTemperature()
    {}

    int[] CELSIUS={
            1,
            2,
            3,
            4,
            5,
            6,
            7,
            8,
            9,
            10,
            11,
            12,
            13,
            14,
            15,
            16,
            17,
            18,
            19,
            20,
            21,
            22,
            23,
            24,
            25,
            26,
            27,
            28,
            29,
            30,
            31,
            32,
            33,
            34,
            35,
            36,
            37,
            38,
            39,
            40,
            41,
            42,
            43,
            44,
            45,
            46,
            47,
            48,
            49,
            50,
            51,
            52,
            53,
            54,
            55,
            56,
            57,
            58,
            59,
            60,
            61,
            62,
            63,
            64,
            65,
            66,
            67,
            68,
            69,
            70,
            71,
            72,
            73,
            74,
            75,
            76,
            77,
            78,
            79,
            80,
            81,
            82,
            83,
            84,
            85,
            86,
            87,
            88,
            89,
            90,
            91,
            92,
            93,
            94,
            95,
            96,
            97,
            98,
            99,
            100,
    };

    int[] FAHRENHEIT={
            32,
            34,
            36,
            37,
            39,
            41,
            43,
            45,
            46,
            48,
            50,
            52,
            54,
            55,
            57,
            59,
            61,
            63,
            64,
            66,
            68,
            70,
            72,
            73,
            75,
            77,
            79,
            81,
            82,
            84,
            86,
            88,
            90,
            91,
            93,
            95,
            97,
            99,
            100,
            102,
            104,
            106,
            108,
            109,
            111,
            113,
            115,
            117,
            118,
            120,
            122,
            124,
            126,
            127,
            129,
            131,
            133,
            135,
            136,
            138,
            140,
            142,
            144,
            145,
            147,
            149,
            151,
            153,
            154,
            156,
            158,
            160,
            162,
            163,
            165,
            167,
            169,
            171,
            172,
            174,
            176,
            178,
            180,
            181,
            183,
            185,
            187,
            189,
            190,
            192,
            194,
            196,
            198,
            199,
            201,
            203,
            205,
            207,
            208,
            210,
            212,
    };

    public int unit_conversion(int value, MyStdDefinitions.TemperatureUnit from, MyStdDefinitions.TemperatureUnit To)
    {
        switch(from)
        {
            case UNIT_C:
                switch (To)
                {
                    case UNIT_F:
                        for(int i=0; i<this.CELSIUS.length; i++)
                        {
                            if(value == this.CELSIUS[i])
                            {
                                return(this.FAHRENHEIT[i]);
                            }
                        }
                        break;
                }
                break;
            case UNIT_F:
                switch (To)
                {
                    case UNIT_C:
                        for(int i=0; i<this.FAHRENHEIT.length; i++)
                        {
                            if(value == this.FAHRENHEIT[i])
                            {
                                return(this.CELSIUS[i]);
                            }
                        }
                        break;
                }
                break;
        }
        return value;
    }

    public int convert_to_percentage (int value, MyStdDefinitions.TemperatureUnit unit, int max, int min)
    {
        return (int) (((value-min)*100)/(max-min));
    }

    public int convert_from_percentage (int value, MyStdDefinitions.TemperatureUnit unit, int max, int min)
    {
        int aux_value = (((max-min)*value)/100)+min;
        /* Look for the closest value */
        switch(unit)
        {
            case UNIT_C:
                for(int i=0; i<(this.CELSIUS.length-1); i++)
                {
                    if(CELSIUS[i]<=aux_value && CELSIUS[i+1]>aux_value)
                        return CELSIUS[i];

                }
                return CELSIUS[this.CELSIUS.length-1];
            case UNIT_F:
                for(int i=0; i<(this.FAHRENHEIT.length-1); i++)
                {
                    if(FAHRENHEIT[i]<=aux_value && FAHRENHEIT[i+1]>aux_value)
                        return FAHRENHEIT[i];

                }
                return FAHRENHEIT[this.FAHRENHEIT.length-1];
        }
        return (aux_value);
    }
}
