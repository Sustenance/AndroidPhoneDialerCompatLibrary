package com.sustenance.androidphonedialercompatlib;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sustenance on 8/23/15.
 */
public class PhoneDialer {
    private Context context;
    private int numApps;
    private ArrayList<String> installedPhones;
    private Phone selectedPhone = Phone.DEFAULT;

    public PhoneDialer(Context context, Phone selectedPhone){
        this(context);
        this.selectedPhone = selectedPhone;
    }

    public PhoneDialer(Context context){
        this.context = context;
        this.numApps = 0;
        scanApps();
    }

    public boolean dial(String phoneNumber, Phone phone) {
        if (phoneNumber.length() == 10){
            switch (phone) {
                case GROOVE_IP:
                    groove(phoneNumber);
                    break;
                case GROOVE_IP_LITE:
                    grooveLite(phoneNumber);
                    break;
                case MAGICJACK:
                    magicJack(phoneNumber);
                    break;
                case SKYPE:
                    skype(phoneNumber);
                    break;
                case TALKATONE:
                    talkatone(phoneNumber);
                    break;
                case TEXTPLUS:
                    textPlus(phoneNumber);
                    break;
                case VIBER:
                    viber(phoneNumber);
                    break;
                case VONAGE:
                    vonage(phoneNumber);
                    break;
                case DEFAULT:
                    defaultDialer(phoneNumber);
                    break;
                default:
                    Log.d("PHONEDIALLIB", "Supplied Unsupported Phone: " + phone.packageName);
                    break;
            }
        }
        return false;
    }

    public String[] getInstalledPhones(){
        return (String[])installedPhones.toArray();
    }

    /**
     * Gets a list of the installed packages.
     * If the list size does not match the previous size, checks
     * installed packages against the supported Phone apps
     */
    private void scanApps() {
        final PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        if(this.numApps != packages.size()){
            this.numApps = packages.size();
            ArrayList<String> supportedPackages = new ArrayList<String>();
            for(int i = 0; i < Phone.values().length ; i++){
                supportedPackages.add(Phone.values()[i].packageName);
            }
            for(ApplicationInfo packageInfo : packages) {
                //check that the installed package is not "Disabled" in Android settings
                if(packageInfo.enabled){
                    String packageName = packageInfo.packageName;
                    if (supportedPackages.contains(packageName)){
                        installedPhones.add(packageName);
                    }

                }
            }
            java.util.Collections.sort(installedPhones);

        }else{
            //the number of installed applications has not changed since last scan
        }
    }

    public boolean defaultDialer(String number) {
        try {
            Intent intentGo;
            if (number.length() == 10) {
                intentGo = new Intent(Intent.ACTION_CALL);
            } else {
                intentGo = new Intent(Intent.ACTION_VIEW);
            }
            intentGo.setData(Uri.parse("tel:1" + number));
            intentGo.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intentGo);
            return true;
        } catch (ActivityNotFoundException e) {
            Log.e("DEFAULT CALL", "Default Dialer call failed!", e);
            return false;
        }

    }

    public boolean skype(String number) {
        try {
            Intent callSkype = new Intent(Intent.ACTION_CALL, Uri.parse("skype:+1" + number));
            callSkype.setComponent(new ComponentName("com.skype.raider", "com.skype.raider.Main"));
            callSkype.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(callSkype);
        } catch (ActivityNotFoundException e) {
            Log.e("SKYPE CALL", "Skype Call Failed", e);
            return false;
        }
        return true;
    }

    public boolean textPlus(String number) {
        try {
            Intent callTextPlus = new Intent(Intent.ACTION_CALL, Uri.parse("tel:+1" + number));
            callTextPlus.setComponent(new ComponentName("com.gogii.textplus", "com.gogii.textplus.view.VoiceIntentActivity"));
            callTextPlus.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(callTextPlus);
        } catch (ActivityNotFoundException e) {
            Log.e("TEXTPLUS", "textPlus call failed", e);
            return false;
        }
        return true;
    }

    public boolean talkatone(String number) {
        try {
            Intent callTalkatone = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+1" + number));
            callTalkatone.setComponent(new ComponentName("com.talkatone.android", "com.talkatone.android.ui.launcher.DialInterceptor"));
            callTalkatone.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(callTalkatone);
        } catch (ActivityNotFoundException e) {
            Log.e("TKTN CALL", "TKTN call Failed", e);
            return false;
        }
        return true;
    }

    //Viber doesn't allow starting calls from external applications
    //opens Viber app with number prepopulated, ready for calling
    public boolean viber(String number) {
        try {
            Intent callViber = new Intent(Intent.ACTION_VIEW);
            callViber.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.parse("tel:" + Uri.encode(number));
            callViber.setClassName("com.viber.voip", "com.viber.voip.WelcomeActivity");
            callViber.setData(uri);
            context.startActivity(callViber);
        } catch (ActivityNotFoundException e) {
            Log.e("VIBER CALL", "Viber call Failed", e);
            return false;
        }
        return true;
    }

    public boolean groove(String number) {
        try {
            Intent callGroove = new Intent("android.intent.action.DIAL");
            callGroove.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            callGroove.setData(Uri.parse("tel:+1" + number));
            callGroove.setClassName("com.gvoip", "com.gvoip.ui.SwipeTabsActivity");
            context.startActivity(callGroove);
        } catch (ActivityNotFoundException e) {
            Log.e("GROOVE CALL", "Groove call failed", e);
            return false;
        }
        return true;
    }

    public boolean grooveLite(String number) {
        try {
            Intent callGroove = new Intent("android.intent.action.DIAL");
            callGroove.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            callGroove.setData(Uri.parse("tel:+1" + number));
            callGroove.setClassName("com.snrblabs.grooveip", "com.gvoip.ui.SwipeTabsActivity");
            context.startActivity(callGroove);
        } catch (ActivityNotFoundException e) {
            Log.e("GROOVE Lite CALL", "Groove lite call failed", e);
            return false;
        }
        return true;
    }

    public boolean vonage(String number) {
        try {
            Intent callVonage = new Intent("android.intent.action.CALL_PRIVILEGED");
            callVonage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            callVonage.setData(Uri.parse("tel:+1" + number));
            callVonage.setComponent(new ComponentName("com.vonage.TimeToCall", "com.vonage.TimeToCall.Activities.CallPrilegedHandlerActivity"));
            context.startActivity(callVonage);
        } catch (ActivityNotFoundException e) {
            Log.e("VONAGE CALL", "Vonage call failed", e);
            return false;
        }
        return true;
    }

    public boolean magicJack(String number) {
        try {
            Intent callMagic = new Intent("android.intent.action.CALL_PRIVILEGED", Uri.parse("tel:+1" + number));
            callMagic.setComponent(new ComponentName("com.magicjack", "com.magicjack.ContactsIntegrationActivity"));
            callMagic.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(callMagic);
        } catch (ActivityNotFoundException e) {
            Log.e("MAGIC CALL", "magicJack call failed", e);
            return false;
        }
        return true;
    }

}
