package io.micro.jsbridge;

import java.util.Map;

/**
 * Javascript
 *
 * @author act262@gmail.com
 */
public interface JsCallback {

    void onCall(Map<String, String> payload);

}
