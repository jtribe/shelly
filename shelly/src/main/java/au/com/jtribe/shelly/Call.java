package au.com.jtribe.shelly;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;

/**
 * Represents a phone call to a number using Intent.ACTION_CALL
 */
public final class Call {

  private String phoneNumber;

  Call() {
  }

  /**
   * Adds a phone number to the Call object that will be called using the intent
   *
   * @param number Phone number that will be called
   * @return Call object this method was called on for method chaining
   */

  @CheckResult
  @NonNull
  @RequiresPermission(Manifest.permission.CALL_PHONE)
  public Call number(@NonNull String number) {
    if (number == null) {
      throw new IllegalArgumentException("number == null");
    }
    this.phoneNumber = number;
    return this;
  }

  /**
   * Creates an ACTION_CALL intent adding this object's fields into the intent
   *
   * @return The intent storing this objects data, can be used to open apps capable of placing
   * phone calls
   */
  @CheckResult
  @NonNull
  public Intent asIntent() {
    Intent phoneIntent;
    if (phoneNumber != null) {
      phoneIntent = new Intent(Intent.ACTION_CALL);
      phoneIntent.setData(Uri.parse("tel:" + phoneNumber));
    } else {
      throw new IllegalStateException("You must specify a number to call");
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      phoneIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
    } else {
      //noinspection deprecation
      phoneIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
    }

    return phoneIntent;
  }

  /**
   * Creates an ACTION_CALL chooser intent with this object's fields as data in the intent
   * The chooser has no title
   */
  @CheckResult
  @NonNull
  public Intent asChooserIntent() {
    return Intent.createChooser(asIntent(), null);
  }

  /**
   * Creates an ACTION_CALL chooser intent with this object's fields as data in the intent
   *
   * @param prompt The chooser's title is set by prompt
   */
  @CheckResult
  @NonNull
  public Intent asChooserIntent(@NonNull CharSequence prompt) {
    return Intent.createChooser(asIntent(), prompt);
  }
}
