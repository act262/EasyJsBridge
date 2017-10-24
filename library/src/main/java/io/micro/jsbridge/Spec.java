package io.micro.jsbridge;

import java.util.Collections;
import java.util.Map;

/**
 * Parameter
 *
 * @author act262@gmail.com
 */
class Spec {
    public int id;
    public String type;
    public String host;
    public Map<String, String> payload = Collections.emptyMap();

    @Override
    public String toString() {
        return "Spec{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", host='" + host + '\'' +
                ", payload=" + payload +
                '}';
    }
}
