package io.micro.easyjsbridge.app;

import android.app.Application;

import com.tencent.smtt.sdk.QbSdk;

/**
 * Created by act.zhang on 2017/9/13.
 *
 * @author act262@gmail.com
 */
public class DemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Use x5 webview
        QbSdk.initX5Environment(this, new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                System.out.println("DemoApplication.onCoreInitFinished");
            }

            @Override
            public void onViewInitFinished(boolean b) {
                System.out.println("DemoApplication.onViewInitFinished");
            }
        });

    }
}
