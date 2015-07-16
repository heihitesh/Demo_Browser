package com.jaigaur.demobrowser;

import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Hitesh Verma on 6/7/2015.
 */
public class ourViewClent extends WebViewClient {
    WebView webView;
    @Override
    public boolean shouldOverrideUrlLoading(WebView v,String url){
        v.loadUrl(url);
        return true;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);

    }
}
