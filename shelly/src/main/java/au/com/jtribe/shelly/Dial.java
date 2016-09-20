package au.com.jtribe.shelly;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static au.com.jtribe.shelly.Preconditions.checkNotNull;

/**
 * Represents a phone dial to a number using Intent.ACTION_DIAL
 */
public final class Dial {

  private String phoneNumber;

  public Dial() {
  }

  /**
   * Adds a phone number to the Dial object that will be dialed using the intent
   *
   * @param number Phone number that will be dialed
   * @return Dial object this method was called on for method chaining.
   */
  @NonNull
  @CheckResult
  public Dial number(@NonNull String number) {
    checkNotNull(number, "number == null");
    this.phoneNumber = number;
    return this;
  }

  /**
   * Creates and returns an Intent that will dial the number provided.
   */
  @NonNull
  @CheckResult
  public Intent asIntent() {
    checkNotNull(phoneNumber, "phoneNumber == null");
    Intent phoneIntent = new Intent(Intent.ACTION_DIAL);
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
   * Creates a chooser intent that will dial the phone number provided, the chooser will have no
   * specified title.
   *
   * <b>Note: This may not be an appropriate user experience, since there is most likely a default
   * app configured by the user.</b>
   */
  @NonNull
  @CheckResult
  public Intent asChooserIntent() {
    return asChooserIntent(null);
  }

  /**
   * Creates and returns an Intent that will display a chooser to the user with the specified
   * title, allowing them to pick an app to dial with.
   *
   * <b>Note: This may not be an appropriate user experience, since there is most likely a default
   * app configured by the user.</b>
   *
   * @param prompt The chooser's title is set by prompt
   */
  @NonNull
  @CheckResult
  public Intent asChooserIntent(@Nullable CharSequence prompt) {
    return Intent.createChooser(asIntent(), prompt);
  }
}
