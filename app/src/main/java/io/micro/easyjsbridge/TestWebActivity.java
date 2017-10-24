package io.micro.easyjsbridge;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.HashMap;
import java.util.Map;

import io.micro.easyjsbridge.util.Utils;
import io.micro.jsbridge.BrowserClient;
import io.micro.jsbridge.Factory;
import io.micro.jsbridge.JsBridge;
import io.micro.jsbridge.JsCallback;
import io.micro.jsbridge.JsResultHandler;
import x5webview.X5WebView;

/**
 * WebView
 */
public class TestWebActivity extends AppCompatActivity {

    private WebView mWebView;
    private JsBridge jsBridge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test_web);

        FrameLayout contentLayout = (FrameLayout) findViewById(R.id.fl_content);
        mWebView = new WebView(getApplicationContext());
        contentLayout.addView(mWebView, new FrameLayout.LayoutParams(-1, -1));
        init();

        mWebView.loadUrl("file:///android_asset/index.html");
    }

    private void init() {
        WebSettings webSetting = mWebView.getSettings();
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        // webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        // webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0).getPath());
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        // webSetting.setPreFectch(true);

        webSetting.setUserAgentString(webSetting.getUserAgentString() + " EasyApp/v1.0.0");
        System.out.println("webSetting.getUserAgentString() = " + webSetting.getUserAgentString());

        CookieSyncManager.createInstance(this);
        CookieSyncManager.getInstance().sync();

        setupJsBridge();
        MyWebViewClient webViewClient = new MyWebViewClient();
        mWebView.setWebViewClient(webViewClient);

    }

    private class MyWebViewClient extends WebViewClient {

        private final BrowserClient delegate;

        MyWebViewClient() {
            delegate = Factory.getDefault(jsBridge);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String url) {
            System.err.println("shouldOverrideUrlLoading " + Uri.decode(url));

            if (url.startsWith("tel:")) {
                Utils.callPhone(TestWebActivity.this, url);
                return true;
            } else if (url.endsWith(".apk")) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                return true;
            } else if (url.startsWith("market://")) {
                Utils.toMarket(TestWebActivity.this, url);
                return true;
            }

            return delegate.shouldOverrideUrlLoading(url);
        }

    }

    private void setupJsBridge() {
        jsBridge = Factory.getDefault();
        jsBridge.configure(new X5WebView(mWebView));

        // js -> app
        jsBridge.on("getSession", new JsResultHandler() {
            @Override
            public void perform(Map<String, String> payload, JsCallback callback) {
                System.out.println("TestWebActivity.perform getSession");
                payload.put("uid", "xxxx");
                payload.put("sid", "xxxx");
                callback.onCall(payload);
            }
        });
        jsBridge.on("login", new JsResultHandler() {
            @Override
            public void perform(Map<String, String> payload, JsCallback callback) {
                String username = payload.get("username");
                String password = payload.get("password");
                Toast.makeText(TestWebActivity.this, "goto login", Toast.LENGTH_SHORT).show();

                callback.onCall(null);
            }
        });
        jsBridge.on("helloNative", new JsResultHandler() {
            @Override
            public void perform(Map<String, String> payload, JsCallback callback) {
                System.out.println("TestWebActivity.perform helloNative");
                System.out.println("payload = [" + payload + "], callback = [" + callback + "]");
                Toast.makeText(TestWebActivity.this, "hello native from js", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void clickAppCallJs(View view) {
        // app -> js
        HashMap<String, String> payload = new HashMap<>(2);
        payload.put("uid", "xx");
        payload.put("sid", "yyy");
        jsBridge.send("helloWeb", payload, new JsCallback() {
            @Override
            public void onCall(Map<String, String> payload) {
                System.out.println("receive hello web payload = [" + payload + "]");
                Toast.makeText(TestWebActivity.this, "hello web execute done", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.destroy();
        mWebView = null;
    }
}
