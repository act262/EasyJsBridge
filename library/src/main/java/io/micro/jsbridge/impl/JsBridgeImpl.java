package io.micro.jsbridge.impl;

import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.util.SparseArray;

import org.json.JSONObject;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import io.micro.jsbridge.Browser;
import io.micro.jsbridge.JsBridge;
import io.micro.jsbridge.JsBridgeConfig;
import io.micro.jsbridge.JsCallback;
import io.micro.jsbridge.JsResultHandler;

/**
 * Js bridge implementation.
 *
 * @author act262@gmail.com
 */
public class JsBridgeImpl implements JsBridge {

    private Browser mBrowser;
    private Map<String, JsResultHandler> mListeners = new ArrayMap<>(10);
    private SparseArray<JsCallback> mCallbacks = new SparseArray<>(10);

    public JsBridgeImpl(Browser browser) {
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
    public void send(String type, JsCallback callback) {
        send(type, null, callback);
    }

    @Override
    public <T> void send(String type, Map<String, T> payload) {
        send(type, payload, null);
    }

    @Override
    public <T> void send(String type, Map<String, T> payload, JsCallback callback) {
        int messageId = messageCount;
        if (callback != null) {
            mCallbacks.put(messageId, callback);
        }
        triggerEventToWebView(type, messageId, payload);

        messageCount++;
    }

    private int messageCount = 0;

    // A
    private <T> void triggerEventToWebView(String type, int messageId, Map<String, T> payload) {
        if (payload == null)
            payload = Collections.emptyMap();

        String script = String.format(Locale.getDefault(),
                "Jockey.trigger(\"%s\",%d,%s)",
                type, messageId, new JSONObject(payload));

        if (JsBridgeConfig.DEBUG) {
            Log.d(JsBridgeConfig.TAG, "triggerEventToWebView: " + script);
        }

        mBrowser.execJs(script);
    }

    // B
    private <T> void triggerCallbackToWebView(int messageId, Map<String, T> payload) {
        if (payload == null)
            payload = Collections.emptyMap();

        String script = String.format(Locale.getDefault(),
                "Jockey.triggerCallback(\"%d\", %s)",
                messageId, new JSONObject(payload));

        if (JsBridgeConfig.DEBUG) {
            Log.d(JsBridgeConfig.TAG, "triggerCallbackToWebView: " + script);
        }

        mBrowser.execJs(script);
    }

    // 1
    void triggerEventFromWebView(Spec parameter) {
        if (JsBridgeConfig.DEBUG) {
            Log.d(JsBridgeConfig.TAG, "triggerEventFromWebView: " + parameter);
        }

        if (!handle(parameter.type)) {
            return;
        }

        final int id = parameter.id;
        JsResultHandler handler = mListeners.get(parameter.type);
        handler.perform(parameter.payload, new JsCallback() {
            @Override
            public <T> void onCall(Map<String, T> payload) {
                triggerCallbackToWebView(id, payload);
            }
        });
    }

    // 2
    void triggerCallbackFromWebView(Spec parameter) {
        if (JsBridgeConfig.DEBUG) {
            Log.d(JsBridgeConfig.TAG, "triggerCallbackFromWebView: " + parameter);
        }

        JsCallback callback = mCallbacks.get(parameter.id);
        if (callback != null) {
            callback.onCall(parameter.payload);
            mCallbacks.remove(parameter.id);
        }
    }

}
