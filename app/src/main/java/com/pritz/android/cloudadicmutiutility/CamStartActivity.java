package com.pritz.android.cloudadicmutiutility;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CamStartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cam_start);
        if (null == savedInstanceState) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, CamFragment.newInstance())
                    .commit();
        }
    }
}
