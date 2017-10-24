package x5webview;

import android.webkit.ValueCallback;

import io.micro.jsbridge.Browser;

/**
 * Simple implementation.
 *
 * @author act262@gmail.com
 */
public class SimpleBrowser implements Browser {

    @Override
    public void loadUrl(String url) {
    }

    @Override
    public void evaluateJavascript(String script, ValueCallback<String> resultCallback) {
    }

    @Override
    public void execJs(String script) {
        loadUrl("javascript:" + script);
    }
}
