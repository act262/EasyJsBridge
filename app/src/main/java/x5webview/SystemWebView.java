package x5webview;

import android.os.Build;
import android.webkit.ValueCallback;
import android.webkit.WebView;

import io.micro.jsbridge.Browser;

/**
 * Android system WebView implementation.
 *
 * @author act262@gmail.com
 */
public class SystemWebView extends SimpleBrowser implements Browser {

    private WebView mWebView;

    public SystemWebView(WebView webView) {
        this.mWebView = webView;
    }

    @Override
    public void loadUrl(String url) {
        mWebView.loadUrl(url);
    }

    @Override
    public void evaluateJavascript(String script, ValueCallback<String> resultCallback) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mWebView.evaluateJavascript(script, resultCallback);
        }
    }

}
