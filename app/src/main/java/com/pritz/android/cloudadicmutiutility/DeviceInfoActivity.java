package com.pritz.android.cloudadicmutiutility;

import android.app.ActivityManager;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DeviceInfoActivity extends AppCompatActivity {

    private static final String TAG = DeviceInfoActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_info);

        getCpuInfo();
        getMemoryInfo();
        getDeviceSuperInfo();
    }

    public void getCpuInfo() {
        try {
            Process proc = Runtime.getRuntime().exec("cat /proc/cpuinfo");
            InputStream is = proc.getInputStream();
            TextView tv = (TextView)findViewById(R.id.tvcmd);
            tv.setText(getStringFromInputStream(is));
        }
        catch (IOException e) {
            Log.e(TAG, "------ getCpuInfo " + e.getMessage());
        }
    }

    public void getMemoryInfo() {
        try {
            Process proc = Runtime.getRuntime().exec("cat /proc/meminfo");
            InputStream is = proc.getInputStream();
            TextView tv = (TextView)findViewById(R.id.tvcmd);
            tv.append(getStringFromInputStream(is));
        }
        catch (IOException e) {
            Log.e(TAG, "------ getMemoryInfo " + e.getMessage());
        }
    }

    private static String getStringFromInputStream(InputStream is) {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = null;

        try {
            while((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        }
        catch (IOException e) {
            Log.e(TAG, "------ getStringFromInputStream " + e.getMessage());
        }
        finally {
            if(br != null) {
                try {
                    br.close();
                }
                catch (IOException e) {
                    Log.e(TAG, "------ getStringFromInputStream " + e.getMessage());
                }
            }
        }

        return sb.toString();
    }

    private void getDeviceSuperInfo() {
        Log.i(TAG, "getDeviceSuperInfo");

        try {

            String s = "Debug-information:";
            s += "\n OS Version: "      + System.getProperty("os.version")      + "(" + android.os.Build.VERSION.INCREMENTAL + ")";
            s += "\n OS API Level: "    + android.os.Build.VERSION.SDK_INT;
            s += "\n Device: "          + android.os.Build.DEVICE;
            s += "\n Model (and Product): " + android.os.Build.MODEL            + " ("+ android.os.Build.PRODUCT + ")";

            s += "\n RELEASE: "         + android.os.Build.VERSION.RELEASE;

            ((TextView)findViewById(R.id.dev_model)).setText(s);
            ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
            ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
            activityManager.getMemoryInfo(mi);
            double availableMegs = mi.totalMem / 0x100000L;

//Percentage can be calculated for API 16+
            double percentAvail = mi.availMem / (double)mi.totalMem;
            ((TextView)findViewById(R.id.dev_name)).setText("RAM SIZE : " + String.valueOf(availableMegs) + "MB");
            Log.i(TAG + " | Device Info > ", s);

        } catch (Exception e) {
            Log.e(TAG, "Error getting Device INFO");
        }

    }//end getDeviceSuperInfo
}
