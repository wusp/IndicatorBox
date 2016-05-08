package com.wusp.indicatorbox;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.wusp.indicatorbox_library.ProgressBar.BounceFreeBar;

public class BounceFreeBarActivity extends AppCompatActivity {
    private BounceFreeBar qp;
    private int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bounce_free);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        qp = (BounceFreeBar) findViewById(R.id.qp);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (count % 2 == 1){
                        qp.setVisibility(View.GONE);
                    }else{
                        qp.setVisibility(View.VISIBLE);
                    }
                    count++;
                }
            });
        }
    }

}
