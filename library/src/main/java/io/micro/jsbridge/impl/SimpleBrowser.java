package io.micro.jsbridge.impl;

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
    public void execJs(String script) {
        loadUrl("javascript:" + script);
    }
}
