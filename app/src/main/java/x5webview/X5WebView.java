package x5webview;

import android.webkit.ValueCallback;

import com.tencent.smtt.sdk.WebView;

import io.micro.jsbridge.Browser;

/**
 * TBS x5WebView implementation.
 *
 * @author act262@gmail.com
 */
public class X5WebView extends SimpleBrowser implements Browser {

    private WebView mWebView;

    public X5WebView(WebView webView) {
        this.mWebView = webView;
    }

    @Override
    public void loadUrl(String url) {
        mWebView.loadUrl(url);
    }

    @Override
    public void evaluateJavascript(String script, ValueCallback<String> resultCallback) {
        mWebView.evaluateJavascript(script, (com.tencent.smtt.sdk.ValueCallback<String>) resultCallback);
    }
}
