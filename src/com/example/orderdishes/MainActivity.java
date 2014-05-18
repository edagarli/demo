package com.example.orderdishes;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SlidingDrawer;

public class MainActivity extends Activity {
      private GridView gv;
      private SlidingDrawer sd;
      private ImageView iv;
      private List<ResolveInfo> apps;

      /** Called when the activity is first created. */
      @Override
      public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
           setContentView(R.layout. activity_main);
           loadApps();
            gv = (GridView) findViewById(R.id.allApps);
            sd = (SlidingDrawer) findViewById(R.id.sliding);
            iv = (ImageView) findViewById(R.id.imageViewIcon);
            gv.setAdapter( new GridAdapter());
            sd.setOnDrawerOpenListener( new SlidingDrawer.OnDrawerOpenListener()// 开抽屉
           {
                 @Override
                 public void onDrawerOpened() {
                      iv.setImageResource(R.drawable.ic_launcher); // 响应开抽屉事件
                                                                                       // ，把图片设为向下的
                }
           });
            sd.setOnDrawerCloseListener( new SlidingDrawer.OnDrawerCloseListener() {
                 @Override
                 public void onDrawerClosed() {
                      iv.setImageResource(R.drawable. ic_launcher); // 响应关抽屉事件
                }
           });
     }

      private void loadApps() {
           Intent intent = new Intent(Intent. ACTION_MAIN, null);
           intent.addCategory(Intent. CATEGORY_LAUNCHER );
           apps = getPackageManager().queryIntentActivities(intent, 0);
     }

      public class GridAdapter extends BaseAdapter {
            public GridAdapter() {

           }

            public int getCount() {
                 // TODO Auto-generated method stub
                 return apps.size();
           }

            public Object getItem( int position) {
                 // TODO Auto-generated method stub
                 return apps.get(position);
           }

            public long getItemId( int position) {
                 // TODO Auto-generated method stub
                 return position;
           }

            public View getView( int position, View convertView, ViewGroup parent) {
                 // TODO Auto-generated method stub
                ImageView imageView = null;
                 if (convertView == null) {
                     imageView = new ImageView(MainActivity.this );
                     imageView.setScaleType(ImageView.ScaleType. FIT_CENTER);
                     imageView.setLayoutParams( new GridView.LayoutParams(50, 50));
                } else {
                     imageView = (ImageView) convertView;
                }

                ResolveInfo ri = apps.get(position);
                imageView.setImageDrawable(ri. activityInfo
                           .loadIcon(getPackageManager()));

                 return imageView;
           }

     }
}
