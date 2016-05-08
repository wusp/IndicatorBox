package com.wusp.indicatorbox;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.wusp.indicatorbox_library.ShrinkButton.ShrinkButton;

public class ShrinkButtonActivity extends AppCompatActivity {
    private ShrinkButton btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shrink_button);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        btnSignIn = (ShrinkButton) findViewById(R.id.btnSignIn);
        setSupportActionBar(toolbar);
        ((RelativeLayout)btnSignIn.getParent()).getBackground().setAlpha(155);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Restart progressing animation if the animation is paused or stoped.",
                        Snackbar.LENGTH_SHORT)
                        .setAction("Ok", null).show();
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnSignIn.stop();
                    Snackbar.make(v, "Pause animation.",
                            Snackbar.LENGTH_SHORT)
                            .setAction("Ok", null).show();
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shrink_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
