package io.mosaicnetworks.myfirstapp;

import android.content.Intent;
import android.os.Bundle;


import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

import io.mosaicnetworks.babble.discovery.Peer;

public class NewChatActivity extends AppCompatActivity {

    private MessagingService mMessagingService = MessagingService.getInstance();
    private String mMoniker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_chat);
    }


    // called when the user presses the start chat button
    public void startChat(View view) {
        //get moniker
        EditText editText = findViewById(R.id.editText);
        mMoniker = editText.getText().toString();
        if (mMoniker.isEmpty()) {
            displayOkAlertDialog(R.string.no_moniker_alert_title, R.string.no_moniker_alert_message);
            return;
        }

        joinChat();
    }


    private void joinChat() {
        mMessagingService.configure(new ArrayList<Peer>(), mMoniker, Utils.getIPAddr(this));
        mMessagingService.start();
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("MONIKER", mMoniker);
        startActivity(intent);
    }


    private void displayOkAlertDialog(@StringRes int titleId, @StringRes int messageId) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(titleId)
                .setMessage(messageId)
                .setNeutralButton(R.string.ok_button, null)
                .create();
        alertDialog.show();
    }


}
