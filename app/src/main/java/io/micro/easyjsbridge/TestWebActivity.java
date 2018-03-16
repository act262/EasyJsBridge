package io.micro.easyjsbridge;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.tencent.smtt.sdk.WebView;

/**
 * WebView
 */
public class TestWebActivity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test_web);

        FrameLayout contentLayout = (FrameLayout) findViewById(R.id.fl_content);
        mWebView = new WebView(getApplicationContext());
        contentLayout.addView(mWebView, new FrameLayout.LayoutParams(-1, -1));

        mWebView.loadUrl("file:///android_asset/index.html");
    }

}
