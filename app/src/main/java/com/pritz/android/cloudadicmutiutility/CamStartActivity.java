package com.pritz.android.cloudadicmutiutility;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.flurgle.camerakit.CameraKit;
import com.flurgle.camerakit.CameraListener;
import com.flurgle.camerakit.CameraView;
import com.pritz.android.cloudadicmutiutility.helpers.FileSaver;

import org.w3c.dom.Text;

import java.io.File;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import static android.R.attr.bitmap;
import static android.R.id.message;
import static com.pritz.android.cloudadicmutiutility.R.drawable.camera;
import static junit.runner.Version.id;

public class CamStartActivity extends AppCompatActivity implements View.OnClickListener{

    private CameraView cameraView;
    private ImageButton captureButton;
    private ImageButton switchButton;
    private ImageButton recordButton;
    private Button stopButton;
    private boolean isFrontFacing;
    private TextView mTimer;
    int passedSenconds;
    Boolean isActivityRunning = false;
    Timer timer;
    TimerTask timerTask;

    @Override
    protected void onResume() {
        super.onResume();
        if(cameraView != null) {
            cameraView.start();
        }
    }

    @Override
    protected void onPause() {
        if(cameraView != null) {
            cameraView.stop();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        cameraView = null;
        super.onDestroy();
    }

    public void reScheduleTimer(){
        timer = new Timer();
        timerTask = new myTimerTask();
        timer.schedule(timerTask, 0, 1000);
    }

    private class myTimerTask extends TimerTask {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            passedSenconds++;
            updateLabel.sendEmptyMessage(0);
        }
    }

    private Handler updateLabel = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            //super.handleMessage(msg);

            int seconds = passedSenconds % 60;
            int minutes = (passedSenconds / 60) % 60;
            int hours = (passedSenconds / 3600);
            mTimer.setText(String.format("%02d : %02d", minutes, seconds));
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cam_start);
        cameraView = (CameraView) findViewById(R.id.camera);
        captureButton = (ImageButton) findViewById(R.id.picture);
        switchButton = (ImageButton) findViewById(R.id.switch_camera_id);
        recordButton = (ImageButton) findViewById(R.id.take_video);
        stopButton = (Button) findViewById(R.id.stop_id);
        mTimer = (TextView) findViewById(R.id.time_txt);
        passedSenconds = 0;
        isFrontFacing = false;
        captureButton.setOnClickListener(this);
        switchButton.setOnClickListener(this);
        recordButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);

        cameraView.setCameraListener(new CameraListener() {
            @Override
            public void onPictureTaken(byte[] picture) {
                super.onPictureTaken(picture);
                // add it to the gallery
                Bitmap result = BitmapFactory.decodeByteArray(picture, 0, picture.length);
                boolean canWriteToExternal = FileSaver.isExternalStorageWritable();
                String fileName = "image_" + new Date().getTime() + ".png";

                    FileSaver saver = new FileSaver(CamStartActivity.this)
                            .setExternal(canWriteToExternal)
                            .setFileName(fileName)
                            .setDirectoryName("cloudadic");

                    saver.save(result);
                Toast.makeText(CamStartActivity.this, "saved image " + saver.getFilePath(), Toast.LENGTH_SHORT).show();
                //showDial(saver.getFilePath());

            }

            @Override
            public void onVideoTaken(File video) {
                super.onVideoTaken(video);
                //showDial(video.getAbsolutePath());

            }
        });
    }

    public void showDial(final String filepath){
        new AlertDialog.Builder(this).setIcon(android.R.drawable.gallery_thumb)
                .setTitle("Message")
                .setMessage("File Saved. Do you want to view it?")
                .setPositiveButton("show", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.parse(filepath), "image/* video/*");
                        startActivity(intent);
                    }
                })
                .setNegativeButton("dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing

                    }
                })
                .show();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.picture:
                cameraView.captureImage();
                break;
            case R.id.switch_camera_id:
                if (!isFrontFacing) {
                    cameraView.setFacing(CameraKit.Constants.FACING_FRONT);
                    isFrontFacing = true;
                } else {
                    cameraView.setFacing(CameraKit.Constants.FACING_BACK);

                }
                break;
            case R.id.take_video:
                cameraView.startRecordingVideo();
                recordButton.setEnabled(false);
                stopButton.setVisibility(View.VISIBLE);
                if (isActivityRunning) {
                    //pause running activity
                    timer.cancel();
                    isActivityRunning = false;
                } else {
                    reScheduleTimer();
                    isActivityRunning = true;
                }

              mTimer.setVisibility(View.VISIBLE);
              break;


            case R.id.stop_id:
                timer.cancel();
                passedSenconds = 0;
                mTimer.setText("00 : 00");

                isActivityRunning = false;
                recordButton.setEnabled(true);
                cameraView.stopRecordingVideo();
                stopButton.setVisibility(View.INVISIBLE);
                mTimer.setVisibility(View.INVISIBLE);
                reScheduleTimer();


        }
    }
}
