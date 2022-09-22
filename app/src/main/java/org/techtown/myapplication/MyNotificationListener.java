package org.techtown.myapplication;

import android.app.Notification;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

public class MyNotificationListener extends NotificationListenerService {
    public final static String TAG = "MyNotificationListener";


    // 상단에 표시되어 있는 알림을 지울때 작동이 됩니다.
    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
        Log.d(TAG, "onNotificationRemoved 작동됨");
        Toast.makeText(this, "onNotificationRemoved call", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        final String packageName = sbn.getPackageName();
        Log.d("onNotificationPosted", "onNotificationPosted가 작동됨");
        Toast.makeText(this, "onNotificationPosted call("+packageName+")", Toast.LENGTH_SHORT).show();

    }
}
