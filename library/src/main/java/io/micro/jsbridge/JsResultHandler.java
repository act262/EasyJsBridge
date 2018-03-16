package io.micro.jsbridge;

import java.util.Map;

/**
 * Javascript eval result callback
 *
 * @author act262@gmail.com
 */
public interface JsResultHandler {

    // TODO: 2018/3/12 泛型支持
    void perform(Map<String, Object> payload, JsCallback callback);

}
