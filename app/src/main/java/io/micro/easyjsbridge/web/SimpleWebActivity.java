package io.micro.easyjsbridge.web;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import io.micro.easyjsbridge.R;
import io.micro.easyjsbridge.app.BaseActivity;
import io.micro.jsbridge.BrowserClient;
import io.micro.jsbridge.Factory;
import io.micro.jsbridge.JsBridge;
import io.micro.jsbridge.JsCallback;
import io.micro.jsbridge.JsResultHandler;
import x5webview.SystemWebView;

/**
 * Use System WebView.
 *
 * @author act262@gmail.com
 */
public class SimpleWebActivity extends BaseActivity {

    private WebView mWebView;
    private Toolbar mToolbar;

    private JsBridge jsBridge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_web);
        mToolbar = findViewById(R.id.toolBar);
        setSupportActionBar(mToolbar);

        mWebView = new WebView(this);
        FrameLayout contentLayout = findViewById(R.id.fl_content);
        contentLayout.addView(mWebView);

        initWebView();
        setupWeb();

        mWebView.loadUrl("file:///android_asset/simple_page.html");
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.stopLoading();
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        WebSettings webSetting = mWebView.getSettings();
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0).getPath());
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);

        webSetting.setUserAgentString(webSetting.getUserAgentString() + " EasyApp/v1.0.0");
    }

    private void setupWeb() {
        jsBridge = Factory.getDefault();
        jsBridge.configure(new SystemWebView(mWebView));
        mWebView.setWebChromeClient(new MyWebChromeClient());
        mWebView.setWebViewClient(new MyWebViewClient());

        jsBridge.on("helloNative", new JsResultHandler() {
            @Override
            public void perform(Map<String, String> payload, JsCallback callback) {
                showToast(payload.get("say"));
            }
        });

        jsBridge.on("addRightButton", new JsResultHandler() {
            @Override
            public void perform(Map<String, String> payload, JsCallback callback) {
                addRightButton(payload, callback);
            }
        });

        jsBridge.on("showShare", new JsResultHandler() {
            @Override
            public void perform(Map<String, String> payload, JsCallback callback) {
                String shareTitle = payload.get("shareTitle");
                String shareContent = payload.get("shareContent");
                String shareImage = payload.get("shareImage");
                String shareUrl = payload.get("shareUrl");
                Toast.makeText(SimpleWebActivity.this, "share....", Toast.LENGTH_SHORT).show();
            }
        });

        jsBridge.on("shareButton", new JsResultHandler() {
            @Override
            public void perform(Map<String, String> payload, JsCallback callback) {
                setupShareButton1(payload);
            }
        });
    }

    private void setupShareButton1(final Map<String, String> payload) {
        final ImageButton button = new ImageButton(this);
        mToolbar.addView(button);
        button.setContentDescription(payload.get("text"));
        Glide.with(this).load(payload.get("icon")).into(button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String share = payload.get("share");
                    JSONObject jsonObject = new JSONObject(share);
                    String shareTitle = jsonObject.optString("shareTitle");
                    String shareContent = jsonObject.optString("shareContent");
                    String shareImage = jsonObject.optString("shareImage");
                    String shareUrl = jsonObject.optString("shareUrl");

                    showToast("share");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void addRightButton(Map<String, String> payload, final JsCallback callback) {
        Button button = new Button(this);
        mToolbar.addView(button);
        button.setText(payload.get("text"));
        String icon = payload.get("icon");

        final String clickListener = payload.get("clickListener");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsBridge.send(clickListener);
            }
        });
    }


    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            mToolbar.setTitle(title);
        }
    }

    private class MyWebViewClient extends WebViewClient {

        private final BrowserClient delegate;

        MyWebViewClient() {
            delegate = Factory.getDefault(jsBridge);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String url) {
            return WebHelper.processUrl(SimpleWebActivity.this, url)
                    || delegate.shouldOverrideUrlLoading(url);
        }
    }

}
