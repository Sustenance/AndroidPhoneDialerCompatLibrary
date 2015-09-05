# AndroidPhoneDialerCompatLibrary
A library to send properly formed dial intents to various Android phone apps.

Any Android phone app that can receive an intent to make a phone call must have declared that 
intent in its AndroidManifest.xml. Typically the intent is either:
android.intent.action.DIAL
android.intent.action.CALL

By analyzing the manifests, and sometimes trial and error, you can determine the exact format
for sending phone call data to the different apps.

To add additional phone app support to this library, all that is needed is to add an entry into
the enumeration in Phone.java with the proper intent data.
