package com.pritz.android.cloudadicmutiutility.helpers;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.pritz.android.cloudadicmutiutility.R;

import org.w3c.dom.Text;

/**
 * Created by HP-PC on 03-06-2017.
 */

public class NetworkInfoDialog extends Dialog implements View.OnClickListener {
    public Activity mCalledActivity;

    public Button mDismissButton;
    private TextView mStatusTextView;
    private TextView mNetTypeTextView;

    public NetworkInfoDialog(Activity a) {
        super(a);

        this.mCalledActivity = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

        setContentView(R.layout.network_dialog);
        mStatusTextView = (TextView) findViewById(R.id.txt_network_info_1);
        mNetTypeTextView = (TextView) findViewById(R.id.txt_network_info_2);
        addNetworkInfo();
        mDismissButton = (Button) findViewById(R.id.btn_dismiss);
        mDismissButton.setOnClickListener(this);
    }


    public void addNetworkInfo() {
        ConnectivityManager connectivityManager =
            (ConnectivityManager) mCalledActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
    StringBuilder message = new StringBuilder();
        message.append("INTERNET STATUS - ");
        if(activeNetworkInfo!=null&&activeNetworkInfo.isConnected())

    {
        message.append("CONNECTED");
        mStatusTextView.setText(message.toString());
        message = new StringBuilder();
        message.append("MODE - ");
        message.append(activeNetworkInfo.getTypeName());

        if (activeNetworkInfo.getType() == connectivityManager.TYPE_WIFI) {
            message.append("WIFI");
        } else if (activeNetworkInfo.getType() == connectivityManager.TYPE_WIFI) {
            message.append("MOBILE DATA");
        }
        mNetTypeTextView.setText(message.toString());
    }else

    {
        message.append("DISCONNECTED");
        mStatusTextView.setText(message.toString());
        mNetTypeTextView.setText("NO INTERNET");

    }

}


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_dismiss:
                dismiss();
                break;

            default:
                break;
        }
        dismiss();
    }
}

