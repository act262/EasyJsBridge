package io.micro.jsbridge.impl;

import android.webkit.WebView;

import io.micro.jsbridge.Browser;

/**
 * System WebView Wrapper
 *
 * @author act262@gmail.com
 */
public class SystemWebBrowserImpl extends SimpleBrowser implements Browser {

    private WebView mWebView;

    public SystemWebBrowserImpl(WebView webView) {
        this.mWebView = webView;
    }

    @Override
    public void loadUrl(String url) {
        mWebView.loadUrl(url);
    }

}
