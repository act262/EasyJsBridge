package io.micro.jsbridge.impl;

import android.net.Uri;

import io.micro.jsbridge.JsBridgeConfig;
import io.micro.jsbridge.JsBridgeWebClient;

/**
 * WebViewClient implementation.
 *
 * @author act262@gmail.com
 */
public class JsBridgeWebClientImpl implements JsBridgeWebClient {

    private JsBridgeImpl jsBridge;

    public JsBridgeWebClientImpl(JsBridgeImpl jsBridge) {
        this.jsBridge = jsBridge;
    }

    @Override
    public boolean onInterceptUrl(String url) {
        Uri uri = Uri.parse(url);
        if (matchScheme(uri)) {
            process(uri);
            return true;
        }
        return false;
    }

    private boolean matchScheme(Uri uri) {
        return JsBridgeConfig.SCHEME.equalsIgnoreCase(uri.getScheme());
    }

    private void process(Uri uri) {
        Spec parameter = Spec.parse(uri);
        if ("event".equals(parameter.event)) {
            jsBridge.triggerEventFromWebView(parameter);
        } else if ("callback".equals(parameter.event)) {
            jsBridge.triggerCallbackFromWebView(parameter);
        }
    }

}
