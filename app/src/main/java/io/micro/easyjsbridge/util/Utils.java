package io.micro.easyjsbridge.util;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

public class Utils {

    public static void callPhone(Activity context, String url) {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context,
                    new String[]{Manifest.permission.CALL_PHONE}, 100);

            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        context.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(url)));
    }

    public static void toMarket(Activity activity, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        if (intent.resolveActivity(activity.getPackageManager()) == null) {
            Toast.makeText(activity, "没有安装App Store", Toast.LENGTH_SHORT).show();
            return;
        }
        activity.startActivity(intent);
    }
}
