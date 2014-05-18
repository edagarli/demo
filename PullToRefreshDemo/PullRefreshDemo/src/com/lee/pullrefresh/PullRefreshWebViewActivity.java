package com.lee.pullrefresh;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lee.pullrefresh.ui.PullToRefreshBase;
import com.lee.pullrefresh.ui.PullToRefreshBase.OnRefreshListener;
import com.lee.pullrefresh.ui.PullToRefreshWebView;

public class PullRefreshWebViewActivity extends Activity {

    private WebView mWebView;
    private PullToRefreshWebView mPullWebView;
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm");
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.pull_refresh_webview);
        
        mPullWebView = (PullToRefreshWebView) findViewById(R.id.pull_webview);//new PullToRefreshWebView(this);
        mPullWebView.setOnRefreshListener(new OnRefreshListener<WebView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<WebView> refreshView) {
                loadUrl();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<WebView> refreshView) {
            }
        });
        
        mWebView = mPullWebView.getRefreshableView();
        mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
            
            public void onPageFinished(WebView view, String url) {
                mPullWebView.onPullDownRefreshComplete();
                setLastUpdateTime();
            }
        });

        loadUrl();
        setLastUpdateTime();
    }
    
    
    private void setLastUpdateTime() {
        String text = formatDateTime(System.currentTimeMillis());
        mPullWebView.setLastUpdatedLabel(text);
    }
    
    private String formatDateTime(long time) {
        if (0 == time) {
            return "";
        }
        
        return mDateFormat.format(new Date(time));
    }
    
    int mIndex = 0;
    
    private void loadUrl() {
        
        int length = sUrls.length;
        mIndex = mIndex % length;
        String url = sUrls[mIndex];
        mIndex++;
        mWebView.loadUrl(url);
    }
    
    private static final String[] sUrls = {
//        "file:///android_asset/html/test.html",
        "http://www.baidu.com",
        "http://www.google.com",
        "http://www.163.com",
//        "http://www.sina.com.cn",
//        "http://www.sohu.com"
    };
    
    static class JSInterface {
        public void getClass2() {
            
        }
    }
}
