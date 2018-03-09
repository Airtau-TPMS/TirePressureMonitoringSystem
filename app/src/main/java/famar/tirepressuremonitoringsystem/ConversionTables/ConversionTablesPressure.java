package famar.tirepressuremonitoringsystem.ConversionTables;

import famar.tirepressuremonitoringsystem.pojo.MyStdDefinitions;

/* An utility class mimicking static class behavior:
Declare your class final - Prevents extension of the class since extending a static class makes no sense
Make the constructor private - Prevents instantiation by client code as it makes no sense to instantiate a static class
Make all the members and functions of the class static - Since the class cannot be instantiated no instance methods can be called or instance fields accessed
Note that the compiler will not prevent you from declaring an instance (non-static) member. The issue will only show up if you attempt to call the instance member
*/
public final class ConversionTablesPressure
{
    public ConversionTablesPressure()
    {}

    int[] BARx10={
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

    int[] PSIx10={
            15,
            29,
            44,
            58,
            73,
            87,
            102,
            116,
            131,
            145,
            160,
            174,
            189,
            203,
            218,
            232,
            247,
            261,
            276,
            290,
            305,
            319,
            334,
            348,
            363,
            377,
            392,
            406,
            421,
            435,
            450,
            464,
            479,
            493,
            508,
            522,
            537,
            551,
            566,
            580,
            595,
            609,
            624,
            638,
            653,
            667,
            682,
            696,
            711,
            725,
            740,
            754,
            769,
            783,
            798,
            812,
            827,
            841,
            856,
            870,
            885,
            899,
            914,
            928,
            943,
            957,
            972,
            986,
            1001,
            1015,
            1030,
            1044,
            1059,
            1073,
            1088,
            1102,
            1117,
            1131,
            1146,
            1160,
            1175,
            1189,
            1204,
            1218,
            1233,
            1247,
            1262,
            1276,
            1291,
            1305,
            1320,
            1334,
            1349,
            1363,
            1378,
            1392,
            1407,
            1421,
            1436,
            1450,
    };

    int[] KPAx1={
            10,
            20,
            30,
            40,
            50,
            60,
            70,
            80,
            90,
            100,
            110,
            120,
            130,
            140,
            150,
            160,
            170,
            180,
            190,
            200,
            210,
            220,
            230,
            240,
            250,
            260,
            270,
            280,
            290,
            300,
            310,
            320,
            330,
            340,
            350,
            360,
            370,
            380,
            390,
            400,
            410,
            420,
            430,
            440,
            450,
            460,
            470,
            480,
            490,
            500,
            510,
            520,
            530,
            540,
            550,
            560,
            570,
            580,
            590,
            600,
            610,
            620,
            630,
            640,
            650,
            660,
            670,
            680,
            690,
            700,
            710,
            720,
            730,
            740,
            750,
            760,
            770,
            780,
            790,
            800,
            810,
            820,
            830,
            840,
            850,
            860,
            870,
            880,
            890,
            900,
            910,
            920,
            930,
            940,
            950,
            960,
            970,
            980,
            990,
            1000,
    };

    public int unit_conversion(int value, MyStdDefinitions.PressureUnit from, MyStdDefinitions.PressureUnit To)
    {
        switch(from)
        {
            case UNIT_PSI:
                switch (To)
                {
                    case UNIT_KPA:
                        for(int i=0; i<this.PSIx10.length; i++)
                        {
                            if(value == this.PSIx10[i])
                            {
                                return(this.KPAx1[i]);
                            }
                        }
                        break;
                    case UNIT_BAR:
                        for(int i=0; i<this.PSIx10.length; i++)
                        {
                            if(value == this.PSIx10[i])
                            {
                                return(this.BARx10[i]);
                            }
                        }
                        break;
                }
                break;
            case UNIT_KPA:
                switch (To)
                {
                    case UNIT_PSI:
                        for(int i=0; i<this.KPAx1.length; i++)
                        {
                            if(value == this.KPAx1[i])
                            {
                                return(this.PSIx10[i]);
                            }
                        }
                        break;
                    case UNIT_BAR:
                        for(int i=0; i<this.KPAx1.length; i++)
                        {
                            if(value == this.KPAx1[i])
                            {
                                return(this.BARx10[i]);
                            }
                        }
                        break;
                }
                break;
            case UNIT_BAR:
                switch (To)
                {
                    case UNIT_PSI:
                        for(int i=0; i<this.BARx10.length; i++)
                        {
                            if(value == this.BARx10[i])
                            {
                                return(this.PSIx10[i]);
                            }
                        }
                        break;
                    case UNIT_KPA:
                        for(int i=0; i<this.BARx10.length; i++)
                        {
                            if(value == this.BARx10[i])
                            {
                                return(this.KPAx1[i]);
                            }
                        }
                        break;
                }
        }
        return value;
    }

    public int convert_to_percentage (int value, MyStdDefinitions.PressureUnit unit, int max, int min)
    {
        return (int) (((value-min)*100)/(max-min));
    }

    public int convert_from_percentage (int value, MyStdDefinitions.PressureUnit unit, int max, int min)
    {
        int aux_value = (((max-min)*value)/100)+min;
        /* Look for the closest value */
        switch(unit)
        {
            case UNIT_PSI:
                for(int i=0; i<(this.PSIx10.length-1); i++)
                {
                    if(PSIx10[i]<=aux_value && PSIx10[i+1]>aux_value)
                        return PSIx10[i];

                }
                return PSIx10[this.PSIx10.length-1];
            case UNIT_KPA:
                for(int i=0; i<(this.KPAx1.length-1); i++)
                {
                    if(KPAx1[i]<=aux_value && KPAx1[i+1]>aux_value)
                        return KPAx1[i];

                }
                return KPAx1[this.KPAx1.length-1];
            case UNIT_BAR:
                for(int i=0; i<(this.BARx10.length-1); i++)
                {
                    if(BARx10[i]<=aux_value && BARx10[i+1]>aux_value)
                        return BARx10[i];

                }
                return BARx10[this.BARx10.length-1];
        }
        return (aux_value);
    }
}
