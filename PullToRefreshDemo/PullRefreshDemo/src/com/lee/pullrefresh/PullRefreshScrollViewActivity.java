package com.lee.pullrefresh;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lee.pullrefresh.ui.PullToRefreshBase;
import com.lee.pullrefresh.ui.PullToRefreshBase.OnRefreshListener;
import com.lee.pullrefresh.ui.PullToRefreshScrollView;

public class PullRefreshScrollViewActivity extends Activity {

    private ScrollView mScrollView;
    private PullToRefreshScrollView mPullScrollView;
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm");
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mPullScrollView = new PullToRefreshScrollView(this);
        setContentView(mPullScrollView);
        
        mPullScrollView.setOnRefreshListener(new OnRefreshListener<ScrollView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                new GetDataTask().execute();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                
            }
        });
        
        mScrollView = mPullScrollView.getRefreshableView();
        mScrollView.addView(createTextView());
        
        setLastUpdateTime();
    }
    
    private class GetDataTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... params) {
            // Simulates a background job.
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(String[] result) {
            mPullScrollView.onPullDownRefreshComplete();
            setLastUpdateTime();

            super.onPostExecute(result);
        }
    }
    private void setLastUpdateTime() {
        String text = formatDateTime(System.currentTimeMillis());
        mPullScrollView.setLastUpdatedLabel(text);
    }
    
    private String formatDateTime(long time) {
        if (0 == time) {
            return "";
        }
        
        return mDateFormat.format(new Date(time));
    }
    
    private TextView createTextView() {
        TextView textView = new TextView(this);
        
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < 200; ++i) {
            sb.append(String.format(" %03d", i)).append("\n");
        }
        
        textView.setText(sb.toString());
        textView.setTextColor(Color.WHITE);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(18);
        
        return textView;
    }
}
