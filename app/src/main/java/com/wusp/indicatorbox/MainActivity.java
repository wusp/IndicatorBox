package com.wusp.indicatorbox;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ListView lvContent;
    private ContentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        lvContent = (ListView) findViewById(R.id.lvContent);
        setSupportActionBar(toolbar);
        adapter = new ContentAdapter(this, R.layout.item_list_content);
        adapter.add("ShrinkButton Example");
        adapter.add("ViewPager Indicator Example");
        adapter.add("Bounce-FreeDown Example");
        adapter.add("Dynamic MarkArea Example");
        adapter.add("Particle Heart Example");
        adapter.add("Flash Border Example");
        adapter.add("Flash Border and Ripple Circle Example");
        lvContent.setAdapter(adapter);
        lvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                switch (position) {
                    case 0:
                        intent = new Intent(MainActivity.this, ShrinkButtonActivity.class);
                        break;
                    case 1:
                        intent = new Intent(MainActivity.this, ViewPagerIndicatorActivity.class);
                        break;
                    case 2:
                        intent = new Intent(MainActivity.this, BounceFreeBarActivity.class);
                        break;
                    case 3:
                        intent = new Intent(MainActivity.this, DynamicMarkActivity.class);
                        break;
                    case 4:
                        intent = new Intent(MainActivity.this, ParticleActivity.class);
                        break;
                    case 5:
                        intent = new Intent(MainActivity.this, FlashBorderActivity.class);
                        break;
                    case 6:
                        intent = new Intent(MainActivity.this, FlashBorderAndRippleCircleActivity.class);
                        break;
                }
                if (intent != null) {
                    startActivity(intent);
                }
            }
        });
    }

    private class ContentAdapter extends ArrayAdapter<String> {
        private int resId;

        public ContentAdapter(Context context, int resource) {
            super(context, resource);
            resId = resource;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ContentHolder contentHolder;
            if (convertView == null){
                contentHolder = new ContentHolder();
                convertView = getLayoutInflater().inflate(resId, parent, false);
                contentHolder.tvContent = (TextView) convertView.findViewById(R.id.tv);
                convertView.setTag(contentHolder);
            }else{
                contentHolder = (ContentHolder) convertView.getTag();
            }
            contentHolder.tvContent.setText(getItem(position));
            return convertView;
        }
    }

    private class ContentHolder{
        TextView tvContent;
    }
}
