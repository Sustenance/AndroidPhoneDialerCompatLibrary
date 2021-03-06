package com.sustenance.androidphonedialercompatlib;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.util.Log;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberType;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
        numApps = 0;
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

            try{
                ApplicationInfo appInfo = pm.getApplicationInfo(phone.packageName, 0);
                if(appInfo.loadLabel(pm).toString().equals(phoneName)){
                    return dial(phoneNumber, phone);
                }
            }catch (PackageManager.NameNotFoundException e){
                continue;
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


    /**
     *
     * @param phoneNumber The phone numbe to dial
     * @param phone The selected phone app to dial with
     * @return True if success
     */
    public boolean dial(String phoneNumber, Phone phone) {
        phoneNumber = trimPhoneNumberLength(phoneNumber, this.context);
        //String countryCode = GetCountryZipCode();
        if(phoneNumber.length() >= 7){
            try {
                Intent callIntent = new Intent(phone.intentAction, Uri.parse(phone.uriPrefix + phoneNumber + phone.uriPostFix));
                if(!phone.packageName.equals("") && !phone.activityName.equals("")) {
                    callIntent.setComponent(new ComponentName(phone.packageName, phone.activityName));
                }
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(callIntent);
            } catch (ActivityNotFoundException e) {
                Log.e("DIAL LIB", phone.packageName + " call failed", e);
                return false;
            }
            return true;
        }

        return false;
    }



    /**
     * Ensures that the supplied phone number is trimmed of dashes and
     * is only Ten digits long
     * @param phoneNumber A phone number
     * @return A phone number in a dialable format (XXXXXXXXXX)
     */
    public static String trimPhoneNumberLength(String phoneNumber, Context context) {
        phoneNumber = PhoneNumberUtils.stripSeparators(phoneNumber);
        String countryCode = GetCountryZipCode(context);
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        PhoneNumber phoneNumberObj;
        try {
            phoneNumberObj = phoneNumberUtil.parse(phoneNumber, countryCode);
        } catch (NumberParseException e) {
            Log.e("NumberParsing", "Could not parse number");
            return phoneNumber;
        }

        String dialNumber = phoneNumberUtil.format(phoneNumberObj, PhoneNumberUtil.PhoneNumberFormat.E164);
        if(dialNumber!=null && !dialNumber.equals("")){
            return dialNumber;
        }else {
            return phoneNumber;
        }

    }


    /**
     * Gets the two character country code from the system, then matches it to
     * a calling code from the strings resource file.
     * Modified from that found here: http://stackoverflow.com/a/17266260
     * @return The numerical country calling code
     */
    public static String GetCountryZipCode(Context context){
        String CountryZipCode="";
        String locale = context.getResources().getConfiguration().locale.getCountry();

        String[] rl= context.getResources().getStringArray(R.array.CountryCodes);
        for(int i=0;i<rl.length;i++){
            String[] g=rl[i].split(",");
            if(g[1].trim().equals(locale.trim())){
                CountryZipCode=g[0];
                break;
            }
        }
        return CountryZipCode;
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
        }
    }

}
