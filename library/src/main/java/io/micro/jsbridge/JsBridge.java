package io.micro.jsbridge;

import java.util.Map;

/**
 * Javascript & Native Bridge
 *
 * @author act262@gmail.com
 */
public interface JsBridge {

    void on(String type, JsResultHandler handler);

    void off(String type);

    boolean handle(String type);

    void clear();

    void send(String type);

    void send(String type, JsCallback callback);

    <T> void send(String type, Map<String, T> payload);

    <T> void send(String type, Map<String, T> payload, JsCallback callback);

}
