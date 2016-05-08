package com.wusp.indicatorbox;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.wusp.indicatorbox_library.MarkArea.DynamicFailMark;
import com.wusp.indicatorbox_library.MarkArea.DynamicMarkArea;
import com.wusp.indicatorbox_library.MarkArea.DynamicOkMark;

public class DynamicMarkActivity extends AppCompatActivity {
    private int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_mark);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final DynamicMarkArea markArea = (DynamicMarkArea) findViewById(R.id.area);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (count % 2 == 1){
                        DynamicOkMark okMark = new DynamicOkMark(markArea.getmLineWidth(), markArea.getmSmallLineLength(), markArea.getmLongLineLength());
                        markArea.setDynamicPatternDrawer(okMark);
                        markArea.perform();
                    }else{
                        DynamicFailMark failMark = new DynamicFailMark(markArea.getmLineWidth(), (int) (1.5 * markArea.getmLongLineLength()), Color.WHITE);
                        markArea.setDynamicPatternDrawer(failMark);
                        markArea.perform();
                    }
                    count++;
                }
            });
        }
    }
}
