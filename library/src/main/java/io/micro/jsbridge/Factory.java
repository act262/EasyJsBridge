package io.micro.jsbridge;

/**
 * Some factory function.
 */
public class Factory {

    public static JsBridge getDefault() {
        return new JsBridgeImpl();
    }

    public static BrowserClient getDefault(JsBridge jsBridge) {
        return new BrowserClientImpl((JsBridgeImpl) jsBridge);
    }
}
