package au.com.jtribe.shelly;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

/**
 * Shelly is a factory that creates instances of SocialShare's
 */
public final class Shelly {

  private Shelly() {
    // No instances
  }

  /**
   * Begin a social share.
   *
   * @return SocialShare that can be used to perform a social share.
   */
  @NonNull
  @CheckResult
  public static SocialShare share() {
    return new SocialShare();
  }

  /**
   * Begins composing an email template for the user.
   *
   * @return Email that can be configured and displayed to the user.
   */
  @NonNull
  @CheckResult
  public static Email email() {
    return new Email();
  }

  /**
   * Begin a phone dial
   *
   * @return Dial that can be used to automatically dial a phone number
   */
  @NonNull
  @CheckResult
  public static Dial dial() {
    return new Dial();
  }

  /**
   * Begin a phone call
   *
   * @return Call that can be used to automatically call a phone number
   */
  @NonNull
  @CheckResult
  public static Call call() {
    return new Call();
  }

  /**
   * Set an alarm
   *
   * @return Alarm that can be set to go off at a specific time
   */
  @NonNull
  @CheckResult
  public static Alarm alarm() {
    return new Alarm();
  }
}
