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
    private static int numApps;
    private ArrayList<String> installedPhones;
    private ArrayList<String> installedPhonesByName;
    private Phone selectedPhone = Phone.DEFAULT;
    private static List<ApplicationInfo> packages;

    public PhoneDialer(Context context, Phone selectedPhone){
        this(context);
        this.selectedPhone = selectedPhone;
    }

    public PhoneDialer(Context context){
        this.context = context;
        this.numApps = 0;
        this.installedPhones = new ArrayList<String>();
        this.installedPhonesByName = new ArrayList<String>();
        scanApps();
    }

    /**
     * Helper method which converts the given common phoneName
     * to the correct Phone enum value, then calls dial
     * @param phoneNumber The phone number to dial
     * @param phoneName The phone app to use for dialing by Name
     * @return True if success
     */
    public boolean dialByName(String phoneNumber, String phoneName) {
        if (phoneName.equals("Default") || phoneName.equals("")){
            return dial(phoneNumber, Phone.DEFAULT);
        }
        final PackageManager pm = context.getPackageManager();
        for(Phone phone : Phone.values()) {
            for(ApplicationInfo packageInfo : packages) {
                if(packageInfo.loadLabel(pm).toString().equals(phoneName) &&
                        packageInfo.packageName.equals(phone.packageName)) {
                    return dial(phoneNumber, phone);
                }
            }
        }
        return false;
    }

    /**
     * Helper method which converts the given app package name
     * to the correct Phone enum value, then calls dial
     * @param phoneNumber The phone number to dial
     * @param phonePackageName The phone app to use by package name
     * @return True if success
     */
    public boolean dialByPackage(String phoneNumber, String phonePackageName) {
        for(Phone phone : Phone.values()) {
            if(phonePackageName.equals(phone.packageName)) {
                return dial(phoneNumber, phone);
            }
        }
        return false;
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

    /**
     * Use sparingly; scans through all installed apps on each call
     * @return  An ArrayList containing the installed phone apps by package name
     */
    public ArrayList<String> getInstalledPhones(){
        scanApps();
        return this.installedPhones;
    }

    /**
     * Use sparingly; scans through all installed apps on each call
     * @return An ArrayList containing the installed phone apps by common name
     */
    public ArrayList<String> getInstalledPhonesByName() {
        scanApps();
        return this.installedPhonesByName;
    }

    /**
     * Gets a list of the installed packages.
     * If the list size does not match the previous size, checks
     * installed packages against the supported Phone apps
     */
    private void scanApps() {
        final PackageManager pm = context.getPackageManager();
        packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        if(numApps != packages.size()){
            numApps = packages.size();
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
                        installedPhonesByName.add(packageInfo.loadLabel(pm).toString());
                    }

                }
            }
            installedPhones.add("Default");
            installedPhonesByName.add("Default");
            java.util.Collections.sort(installedPhones);
            //Log.d("INSTALLED PHONES", installedPhones.toString());
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
            Intent callTalkatone = new Intent(Intent.ACTION_CALL, Uri.parse("tel:+1" + number));
            callTalkatone.setComponent(new ComponentName("com.talkatone.android", "com.talkatone.android.ui.launcher.OutgoingCallInterceptor"));
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
            Intent callMagic = new Intent("android.intent.action.CALL", Uri.parse("tel:+1" + number));
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
