package io.micro.jsbridge;

/**
 * WebViewClient core's interface.
 *
 * @author act262@gmail.com
 */
public interface JsBridgeWebClient {

    /**
     * Should intercept some protocol url.
     */
    boolean onInterceptUrl(String url);

}
