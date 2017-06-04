package com.pritz.android.cloudadicmutiutility;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class NotificationAct extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "checker";
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        mEditText = (EditText) findViewById(R.id.edit_text);
        //buildNotification();
        findViewById(R.id.button).setOnClickListener(this);
    }





    @Override
    public void onClick(View v) {
        Intent intent=new Intent(this,NotificationService.class);
        intent.putExtra("text", mEditText.getText().toString());
        AlarmManager manager=(AlarmManager)getSystemService(Activity.ALARM_SERVICE);
        PendingIntent pendingIntent=PendingIntent.getService(this,
                0,intent, 0);
        Calendar cal = Calendar.getInstance();
        Log.d(TAG, "onClick: " + cal.getTime().toString());
        cal.add(Calendar.MINUTE, 1);
        Log.d(TAG, "onClick: " + cal.getTime().toString());
        manager.setExact(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),pendingIntent);
        Toast.makeText(this, "You will get a notification in 1 minute", Toast.LENGTH_LONG).show();

    }
}
