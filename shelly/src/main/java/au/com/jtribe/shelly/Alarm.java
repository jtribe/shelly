package au.com.jtribe.shelly;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.AlarmClock;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static au.com.jtribe.shelly.Preconditions.checkNotNull;

/**
 * Created by mathias on 12/10/2016.
 */

public final class Alarm {

  private String message;
  private Integer hour;
  private Integer minute;

  //TODO:
  private ArrayList<Integer> daysToRepeat;
  private Uri ringTone;
  private Boolean vibrate;
  private Boolean silenceRingTone;
  private Boolean skipUi;

  Alarm() {
    this.daysToRepeat = new ArrayList<>(7);
  }

  @NonNull
  @CheckResult
  public Alarm message(@NonNull String message) {
    checkNotNull(message, "message == null");
    this.message = message;
    return this;
  }


  //Regarding validation: The intent will accept hours and minutes outside of their natural range
  //However it will just require the user to set the time of the alarm if they provide invalid hours/minutes in the intent
  @NonNull
  @CheckResult
  public Alarm hour(@NonNull Integer hour) {
    checkNotNull(hour, "hour == null");
    //if ( hour > 24 || hour < 0 )  // TODO: validate the hour
    this.hour = hour;
    return this;
  }

  @NonNull
  @CheckResult
  public Alarm minute(@NonNull Integer minute) {
    checkNotNull(minute, "minute == null");
    //if ( minute > 60 || minute < 0 )  // TODO: validate the hour
    this.minute = minute;
    return this;
  }

  //When true the UI of clock/alarm is never shown, the alarm is still set but the alarm activity doesnt open
  @NonNull
  @CheckResult
  public Alarm skipUi(@NonNull Boolean skipUi) {
    checkNotNull(skipUi, "skipUi == null");
    this.skipUi = skipUi;
    return this;
  }

  @NonNull
  @CheckResult
  public Alarm ringTone(@NonNull Uri ringTone) {
    checkNotNull(ringTone, "ringTone == null");
    this.ringTone = ringTone;
    return this;
  }

  @NonNull
  @CheckResult
  public Alarm silenceRingTone(@NonNull Boolean silenceRingTone) {
    checkNotNull(silenceRingTone, "silenceRingTone == null");
    this.silenceRingTone = silenceRingTone;
    return this;
  }

  @NonNull
  @CheckResult
  public Alarm vibrate(@NonNull Boolean vibrate) {
    checkNotNull(vibrate, "vibrate == null");
    this.vibrate = vibrate;
    return this;
  }

  @NonNull
  @CheckResult
  public Alarm daysToRepeat(@NonNull Integer... days) {
    checkNotNull(days, "days == null");
    this.daysToRepeat.addAll(Arrays.asList(days));
    return this;
  }




  /**
   * Creates and returns an Intent that will create and configure an alarm with the values provided.
   */
  @NonNull
  @CheckResult
  public Intent asIntent() {
    Intent alarmClockIntent = new Intent(AlarmClock.ACTION_SET_ALARM);

    if ( this.message != null)
      alarmClockIntent.putExtra(AlarmClock.EXTRA_MESSAGE, this.message);
    if ( this.hour != null)  //Should i validate here also thats its between 0-24?
      alarmClockIntent.putExtra(AlarmClock.EXTRA_HOUR, this.hour);
    if ( this.minute != null)  //Should i validate here also thats its between 0-60?
      alarmClockIntent.putExtra(AlarmClock.EXTRA_MINUTES, this.minute);
    if ( this.skipUi != null)
      alarmClockIntent.putExtra(AlarmClock.EXTRA_SKIP_UI, this.skipUi);
    if ( this.ringTone != null)
      alarmClockIntent.putExtra(AlarmClock.EXTRA_RINGTONE, this.ringTone.toString());
    //if silenceRingTone is set override any set ringtones
    if ( this.silenceRingTone != null)
      if ( this.silenceRingTone)
        alarmClockIntent.putExtra(AlarmClock.EXTRA_RINGTONE, AlarmClock.VALUE_RINGTONE_SILENT);
    if ( this.vibrate != null)
      alarmClockIntent.putExtra(AlarmClock.EXTRA_VIBRATE, this.vibrate);
    if ( !this.daysToRepeat.isEmpty() )
      alarmClockIntent.putIntegerArrayListExtra(AlarmClock.EXTRA_DAYS, this.daysToRepeat);


    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      alarmClockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
    } else {
      //noinspection deprecation
      alarmClockIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
    }

    return alarmClockIntent;
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
