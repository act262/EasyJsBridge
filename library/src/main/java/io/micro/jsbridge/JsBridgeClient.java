package io.micro.jsbridge;

import java.util.Map;

import io.micro.jsbridge.impl.JsBridgeImpl;
import io.micro.jsbridge.impl.JsBridgeWebClientImpl;

/**
 * JsBridge Adapter
 *
 * @author act262@gmail.com
 */
public class JsBridgeClient implements JsBridge, JsBridgeWebClient {

    private JsBridgeImpl bridgeImpl;
    private JsBridgeWebClient bridgeWebClient;

    public JsBridgeClient(Browser browser) {
        this.bridgeImpl = new JsBridgeImpl(browser);
        this.bridgeWebClient = new JsBridgeWebClientImpl(bridgeImpl);
    }

    @Override
    public boolean onInterceptUrl(String url) {
        return bridgeWebClient.onInterceptUrl(url);
    }

    @Override
    public void on(String type, JsResultHandler handler) {
        bridgeImpl.on(type, handler);
    }

    @Override
    public void off(String type) {
        bridgeImpl.off(type);
    }

    @Override
    public boolean handle(String type) {
        return bridgeImpl.handle(type);
    }

    @Override
    public void clear() {
        bridgeImpl.clear();
    }

    @Override
    public void send(String type) {
        bridgeImpl.send(type);
    }

    @Override
    public void send(String type, JsCallback callback) {
        bridgeImpl.send(type, callback);
    }

    @Override
    public <T> void send(String type, Map<String, T> payload) {
        bridgeImpl.send(type, payload);
    }

    @Override
    public <T> void send(String type, Map<String, T> payload, JsCallback callback) {
        bridgeImpl.send(type, payload, callback);
    }
}
