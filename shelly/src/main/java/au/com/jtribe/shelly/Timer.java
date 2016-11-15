package au.com.jtribe.shelly;

import android.content.Intent;
import android.os.Build;
import android.provider.AlarmClock;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static au.com.jtribe.shelly.Preconditions.checkNotNull;

/**
 * Represents a Timer that will go off after the set duration
 */
public final class Timer {

  private Integer hours;
  private Integer minutes;
  private Integer seconds;
  private String message;

  private Boolean skipUi;

  Timer() {
  }

  /**
   * Adds the specified amount of hours to the duration of the Timer
   *
   * @param hours amount of hours the Timer should go for
   * @return Object this method was called on for method chaining.
   */
  @NonNull
  @CheckResult
  public Timer hours(@NonNull Integer hours) {
    checkNotNull(hours, "hour == null");
    this.hours = hours;
    return this;
  }

  /**
   * Adds the specified amount of minutes to the duration of the Timer
   *
   * @param minutes amount of minutes the Timer should go for
   * @return Object this method was called on for method chaining.
   */
  @NonNull
  @CheckResult
  public Timer minutes(@NonNull Integer minutes) {
    checkNotNull(minutes, "minutes == null");
    this.minutes = minutes;
    return this;
  }

  /**
   * Adds the specified amount of seconds to the duration of the Timer
   *
   * @param seconds amount of seconds the Timer should go for
   * @return Object this method was called on for method chaining.
   */
  @NonNull
  @CheckResult
  public Timer seconds(@NonNull Integer seconds) {
    checkNotNull(seconds, "seconds == null");
    this.seconds = seconds;
    return this;
  }

  /**
   * Adds a flag to skip the Timer activity UI.
   * If true this Timer will be set automatically without leaving the current activity
   * If false this Timer will be opened in a new Activity where the user needs to set the timer
   *
   * @param skipUi The flag used to skip the Timer activity UI
   * @return Object this method was called on for method chaining.
   */
  @NonNull
  @CheckResult
  public Timer skipUi(@NonNull Boolean skipUi) {
    checkNotNull(skipUi, "skipUi == null");
    this.skipUi = skipUi;
    return this;
  }

  /**
   * Adds a message to the timer which is displayed when the Timer expires
   *
   * @param message The message to add to the Timer
   * @return Object this method was called on for method chaining.
   */
  @NonNull
  @CheckResult
  public Timer message(@NonNull String message) {
    checkNotNull(message, "message == null");
    this.message = message;
    return this;
  }

  /**
   * Creates and returns an Intent that will create and configure an alarm with the values
   * provided.
   */
  @NonNull
  @CheckResult
  public Intent asIntent() {
    Intent timerIntent = new Intent(AlarmClock.ACTION_SET_TIMER);

    //get total amount of seconds
    int totalSeconds = 0;
    if (seconds != null) {
      totalSeconds += seconds;
    }
    if (minutes != null) {
      totalSeconds += (minutes * 60);
    }
    if (hours != null) {
      totalSeconds += (hours * 60 * 60);
    }
    if (totalSeconds > 0) {
      timerIntent.putExtra(AlarmClock.EXTRA_LENGTH, totalSeconds);
    }

    if (this.message != null) {
      timerIntent.putExtra(AlarmClock.EXTRA_MESSAGE, this.message);
    }
    if (this.skipUi != null) {
      timerIntent.putExtra(AlarmClock.EXTRA_SKIP_UI, this.skipUi);
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      timerIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
    } else {
      //noinspection deprecation
      timerIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
    }

    return timerIntent;
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
