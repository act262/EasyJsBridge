package io.micro.easyjsbridge.app;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by act.zhang on 2017/11/1.
 *
 * @author act262@gmail.com
 */
public class BaseActivity extends AppCompatActivity {

    protected void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
