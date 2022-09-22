package org.techtown.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
/*
    어플리케이션을 종료하기 전까지는 백그라운드로 작동이 되나 메인 UI가 종료되면 백그라운드의 작업도 같이 종료된다.
    Service나 JobService 와 같은 서비스를 사용해 작업이 되는지 확인해봐야함
 */
public class MyReceiver extends BroadcastReceiver {

    private static final String TAG = "SMSReceiver"; // 로그에 사용하는 태그
    public SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // date 변수의 형식

    @Override
    public void onReceive(Context context, Intent intent) {
        // onReceive가 작동이 되었을 때 message의 길이가 0보다 크면 받은 메시지를 로그로 보여준다.
        Log.d(TAG, "onReceive() 작동"); // onReceive 작동확인 로그
        Bundle bundle = intent.getExtras(); // 수신 받은 인텐드
        SmsMessage[] messages = parseSmsMessage(bundle); // 인텐드를 메시지 형식으로 변환

        if(messages.length > 0){
            String sender = messages[0].getOriginatingAddress();
            String content = messages[0].getMessageBody();
            Date date = new Date(messages[0].getTimestampMillis());

            Log.d(TAG, "sender : " + sender);
            Log.d(TAG, "SMS Message: " + content);
            Log.d(TAG, "date : " + date);
            sendToActivity(context, sender,content, date); // MainActivity로 인텐트를 보내는 함수
        }
    }
    // MainActivity로 인텐트를 보내는 함수
    private void sendToActivity(Context context, String sender, String content, Date date){
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // intent에 담을 데이터의 키 값과 데이터
        intent.putExtra("sender", sender);
        intent.putExtra("content",content);
        intent.putExtra("date",format.format(date));
        context.startActivity(intent); // Intent에 데이터를 담은 뒤 Activity에 보낸다.
        Toast.makeText(context, sender+"\n"+content+"\n"+date, Toast.LENGTH_SHORT).show();
    }

    // Intent에 저장된 메시지의 형식을 바꿔주는 함수
    private SmsMessage[] parseSmsMessage(Bundle bundle){
        // PDU: Protocol Data Units
        Object[] objs = (Object[]) bundle.get("pdus"); // intent의 pdus부분에서 메시지를 가져온다.
        SmsMessage[] messages = new SmsMessage[objs.length];

        for(int i=0; i<objs.length; i++){
            messages[i] = SmsMessage.createFromPdu((byte[])objs[i]);
        }
        return messages;
    }
}