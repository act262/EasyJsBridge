package io.micro.jsbridge;

import android.util.SparseArray;

import org.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Js bridge implementation.
 *
 * @author act262@gmail.com
 */
class JsBridgeImpl implements JsBridge {

    private Browser mBrowser;
    private Map<String, JsResultHandler> mListeners = new HashMap<>(10);
    private SparseArray<JsCallback> mCallbacks = new SparseArray<>(10);

    @Override
    public void configure(Browser browser) {
        this.mBrowser = browser;
    }

    @Override
    public void on(String type, JsResultHandler handler) {
        if (!handle(type)) {
            mListeners.put(type, handler);
        }
    }

    @Override
    public void off(String type) {
        mListeners.remove(type);
    }

    @Override
    public boolean handle(String type) {
        return mListeners.containsKey(type);
    }

    @Override
    public void clear() {
        mListeners.clear();
        mListeners = null;
        mCallbacks.clear();
        mCallbacks = null;
    }

    @Override
    public void send(String type) {
        send(type, null, null);
    }

    @Override
    public void send(String type, Map<String, String> payload) {
        send(type, payload, null);
    }

    @Override
    public void send(String type, JsCallback callback) {
        send(type, null, callback);
    }

    @Override
    public void send(String type, Map<String, String> payload, JsCallback callback) {
        int messageId = messageCount;
        if (callback != null) {
            mCallbacks.put(messageId, callback);
        }
        triggerEventToWebView(type, messageId, payload);

        messageCount++;
    }

    private int messageCount = 0;

    // A
    private void triggerEventToWebView(String type, int messageId, Map<String, String> payload) {
        if (payload == null)
            payload = Collections.emptyMap();

        String script = String.format(Locale.getDefault(),
                "Jockey.trigger(\"%s\",%d,%s)",
                type, messageId, new JSONObject(payload));

        System.out.println("JsBridgeImpl.triggerEventToWebView script = " + script);
        mBrowser.execJs(script);
    }

    // B
    private void triggerCallbackToWebView(int messageId, Map<String, String> payload) {
        if (payload == null)
            payload = Collections.emptyMap();

        String script = String.format(Locale.getDefault(),
                "Jockey.triggerCallback(\"%d\", %s)",
                messageId, new JSONObject(payload));

        System.out.println("JsBridgeImpl.triggerCallbackToWebView script = " + script);
        mBrowser.execJs(script);
    }

    // 1
    void triggerEventFromWebView(Spec parameter) {
        if (!handle(parameter.type)) {
            return;
        }
        final int id = parameter.id;
        JsResultHandler handler = mListeners.get(parameter.type);
        handler.perform(parameter.payload, new JsCallback() {
            @Override
            public void onCall(Map<String, String> payload) {
                triggerCallbackToWebView(id, payload);
            }
        });

        System.out.println("JsBridgeImpl.triggerEventFromWebView " + parameter);
    }

    // 2
    void triggerCallbackFromWebView(Spec parameter) {
        JsCallback callback = mCallbacks.get(parameter.id);
        if (callback != null) {
            callback.onCall(parameter.payload);
            mCallbacks.remove(parameter.id);
        }

        System.out.println("JsBridgeImpl.triggerCallbackFromWebView " + parameter);
    }

}
