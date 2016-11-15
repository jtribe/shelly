package au.com.jtribe.shelly;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

/**
 * Shelly is a factory that builds objects that are used to simplify intents.
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

  /**
   * Set a timer
   *
   * @return Timer that can be set to go off after a specific duration
   */
  @NonNull
  @CheckResult
  public static Timer timer() {
    return new Timer();
  }

  /**
   * Create a Calendar event
   *
   * @return Calendar that can have a start time, end time, attendees, location and title
   */
  @NonNull
  @CheckResult
  public static Calendar calendar() {
    return new Calendar();
  }

  /**
   * Create a Map intent
   *
   * @return Map intent at a specific address or latitude longitude
   */
  @NonNull
  @CheckResult
  public static Map map() {
    return new Map();
  }

}
