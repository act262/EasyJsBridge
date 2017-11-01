package io.micro.easyjsbridge;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import io.micro.easyjsbridge.web.SimpleWebActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);
    }

    public void clickSimple(View view) {
        startActivity(new Intent(this, SimpleWebActivity.class));
    }

    public void clickWebView(View view) {
        startActivity(new Intent(this, TestWebActivity.class));
    }
}
