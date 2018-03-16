package io.micro.jsbridge;

/**
 * WebView core's abstract interface.
 *
 * @author act262@gmail.com
 */
public interface Browser {

    void loadUrl(String url);

    void execJs(String script);
}
