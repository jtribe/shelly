package au.com.jtribe.shelly;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;

import static au.com.jtribe.shelly.Preconditions.checkNotNull;

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
  @NonNull
  @CheckResult
  @RequiresPermission(Manifest.permission.CALL_PHONE)
  public Call number(@NonNull String number) {
    checkNotNull(number, "number == null");
    this.phoneNumber = number;
    return this;
  }

  /**
   * Creates and returns an Intent that will call the number provided.
   */
  @NonNull
  @CheckResult
  public Intent asIntent() {
    checkNotNull(phoneNumber, "phoneNumber == null");

    Intent phoneIntent = new Intent(Intent.ACTION_CALL);
    phoneIntent.setData(Uri.parse("tel:" + phoneNumber));

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      phoneIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
    } else {
      //noinspection deprecation
      phoneIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
    }

    return phoneIntent;
  }

  /**
   * Creates a chooser intent that will call the phone number provided, the chooser will have no
   * specified title.
   *
   * <b>Note: This may not be an appropriate user experience, since there is most likely a default app
   * configured by the user.</b>
   */
  @NonNull
  @CheckResult
  public Intent asChooserIntent() {
    return asChooserIntent(null);
  }

  /**
   * Creates an ACTION_CALL chooser intent with this object's fields as data in the intent
   *
   * <b>Note: This may not be an appropriate user experience, since there is most likely a default app
   * configured by the user.</b>
   *
   * @param prompt The chooser's title is set by prompt
   */
  @NonNull
  @CheckResult
  public Intent asChooserIntent(@Nullable CharSequence prompt) {
    return Intent.createChooser(asIntent(), prompt);
  }
}
