package com.microquation.sample.activity;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.microquation.linkedme.android.LinkedME;
import com.microquation.sample.R;

/**
 * Created by LinkedME06 on 13/02/2017.
 */

public class AdDetailActivity extends BaseActivity {

    private WebView webView;
    private ProgressBar loading;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ad_detail);
        webView = (WebView) findViewById(R.id.web_view);
        loading = (ProgressBar) findViewById(R.id.loading);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        } else {
            webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        }
        webView.getSettings().setJavaScriptEnabled(true);
        String url = "http://www.linkedme.cc";
        if (!TextUtils.isEmpty(getIntent().getStringExtra("ad_url"))){
            url = getIntent().getStringExtra("ad_url");
        }
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                loading.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                loading.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }
        });

    }

    @Override
    public void onBackPressed() {
        // TODO: 27/02/2017 广告演示：广告详情展示完毕后需要必经的方法中调用该方法执行深度链接跳转
        //用户点击广告详情返回后，一定要执行跳转到详情页
        LinkedME.getInstance().setImmediate(true);
        finish();
    }
}
