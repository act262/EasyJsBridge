package io.micro.jsbridge;

import android.net.Uri;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

/**
 * WebViewClient implementation.
 *
 * @author act262@gmail.com
 */
class BrowserClientImpl implements BrowserClient {

    private JsBridgeImpl jsBridge;

    public BrowserClientImpl(JsBridgeImpl jsBridge) {
        this.jsBridge = jsBridge;
    }

    @Override
    public boolean shouldOverrideUrlLoading(String url) {
        Uri uri = Uri.parse(url);
        if (matchScheme(uri)) {
            process(uri);
            return true;
        }
        return false;
    }


    private boolean matchScheme(Uri uri) {
        return "jockey".equalsIgnoreCase(uri.getScheme());
    }

    private void process(Uri uri) {
        // jockey://event/0?{"id":0,"type":"getEnvironment","host":"h5.jfz.net","payload":{}}

        String scheme = uri.getScheme(); // jockey
        String host = uri.getHost(); // event ? callback
        String path = uri.getPath(); // 0 -> id
        String query = uri.getQuery(); // {"id":0,"host":"h5.jfz.net","type":"xxx","payload":{}}

        Spec parameter = parse(query);

        if ("event".equals(host)) {
            jsBridge.triggerEventFromWebView(parameter);
        } else if ("callback".equals(host)) {
            jsBridge.triggerCallbackFromWebView(parameter);
        }
    }

    private Spec parse(String query) {
        Spec parameter = new Spec();
        try {
            JSONObject jsonObject = new JSONObject(query);
            parameter.id = jsonObject.optInt("id");
            parameter.host = jsonObject.optString("host");
            parameter.type = jsonObject.optString("type");

            if (jsonObject.has("payload")) {
                JSONObject payload = jsonObject.optJSONObject("payload");
                parameter.payload = new HashMap<>(payload.length());
                Iterator<String> keys = payload.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    parameter.payload.put(key, payload.optString(key));
                }
            } else {
                parameter.payload = Collections.emptyMap();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return parameter;
    }
}
