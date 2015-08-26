package com.sustenance.androidphonedialercompatlib;

import android.content.Intent;

/**
 * Created by Sustenance on 8/23/15.
 */
public enum Phone {
    DEFAULT("", "", Intent.ACTION_CALL, "tel:", ""),
    //BIGO("sg.bigo", "com.yy.iheima.DialWithWeihuiActivity",Intent.ACTION_DIAL , "tel:", ""), gets the intent, does nothing with the data
    DINGTONE("me.dingtone.app.im","me.dingtone.app.im.SplashActivity", Intent.ACTION_CALL, "tel:", ""),
    //FREEPP("com.browan.freeppmobile.android", "com.browan.freeppmobile.android.ui.ReceiveThirdInfoActivity", Intent.ACTION_VIEW , "freepp:", ""),
    FREETONE("com.textmeinc.freetone", "com.textmeinc.textme.activity.InCallActivity",Intent.ACTION_CALL , "tel:", ""),
    FRING("com.fring", "com.onefone.ui.SplashActivity", "android.intent.action.CALL_PRIVILEGED" , "tel:", ""),
    GROOVE_IP("com.gvoip", "com.gvoip.ui.SwipeTabsActivity", Intent.ACTION_DIAL,"tel:", ""),
    GROOVE_IP_LITE("com.snrblabs.grooveip", "com.gvoip.ui.SwipeTabsActivity", Intent.ACTION_DIAL,"tel:+", ""),
    HANGOUTS_DIAL("com.google.android.apps.hangoutsdialer", "com.google.android.apps.hangoutsdialer.app.LaunchActivity", Intent.ACTION_DIAL, "tel:", ""),
    //LINE("jp.naver.line.android", "", Intent.ACTION_CALL, "tel:+1"),
    MAGICJACK("com.magicjack", "com.magicjack.ContactsIntegrationActivity", Intent.ACTION_CALL,"tel:", ""),
    //MO_PLUS("com.moplus.moplusapp", "com.moplus.moplusapp.ui.PreparedCallActivity", "android.intent.action.CALL_PRIVILEGED", "tel:", ""),
    NEXTPLUS("me.nextplus.smsfreetext.phonecalls", "com.nextplus.android.activity.DialerActivity", Intent.ACTION_DIAL, "tel:", ""),
    SKYPE("com.skype.raider", "com.skype.raider.Main", Intent.ACTION_CALL, "skype:", ""),
    TALKATONE("com.talkatone.android", "com.talkatone.android.ui.launcher.OutgoingCallInterceptor" ,Intent.ACTION_CALL,"tel:", ""),
    TALKU("me.talkyou.app.im", "me.talkyou.app.im.TalkuSplashActivity",Intent.ACTION_CALL , "tel:", ""),
    TELEME("com.wagtailapp", "com.wagtailapp.ui.outgoingcall.OutgoingCallChooser", Intent.ACTION_CALL, "sip:", ""),
    TEXTFREE_PINGER("com.pinger.ppa", "com.pinger.ppa.activities.TFSplash",Intent.ACTION_CALL , "tel:", ""),
    TEXTFREE_VOICE("com.pinger.textfree.call","com.pinger.textfree.call.activities.TFSplash",Intent.ACTION_CALL,"tel:", ""),
    TEXTNOW("com.enflick.android.TextNow", "com.enflick.android.TextNow.activities.MainActivity",Intent.ACTION_CALL , "tel:", ""),
    TEXTPLUS("com.gogii.textplus", "com.gogii.textplus.view.VoiceIntentActivity", Intent.ACTION_CALL,"tel:", ""),
    VIBER("com.viber.voip", "com.viber.voip.WelcomeActivity", Intent.ACTION_DIAL,"tel:", ""),
    VONAGE("com.vonage.TimeToCall", "com.vonage.TimeToCall.Activities.CallPrilegedHandlerActivity", "android.intent.action.CALL_PRIVILEGED","tel:", ""),
    VOXOFON("com.voxofon", "com.csipsimple.ui.SipHome", Intent.ACTION_DIAL, "tel:", ""),
    WEPHONE("com.wephoneapp", "com.wephoneapp.ui.outgoingcall.OutgoingCallChooser", Intent.ACTION_CALL , "sip:", "");
    //YOUROAM("com.onePhone", "com.onePhone.ui.outgoingcall.OutgoingCallChooser", Intent.ACTION_CALL, "tel:", ""); //app accepts phone number, but then promptly erases it

    public String packageName;
    public String activityName;
    public String intentAction;
    public String uriPrefix;
    public String uriPostFix;

    Phone(String packageName, String activityName, String intentAction, String uriPrefix, String uriPostFix) {
        this.packageName = packageName;
        this.activityName = activityName;
        this.intentAction = intentAction;
        this.uriPrefix = uriPrefix;
        this.uriPostFix = uriPostFix;
    }

}
