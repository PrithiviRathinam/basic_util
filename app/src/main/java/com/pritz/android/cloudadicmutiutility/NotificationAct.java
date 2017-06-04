package com.pritz.android.cloudadicmutiutility;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.widget.EditText;

import static com.pritz.android.cloudadicmutiutility.R.drawable.notification;

public class NotificationAct extends AppCompatActivity {

    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        mEditText = (EditText) findViewById(R.id.edit_text);
        buildNotification();
    }

    public void buildNotification(){
        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.notification)
                        .setContentTitle("Your notification")
                        .setAutoCancel(true)
                        .setContentText(mEditText.getText());
        mBuilder.build().notify();
    }
}
