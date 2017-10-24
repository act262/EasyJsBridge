package io.micro.jsbridge;

import android.webkit.ValueCallback;

/**
 * WebView core's abstract interface.
 *
 * @author act262@gmail.com
 */
public interface Browser {

    void loadUrl(String url);

    void evaluateJavascript(String script, ValueCallback<String> resultCallback);

    void execJs(String script);

}
