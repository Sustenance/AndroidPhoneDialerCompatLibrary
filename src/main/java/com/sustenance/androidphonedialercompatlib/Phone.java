package com.sustenance.androidphonedialercompatlib;

import android.content.Intent;

/**
 * Created by Sustenance on 8/23/15.
 */
public enum Phone {
    DEFAULT("", "", Intent.ACTION_CALL, "tel:1"),
    DINGTONE("me.dingtone.app.im","me.dingtone.app.im.SplashActivity", Intent.ACTION_CALL, "tel:"),
    FREETONE("com.textmeinc.freetone", "com.textmeinc.textme.activity.InCallActivity",Intent.ACTION_CALL , "tel:"),
    GROOVE_IP("com.gvoip", "com.gvoip.ui.SwipeTabsActivity", Intent.ACTION_DIAL,"tel:+1"),
    GROOVE_IP_LITE("com.snrblabs.grooveip", "com.gvoip.ui.SwipeTabsActivity", Intent.ACTION_DIAL,"tel:+1"),
    HANGOUTS_DIAL("com.google.android.apps.hangoutsdialer", "com.google.android.apps.hangoutsdialer.app.LaunchActivity", Intent.ACTION_DIAL, "tel:+1"),
    //LINE("jp.naver.line.android", "", Intent.ACTION_CALL, "tel:+1"),
    MAGICJACK("com.magicjack", "com.magicjack.ContactsIntegrationActivity", Intent.ACTION_CALL,"tel:+1"),
    NEXTPLUS("me.nextplus.smsfreetext.phonecalls", "com.nextplus.android.activity.DialerActivity", Intent.ACTION_DIAL, "tel:+1"),
    SKYPE("com.skype.raider", "com.skype.raider.Main", Intent.ACTION_CALL, "skype:+1"),
    TALKATONE("com.talkatone.android", "com.talkatone.android.ui.launcher.OutgoingCallInterceptor" ,Intent.ACTION_CALL,"tel:1"),
    TEXTFREE_VOICE("com.pinger.textfree.call","com.pinger.textfree.call.activities.TFSplash",Intent.ACTION_CALL,"tel:"),
    TEXTPLUS("com.gogii.textplus", "com.gogii.textplus.view.VoiceIntentActivity", Intent.ACTION_CALL,"tel:+1"),
    VIBER("com.viber.voip", "com.viber.voip.WelcomeActivity", Intent.ACTION_DIAL,"tel:"),
    VONAGE("com.vonage.TimeToCall", "com.vonage.TimeToCall.Activities.CallPrilegedHandlerActivity", "android.intent.action.CALL_PRIVILEGED","tel:+1"),
    VOXOFON("com.voxofon", "com.csipsimple.ui.SipHome", Intent.ACTION_DIAL, "tel:");

    public String packageName;
    public String activityName;
    public String intentAction;
    public String uriPrefix;

    Phone(String packageName, String activityName, String intentAction, String uriPrefix) {
        this.packageName = packageName;
        this.activityName = activityName;
        this.intentAction = intentAction;
        this.uriPrefix = uriPrefix;
    }

}
