package io.micro.easyjsbridge.web;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import io.micro.easyjsbridge.util.Utils;

/**
 * Web tools
 *
 * @author act262@gmail.com
 */
public class WebHelper {
    /**
     * 预处理一些特定的协议
     */
    public static boolean processUrl(Activity activity, String url) {
        if (url.startsWith("tel:")) {
            Utils.callPhone(activity, url);
            return true;
        } else if (url.endsWith(".apk")) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            return true;
        } else if (url.startsWith("market://")) {
            Utils.toMarket(activity, url);
            return true;
        }
        return false;
    }

}
