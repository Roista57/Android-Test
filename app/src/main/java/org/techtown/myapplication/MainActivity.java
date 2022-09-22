package org.techtown.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends AppCompatActivity {
    TextView senderTextView;
    TextView contentTextView;
    TextView dateTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        senderTextView = findViewById(R.id.senderTextView);
        contentTextView = findViewById(R.id.contentTextView);
        dateTextView = findViewById(R.id.dateTextView);
        requirePerms();
        startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
        Intent receiverIntent = getIntent(); // 인텐트를 받는다.
        broadcastReceiver(receiverIntent); // 브로드캐스트 리시버에서 보낸 인텐트를 받아 TextView에 보여줌
    }
    // 새로운 인텐트가 오는 경우
    @Override
    protected void onNewIntent(Intent intent){
        broadcastReceiver(intent);
        super.onNewIntent(intent);
    }

    // 브로드캐스트 리시버에서 보낸 인텐트를 받아 TextView에 보여줌
    private void broadcastReceiver(Intent intent){
        senderTextView.setText(intent.getStringExtra("sender"));
        contentTextView.setText(intent.getStringExtra("content"));
        dateTextView.setText(intent.getStringExtra("date"));
    }

    private void requirePerms(){
        String[] permissions = {Manifest.permission.RECEIVE_SMS};
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
        if(permissionCheck == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, permissions, 1);
        }
    }
}