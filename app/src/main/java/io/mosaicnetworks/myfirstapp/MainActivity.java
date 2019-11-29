package io.mosaicnetworks.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import io.mosaicnetworks.babble.node.KeyPair;

import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "FIRST-BABBLE-APP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        KeyPair kp = new KeyPair();
        Log.i("Yippee",kp.privateKey);
    }

    // called when the user presses the new chat button
    public void newChat(View view) {
        Log.i("Ki","newChat Called");
        Intent intent = new Intent(this, NewChatActivity.class);
        startActivity(intent);
    }


    // called when the user presses the join chat button
    public void joinChat(View view) {
        Intent intent = new Intent(this, JoinChatActivity.class);
        startActivity(intent);
    }
}
