package com.pritz.android.cloudadicmutiutility;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.pritz.android.cloudadicmutiutility.helpers.NetworkInfoDialog;

public class MainActivity extends AppCompatActivity
            implements View.OnClickListener{

    private ImageButton mCameraOption;
    private ImageButton mLocationOption;
    private ImageButton mNotification;
    private ImageButton mWeatherOption;
    private ImageButton mDeviceInfoOption;
    private ImageButton mCheckInternetOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCameraOption = (ImageButton) findViewById(R.id.btn_camera);
        mLocationOption = (ImageButton) findViewById(R.id.btn_location);
        mNotification = (ImageButton) findViewById(R.id.btn_notif);
        mWeatherOption = (ImageButton) findViewById(R.id.btn_weather);
        mDeviceInfoOption = (ImageButton) findViewById(R.id.btn_weather);
        mCheckInternetOption = (ImageButton) findViewById(R.id.btn_check_internet);
        mCameraOption.setOnClickListener(this);
        mLocationOption.setOnClickListener(this);
        mDeviceInfoOption.setOnClickListener(this);
        mNotification.setOnClickListener(this);
        mCheckInternetOption.setOnClickListener(this);
        mWeatherOption.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_camera:
                startActivity(new Intent(this, CamStartActivity.class));
                break;
            case R.id.btn_check_internet:
                NetworkInfoDialog dialog=new NetworkInfoDialog(this);
                dialog.show();
                //Another Simple Approach using AlertDialog
                //checkInternetProp();
                break;

            case R.id.btn_weather:
                startActivity(new Intent(this, WeatherActivity.class));
                break;
            case R.id.btn_location:
                startActivity(new Intent(this, MapsActivity.class));
                break;

            case R.id.btn_device:
                startActivity(new Intent(this, DeviceInfoActivity.class));
                break;

            case R.id.btn_notif:
                startActivity(new Intent(this, NotificationAct.class));
        }
    }

    public void checkInternetProp(){
        ConnectivityManager connectivityManager =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        StringBuilder message = new StringBuilder();
        message.append("INTERNET STATUS - ");
        if(activeNetworkInfo!= null && activeNetworkInfo.isConnected()){
            message.append("CONNECTED\n");
            message.append("MODE - ");


            if(activeNetworkInfo.getType() == connectivityManager.TYPE_WIFI ) {
                message.append("WIFI");
            } else if( activeNetworkInfo.getType() == connectivityManager.TYPE_WIFI ) {
                message.append("MOBILE DATA");
            }
        }else{
            message.append("DISCONNECTED\n");
            message.append("NO INTERNET");
        }


        new AlertDialog.Builder(this).setIcon(R.drawable.internet)
                .setTitle("Network Info")
                .setMessage(message.toString())
                .setPositiveButton("DISMISS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })

                .show();

    }
}
