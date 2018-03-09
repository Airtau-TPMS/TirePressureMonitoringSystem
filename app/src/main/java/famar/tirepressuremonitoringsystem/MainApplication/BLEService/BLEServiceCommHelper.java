package famar.tirepressuremonitoringsystem.MainApplication.BLEService;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.util.List;

public class BLEServiceCommHelper
{
    private String TAG = "BLECommHelper";

    private BluetoothAdapter mBluetoothAdapter;
    BLEService bleService;
    iBLEProcessMessageCallback BLEProcessMessageCallback;

    public BLEServiceCommHelper(BLEService bleService, iBLEProcessMessageCallback Callback)
    {
        this.bleService = bleService;
        this.BLEProcessMessageCallback = Callback;
    }

    /*****************************************************************************
     * @fn         openBLEComm
     *
     * @brief      Start the service actions
     *
     * @param
     *
     * @return     void
     ******************************************************************************/
    public void openBLEComm()
    {
        /**
         * The BluetoothAdapter is required for any and all Bluetooth activity. The BluetoothAdapter
         * represents the device's own Bluetooth adapter (the Bluetooth radio). There's one Bluetooth
         * adapter for the entire system, and your application can interact with it using this object.
         */
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        /**
         * Ensures Bluetooth is available on the device and it is enabled. If not,
         * displays a dialog requesting user permission to enable Bluetooth.
         */
        if (mBluetoothAdapter != null && mBluetoothAdapter.isEnabled())
        {
            /* Enable the scanning for BLE devices */
            scanBLEdevice(true);
        }
    }

    void closeBLEComm()
    {
        Log.i(TAG, "closeBLEComm");

        try
        {
            if (mBluetoothAdapter != null && mBluetoothAdapter.isEnabled())
            {
                /* Disable the scanning for BLE devices */
                scanBLEdevice(false);
            }
        }
        catch (Exception e)
        {
        }
    }

    /*****************************************************************************
     * @fn         scanBLEdevice
     *
     * @brief      To find BLE devices, you use the startLeScan()/startScan() method.
     *             This method takes a callback as a parameter. This callback is how
     *             scan results are returned. Because scanning is battery-intensive,
     *             you should observe the following guidelines:
     *             - As soon as you find the desired device, stop scanning.
     *             - Never scan on a loop, and set a time limit on your scan. A device
     *               that was previously available may have moved out of range, and
     *               continuing to scan drains the battery.
     *
     * @param      enable - Enable or Disable the BLE scan for devices
     *
     * @return     void
     ******************************************************************************/
    private void scanBLEdevice(final boolean enable)
    {
        if (enable)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
                scanBLEDevice21(true);
            }
            else
            {
                scanBLEDevice18(true);
            }
        }
        else
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
                scanBLEDevice21(false);
            }
            else
            {
                scanBLEDevice18(false);
            }
        }
    }

    /*****************************************************************************
     * @fn         processScanRecord
     *
     * @brief      Prepare the scanRecord to be procesed by the UI
     ******************************************************************************/
    public String processScanRecord (byte[] scanRecord)
    {
        StringBuilder hex = new StringBuilder(scanRecord.length * 2);
        for (byte b : scanRecord)
            hex.append(String.format("%02X", b));

        Log.d("DEBUG", "decoded String : " + hex.toString());

        return(hex.toString());
    }

    @RequiresApi(21)
    private void scanBLEDevice21(boolean enable)
    {
        /*****************************************************************************
         * @fn mScanCallback
         *
         * @brief This object contains the method to which the scan results
         *             are returned.
         ******************************************************************************/
        ScanCallback mScanCallback = new ScanCallback()
        {
            /*****************************************************************************
             * @fn onScanResult
             *
             * @brief Callback when a BLE advertisement has been found.
             *
             * @param callbackType - Determines how this callback was triggered.
             *                       Could be one of:
             *                        CALLBACK_TYPE_ALL_MATCHES
             *                        CALLBACK_TYPE_FIRST_MATCH
             *                        CALLBACK_TYPE_MATCH_LOST
             *
             * @param result - ScanResult: A Bluetooth LE scan result.
             *
             * @return void
             ******************************************************************************/
            @Override
            public void onScanResult(int callbackType, ScanResult result)
            {
                String devicename = result.getDevice().getName();
                if (devicename != null)
                {
                    Log.i(TAG, "Device name:" + devicename);

                    Log.i(TAG, "callbackType: " + String.valueOf(callbackType));
                    Log.i(TAG, "result: " + result.toString());

                    if (devicename.contains(BLEServiceConstants.BTDeviceName))
                    {
                        Log.i(TAG, "Advertisement: ");

                        /* 0x100 is the manufacturer key used by the TPMS chinesse modules */
                        BLEProcessMessageCallback.sendMessage(processScanRecord(result.getScanRecord().getManufacturerSpecificData(0x100)));
                    }
                }
            }

            /*****************************************************************************
             * @fn onBatchScanResults
             *
             * @brief Callback when batch results are delivered.
             *
             * @param results - List: List of scan results that are previously scanned.
             *
             * @return void
             ******************************************************************************/
            @Override
            public void onBatchScanResults(List<ScanResult> results)
            {
                for (ScanResult sr : results)
                {
                    Log.i(TAG, "onBatchScanResults:" + sr.toString());
                }
            }

            /*****************************************************************************
             * @fn onScanFailed
             *
             * @brief Callback when scan could not be started.
             *
             * @param errorCode - int: Error code (one of SCAN_FAILED_*) for scan failure.
             *
             * @return void
             ******************************************************************************/
            @Override
            public void onScanFailed(int errorCode)
            {
                Log.e(TAG, "onScanFailed: " + errorCode);
            }
        };

        final BluetoothLeScanner bluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();

        if (enable)
        {
            bluetoothLeScanner.startScan(mScanCallback);
        }
        else
        {
            bluetoothLeScanner.stopScan(mScanCallback);
        }
    }

    private void scanBLEDevice18(boolean enable)
    {

        /*****************************************************************************
         * @fn mLeScanCallback
         *
         * @brief This object contains the callback to which the scan results
         *             are returned.
         ******************************************************************************/
        BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback()
        {
            @Override
            public void onLeScan(final BluetoothDevice device, int rssi, final byte[] scanRecord)
            {
                Log.i(TAG, "onLeScan");

                Runnable r = new Runnable()
                {
                    public void run()
                    {
                        Log.i("onLeScan", device.toString());
                        BLEProcessMessageCallback.sendMessage(processScanRecord(scanRecord));
                    }
                };

                Thread t = new Thread(r);
                t.start();
            }
        };

        if (enable)
        {
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        }
        else
        {
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
    }
}
