package au.com.jtribe.shelly;

import android.content.Intent;
import android.os.Build;
import android.provider.CalendarContract;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static au.com.jtribe.shelly.Preconditions.checkNotNull;

/**
 * Created by mathias on 13/10/2016.
 */

public final class Calendar {

  private String eventTitle;

  //Time = ms since epoch
  //Need a smart API for this so they don't need to enter in time as longs.
  private Long eventStartTime;
  private Long eventEndTime;

  private String eventDescription;
  private String eventLocation;
  private final List<String> attendeeEmails;
  private Boolean isAllDayEvent;


  Calendar() {
    attendeeEmails = new ArrayList<>();
  }

  @NonNull
  @CheckResult
  public Calendar eventTitle(@NonNull String eventTitle) {
    checkNotNull(eventTitle, "eventTitle == null");
    this.eventTitle = eventTitle;
    return this;
  }

  @NonNull
  @CheckResult
  public Calendar eventDescription(@NonNull String eventDescription) {
    checkNotNull(eventDescription, "eventDescription == null");
    this.eventDescription = eventDescription;
    return this;
  }

  @NonNull
  @CheckResult
  public Calendar eventLocation(@NonNull String eventLocation) {
    checkNotNull(eventLocation, "eventLocation == null");
    this.eventLocation = eventLocation;
    return this;
  }

  @NonNull
  @CheckResult
  public Calendar eventStartTime(@NonNull java.util.Calendar calendar) {
    checkNotNull(calendar, "calendar == null");
    this.eventStartTime = calendar.getTime().getTime();
    return this;
  }

  @NonNull
  @CheckResult
  public Calendar eventEndTime(@NonNull java.util.Calendar calendar) {
    checkNotNull(calendar, "calendar == null");
    this.eventEndTime = calendar.getTime().getTime();
    return this;
  }

  @NonNull
  @CheckResult
  public Calendar isAllDayEvent(@NonNull Boolean isAllDayEvent) {
    checkNotNull(isAllDayEvent, "isAllDayEvent == null");
    this.isAllDayEvent = isAllDayEvent;
    return this;
  }

  @NonNull
  @CheckResult
  public Calendar attendeeEmails(@NonNull String... attendeeEmails) {
    checkNotNull(attendeeEmails, "attendeeEmails == null");
    this.attendeeEmails.addAll(Arrays.asList(attendeeEmails));
    return this;
  }




  /**
   * Creates and returns an Intent that will create and configure an alarm with the values
   * provided.
   */
  @NonNull
  @CheckResult
  public Intent asIntent() {
    Intent calendarIntent = new Intent(Intent.ACTION_INSERT);
    //Don't need both, one or the other seem to achieve the same thing, maybe api < 14 need the setType
    calendarIntent.setData(CalendarContract.Events.CONTENT_URI);
    calendarIntent.setType("vnd.android.cursor.dir/event");

    if ( this.eventTitle != null)
      calendarIntent.putExtra(CalendarContract.Events.TITLE, this.eventTitle);
    if ( this.eventDescription != null)
      calendarIntent.putExtra(CalendarContract.Events.DESCRIPTION, this.eventDescription);
    if ( this.eventLocation != null)
      calendarIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, this.eventLocation);
    if ( this.eventStartTime != null)
      calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, this.eventStartTime);
    if ( this.eventEndTime != null)
      calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, this.eventEndTime);
    if (this.isAllDayEvent != null)
      calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, this.isAllDayEvent);
    if ( !this.attendeeEmails.isEmpty()) {
      String[] emailsArray = new String[this.attendeeEmails.size()];
      calendarIntent.putExtra(Intent.EXTRA_EMAIL, this.attendeeEmails.toArray(emailsArray));
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      calendarIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
    } else {
      //noinspection deprecation
      calendarIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
    }

    return calendarIntent;
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
