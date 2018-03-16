package io.micro.jsbridge.impl;

import android.net.Uri;
import android.support.v4.util.ArrayMap;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

/**
 * ParameterSpec
 *
 * @author act262@gmail.com
 */
class Spec {
    public String url;
    public String event;

    public int id;
    public String type;
    public String host;
    public Map<String, Object> payload = Collections.emptyMap();

    @Override
    public String toString() {
        return url;
    }

    public static Spec parse(Uri uri) {
        // jockey://event/0?{"id":0,"type":"getEnvironment","host":"h5.jfz.net","payload":{}}

        String scheme = uri.getScheme(); // jockey
        String host = uri.getHost(); // event ? callback
        String path = uri.getPath(); // 0 -> id
        String query = uri.getQuery(); // {"id":0,"host":"h5.jfz.net","type":"xxx","payload":{}}

        Spec parameter = new Spec();
        try {
            JSONObject jsonObject = new JSONObject(query);
            parameter.url = Uri.decode(uri.toString());
            parameter.event = host;

            parameter.id = jsonObject.optInt("id");
            parameter.host = jsonObject.optString("host");
            parameter.type = jsonObject.optString("type");

            if (jsonObject.has("payload")) {
                JSONObject payload = jsonObject.optJSONObject("payload");
                parameter.payload = new ArrayMap<>(payload.length());
                Iterator<String> keys = payload.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    parameter.payload.put(key, payload.opt(key));
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
