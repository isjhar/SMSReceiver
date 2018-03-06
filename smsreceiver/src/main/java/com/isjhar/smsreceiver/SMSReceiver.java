package com.isjhar.smsreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.unity3d.player.UnityPlayer;

/**
 * Created by Crew on 3/6/2018.
 */

public class SMSReceiver extends BroadcastReceiver
{
    private static String TAG = "SMSReceiver";

    @Override
    public void onReceive(Context context, Intent intent)
    {
        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED"))
        {
            Bundle bundle = intent.getExtras();
            if(bundle != null)
            {
                // --- retrieve the sms message received ---
                try
                {
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    for (int i = 0; i < pdus.length; i++)
                    {
                        SmsMessage message = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        UnityPlayer.UnitySendMessage("Login Manager", "OnSMSReceived", String.format("{ \"from\" : \"%s\", \"message\" : \"%s\" }", message.getDisplayOriginatingAddress(), message.getDisplayMessageBody()));
                    }
                }
                catch (Exception e)
                {
                    UnityPlayer.UnitySendMessage("Login Manager", "OnSMSReceived", String.format("{ \"error\" : \"%s\" }", e.getMessage()));
                }
            }
        }
    }
}
