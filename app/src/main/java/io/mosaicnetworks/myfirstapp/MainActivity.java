package io.mosaicnetworks.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import io.mosaicnetworks.babble.node.KeyPair;

import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        KeyPair kp = new KeyPair();
        Log.i("Yippee",kp.privateKey);
    }
}
