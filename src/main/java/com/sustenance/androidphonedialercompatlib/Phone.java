package com.sustenance.androidphonedialercompatlib;

/**
 * Created by Sustenance on 8/23/15.
 */
public enum Phone {
    DEFAULT("", ""),
    SKYPE("com.skype.raider", "com.skype.raider.Main"),
    TALKATONE("com.talkatone.android", "com.talkatone.android.ui.launcher.DialInterceptor"),
    VIBER("com.viber.voip", "com.viber.voip.WelcomeActivity"),
    GROOVE_IP("com.gvoip", "com.gvoip.ui.SwipeTabsActivity"),
    GROOVE_IP_LITE("com.snrblabs.grooveip", "com.gvoip.ui.SwipeTabsActivity"),
    TEXTPLUS("com.gogii.textplus", "com.gogii.textplus.view.VoiceIntentActivity"),
    VONAGE("com.vonage.TimeToCall", "com.vonage.TimeToCall.Activities.CallPrilegedHandlerActivity"),
    MAGICJACK("com.magicjack", "com.magicjack.ContactsIntegrationActivity");

    public String packageName;
    public String activityName;

    Phone(String packageName, String activityName) {
        this.packageName = packageName;
        this.activityName = activityName;
    }

}
