package io.micro.jsbridge;

import java.util.Map;

/**
 * Javascript Bridge
 *
 * @author act262@gmail.com
 */
public interface JsBridge {

    void configure(Browser browser);

    void on(String type, JsResultHandler handler);

    void off(String type);

    boolean handle(String type);

    void clear();

    void send(String type);

    void send(String type, Map<String, String> payload);

    void send(String type, JsCallback callback);

    void send(String type, Map<String, String> payload, JsCallback callback);

}
