package au.com.jtribe.shelly;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresPermission;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.jar.Manifest;

/**
 * Represents an email to a person or group of people.
 */
public final class Phone {
    private final Context context;
    private String toDial;
    private String toCall;

    Phone(Context context) {
        this.context = context;
    }

    /**
     * Dials the phone number, but does not call it automatically.
     *
     * @param phoneNumber Phone number to dial using the phone app.
     * @return Object this method was called on for method chaining.
     */
    public Phone dial(String phoneNumber) {
        if (phoneNumber == null) throw new IllegalArgumentException("phoneNumber == null");
        toDial = phoneNumber;
        return this;
    }

    /**
     * Calls the phone number automatically.
     *
     * WARNING: This requires the permission {@link android.Manifest.permission#CALL_PHONE} permission to be declared in your Manifest.
     *
     * @param phoneNumber Phone number to call using the phone app.
     * @return Object this method was called on for method chaining.
     */
    @RequiresPermission(android.Manifest.permission.CALL_PHONE)
    public Phone call(String phoneNumber) {
        if (phoneNumber == null) throw new IllegalArgumentException("phoneNumber == null");
        toCall = phoneNumber;
        return this;
    }

    /**
     * Starts an activity to send an email with the configured details.
     *
     * @return Boolean true if there is an activity to handle the Intent and it was started, false otherwise.
     */
    public boolean send() {
        Intent phoneIntent;
        if (toDial != null) {
            phoneIntent = new Intent(Intent.ACTION_DIAL);
            phoneIntent.setData(Uri.parse("tel:" + toDial));
        } else if (toCall != null) {
            phoneIntent = new Intent(Intent.ACTION_CALL);
            phoneIntent.setData(Uri.parse("tel:" + toDial));
        } else {
            throw new IllegalStateException("You must specify a number to dial");
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            phoneIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        } else {
            //noinspection deprecation
            phoneIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        }

        if (phoneIntent.resolveActivity(this.context.getPackageManager()) != null) {
            this.context.startActivity(phoneIntent);
            return true;
        }

        return false;
    }
}
