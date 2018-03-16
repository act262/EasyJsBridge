package io.micro.jsbridge;

import java.util.Map;

/**
 * Javascript
 *
 * @author act262@gmail.com
 */
public interface JsCallback {

   <T> void onCall(Map<String, T> payload);

}
