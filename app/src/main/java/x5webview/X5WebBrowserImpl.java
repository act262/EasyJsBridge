package x5webview;

import com.tencent.smtt.sdk.WebView;

import io.micro.jsbridge.Browser;
import io.micro.jsbridge.impl.SimpleBrowser;

/**
 * TBS x5WebView implementation.
 *
 * @author act262@gmail.com
 */
public class X5WebBrowserImpl extends SimpleBrowser implements Browser {
    private WebView mWebView;

    public X5WebBrowserImpl(WebView webView) {
        this.mWebView = webView;
    }

    public void loadUrl(String url) {
        this.mWebView.loadUrl(url);
    }
}

